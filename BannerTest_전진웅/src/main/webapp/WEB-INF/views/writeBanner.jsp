<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

<article>
<h1>배너 등록</h1>  
<form name="frm" action="bannerWrite" method="post">

	<table border="1"  cellspacing="0"  width="800">
		<tr>
			<th>제목 </th><td width="642" >
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
	    	<td width="642" style="vertical-align:top; ">
	   			<input type="hidden" name="image" id="image" value="">
	   			<div id="filename"></div></td></tr>
	</table>
	<input class="btn" type="submit" value="등록" >
	<input class="btn" type="reset" value="취소" >
	</form>
	
	
	
	<div style="position:relative; top:-70px; left:500px; ">
		<form name="fromm" id="fileupForm" method="post" enctype="multipart/form-data">
					<input type="file" name="fileimage">
					<input type="button" id="myButton" value="추가">
		</form>
	</div>
	
	
</article>
