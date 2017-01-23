/*	可能有用的的一些代码
// 表格弹窗新增点击确定
var doPopupConfirm = function() {
	var li = pluginDataTableManager.dt.pqe.getRecords();
	// 输入中有,输出中没有,删除
	for (var i = 0; i < inputDataLi.length; i++) {
		var isIn = false;
		for (var j = 0; j < li.length; j++) {
			if (inputDataLi[i].id == li[j].id) {
				isIn = true;
				break;
			}
		}
		if (!isIn) {
			self.dt.removeRow(inputDataLi[i].id);
		}
	}
	
	for (var i = 0; i < li.length; i++) {
		var record = self.dt.getRecord(li[i].id);
		if (record) {
			for (var key in li[i]) {
				record.set(key, li[i][key]);
			}
		}
	}
	self.dt.addRows(li);
};

// 渲染工具栏按钮
if (self.param.columnModel.editorToolbar && self.param.columnModel.editorToolbar.buttonGroupOrButtonOrSplitButton) {
	for (var i = 0; i < self.param.columnModel.editorToolbar.buttonGroupOrButtonOrSplitButton.length; i++) {
		var btnTemplate = null;
		var replObj = {};
		var button = self.param.columnModel.editorToolbar.buttonGroupOrButtonOrSplitButton[i];
		if (button.mode == "fn") {
			btnTemplate = "<input type='button' value='{value}' class='{class}' onclick='{fnName}(\"{columnModelName}\")'/>";
			replObj = {
				value: button.text,
				"class": button.iconCls,
				fnName: button.handler,
				columnModelName: self.param.columnModelName
			};
		} else if (button.mode == "url") {
			btnTemplate = "<input type='button' value='{value}' onclick='location.href=\"{href}\"' class='{class}' />";
			replObj = {
				value: button.text,
				href: button.handler,
				"class": button.iconCls
			};
		} else {
			btnTemplate = "<input type='button' value='{value}' onclick='window.open(\"{href}\")' class='{class}' />";
			replObj = {
				value: button.text,
				href: button.handler,
				"class": button.iconCls
			};
		}
		if (btnTemplate) {
			btnTemplate = Y.Lang.sub(btnTemplate, replObj);
			bodyHtmlLi.push(btnTemplate);
		}
	}
}

// 数据较验

var formManager = new FormManager();
var detailDataLi = pluginDataTableManager.dt.pqe.getRecords();
var dataSetId = self.param.columnModelName;
var validateResult = formManager.dsDetailValidator(g_datasourceJson, dataSetId, detailDataLi);

if (!validateResult.result) {
	showError(validateResult.message);
} else {
	this.hide();
	// code that executes the user confirmed action goes here
	if (this.callback) {
		this.callback();
	}
	// callback reference removed, so it won't persist
	this.callback = false;
}
dialog.callback = doPopupConfirm;
 */

DataTableManager.prototype.createAddRowGrid = function(inputDataLi) {
	var self = this;
}

function doPluginVirtualColumnBtnAction(columnModelName, elem, fn){
	var self = g_gridPanelDict[columnModelName + "_addrow"];
	var dt = self.dt;
	/*
	var yInst = self.yInst;
	var o = dt.getRecord(yInst.one(elem));
	fn(o, columnModelName);
	*/
}

/**
 * 插件表头新增,新增一行
 */
function g_pluginAddRow(dataSetId) {
	var formManager = new FormManager();
	var data = formManager.getDataSetNewData(dataSetId);
//	g_gridPanelDict[dataSetId + "_addrow"].dt.addRow(data);
	// TODO,修改成弹出Form的方式来修改,
}

/*
	"buttonConfig": {
		"selectRowBtn": {
			selectFunc: function(datas){},// 单多选回调
			queryFunc: function(){}// 单多选回调
		}
	}
 */
/**
 * 点击选择,选择回多行,需要从全局配置中读queryFunc,selectFunc是这里面应用上的
 * @param dataSetId
 */
