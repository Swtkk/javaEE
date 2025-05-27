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
<head><title>Panel Administratora</title></head>
<body>
<h2>Witaj, <%= user.getUsername() %> <%= user.getRole().getName() %></h2>
<p>Tu będą funkcje admina: zarządzanie produktami, użytkownikami itd.</p>
<a href="${pageContext.request.contextPath}/client/shop">Przejdź do sklepu</a>
<form action="${pageContext.request.contextPath}/populate-products" method="post">
    <input type="submit" value="Wstaw testowe produkty">
</form>
<c:if test="${param.success == 'true'}">
    <p style="color:green;">Produkty testowe zostały dodane!</p>
</c:if>

<a href="logout">Wyloguj się</a>
</body>
</html>
