<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Entertainment Manager</title>
</head>
<body>
    <h1>Upload users:</h1>
    <form enctype="multipart/form-data" action="/upload/users" method="POST">
        <input type="file" name="file"/>
        <input type="submit" value="Submit"/>
    </form>
    <h1>Upload events:</h1>
    <form enctype="multipart/form-data" action="/upload/events" method="POST">
        <input type="file" name="file"/>
        <input type="submit" value="Submit"/>
    </form>
</body>
</html>