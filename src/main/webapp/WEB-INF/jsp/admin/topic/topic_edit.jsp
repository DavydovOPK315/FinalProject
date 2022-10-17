<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit topic</title>
    <link rel="stylesheet" type="text/css" href="${app}/static/css/admin.css">
</head>
<body>
<main>
    <p><a href="${app}/admin_page.jsp"><fmt:message key="admin.courses.admin.page"/></a></p>
    <hr/>
    <br/>
    <p><a href="${app}/admin/topics"><fmt:message key="admin.topics.all"/></a></p>
    <hr/>
    <br/>
    <p><a href="${app}/admin/topics/new"><fmt:message key="admin.topics.add.new.topic"/></a></p>
    <hr/>
    <br/>

    <c:if test="${not empty requestScope.message}">
        <h1><fmt:message key="${requestScope.message}"/></h1>
        <hr/>
    </c:if>

    <p><fmt:message key="elective.courses.topic"/>:</p>
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
            <form action="${app}/admin/topics/edit" method="post">
                <td><input type="hidden" name="id" value="${requestScope.topic.getId()}">
                    ${requestScope.topic.getId()}
                </td>
                <td><input type="text" id="name" name="name" value="${requestScope.topic.getName()}" minlength="3"
                           maxlength="25" required>
                </td>
                <td>${requestScope.topic.getCreated()}</td>
                <td>
                    <select name="status" required>
                        <c:forEach items="${requestScope.statuses}" var="status">
                            <c:choose>
                                <c:when test="${status.name() == requestScope.topic.getStatus().name()}">
                                    <option value="${status.name()}" selected>${status.name()}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${status.name()}">${status.name()}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
                <td><input type="submit" value="<fmt:message key="admin.topic.edit.update.topic"/>"></td>
            </form>
            <form action="${app}/admin/topics/delete?id=${requestScope.topic.getId()}" method="post">
                <td><input type="submit" value="<fmt:message key="admin.topic.edit.delete.topic"/>"></td>
            </form>
        </tr>
    </table>
</main>
</body>
</html>