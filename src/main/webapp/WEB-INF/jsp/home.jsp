<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Spring | Welcome</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body style="">
    <style>
        <%@include file="../css/master.css"%>
        <%@include file="../css/bootstrap/bootstrap.min.css"%>
    </style>


    <c:choose>
        <c:when test="${downloadingSuccess == true}">
            <h3>Downloaded successfully.</h3>
        </c:when>
        <c:when test="${downloadingSuccess == false}">
            <h3>Downloading failed.</h3>
        </c:when>
    </c:choose>


    <c:choose>
        <c:when test="${!ftpClientIsConnected}">
            <jsp:include page="creatingConnection.jsp"/>
        </c:when>
        <c:otherwise>
            <h3>Connection is created</h3>
            <form class="connection_form" action="filesList">
                <input type="submit" value="Show files">
            </form>
            <form action="closeConnection" class="connection_form">
                <input type="submit" value="Destroy connection">
            </form>
        </c:otherwise>
    </c:choose>
</body>
</html>