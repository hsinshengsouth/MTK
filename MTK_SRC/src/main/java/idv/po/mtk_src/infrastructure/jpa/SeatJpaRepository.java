package idv.po.mtk_src.infrastructure.jpa;

import idv.po.mtk_src.booking.seat.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SeatJpaRepository extends JpaRepository<Seat, UUID> {

  @Query(
      """
    SELECT s
    FROM Seat s
    WHERE s.screen.screenId = :screenId
      AND s.isActive = true
      AND NOT EXISTS (
        SELECT 1 FROM TicketDetail td
        WHERE td.showtimeId = :showtimeId
          AND td.rowLabel = s.rowLabel
          AND td.seatNo = s.seatNo
          AND td.bookingStatus = 'BOOKED'
      )
    """)
  List<Seat> findSeatsByScreenAndShowtime(
      @Param("screenId") UUID screenId, @Param("showtimeId") UUID showtimeId);

  @Query(
      """
    SELECT COUNT(s)
    FROM Seat s
    WHERE s.screen.screenId = :screenId
      AND s.isActive = true
      AND NOT EXISTS (
        SELECT 1 FROM TicketDetail td
        WHERE td.showtimeId = :showtimeId
          AND td.rowLabel = s.rowLabel
          AND td.seatNo = s.seatNo
          AND td.bookingStatus = 'BOOKED'
      )
    """)
  Long countSeatsByScreenAndShowtime(
      @Param("screenId") UUID screenId, @Param("showtimeId") UUID showtimeId);
}
