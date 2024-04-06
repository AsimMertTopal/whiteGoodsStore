package AsimMertTopal.whiteGoodsStore.repository;

import AsimMertTopal.whiteGoodsStore.entities.Category;
import AsimMertTopal.whiteGoodsStore.entities.Product;
import AsimMertTopal.whiteGoodsStore.entities.Seller;
import AsimMertTopal.whiteGoodsStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);

    List<Product> findByUser(User seller);

    List<Product> findBySeller(Seller seller);
}
