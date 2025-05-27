package org.example.projektjavaee.web;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.projektjavaee.model.Order;
import org.example.projektjavaee.model.User;
import org.example.projektjavaee.service.OrderService;

import java.io.IOException;
import java.util.List;

@WebServlet("/client/orders")
public class OrderListServlet extends HttpServlet {

    @Inject
    private OrderService orderService;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        List<Order> orders = orderService.getUserOrders(user);
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/client/orders.jsp").forward(req, resp);
    }
}
