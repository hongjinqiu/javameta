<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%-- 
<c:set var="itemStatusJsp" value="${itemStatus}" scope="request"></c:set>
<c:set var="columnModelForJsp" value="${item}" scope="request"></c:set>
 --%>
<div id="${columnModelForJsp.dataSetId}_editor" style="display: none;">
	<div class="form-main">
		<c:if test="${not empty columnModelForJsp.idColumn}">
			<c:if test="${not empty columnModelForJsp.idColumn.hideable and columnModelForJsp.idColumn.hideable}">
				<c:set var="hiddenColumnForJsp" value="${columnModelForJsp.idColumn}" scope="request"></c:set>
				<!-- idcolumn没有editor,不用判断rendererTemplate,直接用hiddenColumn_editor.jsp -->
				<jsp:include page="/webpage/model/render/hiddenColumn_list_editor.jsp"></jsp:include>
			</c:if>
		</c:if>
		<c:forEach items="${columnModelForJsp.columnList}" var="hiddenItem">
			<c:if test="${not empty hiddenItem.hideable and hiddenItem.hideable and hiddenItem.xmlName != 'virtual-column'}">
				<c:set var="hiddenColumnForJsp" value="${hiddenItem}" scope="request"></c:set>
				<c:if test="${empty hiddenItem.editorRendererTemplate}">
					<jsp:include page="/webpage/model/render/hiddenColumn_list_editor.jsp"></jsp:include>
				</c:if>
				<c:if test="${not empty hiddenItem.editorRendererTemplate}">
					<jsp:include page="${hiddenItem.editorRendererTemplate}"></jsp:include>
				</c:if>
			</c:if>
		</c:forEach>
		<c:forEach items="${detailRenderLi}" var="detailRenderItem">
			<c:if test="${detailRenderItem.key == columnModelForJsp.name}">
				<table border="0" width="100%" class="form-main-table">
					<c:forEach items="${detailRenderItem.value}" var="detailRenderValueItem" varStatus="detailRenderValueItemStatus">
						<tr>
							<c:forEach items="${detailRenderValueItem}" var="columnItem" varStatus="columnItemStatus">
								<c:set var="columnItemForJsp" value="${columnItem}" scope="request"></c:set>
								<c:if test="${empty columnItem.column.editorRendererTemplate}">
									<jsp:include page="/webpage/model/render/formColumn_list_editor.jsp"></jsp:include>
								</c:if>
								<c:if test="${not empty columnItem.column.editorRendererTemplate}">
									<jsp:include page="${columnItem.column.editorRendererTemplate}"></jsp:include>
								</c:if>
							</c:forEach>
						</tr>
					</c:forEach>
				</table>
			</c:if>
		</c:forEach>
	</div>
</div>
