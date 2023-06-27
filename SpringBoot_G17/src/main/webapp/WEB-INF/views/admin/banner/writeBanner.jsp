<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/admin/header.jsp" %> 
<%@ include file="../../include/admin/sub_menu.jsp" %>
<article>
<h1>배너 등록</h1>  
<form name="frm" >
	<table id="list">
		<tr><th>제목 </th><td width="642" >
			<input type="text" name="subject" size="47" ></td></tr>
		<tr><th>순위 </th>
			<td width="642">
				<select name="order_seq">
					<option value="">디스플레이될 순서를 선택하세요 </option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">사용안함 </option>
				</select>
			</td></tr>
		<tr height="250"><th>배너 이미지</th>
	    	<td width="642" style="vertical-align:top;">
	   			<input type="hidden" name="image" id="image" value="">
	   			<div id="filename"></div></td></tr>
	</table>
	<input class="btn" type="button" value="등록" onClick="go_banner_save()">   
	<input class="btn" type="button" value="취소" onClick="go_mov()">
	</form>
	<div style="position:relative; top:-70px; ">
		<form name="fromm" id="fileupForm" method="post" enctype="multipart/form-data">
					<input type="file" name="fileimage"><input type="button" id="myButton" value="추가">
		</form>
	</div></article>
<%@ include file="../../include/admin/footer.jsp" %>