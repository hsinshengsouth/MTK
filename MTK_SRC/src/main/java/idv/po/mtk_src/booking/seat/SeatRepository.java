package idv.po.mtk_src.booking.seat;

import java.util.List;
import java.util.UUID;

public interface SeatRepository {

  List<Seat> findSeatsByScreenAndShowtime(UUID screenId, UUID showtimeId);

  Long countSeatsByScreenAndShowtime(UUID screenId, UUID showtimeId);
}
