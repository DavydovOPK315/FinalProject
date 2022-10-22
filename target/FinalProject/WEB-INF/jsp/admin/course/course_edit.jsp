<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit course</title>
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
    <p><a href="${app}/admin/courses/new"><fmt:message key="admin.courses.add.new"/></a></p>
    <hr/>
    <br/>

    <c:if test="${not empty requestScope.message}">
        <h1><fmt:message key="${requestScope.message}"/></h1>
        <hr/>
    </c:if>

    <p><fmt:message key="admin.courses.edit.courses"/></p>
    <table class="tg">
        <tr>
            <th width="60">ID</th>
            <th width="120"><fmt:message key="elective.account.name"/></th>
            <th width="120"><fmt:message key="elective.account.date.start"/></th>
            <th width="120"><fmt:message key="elective.account.date.end"/></th>
            <th width="120"><fmt:message key="elective.account.description"/></th>
            <th width="120"><fmt:message key="elective.account.created"/></th>
            <th width="120"><fmt:message key="elective.account.status"/></th>
            <th width="120"><fmt:message key="elective.account.teacher"/></th>
            <th width="120"><fmt:message key="elective.account.action"/> 1</th>
            <th width="120"><fmt:message key="elective.account.action"/> 2</th>
        </tr>
        <tr>
            <form action="${app}/admin/courses/edit" method="post">
                <td><input type="hidden" name="id" value="${requestScope.course.getId()}">
                    ${requestScope.course.getId()}
                </td>
                <td><input type="text" name="name" value="${requestScope.course.getName()}" minlength="3" maxlength="45"
                           required></td>
                <td><input type="date" name="date_start" value="${requestScope.course.getDateStart()}" required></td>
                <td><input type="date" name="date_end" value="${requestScope.course.getDateEnd()}" required></td>
                <td><textarea type="text" name="description"
                              minlength="5"
                              maxlength="500" required>${requestScope.course.getDescription()}</textarea>
                </td>
                <td>${requestScope.course.getCreated()}</td>
                <td>
                    ${requestScope.course.getStatus()}
                </td>
                <td>
                    <select name="teacher_login" required>
                        <c:forEach items="${requestScope.users}" var="user">
                            <c:choose>
                                <c:when test="${user.getLogin() == requestScope.course.getTeacherLogin()}">
                                    <option value="${user.getLogin()}" selected>${user.getLogin()}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${user.getLogin()}">${user.getLogin()}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
                <td><input type="submit" value="<fmt:message key="admin.courses.edit.update.course"/>"></td>
            </form>

            <form action="${app}/admin/courses/delete?id=${requestScope.course.getId()}" method="post">
                <td><input type="submit" value="<fmt:message key="admin.courses.edit.delete.course"/>"></td>
            </form>
    </table>

    <p><fmt:message key="admin.courses.edit.add.related.topics"/></p>
    <form action="${app}/admin/courses/add/topics?id=${requestScope.course.getId()}" method="post">
        <table class="tg">
            <tr>
                <th width="120"><fmt:message key="admin.courses.new.topics"/></th>
                <th width="120"><fmt:message key="elective.account.action"/></th>
            </tr>
            <tr>
                <td>
                    <select name="selected_topics" multiple="multiple" required>
                        <c:forEach items="${requestScope.allTopics}" var="topic">
                            <option value="${topic.getId()}">${topic.getName()}</option>
                        </c:forEach>
                    </select>
                </td>
                <td><input type="submit" value="<fmt:message key="admin.courses.edit.topic.add.to.course"/>"></td>
            </tr>
        </table>
    </form>

    <p><fmt:message key="admin.courses.edit.add.related.topics"/></p>
    <table class="tg">
        <tr>
            <th width="140"><fmt:message key="elective.account.course.topics.students"/></th>
            <c:forEach items="${requestScope.topics}" var="topic">
                <th width="120">
                    <a href="${app}/admin/topics/edit?id=${topic.getId()}">${topic.getName()}</a>
                    <a href="${app}/admin/courses/topics/delete?courseId=${requestScope.course.getId()}&topicId=${topic.getId()}">
                        <img width="10" height="10" src="<c:url value="/static/image/x.png"/>" alt="image"/>
                    </a>
                </th>
            </c:forEach>
            <th width="120"><fmt:message key="elective.account.avg"/></th>
        </tr>
        <c:forEach items="${requestScope.students}" var="student">
            <tr>
                <td>
                    <a href="${app}/admin/users/edit?id=${student.getId()}"><mylib:fullname user=""
                                                                                            userDto="${student}"/></a>
                </td>
                <c:forEach items="${student.performanceList}" var="perform">
                    <td>${perform.getGrade()}</td>
                </c:forEach>
                <td>${student.getAverageGrade()}</td>
            </tr>
        </c:forEach>
    </table>
    <p><fmt:message key="elective.separate.course.students"/> ${requestScope.students.size()}</p>
</main>
</body>
</html>