<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>

<html>
    <head>
        <title>Share task</title>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
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

                            <h1>Share task:</h1>

                            <c:url var="addAction" value="/tasks/share"/>
                            <form:form cssClass="form-horizontal" action="${addAction}" commandName="task">

                                <c:if test="${!empty task.title}">
                                    <div class="form-group">
                                        <form:label cssClass="col-sm-2 control-label" for="inputEmail3" path="id">
                                            <spring:message text="ID"/>
                                        </form:label>
                                        <form:input path="id" readonly="true" size="8" disabled="true"/>
                                        <form:hidden path="id"/>
                                    </div>
                                </c:if>

                                <div class="form-group">
                                    <form:label cssClass="col-sm-2 control-label" for="inputEmail3" path="title">
                                        <spring:message text="Title"/>
                                    </form:label>
                                    <form:input path="title" readonly="true"/>
                                </div>

                                <div class="form-group">
                                    <form:label cssClass="col-sm-2 control-label" for="inputEmail3" path="status">
                                       Share with:
                                    </form:label>
                                    <form:input path="status"/>
                                </div>

                                <c:if test="${!empty task.title}">
                                    <div class="form-group">
                                        <div class="col-sm-offset-2 col-sm-10">
                                            <button type="submit" class="btn btn-default" value="">
                                                Share Task
                                            </button>
                                        </div>
                                    </div>
                                </c:if>

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



















