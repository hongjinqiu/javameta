<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<div style="margin-top: 10px;">
	<table id="${columnModelForJsp.name}"></table>
</div>
<script type="text/javascript">
g_delayLi.push(function() {
	createSelectorTemplateGrid();
});
</script>
