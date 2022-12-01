<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>--%>
<html>
<%--<%@page contentType="text/html" pageEncoding="UTF-8" %>--%>
<%--<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>--%>

<head>
<%--    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">--%>
    <title>X</title>
<%--    <base href="${pageContext.request.contextPath}/"/>--%>
<%--    <link rel="stylesheet" href="resources/css/style.css">--%>
</head>
<body>
<h1>It's my first JSP file!</h1>

<c:forEach var = "menu" items="${menus}">
    <p>${menu.restaurant}</p>
    </c:forEach>

<%--<c:forEach items="${menus}" var="menu">--%>
<%--<jsp:useBean id="menu" type="com.pafolder.graduation.model.Menu"/>--%>
<%--<jsp:useBean id="menu" type="com.pafolder.graduation.model.Menu"/>--%>
<%--    <p>${menu.restaurant}</p>--%>
<%--</c:forEach>--%>
</body>
</html>