package AsimMertTopal.whiteGoodsStore.dto.product;

import AsimMertTopal.whiteGoodsStore.dto.category.CategoryDto;
import AsimMertTopal.whiteGoodsStore.entities.Product;

public record ProductSaveDto(Long id,
                             String name,
                             String description,


                             double price,
                             double stock,
                             Long categoryId,

Long userId,
                             String imageUrl
) {


}
