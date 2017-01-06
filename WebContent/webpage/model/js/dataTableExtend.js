function DataTableManager() {
	this.param = null;
	this.dt = null;
}

DataTableManager.prototype.createDataGrid = function(Y, param, config) {
	var self = this;
	this.param = param;
	
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
	var columns = columnManager.getColumns(param.columnModelName, columnModel, Y);
	var gridConfig = {
			columns : columns,
			data : data,
			width: "100%"
			//		,datasource: dataSource
	};
	// TODO,调用easyui的grid来画一个表格出来,
}

DataTableManager.prototype.getSelectRecordLi = function() {
	return this.dt.datagrid("getSelections");
}

function doVirtualColumnBtnAction(columnModelName, elem, fn){
	var self = g_gridPanelDict[columnModelName];
	var dt = self.dt;
	// 需要再加一个参数进来,比如id,让grid来获取,
	/*
	var yInst = self.yInst;
	var o = dt.getRecord(yInst.one(elem));
	fn(o, columnModelName);
	*/
}
