<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${app}/static/css/style_login.css">
    <title>Login</title>
    <script src='https://www.google.com/recaptcha/api.js?hl=en'></script>
</head>
<body>
<main>
    <div class="frame">
        <div class="nav1">
            <ul class="links">
                <li class="login-active"><a class="butn"><fmt:message key="form.login"/></a></li>
            </ul>
        </div>
        <div>
            <c:if test="${not empty requestScope.message}">
                <div class="errors">
                    <p class="text-error"><fmt:message key="${requestScope.message}"/></p>
                </div>
            </c:if>
            <form class="form-login" action="${app}/login" method="POST">
                <label for="login"><fmt:message key="login"/> </label>
                <input type="text" id="login" name="login" autofocus minlength="4" maxlength="30" required
                       class="form-styling" oninvalid="this.setCustomValidity('<fmt:message key="input.empty.warn"/>')"/>
                <label for="password"><fmt:message key="password"/> </label>
                <input type="password" id="password" name="password" minlength="4" maxlength="30" required
                       class="form-styling" oninvalid="this.setCustomValidity('<fmt:message key="input.empty.warn"/>')">

                <div class="g-recaptcha" data-sitekey="6Ld50h0iAAAAACdkS2bqKN9zYFnBQwppW4uGxc6y"></div>
                <br/>
                <div class="butn-animate">
                    <button type="submit" class="butn-login"><fmt:message key="form.login.action"/></button>
                </div>
                <div class="frame-end">
                    <a class="sign-up" href="${app}/registration.jsp"><fmt:message key="register"/> |</a>
                    <a class="sign-up" href="${app}/forgot_password_form.jsp"><fmt:message key="forgotPassword"/> </a>
                </div>
            </form>
        </div>
    </div>
</main>
</body>
</html>