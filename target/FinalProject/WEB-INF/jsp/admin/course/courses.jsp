<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>All courses</title>
    <link rel="stylesheet" type="text/css" href="${app}/static/css/more_button.css">
    <link rel="stylesheet" type="text/css" href="${app}/static/css/admin.css">
</head>
<body>
<main>
    <p><a href="${app}/admin_page.jsp"><fmt:message key="admin.courses.admin.page"/></a></p>
    <hr/>
    <p><a href="${app}/admin/courses/new"><fmt:message key="admin.courses.add.new"/></a></p>
    <hr/>
    <p><a href="${app}/admin/courses"><fmt:message key="admin.page.courses"/></a></p>
    <hr/>
    <p><fmt:message key="elective.separate.course.status"/>
        <a href="${app}/admin/courses/filter?key=status&value=NOT_STARTED"> NOT_STARTED </a>
        <a href="${app}/admin/courses/filter?key=status&value=CURRENT"> | CURRENT</a>
        <a href="${app}/admin/courses/filter?key=status&value=FINISHED"> | FINISHED</a>
    </p>
    <hr/>
    <p><fmt:message key="elective.courses.name"/>:
        <a href="${app}/admin/courses/filter?key=alphabet&value=AZ"> az </a>
        <a href="${app}/admin/courses/filter?key=alphabet&value=ZA"> | za</a>
    </p>
    <hr/>
    <p><fmt:message key="elective.courses.duration"/>:
        <a href="${app}/admin/courses/filter?key=duration&value=MIN-MAX"> MIN-MAX </a>
        <a href="${app}/admin/courses/filter?key=duration&value=MAX-MIN"> | MAX-MIN</a>
    </p>
    <hr/>
    <p><fmt:message key="elective.separate.course.students"/>
        <a href="${app}/admin/courses/filter?key=students&value=MIN-MAX"> MIN-MAX </a>
        <a href="${app}/admin/courses/filter?key=students&value=MAX-MIN"> | MAX-MIN</a>
    </p>
    <hr/>
    <br/>
    <form action="${app}/admin/courses/filter" method="get">
        <input type="hidden" name="key" value="teacher_login">
        <label> <fmt:message key="admin.courses.find.teacher.login"/> </label>
        <select name="value" required>
            <c:forEach items="${requestScope.teachers}" var="teacher">
                <option value="${teacher.getId()}">${teacher.getLogin()}</option>
            </c:forEach>
        </select>
        <input type="submit" value="<fmt:message key="elective.courses.find.courses"/>">
    </form>
    <hr/>
    <br/>
    <form action="${app}/admin/courses/filter" method="get">
        <input type="hidden" name="key" value="login">
        <label for="login"> <fmt:message key="admin.courses.find.user.login"/> </label>
        <input type="text" minlength="2" name="value" id="login" placeholder="login" required>
        <input type="submit" value="<fmt:message key="elective.courses.find.courses"/>">
    </form>
    <hr/>
    <br/>
    <form action="${app}/admin/courses/filter" method="get">
        <input type="hidden" name="key" value="name">
        <label for="courseName"> <fmt:message key="admin.courses.find.name"/> </label>
        <input type="text" minlength="2" name="value" id="courseName"
               placeholder="<fmt:message key="elective.courses.name"/>" required>
        <input type="submit" value="<fmt:message key="elective.courses.find.courses"/>">
    </form>
    <hr/>
    <br/>
    <form action="${app}/admin/courses/filter" method="get">
        <input type="hidden" name="key" value="topic">
        <label> <fmt:message key="admin.courses.find.topic"/> </label>
        <select name="value" required>
            <c:forEach items="${requestScope.topics}" var="topic">
                <option value="${topic.getName()}">${topic.getName()}</option>
            </c:forEach>
        </select>
        <input type="submit" value="<fmt:message key="elective.courses.find.courses"/>">
    </form>
    <hr/>
    <br/>

    <form action="${app}/admin/courses/filter" method="get">
        <input type="hidden" name="key" value="date_started">
        <label> <fmt:message key="admin.courses.find.date.start"/> </label>
        <input type="date" name="date_start" value="${requestScope.dateNow}" min="${requestScope.dateNow}" required>
        <input type="submit" value="<fmt:message key="elective.courses.find.courses"/>">
    </form>
    <hr/>
    <br/>

    <c:if test="${not empty requestScope.message}">
        <c:choose>
            <c:when test="${empty requestScope.value}">
                <h1><fmt:message key="${requestScope.message}"/></h1>
            </c:when>
            <c:otherwise>
                <h1><fmt:message key="elective.courses.message"/> ${requestScope.courseList.size()}
                    <fmt:message key="elective.courses.filter.courses.by"/>
                    <fmt:message key="${requestScope.message}"/> ${requestScope.value}</h1>
            </c:otherwise>
        </c:choose>
        <hr/>
    </c:if>

    <c:if test="${requestScope.courseList.size() > 0}">
        <p><fmt:message key="admin.page.courses"/></p>
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
            </tr>
            <c:forEach items="${requestScope.courseList}" var="course">
                <tr class="all_items">
                    <td>
                        <a href="${app}/admin/courses/edit?id=${course.getId()}"> ${course.getId()} </a>
                    </td>
                    <td>
                        <a href="${app}/admin/courses/edit?id=${course.getId()}"> ${course.getName()} </a>
                    </td>
                    <td>${course.getDateStart()}</td>
                    <td>${course.getDateEnd()}</td>
                    <td>
                        <div class="description_td">${course.getDescription()}</div>
                    </td>
                    <td>${course.getCreated()}</td>
                    <td>${course.getStatus()}</td>
                    <td>
                        <a href="${app}/admin/users/edit?id=${course.getTeacherId()}">${course.getTeacherLogin()}</a>
                    </td>
                    <td>${course.getNumberStudents()}</td>
                </tr>
            </c:forEach>
        </table>
        <div class="button__more">
            <button id="btnMore"><fmt:message key="load.more"/></button>
        </div>
    </c:if>
</main>
<script src="${app}/static/js/jquery/jquery-2.2.4.min.js"></script>
<script src="${app}/static/js/more.js"></script>
</body>
</html>