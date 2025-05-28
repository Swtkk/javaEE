package org.example.projektjavaee.web;

import jakarta.servlet.http.*;
import org.example.projektjavaee.dao.ProductDAO;
import org.example.projektjavaee.model.Product;
import org.example.projektjavaee.web.AddToCartServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AddToCartServletTest {

    private AddToCartServlet servlet;
    private ProductDAO productDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new AddToCartServlet();

        productDAO = mock(ProductDAO.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);

        // Ręczne wstrzyknięcie mocka ProductDAO po nazwie pola
        var field = AddToCartServlet.class.getDeclaredField("productDAO");
        field.setAccessible(true);
        field.set(servlet, productDAO);
    }
    @Test
    void testDoPostAddsProductToCart() throws IOException {
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");

        when(request.getParameter("productId")).thenReturn("1");
        when(productDAO.find(1L)).thenReturn(product);
        when(request.getSession()).thenReturn(session);

        Map<Product, Integer> cart = new HashMap<>();
        when(session.getAttribute("cart")).thenReturn(cart);

        servlet.doPost(request, response);

        verify(session).setAttribute(eq("cart"), any(Map.class));
        verify(response).sendRedirect("client/shop");
    }

    @Test
    void testDoPostInitializesNewCartIfNoneExists() throws IOException {
        Product product = new Product();
        product.setId(2L);
        product.setName("Smartfon");

        when(request.getParameter("productId")).thenReturn("2");
        when(productDAO.find(2L)).thenReturn(product);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("cart")).thenReturn(null);

        servlet.doPost(request, response);

        verify(session).setAttribute(eq("cart"), any(Map.class));
        verify(response).sendRedirect("client/shop");
    }
}
