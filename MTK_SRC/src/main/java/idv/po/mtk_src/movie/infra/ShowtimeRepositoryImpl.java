package idv.po.mtk_src.movie.infra;

import idv.po.mtk_src.infrastructure.jpa.ShowtimeJpaRepository;
import idv.po.mtk_src.movie.domain.command.ShowtimeRepository;
import idv.po.mtk_src.movie.domain.model.ShowTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ShowtimeRepositoryImpl implements ShowtimeRepository {

  private final ShowtimeJpaRepository showtimeJpaRepository;

  @Override
  public void saveAll(List<ShowTime> showTimes) {
    showtimeJpaRepository.saveAll(showTimes);
  }

  @Override
  public Optional<ShowTime> findByShowTimeId(UUID showtimeId) {
    return showtimeJpaRepository.findByShowtimeId(showtimeId);
  }
}
