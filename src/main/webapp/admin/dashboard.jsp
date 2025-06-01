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
    <title>Panel Administratora</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
</head>
<body>
<div class="container">
    <h2>Witaj, <%= user.getUsername() %> (<%= user.getRole().getName() %>)</h2>
    <p>Panel administracyjny: zarządzanie produktami, użytkownikami itd.</p>

    <a href="${pageContext.request.contextPath}/client/shop" class="button">Przejdź do sklepu</a>
    <a href="${pageContext.request.contextPath}/admin/addProduct" class="button">Dodaj produkt</a>

    <% if (user.getRole().getName().equals("ADMIN")) { %>
    <a href="${pageContext.request.contextPath}/admin/logs" class="button">Dziennik zdarzeń</a>
    <% } %>


    <form action="${pageContext.request.contextPath}/populate-products" method="post">
        <input type="submit" value="Wstaw testowe produkty">
    </form>

    <c:if test="${param.success == 'true'}">
        <p class="success-msg">Produkty testowe zostały dodane!</p>
    </c:if>

    <a href="${pageContext.request.contextPath}/logout" class="logout">Wyloguj się</a>
</div>
</body>
</html>
