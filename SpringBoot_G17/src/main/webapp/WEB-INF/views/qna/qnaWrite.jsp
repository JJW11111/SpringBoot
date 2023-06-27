<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/headerfooter/header.jsp" %>
<%@ include file="../include/sub04/sub_image_menu.jsp" %> 

<article>
<h2> 1:1 고객 게시판 </h2>
<h3> 고객님의 질문에 대해서 운영자가 1:1 답변을 드립니다.</h3>    
<form name="formm" method="post" 	action="qnaWrite">
    <fieldset><legend>Board Info</legend>
    	<label>Title</label><input type="text" name="subject"  size="60" ><br>
    	
    	<label>Secret mode</label>
    		<input name="check" type="checkbox" value="secret" onchange="enabled()">
    			&nbsp;비밀글로 하기&nbsp;&nbsp;
    		<input name="pass" type="password" size="15" disabled="disabled"><br>
    	
    	<label>Content</label><textarea rows="8" cols="65" name="content" ></textarea><br>
    </fieldset>   
    <div class="clear"></div>
    <div id="buttons" style="float:right">
	    <input type="submit"  value="글쓰기"     class="submit"> 
    	<input type="reset"   value="취소"     class="cancel">
    	<input type="button"  value="쇼핑 계속하기"  class="submit"  onclick="location.href='/'"> 
    </div>
</form>
<%@ include file="../include/headerfooter/footer.jsp" %>