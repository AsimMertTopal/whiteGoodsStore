package AsimMertTopal.whiteGoodsStore.service;

import AsimMertTopal.whiteGoodsStore.dto.category.CategoryAllDto;
import AsimMertTopal.whiteGoodsStore.dto.category.CategoryProductDto;
import AsimMertTopal.whiteGoodsStore.dto.category.CategorySaveDto;
import AsimMertTopal.whiteGoodsStore.dto.category.CategoryUpdateDto;
import AsimMertTopal.whiteGoodsStore.dto.product.ProductDto;
import AsimMertTopal.whiteGoodsStore.entities.Category;
import AsimMertTopal.whiteGoodsStore.entities.Product;
import AsimMertTopal.whiteGoodsStore.repository.CategoryRepository;
import AsimMertTopal.whiteGoodsStore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

   private final CategoryRepository categoryRepository;
   private final ProductRepository productRepository;

   @Transactional
   public ResponseEntity<String> save(CategorySaveDto dto) {
      try {
         Category category = new Category();
         category.setName(dto.name());

         if (dto.id() != null) {
            category.setId(dto.id());
         }

         Category savedCategory = categoryRepository.save(category);

         return ResponseEntity.status(HttpStatus.CREATED)
                 .body("Ürün başarıyla eklendi. Ürün ID: " + savedCategory.getId());
      } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                 .body("Kategori eklenirken bir hata oluştu: " + e.getMessage());
      }
   }

   @Transactional
   public List<CategoryAllDto> getAll() {
      try {
         List<Category> categories = categoryRepository.findAll();

         return categories.stream()
                 .map(category -> new CategoryAllDto(category.getId(), category.getName()))
                 .collect(Collectors.toList());
      } catch (Exception e) {
         throw new RuntimeException("Kategoriler alınırken bir hata oluştu.", e);
      }
   }

   @Transactional
   public String delete(Long id) {
      try {
         Optional<Category> category = categoryRepository.findById(id);
         if (category.isPresent()) {
            categoryRepository.delete(category.get());
            return "Kategori başarıyla silindi.";
         }
         return "Böyle bir kategori mevcut değil.";
      } catch (Exception e) {
         throw new RuntimeException("Kategori silinirken bir hata oluştu.", e);
      }
   }

   @Transactional
   public CategoryUpdateDto update(Long id, CategoryUpdateDto updateDto) {
      try {
         Optional<Category> optionalCategory = categoryRepository.findById(id);

         if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            existingCategory.setName(updateDto.name());

            Category updatedCategory = categoryRepository.save(existingCategory);
            return new CategoryUpdateDto(updatedCategory.getId(), updatedCategory.getName());
         } else {
            throw new IllegalArgumentException("Böyle bir kategori mevcut değil: " + id);
         }
      } catch (Exception e) {
         throw new RuntimeException("Kategori güncellenirken bir hata oluştu.", e);
      }
   }

   @Transactional
   public CategoryProductDto getProductsByCategory(Long categoryId) {
      try {
         Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

         if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();

            List<ProductDto> productDtos = productRepository.findByCategory(category)
                    .stream()
                    .map(this::mapToProductDto)
                    .collect(Collectors.toList());

            return new CategoryProductDto(category.getId(), category.getName(), productDtos);
         } else {
            throw new IllegalArgumentException("Böyle bir kategori mevcut değil: " + categoryId);
         }
      } catch (Exception e) {
         throw new RuntimeException("Kategori ürünleri getirilirken bir hata oluştu.", e);
      }
   }

   private ProductDto mapToProductDto(Product product) {
      return new ProductDto(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getStock());
   }
}
