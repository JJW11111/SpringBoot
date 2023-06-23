<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<script type="text/javascript">
$(function(){
	$('#myButton').click( function(){
		
		var formselect = $("#fileupForm")[0];   // 지목된 폼을 변수에 저장
		var formdata = new FormData(formselect);   // 전송용 폼객에 다시 저장
		
		$.ajax({    // 웹페이지 이동 또는 새로고침이 필요없는 request요청
			url:"<%=request.getContextPath() %>/fileup",    // 현재주소의 fileup 리퀘스트로 요청  http://localhost:8070/fileup
			type:"POST",
			enctype:"multipart/form-data",
			async: false, 
			data: formdata,
	    	timeout: 10000,
	    	contentType : false,
	        processData : false,
	        
	        success : function(data){    // controller 에서 린턴된 해시맵이  data 로 전달됩니다
	            if( data.STATUS == 1 ){  	// 동적으로 div태그 달아주기.
	            	$("#filename").append("<div>"+data.FILENAME+"</div>");
	            	$("#image").val(data.FILENAME);
	            	$("#filename").append("<img src='product_images/"+data.FILENAME+"' height='150'/>");
	            }
	        },
	        error: function() {				alert("실패");			}
		});
	
	});
});
</script>

</head>
<body>
<article>

	<h1>배너 리스트</h1>
	<form name="frm" method="post">
		<table >
			<tr><td width="800" align="right">
				<input class="btn" type="button" name="btn_write" value="새배너 등록" 
						onClick="location.href='newBannerWrite'"></td></tr>
		</table>
		<table border="1"  cellspacing="0"  width="800">
			<tr>
				<th width="100">번호</th>
				<th>제목</th>
				<th  width="100">순위</th>
				<th  width="100">사용유무 </th>
				<th  width="100">등록일</th>
				<th  width="100">수정 </th></tr>
			<c:choose>
		    	<c:when test="${bannerListSize==0}">
		    			<tr><td width="100%" colspan="6" align="center" height="23">등록된 상품이 없습니다.</td></tr>
		    	</c:when>
		    	<c:otherwise>
		    			<c:forEach items="${bannerList}" var="bannerVO">
		    				<tr>
		    					<td height="23" align="center" >${bannerVO.bseq}</td>
		    					<td style="text-align:left; padding-left:50px; padding-right:0px;width:300px;">
		    						${bannerVO.subject}
		    					</td>
		    					<td>
			    						<select name="order_seq" id="${bannerVO.bseq}"	
			    								onChange="change_order('${bannerVO.bseq}');">
				    							<c:forEach var="cnt" begin="1" end="5" varStatus="status">
				    								<c:choose>
														<c:when test="${cnt==bannerVO.order_seq}">
															<option value="${cnt}" selected>${cnt}</option>
														</c:when>
														<c:otherwise>
															<option value="${cnt}" >${cnt}</option>
														</c:otherwise>
													</c:choose>
				    							</c:forEach>
				    							<c:choose>
													<c:when test="${bannerVO.order_seq==6}">
														<option value="6" selected>사용안함</option>
													</c:when>
													<c:otherwise>
														<option value="6" >사용안함</option>
													</c:otherwise>
												</c:choose>
			    						</select>
		    					</td>
		    					<td>${bannerVO.useyn}</td>
		    					<td width="150"><fmt:formatDate value="${bannerVO.indate}"/></td>
			      				<td><input type="button" value="수정" 
			      					onClick="location.href='editBannerForm?bseq=${bannerVO.bseq}'"></td></td>
		    				</tr>
		    			</c:forEach>
		    	</c:otherwise>
		    </c:choose>
		</table>
	</form>
	
	<a href="moveBannerPage" target="blank_"><h2>롤링베너로 이동</h2></a>

</article>
</body>
</html>