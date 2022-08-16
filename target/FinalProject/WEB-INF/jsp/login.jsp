<%--<%@ include file="/WEB-INF/jspf/taglib.jspf" %>--%>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%--<%@ include file="/WEB-INF/jspf/header.jspf" %>--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/style_login.css"/>">
    <title>Login</title>
</head>
<body>
<header>
    <div class="content">
        <a href="<%=request.getContextPath()+"/main.jsp"%>"><img src="<c:url value="/static/css/photo/Gameshop.png"/>"
                                                                 alt="Logo" class="header__logo"/></a>
        <div class="menu">
            <div class="icon-close">
                <img src="<c:url value="/static/css/photo/x.png"/>">
            </div>
        </div>
        <div class="icon-menu">
            <img src="<c:url value="/static/css/photo/Hamburger.png"/>" height="40">
        </div>
    </div>
</header>
<main>
    <div class="frame">
        <div class="nav1">
            <ul class="links">
                <li class="login-active"><a class="butn">Log in</a></li>
            </ul>
        </div>
        <div>
            <c:if test="${not empty requestScope.message}">
                <div class="errors">
                    <p class="text-error">${requestScope.message}</p>
                </div>
            </c:if>
            <form class="form-login" action="<%=request.getContextPath()+"/login"%>" method="POST">
                <label for="login">Login: </label>
                <input type="text" id="login" name="login" autofocus required size="30" minlength="4"
                       class="form-styling"/>
                <label for="password">Enter password: </label>
                <input type="password" id="password" name="password" required size="30" minlength="4"
                       class="form-styling">
                <div class="butn-animate">
                    <button type="submit" class="butn-login">Log in</button>
                </div>
                <div class="frame-end">
                    <a class="sign-up" href="<%=request.getContextPath()+"/registration"%>">Register</a> |
                    <a class="sign-up" href="<%=request.getContextPath()+"/forgot_password_form.jsp"%>">Forgot password</a>
                </div>
            </form>
        </div>
    </div>
</main>
</body>
</html>