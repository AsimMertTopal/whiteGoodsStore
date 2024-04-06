package AsimMertTopal.whiteGoodsStore.repository;

import AsimMertTopal.whiteGoodsStore.entities.Favorites;
import AsimMertTopal.whiteGoodsStore.entities.Product;
import AsimMertTopal.whiteGoodsStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {

    Favorites findByUserAndProduct(User user, Product product);

    @Query("SELECT f FROM Favorites f JOIN FETCH f.product WHERE f.user.id = :userId")
    List<Favorites> findAllFavoritesWithProductByUserId(@Param("userId") Long userId);


    List<Favorites> findAllByUser(User user);
}
