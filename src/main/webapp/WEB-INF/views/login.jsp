<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Padfriender : Login</title>
</head>
<body>
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>.
<div content>
    <form name="f" action="login" method="post">
        <fieldset>
            <legend>Please Login</legend>
            <c:if test="${param.error}">
                Invalid username or password.
            </c:if>
            <c:if test="${param.logout}">
                You have been logged out.
            </c:if>
            <label for="username">Username</label>
            <input type="text" id="username" name="username"/>
            <label for="password">Password</label>
            <input type="password" id="password" name="password"/>
            <div class="form-actions">
                <button type="submit" class="btn">Log in</button>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>
