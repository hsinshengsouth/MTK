package idv.po.mtk_src.booking.web;

import idv.po.mtk_src.booking.app.BookingService;
import idv.po.mtk_src.booking.vo.BookingRequest;
import idv.po.mtk_src.booking.vo.BookingResponse;
import idv.po.mtk_src.booking.vo.PaymentInfo;
import idv.po.mtk_src.booking.vo.UuidDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/booking/command")
public class BookingController {


    private final BookingService bookingService;



    @PostMapping("/book")
    public ResponseEntity<BookingResponse> bookingTicket(@RequestBody BookingRequest request){
        return ResponseEntity.ok(bookingService.bookingTicket(request));
    }



    @PostMapping("/pay")
    public ResponseEntity<?> payment(@RequestBody UuidDTO detailUuids, @RequestBody PaymentInfo info){
        return ResponseEntity.ok(bookingService.confirmPayment(detailUuids.getUuids(),info));
    }


}
