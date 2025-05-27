<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Rejestracja</title></head>
<body>
<h2>Rejestracja</h2>
<form method="post" action="register">
    Nazwa użytkownika: <input type="text" name="username" required><br>
    Hasło: <input type="password" name="password" required><br>
    Rola:
    <select name="role">
        <option value="CLIENT">Klient</option>
        <option value="WORKER">Pracownik</option>
        <option value="ADMIN">Administrator</option>
    </select><br>
    <input type="submit" value="Zarejestruj">
</form>
<c:if test="${not empty error}">
    <p style="color:red">${error}</p>
</c:if>
<a href="login.jsp">Masz już konto? Zaloguj się</a>
</body>
</html>
