package idv.po.mtk_src.movie.domain.model;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "screens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Screen {

  public Screen(String ignoredScreenName) {}

  @Id
  @GeneratedValue
  @Column(name = "screen_id", nullable = false, updatable = false)
  private UUID screenId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "theater_id", nullable = false)
  private Theater theater;

  @Column(name = "screen_name", nullable = false, length = 100)
  private String screenName;
}
