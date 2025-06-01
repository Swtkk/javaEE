<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.example.projektjavaee.model.Product" %>
<%@ page import="java.util.Map" %>

<%
    Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
    String flash = (String) session.getAttribute("flash");
    if (flash != null) {
%>
<p style="color:green; font-weight:bold; text-align:center;"><%= flash %></p>
<%
        session.removeAttribute("flash");
    }
%>

<html>
<head>
    <title>Twój koszyk</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart.css">
</head>
<body>
<div class="container">
    <h2>Koszyk</h2>
    <a href="shop" class="button">Powrót do sklepu</a> |
    <a href="../logout" class="button logout">Wyloguj się</a>

    <%
        if (cart == null || cart.isEmpty()) {
    %>
    <p>Twój koszyk jest pusty.</p>
    <%
    } else {
        double total = 0;
    %>
    <table>
        <tr><th>Produkt</th><th>Ilość</th><th>Cena</th><th>Suma</th><th>Akcja</th></tr>
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
            <td>
                <form method="post" action="../order" style="display:inline;">
                    <input type="hidden" name="action" value="remove">
                    <input type="hidden" name="productId" value="<%= p.getId() %>">
                    <input type="submit" value="Usuń" class="btn-red">
                </form>
            </td>
        </tr>
        <%
            }
        %>
        <tr>
            <td colspan="3"><strong>Razem</strong></td>
            <td><strong><%= total %> zł</strong></td>
            <td></td>
        </tr>
    </table>

    <form method="post" action="../order">
        <label>Adres e-mail do potwierdzenia:</label><br>
        <input type="email" name="email" required><br><br>
        <input type="submit" value="Złóż zamówienie">
    </form>
    <%
        }
    %>
</div>
</body>
</html>
