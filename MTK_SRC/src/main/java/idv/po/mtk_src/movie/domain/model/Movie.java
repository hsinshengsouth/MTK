package idv.po.mtk_src.movie.domain.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import idv.po.mtk_src.movie.domain.command.AddMovieCommand;
import idv.po.mtk_src.movie.domain.event.MovieCreatedEvent;
import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "movies")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Movie {

  @Id
  @GeneratedValue(generator = "uuid2")
  @Column(name = "movie_id", updatable = false, nullable = false)
  private UUID movieId;

  private String chTitle;
  private String enTitle;
  private String keyword;
  private String director;
  private String description;
  private Double rating;
  private Integer runtimeMinutes;
  private String posterUrl;
  private String movieStatus;
  private ZonedDateTime startDate;
  private ZonedDateTime endDate;
  private ZonedDateTime createTime;
  private ZonedDateTime updateTime;

  @Type(JsonBinaryType.class)
  @Column(name = "actors", columnDefinition = "jsonb")
  private List<String> actors;

  @Type(JsonBinaryType.class)
  @Column(name = "genres", columnDefinition = "jsonb")
  private List<String> genres;

  public Movie(String ignoredMovieTitle) {}

  public static Movie createFromCommand(AddMovieCommand cmd) {
    return Movie.builder()
        .enTitle(cmd.enTitle())
        .chTitle(cmd.chTitle())
        .keyword(cmd.keyword())
        .director(cmd.director())
        .description(cmd.description())
        .rating(cmd.rating())
        .runtimeMinutes(cmd.runtimeMinutes())
        .posterUrl(cmd.posterUrl())
        .movieStatus(cmd.movieStatus())
        .startDate(cmd.startDate())
        .endDate(cmd.endDate())
        .createTime(ZonedDateTime.now())
        .updateTime(cmd.updateTime())
        .genres(cmd.genres())
        .actors(cmd.actors())
        .build();
  }

  public MovieCreatedEvent createMovieCreatedEvent() {
    return new MovieCreatedEvent(
        this.movieId,
        this.chTitle,
        this.enTitle,
        this.keyword,
        this.genres,
        this.director,
        this.actors,
        this.description,
        this.rating,
        this.runtimeMinutes,
        this.posterUrl,
        this.movieStatus,
        this.startDate,
        this.endDate);
  }
}
