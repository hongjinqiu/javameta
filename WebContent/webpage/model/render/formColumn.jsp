<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%-- 
<c:set var="columnItemForJsp" value="${columnItem}" scope="request"></c:set>
columnItemForJsp.column
 --%>
<td width="${columnItemForJsp.labelWidth}" class="searchTitle">
	<c:if test="${columnItemForJsp.required}">
		<font style="color: red">*</font>
	</c:if>
	${columnItemForJsp.label}：
</td>
<td width="${columnItemForJsp.columnWidth}" colspan="${columnItemForJsp.columnSpan}">
	<div id="${columnModelForJsp.name}_${columnModelForJsp.dataSetId}_${columnItemForJsp.name}_render">
		// todo
		<c:set value="" var="editorName"></c:set>
		<c:if test="${not empty columnItemForJsp.column.editor }"></c:if>
		
		<c:if test="${parameterForJsp.queryParameter.editor == 'textfield'}">
			<input id="q_${parameterForJsp.name}_${parameterForJsp.queryParameter.editor}" name="${parameterForJsp.name}" type="text" class="${parameterForJsp.queryParameter.fieldCls}" style="${parameterForJsp.queryParameter.style}" />
		</c:if>
		<c:if test="${parameterForJsp.queryParameter.editor == 'textareafield'}">
			<textarea id="q_${parameterForJsp.name}_${parameterForJsp.queryParameter.editor}" name="${parameterForJsp.name}" class="${parameterForJsp.queryParameter.fieldCls}" style="${parameterForJsp.queryParameter.style}"></textarea>
		</c:if>
		<c:if test="${parameterForJsp.queryParameter.editor == 'numberfield'}">
			<input id="q_${parameterForJsp.name}_${parameterForJsp.queryParameter.editor}" name="${parameterForJsp.name}" type="text" class="${parameterForJsp.queryParameter.fieldCls}" style="${parameterForJsp.queryParameter.style}" />
		</c:if>
		<c:if test="${parameterForJsp.queryParameter.editor == 'datefield'}">
			<c:if test="${parameterForJsp.queryParameter.dbPattern == 'yyyyMMdd'}">
				<input id="q_${parameterForJsp.name}_${parameterForJsp.queryParameter.editor}" name="${parameterForJsp.name}" type="text" class="${parameterForJsp.queryParameter.fieldCls}" style="${parameterForJsp.queryParameter.style}" />
			</c:if>
			<c:if test="${parameterForJsp.queryParameter.dbPattern == 'yyyyMMddHHmmss'}">
				<input id="q_${parameterForJsp.name}_${parameterForJsp.queryParameter.editor}" name="${parameterForJsp.name}" type="text" class="${parameterForJsp.queryParameter.fieldCls}" style="${parameterForJsp.queryParameter.style}" />
			</c:if>
			<c:if test="${parameterForJsp.queryParameter.dbPattern == 'HHmmss'}">
				<input id="q_${parameterForJsp.name}_${parameterForJsp.queryParameter.editor}" name="${parameterForJsp.name}" class="${parameterForJsp.queryParameter.fieldCls}" style="${parameterForJsp.queryParameter.style}" />
			</c:if>
		</c:if>
		<c:if test="${parameterForJsp.queryParameter.editor == 'combofield'}">
			<input id="q_${parameterForJsp.name}_${parameterForJsp.queryParameter.editor}" name="${parameterForJsp.name}" class="${parameterForJsp.queryParameter.fieldCls}" style="${parameterForJsp.queryParameter.style}" />
		</c:if>
		<c:if test="${parameterForJsp.queryParameter.editor == 'displayfield'}">
			<span id="q_${parameterForJsp.name}_${parameterForJsp.queryParameter.editor}" name="${parameterForJsp.name}" class="${parameterForJsp.queryParameter.fieldCls}" style="${parameterForJsp.queryParameter.style}">
			</span>
		</c:if>
		<c:if test="${parameterForJsp.queryParameter.editor == 'checkboxfield'}">
			<input id="q_${parameterForJsp.name}_${parameterForJsp.queryParameter.editor}" name="${parameterForJsp.name}" class="${parameterForJsp.queryParameter.fieldCls}" style="${parameterForJsp.queryParameter.style}" />
		</c:if>
		<c:if test="${parameterForJsp.queryParameter.editor == 'radiofield'}">
			<input id="q_${parameterForJsp.name}_${parameterForJsp.queryParameter.editor}" name="${parameterForJsp.name}" class="${parameterForJsp.queryParameter.fieldCls}" style="${parameterForJsp.queryParameter.style}" />
		</c:if>
		<c:if test="${parameterForJsp.queryParameter.editor == 'triggerfield'}">
			<input id="q_${parameterForJsp.name}_${parameterForJsp.queryParameter.editor}" name="${parameterForJsp.name}" type="text" class="${parameterForJsp.queryParameter.fieldCls}" style="${parameterForJsp.queryParameter.style}" />
			<input type="button" id="q_${parameterForJsp.name}_${parameterForJsp.queryParameter.editor}_select" name="selectBtn" value="选择" />
			<input type="button" id="q_${parameterForJsp.name}_${parameterForJsp.queryParameter.editor}_view" name="viewBtn" value="查看" />
			<input type="button" id="q_${parameterForJsp.name}_${parameterForJsp.queryParameter.editor}_delete" name="deleteBtn" value="删除" />
		</c:if>
	</div>
	<script type="text/javascript">
	g_yuiCommondLi.push(function() {
		var formFieldFactory = new FormFieldFactory();
		var field = formFieldFactory.getFormField('', '${columnItemForJsp.name}', '${columnModelForJsp.dataSetId}');
		g_masterFormFieldLi.push(field);
		g_masterFormFieldDict['${columnItemForJsp.name}'] = field;
	});
	g_yuiCommondLi[g_yuiCommondLi.length - 1]();
	</script>
</td>
