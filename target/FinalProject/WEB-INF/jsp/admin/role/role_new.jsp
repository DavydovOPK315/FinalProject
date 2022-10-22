<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add role</title>
    <link rel="stylesheet" type="text/css" href="${app}/static/css/admin.css">
</head>
<body>
<main>
    <p><a href="${app}/admin_page.jsp"><fmt:message key="admin.courses.admin.page"/></a></p>
    <hr/>
    <br/>
    <p><a href="${app}/admin/roles"><fmt:message key="admin.roles.all"/> </a></p>
    <hr/>
    <br/>

    <c:if test="${not empty requestScope.message}">
        <h1><fmt:message key="${requestScope.message}"/></h1>
        <hr/>
    </c:if>

    <p><fmt:message key="admin.role.new.new.role"/></p>
    <table class="tg">
        <tr>
            <th width="120">ID</th>
            <th width="120"><fmt:message key="elective.account.name"/></th>
            <th width="120"><fmt:message key="elective.account.created"/></th>
            <th width="120"><fmt:message key="elective.account.status"/></th>
            <th width="120"><fmt:message key="elective.account.action"/></th>
        </tr>
        <form action="${app}/admin/roles/new" method="post">
            <tr>
                <td>default</td>
                <td><input type="text" id="name" name="name" minlength="3" maxlength="25" required></td>
                <td>${requestScope.date}</td>
                <td>
                    <select name="status" required>
                        <c:forEach items="${requestScope.statuses}" var="status">
                            <option value="${status.name()}">${status.name()}</option>
                        </c:forEach>
                    </select>
                </td>
                <td><input type="submit" value="<fmt:message key="admin.roles.add.new.role"/>"></td>
            </tr>
        </form>
    </table>
</main>
</body>
</html>