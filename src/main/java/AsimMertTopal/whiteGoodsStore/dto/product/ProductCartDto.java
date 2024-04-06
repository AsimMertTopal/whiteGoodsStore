package AsimMertTopal.whiteGoodsStore.dto.product;

public record ProductCartDto(  Long id,
                               String name,
                               String description,
                               double price,
                               double stock,
                               int quantity) {
}
