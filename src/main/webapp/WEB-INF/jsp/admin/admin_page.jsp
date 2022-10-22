<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${app}/static/css/admin.css">
    <title>Admin page</title>
</head>
<body>
<main>
    <h1><fmt:message key="admin.courses.admin.page"/></h1>
    <h2><fmt:message key="account.hello"/>, <mylib:fullname user="${sessionScope.user}" userDto=""/></h2>
    <div class="admin_welcome_page">
        <table class="tg">
            <tr>
                <th width="120"><fmt:message key="admin.page.action"/> 1</th>
                <th width="120"><fmt:message key="admin.page.action"/> 2</th>
                <th width="120"><fmt:message key="admin.page.action"/> 3</th>
                <th width="120"><fmt:message key="admin.page.action"/> 4</th>
            </tr>
            <tr>
                <td><a href="${app}/admin/roles"><fmt:message key="admin.page.roles"/></a></td>
                <td><a href="${app}/admin/users"><fmt:message key="admin.page.users"/></a></td>
                <td><a href="${app}/admin/courses"><fmt:message key="admin.page.courses"/></a></td>
                <td><a href="${app}/admin/topics"><fmt:message key="admin.page.topics"/></a></td>
            </tr>
        </table>
    </div>
</main>
</body>
</html>