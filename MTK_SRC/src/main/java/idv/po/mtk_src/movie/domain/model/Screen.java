package idv.po.mtk_src.movie.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "screens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Screen {
    @Id
    @Column(name = "screen_id", length = 20)
    private String screenId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id", nullable = false)
    private MovieTheater theater;

    @Column(name = "screen_name", nullable = false, length = 100)
    private String screenName;
}
