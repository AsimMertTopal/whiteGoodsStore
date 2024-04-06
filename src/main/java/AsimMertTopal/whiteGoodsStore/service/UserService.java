package AsimMertTopal.whiteGoodsStore.service;

import AsimMertTopal.whiteGoodsStore.dto.login.LoginDto;
import AsimMertTopal.whiteGoodsStore.dto.login.SigninRequest;
import AsimMertTopal.whiteGoodsStore.dto.user.UserDto;
import AsimMertTopal.whiteGoodsStore.dto.user.UserSellerDto;
import AsimMertTopal.whiteGoodsStore.entities.Seller;
import AsimMertTopal.whiteGoodsStore.entities.User;
import AsimMertTopal.whiteGoodsStore.enums.Role;
import AsimMertTopal.whiteGoodsStore.repository.CartRepository;
import AsimMertTopal.whiteGoodsStore.repository.OrderRepository;
import AsimMertTopal.whiteGoodsStore.repository.SellerRepository;
import AsimMertTopal.whiteGoodsStore.repository.UserRepository;
import AsimMertTopal.whiteGoodsStore.service.auth.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final OrderRepository orderRepository;

    private final SellerRepository  sellerRepository;

    @Transactional
    public LoginDto save(UserDto dto) {
        try {
            User user = new User();
            user.setUsername(dto.username());
            user.setEmail(dto.email());
            user.setPassword(passwordEncoder.encode(dto.password()));

            user.setRoles(Role.ROLE_USER.name());

            userRepository.save(user);

            user.createOrder();
            user.getOrder().setUser(user);
            orderRepository.save(user.getOrder());

            user.createUserCart();
            user.getCart().setUser(user);
            cartRepository.save(user.getCart());

            user.setCart(user.getCart());
            user.setOrder(user.getOrder());
            userRepository.save(user);

            var jwt = jwtService.generateToken(user);
            return new LoginDto(user.getId(),jwt, user.getUsername());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }






    public LoginDto createUserAndSeller(UserSellerDto userDto) {
        try {
            User newUser = new User();
            newUser.setUsername(userDto.username());
            newUser.setEmail(userDto.email());
            newUser.setPassword(passwordEncoder.encode(userDto.password()));
            newUser.setRoles(Role.ROLE_SELLER.name());

            userRepository.save(newUser);

            newUser.createOrder();
            newUser.getOrder().setUser(newUser);
            orderRepository.save(newUser.getOrder());

            newUser.createUserCart();
            newUser.getCart().setUser(newUser);
            cartRepository.save(newUser.getCart());

            Seller newSeller = new Seller();
            newSeller.setUser(newUser);
            newSeller.setCompanyName(userDto.companyName());
            newSeller.setTaxNumber(userDto.taxNumber());

            newSeller.setCompanyName(userDto.username());

            sellerRepository.save(newSeller);

            newUser.setSeller(newSeller);
            userRepository.save(newUser);

            var jwt = jwtService.generateToken(newUser);
            return new LoginDto(userDto.id(), jwt, newUser.getUsername());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




    public LoginDto signin(SigninRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Geçersiz e-posta veya şifre."));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("Geçersiz e-posta veya şifre.");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), request.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateToken(user);

        return LoginDto.builder().name(user.getUsername()) .token(jwt).userId(user.getId()) .build();
    }


}
