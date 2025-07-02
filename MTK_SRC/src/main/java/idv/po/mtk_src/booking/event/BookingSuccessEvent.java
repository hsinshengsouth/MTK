package idv.po.mtk_src.booking.event;

import idv.po.mtk_src.booking.vo.SeatInfo;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public record BookingSuccessEvent(
         UUID memberId,
         String memberName,
         String memberEmail,
         String movieName,
         String screenName,
         ZonedDateTime showTime,
         ZonedDateTime createTime,
         List<SeatInfo> seats,
         BigDecimal totalPrice
) {

}
