package idv.po.mtk_src.movie.domain.command;

import idv.po.mtk_src.movie.domain.model.Screen;

import java.util.Optional;

public interface ScreenRepository {

    Optional<Screen> findByTheaterIdAndTheaterName(String theaterId, String theaterName);

}
