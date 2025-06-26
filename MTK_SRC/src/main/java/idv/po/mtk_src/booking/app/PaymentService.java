package idv.po.mtk_src.booking.app;

import idv.po.mtk_src.booking.vo.PaymentInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PaymentService {

  public boolean processPayment(PaymentInfo info, BigDecimal amount) {
    // 模擬：如果卡號結尾是偶數，付款成功；奇數，失敗
    String card = info.getCardLastFour();
    return card != null
        && !card.isEmpty()
        && Character.getNumericValue(card.charAt(card.length() - 1)) % 2 == 0;
  }
}
