<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url var="addAction" value="/tasks/share"/>
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
                <form:label path="recentlySharedTo">
                    <spring:message text="CreatedBy"/>
                </form:label>
            </td>
            <td>
                <form:input path="recentlySharedTo"/>
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