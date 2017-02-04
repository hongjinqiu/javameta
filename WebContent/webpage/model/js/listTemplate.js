var dtInst;

//--------------测试函数区----------------
function doEdit(o) {
	console.log(o);
	console.log(o.toJSON());
}

function test() {
	console.log(getSelectRecordLi());
}
//------------------------------

function getSelectRecordLi() {
	return dtInst.getSelectRecordLi();
}

function getQueryString() {
	return stringifyJSON(getQueryDict());
}

function getQueryDict() {
	var result = {};
	for (var key in g_masterFormFieldDict) {
		result[key] = g_masterFormFieldDict[key].get("value");
	}
	return result;
}

function createListTemplateGrid() {
	var url = webRoot + "/schema/listschema.do?@name=" + listTemplate.id + "&format=json";
	createGridWithUrl({
		url: url
	}, {
		onLoadSuccess: function(data) {
			if (data.relationBo) {
				g_relationManager.mergeRelationBo(data.relationBo);
			}
			if (data.usedCheckBo) {
				g_usedCheck = data.usedCheckBo;
			}
		}
	});
}

function createGridWithUrl(config, datagridConfig) {
	var formTemplateIterator = new FormTemplateIterator();
	var listColumnModel = null;
	var result = "";
	formTemplateIterator.iterateAnyTemplateColumnModel(result, function(columnModel, result){
		listColumnModel = columnModel;
		return true;
	});
	
	var dataTableManager = new DataTableManager();
	var url = "";
	if (config && config.url) {
		url = config.url;
	}
	var param = {
//		data:g_dataBo.items,
		data:[],
		columnModel:listColumnModel,
		columnModelName:listColumnModel.name,
		render:"#" + listColumnModel.name,
		url:url,
//		totalResults: g_dataBo.totalResults || 1,
		pageSize: DATA_PROVIDER_SIZE,
		pagination: true
	};
	var onBeforeLoad = function(param) {
		var queryDict = getQueryDict();
		for (var key in queryDict) {
			param[key] = queryDict[key];
		}
		return true;
	};
	if (datagridConfig) {
		datagridConfig.onBeforeLoad = onBeforeLoad;
	} else {
		datagridConfig = {
			onBeforeLoad: onBeforeLoad
		}
	}
	if (config && config.columnManager) {
		param.columnManager = config.columnManager;
	}
	
	dtInst = dataTableManager.createDataGrid(param, datagridConfig);
	g_gridPanelDict[listColumnModel.dataSetId || listColumnModel.name] = dtInst;
}

function listMain() {
	
}

function queryFormValidator() {
	var messageLi = [];
	
	var formTemplateIterator = new FormTemplateIterator();
	var result = "";
	formTemplateIterator.iterateAllTemplateQueryParameter(result, function(queryParameter, result){
		for (var key in g_masterFormFieldDict) {
			if (queryParameter.name == key) {
				if (!g_masterFormFieldDict[key].get("isValid")) {
					messageLi.push(queryParameter.text + g_masterFormFieldDict[key].get("error"));
				}
			}
		}
	});
	
	if (messageLi.length > 0) {
		return {
			"result": false,
			"message": messageLi.join("<br />")
		};
	}
	return {
		"result": true
	};
}

function applyQueryBtnBehavior() {
		if ($("#queryBtn").length > 0) {
			$("#queryBtn").on("click", function(e){
				var validateResult = queryFormValidator();
				
				if (!validateResult.result) {
					showError(validateResult.message);
				} else {
					dtInst.dt.datagrid('load');
				}
			});

			if ($("#btnMore").length > 0) {
				$("#btnMore").on("click", function(e){
					var trCount = $("#queryMain .queryLine").size();
					if (trCount > 1) {
						$("#queryContent").animate({
							height: 26 * trCount
						});
					}
					$("#btnMore").hide();
					$("#btnUp").show();
				});
				$("#btnUp").on("click", function(e){
					$("#queryContent").animate({
						height: 22
					});
					
					$("#btnMore").show();
					$("#btnUp").hide();
				});
			}
			$("#queryReset").on("click", function(e){
				var queryParameterManager = new QueryParameterManager();
				queryParameterManager.applyQueryDefaultValue();
			});
		}
}
