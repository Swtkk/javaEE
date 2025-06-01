package org.example.projektjavaee.web;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.projektjavaee.dao.EventLogDAO;
import org.example.projektjavaee.model.User;
import org.example.projektjavaee.service.UserService;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Inject
    private EventLogDAO logDAO;

    @Inject
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = userService.login(username, password);

        if (user != null) {
            req.getSession().setAttribute("user", user);

            // ✅ Logowanie sukcesu
            logDAO.log(user.getUsername(), "LOGIN_SUCCESS", "Użytkownik zalogował się pomyślnie.");

            // Przekierowanie wg roli
            String role = user.getRole().getName();
            if ("ADMIN".equalsIgnoreCase(role) || "WORKER".equalsIgnoreCase(role)) {
                resp.sendRedirect("admin/dashboard.jsp");
            } else if ("CLIENT".equalsIgnoreCase(role)) {
                resp.sendRedirect("client/shop");
            } else {
                resp.sendRedirect("home.jsp");
            }
        } else {
            // ✅ Logowanie błędne (próba)
            logDAO.log(username, "LOGIN_FAIL", "Nieudana próba logowania.");

            resp.sendRedirect("login.jsp?error=true");
        }
    }
}
