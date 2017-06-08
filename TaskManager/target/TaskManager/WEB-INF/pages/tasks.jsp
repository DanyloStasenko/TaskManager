<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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
                        <th width="180">Status</th>
                        <th width="180">Edit</th>
                        <th width="180">Delete</th>
                        <th width="180">Share</th>
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

            <c:url var="addAction" value="/tasks/add"/>
            <form:form action="${addAction}" commandName="task">
                <table>
                    <c:if test="${!empty task.title}">
                        <tr>
                            <td>
                                <form:label path="id">
                                    <spring:message text="ID"/>
                                </form:label>
                            </td>
                            <td>
                                <form:input path="id" readonly="true" size="8" disabled="true"/>
                                <form:hidden path="id"/>
                            </td>
                        </tr>
                    </c:if>

                    <tr>
                        <td>
                            <form:label path="title">
                                <spring:message text="Title"/>
                            </form:label>
                        </td>
                        <td>
                            <form:input path="title"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <form:label path="status">
                                <spring:message text="Status"/>
                            </form:label>
                        </td>
                        <td>
                            <form:input path="status"/>
                        </td>
                    </tr>

                    <tr>
                        <td colspan="2">

                            <c:if test="${!empty task.title}">
                                <input type="submit"
                                       value="<spring:message text="Edit Task"/>"/>
                            </c:if>

                            <c:if test="${empty task.title}">
                                <input type="submit"
                                       value="<spring:message text="Add Task"/>"/>
                            </c:if>
                        </td>
                    </tr>
                </table>
            </form:form>
            <br>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <p> Welcome : ${pageContext.request.userPrincipal.name} | <a href="<c:url value="/j_spring_security_logout"/>">Logout</a></p>
            </c:if>
            <p>Copyright &copy; 2017 <a href="http://github.com/danylostasenko">Danylo Stasenko</a></p>
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