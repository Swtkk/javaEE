<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.example.projektjavaee.model.Product" %>
<%@ page import="java.util.Map" %>
<%
    Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
%>
<html>
<head><title>Twój koszyk</title></head>
<body>
<h2>Koszyk</h2>
<a href="shop">Powrót do sklepu</a> |
<a href="../logout">Wyloguj się</a>

<%
    if (cart == null || cart.isEmpty()) {
%>
<p>Twój koszyk jest pusty.</p>
<%
} else {
    double total = 0;
%>
<table border="1">
    <tr><th>Produkt</th><th>Ilość</th><th>Cena</th><th>Suma</th></tr>
    <%
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            double sum = qty * p.getPrice();
            total += sum;
    %>
    <tr>
        <td><%= p.getName() %></td>
        <td><%= qty %></td>
        <td><%= p.getPrice() %> zł</td>
        <td><%= sum %> zł</td>
    </tr>
    <%
        }
    %>
    <tr><td colspan="3">Razem</td><td><strong><%= total %> zł</strong></td></tr>
</table>

<form method="post" action="../order">
    <label>Adres e-mail do potwierdzenia:</label><br>
    <input type="email" name="email" required><br><br>
    <input type="submit" value="Złóż zamówienie">
</form>
<%
    }
%>
</body>
</html>
