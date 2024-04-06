package AsimMertTopal.whiteGoodsStore.dto.login;

public record SigninRequest (
    String email,
    Long userId,
    String password) {
}
