package idv.po.mtk_src.movie.infra;

import idv.po.mtk_src.infrastructure.jpa.TheaterJpaRepository;
import idv.po.mtk_src.movie.domain.command.TheaterRepository;
import idv.po.mtk_src.movie.domain.model.Theater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TheaterRepositoryImpl implements TheaterRepository {

  private final TheaterJpaRepository theaterJpaRepository;

  @Override
  public Optional<Theater> findByTheaterId(UUID theaterId) {
    return theaterJpaRepository.findByTheaterId(theaterId);
  }
}
