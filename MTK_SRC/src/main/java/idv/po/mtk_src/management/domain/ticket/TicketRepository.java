package idv.po.mtk_src.management.domain.ticket;



import java.util.Optional;
import java.util.UUID;

public interface TicketRepository {

    void addTicket(Ticket ticket);
    Optional<Ticket> getTicket(UUID ticketId);


}
