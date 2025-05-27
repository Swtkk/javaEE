<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.example.projektjavaee.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole().getName())) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>
<html>
<head><title>Panel Administratora</title></head>
<body>
<h2>Witaj, <%= user.getUsername() %> (ADMIN)</h2>
<p>Tu będą funkcje admina: zarządzanie produktami, użytkownikami itd.</p>
<a href="${pageContext.request.contextPath}/client/shop.jsp">Przejdź do sklepu</a>
<a href="logout">Wyloguj się</a>
</body>
</html>
