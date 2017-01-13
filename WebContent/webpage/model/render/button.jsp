<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<c:if test="${buttonForJsp.mode == 'fn'}">
	<input type="button" id="${buttonForJsp.name}" value="${buttonForJsp.text}" class="${buttonForJsp.iconCls}" onclick="${buttonForJsp.handler}('${buttonForJsp.name}')"/>
</c:if>
<c:if test="${buttonForJsp.mode == 'url'}">
	<input type="button" id="${buttonForJsp.name}" value="${buttonForJsp.text}" class="${buttonForJsp.iconCls}" onclick="location.href='${buttonForJsp.handler}'"/>
</c:if>
<c:if test="${buttonForJsp.mode == 'url^'}">
	<input type="button" id="${buttonForJsp.name}" value="${buttonForJsp.text}" class="${buttonForJsp.iconCls}" onclick="window.open('${buttonForJsp.handler}')"/>
</c:if>
