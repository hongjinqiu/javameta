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

DataTableManager.prototype.doPopupConfirm = function() {
	var self = this;
	var data = {};
	for (var key in g_popupFormField) {
		data[key] = g_popupFormField[key].get("value");
	}
	var dataSetId = self.param.columnModel.dataSetId;
	
	// 数据校验
	var formManager = new FormManager();
	var detailDataLi = [data];
	var validateResult = formManager.dsDetailValidator(g_datasourceJson, dataSetId, detailDataLi);

	if (!validateResult.result) {
		var message = validateResult.message;
		message = message.replace(/序号为\d*的分录，/g, "");
		showError(message);
		return false;
	} else {
		// getRowIndex	row	Return the specified row index, the row parameter can be a row record or an id field value.
		var rowIndex = g_gridPanelDict[dataSetId].dt.datagrid("getRowIndex", data.id);// 文档上说可以用record或id field value,实际上用record会返回-1,只能用id field value 
		if (rowIndex > -1) {// update
			g_gridPanelDict[dataSetId].dt.datagrid("updateRow", {
				index: rowIndex,
				row: data
			});
		} else {// append
			g_gridPanelDict[dataSetId].dt.datagrid("appendRow", data);
		}
		return true;
	}
}

DataTableManager.prototype.createAddRowGrid = function(inputDataLi) {
	var self = this;
	var dataSetId = self.param.columnModel.dataSetId;
	var editorHtml = $("#" + dataSetId + "_editor").html();
	editorHtml = editorHtml.replace(/<script[\s\S]*?>[\s\S]*?<\/script>/mig, "");// 清除html中所有的<script type="text/javascript">...</script>内容,避免重复执行js,
	var index = new DatasourceFactory().getSequenceNo();
	editorHtml = editorHtml.replace(/{index}/g, index);
	g_popupGridEditDialog = $(editorHtml).dialog({
		title: self.param.columnModel.text,
	    width: 800,
	    height: 400,
	    cache: false,
	    modal: true,
	    resizable:true,
	    buttons:[{
			text:'确定',
			handler:function(){
				var result = self.doPopupConfirm();
				if (result) {
					g_popupGridEditDialog.dialog("destroy");
				}
			}
		},{
			text:'取消',
			handler:function(){
				g_popupGridEditDialog.dialog("destroy");
			}
		}],
		onOpen: function() {
			for (var i = 0; i < g_gridCommondDict[dataSetId].length; i++) {
				var field = g_gridCommondDict[dataSetId][i](index);// 生成field,function定义在*_list_editor.jsp中
				g_popupFormField[field.get("name")] = field;
			}
			// 赋初始化值
			var data = inputDataLi[0];
			for (var key in g_popupFormField) {
				if (data[key]) {
					g_popupFormField[key].set("value", data[key]);
				}
			}
		},
		onClose: function() {
			g_popupFormField = {};// 清空弹出控件引用,
			g_popupGridEditDialog = null;
		},
		onDestroy: function() {
			g_popupFormField = {};// 清空弹出控件引用,
			g_popupGridEditDialog = null;
		}
	});
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
 * 插件表头新增,新增一行,表格的弹窗编辑改为form形式,这个方法暂没用了
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
        	if (button.relationDS && button.relationDS.relationItem) {
        		var formManager = new FormManager();
        		var bo = formManager.getBo();
        		var commonUtil = new CommonUtil();
        		var data = {};
        		var relationItem = commonUtil.getRelationItem(button.relationDS, bo, data);
        		if (!relationItem) {
        			showAlert("无法打开选择器");
        		} else {
        			window.s_selectFunc = function(selectValueLi) {
        				if (button.jsConfig && button.jsConfig.selectFunc) {
        					button.jsConfig.selectFunc(selectValueLi);
        				} else {
        					selectRowBtnDefaultAction(dataSetId, toolbarOrColumnModel, button, relationItem, selectValueLi);
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
        			
        			var url = webRoot + "/schema/selectorschema.do?@name={NAME_VALUE}&@multi={MULTI_VALUE}&@displayField={DISPLAY_FIELD_VALUE}&date=" + new Date();
        			var selectorName = relationItem.relationConfig.selectorName;
        			url = url.replace("{NAME_VALUE}", selectorName);
        			var multi = relationItem.relationConfig.selectionMode == "multi";
        			url = url.replace("{MULTI_VALUE}", multi);
        			var displayField = relationItem.relationConfig.displayField;
        			url = url.replace("{DISPLAY_FIELD_VALUE}", displayField);
        			var selectorTitle = g_relationBo[selectorName].description;
        			window.s_dialog = showModalDialog({
        				"title": selectorTitle,
        				"url": url
        			});
        			window.s_closeDialog = function() {
        				if (window.s_dialog) {
        					//window.s_dialog.hide();
        					window.s_dialog.dialog("destroy");
        				}
        				window.s_dialog = null;
        				window.s_selectFunc = null;
        				window.s_queryFunc = null;
        			}
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
function g_addRow(dataSetId, btnName) {
	var inputDataLi = [];
	var formManager = new FormManager();
	var data = formManager.getDataSetNewData(dataSetId);
	inputDataLi.push(data);
	g_gridPanelDict[dataSetId].createAddRowGrid(inputDataLi);
}

function getCopyArray(li) {
	var li2 = [];
	for (var i = 0; i < li.length; i++) {
		li2.push(li[i]);
	}
	return li2;
}

/**
 * 点击删除,删除多行
 */
function g_removeRow(dataSetId, btnName) {
	var selectRecordLi = g_gridPanelDict[dataSetId].getSelectRecordLi();
	// easyui delete rows时,selectRecordLi的长度会跟着变化，因此需要复制一个引用出来,
	selectRecordLi = getCopyArray(selectRecordLi);
	
	if (selectRecordLi.length == 0) {
		showAlert("请先选择");
	} else {
		showConfirm("您确定要删除吗？", function(){
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
					var index = g_gridPanelDict[dataSetId].dt.datagrid("getRowIndex", selectRecordLi[i]);
					g_gridPanelDict[dataSetId].dt.datagrid("deleteRow", index);
				}
			}
			if (hasUsed) {
				showAlert("部份数据已被用，不可删除！");
			}
		});
	}
}

/**
 * 点击删除,删除一行
 */
function g_removeSingleRow(o, dataSetId) {
	showConfirm("您确定要删除吗？", function(){
		var index = g_gridPanelDict[dataSetId].dt.datagrid("getRowIndex", o);
		g_gridPanelDict[dataSetId].dt.datagrid("deleteRow", index);
	});
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
function g_copySingleRow(o, dataSetId) {
	var inputDataLi = [];
	var formManager = new FormManager();
	var data = formManager.getDataSetCopyData(dataSetId, o);
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
			inputDataLi.push(selectRecordLi[i]);
		}
		g_gridPanelDict[dataSetId].createAddRowGrid(inputDataLi);
	}
}



