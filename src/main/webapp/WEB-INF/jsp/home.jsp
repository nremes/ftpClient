<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Spring | Welcome</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script src="../js/bootstrap/bootstrap.bundle.js" type="text/javascript"></script>
</head>
<body style="">
    <style>
        <%@include file="../css/master.css"%>
        <%@include file="../css/bootstrap/bootstrap.min.css"%>
    </style>



    <c:choose>
        <c:when test="${!ftpClientIsConnected}">
            <jsp:include page="creatingConnection.jsp"/>
        </c:when>
        <c:otherwise>
            <h3>Connection is created</h3>
            <form class="connection_form" action="filesList">
                <input type="submit" value="Show files">
            </form>
            <form action="closeConnection">
                <input type="submit" value="Destroy connection">
            </form>
        </c:otherwise>
    </c:choose>
</body>
</html>