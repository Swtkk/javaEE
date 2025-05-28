package org.example.projektjavaee.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.example.projektjavaee.dao.ProductDAO;
import org.example.projektjavaee.model.Product;
import org.example.projektjavaee.web.ProductServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

class ProductServletTest {

    private ProductServlet servlet;
    private ProductDAO productDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new ProductServlet();
        productDAO = mock(ProductDAO.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);

        var field = ProductServlet.class.getDeclaredField("productDAO");
        field.setAccessible(true);
        field.set(servlet, productDAO);
    }

    @Test
    void testDoGetLoadsProductsAndForwards() throws ServletException, IOException {
        List<Product> productList = List.of(new Product(), new Product());
        when(productDAO.findAll()).thenReturn(productList);
        when(request.getRequestDispatcher("/client/shop.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(request).setAttribute("products", productList);
        verify(dispatcher).forward(request, response);
    }
}
