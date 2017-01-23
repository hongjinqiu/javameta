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
	createGridWithUrl(url);
}

function createGridWithUrl(url, config) {
	var formTemplateIterator = new FormTemplateIterator();
	var listColumnModel = null;
	var result = "";
	formTemplateIterator.iterateAnyTemplateColumnModel(result, function(columnModel, result){
		listColumnModel = columnModel;
		return true;
	});
	
	var dataTableManager = new DataTableManager();
	var param = {
//		data:g_dataBo.items,
		data:[],
		columnModel:listColumnModel,
		columnModelName:listColumnModel.name,
		render:"#" + listColumnModel.name,
		url:url,
//		totalResults: g_dataBo.totalResults || 1,
		pageSize: DATA_PROVIDER_SIZE,
		pagination: true,
		onLoadSuccess: function(data) {
		},
		onBeforeLoad: function(param) {
			var queryDict = getQueryDict();
			for (var key in queryDict) {
				param[key] = queryDict[key];
			}
			console.log(param);
			return true;
		}
	};
	if (config && config.columnManager) {
		param.columnManager = config.columnManager;
	}
		// TODO loader,
		/*
param.loader,
Defines how to load data from remote server. Return false can abort this action. This function takes following parameters:
param: the parameter object to pass to remote server.
success(data): the callback function that will be called when retrieve data successfully.
error(): the callback function that will be called when failed to retrieve data.

	
load	param	 Load and show the first page rows. If the 'param' is specified, it will replace with the queryParams property. Usually do a query by passing some parameters, this method can be called to load new data from server.
$('#dg').datagrid('load',{
	code: '01',
	name: 'name01'
});
reload	param	Reload the rows. Same as the 'load' method but stay on current page.
		 */
	dtInst = dataTableManager.createDataGrid(param);
	g_gridPanelDict[listColumnModel.name] = dtInst;
}

function listMain() {
	if (true) {
		return;
	}
	var queryParameterManager = new QueryParameterManager();
	queryParameterManager.applyQueryDefaultValue();
	queryParameterManager.applyFormData();
	queryParameterManager.applyObserveEventBehavior();
	applyQueryBtnBehavior();
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
