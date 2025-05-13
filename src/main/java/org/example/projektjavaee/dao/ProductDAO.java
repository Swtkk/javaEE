package org.example.projektjavaee.dao;

import org.example.projektjavaee.model.Product;

import java.util.List;

public interface ProductDAO {
    void create(Product p);
    Product find(Long id);
    List<Product> findAll();
    void update(Product p);
    void delete(Long id);
}
