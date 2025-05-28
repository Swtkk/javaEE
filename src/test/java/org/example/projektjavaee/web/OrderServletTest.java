package org.example.projektjavaee.web;

import jakarta.servlet.http.*;
import org.example.projektjavaee.model.Product;
import org.example.projektjavaee.model.User;
import org.example.projektjavaee.service.OrderService;
import org.example.projektjavaee.web.OrderServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.mockito.Mockito.*;

class OrderServletTest {

    private OrderServlet servlet;
    private OrderService orderService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new OrderServlet();
        orderService = mock(OrderService.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);

        var field = OrderServlet.class.getDeclaredField("orderService");
        field.setAccessible(true);
        field.set(servlet, orderService);
    }

    @Test
    void testPlaceOrderSuccess() throws IOException {
        User user = new User();
        Map<Product, Integer> cart = new HashMap<>();
        cart.put(new Product(), 2);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(session.getAttribute("cart")).thenReturn(cart);
        when(request.getParameter("email")).thenReturn("test@example.com");

        servlet.doPost(request, response);

        verify(orderService).placeOrder(eq(user), eq(cart), eq("test@example.com"));
        verify(session).removeAttribute("cart");
        verify(session).setAttribute(eq("flash"), contains("test@example.com"));
        verify(response).sendRedirect("client/shop");
    }

    @Test
    void testPlaceOrderEmptyCart() throws IOException {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("cart")).thenReturn(new HashMap<>());

        servlet.doPost(request, response);

        verify(response).sendRedirect("client/cart.jsp?error=empty");
    }
}
