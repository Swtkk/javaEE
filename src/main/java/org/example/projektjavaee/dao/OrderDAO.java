package org.example.projektjavaee.dao;

import org.example.projektjavaee.model.Order;
import org.example.projektjavaee.model.User;

import java.util.List;

public interface OrderDAO {
    void create(Order order);
    List<Order> findByUser(User user);
}
