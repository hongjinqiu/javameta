<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<div class="formToolbar form-hd">
	<c:forEach items="${toolbarBo}" var="toolbarBoItem">
		<c:if test="${toolbarBoItem.key == toolbarForJsp.name}">
			<c:forEach items="${toolbarBoItem.value}" var="buttonBoItem" varStatus="buttonBoItemStatus">
				<c:forEach items="${toolbarForJsp.buttonGroupOrButtonOrSplitButton}" var="buttonItem" varStatus="buttonStatus">
					<c:if test="${buttonBoItemStatus.index == buttonStatus.index}">
						<c:if test="${buttonBoItem.isShow}">
							<c:set value="${buttonItem}" var="buttonForJsp" scope="request"></c:set>
							<c:if test="${empty buttonItem.rendererTemplate}">
								<jsp:include page="/webpage/model/render/button.jsp"></jsp:include>
							</c:if>
							<c:if test="${not empty buttonItem.rendererTemplate}">
								<jsp:include page="${buttonItem.rendererTemplate}"></jsp:include>
							</c:if>
						</c:if>
					</c:if>
				</c:forEach>
			</c:forEach>
		</c:if>
	</c:forEach>
</div>
