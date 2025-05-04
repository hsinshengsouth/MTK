package idv.po.mtk_src.movie.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "movie_theaters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "theater_id", updatable = false, nullable = false)
    private UUID theaterId;


    @Column(name = "theater_name", nullable = false, length = 255)
    private String theaterName;

    @Column(name = "theater_email")
    private String theaterEmail;

    @Column(name = "address")
    private String address;


}
