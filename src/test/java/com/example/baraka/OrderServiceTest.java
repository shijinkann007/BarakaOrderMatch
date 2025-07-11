package com.example.baraka;


import com.example.baraka.dto.OrderRequest;
import com.example.baraka.dto.OrderResponse;
import com.example.baraka.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(); // assuming no dependencies to inject
    }

    @Test
    void testOrderBuy() {

        OrderRequest req = new OrderRequest("SUI", 2.1, 100.0, "BUY");
        OrderResponse response = orderService.orderCreation(req);
        assertNotNull(response);
        assertEquals("SUI", response.asset());
        assertEquals(2.1, response.price());
        assertEquals(100.0, response.amount());
        assertEquals("BUY", response.direction());
        assertEquals(100.0, response.pendingAmount()); // not matched yet
        assertTrue(response.id() > 0);
    }

    @Test
    void testOrderSell() {

        OrderRequest req = new OrderRequest("SUI", 2.1, 100.0, "SELL");
        OrderResponse response = orderService.orderCreation(req);
        assertNotNull(response);
        assertEquals("SUI", response.asset());
        assertEquals(2.1, response.price());
        assertEquals(100.0, response.amount());
        assertEquals("SELL", response.direction());
        assertEquals(100.0, response.pendingAmount()); // not matched yet
        assertTrue(response.id() > 0);
    }

    @Test
    void testGetOrder() {

        OrderRequest req = new OrderRequest("ETH", 1234.0, 0.5, "SELL");
        OrderResponse created = orderService.orderCreation(req);
        OrderResponse fetched = orderService.getOrder(created.id());
        assertEquals(created.id(), fetched.id());
        assertEquals("ETH", fetched.asset());
        assertEquals("SELL", fetched.direction());
        assertEquals(0.5, fetched.pendingAmount());
    }
}
