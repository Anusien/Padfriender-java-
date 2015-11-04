<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Padfriender : Login</title>
</head>
<body>
<div content>
    <form name="login" action="login" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <fieldset>
            <legend>Please Login</legend>
            <c:if test="${param.error}">
                Invalid username or password.
            </c:if>
            <c:if test="${param.logout}">
                You have been logged out.
            </c:if>
            <label for="email">E-mail Address</label>
            <input type="email" id="email" name="email"/>

            <label for="password">Password</label>
            <input type="password" id="password" name="password"/>

            <div class="form-actions">
                <button type="submit" class="btn">Log in</button>
            </div>
        </fieldset>
    </form>
    <a href="signup">Sign up instead</a>
</div>
</body>
</html>
