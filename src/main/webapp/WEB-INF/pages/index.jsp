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
       <h3><i>${message}</i></h3>
    </c:if>
    <h2>Here you can:</h2>
    <ul>
        <li><a href="/upload">Upload users and events</a></li>
        <li><a href="/book">Book a ticket</a></li>
        <li><a href="/tickets">View tickets in PDF format</a></li>
    </ul>
    <h2>Please also check our info controller:</h2>
    <ul>
        <li><a href="/view/auditoriums">Get all auditoriums</a></li>
        <li><a href="/view/auditoriums/Plaza">Get info about Plaza</a></li>
        <li><a href="/view/events">Check all events</a></li>
        <li><a href="/view/events/Okean Elzy Concert">Check Okean Elzy show</a></li>
        <li><a href="/view/users/Jon Snow">Something about Jon Snow</a></li>
    </ul>
</body>
</html>