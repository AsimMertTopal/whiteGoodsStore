package AsimMertTopal.whiteGoodsStore.dto.product;

import AsimMertTopal.whiteGoodsStore.dto.category.CategoryDto;

public record ProductDto(
                            Long id,
                            String name,
                            String description,
                            double price,
                            double stock
                            ) {
}
