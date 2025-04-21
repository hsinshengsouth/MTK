package idv.po.mtk_src.movie.domain.model;

import idv.po.mtk_src.movie.domain.command.AddMovieCommand;
import idv.po.mtk_src.movie.domain.event.MovieCreatedEvent;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


import java.math.BigDecimal;
import java.time.ZonedDateTime;

import java.util.List;

@Entity
@Table(name = "movies")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Movie {

    @jakarta.persistence.Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "movie_id", updatable = false, nullable = false, length = 36)
    private String movieId;

    private String title;
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

    @ElementCollection
    @CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "genre")
    private List<String> genres;

    @ElementCollection
    @CollectionTable(name = "movie_actors", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "actor")
    private List<String> actors;




    public static Movie createFromCommand(AddMovieCommand cmd) {
        return Movie.builder()
                .title(cmd.title())
                .director(cmd.director())
                .description(cmd.description())
                .rating(cmd.rating())
                .runtimeMinutes(cmd.runtimeMinutes())
                .posterUrl(cmd.posterUrl())
                .movieStatus(cmd.movieStatus())
                .startDate(cmd.startDate())
                .endDate(cmd.endDate())
                .createTime(cmd.createTime())
                .updateTime(cmd.updateTime())
                .genres(cmd.genres())
                .actors(cmd.actors())
                .build();
    }


    public MovieCreatedEvent createMovieCreatedEvent() {
        return new MovieCreatedEvent(
                this.movieId,
                this.title,
                this.genres,
                this.director,
                this.actors,
                this.description,
                this.rating,
                this.runtimeMinutes,
                this.posterUrl,
                this.movieStatus,
                this.startDate,
                this.endDate
        );
    }


}
