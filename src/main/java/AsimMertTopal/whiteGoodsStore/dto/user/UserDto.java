package AsimMertTopal.whiteGoodsStore.dto.user;

import AsimMertTopal.whiteGoodsStore.dto.cartItems.CartDto;
import AsimMertTopal.whiteGoodsStore.dto.favorites.FavoritesAddDto;
import AsimMertTopal.whiteGoodsStore.dto.order.OrderDto;

import java.util.List;

public record UserDto(
        Long id,
        String username,
        String token,
        String email,
        String password,
        Long orderId,
        List<FavoritesAddDto> favorites,
        Long cartId
) {
}
