package AsimMertTopal.whiteGoodsStore.dto.cartItems;

import AsimMertTopal.whiteGoodsStore.dto.product.ProductSaveDto;
import AsimMertTopal.whiteGoodsStore.dto.user.UserDto;

public record AddToCartItemsDto(Long userId, Long productId, int quantity) {


}
