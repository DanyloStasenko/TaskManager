<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>

<html>
<head>
    <title>Error page :(</title>

    <link rel="shortcut icon" href="../favicon.ico"/>
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link href="../css/stylet.css" rel="stylesheet">
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
                            <li>
                                <a href="/users">Users</a>
                            </li>
                            <li class="active">
                                <a href="">Error</a>
                            </li>
                        </ul>

                        <c:if test="${pageContext.request.userPrincipal.name != null}">
                            <ul class="nav navbar-nav navbar-right">
                                <li>
                                    <a href="<c:url value="/j_spring_security_logout"/>">Logout (${pageContext.request.userPrincipal.name})</a>
                                </li>
                            </ul>
                        </c:if>

                    </div>

                </nav>

                <div class="jumbotron">
                    <h2>
                        <c:if test="${!empty message}">
                            <h1>${message}</h1>
                        </c:if>
                    </h2>
                    <p>
                        You see this page because some error happened while processing Your request
                        You can get more info by the following link
                    </p>
                    <p>
                        <a class="btn btn-primary btn-large" href="#">Learn more</a>
                    </p>
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




