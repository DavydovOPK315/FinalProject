<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${app}/static/css/style_login.css">
    <title>Reset form</title>
</head>
<body>
<main>
    <div class="frame">
        <div class="nav1">
            <ul class="links">
                <li class="login-active">
                    <c:choose>
                        <c:when test="${not empty requestScope.message}">
                            <div class="errors">
                                <a class="butn"><fmt:message key="${requestScope.message}"/></a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="errors">
                                <a class="butn"><fmt:message key="forgot.password.info"/></a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </li>
            </ul>
            <c:if test="${empty requestScope.error}">
                <form class="form-login" action="${app}/forgot_password" method="POST">
                    <label id="email"><fmt:message key="forgot.password.enter.mail"/></label>
                    <input type="email" name="email" class="form-styling" placeholder="Enter your e-mail" required autofocus
                           oninvalid="this.setCustomValidity('<fmt:message key="input.empty.warn"/>')"
                           oninput="this.setCustomValidity('')"/>
                    <div class="butn-animate">
                        <input type="submit" value="<fmt:message key="forgot.password.send"/>" class="butn-login"/>
                    </div>
                    <div class="frame-end">
                        <a class="sign-up" href="${app}/login.jsp"> <fmt:message key="form.login.action"/> </a> |
                        <a class="sign-up" href="${app}/registration.jsp"><fmt:message key="register"/></a>
                    </div>
                </form>
            </c:if>
            <c:if test="${not empty requestScope.error}">
                <div class="errors">
                    <p class="text-error"><fmt:message key="${requestScope.error}"/></p>
                </div>
            </c:if>
        </div>
    </div>
</main>
</body>
</html>