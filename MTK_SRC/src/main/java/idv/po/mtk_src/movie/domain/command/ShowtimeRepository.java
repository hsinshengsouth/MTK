package idv.po.mtk_src.movie.domain.command;

import idv.po.mtk_src.movie.domain.model.ShowTime;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShowtimeRepository {

    void saveAll(List<ShowTime> showTimes);
    Optional<ShowTime> findByShowTimeId(UUID showtimeId);
}
