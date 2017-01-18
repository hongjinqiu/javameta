<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据源模型控制台</title>
<link rel="stylesheet" type="text/css" href="${webRoot}/webpage/css/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${webRoot}/webpage/css/themes/icon.css">
<script type="text/javascript" src="${webRoot}/webpage/js/jquery.min.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/js/stringifyjson.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/js/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	var sysParam = {};
	var webRoot = "${webRoot}";
</script>
<script type="text/javascript" src="${webRoot}/webpage/js/common.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/js/dataTableExtend.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/js/columnManager.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/js/lformcommon.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/js/lFormField.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/js/lFormTriggerField.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/js/queryParameter.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/js/relationManager.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/js/listTemplateFactory.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/js/templateService.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/js/listTemplate.js"></script>

<c:if test="${not empty formTemplate.scripts}">
	<c:set value="${fn:split(formTemplate.scripts, ',') }" var="scriptsLi"></c:set>
	<c:forEach items="${scriptsLi}" var="scriptItem">
		<script type="text/javascript" src="${webRoot}/webpage/${scriptItem}"></script>
	</c:forEach>
</c:if>
<script type="text/javascript">
//g_formTemplateJsonData
//listTemplate

</script>
</head>

<body>

</body>
</html>
