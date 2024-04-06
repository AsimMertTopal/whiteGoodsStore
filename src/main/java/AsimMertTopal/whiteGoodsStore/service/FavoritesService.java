package AsimMertTopal.whiteGoodsStore.service;

import AsimMertTopal.whiteGoodsStore.dto.favorites.FavoritesProductDto;
import AsimMertTopal.whiteGoodsStore.entities.Favorites;
import AsimMertTopal.whiteGoodsStore.entities.Product;
import AsimMertTopal.whiteGoodsStore.entities.User;
import AsimMertTopal.whiteGoodsStore.repository.CartRepository;
import AsimMertTopal.whiteGoodsStore.repository.FavoritesRepository;
import AsimMertTopal.whiteGoodsStore.repository.ProductRepository;
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
public class FavoritesService {
    private final FavoritesRepository favoritesRepository;
    private final UserRepository    userRepository;
    private final ProductRepository  productRepository;

    private final ProductService productService;

    private final CartRepository cartRepository;

    @Transactional
    public ResponseEntity<String> add(Long productId, Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Kullanıcı bulunamadı"));
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Ürün bulunamadı"));

            Favorites existingFavorites = favoritesRepository.findByUserAndProduct(user, product);

            if (existingFavorites == null) {
                Favorites favorites = new Favorites();
                favorites.setProduct(product);
                favorites.setUser(user);
                favoritesRepository.save(favorites);

                return ResponseEntity.ok("Ürün favorilere eklendi.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ürün zaten favorilerde.");
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



    @Transactional
    public String remove(Long productId, Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Kullanıcı bulunamadı"));
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Ürün bulunamadı"));

            Favorites existingFavorites = favoritesRepository.findByUserAndProduct(user, product);

            if (existingFavorites != null) {
                favoritesRepository.delete(existingFavorites);
                return "Ürün favorilerden çıkartıldı.";
            } else {
                return "Ürün zaten favorilerde değil.";
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Transactional(readOnly = true)
    public List<FavoritesProductDto> getAllFavoritesWithProductForUser(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Kullanıcı bulunamadı"));

            List<Favorites> favorites = favoritesRepository.findAllByUser(user);

            if (favorites.isEmpty()) {
                return Collections.emptyList();
            }

            return favorites.stream()
                    .map(favorite -> {
                        Product product = favorite.getProduct();
                        return new FavoritesProductDto(
                                product.getId(),
                                product.getName(),
                                product.getDescription(),
                                product.getPrice()

                        );
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Favori ürünler getirilirken bir hata oluştu.");
        }
    }





}
