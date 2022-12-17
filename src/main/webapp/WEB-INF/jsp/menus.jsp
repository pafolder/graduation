<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<html>
<%--<%@page contentType="text/html" pageEncoding="UTF-8" %>--%>
<%--<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>--%>

<head>
    <%--    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">--%>
    <title>Menus</title>
    <%--    <base href="${pageContext.request.contextPath}/"/>--%>
    <%--    <link rel="stylesheet" href="resources/css/style.css">--%>


    <link rel="stylesheet" href="/webjars/bootstrap/5.2.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/style.css?v=2">

    <script src="webjars/jquery/3.6.1/dist/jquery.min.js" defer></script>
    <script src="webjars/bootstrap/5.2.3/dist/js/bootstrap.min.js" defer></script>
    <script type="text/javascript" src="resources/js/menus.js" defer></script>
</head>
<body>
<div class="container">
    <h1>It's my first JSP file!</h1>
    <h2>Here are the menus:</h2>
    <button class="btn btn-success">Green</button>
</div>
<div>
    <p> Current user <b>"${currentUser}"</b></p>
</div>

<c:forEach var="menu" items="${menus}">
    <p>${menu.restaurant}</p>
</c:forEach>

<%--<c:forEach items="${menus}" var="menu">--%>
<%--<jsp:useBean id="menu" type="com.pafolder.graduation.model.Menu"/>--%>
<%--<jsp:useBean id="menu" type="com.pafolder.graduation.model.Menu"/>--%>
<%--    <p>${menu.restaurant}</p>--%>
<%--</c:forEach>--%>
</body>
</html>