package org.example.projektjavaee.web;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.projektjavaee.model.User;

import java.io.IOException;

@WebFilter("/client/*")
public class ClientAuthFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");

            if ("CLIENT".equalsIgnoreCase(user.getRole().getName()) || "WORKER".equalsIgnoreCase(user.getRole().getName()) || "ADMIN".equalsIgnoreCase(user.getRole().getName())) {
                chain.doFilter(request, response);
                return;
            }
        }
        ((HttpServletResponse) response).sendRedirect(req.getContextPath() + "/login.jsp");
    }
}
