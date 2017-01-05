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
<script type="text/javascript" src="${webRoot}/webpage/js/jquery.easyui.min.js"></script>
<script type="text/javascript">
	var sysParam = {};
</script>
<script type="text/javascript" src="${webRoot}/webpage/${formTemplate.scripts}"></script>
<!-- 
<script type="text/javascript" src="

/app/comboview?
js/moduleConfig.js
js/common.js
js/dataTableExtend.js
js/dataTableDataSourceExtend.js
js/columnManager.js
js/columnDataSourceManager.js
js/ds_formtoolbar.js
js/modelService.js
js/modelTemplateFactory.js
js/ds_formFieldFactory.js
js/formTemplateFactory.js
js/defaultAction.js
js/relationManager.js
js/listTemplateService.js
js/templateService.js
js/columnSequenceService.js
js/formManager.js

"></script>
 -->
<script type="text/javascript">
	var g_dataBo = ${dataBoJson};
	var g_formTemplateJsonData = ${formTemplateJsonData};
	<c:if test="${not empty datasourceJson}">
		var g_datasourceJson = ${datasourceJson};
	</c:if>
	<c:if test="${empty datasourceJson}">
		var g_datasourceJson = null;
	</c:if>
	
</script>
</head>

<body>
ddddddddddddddd_wwwwwwwwwww
eeeeeea
dd
</body>
</html>
