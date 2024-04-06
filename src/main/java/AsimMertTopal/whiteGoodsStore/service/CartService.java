package AsimMertTopal.whiteGoodsStore.service;

import AsimMertTopal.whiteGoodsStore.dto.cartItems.CartDto;
import AsimMertTopal.whiteGoodsStore.entities.Cart;
import AsimMertTopal.whiteGoodsStore.entities.User;
import AsimMertTopal.whiteGoodsStore.repository.CartRepository;
import AsimMertTopal.whiteGoodsStore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<String> save(CartDto dto) {
        try {
            Cart cart = new Cart();

            User user = userRepository.findById(dto.userId())
                    .orElseThrow(() -> new IllegalArgumentException("Geçersiz kullanıcı ID: " + dto.userId()));

            cart.setUser(user);



            cartRepository.save(cart);


            return ResponseEntity.ok("Sepet başarıyla oluşturuldu ve kaydedildi.");
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Sepet oluşturma işlemi başarısız oldu: " + e.getMessage());
        }
    }
}
