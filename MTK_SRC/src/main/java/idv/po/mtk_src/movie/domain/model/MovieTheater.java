package idv.po.mtk_src.movie.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "movie_theaters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieTheater {

    @Id
    @Column(name = "theater_id", length = 20)
    private String theaterId;

    @Column(name = "theater_name", nullable = false, length = 255)
    private String theaterName;

    @Column(name = "theater_email")
    private String theaterEmail;

    @Column(name = "address")
    private String address;


}
