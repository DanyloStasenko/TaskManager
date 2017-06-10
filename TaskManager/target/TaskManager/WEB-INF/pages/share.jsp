<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>

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
                <form:input path="title" readonly="true" />
            </td>
        </tr>

        <tr>
            <td>
                <form:label path="status">
                    <spring:message text="Share with: "/>
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
                           value="<spring:message text="Share"/>"/>
                </c:if>

            </td>
        </tr>
    </table>
</form:form>