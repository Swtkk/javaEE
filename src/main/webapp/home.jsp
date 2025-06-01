<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.example.projektjavaee.model.User" %>
<%
    User user = (User) session.getAttribute("user");
%>
<html>
<head>
    <title>Strona główna</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
</head>
<body>
<div class="container">
    <h1>Witaj w sklepie internetowym</h1>

    <a href="login.jsp">Logowanie</a>
    <a href="register.jsp">Rejestracja</a>
    <a href="${pageContext.request.contextPath}/client/shop">Przejdź do sklepu</a>

    <% if (user != null) { %>
    <p>Zalogowano jako: <%= user.getUsername() %> (<%= user.getRole().getName() %>)</p>
    <a href="logout">Wyloguj się</a>
    <% } %>
</div>
</body>

</html>
