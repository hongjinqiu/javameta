function ColumnDatasourceManager() {
}
/*
ColumnDatasourceManager.prototype.createVirtualColumn = function(columnModelName, columnModel, columnIndex) {
	var self = this;
	var yInst = self.yInst;
	var i = columnIndex;
	if (columnModel.columnList[i].xmlName == "virtual-column" && columnModel.columnList[i].hideable != true) {
		var virtualColumn = columnModel.columnList[i];
		return {
			field: columnModel.columnList[i].name,
			title: columnModel.columnList[i].text,
			allowHTML:  true, // to avoid HTML escaping
			formatter:      function(virtualColumn){
				return function(o){
					var htmlLi = [];
					var buttonBoLi = null;
					if (o.value) {
						buttonBoLi = o.value[virtualColumn.buttons.xmlName];
					}
					for (var j = 0; j < virtualColumn.buttons.button.length; j++) {
						var btnTemplate = null;
						if (virtualColumn.buttons.button[j].mode == "fn") {
							btnTemplate = "<input type='button' value='{value}' onclick='doVirtualColumnBtnAction(\"{columnModelName}\", this, {handler})' class='{class}' />";
						} else if (virtualColumn.buttons.button[j].mode == "url") {
							btnTemplate = "<input type='button' value='{value}' onclick='location.href=\"{href}\"' class='{class}' />";
						} else {
							btnTemplate = "<input type='button' value='{value}' onclick='window.open(\"{href}\")' class='{class}' />";
						}
						if (!buttonBoLi || buttonBoLi[j]["isShow"]) {
							var id = columnModel.idColumn.name;
							var isUsed = g_usedCheck && g_usedCheck[columnModel.dataSetId] && g_usedCheck[columnModel.dataSetId][o.data[id]];
							if (!(isUsed && virtualColumn.buttons.button[j].name == "btn_delete")) {
								// handler进行值的预替换,
								var Y = yInst;
								var handler = virtualColumn.buttons.button[j].handler;
								handler = Y.Lang.sub(handler, o.data);
								htmlLi.push(Y.Lang.sub(btnTemplate, {
									value: virtualColumn.buttons.button[j].text,
									handler: handler,
									"class": virtualColumn.buttons.button[j].iconCls,
									href: handler,
									columnModelName: columnModelName
								}));
							}
						}
					}
					return htmlLi.join("");
				}
			}(virtualColumn)
		};
	}
	return null;
}*/

ColumnDatasourceManager.prototype.getColumns = function(columnModelName, columnModel) {
	var self = this;
	self.yInst = Y;
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
