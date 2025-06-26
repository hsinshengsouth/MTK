package idv.po.mtk_src.booking.app;

import idv.po.mtk_src.booking.seat.Seat;
import idv.po.mtk_src.booking.seat.SeatRepository;
import idv.po.mtk_src.booking.ticketdetail.TicketDetail;
import idv.po.mtk_src.booking.ticketdetail.TicketDetailRepository;
import idv.po.mtk_src.booking.vo.*;
import idv.po.mtk_src.infrastructure.exception.ResourceNotFoundException;
import idv.po.mtk_src.infrastructure.utils.KafkaEventPublisher;
import idv.po.mtk_src.management.domain.ticket.TicketRepository;
import idv.po.mtk_src.member.domain.member.Member;
import idv.po.mtk_src.member.domain.member.MemberRepository;
import idv.po.mtk_src.movie.domain.command.ShowtimeRepository;
import idv.po.mtk_src.movie.domain.model.ShowTime;
import lombok.AllArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingService {

  private final SeatRepository seatRepository;
  private final TicketRepository ticketRepository;
  private final TicketDetailRepository ticketDetailRepository;
  private final ShowtimeRepository showtimeRepository;
  private final MemberRepository memberRepository;

  private final RedissonClient redissonClient;
  private final PaymentService paymentService;

  private final KafkaEventPublisher kafkaPublisher;

  public SeatStatusResponse checkSeatStatus(UUID screenId, UUID showtimeId) {

    List<Seat> availableSeats = seatRepository.findSeatsByScreenAndShowtime(screenId, showtimeId);

    List<SeatStatusDto> seats =
        availableSeats.stream()
            .map(seat -> new SeatStatusDto(seat.getRowLabel(), seat.getSeatNo(), true))
            .toList();

    Long seatCount = seatRepository.countSeatsByScreenAndShowtime(screenId, showtimeId);

    return new SeatStatusResponse(showtimeId, seatCount, seats);
  }

  @Transactional
  public BookingResponse bookingTicket(BookingRequest request) {

    ticketRepository
        .getTicket(request.getTicketId())
        .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));

    ShowTime show =
        showtimeRepository
            .findByShowTimeId(request.getShowtimeId())
            .orElseThrow(() -> new ResourceNotFoundException("Showtime not found"));

    List<SeatInfo> seatInfos = new ArrayList<>();
    List<TicketDetail> ticketDetails = new ArrayList<>();
    List<RLock> locks = new ArrayList<>();
    BigDecimal seatNumber = BigDecimal.valueOf(request.getSeats().size());

    if (!CollectionUtils.isEmpty(request.getSeats())) {
      // 檢查所有位子是否都還沒被訂
      for (SeatRequest seat : request.getSeats()) {
        String lockKey =
            String.format(
                "seatlock:showtime:%s:row:%s:no:%d",
                request.getShowtimeId(), seat.getRowLabel(), seat.getSeatNo());
        RLock lock = redissonClient.getLock(lockKey);

        if (lock.isHeldByCurrentThread()) {
          locks.add(lock);
        } else {
          // 沒搶到任何一個座位就全部釋放，回傳失敗
          releaseLocks(locks);
          throw new IllegalStateException(
              "Seat " + seat.getRowLabel() + "-" + seat.getSeatNo() + " 已被鎖定搶走");
        }

        String rowLabel = seat.getRowLabel();
        Integer seatNo = seat.getSeatNo();

        boolean seatBooked =
            ticketDetailRepository.existsByShowtimeIdAndRowLabelAndSeatNoAndBookingStatus(
                request.getShowtimeId(), rowLabel, seatNo, "PENDING");
        if (seatBooked) {
          throw new IllegalStateException("Seat " + rowLabel + "-" + seatNo + " booked");
        }
      }

      ticketDetails =
          request.getSeats().stream()
              .map(
                  seatReq ->
                      TicketDetail.builder()
                          .ticketId(request.getTicketId())
                          .memberId(request.getMemberId())
                          .rowLabel(seatReq.getRowLabel())
                          .seatNo(seatReq.getSeatNo())
                          .bookingStatus("PENDING")
                          .createTime(ZonedDateTime.now())
                          .build())
              .collect(Collectors.toList());

      ticketDetails = ticketDetailRepository.persistTicketDetail(ticketDetails);

      seatInfos =
          request.getSeats().stream()
              .map(
                  seatReq ->
                      SeatInfo.builder()
                          .rowLabel(seatReq.getRowLabel())
                          .seatNo(seatReq.getSeatNo())
                          .build())
              .collect(Collectors.toList());
    }

    return BookingResponse.builder()
        .seats(seatInfos)
        .status("PENDING")
        .ticketDetailId(
            ticketDetails.stream().map(TicketDetail::getTicketId).collect(Collectors.toList()))
        .screenName(show.getScreen().getScreenName())
        .movieName(show.getMovie().getChTitle())
        .showtimeId(show.getShowtimeId())
        .bookingTime(ZonedDateTime.now())
        .totalPrice(seatNumber.multiply(show.getPrice()))
        .build();
  }

  @Transactional
  public String confirmPayment(List<UUID> ticketDetailId, PaymentInfo paymentInfo) {

    // 1. 呼叫模擬金流
    boolean paid = paymentService.processPayment(paymentInfo, paymentInfo.getPayAmount());

    List<TicketDetail> details = ticketDetailRepository.findAllDetail(ticketDetailId);
    List<RLock> locks = new ArrayList<>();

    try {
      for (TicketDetail d : details) {
        String lockKey =
            String.format(
                "seatlock:showtime:%s:row:%s:no:%d",
                d.getShowtimeId(), d.getRowLabel(), d.getSeatNo());
        RLock lock = redissonClient.getLock(lockKey);
        if (lock.isHeldByCurrentThread()) {
          locks.add(lock);
        }
      }

      if (paid) {
        details.forEach(d -> d.setBookingStatus("BOOKED"));
      } else {
        details.forEach(d -> d.setBookingStatus("CANCELLED"));
        return "Pay Fail";
      }

      details = ticketDetailRepository.persistTicketDetail(details);
      List<SeatInfo> seats =
          details.stream()
              .map(
                  detail ->
                      SeatInfo.builder()
                          .rowLabel(detail.getRowLabel())
                          .seatNo(detail.getSeatNo())
                          .build())
              .toList();

      afterBookingSuccess(details.get(0), seats);

    } finally {
      releaseLocks(locks);
    }

    return "Pay Success";
  }

  public void afterBookingSuccess(TicketDetail ticket, List<SeatInfo> seats) {

    ShowTime show =
        showtimeRepository
            .findByShowTimeId(ticket.getShowtimeId())
            .orElseThrow(() -> new ResourceNotFoundException("Showtime not found"));

    Member member =
        memberRepository
            .findMemberById(ticket.getMemberId())
            .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

    int seatNum = seats.size();

    BookingSuccessEvent event =
        BookingSuccessEvent.builder()
            .memberId(ticket.getMemberId())
            .memberName(member.getMemberName())
            .memberEmail(member.getMemberEmail())
            .movieName(show.getMovie().getChTitle())
            .screenName(show.getScreen().getScreenName())
            .showTime(show.getDateTime())
            .createTime(ZonedDateTime.now())
            .seats(seats)
            .totalPrice(show.getPrice().multiply(BigDecimal.valueOf(seatNum)))
            .build();

    String kafkaKey =
        String.format("%s:%s", ticket.getMemberId().toString(), ticket.getTicketId().toString());

    kafkaPublisher.publish("booking-success", kafkaKey, event);
  }

  private void releaseLocks(List<RLock> locks) {
    for (RLock lock : locks) {
      if (lock.isHeldByCurrentThread()) {
        lock.unlock();
      }
    }
  }
}
