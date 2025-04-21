package idv.po.mtk_src.movie.domain.event;

import idv.po.mtk_src.movie.domain.model.Movie;
import idv.po.mtk_src.movie.domain.model.Screen;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record ShowtimeCreatedEvent (
        Long showtimeId,
        Movie movie,
        Screen screen,
        ZonedDateTime dateTime,
        BigDecimal price
){
}
