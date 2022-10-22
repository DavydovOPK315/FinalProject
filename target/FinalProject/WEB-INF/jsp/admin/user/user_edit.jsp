<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit user</title>
    <link rel="stylesheet" type="text/css" href="${app}/static/css/admin.css">
</head>
<body>
<main>
    <p><a href="${app}/admin_page.jsp"><fmt:message key="admin.courses.admin.page"/></a></p>
    <hr/>
    <br/>
    <p><a href="${app}/admin/users"><fmt:message key="admin.users.all.users"/></a></p>
    <hr/>
    <br/>
    <p><a href="${app}/admin/users/new"><fmt:message key="admin.users.add.new.user"/></a></p>
    <hr/>
    <br/>

    <c:if test="${not empty requestScope.message}">
        <h1><fmt:message key="${requestScope.message}"/></h1>
        <hr/>
    </c:if>

    <p><fmt:message key="admin.user.edit"/>: </p>
    <table class="tg">
        <tr>
            <th width="80">ID</th>
            <th width="120"><fmt:message key="admin.users.login"/></th>
            <th width="120"><fmt:message key="admin.users.firstname"/></th>
            <th width="120"><fmt:message key="admin.users.lastname"/></th>
            <th width="120"><fmt:message key="admin.users.email"/></th>
            <th width="120"><fmt:message key="admin.users.created"/></th>
            <th width="120"><fmt:message key="admin.users.status"/></th>
            <th width="120"><fmt:message key="admin.users.role"/></th>
            <th width="120"><fmt:message key="elective.account.action"/> 1</th>
            <th width="120"><fmt:message key="elective.account.action"/> 2</th>
        </tr>
        <tr>
            <form action="${app}/admin/users/edit" method="post">
                <td><input type="hidden" name="id" value="${requestScope.userDto.getId()}" required>
                    ${requestScope.userDto.getId()}
                </td>

                <td><input type="hidden" id="login" name="login" value="${requestScope.userDto.getLogin()}"
                           minlength="4" maxlength="30" required>${requestScope.userDto.getLogin()}</td>
                <td><input type="text" id="first_name" name="first_name" value="${requestScope.userDto.getFirstName()}"
                           minlength="4" maxlength="30" required></td>
                <td><input type="text" id="last_name" name="last_name" value="${requestScope.userDto.getLastName()}"
                           minlength="4" maxlength="30" required></td>
                <td><input type="email" id="email" name="email" value="${requestScope.userDto.getEmail()}" required>
                </td>
                <td>${requestScope.userDto.getCreated()}</td>
                <td>
                    <select name="status" required>
                        <c:forEach items="${requestScope.statuses}" var="status">
                            <c:choose>
                                <c:when test="${status.name() == requestScope.userDto.getStatus()}">
                                    <option value="${status.name()}" selected>${status.name()}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${status.name()}">${status.name()}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <select name="role" required>
                        <c:forEach items="${requestScope.roles}" var="role">
                            <c:choose>
                                <c:when test="${role.getName() == requestScope.userDto.getRole()}">
                                    <option value="${role.getId()}" selected>${role.getName()}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${role.getId()}">${role.getName()}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
                <td><input type="submit" value="<fmt:message key="admin.user.edit"/>"></td>
            </form>
            <form action="${app}/admin/users/delete?id=${requestScope.userDto.getId()}" method="post">
                <td><input type="submit" value="<fmt:message key="admin.user.edit.delete"/> "></td>
            </form>
        </tr>
    </table>

    <p><fmt:message key="elective.account.enroll"/></p>
    <c:choose>
        <c:when test="${empty requestScope.courseList}">
            <h2><fmt:message key="elective.account.not.enroll"/></h2>
        </c:when>
        <c:otherwise>
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
                    <th width="120"><fmt:message key="elective.account.students"/></th>

                    <c:if test="${requestScope.userDto.getRole() == 'STUDENT'}">
                        <th width="120"><fmt:message key="elective.account.avg"/></th>
                    </c:if>
                </tr>
                <c:forEach items="${requestScope.courseList}" var="course">
                    <tr>
                        <td>
                            <a href="${app}/admin/courses/edit?id=${course.getId()}"> ${course.getId()} </a>
                        </td>
                        <td>
                            <a href="${app}/admin/courses/edit?id=${course.getId()}"> ${course.getName()} </a>
                        </td>
                        <td>${course.getDateStart()}</td>
                        <td>${course.getDateEnd()}</td>
                        <td>
                            <textarea type="text" name="description" minlength="5" maxlength="500">
                                    ${course.getDescription()}
                            </textarea>
                        </td>
                        <td>${course.getCreated()}</td>
                        <td>${course.getStatus()}</td>
                        <td>
                            <a href="${app}/admin/users/edit?id=${course.getTeacherId()}">${course.getTeacherLogin()}</a>
                        </td>
                        <td>${course.getNumberStudents()}</td>
                        <c:if test="${requestScope.userDto.getRole() == 'STUDENT'}">
                            <td>${course.getAverageGrade()}</td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</main>
</body>
</html>