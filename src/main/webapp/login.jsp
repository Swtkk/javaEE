<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Logowanie</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
<div class="container">
    <h2>Logowanie</h2>
    <form method="post" action="login">
        <input type="text" name="username" placeholder="Login" required>
        <input type="password" name="password" placeholder="Hasło" required>
        <input type="submit" value="Zaloguj">
    </form>

    <a href="register.jsp" class="button-link">Zarejestruj się</a>

    <c:if test="${param.error == 'true'}">
        <p style="color:red">Niepoprawny login lub hasło!</p>
    </c:if>
</div>
</body>
</html>
