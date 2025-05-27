package org.example.projektjavaee.web;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.projektjavaee.dao.ProductDAO;
import org.example.projektjavaee.model.Product;
import org.example.projektjavaee.service.ProductService;

import java.io.IOException;
import java.util.List;

@WebServlet("/client/shop")
public class ProductServlet extends HttpServlet {
    @Inject
    private ProductDAO productDAO;
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Product> products = productDAO.findAll();
        req.setAttribute("products", products);
        try {
            req.getRequestDispatcher("/client/shop.jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
