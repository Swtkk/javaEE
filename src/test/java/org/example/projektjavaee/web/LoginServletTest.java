package org.example.projektjavaee.web;

import jakarta.servlet.http.*;
import org.example.projektjavaee.model.Role;
import org.example.projektjavaee.model.User;
import org.example.projektjavaee.service.UserService;
import org.example.projektjavaee.web.LoginServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

class LoginServletTest {

    private LoginServlet servlet;
    private UserService userService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new LoginServlet();
        userService = mock(UserService.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);

        var field = LoginServlet.class.getDeclaredField("userService");
        field.setAccessible(true);
        field.set(servlet, userService);
    }

    private User mockUserWithRole(String roleName) {
        User user = new User();
        Role role = new Role();
        role.setName(roleName);
        user.setRole(role);
        return user;
    }

    @Test
    void testSuccessfulLoginClientRedirect() throws IOException {
        when(request.getParameter("username")).thenReturn("client");
        when(request.getParameter("password")).thenReturn("1234");
        when(userService.login("client", "1234")).thenReturn(mockUserWithRole("CLIENT"));
        when(request.getSession()).thenReturn(session);

        servlet.doPost(request, response);

        verify(session).setAttribute(eq("user"), any(User.class));
        verify(response).sendRedirect("client/shop");
    }

    @Test
    void testSuccessfulLoginAdminRedirect() throws IOException {
        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("pass");
        when(userService.login("admin", "pass")).thenReturn(mockUserWithRole("ADMIN"));
        when(request.getSession()).thenReturn(session);

        servlet.doPost(request, response);

        verify(response).sendRedirect("admin/dashboard.jsp");
    }

    @Test
    void testFailedLoginRedirect() throws IOException {
        when(request.getParameter("username")).thenReturn("ghost");
        when(request.getParameter("password")).thenReturn("wrong");
        when(userService.login("ghost", "wrong")).thenReturn(null);

        servlet.doPost(request, response);

        verify(response).sendRedirect("login.jsp?error=true");
    }
}
