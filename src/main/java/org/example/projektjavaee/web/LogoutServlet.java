package org.example.projektjavaee.web;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.projektjavaee.dao.EventLogDAO;
import org.example.projektjavaee.model.User;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Inject
    private EventLogDAO logDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);

        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                // ✅ Zapisz log wylogowania
                logDAO.log(user.getUsername(), "LOGOUT", "Użytkownik wylogował się.");
            }
            session.invalidate();
        }

        resp.sendRedirect("login.jsp");
    }
}
