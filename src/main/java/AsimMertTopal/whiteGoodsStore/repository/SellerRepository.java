package AsimMertTopal.whiteGoodsStore.repository;

import AsimMertTopal.whiteGoodsStore.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository  extends JpaRepository<Seller, Long> {
    Optional<Seller> findByUserId(Long userId);
}
