<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.projektjavaee.model.Order" %>
<%@ page import="org.example.projektjavaee.model.OrderItem" %>
<%@ page import="org.example.projektjavaee.model.Product" %>
<%@ page import="org.example.projektjavaee.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    List<Order> orders = (List<Order>) request.getAttribute("orders");
%>
<html>
<head><title>Moje zamówienia</title></head>
<body>
<h2>Zamówienia użytkownika <%= user.getUsername() %></h2>

<a href="shop">Powrót do sklepu</a> | <a href="../logout">Wyloguj się</a>

<%
    if (orders == null || orders.isEmpty()) {
%>
<p>Brak zamówień.</p>
<%
} else {
%>
<ul>
    <%
        for (Order o : orders) {
    %>
    <li>
        <strong>Zamówienie z dnia:</strong> <%= o.getOrderDate() %>
        <ul>
            <%
                for (OrderItem item : o.getItems()) {
                    Product p = item.getProduct();
            %>
            <li><%= p.getName() %> - ilość: <%= item.getQuantity() %></li>
            <%
                }
            %>
        </ul>
    </li>
    <%
        }
    %>
</ul>
<%
    }
%>
</body>
</html>
