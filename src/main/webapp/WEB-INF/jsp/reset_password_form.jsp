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
<div>
    <c:choose>
        <c:when test="${requestScope.validLink}">
            <main>
                <div class="frame">
                    <div class="nav1">
                        <ul class="links">
                            <li class="login-active"><a class="butn"><fmt:message key="reset.password.info"/></a></li>
                        </ul>
                    </div>
                    <div>
                        <c:if test="${requestScope.message}">
                            <div class="errors">
                                <p class="text-error"><fmt:message key="${requestScope.message}"/></p>
                            </div>
                        </c:if>
                        <form class="form-login" action="${app}/reset_password" method="POST" name="form">
                            <input type="hidden" name="token" value="${requestScope.token}"/>
                            <label for="password"><fmt:message key="reset.password.new.password"/></label>
                            <input type="password" name="password" id="password" class="form-styling"
                                   placeholder="Enter your new password" minlength="4" required autofocus/>
                            <label for="password"><fmt:message key="reset.password.confirm.password"/></label>
                            <input type="password" name="password" class="form-styling"
                                   placeholder="Confirm your new password" minlength="4"
                                   required oninput="checkPasswordMatch(this);"/>
                            <div class="butn-animate">
                                <input type="submit" value="<fmt:message key="reset.password.change.password"/>" class="butn-login"/>
                            </div>
                            <div class="frame-end">
                                <a class="sign-up" href="${app}/login.jsp"> <fmt:message key="form.login.action"/> </a> |
                                <a class="sign-up" href="${app}/registration.jsp"><fmt:message key="register"/></a>
                            </div>
                        </form>
                    </div>
                </div>
            </main>
        </c:when>
        <c:otherwise>
            <h1><fmt:message key="reset.password.fail.reset"/></h1>
            <p><fmt:message key="reset.password.invalid.link"/></p>
        </c:otherwise>
    </c:choose>
</div>
<script>
    sessionStorage.setItem('lang', '${sessionScope.lang}')
    sessionStorage.setItem('value', '<fmt:message key="reset.password.match"/>')
</script>
<script src="${app}/static/js/checkPasswordMatch.js"></script>
</body>
</html>