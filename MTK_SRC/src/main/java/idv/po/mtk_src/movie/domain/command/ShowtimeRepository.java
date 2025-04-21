package idv.po.mtk_src.movie.domain.command;

import idv.po.mtk_src.movie.domain.model.ShowTime;

import java.util.List;

public interface ShowtimeRepository {

    void saveAll(List<ShowTime> showTimes);


}
