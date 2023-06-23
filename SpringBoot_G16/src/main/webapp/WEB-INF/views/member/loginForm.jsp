<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>loginForm</title>

<link rel="stylesheet" type="text/css" href="/css/board.css" >
<script type="text/javascript" src="/script/board.js"></script>

</head>
<body>
<form action="login" method="post">
		<div class="box"><div id="title">로그인</div></div>
		<div class="box"><div class="attr1">아이디</div>
			<div class="attr2">&nbsp;&nbsp;<input type="text" size="20" name="userid"  
					style="width:200px;height:20px;" value="${dto.userid}"></div></div>
		<div class="box">
			<div class="attr1">비밀번호</div>
			<div class="attr2">&nbsp;&nbsp;
				<input type="password" size="20" name="pwd" style="width:200px;height:20px;" >
			</div>
		</div>
		<div class="box"><div id="footer">
			<input type="submit" value="로그인" />
			<input type="reset" value="다시작성" />
			<input type="button" value="일반회원가입" onClick="location.href='memberJoinForm'"/>
			<!-- <input type="button" value="카카오로 회원가입 및 로그인"	onclick="location.href='kakaostart'"> -->
			<hr>
				<a href="kakaostart"><img src="/images/kakao.png"    style="width:300px;"></a>
				<img src="/images/naver.png"     style="width:300px;">
				<img src="/images/google.png"    style="width:300px;">
				<img src="/images/facebook.png"    style="width:300px;">
			</div></div>
			
		<div class="box"><div id="footer">${message}</div></div>
</form>

</body>
</html>