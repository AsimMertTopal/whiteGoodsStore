package AsimMertTopal.whiteGoodsStore.dto.user;

import AsimMertTopal.whiteGoodsStore.dto.favorites.FavoritesAddDto;

import java.util.List;

public record UserSellerDto(

        Long id,
        String username,
        String token,
        String email,
        Long taxNumber,

        String companyName,
        String password,
        Long orderId,
        List<FavoritesAddDto> favorites,
        Long cartId
) {
}
