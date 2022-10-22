<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit role</title>
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
    <p><a href="${app}/admin/roles/new"><fmt:message key="admin.roles.add.new.role"/></a></p>
    <hr/>
    <br/>

    <c:if test="${not empty requestScope.message}">
        <h1><fmt:message key="${requestScope.message}"/></h1>
        <hr/>
    </c:if>

    <p><fmt:message key="admin.users.role"/>:</p>
    <table class="tg">
        <tr>
            <th width="120">ID</th>
            <th width="120"><fmt:message key="elective.account.name"/></th>
            <th width="120"><fmt:message key="elective.account.created"/></th>
            <th width="120"><fmt:message key="elective.account.status"/></th>
            <th width="120"><fmt:message key="elective.account.action"/> 1</th>
            <th width="120"><fmt:message key="elective.account.action"/> 2</th>
        </tr>
        <tr>
            <form action="${app}/admin/roles/edit" method="post">
                <td><input type="hidden" name="id" value="${requestScope.role.getId()}">
                    ${requestScope.role.getId()}
                </td>
                <td><input type="text" id="name" name="name" value="${requestScope.role.getName()}" minlength="3"
                           maxlength="25" required>
                </td>
                <td>${requestScope.role.getCreated()}</td>
                <td>
                    <select name="status" required>
                        <c:forEach items="${requestScope.statuses}" var="status">
                            <c:choose>
                                <c:when test="${status.name() == requestScope.role.getStatus().name()}">
                                    <option value="${status.name()}" selected>${status.name()}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${status.name()}">${status.name()}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
                <td><input type="submit" value="<fmt:message key="admin.role.edit.update.role"/>"></td>
            </form>
            <form action="${app}/admin/roles/delete?id=${requestScope.role.getId()}" method="post">
                <td><input type="submit" value="<fmt:message key="admin.role.edit.delete.role"/>"></td>
            </form>
        </tr>
    </table>
</main>
</body>
</html>