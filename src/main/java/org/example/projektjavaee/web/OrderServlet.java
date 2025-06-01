package org.example.projektjavaee.web;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.projektjavaee.dao.EventLogDAO;
import org.example.projektjavaee.model.Product;
import org.example.projektjavaee.model.User;
import org.example.projektjavaee.service.OrderService;

import java.io.IOException;
import java.util.Map;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    @Inject
    private OrderService orderService;

    @Inject
    private EventLogDAO logDAO; // Dodane: logowanie zdarzeń

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        String action = req.getParameter("action");
        User user = (User) session.getAttribute("user");

        if ("remove".equals(action)) {
            Long productId = Long.parseLong(req.getParameter("productId"));
            Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");

            if (cart != null) {
                Product toRemove = null;
                for (Product p : cart.keySet()) {
                    if (p.getId().equals(productId)) {
                        toRemove = p;
                        break;
                    }
                }
                if (toRemove != null) {
                    cart.remove(toRemove);
                    session.setAttribute("flash", "Produkt został usunięty z koszyka.");

                    // 🔴 Zapisz log usunięcia produktu z koszyka
                    if (user != null) {
                        logDAO.log(user.getUsername(), "REMOVE_FROM_CART",
                                "Usunięto produkt z koszyka: " + toRemove.getName());
                    }
                }
            }

            resp.sendRedirect("client/cart.jsp");
            return;
        }

        // Obsługa złożenia zamówienia
        String email = req.getParameter("email");
        Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");

        if (cart != null && !cart.isEmpty()) {
            orderService.placeOrder(user, cart, email);
            session.removeAttribute("cart");
            session.setAttribute("flash", "Zamówienie zostało złożone pomyślnie i wysłane na e-mail: " + email);

            // ✅ Logowanie złożenia zamówienia
            if (user != null) {
                logDAO.log(user.getUsername(), "PLACE_ORDER",
                        "Złożono zamówienie na " + cart.size() + " produktów. Email: " + email);
            }

            resp.sendRedirect("client/shop");
        } else {
            resp.sendRedirect("client/cart.jsp?error=empty");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}
