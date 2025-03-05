package idv.po.mtk_src.movie.domain.query.readmodel;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document(indexName="movie")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieReadModel  extends  MovieInfo{
    @Id
    private String   movieID;

    @Field(type = FieldType.Text, analyzer = "movie_analyzer")
    private String enMovieName;

    @Field(type=FieldType.Text,analyzer = "chinese_analyzer")
    private String chMovieName;



    @Field(type = FieldType.Keyword)
    private List<String> genres;

    @Field(type = FieldType.Text)
    private String director;

    @Field(type = FieldType.Text)
    private List<String> actors;

    @Field(type = FieldType.Text, analyzer = "movie_analyzer")
    private String description;

    @Field(type = FieldType.Float)
    private float rating;

    @Field(type = FieldType.Integer)
    private int runtimeMinutes;

    @Field(type = FieldType.Keyword)
    private String posterUrl;

    @Field(type = FieldType.Keyword)
    private String movieStatus;

    @Field(type = FieldType.Date)
    private LocalDateTime startDate;

    @Field(type = FieldType.Date)
    private LocalDateTime endDate;

    @Field(type = FieldType.Date)
    private LocalDateTime createTime;

    @Field(type = FieldType.Date)
    private LocalDateTime updateTime;

    @Field(type = FieldType.Nested)
    private List<Showtime> showtime;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Showtime {
        @Field(type = FieldType.Keyword)
        private String theaterId;

        @Field(type = FieldType.Keyword)
        private String theaterName;

        @Field(type = FieldType.Date)
        private LocalDateTime dateTime;

        @Field(type = FieldType.Integer)
        private int seatsAvailable;

        @Field(type = FieldType.Float)
        private BigDecimal price;
    }
}

