package AsimMertTopal.whiteGoodsStore.controllers;

import AsimMertTopal.whiteGoodsStore.dto.cartItems.CartSummaryDto;
import AsimMertTopal.whiteGoodsStore.dto.product.ProductSaveDto;
import AsimMertTopal.whiteGoodsStore.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartItems")
@RequiredArgsConstructor
public class CartItemControllers {

    private final CartItemService cartItemService;


    @PostMapping("/addToCart/{productId}/{userId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ProductSaveDto> addToCart(@PathVariable Long productId, @PathVariable Long userId) {
        return cartItemService.addToCart(productId,userId);
    }


    @PutMapping("/increase-quantity/{cartItemId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> increaseCartItemQuantity(@PathVariable Long cartItemId) {
        return cartItemService.increaseCartItemQuantity(cartItemId);
    }

    @PutMapping("/decrease-quantity/{cartItemId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> decreaseCartItemQuantity(@PathVariable Long cartItemId) {
        return cartItemService.decreaseCartItemQuantity(cartItemId);
    }
    @GetMapping("/view")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<CartSummaryDto> viewCart(@RequestParam Long userId) {
        CartSummaryDto cartSummary = cartItemService.viewCart(userId);
        return ResponseEntity.ok(cartSummary);
    }

    @DeleteMapping("/clear/{userId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> clearCart(@PathVariable Long userId) {
        try {
            cartItemService.clearCart(userId);
            return ResponseEntity.ok("Sepet başarıyla temizlendi!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Sepet temizlenirken bir hata oluştu: " + e.getMessage());
        }
    }
}
