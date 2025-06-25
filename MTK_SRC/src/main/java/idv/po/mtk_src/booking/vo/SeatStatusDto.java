package idv.po.mtk_src.booking.vo;

public record SeatStatusDto(
        String rowLabel,
        int seatNo,
        boolean available
) {
}
