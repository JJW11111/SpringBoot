<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1><% out.println("MyBatis : UserList"); %></h1>
<c:forEach var="user" items="${users }">
	<h1>${user.id } / ${user.name }</h1>
</c:forEach>
</body>
</html>