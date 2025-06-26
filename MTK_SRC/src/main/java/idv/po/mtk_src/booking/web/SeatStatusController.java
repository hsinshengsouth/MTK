package idv.po.mtk_src.booking.web;

import idv.po.mtk_src.booking.app.BookingService;
import idv.po.mtk_src.booking.vo.SeatStatusResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/seats")
public class SeatStatusController {

  private final BookingService bookingService;

  @GetMapping("/check")
  public ResponseEntity<SeatStatusResponse> checkSeatStatus(
      @RequestParam UUID showtimeId, @RequestParam UUID screenId) {
    return ResponseEntity.ok(bookingService.checkSeatStatus(screenId, showtimeId));
  }
}
