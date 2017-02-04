function getFirstDataSetIdOrColumnModelName() {
	var formTemplateIterator = new FormTemplateIterator();
	var firstDataSetIdOrColumnModelName = "";
	var result = "";
	formTemplateIterator.iterateAnyTemplateColumnModel(result, function(columnModel, result) {
		firstDataSetIdOrColumnModelName = columnModel.dataSetId || columnModel.name;
		return true;
	});
	return firstDataSetIdOrColumnModelName;
}

function firstLowerCase(text) {
	return text.substring(0,1).toLowerCase() + text.substring(1);
}

function g_deleteRecord(o) {
	showConfirm("确认删除？", function(){
		var url = "/" + firstLowerCase(listTemplate.datasourceModelId) + "/deleteData.do?format=json";
		ajaxRequest({
			url: url
			,params: {
				"id": o.id,
				"datasourceModelId": listTemplate.datasourceModelId
			},
			callback: function(o) {
//			showSuccess("删除数据成功");
				g_gridPanelDict[getFirstDataSetIdOrColumnModelName()].dt.datagrid("reload");
			}
		});
	});
}

function g_deleteRecords() {
	var selectRecords = g_gridPanelDict[getFirstDataSetIdOrColumnModelName()].getSelectRecordLi();
	if (selectRecords.length > 0) {
		showConfirm("确认删除？", function(){
			var errorMsgLi = [];
			
			var url = "/" + firstLowerCase(listTemplate.datasourceModelId) + "/deleteData.do?format=json";
			for (var i = 0; i < selectRecords.length; i++) {
				ajaxRequest({
					url: url
					,params: {
						"id": selectRecords[i].id,
						"datasourceModelId": listTemplate.datasourceModelId
					},
					callback: function(o) {
						//g_gridPanelDict[getFirstDataSetIdOrColumnModelName()].dt.datagrid("reload");
					},
					failCallback: function(o) {
						var message = "记录" + selectRecords[i].code;
						message += "：" + o.message+"；";
						errorMsgLi.push(message);
					}
				});
			}
			if (errorMsgLi.length > 0) {
				showError(errorMsgLi.join("<br />"));
			}
			g_gridPanelDict[getFirstDataSetIdOrColumnModelName()].dt.datagrid("reload");
		});
	} else {
		showAlert("请选择记录！");
	}
}

