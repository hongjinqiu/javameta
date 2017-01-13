<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<c:if test="${buttonForJsp.mode == 'fn'}">
	<a id="${buttonForJsp.name}" href="javascript:${buttonForJsp.handler}('${buttonForJsp.name}')" class="easyui-linkbutton" data-options="iconCls:'${buttonForJsp.iconCls}',plain:true">
		${buttonForJsp.text}
	</a>
</c:if>
<c:if test="${buttonForJsp.mode == 'url'}">
	<a id="${buttonForJsp.name}" href="${buttonForJsp.handler}" class="easyui-linkbutton" data-options="iconCls:'${buttonForJsp.iconCls}',plain:true">
		${buttonForJsp.text}
	</a>
</c:if>
<c:if test="${buttonForJsp.mode == 'url^'}">
	<a id="${buttonForJsp.name}" href="${buttonForJsp.handler}" target="_blank" class="easyui-linkbutton" data-options="iconCls:'${buttonForJsp.iconCls}',plain:true">
		${buttonForJsp.text}
	</a>
</c:if>