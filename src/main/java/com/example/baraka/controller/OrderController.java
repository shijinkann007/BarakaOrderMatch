package com.example.baraka.controller;

import com.example.baraka.dto.OrderRequest;
import com.example.baraka.dto.OrderResponse;
import com.example.baraka.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
    * Auther shijinkoyambil
    * since 06/07/2025
 */

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.orderCreation(orderRequest));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable long orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }
}
