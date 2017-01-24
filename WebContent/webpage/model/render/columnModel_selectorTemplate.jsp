<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<div style="margin-top: 10px;">
	<table id="${columnModelForJsp.name}"></table>
</div>
<script type="text/javascript">
g_yuiCommondLi.push(function() {
	createSelectorTemplateGrid();
});
g_yuiCommondLi[g_yuiCommondLi.length - 1]();
</script>
