package org.example.projektjavaee.web;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.projektjavaee.dao.EventLogDAO;
import org.example.projektjavaee.dao.ProductDAO;
import org.example.projektjavaee.model.Product;
import org.example.projektjavaee.model.User;

import java.io.IOException;
import java.util.List;

@WebServlet("/client/shop")
public class ProductServlet extends HttpServlet {

    @Inject
    private ProductDAO productDAO;

    @Inject
    private EventLogDAO logDAO; // Wstrzyknięcie logów

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        String idParam = req.getParameter("productId");

        if ("edit".equals(action) && idParam != null) {
            Long id = Long.parseLong(idParam);
            Product product = productDAO.find(id);
            req.setAttribute("product", product);
            req.getRequestDispatcher("/admin/editProduct.jsp").forward(req, resp);
            return;
        }

        // domyślnie: pokaż sklep
        List<Product> products = productDAO.findAll();
        req.setAttribute("products", products);
        req.getRequestDispatcher("/client/shop.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        if ("update".equals(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            String name = req.getParameter("name");
            String desc = req.getParameter("description");
            double price = Double.parseDouble(req.getParameter("price"));

            Product product = productDAO.find(id);
            product.setName(name);
            product.setDescription(desc);
            product.setPrice(price);

            productDAO.update(product);
            session.setAttribute("flash", "Produkt zaktualizowany.");

            // ✅ Logowanie edycji produktu
            if (user != null) {
                logDAO.log(user.getUsername(), "EDIT_PRODUCT",
                        "Zmieniono produkt ID=" + id + ", nowa nazwa: " + name);
            }

        } else if ("delete".equals(action)) {
            Long id = Long.parseLong(req.getParameter("productId"));
            Product product = productDAO.find(id);
            productDAO.delete(id);
            session.setAttribute("flash", "Produkt usunięty.");

            // ✅ Logowanie usunięcia produktu
            if (user != null && product != null) {
                logDAO.log(user.getUsername(), "DELETE_PRODUCT",
                        "Usunięto produkt ID=" + id + ", nazwa: " + product.getName());
            }
        }

        resp.sendRedirect(req.getContextPath() + "/client/shop");
    }
}
