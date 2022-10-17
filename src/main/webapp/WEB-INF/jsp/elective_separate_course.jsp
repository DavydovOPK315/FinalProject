<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Course</title>
    <link rel="stylesheet" href="${app}/static/css/core-style.css">
    <link rel="stylesheet" href="${app}/static/css/separate_course.css">
    <link rel="stylesheet" href="${app}/static/css/base.css">
</head>
<body>
<div class="main__part">
    <div class="green">
        <div class="product">
            <div class="img__desc">
                <img src="${app}/static/image/Course.jpg" width="150" height="150" alt="picture"/>
            </div>
            <div class="description">
                <div class="all__description">
                    <div class="title__product">
                        ${requestScope.course.getName()}
                    </div>
                    <p class="desc">${requestScope.course.getDescription()}</p>
                </div>
            </div>
        </div>
        <hr/>
        <div class="quantity">
            <div class="price">
                <p>
                    <fmt:message key="elective.separate.course.date.start"/> <span class="pro__info">${requestScope.course.getDateStart()}</span>&nbsp;
                    <fmt:message key="elective.separate.course.date.end"/> <span class="pro__info">${requestScope.course.getDateEnd()}</span>&nbsp;
                    <fmt:message key="elective.separate.course.status"/> <span class="pro__info">${requestScope.course.getStatus()}</span>&nbsp;
                    <fmt:message key="elective.separate.course.students"/> <span class="pro__info">${requestScope.numberStudents}</span>&nbsp;
                    <fmt:message key="elective.separate.course.teacher"/> <span class="pro__info">${requestScope.course.getTeacherLogin()}</span>
                    <br/>

                    <fmt:message key="elective.separate.course.topics"/> <span class="pro__info"> </span>&nbsp;

                    <c:forEach items="${requestScope.topics}" var="topic">
                        <span class="pro__info"> ${topic.getName()} </span>&nbsp;
                    </c:forEach>

                </p>
            </div>

            <c:choose>
                <c:when test="${sessionScope.user.getRoleId() == 2 && requestScope.course.getStatus() == 'NOT_STARTED'}">
                    <label class="title__quantity"></label>
                    <form action="${app}/elective/enrolle?id=${requestScope.course.getId()}" method="post">
                        <input type="submit" value="<fmt:message key="elective.separate.course.enrolle"/>" class="button__separate_course">
                    </form>
                </c:when>
                <c:otherwise>
                    <label class="title__quantity"></label>
                    <form action="${app}/elective/courses" method="get">
                        <input type="submit" value="<fmt:message key="elective.separate.course.go.main"/>" class="button__separate_course">
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>