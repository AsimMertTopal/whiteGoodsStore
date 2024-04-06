package AsimMertTopal.whiteGoodsStore.controllers;

import AsimMertTopal.whiteGoodsStore.dto.orderItems.OrderItemsDto;
import AsimMertTopal.whiteGoodsStore.dto.orderItems.OrderItemsUpdateDto;
import AsimMertTopal.whiteGoodsStore.dto.orderItems.SellerOrder;
import AsimMertTopal.whiteGoodsStore.dto.orderItems.UsersOrder;
import AsimMertTopal.whiteGoodsStore.service.OrderItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderItems")
@RequiredArgsConstructor
public class OrderItemsController {
    private final OrderItemsService orderItemsService;

    @PostMapping("/createOrder")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> createOrder(@RequestParam Long userId, @RequestBody OrderItemsDto dto) {
        return orderItemsService.createOrder(userId, dto);
    }

    @DeleteMapping("/cancel/{orderItemId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> cancelOrderItem(@PathVariable Long orderItemId) {
        return orderItemsService.cancelOrderItem(orderItemId);
    }

    @GetMapping("/getUserOrders")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<UsersOrder>getUserOrders(@RequestParam Long userId) {
        return orderItemsService.getUserOrders(userId);
    }

    @GetMapping("/getSellerOrders")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public List<SellerOrder> getSellerOrders (@RequestParam Long userId) {
        return orderItemsService.getSellerOrders(userId);
    }

    @PutMapping("/updateOrder/{orderItemId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> updateOrder(@PathVariable Long orderItemId, @RequestBody OrderItemsUpdateDto dto) {
        return orderItemsService.updateOrder(orderItemId, dto);
    }

}
