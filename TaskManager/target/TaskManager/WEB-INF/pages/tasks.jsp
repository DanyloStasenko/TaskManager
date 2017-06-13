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

    <title>Tasks</title>

    <meta name="description" content="This is task page">
    <meta name="author" content="Danylo Stasenko">

    <link rel="shortcut icon" href="../favicon.ico"/>
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link href="../css/stylet.css" rel="stylesheet">

    <%--this code will execute on every 5 seconds so we are updating data with an AJAX request--%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
    <script type="text/javascript">
        window.setInterval(function() {
            $('#updating').load('/tasks.html #updating');
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
                        <li class="active">
                            <a href="/tasks">Tasks</a>
                        </li>
                        <li>
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
                </div>
            </nav>


            <div id = updating>
                <c:if test="${!empty tasks}">
                    <h1>Tasks List:</h1>
                    <table class="table">
                        <thead>
                            <th width="80">ID</th>
                            <th width="180">Title</th>
                            <th width="180">Status</th>
                        </thead>
                        <tbody>
                            <c:forEach items="${tasks}" var="task">
                                <tr>
                                    <td>${task.id}</td>
                                    <td>${task.title}</td>
                                    <td>${task.status}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>

                <c:if test="${!empty managing}">
                    <h1>Managing List:</h1>
                    <table class="table">
                        <thead>
                        <th width="80">ID</th>
                        <th width="180">Title</th>
                        <th width="400">Status</th>
                        <th width="120">Edit</th>
                        <th width="120">Delete</th>
                        <th width="120">Share</th>
                        </thead>
                        <tbody>
                        <c:forEach items="${managing}" var="task">
                            <tr>
                                <td>${task.id}</td>
                                <td>${task.title}</td>
                                <td>${task.status}</td>
                                <td><a href="<c:url value='/edit/${task.id}'/>">Edit</a></td>
                                <td><a href="<c:url value='/remove/${task.id}'/>">Delete</a></td>
                                <td><a href="<c:url value='/share/${task.id}'/>">Share</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>
        </div>
        <div class="col-md-2">
        </div>


        <div class="row">
            <div class="col-md-12">
                <div class="row">
                    <div class="col-md-2">

                    </div>

                    <div class="col-md-4">

                        <c:url var="addAction" value="/tasks/add"/>
                        <form:form cssClass="form-horizontal" role="form" action="${addAction}" commandName="task">

                            <c:if test="${empty task.title}">
                                <h4>Add task:</h4>
                            </c:if>

                            <c:if test="${!empty task.title}">
                                <h4>Edit task:</h4>
                                <div class="form-group">
                                    <form:label cssClass="col-sm-2 control-label" for="inputEmail3" path="id">
                                        ID
                                    </form:label>
                                    <form:input path="id" readonly="true" size="8" disabled="true"/>
                                    <form:hidden path="id"/>
                                </div>
                            </c:if>

                            <div class="form-group">
                                <form:label cssClass="col-sm-2 control-label" for="text" path="title">
                                    Title
                                </form:label>
                                <form:input path="title"/>
                            </div>

                            <div class="form-group">
                                <form:label cssClass="col-sm-2 control-label" for="text" path="status">
                                    Status
                                </form:label>
                                <form:input path="status"/>
                            </div>


                            <c:if test="${!empty task.title}">
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" class="btn btn-default" value="">
                                            Edit Task
                                        </button>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${empty task.title}">
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" class="btn btn-default" value="">
                                            Add Task
                                        </button>
                                    </div>
                                </div>
                            </c:if>

                        </form:form>
                    </div>
                    <div class="col-md-6">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/scripts.js"></script>
</body>
</html>