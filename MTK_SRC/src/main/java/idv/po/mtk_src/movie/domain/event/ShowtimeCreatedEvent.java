package idv.po.mtk_src.movie.domain.event;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

public record ShowtimeCreatedEvent(
    UUID showtimeId,
    UUID movieId,
    UUID theaterId,
    UUID screenId,
    ZonedDateTime dateTime,
    BigDecimal price) {}
