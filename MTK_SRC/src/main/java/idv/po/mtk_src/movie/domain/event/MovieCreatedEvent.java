package idv.po.mtk_src.movie.domain.event;

import java.time.ZonedDateTime;
import java.util.List;

public record MovieCreatedEvent(
        String movieId,
        String title,
        List<String> genres,
        String director,
        List<String> actors,
        String description,
        double rating,
        int runtimeMinutes,
        String posterUrl,
        String movieStatus,
        ZonedDateTime startDate,
        ZonedDateTime endDate
) {






}
