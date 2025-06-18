package idv.po.mtk_src.management.app;

import idv.po.mtk_src.infrastructure.exception.ResourceNotFoundException;
import idv.po.mtk_src.management.domain.ticket.AddTicketCommand;
import idv.po.mtk_src.management.domain.ticket.Ticket;
import idv.po.mtk_src.management.domain.ticket.TicketRepository;
import idv.po.mtk_src.movie.domain.command.ScreenRepository;
import idv.po.mtk_src.movie.domain.command.ShowtimeRepository;
import idv.po.mtk_src.movie.domain.query.MovieReadRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final MovieReadRepository movieReadRepository;
    private final ScreenRepository screenRepository;
    private final ShowtimeRepository showtimeRepository;

    public void addTicket(AddTicketCommand command) {

        if(movieReadRepository.findById(command.movieId()).isPresent() &&
           screenRepository.findByScreenId(command.screenId()).isPresent()&&
           showtimeRepository.findByShowTimeId(command.showTimeId()).isPresent()
        ) {
            Ticket ticket = new Ticket();
            BeanUtils.copyProperties(command, ticket);
            ticketRepository.addTicket(ticket);
        }else{
            throw new ResourceNotFoundException("movies、showtime and screens are not found");
        }

    }

}
