<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="jumbotron">
    <h1>FTP Client</h1>
</div>
<div class="home_line"></div>
<form class="connection_form" METHOD="post" action="createConnection">
    <p><input type="text" name="hostName" placeholder="Hostname"></p>
    <p><input type="text" name="port" placeholder="Port"></p>
    <p><input type="text" name="userName" placeholder="Username"></p>
    <p><input type="password" name="password" placeholder="Password"></p>
    <p><input type="text" name="encoding" value="UTF-8"></p>
    <p><input class="button_submit" type="Submit" value="Connect"></p>
</form>