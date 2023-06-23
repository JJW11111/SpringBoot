<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="../include/headerfooter/header.jsp" %>
<%@ include file="../include/sub03/sub_image_menu.jsp" %>

<article>
<h2>${title}</h2>
<form name="formm" method="post">
<table id="cartList">
	<tr><th>주문일자</th><th>주문번호</th><th>상품명</th>
        	<th>결제 금액</th> <th>주문 상세</th> <th>처리상태</th></tr>
      	<c:forEach items="${orderList}"  var="orderVO">
      	<tr><td><fmt:formatDate value="${orderVO.indate}" type="date"/></td>
      		<td>${orderVO.oseq} </td>   	<td>${orderVO.pname} </td>
        	<td><fmt:formatNumber value="${orderVO.price2}" type="currency"/> </td>
        	<td><a href="orderDetail?oseq=${orderVO.oseq}">조회</a></td><td>
        	<c:choose>
				<c:when test="${orderVO.result=='4'}">구매확정</c:when>
				<c:otherwise >진행중</c:otherwise>
			</c:choose>	
				</td></tr>
				
      	</c:forEach>    
</table>
<div class="clear"></div>
<div id="buttons" style="float: right">
	<input type="button"    value="쇼핑 계속하기"  class="cancel"  onclick="location.href='/'"> 
</div>
</form>
</article>

<%@ include file="../include/headerfooter/footer.jsp" %>