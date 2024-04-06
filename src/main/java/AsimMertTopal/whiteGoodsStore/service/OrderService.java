package AsimMertTopal.whiteGoodsStore.service;

import AsimMertTopal.whiteGoodsStore.dto.order.OrderDto;
import AsimMertTopal.whiteGoodsStore.dto.orderItems.OrderItemsDto;
import AsimMertTopal.whiteGoodsStore.entities.*;
import AsimMertTopal.whiteGoodsStore.repository.OrderItemsRepository;
import AsimMertTopal.whiteGoodsStore.repository.OrderRepository;
import AsimMertTopal.whiteGoodsStore.repository.ProductRepository;
import AsimMertTopal.whiteGoodsStore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    private final OrderItemsRepository orderItemsRepository;



//
//    private Long findUserIdFromUserEntity(OrderDto dto) {
//        return userRepository.findById(dto.userId())
//                .orElseThrow(() -> new IllegalArgumentException("Kullanıcı bulunamadı"))
//                .getId();
//    }
//    private OrderItems createOrderItems(OrderItemsDto orderItemsDto, User user) {
//        try {
//            Optional<Product> optionalProduct = productRepository.findById(orderItemsDto.productId());
//            if (optionalProduct.isPresent()) {
//                Product product = optionalProduct.get();
//
//                OrderItems orderItems = new OrderItems();
//                orderItems.setProduct(product);
//                orderItems.setUser(user);
//                orderItems.setQuantity(orderItemsDto.quantity());
//                orderItems.setTotalAmount(orderItemsDto.totalAmount());
//                orderItems.setAddress(orderItemsDto.address());
//                orderItems.setPhoneNumber(orderItemsDto.phoneNumber());
//                orderItems.setName(orderItemsDto.name());
//                orderItems.setSurname(orderItemsDto.surname());
//                orderItems.setCity(orderItemsDto.city());
//                orderItems.setCardNumber(orderItemsDto.cardNumber());
//                orderItems.setCvv(orderItemsDto.cvv());
//                orderItems.setOrderDate(orderItemsDto.orderDate());
//
//                return orderItems;
//            } else {
//                throw new RuntimeException("Ürün bulunamadı");
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("Sipariş öğesi oluşturulurken bir hata oluştu.", e);
//        }
//    }
//
//    private OrderDto mapToOrderDto(Order order) {
//        return new OrderDto(
//                order.getId(),
//                order.getUser().getId(),
//                order.getOrderDate(),
//                order.getOrderItems().stream()
//                        .map(this::mapToOrderItemsDto)
//                        .collect(Collectors.toList())
//        );
//    }
//
//    private OrderItemsDto mapToOrderItemsDto(OrderItems orderItems) {
//        return new OrderItemsDto(
//                orderItems.getId(),
//                orderItems.getProduct().getId(),
//                orderItems.getUser().getId(),
//                orderItems.getQuantity(),
//                orderItems.getTotalAmount(),
//                orderItems.getAddress(),
//                orderItems.getPhoneNumber(),
//                orderItems.getName(),
//                orderItems.getSurname(),
//                orderItems.getCity(),
//                orderItems.getCardNumber(),
//                orderItems.getCvv(),
//                orderItems.getOrderDate()
//        );
//    }
}
