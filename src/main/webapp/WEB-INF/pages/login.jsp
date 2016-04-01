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
    <h1>Hello, dear User! You cannot register yet, but if you are lucky to be in our DB, please login: </h1>
    <c:if test="${not empty error}">
        <h3 style="color:red"><i>${error}</i></h3>
    </c:if>
    <c:if test="${not empty message}">
        <h3 style="color:green"><i>${message}</i></h3>
    </c:if>
    <form action="<c:url value='/login_to_app' />" method="POST">
        <label for="email">Email: </label>
        <input type="email" name="email" required/>
        <br>
        <label for="password">Password: </label>
        <input type="password" name="password" required/>
        <br>
        <label for="remember-me">Remember Me: </label>
        <input type="checkbox" name="remember-me" />
        <br>
        <input type="submit" value="LOGIN"/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </form>
</body>
</html>