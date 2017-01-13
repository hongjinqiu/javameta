function ColumnDatasourceManager() {
}

ColumnDatasourceManager.prototype.getColumns = function(columnModelName, columnModel) {
	var self = this;
	var columnManager = new ColumnManager();
	// 换掉createVirtualColumn,button需要做被用的判断,被用时,不显示删除按钮,
	//columnManager.createVirtualColumn = self.createVirtualColumn;
	var columns = columnManager.getColumns(columnModelName, columnModel);
	if (g_datasourceJson) {
		var datasourceIterator = new DatasourceIterator();
		var result = "";
		for (var i = 0; i < columns.length; i++) {
//			columns[i].allowHTML = true;
			datasourceIterator.iterateAllField(g_datasourceJson, result, function(fieldGroup, result){
				if (fieldGroup.getDataSetId() == columnModel.dataSetId && fieldGroup.id == columns[i].field) {
					if (fieldGroup.allowEmpty == "false") {
						columns[i].title = '<font style="color:red">*</font>' + columns[i].title;
					}
				}
			});
		}
	}
	return columns
}
