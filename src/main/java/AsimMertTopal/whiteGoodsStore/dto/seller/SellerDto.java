package AsimMertTopal.whiteGoodsStore.dto.seller;

public class SellerDto {
    private Long taxNumber;

    public SellerDto(Long taxNumber) {
        this.taxNumber = taxNumber;
    }

    public Long getTaxNumber() {
        return taxNumber;
    }

}
