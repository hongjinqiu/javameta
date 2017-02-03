<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<div id="${columnModelForJsp.name}_${columnModelForJsp.dataSetId}_${hiddenColumnForJsp.name}_render" style="display: none">
	<input type="hidden" id="${columnModelForJsp.name}_${columnModelForJsp.dataSetId}_${hiddenColumnForJsp.name}_hidden" name="${hiddenColumnForJsp.name}" value="" />
</div>
<script type="text/javascript">
g_yuiCommondLi.push(function() {
	var formFieldFactory = new FormFieldFactory();
	var field = formFieldFactory.getFormField('${columnModelForJsp.name}_${columnModelForJsp.dataSetId}_${hiddenColumnForJsp.name}_hidden', '${hiddenColumnForJsp.name}', '${columnModelForJsp.dataSetId}');
	g_masterFormFieldLi.push(field);
	g_masterFormFieldDict['${hiddenColumnForJsp.name}'] = field;
});
g_yuiCommondLi[g_yuiCommondLi.length - 1]();
</script>
