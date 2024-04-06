package AsimMertTopal.whiteGoodsStore.dto.orderItems;

import AsimMertTopal.whiteGoodsStore.dto.product.ProductCartDto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderItemsDto (
        Long id,
        Long userId,
        Long productId,
        int quantity,
        double totalAmount,
        String address,
        String phoneNumber,
        String name,
        String surname,
        String city,
        Integer cardNumber,
        Integer cvv,
        List<ProductCartDto> productsInCart,


        LocalDateTime orderDate

        ) {

}
