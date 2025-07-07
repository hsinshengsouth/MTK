package idv.po.mtk_src.infrastructure.jpa;

import idv.po.mtk_src.management.domain.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketJpaRepository extends JpaRepository<Ticket, UUID> {}
