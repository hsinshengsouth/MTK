package idv.po.mtk_src.booking.infra;

import idv.po.mtk_src.booking.seat.Seat;
import idv.po.mtk_src.booking.seat.SeatRepository;
import idv.po.mtk_src.infrastructure.jpa.SeatJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
@RequiredArgsConstructor
public class SeatRepositoryImpl implements SeatRepository {

  private final SeatJpaRepository seatJpaRepository;

  @Override
  public List<Seat> findSeatsByScreenAndShowtime(UUID screenId, UUID showtimeId) {
    return seatJpaRepository.findSeatsByScreenAndShowtime(screenId, showtimeId);
  }

  @Override
  public Long countSeatsByScreenAndShowtime(UUID screenId, UUID showtimeId) {
    return seatJpaRepository.countSeatsByScreenAndShowtime(screenId, showtimeId);
  }
}
