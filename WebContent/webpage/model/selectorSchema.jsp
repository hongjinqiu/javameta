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
<link rel="stylesheet" type="text/css" href="${webRoot}/webpage/model/css/common.css">
<script type="text/javascript" src="${webRoot}/webpage/js/jquery.min.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/js/stringifyjson.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/js/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	var sysParam = {};
	var webRoot = "${webRoot}";
</script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/common.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/templateService.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/dataTableExtend.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/columnManager.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/lformcommon.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/lFormField.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/lFormTriggerField.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/queryParameter.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/listTemplateFactory.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/listTemplate.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/relationManager.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/selectionManager.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/selectorTemplate.js"></script>

<c:if test="${not empty formTemplate.scripts}">
	<c:set value="${fn:split(formTemplate.scripts, ',') }" var="scriptsLi"></c:set>
	<c:forEach items="${scriptsLi}" var="scriptItem">
		<script type="text/javascript" src="${webRoot}/webpage/${scriptItem}"></script>
	</c:forEach>
</c:if>
<script type="text/javascript">
var g_yuiCommondLi = [];
var g_delayLi = [];

var g_dataBo = ${dataBoJson};
var listTemplate = ${listTemplateJson};
var g_formTemplateJsonData = listTemplate;

var DATA_PROVIDER_SIZE = "${pageSize}" || 10;
DATA_PROVIDER_SIZE = parseInt(DATA_PROVIDER_SIZE);

var g_gridPanelDict = {};

if (listTemplate) {
	var listTemplateFactory = new ListTemplateFactory();
	if (typeof(listTemplateExtraInfo) != "undefined") {
		listTemplateFactory.extendListTemplate(listTemplate, listTemplateExtraInfo);
	}
}

<c:if test="${not empty selectionBoJson}">
var g_selectionBo = ${selectionBoJson};
</c:if>
<c:if test="${empty selectionBoJson}">
var g_selectionBo = null;
</c:if>

<c:if test="${not empty layerBoJson}">
var g_layerBo = ${layerBoJson};
</c:if>
<c:if test="${empty layerBoJson}">
var g_layerBo = null;
</c:if>

<c:if test="${not empty layerBoLiJson}">
var g_layerBoLi = ${layerBoLiJson};
</c:if>
<c:if test="${empty layerBoLiJson}">
var g_layerBoLi = null;
</c:if>

//easyui采用懒加载,g_usedCheck没值,渲染表格时没值,被用判断要用表格中每一行的javameta_used字段,
<c:if test="${not empty usedCheckJson}">
var g_usedCheck = ${usedCheckJson};
</c:if>
<c:if test="${empty usedCheckJson}">
var g_usedCheck = {};
</c:if>

<c:if test="${not empty relationBoJson}">
var g_relationBo = ${relationBoJson};
</c:if>
<c:if test="${empty relationBoJson}">
var g_relationBo = {};
</c:if>

<c:if test="${not empty defaultBoJson}">
var g_defaultBo = ${defaultBoJson};
</c:if>
<c:if test="${empty defaultBoJson}">
var g_defaultBo = null;
</c:if>

<c:if test="${not empty formDataJson}">
var g_formDataJson = ${formDataJson};
</c:if>
<c:if test="${empty formDataJson}">
var g_formDataJson = null;
</c:if>

var g_selectionManager = new SelectionManager();
var g_relationManager = new RelationManager();
var g_masterFormFieldDict = {};
</script>
</head>

<body>
<c:forEach items="${formTemplate.toolbarOrDataProviderOrColumnModel}" var="item" varStatus="itemStatus">
	<c:if test="${item.xmlName == 'html-fragment'}">
		<jsp:include page="${item.rendererTemplate}"></jsp:include>
	</c:if>
	<c:if test="${item.xmlName == 'toolbar'}">
		<c:set var="toolbarForJsp" value="${item}" scope="request"></c:set>
		<c:if test="${empty item.rendererTemplate}">
			<jsp:include page="/webpage/model/render/toolbar_list.jsp"></jsp:include>
		</c:if>
		<c:if test="${not empty item.rendererTemplate}">
			<jsp:include page="${item.rendererTemplate}"></jsp:include>
		</c:if>
	</c:if>
	<c:if test="${item.xmlName == 'data-provider'}">
		<c:if test="${not empty item.queryParameters}">
			<c:if test="${empty item.queryParameters.rendererTemplate}">
				<jsp:include page="/webpage/model/render/queryParameters.jsp"></jsp:include>
			</c:if>
			<c:if test="${not empty item.queryParameters.rendererTemplate}">
				<jsp:include page="${item.queryParameters.rendererTemplate}"></jsp:include>
			</c:if>
		</c:if>
	</c:if>
	<c:if test="${item.xmlName == 'column-model'}">
		<c:set var="columnModelForJsp" value="${item}" scope="request"></c:set>
		<c:if test="${empty item.rendererTemplate}">
			<jsp:include page="/webpage/model/render/columnModel_selectorTemplate.jsp"></jsp:include>
		</c:if>
		<c:if test="${not empty item.rendererTemplate}">
			<jsp:include page="${item.rendererTemplate}"></jsp:include>
		</c:if>
	</c:if>
</c:forEach>
<script type="text/javascript">
$(document).ready(function() {
	if (typeof(selectorMain) !== "undefined") {
		selectorMain();
	}
	if (typeof(main) !== "undefined") {
		main();
	}
});
</script>
</body>
</html>
