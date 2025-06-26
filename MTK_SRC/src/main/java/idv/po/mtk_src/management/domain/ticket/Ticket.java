package idv.po.mtk_src.management.domain.ticket;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {

  @Id
  @GeneratedValue
  @Column(name = "ticket_id", columnDefinition = "UUID")
  private UUID ticketId;

  @Column(name = "theater_id", nullable = false, columnDefinition = "UUID")
  private UUID theaterId;

  @Column(name = "screen_id", nullable = false, columnDefinition = "UUID")
  private UUID screenId;

  @Column(name = "movie_id", nullable = false, columnDefinition = "UUID")
  private UUID movieId;

  @Column(name = "ticket_quantity", nullable = false)
  private Integer ticketQuantity;

  @Column(name = "price", nullable = false, precision = 10, scale = 2)
  private BigDecimal price;

  @Column(name = "showtime_id", nullable = false, columnDefinition = "UUID")
  private UUID showtimeId;

  @Column(name = "create_time")
  private ZonedDateTime createTime;
}
