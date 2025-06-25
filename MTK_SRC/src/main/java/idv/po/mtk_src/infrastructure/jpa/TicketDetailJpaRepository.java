package idv.po.mtk_src.infrastructure.jpa;


import idv.po.mtk_src.booking.ticketdetail.TicketDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketDetailJpaRepository  extends JpaRepository<TicketDetail, UUID> {

    boolean existsByShowtimeIdAndRowLabelAndSeatNoAndBookingStatus(
            UUID showtimeId,
            String rowLabel,
            Integer seatNo,
            String booked
    );



}
