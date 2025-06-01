<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.example.projektjavaee.model.User" %>
<%@ page import="org.example.projektjavaee.model.Product" %>
<%@ page import="java.util.List" %>

<%
    User user = (User) session.getAttribute("user");
    List<Product> products = (List<Product>) request.getAttribute("products");
%>

<html>

<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shop.css">

    <title>Sklep klienta</title></head>
<body>
<div class="container">

    <h2>Witaj, <%= user.getUsername() %> (<%= user.getRole().getName() %>)</h2>

    <% if (user.getRole().getName().equals("ADMIN") || user.getRole().getName().equals("WORKER")) { %>
    <a href="${pageContext.request.contextPath}/admin/dashboard.jsp" class="button-link">Powrót</a>
    <% } %>

    <a href="${pageContext.request.contextPath}/client/orders" class="button-link">Zobacz moje zamówienia</a>
    <a href="cart.jsp" class="button-link">Zobacz koszyk</a>
    <a href="../logout" class="button-link logout">Wyloguj się</a>

    <%
        String flash = (String) session.getAttribute("flash");
        if (flash != null) {
    %>
    <p class="flash"><%= flash %></p>
    <%
            session.removeAttribute("flash");
        }
    %>

    <h3>Dostępne produkty:</h3>
    <table>
        <tr><th>Nazwa</th><th>Opis</th><th>Cena</th><th>Akcja</th></tr>
        <% for (Product p : products) { %>
        <tr>
            <td><%= p.getName() %></td>
            <td><%= p.getDescription() %></td>
            <td><%= p.getPrice() %> zł</td>
            <td>
                <form action="${pageContext.request.contextPath}/add-to-cart" method="post" style="display:inline;">
                    <input type="hidden" name="productId" value="<%= p.getId() %>">
                    <input type="submit" value="Dodaj do koszyka">
                </form>

                <% if (user.getRole().getName().equals("ADMIN") || user.getRole().getName().equals("WORKER")) { %>

                <form action="${pageContext.request.contextPath}/client/shop" method="get" style="display:inline;">
                    <input type="hidden" name="action" value="edit">
                    <input type="hidden" name="productId" value="<%= p.getId() %>">
                    <input type="submit" value="Edytuj">
                </form>

                <form action="${pageContext.request.contextPath}/client/shop" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="productId" value="<%= p.getId() %>">
                    <input type="submit" value="Usuń"
                        <%= user.getRole().getName().equals("WORKER") ? "disabled" : "" %>>
                </form>

                <% } %>
            </td>
        </tr>
        <% } %>
    </table>

</div>
</body>

</html>
