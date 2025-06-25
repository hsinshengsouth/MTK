package idv.po.mtk_src.booking.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class BookingSuccessEvent {
    private UUID memberId;
    private String memberName;
    private String memberEmail;
    private String movieName;
    private String screenName;
    private ZonedDateTime showTime;
    private ZonedDateTime createTime;
    private List<SeatInfo> seats;
    private BigDecimal totalPrice;


}
