package org.example.projektjavaee.service;

import org.example.projektjavaee.model.Order;
import org.example.projektjavaee.model.Product;
import org.example.projektjavaee.model.User;

import java.util.List;
import java.util.Map;

public interface OrderService {
    void placeOrder(User user, Map<Product, Integer> cart, String email);
    List<Order> getUserOrders(User user);
}
