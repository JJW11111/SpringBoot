<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/admin/header.jsp"%>
<%@ include file="../../include/admin/sub_menu.jsp"%>

<article>

	<h1>배너 리스트</h1>
	<form name="frm" method="post">
		<table>
			<tr><td width="800" align="right">
				<input class="btn" type="button" name="btn_write" value="새배너 등록" 
						onClick="location.href='newBannerWrite'"></td></tr>
		</table>
		<table id="productList">
			<tr><th>번호</th><th>제목</th><th>순위</th><th>사용유무 </th><th>등록일</th><th>수정 </th></tr>
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

</article>

<%@ include file="../../include/admin/footer.jsp"%>