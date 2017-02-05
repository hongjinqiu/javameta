<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<div id="${columnModelForJsp.name}_${columnModelForJsp.dataSetId}_${hiddenColumnForJsp.name}_render_{index}" style="display: none">
	<input type="hidden" id="${columnModelForJsp.name}_${columnModelForJsp.dataSetId}_${hiddenColumnForJsp.name}_hidden_{index}" name="${hiddenColumnForJsp.name}" value="" />
</div>
<script type="text/javascript">
g_gridCommondDict["${columnModelForJsp.dataSetId}"].push(function(index) {
	var formFieldFactory = new FormFieldFactory();
	var fieldId = '${columnModelForJsp.name}_${columnModelForJsp.dataSetId}_${hiddenColumnForJsp.name}_hidden_{index}';
	fieldId = fieldId.replace(/{index}/g, index);
	var field = formFieldFactory.getFormField(fieldId, '${hiddenColumnForJsp.name}', '${columnModelForJsp.dataSetId}');
	return field;
});
</script>
