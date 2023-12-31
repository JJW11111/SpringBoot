<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BoardView</title>
<link rel="stylesheet" type="text/css" href="/css/board.css" >
<script src="/script/board.js"></script>
</head>
<body>

<div id="wrap" align="center">
	<h1>게시글 상세보기</h1>
	<table>
		<tr><th>작성자</th><td>${board.USERID}</td><th>이메일</th><td>${board.EMAIL}</td></tr>
		<tr><th>작성일</th><td><fmt:formatDate value="${board.WRITEDATE}"/></td>
			<th>조회수</th><td>${board.READCOUNT }</td></tr>
		<tr><th>제목</th><td colspan="3">${board.TITLE}</td></tr>
		<tr><th>내용</th><td colspan="2"><pre>${board.CONTENT}</pre></td>
				<td  width="300" align="center">
						<c:choose>
							<c:when test="${empty board.IMGFILENAME}">
								<img src="/upload/noname.jpg"  width="250">
							</c:when>
							<c:otherwise>
								<img src="/upload/${board.IMGFILENAME}" width="250">
							</c:otherwise>
						</c:choose>
				</td>
		</tr>
	</table><br> <br>
	<input type="button" value="게시글 리스트" onclick="location.href='main'">
	<input type="button" value="게시글 수정" onclick="open_win('boardEditForm?num=${board.NUM}', 'update')">
	<input type="button" value="게시글 삭제" onclick="open_win('boardEditForm?num=${board.NUM}', 'delete')">
</div><br><br>

<c:set var="now" value="<%=new java.util.Date()%>"></c:set>
<div id="wrap" align="center">
<form action="addReply" method="post" name="frm2">
<input type="hidden" name="boardnum" value="${board.NUM}">
<table>
	<tr><th>작성자</th><th>작성일시</th><th>내용</th><th>&nbsp;</th></tr>
	<tr align="center">
		<td width="100">${loginUser.USERID}
			<input type="hidden" name="userid" value="${loginUser.USERID}"></td>
		<td width="100"><fmt:formatDate value="${now}"	pattern="MM/dd HH:mm" /></td>
		<td width="670"><input type="text" name="content" size="85"></td>
		<td width="100"><input type="submit" value="답글작성" onclick="return reply_check();"></td></tr>
	<c:forEach var="reply" items="${replyList}">
		<tr><td align="center">${reply.USERID}</td>
			<td align="center"><fmt:formatDate value="${reply.WRITEDATE}"	pattern="MM/dd HH:mm" /></td>
			<td>${reply.CONTENT}</td>
			<td align="center">
				<c:if test="${reply.USERID==loginUser.USERID}">
					<input type="button" value="삭제" 
				onclick="location.href='deleteReply?num=${reply.REPLYNUM}&boardnum=${reply.BOARDNUM}'">
				</c:if>&nbsp;</td></tr>
	</c:forEach>
</table><br /><br /><br />
</form>	
</div>


</body>
</html>







