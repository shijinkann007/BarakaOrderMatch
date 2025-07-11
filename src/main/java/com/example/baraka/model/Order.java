package com.example.baraka.model;

import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Auther shijinkoyambil
 * since 06/07/2025
 */
@Getter
public class Order {
    private final long id;
    private final Instant timestamp;
    private final String asset;
    private final double price;
    private final double amount;
    private double pendingAmount;
    private final Direction direction;
    private final List<Trade> trades = new ArrayList<>();

    public Order(long id, Instant timestamp, String asset, double price, double amount, double pendingAmount, Direction direction, String assetName) {
        this.id = id;
        this.timestamp = timestamp;
        this.asset = asset;
        this.price = price;
        this.amount = amount;
        this.pendingAmount = pendingAmount;
        this.direction = direction;
    }

    public void fill(double fillAmount, double fillPrice, long counterOrderId) {
        this.pendingAmount -= fillAmount;
        this.trades.add(new Trade(counterOrderId, fillAmount, fillPrice));
    }
}
