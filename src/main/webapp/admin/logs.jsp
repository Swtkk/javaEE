<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.example.projektjavaee.model.EventLog" %>
<%@ page import="org.example.projektjavaee.model.User" %>
<%@ page import="java.util.List" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole().getName())) {
        response.sendRedirect("../login.jsp");
        return;
    }

    List<EventLog> logs = (List<EventLog>) request.getAttribute("logs");
    String selectedFilter = request.getParameter("filter");
%>

<html>
<head>
    <title>Dziennik zdarzeń</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/logs.css">
</head>
<body>
<div class="container">
    <h2>Dziennik zdarzeń</h2>

    <form method="get" action="${pageContext.request.contextPath}/admin/logs">
        <label for="filter">Filtruj wg typu akcji:</label>
        <select name="filter" id="filter">
            <option value="" <%= (selectedFilter == null || selectedFilter.isEmpty()) ? "selected" : "" %>>Wszystkie</option>
            <option value="LOGIN_SUCCESS" <%= "LOGIN_SUCCESS".equalsIgnoreCase(selectedFilter) ? "selected" : "" %>>LOGIN</option>
            <option value="LOGOUT" <%= "LOGOUT".equalsIgnoreCase(selectedFilter) ? "selected" : "" %>>LOGOUT</option>
            <option value="ADD_PRODUCT" <%= "ADD_PRODUCT".equalsIgnoreCase(selectedFilter) ? "selected" : "" %>>ADD_PRODUCT</option>
            <option value="DELETE_PRODUCT" <%= "DELETE_PRODUCT".equalsIgnoreCase(selectedFilter) ? "selected" : "" %>>DELETE_PRODUCT</option>
            <option value="EDIT_PRODUCT" <%= "EDIT_PRODUCT".equalsIgnoreCase(selectedFilter) ? "selected" : "" %>>EDIT_PRODUCT</option>
            <option value="ORDER" <%= "ORDER".equalsIgnoreCase(selectedFilter) ? "selected" : "" %>>ORDER</option>
        </select>
        <input type="submit" value="Filtruj" />
    </form>

    <table>
        <thead>
        <tr>
            <th>Użytkownik</th>
            <th>Typ akcji</th>
            <th>Opis</th>
            <th>Czas</th>
        </tr>
        </thead>
        <tbody>
        <%
            if (logs != null && !logs.isEmpty()) {
                for (EventLog log : logs) {
        %>
        <tr>
            <td><%= log.getUsername() %></td>
            <td><%= log.getAction() %></td>
            <td><%= log.getDetails() %></td>
            <td><%= log.getTimestamp() %></td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="4" style="text-align:center; color:gray;">Brak logów do wyświetlenia.</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <a class="back-link" href="${pageContext.request.contextPath}/admin/dashboard.jsp">← Powrót do panelu</a>
</div>
</body>
</html>
