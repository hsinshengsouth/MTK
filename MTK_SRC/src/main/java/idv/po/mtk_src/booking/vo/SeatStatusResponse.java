package idv.po.mtk_src.booking.vo;

import java.util.List;
import java.util.UUID;

public record SeatStatusResponse(
    UUID showTimeId, Long seatCount, List<SeatStatusDto> seatStatusDtoList) {}
