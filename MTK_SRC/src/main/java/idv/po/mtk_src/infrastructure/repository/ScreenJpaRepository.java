package idv.po.mtk_src.infrastructure.repository;

import idv.po.mtk_src.movie.domain.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScreenJpaRepository  extends JpaRepository<Screen,String> {

    Optional<Screen> findByTheaterIdAndTheaterName(String theaterId, String theaterName);

}
