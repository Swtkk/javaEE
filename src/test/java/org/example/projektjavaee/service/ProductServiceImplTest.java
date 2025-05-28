package org.example.projektjavaee.service;

import org.example.projektjavaee.dao.ProductDAO;
import org.example.projektjavaee.model.Product;
import org.example.projektjavaee.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {

    private ProductDAO productDAO;
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productDAO = mock(ProductDAO.class);
        productService = new ProductServiceImpl();
        productService.productDAO = productDAO; // wstrzyknięcie ręczne, zakładając brak setterów
    }

    @Test
    void testAddProduct() {
        Product p = new Product();
        p.setName("Monitor");
        productService.addProduct(p);
        verify(productDAO).create(p);
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productDAO.findAll()).thenReturn(products);
        List<Product> result = productService.getAllProducts();
        assertEquals(2, result.size());
        verify(productDAO).findAll();
    }

    @Test
    void testInitSampleProductsWhenEmpty() {
        when(productDAO.findAll()).thenReturn(Collections.emptyList());

        productService.initSampleProducts();

        verify(productDAO, times(3)).create(any(Product.class));
    }

    @Test
    void testInitSampleProductsWhenNotEmpty() {
        when(productDAO.findAll()).thenReturn(List.of(new Product())); // niepusta lista

        productService.initSampleProducts();

        verify(productDAO, never()).create(any(Product.class));
    }
}
