package com.example.baraka.repository;

import com.example.baraka.model.Order;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Auther shijinkoyambil
 * since 06/07/2025
 */
@Repository
public class OrderRepository {
    private final Map<Long, Order> store = new ConcurrentHashMap<>();
    public void save(Order order) {
        store.put(order.getId(), order);
    }
    public Order findById(long id) {
        return store.get(id);
    }
}
