<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Rejestracja</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register.css">
</head>
<body>
<div class="container">
    <h2>Rejestracja</h2>
    <form method="post" action="register">
        <input type="text" name="username" placeholder="Nazwa użytkownika" required>
        <input type="password" name="password" placeholder="Hasło" required>
        <select name="role" required>
            <option value="CLIENT">Klient</option>
            <option value="WORKER">Pracownik</option>
            <option value="ADMIN">Administrator</option>
        </select>
        <input type="submit" value="Zarejestruj">
    </form>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>

    <a href="login.jsp">Masz już konto? Zaloguj się</a>
</div>
</body>
</html>
