<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%--  
parameterForJsp
item.put("label", queryParameter.getText());
item.put("name", queryParameter.getName());
item.put("queryParameter", queryParameter);
item.put("nameColSpan", columnColSpan);
--%>
<td class="searchTitle">
	${parameterForJsp.label}：
</td>
<td colspan="${parameterForJsp.nameColSpan}">
	<div id="q_${parameterForJsp.name}_render">
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
		var queryParameterManager = new QueryParameterManager();
		var field = queryParameterManager.getQueryField('q_${parameterForJsp.name}_${parameterForJsp.queryParameter.editor}', '${parameterForJsp.name}');
		//field.render("#q_{{$tdItem.name}}_render");
		g_masterFormFieldDict['${parameterForJsp.name}'] = field;
	});
	g_yuiCommondLi[g_yuiCommondLi.length - 1]();
	</script>
</td>
