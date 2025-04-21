package idv.po.mtk_src.infrastructure.repository;

import idv.po.mtk_src.movie.domain.model.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowtimeJpaRepository  extends JpaRepository<ShowTime,String> {
}
