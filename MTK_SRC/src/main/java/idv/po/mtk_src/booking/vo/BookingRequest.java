package idv.po.mtk_src.booking.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {

  UUID movieId;
  UUID memberId;
  UUID ticketId;
  UUID theaterId;
  UUID screenId;
  UUID showtimeId;
  UUID seatId;
  List<SeatRequest> seats;
}
