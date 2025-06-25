package idv.po.mtk_src.management.web;

import java.math.BigDecimal;
import java.util.UUID;

public record AddTicketCommand(
    UUID theaterId,
    UUID screenId,
    UUID movieId,
    UUID showTimeId,
    Integer quantity,
    BigDecimal price
) {
}
