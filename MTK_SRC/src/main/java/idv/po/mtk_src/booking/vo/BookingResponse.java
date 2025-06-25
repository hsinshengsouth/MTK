package idv.po.mtk_src.booking.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

    UUID ticketId;
    List<UUID> ticketDetailId;
    UUID showtimeId;
    String movieName;
    String screenName;
    List<SeatInfo> seats;
    ZonedDateTime showTime;
    BigDecimal totalPrice;
    String status;          // "SUCCESS", "PARTIAL", "FAIL"
    ZonedDateTime bookingTime;



}
