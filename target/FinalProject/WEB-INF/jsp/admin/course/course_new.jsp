<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="uk">
<head>
    <title>Add course</title>
    <link rel="stylesheet" type="text/css" href="${app}/static/css/admin.css">
</head>
<body>
<main>
    <p><a href="${app}/admin_page.jsp"><fmt:message key="admin.courses.admin.page"/></a></p>
    <hr/>
    <br/>
    <p><a href="${app}/admin/courses"><fmt:message key="admin.page.courses"/></a></p>
    <hr/>
    <br/>

    <c:if test="${not empty requestScope.message}">
        <h1><fmt:message key="${requestScope.message}"/></h1>
        <hr/>
    </c:if>

    <form action="${app}/admin/courses/new" method="post">
        <p><fmt:message key="admin.courses.new.new.courses"/></p>
        <table class="tg">
            <tr>
                <th width="120">ID</th>
                <th width="120"><fmt:message key="elective.account.name"/></th>
                <th width="120"><fmt:message key="elective.account.date.start"/></th>
                <th width="120"><fmt:message key="elective.account.date.end"/></th>
                <th width="120"><fmt:message key="elective.account.description"/></th>
                <th width="120"><fmt:message key="elective.account.created"/></th>
                <th width="120"><fmt:message key="elective.account.status"/></th>
                <th width="120"><fmt:message key="elective.account.teacher"/></th>
            </tr>
            <tr>
                <td>default</td>
                <td><input type="text" name="name" minlength="3" maxlength="25" required></td>
                <td><input type="date" name="date_start" value="${requestScope.date}" required></td>
                <td><input type="date" name="date_end" value="${requestScope.date}" required></td>
                <td><textarea type="text" name="description"
                              minlength="5"
                              maxlength="500" required>${requestScope.course.getDescription()}</textarea></td>
                <td>${requestScope.date}</td>
                <td>
                    calculated
                </td>
                <td>
                    <select name="teacher_login" required>
                        <c:forEach items="${requestScope.users}" var="user">
                            <option value="${user.getLogin()}">${user.getLogin()}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </table>

        <p><fmt:message key="admin.courses.new.select.topics"/></p>
        <table class="tg">
            <tr>
                <th width="120"><fmt:message key="admin.courses.new.topics"/></th>
                <th width="120"><fmt:message key="elective.account.action"/></th>
            </tr>
            <tr>
                <td>
                    <select name="selected_topics" multiple="multiple" required>
                        <c:forEach items="${requestScope.topics}" var="topic">
                            <option value="${topic.getId()}">${topic.getName()}</option>
                        </c:forEach>
                    </select>
                </td>
                <td><input type="submit" value="<fmt:message key="admin.courses.new.create.course"/>"></td>
            </tr>
        </table>
    </form>
</main>
</body>
</html>