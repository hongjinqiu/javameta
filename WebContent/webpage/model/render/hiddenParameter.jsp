<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<div id="q_${hiddenParameterForJsp.name}_render" style="display: none">
	<input type="hidden" id="q_${hiddenParameterForJsp.name}_hidden" name="${hiddenParameterForJsp.name}" value="" />
</div>
<script type="text/javascript">
g_yuiCommondLi.push(function() {
	var queryParameterManager = new QueryParameterManager();
	var field = queryParameterManager.getQueryField('q_${hiddenParameterForJsp.name}_hidden', '${hiddenParameterForJsp.name}');
	g_masterFormFieldDict['${hiddenParameterForJsp.name}'] = field;
});
g_yuiCommondLi[g_yuiCommondLi.length - 1]();
</script>
