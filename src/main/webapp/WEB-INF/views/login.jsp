<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Login12</title>
</head>

<body>
    <font color="red">${errorMessage}</font>
    <c:url var="loginUrl" value="/login" />
	<form action="${loginUrl}" method="post" class="form-horizontal">
        Name HELLO: <input type="text" name="name" />
        Password : <input type="password" name="password" /> 
        <input type="submit" />
    </form>
    <div>Version: ${buildVersion}</div>
</body>

</html>