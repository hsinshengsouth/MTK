package idv.po.mtk_src.movie.domain.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Document(indexName = "movie")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieReadModel  {
    @Id
    private String movieID;

    @Field(type = FieldType.Object)
    @JsonProperty("movie_name")
    private MovieName movieName;

    @Field(type = FieldType.Keyword)
    private List<String> genres;

    @Field(type = FieldType.Text)
    private String director;

    @Field(type = FieldType.Text)
    private List<String> actors;

    @Field(type = FieldType.Text, analyzer = "movie_analyzer")
    private String description;

    @Field(type = FieldType.Float)
    private double rating;

    @Field(type = FieldType.Integer)
    @JsonProperty("runtime_minutes")
    private int runtimeMinutes;

    @Field(type = FieldType.Keyword)
    @JsonProperty("poster_url")
    private String posterUrl;

    @Field(type = FieldType.Keyword)
    @JsonProperty("movie_status")
    private String movieStatus;

    @Field(type = FieldType.Date)
    @JsonProperty("start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private ZonedDateTime startDate;

    @Field(type = FieldType.Date)
    @JsonProperty("end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private ZonedDateTime endDate;

    @Field(type = FieldType.Date)
    @JsonProperty("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createTime;

    @Field(type = FieldType.Date)
    @JsonProperty("update_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updateTime;

    @Field(type = FieldType.Nested)
    @JsonProperty("showtimes")
    private List<Showtime> showtime;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MovieName {
        @Field(type = FieldType.Text, analyzer = "movie_analyzer")
        @JsonProperty("enMovieName")
        private String enMovieName;

        @Field(type = FieldType.Text, analyzer = "chinese_analyzer")
        @JsonProperty("chMovieName")
        private String chMovieName;

        @Field(type = FieldType.Keyword)
        @JsonProperty("keyword")
        private String keyword;
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Showtime {
        @Field(type = FieldType.Keyword)
        @JsonProperty("screen_name")
        private String screenName;

        @Field(type = FieldType.Keyword)
        @JsonProperty("theater_name")
        private String theaterName;

        @Field(type = FieldType.Date)
        @JsonProperty("date_time")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private ZonedDateTime dateTime;

        @Field(type = FieldType.Float)
        private BigDecimal price;
    }
}

