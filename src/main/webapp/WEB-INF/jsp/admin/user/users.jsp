<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>All users</title>
    <link rel="stylesheet" type="text/css" href="${app}/static/css/more_button.css">
    <link rel="stylesheet" type="text/css" href="${app}/static/css/admin.css">
</head>
<body>
<main>
    <p><a href="${app}/admin_page.jsp"><fmt:message key="admin.courses.admin.page"/></a></p>
    <hr/>
    <p><a href="${app}/admin/users/new"><fmt:message key="admin.users.add.new.user"/></a></p>
    <hr/>
    <p><a href="${app}/admin/users"><fmt:message key="admin.users.all.users"/></a></p>
    <hr/>
    <p><fmt:message key="elective.separate.course.status"/></p>
    <a href="${app}/admin/users/filter?key=status&value=ACTIVE"> ACTIVE </a>
    <a href="${app}/admin/users/filter?key=status&value=BANNED"> | BANNED</a>
    <br/>
    <hr/>
    <p><fmt:message key="admin.users.role"/>:</p>
    <a href="${app}/admin/users/filter?key=role&value=ADMIN"> ADMIN </a>
    <a href="${app}/admin/users/filter?key=role&value=STUDENT"> | STUDENT </a>
    <a href="${app}/admin/users/filter?key=role&value=TEACHER"> | TEACHER </a>
    <br/>
    <hr/>
    <br/>
    <form action="${app}/admin/users/filter" method="get">
        <input type="hidden" name="key" value="login" placeholder="login">
        <label for="login"> <fmt:message key="admin.users.find.user.by.login"/> </label>
        <input type="text" minlength="2" name="value" id="login" placeholder="login">
        <input type="submit" value="<fmt:message key="admin.users.find.user"/>">
    </form>
    <br/>
    <hr/>

    <c:if test="${not empty requestScope.message}">
        <h1><fmt:message key="${requestScope.message}"/></h1>
        <hr/>
    </c:if>

    <p><fmt:message key="admin.users.all.users"/>:</p>
    <table class="tg">
        <tr>
            <th width="120">ID</th>
            <th width="120"><fmt:message key="admin.users.login"/></th>
            <th width="120"><fmt:message key="admin.users.firstname"/></th>
            <th width="120"><fmt:message key="admin.users.lastname"/></th>
            <th width="120"><fmt:message key="admin.users.email"/></th>
            <th width="120"><fmt:message key="admin.users.created"/></th>
            <th width="120"><fmt:message key="admin.users.status"/></th>
            <th width="120"><fmt:message key="admin.users.role"/></th>
        </tr>
        <c:forEach items="${requestScope.userDtoList}" var="user">
            <tr class="all_items">
                <td>
                    <a href="${app}/admin/users/edit?id=${user.getId()}"> ${user.getId()} </a>
                </td>
                <td>${user.getLogin()}</td>
                <td>${user.getFirstName()}</td>
                <td>${user.getLastName()}</td>
                <td>${user.getEmail()}</td>
                <td>${user.getCreated()}</td>
                <td>${user.getStatus()}</td>
                <td>${user.getRole()}</td>
            </tr>
        </c:forEach>
    </table>
    <div class="button__more">
        <button id="btnMore"><fmt:message key="load.more"/></button>
    </div>
</main>
<script src="${app}/static/js/jquery/jquery-2.2.4.min.js"></script>
<script src="${app}/static/js/more.js"></script>
</body>
</html>