package idv.po.mtk_src.booking.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatRequest {
  UUID seatId;
  String rowLabel;
  Integer seatNo;
}
