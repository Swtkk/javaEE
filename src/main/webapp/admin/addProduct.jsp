<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.example.projektjavaee.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || (!"ADMIN".equalsIgnoreCase(user.getRole().getName()) && !"WORKER".equalsIgnoreCase(user.getRole().getName()))) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>
<html>
<head>
    <title>Dodaj Produkt</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addProduct.css">
</head>
<body>
<div class="container">
    <h2>Dodaj nowy produkt</h2>
    <form action="${pageContext.request.contextPath}/admin/addProduct" method="post">
        <label>Nazwa:</label>
        <input type="text" name="name" required>

        <label>Opis:</label>
        <textarea name="description" required></textarea>

        <label>Cena:</label>
        <input type="number" step="0.01" name="price" required>

        <input type="submit" value="Dodaj produkt">
    </form>

    <a href="${pageContext.request.contextPath}/admin/dashboard.jsp" class="button">Powrót</a>

    <% if (request.getAttribute("success") != null) { %>
    <p class="success-msg">Produkt został dodany pomyślnie!</p>
    <% } %>
</div>
</body>
</html>
