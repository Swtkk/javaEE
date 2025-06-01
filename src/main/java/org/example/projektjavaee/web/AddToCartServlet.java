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
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {

    @Inject
    private ProductDAO productDAO;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Long productId = Long.parseLong(request.getParameter("productId"));

        // zakładam że masz serwis lub DAO do pobierania produktu
        Product product = productDAO.find(productId);

        Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");

        if (cart == null) {
            cart = new LinkedHashMap<>();
            session.setAttribute("cart", cart);
        }

        // Szukamy czy już istnieje taki produkt w mapie
        Product existing = null;
        for (Product p : cart.keySet()) {
            if (p.getId().equals(product.getId())) {
                existing = p;
                break;
            }
        }

        if (existing != null) {
            cart.put(existing, cart.get(existing) + 1); // zwiększamy ilość
        } else {
            cart.put(product, 1); // nowy wpis
        }

        session.setAttribute("flash", "Produkt został dodany do koszyka.");
        response.sendRedirect("client/shop");
    }


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}

