package com.example.baraka.service;

import com.example.baraka.dto.OrderRequest;
import com.example.baraka.dto.OrderResponse;
import com.example.baraka.model.Direction;
import com.example.baraka.model.Order;
import com.example.baraka.repository.OrderRepository;
import com.example.baraka.utils.OrderEngine;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Auther shijinkoyambil
 * since 06/07/2025
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository = new OrderRepository();
    private final Map<String, OrderEngine> orderBooks = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public OrderResponse orderCreation(OrderRequest req) {
        long id = idGenerator.getAndIncrement();
        Instant now = Instant.now();
        Order newOrder = new Order(id, now, req.asset(), req.price(), req.amount(),
                req.amount(), Direction.valueOf(req.direction()), req.asset());
        orderRepository.save(newOrder);
        // create an OrderEngine for the asset n  try to match this order
        orderBooks
                .computeIfAbsent(req.asset(), key -> new OrderEngine())  // create if not exists
                .matchOrders(newOrder);
        return OrderResponse.fromOrder(newOrder);
    }

    public OrderResponse getOrder(@PathVariable long orderId) {
        Order order = orderRepository.findById(orderId);
        return OrderResponse.fromOrder(order);
    }
}
