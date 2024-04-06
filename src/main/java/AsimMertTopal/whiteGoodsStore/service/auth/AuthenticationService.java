//package AsimMertTopal.whiteGoodsStore.service.auth;
//
//import AsimMertTopal.whiteGoodsStore.dto.login.LoginDto;
//import AsimMertTopal.whiteGoodsStore.dto.login.SignupDto;
//import AsimMertTopal.whiteGoodsStore.dto.user.UserDto;
//import AsimMertTopal.whiteGoodsStore.entities.User;
//import AsimMertTopal.whiteGoodsStore.enums.Role;
//import AsimMertTopal.whiteGoodsStore.repository.CartRepository;
//import AsimMertTopal.whiteGoodsStore.repository.OrderRepository;
//import AsimMertTopal.whiteGoodsStore.repository.UserRepository;
//import AsimMertTopal.whiteGoodsStore.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Collections;
//
//@Service
//@RequiredArgsConstructor
//public class AuthenticationService {
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtService jwtService;
//    private final OrderRepository   orderRepository;
//    private  final CartRepository cartRepository;
//
//    private final UserService userService;
//    private final AuthenticationManager authenticationManager;
//
//
//    public LoginDto signup(SignupDto request) {
//        try {
//            User user = new User();
//            user.setUsername(request.username());
//            user.setEmail(request.email());
//            user.setPassword(passwordEncoder.encode(request.password()));
//
//            user.setRoles(Role.ROLE_USER.name());
//
//            userRepository.save(user);
//
//            user.createOrder();
//            user.getOrder().setUser(user);
//            orderRepository.save(user.getOrder());
//
//            user.createUserCart();
//            user.getCart().setUser(user);
//            cartRepository.save(user.getCart());
//
//            user.setCart(user.getCart());
//            user.setOrder(user.getOrder());
//            userRepository.save(user);
//
//            var jwt = jwtService.generateToken(user);
//            return new LoginDto(jwt, user.getUsername());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//
//
//    public LoginDto signin(SigninRequest request) {
//        var user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(user.getEmail(), request.getPassword()));
//        var jwt = jwtService.generateToken(user);
//        return LoginDto.builder().fullName(user.getFullName()).token(jwt).build();
//    }
//}
