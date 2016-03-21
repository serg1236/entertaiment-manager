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
    <h1>Welcome to Entertainment manager (alpha)!</h1>
    <c:if test="${not empty message}">
       <h3>${message}</h3>
    </c:if>
    <h2>Here you can:</h2>
    <ul>
        <li><a href="/upload">Upload users and events</a></li>
        <li><a href="/book">Book a ticket</a></li>
        <li><a href="/tickets">View tickets</a></li>
    </ul>
</body>
</html>