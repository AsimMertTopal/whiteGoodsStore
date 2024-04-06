package AsimMertTopal.whiteGoodsStore.service;

import AsimMertTopal.whiteGoodsStore.dto.product.ProductSaveDto;
import AsimMertTopal.whiteGoodsStore.entities.Category;
import AsimMertTopal.whiteGoodsStore.entities.Product;
import AsimMertTopal.whiteGoodsStore.entities.Seller;
import AsimMertTopal.whiteGoodsStore.entities.User;
import AsimMertTopal.whiteGoodsStore.repository.CategoryRepository;
import AsimMertTopal.whiteGoodsStore.repository.ProductRepository;
import AsimMertTopal.whiteGoodsStore.repository.SellerRepository;
import AsimMertTopal.whiteGoodsStore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final SellerRepository sellerRepository;
    private  final UserRepository userRepository;

    public boolean isUserSeller(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Geçersiz kullanıcı ID: " + userId));

        List<String> roles = Collections.singletonList(user.getRoles());

        return roles != null && roles.stream().anyMatch(role -> role.equals("ROLE_SELLER"));
    }


    @Transactional
    public ResponseEntity<String> save(ProductSaveDto dto, Long userId) {
        try {
            if (!isUserSeller(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Bu işlemi gerçekleştirmek için satıcı rolüne sahip olmalısınız.");
            }

            Product product = new Product();
            product.setName(dto.name());
            product.setImageUrl(dto.imageUrl());
            product.setDescription(dto.description());
            product.setPrice(dto.price());
            product.setStock(dto.stock());

            if (dto.categoryId() != null) {
                Category category = categoryRepository.findById(dto.categoryId())
                        .orElseThrow(() -> new IllegalArgumentException("Geçersiz kategori ID: " + dto.categoryId()));
                product.setCategory(category);
            }

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Geçersiz kullanıcı ID: " + userId));
            product.setUser(user);

            Product savedProduct = productRepository.save(product);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Ürün başarıyla eklendi. Ürün ID: " + savedProduct.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ürün eklenirken bir hata oluştu: " + e.getMessage());
        }
    }




    @Transactional
    public ResponseEntity<ProductSaveDto> update(Long id, ProductSaveDto dto) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                product.setName(dto.name());
                product.setDescription(dto.description());
                product.setPrice(dto.price());
                product.setStock(dto.stock());

                if (dto.categoryId() != null) {
                    Category category = categoryRepository.findById(dto.categoryId())
                            .orElseThrow(() -> new IllegalArgumentException("Geçersiz kategori ID: " + dto.categoryId()));
                    product.setCategory(category);
                }

                Product updatedProduct = productRepository.save(product);

                return ResponseEntity.ok(new ProductSaveDto(
                        updatedProduct.getId(),
                        updatedProduct.getName(),
                        updatedProduct.getDescription(),
                        updatedProduct.getPrice(),
                        updatedProduct.getStock(),
                        updatedProduct.getCategory().getId(),
                        updatedProduct.getUser().getId(),
                        updatedProduct.getImageUrl()

                ));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ProductSaveDto(null, "Ürün bulunamadı", null, 0, 0, null, null, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ProductSaveDto(null, "Güncelleme başarısız", null, 0, 0, null, null, null));
        }
    }


    @Transactional
    public ResponseEntity<String> delete(Long id) {
        try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
                productRepository.delete(product.get());
                return ResponseEntity.ok("Ürün başarıyla silindi. Ürün ID: " + id);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ürün silinirken bir hata oluştu. Hata: " + e.getMessage());
        }
    }

    @Transactional
    public List<ProductSaveDto> getAll () {
        try {
            List<Product> products=productRepository.findAll();
            return products.stream()
                    .map(product -> new ProductSaveDto(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getStock(), product.getCategory().getId(), product.getUser().getId(), product.getImageUrl()))
                    .collect(Collectors.toList());
        }catch (Exception e) {
            throw new RuntimeException("Urunler getirilirken bir hata oldu.",e);
        }

    }
    @Transactional
    public ResponseEntity<ProductSaveDto> findById(Long id) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                ProductSaveDto productSaveDto = new ProductSaveDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getStock(),
                        product.getUser() != null ? product.getUser().getId() : null,
                        product.getCategory() != null ? product.getCategory().getId() : null,
                        product.getImageUrl()
                );
                return ResponseEntity.ok(productSaveDto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ProductSaveDto(null, "Ürün bulunamadı", null, 0, 0, null, null, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ProductSaveDto(null, "Ürün bulunamadı", null, 0, 0, null, null, null));
        }
    }


    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }



    @Transactional
    public void updateSellerProductStock(Long sellerId, Long productId, int quantityChange) {
        try {
            Seller seller = sellerRepository.findById(sellerId)
                    .orElseThrow(() -> new RuntimeException("Satıcı bulunamadı"));

            List<Product> sellerProducts = productRepository.findBySeller(seller);

            Product productToUpdate = sellerProducts.stream()
                    .filter(product -> product.getId().equals(productId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Satıcıya ait ürün bulunamadı"));

            int updatedStock = (int) (productToUpdate.getStock() + quantityChange);
            if (updatedStock < 0) {
                throw new RuntimeException("Yetersiz stok");
            }

            productToUpdate.setStock(updatedStock);
            productRepository.save(productToUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Stok güncellenirken bir hata oluştu");
        }
    }

    public List<Product> getSellerProducts(Long sellerId) {
        try {
            Seller seller = sellerRepository.findById(sellerId)
                    .orElseThrow(() -> new RuntimeException("Satıcı bulunamadı"));

            List<Product> sellerProducts = productRepository.findBySeller(seller);

            return sellerProducts;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Satıcının ürünleri getirilirken bir hata oluştu");
        }
    }

}
