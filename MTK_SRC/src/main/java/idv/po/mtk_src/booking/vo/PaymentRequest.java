package idv.po.mtk_src.booking.vo;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
  private List<UUID> uuids;
  String cardLastFour;
  BigDecimal payAmount;
}
