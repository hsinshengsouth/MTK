package idv.po.mtk_src.management.infra;

import idv.po.mtk_src.infrastructure.jpa.TicketJpaRepository;
import idv.po.mtk_src.management.domain.ticket.Ticket;
import idv.po.mtk_src.management.domain.ticket.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
@RequiredArgsConstructor
public class TicketRepositoryImpl implements TicketRepository {

    private final TicketJpaRepository ticketJpaRepository;

    @Override
    public void addTicket(Ticket ticket) {
        ticketJpaRepository.save(ticket);
    }

    @Override
    public Optional<Ticket> getTicket(UUID ticketId) {
        return ticketJpaRepository.findById(ticketId);
    }
}
