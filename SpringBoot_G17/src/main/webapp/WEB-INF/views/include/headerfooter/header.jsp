<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link  rel="stylesheet" href="/css/shopping.css">  
<script type="text/javascript" src="/script/member.js"></script>
<script type="text/javascript" src="/script/mypage.js"></script>

<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script type="text/javascript">
    $(function(){
    	var num=0;
    	setInterval(function(){
            $('#imgs').animate({ left : num * -968 },1000);
                num++;
                if(num==Number( '${size}' ) )num=0;
        }, 2000);
    });
</script>



</head>
<body>


<div id="wrap">
	<header>
		<div id="logo"><a href="/"><img src="/images/logo.png"  width="180" height="100"></a></div>
		
		<nav id="top_menu"> <!-- top menu -->
			<ul>
				<c:choose>
					<c:when test="${empty loginUser}">
						<li><a href="loginForm">LOGIN</a></li>
						<li><a href="contract">JOIN</a></li>
					</c:when>
		    		<c:otherwise>
		       			<li style="color:blue;font-weight:bold;font-size:100%;width:150px; ">${loginUser.NAME}(${loginUser.ID})</li>
		       			<li><a href="memberEditForm">정보수정</a></li>
		       			<li><a href="logout">LOGOUT</a></li>
					</c:otherwise>
				</c:choose>
				<li><a href="cartList">CART</a></li>
				<li><a href="myPage">MY PAGE</a></li>
		    	<li ><a href="customer">고객센터</a></li>
		    	<li ><a href="admin">admin</a></li>
			</ul>
		</nav>
		<nav id="catagory_menu"> <!-- catagory menu -->
			<ul>
				<li><a href="catagory?kind=1">Heels</a></li>
				<li><a href="catagory?kind=2">Boots</a></li>
				<li><a href="catagory?kind=3">Sandals</a></li>
				<li><a href="catagory?kind=4">Sneakers</a></li>
				<li><a href="catagory?kind=5">Sleeper</a></li>	
				<li><a href="catagory?kind=6">On Sale</a></li>
			</ul>
		</nav>
		<div class="clear"></div><hr>
	</header>