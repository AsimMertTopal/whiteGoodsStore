package AsimMertTopal.whiteGoodsStore.service;

import AsimMertTopal.whiteGoodsStore.dto.cartItems.CartSummaryDto;
import AsimMertTopal.whiteGoodsStore.dto.product.ProductCartDto;
import AsimMertTopal.whiteGoodsStore.dto.product.ProductSaveDto;
import AsimMertTopal.whiteGoodsStore.entities.CartItems;
import AsimMertTopal.whiteGoodsStore.entities.Product;
import AsimMertTopal.whiteGoodsStore.entities.User;
import AsimMertTopal.whiteGoodsStore.repository.CartItemRepository;
import AsimMertTopal.whiteGoodsStore.repository.CartRepository;
import AsimMertTopal.whiteGoodsStore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final UserRepository userRepository;

    private final CartRepository cartRepository;

    @Transactional
    public ResponseEntity<ProductSaveDto> addToCart(Long productId, Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Kullanıcı Bulunamadı"));

            ResponseEntity<ProductSaveDto> productResponse = productService.findById(productId);
            ProductSaveDto productData = productResponse.getBody();

            Product product = new Product();
            product.setId(productData.id());
            product.setName(productData.name());
            product.setDescription(productData.description());
            product.setPrice(productData.price());
            product.setStock(productData.stock());

            CartItems existingCartItem = cartItemRepository.findByCartUserAndProduct(user, product);

            if (existingCartItem == null) {
                CartItems cartItem = new CartItems();
                cartItem.setProduct(product);
                cartItem.setQuantity(1);
                cartItem.setCart(user.getCart());
                cartItemRepository.save(cartItem);

                user.getCart().getCartItems().add(cartItem);
                cartRepository.save(user.getCart());

                return ResponseEntity.ok(productData);
            } else {
                int newQuantity = Math.min(existingCartItem.getQuantity() + 1, 4);
                existingCartItem.setQuantity(newQuantity);
                cartItemRepository.save(existingCartItem);

                return ResponseEntity.ok(productData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();

        }
    }





    @Transactional
    public ResponseEntity<String> increaseCartItemQuantity(Long cartItemId) {
        try {
            CartItems cartItem = cartItemRepository.findById(cartItemId)
                    .orElseThrow(() -> new RuntimeException("Sepet bulunamadı"));

            int newQuantity = Math.min(cartItem.getQuantity() + 1, 4);
            cartItem.setQuantity(newQuantity);

            return ResponseEntity.ok("Sepetteki ürün miktarı başarıyla artırıldı!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Sepet öğesi miktarı artırılırken hata oluştu");
        }
    }

    @Transactional
    public ResponseEntity<String> decreaseCartItemQuantity(Long cartItemId) {
        try {
            CartItems cartItem = cartItemRepository.findById(cartItemId)
                    .orElseThrow(() -> new RuntimeException("Sepet bulunamadı"));

            int newQuantity = Math.max(cartItem.getQuantity() - 1, 1);
            cartItem.setQuantity(newQuantity);

            return ResponseEntity.ok("Sepetteki öğe miktarı başarıyla azaldı!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Sepette ürün miktarı azaltılırken hata oluştu");
        }
    }

    @Transactional(readOnly = true)
    public CartSummaryDto viewCart(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Kullanıcı Bulunamadı"));

            List<CartItems> cartItems = cartItemRepository.findByCartUser(user);

            if (cartItems.isEmpty()) {
                return new CartSummaryDto(Collections.emptyList(), 0.0);
            }

            List<ProductCartDto> productsInCart = cartItems.stream()
                    .map(cartItem -> {
                        Product product = cartItem.getProduct();
                        return new ProductCartDto(
                                product.getId(),
                                product.getName(),
                                product.getDescription(),
                                product.getPrice(),
                                product.getStock(),
                                (int) cartItem.getQuantity()
                        );
                    })
                    .collect(Collectors.toList());

            double totalCartPrice = productsInCart.stream()
                    .mapToDouble(product -> product.price() * product.quantity())
                    .sum();

            return new CartSummaryDto(productsInCart, totalCartPrice);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Sepet görüntülenirken hata oluştu");
        }
    }


    @Transactional
    public void clearCart(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Kullanıcı Bulunamadı"));

            List<CartItems> cartItems = cartItemRepository.findByCartUser(user);

            for (CartItems cartItem : cartItems) {
                cartItemRepository.delete(cartItem);
            }



        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Sepet silinirken hata oluştu");
        }
    }

}




