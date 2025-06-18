package idv.po.mtk_src.infrastructure.jpa;


import idv.po.mtk_src.movie.domain.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TheaterJpaRepository  extends JpaRepository<Theater, UUID> {
    Optional<Theater> findByTheaterId(UUID theaterId);
}
