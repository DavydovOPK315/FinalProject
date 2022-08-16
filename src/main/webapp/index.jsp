<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>First JSP</title>
</head>
<body>
<h2>Testing JSP</h2>
<p>
    <%= "Hello world" %>
</p>

<p>
    <%
        Date myDate = new Date();
        String result = "Текущая дата :" + myDate;
    %>
    <%= result %>
</p>
</body>
</html>
