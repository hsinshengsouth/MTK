package idv.po.mtk_src.movie.domain.event;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public record MovieCreatedEvent(
        UUID movieId,
        String chTitle,
        String enTitle,
        String keyword,
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
