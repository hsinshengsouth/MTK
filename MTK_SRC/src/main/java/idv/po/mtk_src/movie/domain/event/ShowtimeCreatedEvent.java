package idv.po.mtk_src.movie.domain.event;

import idv.po.mtk_src.movie.domain.model.Movie;
import idv.po.mtk_src.movie.domain.model.Screen;
import idv.po.mtk_src.movie.domain.model.Theater;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

public record ShowtimeCreatedEvent (
        UUID showtimeId,
        Movie movie,
        Theater theater,
        Screen screen,
        ZonedDateTime dateTime,
        BigDecimal price
){
}
