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
    <h1>${email}</h1>
    <c:choose>
        <c:when test="${fn:length(users) > 0}">
            <h2>Found users:</h2>
            <c:forEach var="user" items="${users}">
                <h3>${user.name}</h3>
                <ul>
                    <li>Email: ${user.email}</li>
                    <li>Birth date: <fmt:formatDate pattern="dd-MM-yyyy" value="${user.birthDate}"/></li>
                </ul>
            </c:forEach>
        </c:when>
        <c:otherwise>
        <h2>Nothing found...</h2>
        </c:otherwise>
    </c:choose>
</body>
</html>