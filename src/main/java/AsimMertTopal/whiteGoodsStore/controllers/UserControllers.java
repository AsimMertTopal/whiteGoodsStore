package AsimMertTopal.whiteGoodsStore.controllers;

import AsimMertTopal.whiteGoodsStore.dto.login.LoginDto;
import AsimMertTopal.whiteGoodsStore.dto.login.SigninRequest;
import AsimMertTopal.whiteGoodsStore.dto.user.UserDto;
import AsimMertTopal.whiteGoodsStore.dto.user.UserSellerDto;
import AsimMertTopal.whiteGoodsStore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserControllers {
    private final UserService userService;

    @PostMapping("/register")
    public LoginDto register(@RequestBody UserDto dto) {
        return userService.save(dto);
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginDto> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(userService.signin(request));
    }

    @PostMapping("/sellerRegister")
    public LoginDto sellerRegister(@RequestBody UserSellerDto dto) {
        return userService.createUserAndSeller(dto);
    }
}
