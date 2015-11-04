<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Padfriender : Signup</title>
</head>
<body>
<div content>
    <form name="signup" action="signup" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <fieldset>
        <legend>Sign up</legend>

        <label for="email">E-mail Address*</label>
        <input type="email" id="email" name="email" maxlength="50"><br/>

        <label for="id">Puzzle and Dragons ID (no commas or spaces)*</label>
        <input type="text" id="id" name="id" maxlength="9"><br/>

        <label for="name">In-game name*</label>
        <input type="name" id="name" name="name" maxlength="50"><br/>

        <label for="password">Password*</label>
        <input type="password" id="password" name="password"><br/>

        <label for="password_confirm">Confirm Password*</label>
        <input type="password" id="password_confirm" name="password_confirm"><br/>

        <div class="form-actions">
            <button type="submit" class="btn">Sign up</button>
        </div>
    </fieldset>
  </form>
</div>
</body>
</html>