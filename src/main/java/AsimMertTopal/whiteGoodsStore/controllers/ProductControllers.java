package AsimMertTopal.whiteGoodsStore.controllers;

import AsimMertTopal.whiteGoodsStore.dto.product.ProductSaveDto;
import AsimMertTopal.whiteGoodsStore.entities.Product;
import AsimMertTopal.whiteGoodsStore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin
public class ProductControllers {
    private final ProductService productService;

    @PostMapping("/register/{userId}")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public ResponseEntity<String> save(@RequestBody ProductSaveDto dto, @PathVariable Long userId) {
        return productService.save(dto, userId);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return productService.delete(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public ResponseEntity<ProductSaveDto> update(@PathVariable Long id, @RequestBody ProductSaveDto dto){
        return productService.update(id, dto);
    }

    @GetMapping("/all")
    public List<ProductSaveDto> getAll(){
        return productService.getAll();
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAuthority('ROLE_SELLER','ROLE_USER')")
    public ResponseEntity<ProductSaveDto> getById(@PathVariable Long id){
        return productService.findById(id);
    }

    @PutMapping("/updateProductStock/{productId}")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public ResponseEntity<String> updateProductStock(@PathVariable Long productId,
                                                     @RequestParam Long sellerId,
                                                     @RequestParam int quantityChange) {
        try {
            productService.updateSellerProductStock(sellerId, productId, quantityChange);
            return ResponseEntity.ok("Ürün stok miktarı başarıyla güncellendi.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Ürün stok miktarı güncellenirken bir hata oluştu: " + e.getMessage());
        }
    }
    @GetMapping("/products/{sellerId}")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public ResponseEntity<List<Product>> getSellerProducts(@PathVariable Long sellerId) {
        try {
            List<Product> sellerProducts = productService.getSellerProducts(sellerId);
            return ResponseEntity.ok(sellerProducts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }


}
