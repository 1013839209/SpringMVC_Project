<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/3/21
  Time: 14:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
        welcome to Spring MVC
<br/>

        <c:forEach items="${requestScope.errors}" var="error">
                ${error.getDefaultMessage()}
        </c:forEach>

<fmt:message key="resource.welcome"></fmt:message>
<fmt:message key="resource.exist"></fmt:message>

<br/>
        =============request作用域:
        ${requestScope.student.id}---->${requestScope.student.name}
        ${requestScope.student2.id}---->${requestScope.student2.name}
        ${requestScope.student3.id}---->${requestScope.student3.name}
        ${requestScope.student4.id}---->${requestScope.student4.name}
<br/>
        =============session作用域:
        ${requestScope.student.id}---->${requestScope.student.name}
        ${sessionScope.student2.id}---->${sessionScope.student2.name}
        ${sessionScope.student3.id}---->${sessionScope.student3.name}
        ${sessionScope.student4.id}---->${sessionScope.student4.name}

</body>
</html>
