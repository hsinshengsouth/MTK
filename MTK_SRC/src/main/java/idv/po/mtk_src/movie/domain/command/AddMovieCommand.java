package idv.po.mtk_src.movie.domain.command;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public record AddMovieCommand(
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
        List<Showtime> showtimes,
        ZonedDateTime startDate,
        ZonedDateTime endDate,
        ZonedDateTime createTime,
        ZonedDateTime updateTime
) {
    public record Showtime(
            String theaterId,
            String theaterName,
            ZonedDateTime dateTime,
            int seatsAvailable,
            BigDecimal price
    ) {


    }




}
