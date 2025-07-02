package idv.po.mtk_src.booking.app;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import idv.po.mtk_src.booking.ticketdetail.TicketDetail;
import idv.po.mtk_src.booking.ticketdetail.TicketDetailRepository;
import idv.po.mtk_src.booking.vo.BookingRequest;
import idv.po.mtk_src.booking.vo.BookingResponse;
import idv.po.mtk_src.booking.vo.PaymentRequest;
import idv.po.mtk_src.booking.vo.SeatRequest;
import idv.po.mtk_src.management.domain.ticket.Ticket;
import idv.po.mtk_src.management.domain.ticket.TicketRepository;
import idv.po.mtk_src.movie.domain.command.ShowtimeRepository;
import idv.po.mtk_src.movie.domain.model.Movie;
import idv.po.mtk_src.movie.domain.model.Screen;
import idv.po.mtk_src.movie.domain.model.ShowTime;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

class BookingServiceBookTest {
  
  @Mock TicketRepository ticketRepository;
  @Mock TicketDetailRepository ticketDetailRepository;
  @Mock ShowtimeRepository showtimeRepository;
  @Mock RedissonClient redissonClient;
  @Mock PaymentService paymentService;


  @InjectMocks BookingService bookingService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testBookingTicket_success() {
    UUID ticketId = UUID.randomUUID();
    UUID showtimeId = UUID.randomUUID();
    UUID memberId = UUID.randomUUID();

    BookingRequest request = new BookingRequest();
    request.setTicketId(ticketId);
    request.setShowtimeId(showtimeId);
    request.setMemberId(memberId);
    request.setSeats(List.of(new SeatRequest(UUID.randomUUID(), "A", 1)));

    when(ticketRepository.getTicket(ticketId)).thenReturn(Optional.of(mock(Ticket.class)));
    ShowTime mockShow = mock(ShowTime.class);
    when(mockShow.getShowtimeId()).thenReturn(showtimeId);
    when(mockShow.getScreen()).thenReturn(new Screen("IMAX"));
    when(mockShow.getMovie()).thenReturn(new Movie("Inception"));
    when(mockShow.getPrice()).thenReturn(BigDecimal.valueOf(320));
    when(showtimeRepository.findByShowTimeId(showtimeId)).thenReturn(Optional.of(mockShow));

    RLock mockLock = mock(RLock.class);
    when(redissonClient.getLock(anyString())).thenReturn(mockLock);
    when(mockLock.tryLock()).thenReturn(true);

    when(ticketDetailRepository.existsByShowtimeIdAndRowLabelAndSeatNoAndBookingStatus(
            any(), anyString(), anyInt(), anyString()))
        .thenReturn(false);

    TicketDetail savedDetail =
        TicketDetail.builder()
            .ticketDetailId(UUID.randomUUID())
            .ticketId(ticketId)
            .memberId(memberId)
            .showtimeId(showtimeId)
            .rowLabel("A")
            .seatNo(1)
            .bookingStatus("PENDING")
            .createTime(ZonedDateTime.now())
            .build();

    when(ticketDetailRepository.persistTicketDetail(anyList())).thenReturn(List.of(savedDetail));

    BookingResponse response = bookingService.bookingTicket(request);

    assertNotNull(response);
    assertEquals("PENDING", response.getStatus());
    assertEquals(1, response.getSeats().size());
    assertEquals("A", response.getSeats().get(0).getRowLabel());
    assertEquals(1, response.getSeats().get(0).getSeatNo());
    assertEquals("IMAX", response.getScreenName());
    assertEquals("Inception", response.getMovieName());
    assertEquals(BigDecimal.valueOf(320), response.getTotalPrice());
  }

  @Test
  void testBookingTicket_seatAlreadyBooked_throwsException() {
    UUID ticketId = UUID.randomUUID();
    UUID showtimeId = UUID.randomUUID();
    UUID memberId = UUID.randomUUID();

    BookingRequest request = new BookingRequest();
    request.setTicketId(ticketId);
    request.setShowtimeId(showtimeId);
    request.setMemberId(memberId);
    request.setSeats(List.of(new SeatRequest(UUID.randomUUID(), "A", 1)));

    when(ticketRepository.getTicket(ticketId)).thenReturn(Optional.of(mock(Ticket.class)));
    when(showtimeRepository.findByShowTimeId(showtimeId))
        .thenReturn(Optional.of(mock(ShowTime.class)));
    RLock mockLock = mock(RLock.class);
    when(redissonClient.getLock(anyString())).thenReturn(mockLock);
    when(mockLock.tryLock()).thenReturn(true);

    // Seat is already booked
    when(ticketDetailRepository.existsByShowtimeIdAndRowLabelAndSeatNoAndBookingStatus(
            any(), anyString(), anyInt(), anyString()))
        .thenReturn(true);

    IllegalStateException ex =
        assertThrows(IllegalStateException.class, () -> bookingService.bookingTicket(request));
    assertTrue(ex.getMessage().contains("booked"));
  }

