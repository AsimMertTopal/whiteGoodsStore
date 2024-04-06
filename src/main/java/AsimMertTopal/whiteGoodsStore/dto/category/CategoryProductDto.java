package AsimMertTopal.whiteGoodsStore.dto.category;

import AsimMertTopal.whiteGoodsStore.dto.product.ProductDto;
import AsimMertTopal.whiteGoodsStore.entities.Product;


import java.util.List;


public record CategoryProductDto(Long categoryId, String categoryName, List<ProductDto> products) {
}

