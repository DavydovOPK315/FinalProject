<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>All roles</title>
    <link rel="stylesheet" type="text/css" href="${app}/static/css/admin.css">
</head>
<body>
<main>
    <p><a href="${app}/admin_page.jsp"><fmt:message key="admin.courses.admin.page"/></a></p>
    <hr/>
    <br/>
    <p><a href="${app}/admin/roles/new"><fmt:message key="admin.roles.add.new.role"/></a></p>
    <hr/>
    <br/>

    <c:if test="${not empty requestScope.message}">
        <h1><fmt:message key="${requestScope.message}"/></h1>
        <hr/>
    </c:if>

    <p><fmt:message key="admin.roles.all"/>:</p>
    <table class="tg">
        <tr>
            <th width="120">ID</th>
            <th width="120"><fmt:message key="elective.account.name"/></th>
            <th width="120"><fmt:message key="elective.account.created"/></th>
            <th width="120"><fmt:message key="elective.account.status"/></th>
        </tr>
        <c:forEach items="${requestScope.roleList}" var="role">
            <tr>
                <td>
                    <a href="${app}/admin/roles/edit?id=${role.getId()}"> ${role.getId()} </a>
                </td>
                <td>${role.getName()}</td>
                <td>${role.getCreated()}</td>
                <td>${role.getStatus()} </td>
            </tr
        </c:forEach>
    </table>
</main>
</body>
</html>