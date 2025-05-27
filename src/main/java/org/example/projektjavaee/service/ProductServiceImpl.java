package org.example.projektjavaee.service;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.example.projektjavaee.dao.ProductDAO;
import org.example.projektjavaee.model.Product;

import java.util.List;

@Stateless
public class ProductServiceImpl implements ProductService {
    @Inject
    private ProductDAO productDAO;
    public void addProduct(Product p) { productDAO.create(p); }
    public List<Product> getAllProducts() { return productDAO.findAll(); }
    @PostConstruct
    public void initSampleProducts() {
        if (productDAO.findAll().isEmpty()) {
            Product p1 = new Product();
            p1.setName("Laptop");
            p1.setDescription("Laptop 15 cali, 16GB RAM");
            p1.setPrice(3499.99);
            productDAO.create(p1);

            Product p2 = new Product();
            p2.setName("Smartfon");
            p2.setDescription("Smartfon z aparatem 108 MP");
            p2.setPrice(2499.00);
            productDAO.create(p2);

            Product p3 = new Product();
            p3.setName("Słuchawki");
            p3.setDescription("Bezprzewodowe słuchawki douszne");
            p3.setPrice(299.00);
            productDAO.create(p3);
        }
    }

}