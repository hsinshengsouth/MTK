package idv.po.mtk_src.management.web;

import idv.po.mtk_src.management.app.TicketService;
import idv.po.mtk_src.management.domain.ticket.AddTicketCommand;
import idv.po.mtk_src.movie.domain.command.AddMovieCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets/command")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/addTicket")
    public ResponseEntity<?> addTicket(
            @RequestBody AddTicketCommand ticketCommand
    ){
        ticketService.addTicket(ticketCommand);
        return ResponseEntity.status(HttpStatus.OK).body("Add a new ticket successfully");
    }





}
