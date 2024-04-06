package AsimMertTopal.whiteGoodsStore.service;

import AsimMertTopal.whiteGoodsStore.dto.cartItems.CartSummaryDto;
import AsimMertTopal.whiteGoodsStore.dto.orderItems.OrderItemsDto;
import AsimMertTopal.whiteGoodsStore.dto.orderItems.OrderItemsUpdateDto;
import AsimMertTopal.whiteGoodsStore.dto.orderItems.SellerOrder;
import AsimMertTopal.whiteGoodsStore.dto.orderItems.UsersOrder;
import AsimMertTopal.whiteGoodsStore.dto.product.ProductCartDto;
import AsimMertTopal.whiteGoodsStore.dto.product.ProductDto;
import AsimMertTopal.whiteGoodsStore.entities.*;
import AsimMertTopal.whiteGoodsStore.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemsService {
    private final OrderItemsRepository orderItemsRepository;
    private final OrderRepository   orderRepository;

    private final ProductRepository    productRepository;
    private final ProductService productService;
    private final SellerRepository    sellerRepository;
    private final UserRepository    userRepository;
    private final CartItemService   cartItemService;

    public ResponseEntity<String> createOrder(Long userId, OrderItemsDto dto) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Kullanıcı Bulunamadı"));

            CartSummaryDto cartSummary = cartItemService.viewCart(userId);
            List<ProductCartDto> productsInCartDto = cartSummary.productsInCart();

            if (productsInCartDto.isEmpty()) {
                return ResponseEntity.badRequest().body("Sepet boş. Sipariş oluşturulamaz.");
            }

            Order order = new Order();
            order.setUser(user);
            orderRepository.save(order);

            for (ProductCartDto productInCart : productsInCartDto) {
                Product product = productRepository.findById(productInCart.id())
                        .orElseThrow(() -> new RuntimeException("Ürün bulunamadı"));

                OrderItems orderItems = new OrderItems();
                orderItems.setOrder(order);
                orderItems.setProduct(product);
                orderItems.setUser(user);
                orderItems.setQuantity(productInCart.quantity());
                orderItems.setTotalAmount(productInCart.price() * productInCart.quantity());
                orderItems.setAddress(dto.address());
                orderItems.setPhoneNumber(dto.phoneNumber());
                orderItems.setName(dto.name());
                orderItems.setSurname(dto.surname());
                orderItems.setCity(dto.city());
                orderItems.setCardNumber(dto.cardNumber());
                orderItems.setCvv(dto.cvv());
                orderItems.setOrderDate(LocalDateTime.now());

                orderItems.setSeller(product.getSeller());

                int remainingStock = (int) (product.getStock() - productInCart.quantity());
                if (remainingStock < 0) {
                    return ResponseEntity.badRequest().body("Stok yetersiz. Sipariş oluşturulamaz.");
                }
                product.setStock(remainingStock);
                productRepository.save(product);

                orderItemsRepository.save(orderItems);
            }

            cartItemService.clearCart(userId);

            return ResponseEntity.ok("Sipariş başarıyla oluşturuldu!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Sipariş oluşturulurken bir hata oluştu: " + e.getMessage());
        }
    }

    public ResponseEntity<String> updateOrder(Long orderItemsId, OrderItemsUpdateDto dto) {
        try {
            OrderItems orderItem = orderItemsRepository.findById(orderItemsId)
                    .orElseThrow(() -> new RuntimeException("Sipariş Bulunamadı"));

            orderItem.setAddress(dto.address());
            orderItem.setPhoneNumber(dto.phoneNumber());
            orderItem.setName(dto.name());
            orderItem.setSurname(dto.surname());
            orderItem.setCity(dto.city());

            orderItemsRepository.save(orderItem);

            return ResponseEntity.ok("Sipariş bilgileri başarıyla güncellendi!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Sipariş güncellenirken bir hata oluştu: " + e.getMessage());
        }
    }



    public ResponseEntity<String> cancelOrderItem(Long orderItemId) {
        try {
            OrderItems orderItem = orderItemsRepository.findById(orderItemId)
                    .orElseThrow(() -> new RuntimeException("Sipariş  bulunamadı"));

            Product product = orderItem.getProduct();

            int originalStock = (int) product.getStock();
            int addedStock = orderItem.getQuantity();
            int newStock = originalStock + addedStock;
            product.setStock(newStock);
            productRepository.save(product);

            orderItemsRepository.delete(orderItem);

            return ResponseEntity.ok("Sipariş  başarıyla iptal edildi.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Sipariş iptal edilirken bir hata oluştu: " + e.getMessage());
        }
    }





    public List<UsersOrder> getUserOrders(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Kullanıcı Bulunamadı"));

            List<OrderItems> userOrderItems = orderItemsRepository.findByUser(user);

            if (userOrderItems.isEmpty()) {
                return Collections.emptyList();
            }

            List<UsersOrder> orderDtoList = new ArrayList<>();

            for (OrderItems orderItems : userOrderItems) {
                List<ProductDto> productsInOrderDto = List.of(new ProductDto(
                        orderItems.getProduct().getId(),
                        orderItems.getProduct().getName(),
                        orderItems.getProduct().getDescription(),
                        orderItems.getProduct().getPrice(),
                        orderItems.getProduct().getStock()

                ));

                UsersOrder orderDto = new UsersOrder(
                        orderItems.getOrder().getId(),
                        userId,
                        orderItems.getProduct().getId(),
                        orderItems.getTotalAmount(),
                        orderItems.getAddress(),
                        orderItems.getPhoneNumber(),
                        orderItems.getName(),
                        orderItems.getSurname(),
                        orderItems.getCity(),
                        orderItems.getOrderDate(),
                        productsInOrderDto
                );

                orderDtoList.add(orderDto);
            }

            return orderDtoList;
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Kullanıcının siparişleri ya da Kullanıcı bulunamadı");
        } catch (DataAccessException e) {
            throw new RuntimeException("Ürün getirilirken bir sorun oluştu");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Kullanıcının siparişleri getirilirken bir hata oluştu");
        }
    }


    public List<SellerOrder> getSellerOrders(Long userId) {
        try {
            User seller = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Satıcı Bulunamadı"));

            List<Product> sellerProducts = productRepository.findByUser(seller);

            if (sellerProducts.isEmpty()) {
                return Collections.emptyList();
            }

            List<SellerOrder> summaryList = new ArrayList<>();

            for (Product product : sellerProducts) {
                List<OrderItems> productOrderItems = orderItemsRepository.findByProductId(product.getId());

                if (!productOrderItems.isEmpty()) {
                    List<ProductDto> productDtos = List.of(new ProductDto(
                            product.getId(),
                            product.getName(),
                            product.getDescription(),
                            product.getPrice(),
                            product.getStock()
                    ));

                    int soldQuantity = calculateSoldQuantity(product);
                    double totalRevenue = calculateTotalRevenue(product);

                    List<SellerOrder> sellerOrderList = productOrderItems.stream()
                            .map(orderItems -> new SellerOrder(
                                    orderItems.getOrder().getId(),
                                    orderItems.getUser().getId(),
                                    product.getId(),
                                    orderItems.getTotalAmount(),
                                    orderItems.getAddress(),
                                    orderItems.getPhoneNumber(),
                                    orderItems.getName(),
                                    orderItems.getSurname(),
                                    orderItems.getCity(),
                                    orderItems.getOrderDate(),
                                    productDtos,
                                    totalRevenue
                            ))
                            .collect(Collectors.toList());


                    long productId = product.getId();


                    summaryList.addAll(sellerOrderList);
                }
            }

            return summaryList;
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Satıcı bulunamadı");
        } catch (DataAccessException e) {
            throw new RuntimeException("Sipariş bilgileri getirilirken bir sorun oluştu");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Satıcının sipariş özetleri getirilirken bir hata oluştu");
        }
    }


    private int calculateSoldQuantity(Product product) {
        return orderItemsRepository.findByProductId(product.getId())
                .stream()
                .mapToInt(OrderItems::getQuantity)
                .sum();
    }

    private double calculateTotalRevenue(Product product) {
        return orderItemsRepository.findByProductId(product.getId())
                .stream()
                .mapToDouble(orderItems -> orderItems.getTotalAmount() * orderItems.getQuantity())
                .sum();
    }


}





