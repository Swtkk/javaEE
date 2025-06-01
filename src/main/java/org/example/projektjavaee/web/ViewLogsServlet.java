package org.example.projektjavaee.web;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.projektjavaee.dao.EventLogDAO;
import org.example.projektjavaee.model.EventLog;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/logs")
public class ViewLogsServlet extends HttpServlet {

    @Inject
    private EventLogDAO logDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filter = req.getParameter("filter");
        List<EventLog> logs = (filter != null && !filter.isEmpty())
                ? logDAO.findByAction(filter)
                : logDAO.findAll();

        req.setAttribute("logs", logs);
        req.getRequestDispatcher("/admin/logs.jsp").forward(req, resp);
    }
}