  @Test
  void testBookingTicket_lockNotAcquired_throwsException() {
    UUID ticketId = UUID.randomUUID();
    UUID showtimeId = UUID.randomUUID();
    UUID memberId = UUID.randomUUID();

    BookingRequest request = new BookingRequest();
    request.setTicketId(ticketId);
    request.setShowtimeId(showtimeId);
    request.setMemberId(memberId);
    request.setSeats(List.of(new SeatRequest(UUID.randomUUID(), "A", 1)));

    when(ticketRepository.getTicket(ticketId)).thenReturn(Optional.of(mock(Ticket.class)));
    when(showtimeRepository.findByShowTimeId(showtimeId))
        .thenReturn(Optional.of(mock(ShowTime.class)));
    RLock mockLock = mock(RLock.class);
    when(redissonClient.getLock(anyString())).thenReturn(mockLock);
    when(mockLock.tryLock()).thenReturn(false);

    IllegalStateException ex =
        assertThrows(IllegalStateException.class, () -> bookingService.bookingTicket(request));
    assertTrue(ex.getMessage().contains("已被鎖定搶走"));
  }

  @Test
  void testConfirmPayment_success() {
    // Arrange
    List<UUID> uuids = List.of(UUID.randomUUID());
    PaymentRequest paymentRequest = new PaymentRequest();
    paymentRequest.setUuids(uuids);
    paymentRequest.setCardLastFour("1234");
    paymentRequest.setPayAmount(BigDecimal.valueOf(320));

    // Mock TicketDetail
    TicketDetail ticketDetail =
        TicketDetail.builder()
            .ticketDetailId(UUID.randomUUID())
            .memberId(UUID.randomUUID())
            .showtimeId(UUID.randomUUID())
            .rowLabel("A")
            .seatNo(1)
            .bookingStatus("PENDING")
            .createTime(ZonedDateTime.now())
            .build();

    List<TicketDetail> details = List.of(ticketDetail);

    when(ticketDetailRepository.findAllDetail(uuids)).thenReturn(details);
    when(paymentService.processPayment(paymentRequest, details)).thenReturn(true);

    // Mock Redisson lock
    RLock mockLock = mock(RLock.class);
    when(redissonClient.getLock(anyString())).thenReturn(mockLock);
    when(mockLock.isHeldByCurrentThread()).thenReturn(true);

    // For persistTicketDetail, just return updated details
    when(ticketDetailRepository.persistTicketDetail(anyList())).thenReturn(details);

    // Spy for afterBookingSuccess to verify invocation
    BookingService spyService = Mockito.spy(bookingService);
    doNothing().when(spyService).afterBookingSuccess(any(), any());

    // Act
    String result = spyService.confirmPayment(paymentRequest);

    // Assert
    assertEquals("Pay Success", result);
    verify(paymentService).processPayment(paymentRequest, details);
    verify(ticketDetailRepository).persistTicketDetail(anyList());
    verify(spyService).afterBookingSuccess(any(), any());
  }

  @Test
  void testConfirmPayment_fail() {
    // Arrange
    List<UUID> uuids = List.of(UUID.randomUUID());
    PaymentRequest paymentRequest = new PaymentRequest();
    paymentRequest.setUuids(uuids);
    paymentRequest.setCardLastFour("5678");
    paymentRequest.setPayAmount(BigDecimal.valueOf(320));

    // Mock TicketDetail
    TicketDetail ticketDetail =
        TicketDetail.builder()
            .ticketDetailId(UUID.randomUUID())
            .memberId(UUID.randomUUID())
            .showtimeId(UUID.randomUUID())
            .rowLabel("B")
            .seatNo(2)
            .bookingStatus("PENDING")
            .createTime(ZonedDateTime.now())
            .build();

    List<TicketDetail> details = List.of(ticketDetail);

    when(ticketDetailRepository.findAllDetail(uuids)).thenReturn(details);
    when(paymentService.processPayment(paymentRequest, details)).thenReturn(false);

    // Mock Redisson lock
    RLock mockLock = mock(RLock.class);
    when(redissonClient.getLock(anyString())).thenReturn(mockLock);
    when(mockLock.isHeldByCurrentThread()).thenReturn(true);

    // For persistTicketDetail, just return updated details
    when(ticketDetailRepository.persistTicketDetail(anyList())).thenReturn(details);

    // Spy for afterBookingSuccess to verify NOT called
    BookingService spyService = Mockito.spy(bookingService);
    doNothing().when(spyService).afterBookingSuccess(any(), any());

    // Act
    String result = spyService.confirmPayment(paymentRequest);

    // Assert
    assertEquals("Pay Fail", result);
    verify(paymentService).processPayment(paymentRequest, details);
    verify(ticketDetailRepository).persistTicketDetail(anyList());
    verify(spyService, never()).afterBookingSuccess(any(), any());
  }
}
