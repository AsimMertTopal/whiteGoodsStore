package AsimMertTopal.whiteGoodsStore.dto.order;

import AsimMertTopal.whiteGoodsStore.dto.orderItems.OrderItemsDto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        Long id,
        Long userId,

        Long productId,
        LocalDateTime orderDate,
        List<OrderItemsDto> orderItems) {

}
