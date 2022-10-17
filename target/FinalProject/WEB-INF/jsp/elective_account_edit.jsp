<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${app}/static/css/style_register.css">
    <title>Edit</title>
</head>
<body>
<main>
    <div class="framesignup">
        <div class="nav1">
            <ul class="links">
                <li class="signup-active"><a class="butn"><fmt:message key="elective.account.edit.profile"/></a></li>
            </ul>
        </div>

        <c:if test="${not empty requestScope.message}">
            <div class="errors">
                <p class="text-error"><fmt:message key="${requestScope.message}"/></p>
            </div>
        </c:if>

        <form class="form-signup" method="POST" action="${app}/elective/account/user/edit">
            <div class="part1">
                <div class="login">
                    <label for="login"><fmt:message key="registration.login"/></label>
                    <input type="hidden" name="id" value="${sessionScope.user.getId()}">
                    <input type="text" id="login" name="login" autofocus minlength="4" maxlength="30" required
                           class="form-styling form-styling-username" value="${sessionScope.user.getLogin()}">
                </div>
                <div class="email">
                    <label for="email"><fmt:message key="registration.email"/></label>
                    <input type="email" id="email" name="email" required
                           class="form-styling form-styling-email" value="${sessionScope.user.getEmail()}">
                </div>
            </div>
            <div class="part2">
                <div class="first_name">
                    <label for="first_name"><fmt:message key="registration.firstname"/></label>
                    <input type="text" id="first_name" name="first_name" minlength="4" maxlength="30" required
                           class="form-styling form-styling-part2" value="${sessionScope.user.getFirstName()}">
                </div>
                <div class="last_name">
                    <label for="last_name"><fmt:message key="registration.lastname"/></label>
                    <input type="text" id="last_name" name="last_name" minlength="4" maxlength="30" required
                           class="form-styling form-styling-part2" value="${sessionScope.user.getLastName()}">
                </div>
            </div>
            <div class="password">
                <div class="password1">
                    <label for="password"><fmt:message key="registration.password"/></label>
                    <input type="password" id="password" name="password" minlength="4" maxlength="30" required
                           class="form-styling form-styling-password">
                </div>
            </div>
            <div class="butn-animate">
                <button type="submit" class="butn-signup"><fmt:message key="elective.account.edit.update"/></button>
            </div>
            <div class="frame-end">
                <a class="login" href="${app}/login.jsp"> <fmt:message key="form.login.action"/> |</a>
                <a class="login" href="${app}/forgot_password_form.jsp"><fmt:message key="forgotPassword"/></a>
            </div>
        </form>
    </div>
</main>
</body>
</html>