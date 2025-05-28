package org.example.projektjavaee.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.example.projektjavaee.dao.OrderDAO;
import org.example.projektjavaee.model.Order;
import org.example.projektjavaee.model.OrderItem;
import org.example.projektjavaee.model.Product;
import org.example.projektjavaee.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Stateless
public class OrderServiceImpl implements OrderService {
    @Inject
    OrderDAO orderDAO;
    @Inject
    MailService mailService;

    public void placeOrder(User user, Map<Product, Integer> cart, String email) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());

        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();

            OrderItem item = new OrderItem();
            item.setProduct(p);
            item.setQuantity(qty);
            item.setOrder(order);

            order.getItems().add(item); // wcześniej NULL → teraz działa!
        }

        orderDAO.create(order);
        order.getItems().size(); // wymuszenie załadowania listy
        mailService.sendOrderConfirmation(email, order);

    }

    public List<Order> getUserOrders(User user) {
        return orderDAO.findByUser(user);
    }
}
