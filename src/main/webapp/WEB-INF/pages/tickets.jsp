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
    <h1>Here you can get a ticket list!</h1>
    <h3>Search for tickets by event:</h3>
    <form action="/tickets/byEvent.pdf" method="GET">
        <label for="name">Event:</label>
        <input type="text" name="name" required/>
        <br>
        <label for="date">Time and date:</label>
        <input type="text" name="date" pattern="\d\d\:\d\d\s\d\d\-\d\d\-\d\d\d\d" placeholder="hh:mm dd-MM-yyyy" required/>
        <br>
        <input type="submit" value="submit"/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </form>
    <h3>Search for tickets by user:</h3>
    <form action="/tickets/byUser.pdf" method="GET">
        <label for="email">Email:</label>
        <input type="email" name="email" required/>
        <br>
        <input type="submit" value="submit"/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </form>
</body>
</html>