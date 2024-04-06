package AsimMertTopal.whiteGoodsStore.dto.cartItems;

import AsimMertTopal.whiteGoodsStore.dto.product.ProductCartDto;

import java.util.List;

public record CartSummaryDto(List<ProductCartDto> productsInCart, double totalPrice) {
}
