package org.example.projektjavaee.web;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.projektjavaee.model.Product;
import org.example.projektjavaee.model.User;
import org.example.projektjavaee.service.OrderService;

import java.io.IOException;
import java.util.Map;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    @Inject
    private OrderService orderService;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        String email = req.getParameter("email");

        Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");

        if (cart != null && !cart.isEmpty()) {
            orderService.placeOrder(user, cart, email);
            session.removeAttribute("cart");
            session.setAttribute("flash", "Zamówienie zostało złożone pomyślnie i wysłane na e-mail: " + email);
            resp.sendRedirect("client/shop");
        } else {
            resp.sendRedirect("client/cart.jsp?error=empty");
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}

