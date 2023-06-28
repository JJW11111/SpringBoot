<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/headerfooter/header.jsp" %>
<%@ include file="../include/sub03/sub_image_menu.jsp" %> 

<article>
<h2> My Page(주문 상세 정보) </h2>
<form name="formm" method="post">
	<h3> 주문자 정보 </h3>
	<table id="cartList">
		<tr><th>주문일자</th><th>주문번호</th> <th>주문자</th><th>주문 총액</th></tr>
	   	<tr><td><fmt:formatDate value="${orderDetail.INDATE}" 	type="date"/></td>
		   <td> ${orderDetail.OSEQ} </td><td> ${orderDetail.MNAME} </td>
		   <td> <fmt:formatNumber type="currency"	value="${totalPrice}" /></td></tr>
		<tr>
			<td colspan="4">배송지 : ${orderDetail.ADDRESS1} ${orderDetail.ADDRESS2}
				${orderDetail.ADDRESS3}</td></tr>
	</table>
	<h3> 주문 상품 정보 </h3> 
	<table id="cartList">
		<tr><th>상품명</th><th>상품별주문번호</th> <th>수량</th><th>가격</th><th>처리 상태</th></tr>
	    <c:forEach items="${orderList}"  var="orderVO">
	    	<tr><td> ${orderVO.PNAME} </td><td> ${orderVO.ODSEQ}</td><td> ${orderVO.QUANTITY} </td>
				<td> <fmt:formatNumber type="currency" 	value="${orderVO.PRICE2*orderVO.QUANTITY}" /></td>
				<td>
					
					<c:choose>
				    	<c:when test='${orderVO.RESULT=="1"}'>결제완료</c:when>
						<c:when test='${orderVO.RESULT=="2"}'>배송중</c:when>
						<c:when test='${orderVO.RESULT=="3"}'>
							<span style="color:blue;font-weight:bold;">배송완료</span>
							<input type="button" value="구매확정" 
							onClick="orderEnd('${orderVO.OSEQ}', '${orderVO.ODSEQ}');">
						</c:when>
						<c:otherwise><span style="color:red;font-weight:bold;"> 구매확정 </span></c:otherwise>
			    	</c:choose>
					
			    </td>
			</tr>
	    </c:forEach>
	</table><div class="clear"></div>
	<div id="buttons" style="float:right">
		<input type="button" value="뒤로"  class="cancel"  onclick="history.go(-1)"></div>
</form>
</article>

<%@ include file="../include/headerfooter/footer.jsp" %>