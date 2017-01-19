<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<!-- 
parameterForJsp
item.put("label", queryParameter.getText());
item.put("name", queryParameter.getName());
item.put("queryParameter", queryParameter);
item.put("nameColSpan", columnColSpan);
 -->
<td class="searchTitle">
	${parameterForJsp.label}：
</td>
<td colspan="${parameterForJsp.nameColSpan}">
	<div id="q_${parameterForJsp.name}_render">
		<%-- <input type="hidden" id="q_${hiddenParameterForJsp.name}_hidden" name="${hiddenParameterForJsp.name}" value="" /> --%>
		<c:if test="${parameterForJsp.queryParameter.editor == 'textfield' }">
			<input id="q_${parameterForJsp.name}_${parameterForJsp.queryParameter.editor}" name="${parameterForJsp.name}" type="text" class="" style="" />
		</c:if>
		<c:if test="${parameterForJsp.queryParameter.editor == 'textareafield' }">
			<textarea id="q_${parameterForJsp.name}_${parameterForJsp.queryParameter.editor}" name="${parameterForJsp.name}" class=""></textarea>
		</c:if>
		<!-- 
			} else if (queryParameter.editor == "textfield") {
				field=newLTextField({id:id,name:name,validateInline:true,readonly:readOnly});
				$("#" + self.config.id).textbox({});			
			} else if (queryParameter.editor == "textareafield") {
				field=newLTextareaField({id:id,name:name,validateInline:true,readonly:readOnly});
				只有validatebox,没有.textbox(之类的,
			} else if (queryParameter.editor == "numberfield") {
				field=newLNumberField({id:id,name:name,validateInline:true,readonly:readOnly});
				$("#" + self.config.id).numberbox({});
			} else if (queryParameter.editor == "datefield") {
				field=newLDateField({id:id,name:name,validateInline:true,readonly:readOnly});
				$("#" + self.config.id).datebox({
				$("#" + self.config.id).datetimebox({
				$("#" + self.config.id).timespinner({
			} else if (queryParameter.editor == "combofield") {
				field=newLSelectField({id:id,name:name,validateInline:true,readonly:readOnly});
				$("#" + self.config.id).combobox({
			} else if (queryParameter.editor == "displayfield") {
				field=newLDisplayField({id:id,name:name,validateInline:true,readonly:readOnly});
				没有textfield,也没有validatebox
			} else if (queryParameter.editor == "checkboxfield") {
				field=newLChoiceField({id:id,name:name,validateInline:true,multiple:true,readonly:readOnly});
				$("#" + self.config.id).combobox({
			} else if (queryParameter.editor == "radiofield") {
				field=newLChoiceField({id:id,name:name,validateInline:true,readonly:readOnly});
				$("#" + self.config.id).combobox({
			} else if (queryParameter.editor == "triggerfield") {
				field=newLTriggerField({id:id,name:name,validateInline:true,readonly:readOnly});
				$("#" + self.config.id).textbox({
				$("#" + id + "_select")
				$("#" + self.config.id + "_view").show();
				$("#" + id + "_delete"),
				self._applySelectBtnEventBehavior();
				self._applyViewBtnEventBehavior();
				self._applyDeleteBtnEventBehavior();
			}
		 -->
	</div>
	<script type="text/javascript">
	g_yuiCommondLi.push(function() {
		var queryParameterManager = new QueryParameterManager();
		var field = queryParameterManager.getQueryField('q_${parameterForJsp.name}_${parameterForJsp.queryParameter.editor}', '${parameterForJsp.name}');
		//field.render("#q_{{$tdItem.name}}_render");
		g_masterFormFieldDict['${parameterForJsp.name}'] = field;
	});
	g_yuiCommondLi[g_yuiCommondLi.length - 1]();
	</script>
</td>
