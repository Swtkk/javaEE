package org.example.projektjavaee.web;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.projektjavaee.dao.ProductDAO;
import org.example.projektjavaee.model.Product;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {

    @Inject
    private ProductDAO productDAO;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long productId = Long.parseLong(req.getParameter("productId"));
        Product product = productDAO.find(productId);

        HttpSession session = req.getSession();
        Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }

        cart.put(product, cart.getOrDefault(product, 0) + 1);
        session.setAttribute("cart", cart);

        resp.sendRedirect("client/shop");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}

