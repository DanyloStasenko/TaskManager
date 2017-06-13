<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>

<!DOCTYPE html>
<html lang="en">
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

   <%-- this code will execute on every 5 seconds so we are updating data with an AJAX request--%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
    <script type="text/javascript">
        window.setInterval(function() {
            $('#updating').load('/users.html #updating');
        }, 1000 * 5);   // 5 seconds
    </script>
</head>
<body>
<div class="container-fluid">

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
                        <li class="active">
                            <a href="/users">Users</a>
                        </li>
                    </ul>

                    <c:if test="${pageContext.request.userPrincipal.name != null}">
                        <ul class="nav navbar-nav navbar-right">
                            <li>
                                <a href="<c:url value="/j_spring_security_logout"/>">Logout (${pageContext.request.userPrincipal.name})</a>
                            </li>
                        </ul>
                    </c:if>

                    <c:if test="${pageContext.request.userPrincipal.name == null}">
                        <ul class="nav navbar-nav navbar-right">
                            <li>
                                <a href="<c:url value="/register"/>">Register</a>
                            </li>
                        </ul>
                    </c:if>
                </div>
            </nav>


            <div id = updating>
                <c:if test="${!empty users}">
                    <h1>Users List:</h1>
                    <h3>Hello! Here You can see all users!</h3>
                    <table class="table">
                        <thead>
                        <th width="180">Username</th>
                        <th width="180">Password</th>
                        <th width="180">Email</th>
                        <th width="180">Tasks</th>
                        </thead>

                        <tbody>
                        <c:forEach items="${users}" var="user">
                            <tr>
                                <td>${user.username}</td>
                                <td>${user.password}</td>
                                <td>${user.email}</td>
                                <td>
                                    <c:forEach items="${user.tasks}" var="task">
                                            ${task.title}
                                    </c:forEach>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>


            </div>
        </div>
        <div class="col-md-2">
        </div>



    </div>
</div>

<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/scripts.js"></script>
</body>
</html>