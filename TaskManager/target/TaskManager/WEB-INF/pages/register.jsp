<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Users</title>

    <meta name="description" content="This is task page">
    <meta name="author" content="Danylo Stasenko">

    <link rel="shortcut icon" href="../favicon.ico"/>
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link href="../css/stylet.css" rel="stylesheet">
</head>

<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="row">
                <div class="col-md-2">
                </div>

                <div class="col-md-8">
                    <nav class="navbar navbar-default" role="navigation">
                        <div class="navbar-header">

                            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                                <span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
                            </button>
                        </div>

                        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                            <ul class="nav navbar-nav">
                                <li>
                                    <a href="/tasks">Tasks</a>
                                </li>
                                <li>
                                    <a href="/users">Users</a>
                                </li>
                            </ul>

                            <c:if test="${pageContext.request.userPrincipal.name == null}">
                                <ul class="nav navbar-nav navbar-right">
                                    <li class="active">
                                        <a href="<c:url value="/register"/>">Register</a>
                                    </li>
                                </ul>
                            </c:if>
                        </div>
                    </nav>
                    <h1>Register new user:</h1>

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
                                <td>
                                    <form:label path="email">
                                        <spring:message text="Email "/>
                                    </form:label>
                                </td>
                                <td>
                                    <form:input path="email"/>
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
                </div>
                <div class="col-md-2">
                </div>
            </div>
        </div>
    </div>
</div>


</body>
</html>
