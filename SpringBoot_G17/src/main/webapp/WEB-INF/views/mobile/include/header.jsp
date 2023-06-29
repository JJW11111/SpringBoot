<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link  rel="stylesheet" href="/css/mobile.css">


</head>
<body>
<div id="wrap">
<header>
	<c:choose>
		<c:when test="${empty loginUser}">
			<nav id="top_menu" > <!-- top menu -->
				<ul>
					<li><a href="mloginForm">Login</a></li><li><a href="mcontract">Join</a></li>
					<li><a href="mcartList">Cart</a></li>	<li><a href="mmyPage">My Page</a></li>
					<li ><a href="mqnaList">Q&amp;A</a></li>
				</ul>
			</nav>
		</c:when>
		<c:otherwise>
			<nav id="top_menu" > <!-- top menu -->
				<ul>
					<li><span style="color:yellow;">${loginUser.id}</span>
						<a href="mmemberEditForm.do"> · 정보수정</a><a href="mlogout"> · Logout</a></li>
					<li><a href="mcartList">Cart</a></li>
					<li><a href="mmyPage">My Page</a></li><li ><a href="mqnaList">Q&amp;A</a></li>
			    </ul>
			</nav>
		</c:otherwise>
	</c:choose>
	<div id="logo"><a href="/">Shoes Shop</a></div>
</header>