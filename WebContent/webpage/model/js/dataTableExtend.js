function DataTableManager() {
	this.param = null;
	this.dt = null;
}

DataTableManager.prototype.createDataGridForColumnModel = function(columnModelName) {
	var columnModel = null;
	for (var i = 0; i < g_formTemplateJsonData.toolbarOrDataProviderOrColumnModel.length; i++) {
		if (g_formTemplateJsonData.toolbarOrDataProviderOrColumnModel[i].xmlName == "column-model") {
			if (g_formTemplateJsonData.toolbarOrDataProviderOrColumnModel[i].name == columnModelName) {
				columnModel = g_formTemplateJsonData.toolbarOrDataProviderOrColumnModel[i];
				break;
			}
		}
	}
	if (columnModel) {
		var dataTableManager = new DataTableManager();
		var datasourceModelId = g_formTemplateJsonData.datasourceModelId;
		var columnModelName = columnModel.name;
		var isRender = true;
		var isRenderList = false;
		var items = g_dataBo[columnModelName] || [];
		if (!datasourceModelId) {
			isRenderList = !columnModel.displayMode || columnModel.displayMode == "list";
			isRender = isRenderList;
		} else {
			if (columnModel.dataSetId == "A") {
				isRenderList = false;
				items = g_dataBo[columnModelName] || {};
			} else {
				isRenderList = !columnModel.displayMode || columnModel.displayMode == "list";
			}
		}
		if (isRender) {
			if (isRenderList) {
				var param = {
					data:items,
					columnModel: columnModel,
					columnModelName: columnModelName,
					render:"#" + columnModelName,
					url:"",
					totalResults: g_dataBo.totalResults || 50,
					pageSize: 10000,
					paginatorContainer : null,
					paginatorTemplate : null,
					columnManager: new ColumnDatasourceManager()
				};
				// 表格的编辑控件,需要datasource,
				if (g_datasourceJson) {
					param["datasourceJson"] = g_datasourceJson;
				}
				dataTableManager.createDataGrid(param);
				g_gridPanelDict[columnModelName] = dataTableManager;
			} else {
				// renderForm,不实现,放到主数据集中处理
				console.log("detailDataSet which dataSetId is:" + columnModelName + " can't render to form,");
			}
		}
	}
}

DataTableManager.prototype.createColumnModelToolbar = function(columnModel) {
	var result = [];
	if (columnModel.toolbar && columnModel.toolbar.buttonGroupOrButtonOrSplitButton) {
		for (var i = 0; i < columnModel.toolbar.buttonGroupOrButtonOrSplitButton.length; i++) {
			var button = columnModel.toolbar.buttonGroupOrButtonOrSplitButton[i];
			var config = {};
			if (button.name) {
				config.id = button.name;
			}
			config.text = button.text;
			config.iconCls = button.iconCls;
			if (button.mode == "fn") {
				config.handler = function(button) {
					return function(){
						var param = button.name || "";
						eval(button.handler)(param);
					}
				}(button);
			} else if (button.mode == "url") {
				config.handler = function(button) {
					return function(){
						location.href = button.handler;
					}
				}(button);
			} else if (button.mode == "url^") {
				config.handler = function(button) {
					return function(){
						window.open(button.handler);
					}
				}(button);
			}
			result.push(config);
		}
	}
	return result;
}

DataTableManager.prototype.createDataGrid = function(param) {
	var self = this;
	self.param = param;
	
	var data = param.data;
	var columnModel = param.columnModel;
	var render = param.render;
	var url = param.url;
	var totalResults = param.totalResults;
	var pageSize = param.pageSize;
	var paginatorContainer = param.paginatorContainer;
	var paginatorTemplate = param.paginatorTemplate;
	
	var columnManager = new ColumnManager();
	if (param.columnManager) {
		columnManager = param.columnManager;
	}
	var columns = columnManager.getColumns(param.columnModelName, columnModel);
	var gridConfig = {
		url: url,
		title: columnModel.text,
		idField: columnModel.idColumn.name,
		columns : columns,
		data : data,
		checkOnSelect: true,
		selectOnCheck: true,
		fitColumns: true,
		total: totalResults,
		pageSize: pageSize,
		pagination: param.pagination || false,
		singleSelect: columnModel.selectionMode == "radio",
		//width: "100%"
		//		,datasource: datasource
	};
	if (param.onBeforeLoad) {
		gridConfig.onBeforeLoad = param.onBeforeLoad;
	}
	if (param.onLoadSuccess) {
		gridConfig.onLoadSuccess = param.onLoadSuccess;
	}
	var toolbar = self.createColumnModelToolbar(columnModel);
	if (toolbar.length > 0) {
		gridConfig.toolbar = toolbar;
	}
	$("#" + param.columnModelName).datagrid(gridConfig);
	self.dt = $("#" + param.columnModelName);
	return self;
}

DataTableManager.prototype.getSelectRecordLi = function() {
	return this.dt.datagrid("getSelections");
}

function doVirtualColumnBtnAction(columnModelName, id, fn){
	var self = g_gridPanelDict[columnModelName];
	var dt = self.dt;
	var index = dt.datagrid("getRowIndex", id);
	var dataLi = dt.datagrid("getRows");
	fn(dataLi[index], columnModelName);
}
