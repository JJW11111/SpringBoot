<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/headerfooter/header.jsp" %>
<%@ include file="../include/sub01/sub_image_menu.html" %>

<article>
	<form method="post" action="login" name="loginFrm">
		<fieldset><legend>LogIn&nbsp;&nbsp;&nbsp;&nbsp;${message} </legend>
			<label>User ID</label><input name="id" type="text" value="${dto.id}"><br> 
			<label>Password</label><input name="pwd" type="password" >
			<div>
	            <input type="submit" value="로그인"  style="width:200px;">
	            <input type="button" value="일반회원가입"  onclick="location.href='contract'"  style="width:200px;">
	            <input type="button" value="아이디 비밀번호 찾기"  onclick=""  style="width:200px;">
	            <hr>
				<a href="kakaostart"><img src="/images/kakao.png"    style="width:300px;"></a>
				<img src="/images/naver.png"     style="width:300px;">
				<img src="/images/google.png"    style="width:300px;">
				<img src="/images/facebook.png"    style="width:300px;">
	        </div>
        </fieldset>
	</form>    
	
</article>

<%@ include file="../include/headerfooter/footer.jsp" %>