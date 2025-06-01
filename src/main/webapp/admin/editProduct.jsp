<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.example.projektjavaee.model.Product" %>
<%@ page import="org.example.projektjavaee.model.User" %>
<%
    Product product = (Product) request.getAttribute("product");
%>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || (!"ADMIN".equalsIgnoreCase(user.getRole().getName()) && !"WORKER".equalsIgnoreCase(user.getRole().getName()))) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>
<html>
<head>
    <title>Edytuj Produkt</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/editProduct.css">
</head>
<body>
<div class="container">
    <h2>Edytuj produkt</h2>

    <a href="${pageContext.request.contextPath}/client/shop" class="button">Powrót do sklepu</a>

    <form method="post" action="${pageContext.request.contextPath}/client/shop" class="form">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="<%= product.getId() %>">

        <label>Nazwa:</label>
        <input type="text" name="name" value="<%= product.getName() %>" required>

        <label>Opis:</label>
        <textarea name="description" rows="4" required><%= product.getDescription() %></textarea>

        <label>Cena:</label>
        <input type="number" name="price" step="0.01" value="<%= product.getPrice() %>" required>

        <input type="submit" value="Zapisz zmiany" class="btn-green">
    </form>
</div>
</body>
</html>
