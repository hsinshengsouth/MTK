package idv.po.mtk_src.movie.domain.command;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public record AddMovieCommand(
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
    List<Showtime> showtimes,
    ZonedDateTime startDate,
    ZonedDateTime endDate,
    ZonedDateTime createTime,
    ZonedDateTime updateTime) {
  public record Showtime(
      UUID theaterId,
      String theaterName,
      UUID screenId,
      ZonedDateTime dateTime,
      BigDecimal price) {}
}
