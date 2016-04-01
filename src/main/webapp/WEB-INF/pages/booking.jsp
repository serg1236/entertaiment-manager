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
    <h1>Here you can book a ticket:</h1>
    <form action="/book/ticket" method="POST">
        <label for="event">Event:</label>
        <input type="text" name="event" required/>
        <br>
        <label for="date">Time and date:</label>
        <input type="text" name="date" pattern="\d\d\:\d\d\s\d\d\-\d\d\-\d\d\d\d" placeholder="hh:mm dd-MM-yyyy" required/>
        <br>
        <label for="seat">Seat:</label>
        <input type="number" name="seat" required/>
        <br>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="submit" value="submit"/>
    </form>
</body>
</html>