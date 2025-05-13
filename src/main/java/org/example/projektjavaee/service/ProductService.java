package org.example.projektjavaee.service;

import org.example.projektjavaee.model.Product;

import java.util.List;

public interface ProductService {
    void addProduct(Product p);
    List<Product> getAllProducts();
}