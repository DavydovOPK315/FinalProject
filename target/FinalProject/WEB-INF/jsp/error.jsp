<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="${app}/static/css/style_error_page.css">
    <link rel="stylesheet" href="${app}/static/css/core-style.css">
</head>
<body>
<main class="error_message">
    <h1 class="error_style_h1"> <fmt:message key="error"/> </h1>
</main>
</body>
</html>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>