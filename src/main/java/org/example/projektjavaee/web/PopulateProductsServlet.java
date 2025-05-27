package org.example.projektjavaee.web;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.projektjavaee.service.ProductService;

import java.io.IOException;

@WebServlet("/populate-products")
public class PopulateProductsServlet extends HttpServlet {

    @Inject
    private ProductService productService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        productService.initSampleProducts(); // metoda, którą dodamy poniżej
        resp.sendRedirect("admin/dashboard.jsp?success=true");
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST only");
    }
}
