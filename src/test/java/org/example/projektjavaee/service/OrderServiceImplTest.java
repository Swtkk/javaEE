package org.example.projektjavaee.service;

import org.example.projektjavaee.dao.OrderDAO;
import org.example.projektjavaee.model.*;
import org.example.projektjavaee.service.MailService;
import org.example.projektjavaee.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    private OrderDAO orderDAO;
    private MailService mailService;
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderDAO = mock(OrderDAO.class);
        mailService = mock(MailService.class);
        orderService = new OrderServiceImpl();

        // wstrzyknięcie mocków
        orderService.orderDAO = orderDAO;
        orderService.mailService = mailService;
    }

    @Test
    void testPlaceOrder() {
        User user = new User();
        user.setId(1L);

        Product product = new Product();
        product.setId(100L);
        product.setName("Produkt testowy");

        Map<Product, Integer> cart = new HashMap<>();
        cart.put(product, 2);

        orderService.placeOrder(user, cart, "test@example.com");

        // weryfikujemy, czy create i sendOrderConfirmation zostały wywołane
        verify(orderDAO, times(1)).create(any(Order.class));
        verify(mailService, times(1)).sendOrderConfirmation(eq("test@example.com"), any(Order.class));
    }

    @Test
    void testGetUserOrders() {
        User user = new User();
        user.setId(2L);
        List<Order> expectedOrders = Arrays.asList(new Order(), new Order());

        when(orderDAO.findByUser(user)).thenReturn(expectedOrders);

        List<Order> result = orderService.getUserOrders(user);
        assertEquals(2, result.size());
        verify(orderDAO).findByUser(user);
    }
}
