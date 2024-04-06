package AsimMertTopal.whiteGoodsStore.repository;

import AsimMertTopal.whiteGoodsStore.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
