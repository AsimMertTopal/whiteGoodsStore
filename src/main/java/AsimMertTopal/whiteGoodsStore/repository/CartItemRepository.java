package AsimMertTopal.whiteGoodsStore.repository;

import AsimMertTopal.whiteGoodsStore.dto.product.ProductDto;
import AsimMertTopal.whiteGoodsStore.dto.product.ProductSaveDto;
import AsimMertTopal.whiteGoodsStore.entities.CartItems;
import AsimMertTopal.whiteGoodsStore.entities.Product;
import AsimMertTopal.whiteGoodsStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItems, Long> {
    CartItems findByCartUserAndProduct(User cartUser, Product product);

    List<CartItems> findByCartUser(User user);

}
