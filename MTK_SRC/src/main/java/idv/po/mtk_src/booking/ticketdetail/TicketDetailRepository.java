package idv.po.mtk_src.booking.ticketdetail;

import java.util.List;
import java.util.UUID;

public interface TicketDetailRepository {
  List<TicketDetail> persistTicketDetail(List<TicketDetail> ticketDetails);

  List<TicketDetail> findAllDetail(List<UUID> ticketDetailIds);

  boolean existsByShowtimeIdAndRowLabelAndSeatNoAndBookingStatus(
      UUID showtimeId, String rowLabel, Integer seatNo, String booked);
}
