package AsimMertTopal.whiteGoodsStore.dto.orderItems;

public record OrderItemsUpdateDto( Long orderItemsId,
        String address,
                                   String phoneNumber,
                                   String name,
                                   String surname,
                                   String city) {
}
