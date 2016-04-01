<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<sec:authorize access="hasAnyRole('ROLE_REGISTERED_USER','ROLE_BOOKING_MANAGER')">
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <h3>
            User : ${pageContext.request.userPrincipal.name}
        </h3>
    </c:if>
    <c:url value="/logout_from_app" var="logoutUrl" />
    <form action="${logoutUrl}" method="POST">
        <input type="hidden" name="${_csrf.parameterName}"
            value="${_csrf.token}" />
        <input type="submit" value="LOGOUT" />
    </form>
</sec:authorize>