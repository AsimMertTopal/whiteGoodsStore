package AsimMertTopal.whiteGoodsStore.dto.orderItems;

import AsimMertTopal.whiteGoodsStore.dto.product.ProductCartDto;
import AsimMertTopal.whiteGoodsStore.dto.product.ProductDto;

import java.time.LocalDateTime;
import java.util.List;

public record UsersOrder(Long id,
                         Long userId,
                         Long productId,
                         double totalAmount,
                         String address,
                         String phoneNumber,
                         String name,
                         String surname,
                         String city,
                         LocalDateTime orderDate,
                   List<ProductDto> productsInOrderDto
) {
}
