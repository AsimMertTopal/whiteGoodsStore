package AsimMertTopal.whiteGoodsStore.dto.login;

import lombok.Builder;

@Builder
public record LoginDto(
        Long userId,
        String token,
                       String name) {
}
