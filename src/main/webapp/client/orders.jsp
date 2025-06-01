<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.projektjavaee.model.Order" %>
<%@ page import="org.example.projektjavaee.model.OrderItem" %>
<%@ page import="org.example.projektjavaee.model.Product" %>
<%@ page import="org.example.projektjavaee.model.User" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<%
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
%>

<%
    User user = (User) session.getAttribute("user");
    List<Order> orders = (List<Order>) request.getAttribute("orders");
%>

<html>
<head>
    <title>Moje zamówienia</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/orders.css">
</head>
<body>
<div class="container">
    <h2>Zamówienia użytkownika <%= user.getUsername() %></h2>

    <a href="shop" class="button">Powrót do sklepu</a>
    <a href="../logout" class="button logout">Wyloguj się</a>

    <%
        if (orders == null || orders.isEmpty()) {
    %>
    <p>Brak zamówień.</p>
    <%
    } else {
        for (Order o : orders) {
    %>
    <div class="order-box">
        <h4>Zamówienie z dnia: <%= dtf.format(o.getOrderDate()) %></h4>
        <ul>
            <%
                for (OrderItem item : o.getItems()) {
                    Product p = item.getProduct();
            %>
            <li><%= p.getName() %> — ilość: <%= item.getQuantity() %></li>
            <%
                }
            %>
        </ul>
    </div>
    <%
            }
        }
    %>
</div>
</body>
</html>
