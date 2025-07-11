package com.example.baraka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Auther shijinkoyambil
 * since 06/07/2025
 */
@Getter
public class Trade {
    private final long orderId;
    private final double amount;
    private final double price;
    public Trade(long orderId, double amount, double price) {
        this.orderId = orderId;
        this.amount = amount;
        this.price = price;
    }
}
