package AsimMertTopal.whiteGoodsStore.dto.cartItems;

import AsimMertTopal.whiteGoodsStore.entities.CartItems;

import java.util.List;

public record CartDto(Long id, List<CartItems> cartItems, Long userId){
}
