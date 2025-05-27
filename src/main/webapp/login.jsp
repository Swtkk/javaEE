<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Logowanie</title></head>
<body>
<h2>Logowanie</h2>
<form method="post" action="login">
    Login: <input type="text" name="username" required><br>
    Hasło: <input type="password" name="password" required><br>
    <input type="submit" value="Zaloguj">
</form>
<button>
    <a href="register.jsp">Zarejestruj sie</a>

</button>
<c:if test="${param.error == 'true'}">
    <p style="color:red">Niepoprawny login lub hasło!</p>
</c:if>
</body>
</html>
