package org.example.projektjavaee.web;

import jakarta.inject.Inject;
import org.example.projektjavaee.dao.EventLogDAO;
import org.example.projektjavaee.dao.ProductDAO;
import org.example.projektjavaee.model.Product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.projektjavaee.model.User;

import java.io.IOException;

@WebServlet("/admin/addProduct")
public class AddProductServlet extends HttpServlet {

    @Inject
    private ProductDAO productDao;


    @Inject
    private EventLogDAO logDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/admin/addProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        productDao.create(product); // zapis do bazy
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        if (user != null) {
            logDAO.log(user.getUsername(), "ADD_PRODUCT", "Dodano produkt: " + name);
        }
        request.setAttribute("success", true);
        request.getRequestDispatcher("/admin/addProduct.jsp").forward(request, response);

    }
}
