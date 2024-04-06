package AsimMertTopal.whiteGoodsStore.controllers;

import AsimMertTopal.whiteGoodsStore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderControllers {
    private final OrderService orderService;


}
