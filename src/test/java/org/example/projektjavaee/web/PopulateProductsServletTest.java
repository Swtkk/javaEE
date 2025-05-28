package org.example.projektjavaee.web;

import jakarta.servlet.http.*;
import org.example.projektjavaee.service.ProductService;
import org.example.projektjavaee.web.PopulateProductsServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

class PopulateProductsServletTest {

    private PopulateProductsServlet servlet;
    private ProductService productService;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new PopulateProductsServlet();
        productService = mock(ProductService.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        var field = PopulateProductsServlet.class.getDeclaredField("productService");
        field.setAccessible(true);
        field.set(servlet, productService);
    }

    @Test
    void testDoPostCallsServiceAndRedirects() throws IOException {
        servlet.doPost(request, response);

        verify(productService).initSampleProducts();
        verify(response).sendRedirect("admin/dashboard.jsp?success=true");
    }

    @Test
    void testDoGetReturns405() throws IOException {
        servlet.doGet(request, response);

        verify(response).sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST only");
    }
}
