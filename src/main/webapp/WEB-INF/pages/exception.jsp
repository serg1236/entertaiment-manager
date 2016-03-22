<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Entertainment Manager</title>
</head>
<body>
    <h1>Oops! Something bag happened!</h1>
    <h2><i>${exception}</i></h1>
    <c:if test="${not empty cause}">
        <h2><i>Caused by: ${cause}</i></h2>
    </c:if>
    <h2>Detailed log:</h2>
    <p>${stack}</p>
</body>
</html>