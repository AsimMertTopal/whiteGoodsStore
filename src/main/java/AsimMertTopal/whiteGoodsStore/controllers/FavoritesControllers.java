package AsimMertTopal.whiteGoodsStore.controllers;

import AsimMertTopal.whiteGoodsStore.dto.favorites.FavoritesProductDto;
import AsimMertTopal.whiteGoodsStore.service.FavoritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Configuration
public class FavoritesControllers {
    private final FavoritesService favoritesService;


    @PostMapping("/add/{productId}/{userId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> add(@PathVariable Long productId, @PathVariable Long userId) {
        return favoritesService.add(productId, userId);
    }



    @DeleteMapping("/remove/{productId}/{userId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> remove(@PathVariable Long productId, @PathVariable Long userId) {
        return ResponseEntity.ok(favoritesService.remove(productId, userId));
    }


    @GetMapping("/getAllFavorites/{userId}")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_SELLER')")
    public List<FavoritesProductDto> getAllFavorites(@PathVariable("userId") Long userId) {
        return favoritesService.getAllFavoritesWithProductForUser(userId);
    }


}
