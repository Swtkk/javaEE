package org.example.projektjavaee.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.example.projektjavaee.model.Role;
import org.example.projektjavaee.model.User;
import org.example.projektjavaee.service.UserService;
import org.example.projektjavaee.web.RegisterServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

class RegisterServletTest {

    private RegisterServlet servlet;
    private UserService userService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new RegisterServlet();
        userService = mock(UserService.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);

        var field = RegisterServlet.class.getDeclaredField("userService");
        field.setAccessible(true);
        field.set(servlet, userService);
    }

    @Test
    void testGetRedirectsToRegisterPage() throws IOException {
        servlet.doGet(request, response);
        verify(response).sendRedirect("register.jsp");
    }

    @Test
    void testRegisterUserAlreadyExists() throws ServletException, IOException {
        when(request.getParameter("username")).thenReturn("admin");
        when(userService.existsByUsername("admin")).thenReturn(true);
        when(request.getRequestDispatcher("register.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute("error", "Użytkownik o takiej nazwie już istnieje.");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testRegisterSuccess() throws IOException, ServletException {
        when(request.getParameter("username")).thenReturn("newuser");
        when(request.getParameter("password")).thenReturn("123");
        when(request.getParameter("role")).thenReturn("CLIENT");
        when(userService.existsByUsername("newuser")).thenReturn(false);

        servlet.doPost(request, response);

        verify(userService).register(any(User.class));
        verify(response).sendRedirect("login.jsp");
    }
}
