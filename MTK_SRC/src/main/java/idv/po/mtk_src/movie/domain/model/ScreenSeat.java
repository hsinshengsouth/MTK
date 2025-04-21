package idv.po.mtk_src.movie.domain.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "screen_seats", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"screen_id", "row_label", "seat_no"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long seatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id", nullable = false)
    private Screen screen;

    @Column(name = "row_label", nullable = false, length = 5)
    private String rowLabel;

    @Column(name = "seat_no", nullable = false)
    private Integer seatNo;

    @Column(name = "seat_type", length = 20)
    private String seatType = "REGULAR";

    @Column(name = "is_active", length = 20)
    private String isActive = "ACTIVE";

    @Column(name = "booking_status", length = 20)
    private String bookingStatus;




}
