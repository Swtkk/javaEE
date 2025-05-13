package org.example.projektjavaee.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.example.projektjavaee.dao.ProductDAO;
import org.example.projektjavaee.model.Product;

import java.util.List;

@Stateless
public class ProductServiceImpl implements ProductService {
    @Inject
    private ProductDAO dao;
    public void addProduct(Product p) { dao.create(p); }
    public List<Product> getAllProducts() { return dao.findAll(); }
}