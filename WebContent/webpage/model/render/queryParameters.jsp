<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<c:set value="${fn:length(queryParameterRenderLi)}" var="queryParameterRenderLen"></c:set>
<div id="queryParameters" class="queryDiv" <c:if test="${queryParameterRenderLen == 0}">style="display: none;"</c:if>>
	<div class="queryTitle">查询条件</div>
	<form action="/component/listtemplate" method="post" name="queryForm" id="queryForm">
		<c:forEach items="${hiddenParameterLi}" var="item" varStatus="itemStatus">
			<c:if test="${empty item.rendererTemplate}">
				<c:set value="${item}" var="hiddenParameterForJsp" scope="request"></c:set>
				<jsp:include page="/webpage/model/render/hiddenParameter.jsp"></jsp:include>
			</c:if>
			<c:if test="${not empty item.rendererTemplate}">
				<jsp:include page="${item.rendererTemplate}"></jsp:include>
			</c:if>
		</c:forEach>
		<div id="queryContent" class="queryContent">
			<table id="queryMain" class="queryMain" cellspacing="0" cellpadding="0" border="0">
				<!-- {{range $index, $item := .result.queryParameterRenderLi}} -->
				<c:forEach items="${queryParameterRenderLi}" var="item" varStatus="itemStatus">
					<c:forEach items="${item}" var="tdItem" varStatus="tdItemStatus">
						<c:if test="${empty tdItem.queryParameter.rendererTemplate}">
							<c:set value="${item}" var="parameterForJsp" scope="request"></c:set>
							<jsp:include page="/webpage/model/render/queryParameter.jsp"></jsp:include>
						</c:if>
						<c:if test="${not empty tdItem.queryParameter.rendererTemplate}">
							<jsp:include page="${tdItem.queryParameter.rendererTemplate}"></jsp:include>
						</c:if>
					</c:forEach>
					<c:if test="${itemStatus.index == 0}">
						<td>
							<c:if test="${queryParameterRenderLen > 1}">
								<input style="display: inline;" class="btnMore" id="btnMore" value="更多条件" type="button" />
								<input style="display: none;" id="btnUp" class="btnUp" value="收回" type="button" />
							</c:if>
							<input type="button" value="查询" id="queryBtn" class="queryBtn" />
							<input type="button" value="重置" id="queryReset" class="queryReset" />
						</td>
					</c:if>
					<c:if test="${itemStatus.index != 0}">
						<td>&nbsp;</td>
					</c:if>
				</c:forEach>
			</table>
		</div>
	</form>
</div>
