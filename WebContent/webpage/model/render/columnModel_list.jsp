<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<c:if test="${not empty columnModelForJsp.toolbar.buttonGroupOrButtonOrSplitButton}">
	<c:if test="${empty columnModelForJsp.toolbar.rendererTemplate}">
		<jsp:include page="/webpage/model/render/columnModel_list_toolbar.jsp"></jsp:include>
	</c:if>
	<c:if test="${not empty columnModelForJsp.toolbar.rendererTemplate}">
		<jsp:include page="${columnModelForJsp.toolbar.rendererTemplate}"></jsp:include>
	</c:if>
</c:if>

<table id="${columnModelForJsp.name}"></table>
<script type="text/javascript">
g_yuiCommondLi.push(function() {
	var dataTableManager = new DataTableManager();
	dataTableManager.createDataGridForColumnModel("${columnModelForJsp.name}");
});
</script>
