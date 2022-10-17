<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>All topics</title>
    <link rel="stylesheet" type="text/css" href="${app}/static/css/more_button.css">
    <link rel="stylesheet" type="text/css" href="${app}/static/css/admin.css">
</head>
<body>
<main>
    <p><a href="${app}/admin_page.jsp"><fmt:message key="admin.courses.admin.page"/></a></p>
    <hr/>
    <br/>
    <p><a href="${app}/admin/topics/new"><fmt:message key="admin.topics.add.new.topic"/></a></p>
    <hr/>
    <br/>

    <c:if test="${not empty requestScope.message}">
        <h1><fmt:message key="${requestScope.message}"/></h1>
        <hr/>
    </c:if>

    <p><fmt:message key="admin.topics.all"/>:</p>
    <table class="tg">
        <tr>
            <th width="120">ID</th>
            <th width="120"><fmt:message key="elective.account.name"/></th>
            <th width="120"><fmt:message key="elective.account.created"/></th>
            <th width="120"><fmt:message key="elective.account.status"/></th>
        </tr>
        <c:forEach items="${requestScope.topicList}" var="topic">
            <tr class="all_items">
                <td>
                    <a href="${app}/admin/topics/edit?id=${topic.getId()}"> ${topic.getId()} </a>
                </td>
                <td>${topic.getName()}</td>
                <td>${topic.getCreated()}</td>
                <td>${topic.getStatus()} </td>
            </tr>
        </c:forEach>
    </table>
    <div class="button__more">
        <button id="btnMore"><fmt:message key="load.more"/></button>
    </div>
</main>
<script src="${app}/static/js/jquery/jquery-2.2.4.min.js"></script>
<script src="${app}/static/js/more.js"></script>
</body>
</html>