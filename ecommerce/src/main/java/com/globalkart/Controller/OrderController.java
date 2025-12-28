package com.globalkart.Controller;


import com.globalkart.Service.OrderService;
import com.globalkart.dto.OrderResponseDTO;
import com.globalkart.model.Order;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {


    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place")
    public Order placeOrder(@RequestParam Long userId) {
        return orderService.placeOrder(userId);
    }

    @GetMapping("/history")
    public List<OrderResponseDTO> orderHistory(@RequestParam Long userId) {
        return orderService.getOrderHistory(userId);
    }

}
