package idv.po.mtk_src.booking.infra;

import idv.po.mtk_src.booking.ticketdetail.TicketDetail;
import idv.po.mtk_src.booking.ticketdetail.TicketDetailRepository;
import idv.po.mtk_src.infrastructure.jpa.TicketDetailJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
@RequiredArgsConstructor
public class TicketRepositoryImpl implements TicketDetailRepository {

    private final TicketDetailJpaRepository ticketRepository;

    @Override
    public List<TicketDetail> persistTicketDetail(List<TicketDetail> ticketDetails) {
        return ticketRepository.saveAll(ticketDetails);
    }

    @Override
    public boolean existsByShowtimeIdAndRowLabelAndSeatNoAndBookingStatus(
            UUID showtimeId,
            String rowLabel,
            Integer seatNo,
            String booked
    ) {
        return ticketRepository.existsByShowtimeIdAndRowLabelAndSeatNoAndBookingStatus(
                 showtimeId,
                 rowLabel,
                 seatNo,
                 booked
        );
    }

    @Override
    public List<TicketDetail> findAllDetail(List<UUID> ticketDetailIds) {
        return ticketRepository.findAllById(ticketDetailIds);
    }


}
