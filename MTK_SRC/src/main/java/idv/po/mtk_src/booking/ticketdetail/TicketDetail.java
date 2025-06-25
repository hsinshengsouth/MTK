package idv.po.mtk_src.booking.ticketdetail;

import lombok.*;
import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket_detail")
public class TicketDetail {


    @Id
    @GeneratedValue
    @Column(name = "ticket_detail_id", columnDefinition = "UUID")
    private UUID ticketDetailId;

    @Column(name = "ticket_id", nullable = false, columnDefinition = "UUID")
    private UUID ticketId;

    @Column(name = "member_id", nullable = false, columnDefinition = "UUID")
    private UUID memberId;

    @Column(name = "showtime_id", nullable = false, columnDefinition = "UUID")
    private UUID showtimeId;

    @Column(name = "row_label", nullable = false, length = 5)
    private String rowLabel;

    @Column(name = "seat_no", nullable = false)
    private Integer seatNo;

    @Column(name = "booking_status", length = 30)
    private String bookingStatus;

    @Column(name = "create_time")
    private ZonedDateTime createTime;





}
