<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${app}/static/css/elective_account.css">
    <link rel="stylesheet" href="${app}/static/css/core-style.css">
    <title>Account</title>
</head>
<body>
<article class="my-article">
    <div class="main__greeting">
        <section>
            <div class="greeting">
                <h3><fmt:message key="account.hello"/>, <mylib:fullname user="${sessionScope.user}" userDto=""/></h3>
                <br/>

                <c:if test="${not empty requestScope.message}">
                    <p>${requestScope.courseName}: <fmt:message key="${requestScope.message}"/></p>
                </c:if>

                <c:if test="${empty requestScope.message}">
                    <p><fmt:message key="elective.account.info"/></p>
                </c:if>
            </div>
        </section>
        <aside>
            <div class="account">
                <div class="infor__account">
                    <div class="email__account">
                        <p> ${sessionScope.user.getEmail()}</p>
                    </div>
                </div>
                <a class="gradient-btn_account" href="${app}/elective_account_edit.jsp">
                    <fmt:message key="elective.account.edit"/></a>
                <form action="${app}/elective/account/logout" method="post">
                    <button type="submit" class="gradient-btn_account">
                        <fmt:message key="elective.account.logout"/></button>
                </form>
            </div>
        </aside>
    </div>

    <div class="wrapper_courses">
        <hr/>
        <p class="title_courses"><fmt:message key="elective.account.pdf"/></p>
        <form action="${app}/elective/account/print/courses/report" method="post" class="pdf_icon">
            <input type="image" name="submit" title="<fmt:message key="elective.account.print"/>"
                   src="${app}/static/image/pdf.png" alt="submit">
        </form>
        <hr/>
        <p class="title_courses"><fmt:message key="elective.account.enroll"/></p>
        <c:choose>
            <c:when test="${empty requestScope.courseList}">
                <h2 class="courses_status_label"><fmt:message key="elective.account.not.enroll"/></h2>
            </c:when>
            <c:otherwise>
                <p class="courses_status_label"><fmt:message key="elective.account.sort.status"/>
                    <a class="courses_status"
                       href="${app}/elective/account/courses/filter?key=status&value=NOT_STARTED"> NOT_STARTED </a>
                    <a class="courses_status" href="${app}/elective/account/courses/filter?key=status&value=CURRENT"> |
                        CURRENT</a>
                    <a class="courses_status" href="${app}/elective/account/courses/filter?key=status&value=FINISHED"> |
                        FINISHED</a>
                    <a class="courses_status" href="${app}/elective/account"> | ALL</a>
                </p>
                <table class="tg">
                    <tr>
                        <th width="120"><fmt:message key="elective.account.name"/></th>
                        <th width="120"><fmt:message key="elective.account.date.start"/></th>
                        <th width="120"><fmt:message key="elective.account.date.end"/></th>
                        <th width="120"><fmt:message key="elective.account.description"/></th>
                        <th width="120"><fmt:message key="elective.account.created"/></th>
                        <th width="120"><fmt:message key="elective.account.status"/></th>
                        <th width="120"><fmt:message key="elective.account.teacher"/></th>
                        <th width="120"><fmt:message key="elective.account.students"/></th>
                        <c:if test="${sessionScope.user.getRoleId() == 2}">
                            <th width="120"><fmt:message key="elective.account.avg"/></th>
                        </c:if>
                        <th width="120"><fmt:message key="elective.account.action"/></th>
                    </tr>
                    <c:forEach items="${requestScope.courseList}" var="course">
                        <tr class="all_items">
                            <td>
                                <a href="${app}/elective/separate/course?id=${course.getId()}"> ${course.getName()} </a>
                            </td>
                            <td>${course.getDateStart()}</td>
                            <td>${course.getDateEnd()}</td>
                            <td>
                                <div class="description_td">${course.getDescription()}</div>
                            </td>
                            <td>${course.getCreated()}</td>
                            <td>${course.getStatus()}</td>
                            <td>${course.getTeacherLogin()}</td>
                            <td>${course.getNumberStudents()}</td>
                            <c:if test="${sessionScope.user.getRoleId() == 2}">
                                <td>${course.getAverageGrade()}</td>
                            </c:if>
                            <td>
                                <c:choose>
                                    <c:when test="${sessionScope.user.getRoleId() == 2 && course.getStatus() == 'NOT_STARTED'}">
                                        <form action="${app}/elective/account/course" method="get">
                                            <input type="hidden" name="id" value="${course.getId()}">
                                            <input type="image" name="submit"
                                                   title="<fmt:message key="elective.account.see"/>"
                                                   src="${app}/static/image/Eye.png" alt="submit">
                                        </form>
                                        <form action="${app}/elective/account/leave/course?id=${course.getId()}"
                                              method="post">
                                            <input type="submit"
                                                   value="<fmt:message key="elective.account.leave.course"/>"
                                                   class="button__separate_course">
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <form action="${app}/elective/account/course" method="get">
                                            <input type="hidden" name="id" value="${course.getId()}">
                                            <input type="image" name="submit"
                                                   title="<fmt:message key="elective.account.see"/>"
                                                   src="${app}/static/image/Eye.png" alt="submit">
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <div class="button__more">
                    <button id="btnMore"><fmt:message key="load.more"/></button>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</article>
<script src="${app}/static/js/jquery/jquery-2.2.4.min.js"></script>
<script src="${app}/static/js/more.js"></script>
</body>
</html>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>