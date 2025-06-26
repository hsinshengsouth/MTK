package idv.po.mtk_src.movie.domain.command;

import idv.po.mtk_src.movie.domain.model.Screen;

import java.util.Optional;
import java.util.UUID;

public interface ScreenRepository {

  Optional<Screen> findByScreenId(UUID screenId);
}
