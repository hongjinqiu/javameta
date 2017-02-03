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
		<c:set value="" var="editorName"></c:set>
		<c:if test="${not empty columnItemForJsp.column.editor and not empty columnItemForJsp.column.editor.name}">
			<c:set value="${columnItemForJsp.column.editor.name}" var="editorName"></c:set>
		</c:if>
		<c:if test="${empty editorName}">
			<c:if test="${columnItemForJsp.column.xmlName == 'trigger-column'}">
				<c:set value="triggerfield" var="editorName"></c:set>
			</c:if>
			<c:if test="${columnItemForJsp.column.xmlName == 'string-column'}">
				<c:set value="textfield" var="editorName"></c:set>
			</c:if>
			<c:if test="${columnItemForJsp.column.xmlName == 'number-column'}">
				<c:set value="numberfield" var="editorName"></c:set>
			</c:if>
			<c:if test="${columnItemForJsp.column.xmlName == 'date-column'}">
				<c:set value="datefield" var="editorName"></c:set>
			</c:if>
			<c:if test="${columnItemForJsp.column.xmlName == 'dictionary-column'}">
				<c:set value="combofield" var="editorName"></c:set>
			</c:if>
		</c:if>
		
		<c:if test="${editorName == 'textfield'}">
			<input id="${columnModelForJsp.name}_${columnModelForJsp.dataSetId}_${columnItemForJsp.name}" name="${columnItemForJsp.name}" type="text" class="${columnItemForJsp.column.editorFieldCls}" style="${columnItemForJsp.column.editorStyle}" />
		</c:if>
		<c:if test="${editorName == 'textareafield'}">
			<textarea id="${columnModelForJsp.name}_${columnModelForJsp.dataSetId}_${columnItemForJsp.name}" name="${columnItemForJsp.name}" class="${columnItemForJsp.column.editorFieldCls}" style="${columnItemForJsp.column.editorStyle}"></textarea>
		</c:if>
		<c:if test="${editorName == 'numberfield'}">
			<input id="${columnModelForJsp.name}_${columnModelForJsp.dataSetId}_${columnItemForJsp.name}" name="${columnItemForJsp.name}" type="text" class="${columnItemForJsp.column.editorFieldCls}" style="${columnItemForJsp.column.editorStyle}" />
		</c:if>
		<c:if test="${editorName == 'datefield'}">
			<c:if test="${columnItemForJsp.column.dbPattern == 'yyyyMMdd'}">
				<input id="${columnModelForJsp.name}_${columnModelForJsp.dataSetId}_${columnItemForJsp.name}" name="${columnItemForJsp.name}" type="text" class="${columnItemForJsp.column.editorFieldCls}" style="${columnItemForJsp.column.editorStyle}" />
			</c:if>
			<c:if test="${columnItemForJsp.column.dbPattern == 'yyyyMMddHHmmss'}">
				<input id="${columnModelForJsp.name}_${columnModelForJsp.dataSetId}_${columnItemForJsp.name}" name="${columnItemForJsp.name}" type="text" class="${columnItemForJsp.column.editorFieldCls}" style="${columnItemForJsp.column.editorStyle}" />
			</c:if>
			<c:if test="${columnItemForJsp.column.dbPattern == 'HHmmss'}">
				<input id="${columnModelForJsp.name}_${columnModelForJsp.dataSetId}_${columnItemForJsp.name}" name="${columnItemForJsp.name}" class="${columnItemForJsp.column.editorFieldCls}" style="${columnItemForJsp.column.editorStyle}" />
			</c:if>
		</c:if>
		<c:if test="${editorName == 'combofield'}">
			<input id="${columnModelForJsp.name}_${columnModelForJsp.dataSetId}_${columnItemForJsp.name}" name="${columnItemForJsp.name}" class="${columnItemForJsp.column.editorFieldCls}" style="${columnItemForJsp.column.editorStyle}" />
		</c:if>
		<c:if test="${editorName == 'displayfield'}">
			<span id="${columnModelForJsp.name}_${columnModelForJsp.dataSetId}_${columnItemForJsp.name}" name="${columnItemForJsp.name}" class="${columnItemForJsp.column.editorFieldCls}" style="${columnItemForJsp.column.editorStyle}">
			</span>
		</c:if>
		<c:if test="${editorName == 'checkboxfield'}">
			<input id="${columnModelForJsp.name}_${columnModelForJsp.dataSetId}_${columnItemForJsp.name}" name="${columnItemForJsp.name}" class="${columnItemForJsp.column.editorFieldCls}" style="${columnItemForJsp.column.editorStyle}" />
		</c:if>
		<c:if test="${editorName == 'radiofield'}">
			<input id="${columnModelForJsp.name}_${columnModelForJsp.dataSetId}_${columnItemForJsp.name}" name="${columnItemForJsp.name}" class="${columnItemForJsp.column.editorFieldCls}" style="${columnItemForJsp.column.editorStyle}" />
		</c:if>
		<c:if test="${editorName == 'triggerfield'}">
			<input id="${columnModelForJsp.name}_${columnModelForJsp.dataSetId}_${columnItemForJsp.name}" name="${columnItemForJsp.name}" type="text" class="${columnItemForJsp.column.editorFieldCls}" style="${columnItemForJsp.column.editorStyle}" />
			<input type="button" id="${columnModelForJsp.name}_${columnModelForJsp.dataSetId}_${columnItemForJsp.name}_select" name="selectBtn" value="选择" />
			<input type="button" id="${columnModelForJsp.name}_${columnModelForJsp.dataSetId}_${columnItemForJsp.name}_view" name="viewBtn" value="查看" />
			<input type="button" id="${columnModelForJsp.name}_${columnModelForJsp.dataSetId}_${columnItemForJsp.name}_delete" name="deleteBtn" value="删除" />
		</c:if>
	</div>
	<script type="text/javascript">
	g_yuiCommondLi.push(function() {
		var formFieldFactory = new FormFieldFactory();
		var field = formFieldFactory.getFormField('${columnModelForJsp.name}_${columnModelForJsp.dataSetId}_${columnItemForJsp.name}', '${columnItemForJsp.name}', '${columnModelForJsp.dataSetId}');
		g_masterFormFieldLi.push(field);
		g_masterFormFieldDict['${columnItemForJsp.name}'] = field;
	});
	g_yuiCommondLi[g_yuiCommondLi.length - 1]();
	</script>
</td>
