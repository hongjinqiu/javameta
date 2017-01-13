<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<div id="${columnModelForJsp.name}_tb">
	<c:forEach items="${columnModelForJsp.toolbar.buttonGroupOrButtonOrSplitButton}" var="buttonItem" varStatus="buttonStatus">
		<c:set value="${buttonItem}" var="buttonForJsp" scope="request"></c:set>
		<c:if test="${empty buttonItem.rendererTemplate}">
			<jsp:include page="/webpage/model/render/columnModel_list_toolbar_button.jsp"></jsp:include>
		</c:if>
		<c:if test="${not empty buttonItem.rendererTemplate}">
			<jsp:include page="${buttonItem.rendererTemplate}"></jsp:include>
		</c:if>
	</c:forEach>
</div>