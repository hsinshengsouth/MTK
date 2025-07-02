package idv.po.mtk_src.booking.app;

import idv.po.mtk_src.booking.ticketdetail.TicketDetail;
import idv.po.mtk_src.booking.vo.PaymentRequest;
import idv.po.mtk_src.booking.vo.SeatInfo;
import idv.po.mtk_src.infrastructure.exception.ResourceNotFoundException;
import idv.po.mtk_src.management.domain.ticket.TicketRepository;
import io.micrometer.common.util.StringUtils;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {

  private final TicketRepository ticketRepository;

  public boolean processPayment(PaymentRequest paymentRequest, List<TicketDetail> details) {

    // 模擬：如果卡號結尾是偶數，付款成功；奇數，失敗
    String card = paymentRequest.getCardLastFour();

    BigDecimal seatSize =
        new BigDecimal(
            details.stream()
                .map(
                    detail ->
                        SeatInfo.builder()
                            .rowLabel(detail.getRowLabel())
                            .seatNo(detail.getSeatNo())
                            .build())
                .toList()
                .size());

    BigDecimal ticketPrice =
        ticketRepository
            .getTicket(details.get(0).getTicketId())
            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"))
            .getPrice()
            .multiply(seatSize);

    return StringUtils.isNotBlank(card)
        && Character.getNumericValue(card.charAt(card.length() - 1)) % 2 == 0
        && paymentRequest.getPayAmount().compareTo(ticketPrice) == 0;
  }
}
