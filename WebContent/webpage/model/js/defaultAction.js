/**
 * 可以实现复制值出来后,再继续复制值,
 * @param data
 * @param columnLi
 * @param columnName
 * @param columnValue
 */
function _recurionApplyCopyField(data, columnLi, columnName, columnValue) {
	var bo = new FormManager().getBo();
	data[columnName] = columnValue;
	var commonUtil = new CommonUtil();
	for (var i = 0; i < columnLi.length; i++) {
		if (columnLi[i].name == columnName) {
			if (columnLi[i].xmlName == "trigger-column") {
				if (columnLi[i].relationDS) {
					var relationItem = commonUtil.getRelationItem(columnLi[i].relationDS, bo, data);
					if (relationItem.copyConfig) {
						for (var j = 0; j < relationItem.copyConfig.length; j++) {
							var copySrcField = relationItem.copyConfig[j].copySrcField;
							var selectorDict = g_relationManager.getRelationBo(relationItem.relationConfig.selectorName, columnValue);
							if (selectorDict) {
								var copySrcValue = selectorDict[copySrcField];
								_recurionApplyCopyField(data, columnLi, relationItem.copyConfig[j].copyDestField, copySrcValue);
							}
						}
					}
				}
			}
		}
	}
}

function selectRowBtnDefaultAction(dataSetId, toolbarOrColumnModel, button, inputValueLi) {
	var selectValueLi = [];
	if (button.relationDS && button.relationDS.relationItem) {
		var relationItem = button.relationDS.relationItem[0];
		var selectorName = relationItem.relationConfig.selectorName;
		for (var i = 0; i < inputValueLi.length; i++) {
			var selectorDict = g_relationManager.getRelationBo(selectorName, inputValueLi[i]);
			selectValueLi.push(selectorDict);
		}
	}
	
	var formManager = new FormManager();
	var formTemplateIterator = new FormTemplateIterator();
	var result = "";

	// use default action
	var dataLi = [];
	var columnResult = "";
	var columnLi = [];
	formTemplateIterator.iterateAllTemplateColumn(dataSetId, columnResult, function IterateFunc(column, result) {
		columnLi.push(column);
	});
	for (var i = 0; i < selectValueLi.length; i++) {
		var data = formManager.getDataSetNewData(dataSetId);
		if (button.relationDS && button.relationDS.relationItem) {
			var relationItem = button.relationDS.relationItem[0];
			if (relationItem.copyConfig) {
				for (var j = 0; j < relationItem.copyConfig.length; j++) {
					var columnName = relationItem.copyConfig[j].copyDestField;
					var copySrcField = relationItem.copyConfig[j].copySrcField;
					var columnValue = selectValueLi[i][copySrcField];
					_recurionApplyCopyField(data, columnLi, columnName, columnValue);
				}
			}
		}
		dataLi.push(data);
	}
	// 允许重复的判断,
	//var gridDataLi = g_gridPanelDict[dataSetId].dt.datagrid("getData");// getData返回{"total": 0, rows: []},getRows返回当前页的数据,由于没有分页，因此，用getRows
	var gridDataLi = g_gridPanelDict[dataSetId].dt.datagrid("getRows");
	var notAllowDuplicateColumn = [];
	var datasourceIterator = new DatasourceIterator();
	datasourceIterator.iterateAllField(g_datasourceJson, result, function(fieldGroup, result){
		if (fieldGroup.getDataSetId() == dataSetId && fieldGroup.allowDuplicate != true) {
			notAllowDuplicateColumn.push(fieldGroup.id);
		}
	});
	for (var i = 0; i < dataLi.length; i++) {
		var isIn = false;
		for (var j = 0; j < gridDataLi.length; j++) {
			var flag = notAllowDuplicateColumn.length > 0;
			for (var k = 0; k < notAllowDuplicateColumn.length; k++) {
				flag = flag && (dataLi[i][notAllowDuplicateColumn[k]] == gridDataLi[j][notAllowDuplicateColumn[k]]);
			}
			if (flag) {
				isIn = true;
				break
			}
		}
		if (!isIn) {
			gridDataLi.push(dataLi[i]);
		}
	}
	g_gridPanelDict[dataSetId].dt.datagrid("loadData", gridDataLi);
}
