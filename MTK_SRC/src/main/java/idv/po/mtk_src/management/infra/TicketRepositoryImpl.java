package idv.po.mtk_src.management.infra;

import idv.po.mtk_src.management.domain.ticket.Ticket;
import idv.po.mtk_src.management.domain.ticket.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class TicketRepositoryImpl implements TicketRepository {
    @Override
    public void addTicket(Ticket ticket) {

    }
}
