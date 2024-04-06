package AsimMertTopal.whiteGoodsStore.repository;

import AsimMertTopal.whiteGoodsStore.entities.OrderItems;
import AsimMertTopal.whiteGoodsStore.entities.Product;
import AsimMertTopal.whiteGoodsStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemsRepository  extends JpaRepository<OrderItems, Long> {
    List<OrderItems> findByUser(User user);

    List<OrderItems> findByProduct(Product product);


    List<OrderItems> findBySellerId(Long sellerId);

    List<OrderItems> findByProductId(Long id);

    List<OrderItems> findByOrderId(Long orderId);
}
