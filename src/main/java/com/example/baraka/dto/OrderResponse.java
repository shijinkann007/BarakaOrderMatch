package com.example.baraka.dto;

/**
 * Auther shijinkoyambil
 * since 06/07/2025
 */

import com.example.baraka.model.Order;
import com.example.baraka.model.Trade;

import java.time.Instant;
import java.util.List;

public record OrderResponse(
        long id,
        Instant timestamp,
        String asset,
        double price,
        double amount,
        String direction,
        double pendingAmount,
        List<Trade> trades
) {
    public static OrderResponse fromOrder(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTimestamp(),
                order.getAsset(),
                order.getPrice(),
                order.getAmount(),
                order.getDirection().name(),
                order.getPendingAmount(),
                order.getTrades()
        );
    }
}
