package idv.po.mtk_src.movie.domain.command;

import idv.po.mtk_src.movie.domain.model.Theater;

import java.util.Optional;
import java.util.UUID;

public interface TheaterRepository {

  Optional<Theater> findByTheaterId(UUID theaterId);
}
