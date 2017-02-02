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
<script type="text/javascript" src="${webRoot}/webpage/model/js/common.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/dataTableExtend.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/dataTableDatasourceExtend.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/columnManager.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/columnDatasourceManager.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/ds_formtoolbar.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/datasourceService.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/datasourceFactory.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/ds_formFieldFactory.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/formTemplateFactory.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/defaultAction.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/relationManager.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/templateService.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/columnSequenceService.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/formField.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/formTriggerField.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/formManager.js"></script>

<c:if test="${not empty formTemplate.scripts}">
	<c:set value="${fn:split(formTemplate.scripts, ',') }" var="scriptsLi"></c:set>
	<c:forEach items="${scriptsLi}" var="scriptItem">
		<script type="text/javascript" src="${webRoot}/webpage/${scriptItem}"></script>
	</c:forEach>
</c:if>

<script type="text/javascript">
	var g_dataBo = ${dataBoJson};
	var g_formTemplateJsonData = ${formTemplateJsonData};
	<c:if test="${not empty datasourceJson}">
		var g_datasourceJson = ${datasourceJson};
	</c:if>
	<c:if test="${empty datasourceJson}">
		var g_datasourceJson = null;
	</c:if>

	if (g_datasourceJson) {
		var datasourceFactory = new DatasourceFactory();
		datasourceFactory.enhanceDatasource(g_datasourceJson);
		if (typeof(modelExtraInfo) != "undefined") {
			datasourceFactory.extendDatasource(g_datasourceJson, modelExtraInfo);
		}
	}
	if (g_formTemplateJsonData) {
		var formTemplateFactory = new FormTemplateFactory();
		if (typeof(modelExtraInfo) != "undefined") {
			formTemplateFactory.extendFormTemplate(modelExtraInfo);
		}
	}
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
		var g_relationBo = null;
	</c:if>
	
	var g_relationManager = new RelationManager();
	
	var g_masterFormFieldLi = [];
	var g_masterFormFieldDict = {};
	var g_gridPanelDict = {};
	var g_formStatus = "${formStatus}";// 表单状态,view或""
	var g_copyFlag = "${copyFlag}";// true|false,是否从列表页点击复制按钮进入
	var g_id = "${id}";
	
	var g_yuiCommondLi = [];

	var g_Y = null;
	
	var g_popupFormField = {};// 用于表格弹出时,存放弹出框控件数据引用,
	
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
			<jsp:include page="/webpage/model/render/toolbar.jsp"></jsp:include>
		</c:if>
		<c:if test="${not empty item.rendererTemplate}">
			<jsp:include page="${item.rendererTemplate}"></jsp:include>
		</c:if>
	</c:if>
	<c:if test="${item.xmlName == 'column-model'}">
		<c:set var="columnModelForJsp" value="${item}" scope="request"></c:set>
		<c:if test="${item.dataSetId != 'A'}">
			<c:if test="${empty item.rendererTemplate}">
				<jsp:include page="/webpage/model/render/columnModel_list.jsp"></jsp:include>
			</c:if>
			<c:if test="${not empty item.rendererTemplate}">
				<jsp:include page="${item.rendererTemplate}"></jsp:include>
			</c:if>
		</c:if>
		<c:if test="${item.dataSetId == 'A'}">
			<c:set var="itemStatusJsp" value="${itemStatus}" scope="request"></c:set>
			<c:if test="${empty item.rendererTemplate}">
				<jsp:include page="/webpage/model/render/columnModel_form.jsp"></jsp:include>
			</c:if>
			<c:if test="${not empty item.rendererTemplate}">
				<jsp:include page="${item.rendererTemplate}"></jsp:include>
			</c:if>
		</c:if>
	</c:if>
</c:forEach>


<script type="text/javascript">
	$(document).ready(function(){
		/* 
		for (var i = 0; i < g_yuiCommondLi.length; i++) {
			g_yuiCommondLi[i]();
		}
		*/
		if (typeof(main) !== "undefined") {
			main();
		}
	});
</script>
<!-- 
<div id="testDiv">
	ddddddddddddddd_wwwwwwwwwww
	eeeeeea
	dd
	<input id="i_a" type="text" name="name1" value="name1" />
	<br />
	<input type="text" id="nn" />
	<br />
	<input id="dd" type="text" />
	<br />
	<input id="dt" type="text" name="birthday">
	<br />
	<input id="myTestText" type="text" name="myTestText" />
</div>
 -->
</body>
</html>
