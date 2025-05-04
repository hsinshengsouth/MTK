package idv.po.mtk_src.infrastructure.repository;

import idv.po.mtk_src.movie.domain.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ScreenJpaRepository  extends JpaRepository<Screen, UUID> {
    Optional<Screen> findByScreenId(UUID screenId);
}
