<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.example.projektjavaee.model.User" %>
<%@ page import="org.example.projektjavaee.model.Product" %>
<%@ page import="java.util.List" %>

<%
    User user = (User) session.getAttribute("user");
    List<Product> products = (List<Product>) request.getAttribute("products");
%>

<html>

<head><title>Sklep klienta</title></head>
<body>
<h2>Witaj, <%= user.getUsername() %> (KLIENT)</h2>

<a href="${pageContext.request.contextPath}/client/orders">Zobacz moje zamówienia</a>
<%
    String flash = (String) session.getAttribute("flash");
    if (flash != null) {
%>
<p style="color:green;"><%= flash %></p>
<%
        session.removeAttribute("flash");
    }
%>


<a href="../logout">Wyloguj się</a> |
<a href="cart.jsp">Zobacz koszyk</a>

<h3>Dostępne produkty:</h3>
<table border="1">
    <tr><th>Nazwa</th><th>Opis</th><th>Cena</th><th>Akcja</th></tr>
    <%
        for (Product p : products) {
    %>
    <tr>
        <td><%= p.getName() %></td>
        <td><%= p.getDescription() %></td>
        <td><%= p.getPrice() %> zł</td>
        <td>
            <form action="${pageContext.request.contextPath}/add-to-cart" method="post">
            <input type="hidden" name="productId" value="<%= p.getId() %>">
                <input type="submit" value="Dodaj do koszyka">
            </form>
        </td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
