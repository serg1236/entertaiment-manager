<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt"
           uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Entertainment Manager</title>
</head>
<body>
    <c:choose>
        <c:when test="${fn:length(events) > 0}">
            <h2>Found events:</h2>
            <c:forEach var="event" items="${events}">
                <h3>${event.name}</h3>
                <ul>
                    <fmt:setLocale value="en_US"/>
                    <li>Regular price: <fmt:formatNumber value="${event.basePrice}" type="currency"/></li>
                    <li>Rating: ${event.rating}</li>
                </ul>
            </c:forEach>
        </c:when>
        <c:otherwise>
        <h2>Nothing found...</h2>
        </c:otherwise>
    </c:choose>
</body>
</html>