<%--<%@ include file="/WEB-INF/jspf/taglib.jspf" %>--%>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%--<%@ include file="/WEB-INF/jspf/header.jspf" %>--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/style_login.css"/>">
    <title>Reset form</title>
</head>
<body>
<header>
    <div class="content">
        <a href="<%=request.getContextPath()+"/main.jsp"%>">
            <img src="<c:url value="/static/css/photo/Gameshop.png"/>" alt="Logo" class="header__logo"/>
        </a>
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
                <li class="login-active">
                    <c:choose>
                        <c:when test="${not empty requestScope.message}">
                            <a class="butn">${requestScope.message}</a>
                        </c:when>
                        <c:otherwise>
                            <a class="butn">Will be sending a reset password link to your email</a>
                        </c:otherwise>
                    </c:choose>
                </li>
            </ul>
            <div>
                <c:choose>
                    <c:when test="${not empty requestScope.error}">
                        <p>${requestScope.error}</p>
                    </c:when>
                    <c:otherwise>

                        <form class="form-login" action="<%=request.getContextPath()+"/resetPassword"%>" method="POST">
                            <label id="email">Enter your email</label>
                            <input type="email" name="email" class="form-styling" placeholder="Enter your e-mail" required autofocus/>
                            <div class="butn-animate">
                                <input type="submit" value="Send" class="butn-login"/>
                            </div>
                            <div class="frame-end">
                                <a class="sign-up" href="<%=request.getContextPath()+"/login.jsp"%>">Log in</a> |
                                <a class="sign-up" href="<%=request.getContextPath()+"/registration.jsp"%>">Register</a>
                            </div>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</main>
</body>
</html>