package AsimMertTopal.whiteGoodsStore.controllers;

import AsimMertTopal.whiteGoodsStore.dto.category.CategoryAllDto;
import AsimMertTopal.whiteGoodsStore.dto.category.CategoryProductDto;
import AsimMertTopal.whiteGoodsStore.dto.category.CategorySaveDto;
import AsimMertTopal.whiteGoodsStore.dto.category.CategoryUpdateDto;
import AsimMertTopal.whiteGoodsStore.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryControllers {
     final CategoryService categoryService;


     @PostMapping("/register")
     public ResponseEntity<String> save (@RequestBody CategorySaveDto dto){
          return categoryService.save(dto);
     }

     @GetMapping("/all")
     public List<CategoryAllDto> getAll() {
          return categoryService.getAll();
     }

     @DeleteMapping("/delete/{id}")
     public String delete(@PathVariable Long id) {
          return categoryService.delete(id);
     }
     @PutMapping("/update/{id}")
     public CategoryUpdateDto update(@PathVariable Long id, @RequestBody CategoryUpdateDto updateDto) {
          return categoryService.update(id, updateDto);
     }

     @GetMapping("/products/{categoryId}")
     public CategoryProductDto getProductsByCategoryId(@PathVariable Long categoryId) {
          return categoryService.getProductsByCategory(categoryId);
     }

}
