<%--<%@ include file="/WEB-INF/jspf/taglib.jspf" %>--%>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%--<%@ include file="/WEB-INF/jspf/header.jspf" %>--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/style_register.css"/>">
    <title>Registration</title>
</head>
<body>
<header>
    <div class="content">
        <a href="<%=request.getContextPath()+"/main.jsp"%>"><img src="<c:url value="/static/css/photo/Gameshop.png"/>"
                                                                 alt="Логотип"
                                                                 class="header__logo"/></a>
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
    <div class="framesignup">
        <div class="nav1">
            <ul class="links">
                <li class="signup-active"><a class="butn">Sign up</a></li>
            </ul>
        </div>
        <%--        <div class="errors" th:if="${formError}">--%>
        <%--            <p class="text-error" th:text="${formError}"></p>--%>
        <%--        </div>--%>

        <form class="form-signup" method="POST" action="<%=request.getContextPath()+"/registration"%>">
            <div class="part1">
                <div class="login">
                    <label for="login">Login: </label>
                    <input type="text" id="login" name="login" autofocus required size="30" minlength="4"
                           class="form-styling form-styling-username">
                </div>
                <div class="email">
                    <label for="email">Email: </label>
                    <input type="email" id="email" name="email" required size="30" minlength="4"
                           class="form-styling form-styling-email">
                </div>
            </div>
            <div class="part2">
                <div class="first_name">
                    <label for="first_name">First name: </label>
                    <input type="text" id="first_name" name="first_name" required size="30" minlength="4"
                           class="form-styling form-styling-part2">
                </div>
                <div class="last_name">
                    <label for="last_name">Enter last name: </label>
                    <input type="text" id="last_name" name="last_name" required size="30" minlength="4"
                           class="form-styling form-styling-part2">
                </div>
            </div>
            <div class="password">
                <div class="password1">
                    <label for="password">Enter password: </label>
                    <input type="password" id="password" name="password" required size="30" minlength="4"
                           class="form-styling form-styling-password">
                </div>
            </div>
            <div class="butn-animate">
                <button type="submit" class="butn-signup">Register</button>
            </div>
            <div class="frame-end">
                <a class="login" href="<%=request.getContextPath()+"/login.jsp"%>"> Log in | </a>
                <a class="login" href="<%=request.getContextPath()+"/forgot_password_form.jsp"%>"> Forgot password </a>
            </div>
        </form>
    </div>
</main>
</body>
</html>