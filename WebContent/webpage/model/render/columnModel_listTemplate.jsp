<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<table id="${columnModelForJsp.name}"></table>
<script type="text/javascript">
g_yuiCommondLi.push(function() {
	createListTemplateGrid();
});
g_yuiCommondLi[g_yuiCommondLi.length - 1]();
</script>
