package idv.po.mtk_src.movie.domain.model;

import idv.po.mtk_src.movie.domain.event.ShowtimeCreatedEvent;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "showtimes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowTime {

    @Id
    @GeneratedValue
    @Column(name = "showtime_id", nullable = false, updatable = false)
    private UUID showtimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id", nullable = false)
    private Screen screen;

    @Column(name = "date_time", nullable = false)
    private ZonedDateTime dateTime;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;


    public ShowtimeCreatedEvent createShowtimeCreatedEvent() {
        return new ShowtimeCreatedEvent(
                this.showtimeId,
                this.movie,
                this.theater,
                this.screen,
                this.dateTime,
                this.price
        );
    }

}
