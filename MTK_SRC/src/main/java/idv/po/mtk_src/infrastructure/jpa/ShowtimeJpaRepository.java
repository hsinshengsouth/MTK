package idv.po.mtk_src.infrastructure.jpa;

import idv.po.mtk_src.movie.domain.model.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ShowtimeJpaRepository  extends JpaRepository<ShowTime,Long> {
    Optional<ShowTime> findByShowtimeId(UUID showtimeId);
}
