package AsimMertTopal.whiteGoodsStore.repository;

import AsimMertTopal.whiteGoodsStore.entities.Order;
import AsimMertTopal.whiteGoodsStore.entities.Product;
import AsimMertTopal.whiteGoodsStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
