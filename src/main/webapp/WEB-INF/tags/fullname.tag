<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="user" required="true" type="ua.com.epam.project.entity.User" %>
<%@ attribute name="userDto" required="true" type="ua.com.epam.project.dto.UserDto" %>

<c:choose>
    <c:when test="${not empty userDto}">
        ${userDto.lastName} ${userDto.firstName}
    </c:when>
    <c:otherwise>
        ${user.firstName} ${user.lastName}
    </c:otherwise>
</c:choose>