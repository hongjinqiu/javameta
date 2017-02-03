<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%-- 
<c:set var="itemStatusJsp" value="${itemStatus}" scope="request"></c:set>
<c:set var="columnModelForJsp" value="${item}" scope="request"></c:set>
 --%>
<div id="${columnModelForJsp.name}_${columnModelForJsp.dataSetId}_${itemStatusJsp.index}" style="visibility: hidden;" class="form-main">
	<c:if test="${not empty columnModelForJsp.idColumn}">
		<c:if test="${not empty columnModelForJsp.idColumn.hideable and columnModelForJsp.idColumn.hideable}">
			<c:set var="hiddenColumnForJsp" value="${columnModelForJsp.idColumn}" scope="request"></c:set>
			<c:if test="${empty columnModelForJsp.idColumn.rendererTemplate}">
				<jsp:include page="/webpage/model/render/hiddenColumn.jsp"></jsp:include>
			</c:if>
			<c:if test="${not empty columnModelForJsp.idColumn.rendererTemplate}">
				<jsp:include page="${columnModelForJsp.idColumn.rendererTemplate}"></jsp:include>
			</c:if>
		</c:if>
	</c:if>
	<c:forEach items="${columnModelForJsp.columnList}" var="hiddenItem">
		<c:if test="${not empty hiddenItem.hideable and hiddenItem.hideable}">
			<c:set var="hiddenColumnForJsp" value="${hiddenItem}" scope="request"></c:set>
			<c:if test="${empty hiddenItem.rendererTemplate}">
				<jsp:include page="/webpage/model/render/hiddenColumn.jsp"></jsp:include>
			</c:if>
			<c:if test="${not empty hiddenItem.rendererTemplate}">
				<jsp:include page="${hiddenItem.rendererTemplate}"></jsp:include>
			</c:if>
		</c:if>
	</c:forEach>
	<c:forEach items="${masterRenderLi}" var="masterRenderItem">
		<c:if test="${masterRenderItem.key == columnModelForJsp.name}">
			<table border="0" width="100%" class="form-main-table">
				<c:forEach items="${masterRenderItem.value}" var="masterRenderValueItem" varStatus="masterRenderValueItemStatus">
					<tr>
						<c:forEach items="${masterRenderValueItem}" var="columnItem" varStatus="columnItemStatus">
							<c:set var="columnItemForJsp" value="${columnItem}" scope="request"></c:set>
							<c:if test="${empty columnItem.column.rendererTemplate}">
								<jsp:include page="/webpage/model/render/formColumn.jsp"></jsp:include>
							</c:if>
							<c:if test="${not empty columnItem.column.rendererTemplate}">
								<jsp:include page="${columnItem.column.rendererTemplate}"></jsp:include>
							</c:if>
						</c:forEach>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</c:forEach>
</div>
