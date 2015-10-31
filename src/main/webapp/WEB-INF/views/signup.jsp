<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Padfriender : Login</title>
</head>
<body>
<div content>
    <form name="signup" action="signup" method="post">
    <fieldset>
        <legend>Sign up</legend>

        <input type="email" id="email" name="email">
        <label for="email">E-mail Address</label>

        <input type="password" id="password" name="password">
        <label for="password">Password</label>

        <input type="password" id="password_confirm" name="password_confirm">
        <label for="password_confirm">Password</label>

        <div class="form-actions">
            <button type="submit" class="btn">Log in</button>
        </div>
    </fieldset>
  </form>
</div>
</body>
</html>