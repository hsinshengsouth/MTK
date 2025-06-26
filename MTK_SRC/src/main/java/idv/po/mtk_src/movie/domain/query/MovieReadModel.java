package idv.po.mtk_src.movie.domain.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "movie")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieReadModel {
  @Id private String movieID;

  @Field(type = FieldType.Object)
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
  private int runtimeMinutes;

  @Field(type = FieldType.Keyword)
  private String posterUrl;

  @Field(type = FieldType.Keyword)
  private String movieStatus;

  @Field(type = FieldType.Date)
  // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private ZonedDateTime startDate;

  @Field(type = FieldType.Date)
  // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private ZonedDateTime endDate;

  @Field(type = FieldType.Nested)
  @JsonProperty("showtime")
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
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Showtime {
    @Field(type = FieldType.Keyword)
    private String screenName;

    @Field(type = FieldType.Keyword)
    private String theaterName;

    @Field(type = FieldType.Date)
    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private ZonedDateTime dateTime;

    @Field(type = FieldType.Float)
    private BigDecimal price;
  }
}
