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

function getQueryString(Y) {
	var result = {};
	for (var key in g_masterFormFieldDict) {
		result[key] = g_masterFormFieldDict[key].get("value");
	}
	return stringifyJSON(result);
}

function createGridWithUrl(url, config) {
		var dataTableManager = new DataTableManager();
		var renderName = "#columnModel_1";
		var columnModelName = renderName.replace("#", "");
		var param = {
				data:g_dataBo.items,
				columnModel:listTemplate.ColumnModel,
				columnModelName:columnModelName,
				render:renderName,
				url:url,
				totalResults: g_dataBo.totalResults || 1,
				pageSize: DATA_PROVIDER_SIZE
		};
		if (config && config.columnManager) {
			param.columnManager = config.columnManager;
		}
		dtInst = dataTableManager.createDataGrid(param);
		g_gridPanelDict[columnModelName] = dtInst;
		var queryParameterManager = new QueryParameterManager();
		queryParameterManager.applyQueryDefaultValue();
		queryParameterManager.applyFormData();
		queryParameterManager.applyObserveEventBehavior();
		applyQueryBtnBehavior();
}

function listMain() {
	var url = "/console/listschema?@name=" + listTemplate.id + "&format=json";
	createGridWithUrl(url);
}

function queryFormValidator() {
	var messageLi = [];
	
	var formTemplateIterator = new FormTemplateIterator();
	var result = "";
	formTemplateIterator.iterateAllTemplateQueryParameter(result, function(queryParameter, result){
		for (var key in g_masterFormFieldDict) {
			if (queryParameter.name == key) {
				if (!g_masterFormFieldDict[key].validateField()) {
					messageLi.push(queryParameter.Text + g_masterFormFieldDict[key].get("error"));
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
					var pagModel = dtInst.dt.datagrid("getPager");
					var page = pagModel.pageNumber;
					if (page == 1) {
						dtInst.dt.datagrid('reload');
					} else {
						dtInst.dt.datagrid('gotoPage', 1);
					}
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
