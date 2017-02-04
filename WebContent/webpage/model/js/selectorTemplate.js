function getFirstDataSetIdOrColumnModelName() {
	var formTemplateIterator = new FormTemplateIterator();
	var firstDataSetIdOrColumnModelName = "";
	var result = "";
	formTemplateIterator.iterateAnyTemplateColumnModel(result, function(columnModel, result) {
		firstDataSetIdOrColumnModelName = columnModel.dataSetId || columnModel.name;
		return true;
	});
	return firstDataSetIdOrColumnModelName;
}

function syncSelection(record) {
	if (typeof(record) == "object") {// easyui有bug,有时候会传"id"进来,
		// 是否添加
		var id = record["id"];
		var idInputItem = $("#selectionResult .selectionItem input[value='" + id + "']");
		if (idInputItem.length == 0) {
			var tempLi = ['<div class="selectionItem">'];
			tempLi.push('<div class="left">{display}</div>');
			tempLi.push('<div class="right" onclick="removeSelection(this)"><input type="hidden" name="selectionId" value="{id}" /></div>');
			tempLi.push('</div>');
			
			var display = "";
			var formTemplateIterator = new FormTemplateIterator();
			var result = "";
			formTemplateIterator.iterateAnyTemplateColumnModel(result, function(columnModel, result){
				display = objectReplace(columnModel.selectionTemplate, record);
				return true;
			});
			
			var tempContent = objectReplace(tempLi.join(""), {
				"display": display,
				"id": id
			});
			
			$("#selectionResult").html($("#selectionResult").html() + tempContent);
		}
	}
}

function syncSelectionWhenChangeCheckbox(dataGrid) {
	var checkLi = dataGrid.datagrid("getChecked");
	var rows = dataGrid.datagrid("getRows");
	var unCheckLi = [];
	for (var i = 0; i < rows.length; i++) {
		var isIn = false;
		for (var j = 0; j < checkLi.length; j++) {
			if (rows[i].id == checkLi[j].id) {
				isIn = true;
				break;
			}
		}
		if (!isIn) {
			unCheckLi.push(rows[i]);
		}
	}
	
	if (rows.length > 0) {// easyui有bug,有时候getChecked返回["id"],但是getRows返回[]
		for (var i = 0; i < checkLi.length; i++) {
			var record = checkLi[i];
			g_selectionManager.addSelectionBo(record);
			syncSelection(record);
		}
	}
	for (var i = 0; i < unCheckLi.length; i++) {
		// 是否删除
		var id = unCheckLi[i]["id"];
		var idInputItem = $("#selectionResult .selectionItem input[value='" + id + "']");
		if (idInputItem.length > 0) {
			var selectionItemLi = $("#selectionResult .selectionItem");
			selectionItemLi.each(function(selectionItemIndex, selectionItem){
				if ($(selectionItem).find("input[value='" + id + "']").length > 0) {
					$(selectionItem).remove();
				}
			});
		}
	}
}

function removeSelection(elem) {
	$(elem).parent(".selectionItem").remove();
	syncCheckboxWhenChangeSelection(dtInst.dt);
}

function syncCheckboxWhenChangeSelection(dataGrid) {
	var selectionInputValueLi = [];
	$("#selectionResult .selectionItem input").each(function(index, item){
		selectionInputValueLi.push($(item).val());
	});
	//dataGrid.datagrid("uncheckAll");
	//dataGrid.datagrid("unselectAll");
	dataGrid.datagrid("clearChecked");
	for (var i = 0; i < selectionInputValueLi.length; i++) {
		var index = dataGrid.datagrid("getRowIndex", selectionInputValueLi[i]);
		dataGrid.datagrid("checkRow", index);
	}
}

/**
 * form页面传id进来,后端将其放置到g_selectionBo中,选择器要回显这些内容放到选择区域内,并同步到选择框中
 */
function syncCallbackSelection() {
	if (g_selectionBo) {
		for (var key in g_selectionBo) {
			if (/^\d+$/g.test(key)) {
				syncSelection(g_selectionBo[key]);
			}
		}
	}
	// 此时表格有可能还没好,因此需要用setTimeout来同步一次,
	/*
	setTimeout(function() {
		syncCheckboxWhenChangeSelection(dtInst.dt);
	}, 1000);
	*/
}

function createSelectorTemplateGrid() {
	var url = webRoot + "/schema/selectorschema.do?@name=" + listTemplate.id + "&format=json";
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
			// 此时表格有可能还没好,因此需要用setTimeout来同步一次,
			setTimeout(function() {
				syncCheckboxWhenChangeSelection(dtInst.dt);
			}, 500);
		},
		onCheck: function(index,row){
			syncSelectionWhenChangeCheckbox(dtInst.dt);
		},
		onUncheck: function(index, row) {
			syncSelectionWhenChangeCheckbox(dtInst.dt);
		},
		onCheckAll: function(rows) {
			syncSelectionWhenChangeCheckbox(dtInst.dt);
		},
		onUncheckAll: function(rows) {
			syncSelectionWhenChangeCheckbox(dtInst.dt);
		}
	});
}

function selectorMain() {
	/*
	var id = listTemplate.id;
	var url = webRoot + "/schema/selectorschema.do?@name=" + id + "&format=json";
	createGridWithUrl(url);
	var dataGrid = dtInst.dt;
	dataGrid.datagrid({
		onCheck: function(index,row){
			syncSelectionWhenChangeCheckbox(dtInst.dt);
		},
		onCheckAll: function(rows) {
			syncSelectionWhenChangeCheckbox(dtInst.dt);
		}
	});
	*/
	
	/*
	var queryParameterManager = new QueryParameterManager();
	queryParameterManager.applyQueryDefaultValue();
	queryParameterManager.applyFormData();
	queryParameterManager.applyObserveEventBehavior();
	applyQueryBtnBehavior();
	*/
	
	$("#confirmBtn").on("click", function(e){
		if (parent && parent.g_relationManager) {
			var selectorId = listTemplate.id;
			var selectValueLi = [];
			$("#selectionResult .selectionItem input").each(function(index, item){
				selectValueLi.push($(item).val());
			});
			
			if (!selectValueLi || selectValueLi.length == 0) {
				//showAlert("请先选择");
				parent.s_selectFunc([]);
			} else {
				for (var i = 0; i < selectValueLi.length; i++) {
					if (g_selectionBo[selectValueLi[i]]) {
						parent.g_relationManager.addRelationBo(selectorId, g_selectionBo["url"], g_selectionBo[selectValueLi[i]]);
					}
				}
				parent.s_selectFunc(selectValueLi);
			}
			parent.s_closeDialog();
		} else {
			alert("找不到父窗口，无法赋值！");
		}
	});
	$("#clearBtn").on("click", function(e){
		$("#selectionResult").html("");
		syncCheckboxWhenChangeSelection(dtInst.dt);
	});
	// 取得父函数的queryFunc,并设置到页面上的hidden field里面,最后调用refresh,应用这些参数查询数据
	if (parent && parent.s_queryFunc) {
		var queryDict = parent.s_queryFunc();
		for (var key in queryDict) {
			if (g_masterFormFieldDict[key]) {
				g_masterFormFieldDict[key].set("value", queryDict[key]);
			}
		}
	}
	// 创建表格
	for (var i = 0; i < g_delayLi.length; i++) {
		g_delayLi[i]();
	}
	// 同步g_selectionBo到选择区域,
	syncCallbackSelection();
	
	//g_gridPanelDict[getFirstDataSetIdOrColumnModelName()].dt.datagrid("load");
}
