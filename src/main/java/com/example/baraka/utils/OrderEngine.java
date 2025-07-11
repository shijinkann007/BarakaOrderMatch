package com.example.baraka.utils;

import com.example.baraka.model.Direction;
import com.example.baraka.model.Order;
import com.example.baraka.repository.OrderRepository;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Auther shijinkoyambil
 * since 06/07/2025
 */
public class OrderEngine {

    private final PriorityQueue<Order> buyOrders = new PriorityQueue<>(
            Comparator.comparing(Order::getPrice).reversed().thenComparing(Order::getTimestamp));

    private final PriorityQueue<Order> sellOrders = new PriorityQueue<>(
            Comparator.comparing(Order::getPrice).thenComparing(Order::getTimestamp));

    // This method tries to match an incoming order with existing ones in the opposite order book
    public synchronized void matchOrders(Order incomingOrder) {
        // Choose which book to match against: if incoming is BUY, match against SELL orders
        PriorityQueue<Order> oppositeOrderBook =
                incomingOrder.getDirection() == Direction.BUY ? sellOrders : buyOrders;

        // Keep trying to match until either:
        // 1. Opposite book is empty
        // 2. Incoming order is fully filled
        while (!oppositeOrderBook.isEmpty() && incomingOrder.getPendingAmount() > 0) {
            // Look at the best matching order from the opposite side (but don't remove it yet)
            Order existingOrder = oppositeOrderBook.peek();

            // Check if the price conditions match for the trade
            boolean isPriceMatch;
            if (incomingOrder.getDirection() == Direction.BUY) {
                // Buy order can only match if existing sell price is lower or equal
                isPriceMatch = existingOrder.getPrice() <= incomingOrder.getPrice();
            } else {
                // Sell order can only match if existing buy price is higher or equal
                isPriceMatch = existingOrder.getPrice() >= incomingOrder.getPrice();
            }

            // If prices don't match, stop trying to match
            if (!isPriceMatch) {
                break;
            }

            // Find how much we can fill in this trade
            double fillAmount = Math.min(
                    incomingOrder.getPendingAmount(),
                    existingOrder.getPendingAmount()
            );

            // Fill the trade from both sides
            incomingOrder.fill(fillAmount, existingOrder.getPrice(), existingOrder.getId());
            existingOrder.fill(fillAmount, existingOrder.getPrice(), incomingOrder.getId());

            // If the existing order is completely filled, remove it from the book
            if (existingOrder.getPendingAmount() == 0) {
                oppositeOrderBook.poll();
            }
        }

        // If the incoming order is not completely filled, add it to its own order book
        if (incomingOrder.getPendingAmount() > 0) {
            if (incomingOrder.getDirection() == Direction.BUY) {
                buyOrders.add(incomingOrder);
            } else {
                sellOrders.add(incomingOrder);
            }
        }
    }
}
