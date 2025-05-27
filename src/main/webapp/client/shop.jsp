<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.example.projektjavaee.model.User" %>
<%
    User user = (User) session.getAttribute("user");
%>
<html>
<head><title>Sklep klienta</title></head>
<body>
<h2>Witaj, <%= user.getUsername() %> (KLIENT)</h2>
<p>Tu będzie koszyk, zamówienia, lista produktów itd.</p>
<a href="../logout">Wyloguj się</a>
</body>
</html>
