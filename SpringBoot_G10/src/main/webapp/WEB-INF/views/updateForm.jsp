<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<table width="700" cellspacing="0" cellpadding="0" border="1">
		<form action="update" method="post">
			<tr>
				<td width="80">작성자</td>
				<td><input type="text" name="writer" size="80"
					value="${dto.writer }"></td>
			</tr>
			<tr>
				<td width="80">제목</td>
				<td><input type="text" name="title" size="80"
					value="${dto.title }"></td>
			</tr>
			<tr>
				<td width="80">내용</td>
				<td><input type="text" name="content" size="80"
					value="${dto.content }"></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="수정">&nbsp;&nbsp;
				<input type="hidden" name="id" value="${dto.id }"> 
				<a href="/">목록보기</a></td>
			</tr>
		</form>
	</table>
</body>
</html>