function g_selectRow(dataSetId, btnName) {
//	var formManager = new FormManager();
	var formTemplateIterator = new FormTemplateIterator();
	var result = "";
	formTemplateIterator.iterateAnyTemplateButton(result, function(toolbarOrColumnModel, button, result) {
		if (toolbarOrColumnModel.dataSetId == dataSetId && button.name == btnName) {
			window.s_selectFunc = function(selectValueLi) {
				if (button.jsConfig && button.jsConfig.selectFunc) {
					button.jsConfig.selectFunc(selectValueLi);
				} else {
					selectRowBtnDefaultAction(dataSetId, toolbarOrColumnModel, button, selectValueLi);
				}
        	};
        	window.s_queryFunc = function() {
        		var queryFunc = null;
        		if (button.jsConfig && button.jsConfig.queryFunc) {
        			queryFunc = button.jsConfig.queryFunc;
        		}
        		if (queryFunc) {
        			return queryFunc();
        		}
        		return {};
        	};
        	if (button.relationDS && button.relationDS.relationItem) {
    			var relationItem = button.relationDS.relationItem[0];
    			var url = "/schema/selectorschema?@name={NAME_VALUE}&@multi={MULTI_VALUE}&@displayField={DISPLAY_FIELD_VALUE}&date=" + new Date();
    			var selectorName = relationItem.relationConfig.selectorName;
    			url = url.replace("{NAME_VALUE}", selectorName);
    			var multi = relationItem.relationConfig.selectionMode == "multi";
    			url = url.replace("{MULTI_VALUE}", multi);
    			var displayField = relationItem.relationConfig.displayField;
    			url = url.replace("{DISPLAY_FIELD_VALUE}", displayField);
    			var selectorTitle = g_relationBo[selectorName].description;
    			var dialog = showModalDialog({
    				"title": selectorTitle,
    				"url": url
    			});
    			window.s_closeDialog = function() {
    				if (window.s_dialog) {
    					window.s_dialog.hide();
    				}
    				window.s_dialog = null;
    				window.s_selectFunc = null;
    				window.s_queryFunc = null;
    			}
        	}
        	
			return true;
		}
		return false;
	});
}

/**
 * 点击新增,新增一行
 */
function g_addRow(dataSetId) {
	var inputDataLi = [];
	var formManager = new FormManager();
	var data = formManager.getDataSetNewData(dataSetId);
	inputDataLi.push(data);
	g_gridPanelDict[dataSetId].createAddRowGrid(inputDataLi);
}

/**
 * 点击删除,删除多行
 */
function g_removeRow(dataSetId) {
	var selectRecordLi = g_gridPanelDict[dataSetId].getSelectRecordLi();
	if (selectRecordLi.length == 0) {
		showAlert("请先选择");
	} else {
		var hasUsed = false;
		for (var i = 0; i < selectRecordLi.length; i++) {
			var isUsed;
			if (selectRecordLi[i]["javameta_used"] !== undefined) {// 列表页,easyui用的是懒加载,g_usedCheck没值,值放到row.javameta_used中,
				isUsed = selectRecordLi[i]["javameta_used"];
			} else {
				isUsed = g_usedCheck && g_usedCheck[dataSetId] && g_usedCheck[dataSetId][selectRecordLi[i].id];
			}
			if (isUsed) {
				hasUsed = true;
			} else {
				// g_gridPanelDict[dataSetId].dt.removeRow(selectRecordLi[i]);
				// $('#tt').datagrid('deleteRow',index);
				var rowLi = g_gridPanelDict[dataSetId].dt.datagrid("getData");
				for (var j = 0; j < rowLi.length; j++) {
					if (rowLi[j].id == selectRecordLi[i].id) {
						g_gridPanelDict[dataSetId].dt.datagrid("deleteRow", j);
					}
				}
			}
		}
		if (hasUsed) {
			showAlert("部份数据已被用，不可删除！");
		}
	}
}

/**
 * 点击删除,删除一行
 */
function g_removeSingleRow(o, dataSetId) {
	var index = g_gridPanelDict[dataSetId].dt.datagrid("getRowIndex", o);
	g_gridPanelDict[dataSetId].dt.datagrid("deleteRow", index);
}

/**
 * 点击行项编辑,编辑一行
 */
function g_editSingleRow(o, dataSetId) {
	var inputDataLi = [];
	inputDataLi.push(o);
	g_gridPanelDict[dataSetId].createAddRowGrid(inputDataLi);
}

/**
 * 点击行项复制,复制一行
 */
function g_copyRow(o, dataSetId) {
	var inputDataLi = [];
	var formManager = new FormManager();
	var data = formManager.getDataSetCopyData(dataSetId, o.toJSON());
	inputDataLi.push(data);
	g_gridPanelDict[dataSetId].createAddRowGrid(inputDataLi);
}

/**
 * 点击表格头编辑,编辑多行
 */
function g_editRow(dataSetId) {
	var selectRecordLi = g_gridPanelDict[dataSetId].getSelectRecordLi();
	if (selectRecordLi.length == 0) {
		showAlert("请先选择");
	} else if (selectRecordLi.length > 1) {
		showAlert("只能选择1行");
	} else {
		var inputDataLi = [];
		for (var i = 0; i < selectRecordLi.length; i++) {
			inputDataLi.push(selectRecordLi[i].toJSON());
		}
		g_gridPanelDict[dataSetId].createAddRowGrid(inputDataLi);
	}
}



