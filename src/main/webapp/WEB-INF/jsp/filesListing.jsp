<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Spring | Files Listing</title>
</head>
<body>
    <h4>Current working directory: ${currentWorkingDirectory}</h4>
    <c:forEach var="file" items="${files}">
        <c:choose>
            <c:when test="${file.isFile()}">
                <p><a href="downloadFile?file=${file.getName()}"><c:out value="${file.getName()}  -- FILE" /></a></p>
            </c:when>
            <c:otherwise>
                <p><a href="changeRemoteDirectory?dir=${file.getName()}"><c:out value="${file.getName()} -- DIR" /></a></p>
            </c:otherwise>
        </c:choose>

    </c:forEach>
</body>
</html>