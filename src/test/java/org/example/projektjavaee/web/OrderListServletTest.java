package org.example.projektjavaee.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.example.projektjavaee.model.Order;
import org.example.projektjavaee.model.User;
import org.example.projektjavaee.service.OrderService;
import org.example.projektjavaee.web.OrderListServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

class OrderListServletTest {

    private OrderListServlet servlet;
    private OrderService orderService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new OrderListServlet();
        orderService = mock(OrderService.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);

        var field = OrderListServlet.class.getDeclaredField("orderService");
        field.setAccessible(true);
        field.set(servlet, orderService);
    }

    @Test
    void testDoGetDisplaysOrderList() throws ServletException, IOException {
        User user = new User();
        List<Order> orders = List.of(new Order());

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(orderService.getUserOrders(user)).thenReturn(orders);
        when(request.getRequestDispatcher("/client/orders.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(request).setAttribute("orders", orders);
        verify(dispatcher).forward(request, response);
    }
}
