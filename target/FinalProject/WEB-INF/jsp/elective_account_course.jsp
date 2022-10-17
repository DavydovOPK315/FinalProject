<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${app}/static/css/elective_account_courses.css">
    <link rel="stylesheet" href="${app}/static/css/core-style.css">
    <title>Account course</title>
</head>
<body>
<main>
    <div class="wrapper_courses">
        <c:if test="${not empty requestScope.message}">
            <h1 class="message"><fmt:message key="${requestScope.message}"/></h1>
            <hr/>
        </c:if>

        <p class="title_courses"><fmt:message key="elective.account.course.info"/></p><br/>

        <c:if test="${sessionScope.user.getRoleId() == 3 && requestScope.course.getStatus() == 'CURRENT'}">
        <form action="${app}/elective/account/update/scores" method="post">
            </c:if>
            <table class="tg">
                <tr>
                    <th width="120"><fmt:message key="elective.account.name"/></th>
                    <th width="120"><fmt:message key="elective.account.date.start"/></th>
                    <th width="120"><fmt:message key="elective.account.date.end"/></th>
                    <th width="120"><fmt:message key="elective.account.description"/></th>
                    <th width="120"><fmt:message key="elective.account.created"/></th>
                    <th width="120"><fmt:message key="elective.account.status"/></th>
                    <th width="120"><fmt:message key="elective.account.teacher"/></th>
                    <th width="120"><fmt:message key="elective.account.course.action.one"/></th>
                    <th width="120"><fmt:message key="elective.account.course.action.two"/></th>
                </tr>
                <tr>
                    <td>
                        <a href="${app}/elective/separate/course?id=${requestScope.course.getId()}"> ${requestScope.course.getName()} </a>
                    </td>
                    <td>${requestScope.course.getDateStart()}</td>
                    <td>${requestScope.course.getDateEnd()}</td>
                    <td>
                        <div class="description_td">${requestScope.course.getDescription()}</div>
                    </td>
                    <td>${requestScope.course.getCreated()}</td>
                    <td>${requestScope.course.getStatus()}</td>
                    <td>${requestScope.course.getTeacherLogin()}</td>
                    <td>
                        <c:choose>
                            <c:when test="${sessionScope.user.getRoleId() == 2 && requestScope.course.getStatus() == 'NOT_STARTED'}">
                                <form action="${app}/elective/account/leave/course?id=${requestScope.course.getId()}"
                                      method="post">
                                    <input type="submit" value="<fmt:message key="elective.account.leave.course"/>"
                                           class="button__separate_course">
                                </form>
                            </c:when>
                            <c:when test="${sessionScope.user.getRoleId() == 3 && requestScope.course.getStatus() == 'CURRENT'}">
                                <input type="submit" value="<fmt:message key="elective.account.course.update.grades"/>" class="button__separate_course">
                            </c:when>
                            <c:otherwise>
                                <form action="${app}/elective/courses" method="get">
                                    <input type="submit" value="<fmt:message key="elective.separate.course.go.main"/>" class="button__separate_course">
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <a href="${app}/elective/account/course/print/course/grade/report?courseId=${requestScope.course.getId()}">
                            <img title="<fmt:message key="elective.account.print"/>" src="${app}/static/image/pdf.png" alt="Print">
                        </a>
                    </td>
            </table>
            <br/>
            <br/>
            <p class="title_courses"><fmt:message key="elective.account.course.progress"/></p>
            <table class="tg">
                <tr>
                    <th width="120"><fmt:message key="elective.account.course.topics.students"/></th>
                    <c:forEach items="${requestScope.topics}" var="topic">
                        <th width="120">${topic.getName()}</th>
                    </c:forEach>
                    <th width="120"><fmt:message key="elective.account.avg"/></th>
                </tr>
                <c:choose>
                    <c:when test="${sessionScope.user.getRoleId() == 2}">
                        <tr>
                            <td>
                                <mylib:fullname user="" userDto="${requestScope.user}"/>
                            </td>
                            <c:forEach items="${requestScope.user.performanceList}" var="perform">
                                <td>${perform.getGrade()}</td>
                            </c:forEach>
                            <td>${requestScope.user.getAverageGrade()}</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="courseId" value="${requestScope.course.getId()}">
                        <c:forEach items="${requestScope.students}" var="student">
                            <tr>
                                <td>
                                    <input type="hidden" name="studentId" value="${student.getId()}">
                                    <mylib:fullname user="" userDto="${student}"/>
                                </td>
                                <c:forEach items="${student.performanceList}" var="perform">
                                    <td>
                                        <input type="number" name="grade" value="${perform.getGrade()}" min="0"
                                               max="100" required>
                                    </td>
                                </c:forEach>
                                <td>${student.getAverageGrade()}</td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </table>
            <c:if test="${sessionScope.user.getRoleId() == 3 && requestScope.course.getStatus() == 'CURRENT'}">
        </form>
        </c:if>
        <br/>
        <br/>
        <p class="title_courses"><fmt:message key="elective.account.course.students"/> ${requestScope.students.size()}</p>
        <br/>
        <br/>
    </div>
</main>
</body>
</html>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>