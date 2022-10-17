<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add user</title>
    <link rel="stylesheet" type="text/css" href="${app}/static/css/admin.css">
</head>
<body>
<main>
    <p><a href="${app}/admin_page.jsp"><fmt:message key="admin.courses.admin.page"/></a></p>
    <hr/>
    <br/>
    <p><a href="${app}/admin/users"><fmt:message key="admin.users.add.new.user"/></a></p>
    <hr/>
    <br/>

    <c:if test="${not empty requestScope.message}">
        <h1><fmt:message key="${requestScope.message}"/></h1>
        <hr/>
    </c:if>

    <p><fmt:message key="admin.users.add.user"/></p>
    <form action="${app}/admin/users/new" method="post">
        <table class="tg">
            <tr>
                <th width="120">ID</th>
                <th width="120"><fmt:message key="admin.users.login"/></th>
                <th width="120"><fmt:message key="admin.users.firstname"/></th>
                <th width="120"><fmt:message key="admin.users.lastname"/></th>
                <th width="120"><fmt:message key="admin.users.email"/></th>
                <th width="120"><fmt:message key="admin.users.password"/></th>
                <th width="120"><fmt:message key="admin.users.created"/></th>
                <th width="120"><fmt:message key="admin.users.status"/></th>
                <th width="120"><fmt:message key="admin.users.role"/></th>
                <th width="120"><fmt:message key="elective.account.action"/></th>
            </tr>
            <tr>
                <td>default</td>
                <td><input type="text" name="login" minlength="4" maxlength="30" required></td>
                <td><input type="text" name="first_name" minlength="4" maxlength="30" required></td>
                <td><input type="text" name="last_name" minlength="4" maxlength="30" required></td>
                <td><input type="email" name="email" required></td>
                <td><input type="password" name="password" minlength="4" maxlength="30" required></td>
                <td>${requestScope.date}</td>
                <td>
                    <select name="status" required>
                        <c:forEach items="${requestScope.statuses}" var="status">
                            <option value="${status.name()}">${status.name()}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <select name="role" required>
                        <c:forEach items="${requestScope.roles}" var="role">
                            <option value="${role.getId()}">${role.getName()}</option>
                        </c:forEach>
                    </select>
                </td>
                <td><input type="submit" value="<fmt:message key="admin.users.add.user"/>"></td>
            </tr>
        </table>
    </form>
</main>
</body>
</html>