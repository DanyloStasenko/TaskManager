<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>

<html>
<head>
    <link rel="shortcut icon" href="../favicon.ico"/>
    <title>Register new user</title>
</head>
<body>

<c:url var="addAction" value="/register/adduser"/>
<form:form action="${addAction}" commandName="user">
    <table class="tg">
        <tr>
            <td width ="100">
                <form:label path="username">
                    <spring:message text="Username "/>
                </form:label>
            </td>
            <td>
                <form:input path="username"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="password">
                    <spring:message text="Password "/>
                </form:label>
            </td>
            <td>
                <form:input path="password"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <c:if test="${empty user.username}">
                    <input type="submit"
                           value="<spring:message text="Add User"/>"/>
                </c:if>
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>
