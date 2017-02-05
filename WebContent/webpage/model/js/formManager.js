function FormManager() {
}

FormManager.prototype.isMatchDetailEditor = function(dataSetId) {
	var isPopup = false;
	if (g_popupFormField) {
		for (var key in g_popupFormField) {
			if (g_popupFormField[key].get("dataSetId") == dataSetId) {
				isPopup = true;
			}
			break;
		}
	}
	return isPopup;
}

FormManager.prototype.getDataIsUsedForFormObj = function(formObj) {
	var self = formObj;
	var dataSetId = self.get("dataSetId");
	var isUsed = false;
	if (dataSetId == "A") {
		if (g_usedCheck) {
			if (g_usedCheck["A"]) {
				var idValue = g_masterFormFieldDict["id"].get("value");
				if (g_usedCheck["A"][idValue]) {
					isUsed = true;
				}
			}
		}
	} else {
		if (g_usedCheck) {
			if (g_usedCheck[dataSetId]) {
				// 现在的表格是一个弹出form页面,没有多条数据,取得这个弹出的一行是否被用,
				var formManager = new FormManager();
				if (formManager.isMatchDetailEditor(dataSetId)) {
					var idValue = g_popupFormField["id"].get("value");
					if (g_usedCheck[dataSetId][idValue]) {
						isUsed = true;
					}
				}
			}
		}
	}
	return isUsed;
}

/**
 * 返回	true	表示formObj readonly == true,不可修改readonly的值
 * @param formObj
 * @param val
 * @returns {Boolean}
 */
FormManager.prototype.isReadonlyForEver = function(formObj) {
	var self = formObj;
	var formTemplateIterator = new FormTemplateIterator();
	var result = "";
	var dataSetId = self.get("dataSetId");
	var readonlyForEver = false;
	
	formTemplateIterator.iterateAnyTemplateColumn(dataSetId, result, function(column, result){
		if (column.name == self.get("name")) {
			if (column.fixReadOnly) {
				readonlyForEver = true;
			}
			return true;
		}
		return false;
	});

	// 验证被用
	if (!readonlyForEver) {
		var datasourceIterator = new DatasourceIterator();
		var result = "";
		datasourceIterator.iterateAllField(g_datasourceJson, result, function(fieldGroup, result){
			if (fieldGroup.getDataSetId() == dataSetId && fieldGroup.id == self.get("name")) {
				var usedFormManager = new FormManager();
				var isUsed = usedFormManager.getDataIsUsedForFormObj(formObj);
				if (isUsed) {
					if (fieldGroup.denyEditInUsed) {
						readonlyForEver = true;
					}
				}
			}
		});
	}
	
	return readonlyForEver;
}

FormManager.prototype.initializeAttr = function(formObj) {
	var self = formObj;
	if (g_datasourceJson) {
    	var datasourceIterator = new DatasourceIterator();
    	var result = "";
    	datasourceIterator.iterateAllField(g_datasourceJson, result, function(fieldGroup, result){
    		if (fieldGroup.id == self.get("name") && fieldGroup.getDataSetId() == self.get("dataSetId")) {
    			if (fieldGroup.allowEmpty != true) {
    				self.set("required", true);
    			}
    		}
    	});
    	
    	var formTemplateIterator = new FormTemplateIterator();
    	var result = "";
    	var dataSetId = self.get("dataSetId");
    	formTemplateIterator.iterateAnyTemplateColumn(dataSetId, result, function(column, result){
    		if (column.name == self.get("name")) {
    			if (column.fixReadOnly) {
    				self.set("readonly", true);
    			} else if (column.readOnly) {
    				self.set("readonly", true);
    			}
    			if (column.zeroShowEmpty) {
    				self.set("zeroShowEmpty", true);
    			}
    			if (column.fieldWidth) {
    				self.set("fieldWidth", column.fieldWidth);
    			}
    			if (column.fieldHeight) {
    				self.set("fieldHeight", column.fieldHeight);
    			}
    			if (column.fieldCls) {
    				self.set("fieldCls", column.fieldCls);
    			}
    			return true;
    		}
    		return false;
    	});
    	
    	// apply number field currencyFormat
    	var formManager = new FormManager();
    	formManager.applyNumberDisplayPattern(formObj);

    	formManager.updateSingleFieldAttr4GlobalParam(formObj);
    }
}

/**
 * 应用numberField的格式化,
 */
FormManager.prototype.applyNumberDisplayPattern = function(formObj) {
	var self = formObj;
	var formTemplateIterator = new FormTemplateIterator();
	var result = "";
	var dataSetId = self.get("dataSetId");
	formTemplateIterator.iterateAnyTemplateColumn(dataSetId, result, function(column, result){
		if (column.xmlName == "number-column" && column.name == self.get("name")) {
			if (column.isPercent) {
				self.set("prefix", "");
				self.set("precision", 2);
				self.set("decimalSeparator", ".");
				self.set("groupSeparator", "");
				self.set("suffix", "%");
			} else {
				self.set("prefix", column.prefix);
				self.set("precision", column.decimalPlaces);
				self.set("decimalSeparator", column.decimalSeparator);
				self.set("groupSeparator", column.thousandsSeparator);
				self.set("suffix", column.suffix);
			}
			return true;
		}
		return false;
	});
}

/**
 * 当前,只更新主数据集字段,因为分录字段操作时,一般全局变量不会更新
 */
FormManager.prototype.updateAllFieldAttr4GlobalParam = function() {
	var self = this;
	for (var key in g_masterFormFieldDict) {
		var formObj = g_masterFormFieldDict[key];
		self.updateSingleFieldAttr4GlobalParam(formObj);
	}
}

FormManager.prototype.updateSingleFieldAttr4GlobalParam = function(formObj) {
	var self = formObj;
	if (g_datasourceJson) {
		// 被用,赋值readonly=true
		if (self.get("readonly") !== true) {
			var datasourceIterator = new DatasourceIterator();
			var result = "";
			datasourceIterator.iterateAllField(g_datasourceJson, result, function(fieldGroup, result){
				if (fieldGroup.id == self.get("name") && fieldGroup.getDataSetId() == self.get("dataSetId")) {
					var usedFormManager = new FormManager();
					var isUsed = usedFormManager.getDataIsUsedForFormObj(formObj);
					if (isUsed && fieldGroup.denyEditInUsed) {
						self.set("readonly", true);
					}
				}
			});
		}
	}
}

FormManager.prototype.applyEventBehavior = function(formObj) {
	var self = formObj;
	
	var dataSetId = self.get("dataSetId");
	var name = self.get("name");
	// 应用上js相关的操作,
    var datasourceIterator = new DatasourceIterator();
	var result = "";
	datasourceIterator.iterateAllField(g_datasourceJson, result, function(fieldGroup, result){
		if (fieldGroup.id == self.get("name") && fieldGroup.getDataSetId() == self.get("dataSetId")) {
			if (fieldGroup.jsConfig && fieldGroup.jsConfig.listeners) {
				for (var key in fieldGroup.jsConfig.listeners) {
					var id = self.get("id");
					if (key == "change") {
						self.set(key, function(key) {
							return function(newValue, oldValue) {
								fieldGroup.jsConfig.listeners[key](newValue, oldValue, self);
							}
						}(key));
					} else {
						self.set(key, function(key) {
							return function(e) {
								fieldGroup.jsConfig.listeners[key](e, self);
							}
						}(key));
					}
				}
			}
		}
	});
	// observe的添加,主要用于清值,暂时不支持树联动,
	var formTemplateIterator = new FormTemplateIterator();
	var result = "";
	formTemplateIterator.iterateAnyTemplateColumn(dataSetId, result, function(column, result){
		if (column.name == name) {
			if (column.editor && column.editor.editorAttribute) {
				for (var i = 0; i < column.editor.editorAttribute.length; i++) {
					if (column.editor.editorAttribute[i].name == "observe") {
						var observeFields = column.editor.editorAttribute[i].value.split(",");
						if (dataSetId == "A") {
							self.set("change", function() {
								for (var j = 0; j < observeFields.length; j++) {
									var targetObj = g_masterFormFieldDict[observeFields[j]];
									if (targetObj) {
										targetObj.set("value", "");
									}
								}
							});
						} else {
							self.set("change", function() {
								var formManager = new FormManager();
								if (formManager.isMatchDetailEditor(dataSetId)) {
									for (var j = 0; j < observeFields.length; j++) {
										var targetObj = g_popupFormField[observeFields[j]];
										if (targetObj) {
											targetObj.set("value", "");
										}
									}
								}
							});
						}
					}
				}
			}
			return true;
		}
		return false;
	});
}

FormManager.prototype.setChoices = function(formObj) {
	var self = formObj;
	var choices = [];
	if (g_layerBoLi) {
		var formTemplateIterator = new FormTemplateIterator();
    	var result = "";
    	var dataSetId = self.get("dataSetId");
    	formTemplateIterator.iterateAnyTemplateColumn(dataSetId, result, function(column, result){
    		if (column.name == self.get("name")) {
    			if (g_layerBoLi[column.dictionary]) {
					for (var k = 0; k < g_layerBoLi[column.dictionary].length; k++) {
						var dictionaryItem = g_layerBoLi[column.dictionary][k];
						choices.push({
							value: dictionaryItem["code"],
							label: dictionaryItem["name"]
						});
					}
				}
    			return true;
    		}
    		return false;
    	});
	}
	self.set("choices", choices);
}

FormManager.prototype.getBo = function() {
	var datasourceIterator = new DatasourceIterator();
	var datasource = g_datasourceJson;
	var bo = {"A": {}};
	var result = "";
	for (var key in g_masterFormFieldDict) {
		var formFieldObj = g_masterFormFieldDict[key];
		if (formFieldObj) {
			bo["A"][key] = formFieldObj.get("value");
		}
	}
	datasourceIterator.iterateAllDataSet(datasource, result, function(dataSet, result){
		var dataSetId = dataSet.id;
		if (dataSetId != "A") {
			var gridObj = g_gridPanelDict[dataSetId];
			if (gridObj) {
				bo[dataSetId] = gridObj.dt.datagrid("getRows");// getData返回{total: xxx, rows: []},因此用getRows,直接返回[]
			}
		}
	});
	if (bo["A"] && bo["A"]["id"]) {
		bo["id"] = bo["A"]["id"];
	} else {
		bo["id"] = "";
	}
	return bo;
}

FormManager.prototype.getDataSetNewData = function(dataSetId) {
	var self = this;
	var datasource = g_datasourceJson;
	var datasourceFactory = new DatasourceFactory();
	var bo = self.getBo();
	var data = {};
	datasourceFactory.applyDataSetDefaultValue(datasource, dataSetId, bo, data);
	datasourceFactory.applyDataSetCalcValue(datasource, dataSetId, bo, data);
	
	var result = "";
	var datasourceIterator = new DatasourceIterator();
	datasourceIterator.iterateAllDataSet(datasource, result, function(dataSet, result){
		if (dataSet.id == dataSetId) {
			if (dataSet.jsConfig && dataSet.jsConfig.afterNewData) {
				dataSet.jsConfig.afterNewData(bo, data);
			}
		}
	});
	data["id"] = datasourceFactory.getSequenceNo();
	return data;
}

FormManager.prototype.getDataSetCopyData = function(dataSetId, srcData) {
	var self = this;
	var datasource = g_datasourceJson;
	var datasourceFactory = new DatasourceFactory();
	var bo = self.getBo();
	var destData = {};
	datasourceFactory.applyDataSetDefaultValue(datasource, dataSetId, bo, destData);
	datasourceFactory.applyDataSetCopyValue(datasource, dataSetId, srcData, destData);
	datasourceFactory.applyDataSetCalcValue(datasource, dataSetId, bo, destData);
	
	var result = "";
	var datasourceIterator = new DatasourceIterator();
	datasourceIterator.iterateAllDataSet(datasource, result, function(dataSet, result){
		if (dataSet.id == dataSetId) {
			if (dataSet.jsConfig && dataSet.jsConfig.afterNewData) {
				dataSet.jsConfig.afterNewData(bo, destData);
			}
		}
	});
	destData["id"] = datasourceFactory.getSequenceNo();
	return destData;
}

/**
 * 数据源字段 fieldGroup 的验证器,返回messageLi
 * 其中,日期控件传的是 input 框里面的值,而不是value,日期控件,get("value")时,其取回的是yyyyMMdd,
 * @param value
 * @param fieldGroup
 */
FormManager.prototype.dsFieldGroupValidator = function(value, dateSeperator, fieldGroup) {
	var messageLi = [];
	if (fieldGroup.allowEmpty != true) {
		if (value === "" || value === null || value === undefined) {
			messageLi.push("不允许为空");
			return messageLi;
		}
	}
	
	var isDataTypeNumber = false;
	isDataTypeNumber = isDataTypeNumber || fieldGroup.fieldType == "DECIMAL";
	isDataTypeNumber = isDataTypeNumber || fieldGroup.fieldType == "DOUBLE";
	isDataTypeNumber = isDataTypeNumber || fieldGroup.fieldType == "FLOAT";
	isDataTypeNumber = isDataTypeNumber || fieldGroup.fieldType == "INT";
	isDataTypeNumber = isDataTypeNumber || fieldGroup.fieldType == "LONG";
	isDataTypeNumber = isDataTypeNumber || fieldGroup.fieldType == "SHORT";
	
	if (fieldGroup.allowEmpty != true) {
		if (isDataTypeNumber && (value == "0")) {
			messageLi.push("不允许为空");
			return messageLi;
		}
	}
	
	var isUnLimit = fieldGroup.limitOption == undefined || fieldGroup.limitOption == "" || fieldGroup.limitOption == "unLimit";
	var dateEnumLi = ["DATE","TIME","TIMESTAMP"];
	var isDate = false;
	for (var i = 0; i < dateEnumLi.length; i++) {
		if (dateEnumLi[i] == fieldGroup.fieldType) {
			isDate = true;
			break;
		}
	}
	if (isDataTypeNumber && isDate) {
		var isAllowEmptyAndZero = fieldGroup.allowEmpty && (value == "0" || value == "");
		if (fieldGroup.fieldType == "DATE") {
			var message = "";
			if (dateSeperator == "-") {
				message = "格式错误，正确格式类似于：1970-01-02";
			} else {
				message = "格式错误，正确格式类似于：1970/01/02";
			}
			if (!/^\d{4}\d{2}\d{2}$/.test(value) && !isAllowEmptyAndZero) {
				messageLi.push(message);
				return messageLi;
			}
		} else if (fieldGroup.fieldType == "TIME") {
			if (!/^\d{2}\d{2}\d{2}$/.test(value) && !isAllowEmptyAndZero) {
				messageLi.push("格式错误，正确格式类似于：03:04:05");
				return messageLi;
			}
		} else if (fieldGroup.fieldType == "TIMESTAMP") {
			var message = "";
			if (dateSeperator == "-") {
				message = "格式错误，正确格式类似于：1970-01-02 03:04:05";
			} else {
				message = "格式错误，正确格式类似于：1970/01/02 03:04:05";
			}
			if (!/^\d{4}\d{2}\d{2}\d{2}\d{2}\d{2}$/.test(value) && !isAllowEmptyAndZero) {
				messageLi.push(message);
				return messageLi;
			}
		}
	} else if (isDataTypeNumber) {
		// 经常用form模型来做报表查询页面,此时,界面上的控件经常是多选,因为,添加逗号支持
		var regexp = /^-?\d*(\.\d*)?$/;
		if (fieldGroup.relationDS && fieldGroup.relationDS.relationItem && fieldGroup.relationDS.relationItem.length > 0) {
			regexp = /^-?[\d,]*(\.\d*)?$/;
		}
		if (fieldGroup.id != "id" && !regexp.test(value)) {
			messageLi.push("必须由数字小数点组成");
			return messageLi;
		}
		if (!isUnLimit) {
			var fieldValueFloat = parseFloat(value);
			if (fieldGroup.limitOption == "limitMax") {
				var maxValue = parseFloat(fieldGroup.limitMax);
				if (maxValue < fieldValueFloat) {
					messageLi.push("超出最大值" + fieldGroup.limitMax);
				}
			} else if (fieldGroup.limitOption == "limitMin") {
				var minValue = parseFloat(fieldGroup.limitMin);
				if (fieldValueFloat < minValue) {
					messageLi.push("小于最小值" + fieldGroup.limitMin);
				}
			} else if (fieldGroup.limitOption == "limitRange") {
				var minValue = parseFloat(fieldGroup.limitMin);
				var maxValue = parseFloat(fieldGroup.limitMax);
				if (fieldValueFloat < minValue || maxValue < fieldValueFloat) {
					messageLi.push("超出范围("+fieldGroup.limitMin+"~"+fieldGroup.limitMax+")");
				}
			}
		}
	} else {
		var isDataTypeString = false;
		isDataTypeString = isDataTypeString || fieldGroup.fieldType == "STRING";
		var isFieldLengthLimit = fieldGroup.fieldLength != "";
		if (isDataTypeString && isFieldLengthLimit) {
			var limit = parseFloat(fieldGroup.fieldLength);
			if (value.length > limit) {
				messageLi.push("长度超出最大值"+fieldGroup.fieldLength);
			}
		}
	}
	return messageLi;
}

/**
 * datasource field validator
 */
FormManager.prototype.dsFormFieldValidator = function(value, formFieldObj, param) {
	var self = this;
	var datasourceIterator = new DatasourceIterator();
	var messageLi = [];
	var result = "";
	var formManager = new FormManager();
	datasourceIterator.iterateAllField(g_datasourceJson, result, function(fieldGroup, result){
		if (fieldGroup.id == formFieldObj.get("name") && fieldGroup.getDataSetId() == formFieldObj.get("dataSetId")) {
			var dateSeperator = formManager._getDateSeperator(fieldGroup.getDataSetId(), fieldGroup.id);
			messageLi = formManager.dsFieldGroupValidator(value, dateSeperator, fieldGroup);
		}
	});
	
	if (messageLi.length > 0) {
		formFieldObj.set("error", messageLi.join("<br />"));
		if (param) {
			param[2] = messageLi.join("<br />");// easyui控件配置,message: "{2}",会把这里的message替换进去
		}
		return false;
	}
	
	return true;
}

FormManager.prototype._getDateSeperator = function(dataSetId, name) {
	var dateSeperator = null;
	var formTemplateIterator = new FormTemplateIterator();
	var result = "";
	formTemplateIterator.iterateAnyTemplateColumn(dataSetId, result, function(column, result){
		if (column.name == name) {
			if (column.xmlName == "date-column") {
				if (column.displayPattern.indexOf("-") > -1) {
					dateSeperator = "-";
				} else if (column.displayPattern.indexOf("/") > -1) {
					dateSeperator = "/";
				}
			}
			return true;
		}
		return false;
	});
	return dateSeperator;
}

/**
 * 表单验证
 * @param datasource
 * @param bo
 * @returns
 */
FormManager.prototype.dsFormValidator = function(datasource, bo) {
	var datasourceIterator = new DatasourceIterator();
	var messageLi = [];
	var masterMessageLi = [];
	var detailMessageDict = {};
	var result = "";
	var formManager = new FormManager();
	datasourceIterator.iterateAllFieldBo(datasource, bo, result, function(fieldGroup, data, rowIndex, result){
		if (fieldGroup.isMasterField()) {
			var formFieldObj = g_masterFormFieldDict[fieldGroup.id];
			var value = data[fieldGroup.id];
			if (value !== undefined && formFieldObj) {
				if(!formManager.dsFormFieldValidator(value, formFieldObj)) {
					masterMessageLi.push(fieldGroup.displayName + formFieldObj.get("error"));
				}
			}
		} else {
			var value = data[fieldGroup.id];
			if (value !== undefined) {
				var dateSeperator = formManager._getDateSeperator(fieldGroup.getDataSetId(), fieldGroup.id);
				var lineMessageLi = formManager.dsFieldGroupValidator(value, dateSeperator, fieldGroup);
				if (lineMessageLi.length > 0) {
					if (!detailMessageDict[fieldGroup.getDataSetId()]) {
						detailMessageDict[fieldGroup.getDataSetId()] = [];
					}
					detailMessageDict[fieldGroup.getDataSetId()].push("序号为" + (rowIndex + 1) + "的分录，" + fieldGroup.displayName + lineMessageLi.join("，"));
				}
			}
		}
	});
	
	datasourceIterator.iterateAllDataSet(datasource, result, function(dataSet, result){
		if (dataSet.jsConfig && dataSet.jsConfig.validateEdit) {
			var dataSetBo = bo[dataSet.id];
			var validateEditMessageLi = dataSet.jsConfig.validateEdit(dataSetBo);
			for (var i = 0; i < validateEditMessageLi.length; i++) {
				if (dataSet.id == "A") {
					masterMessageLi.push(validateEditMessageLi[i]);
				} else {
					if (!detailMessageDict[dataSet.id]) {
						detailMessageDict[dataSet.id] = [];
					}
					detailMessageDict[dataSet.id].push(validateEditMessageLi[i]);
				}
			}
		}
	});
	
	datasourceIterator.iterateAllDataSet(datasource, result, function(dataSet, result){
		if (dataSet.id != "A" && dataSet.allowEmpty != true) {
			var isEmpty = false;
			if (!bo[dataSet.id]) {
				isEmpty = true;
			} else {
				if (bo[dataSet.id].length == 0) {
					isEmpty = true;
				}
			}
			if (isEmpty) {
//				messageLi.push("分录:"+dataSet.displayName+"不允许为空");
				if (!detailMessageDict[dataSet.id]) {
					detailMessageDict[dataSet.id] = [];
				}
				detailMessageDict[dataSet.id].push("分录"+dataSet.displayName+"不允许为空");
			}
		}
	});
	
	if (datasource.jsConfig && datasource.jsConfig.validate) {
		datasource.jsConfig.validate(bo, masterMessageLi, detailMessageDict);
	}
	
	// 合计数据集错误信息到messageLi中
	datasourceIterator.iterateAllDataSet(datasource, result, function(dataSet, result){
		if (dataSet.id == "A") {
			if (masterMessageLi.length > 0) {
				messageLi.push(dataSet.displayName + "错误信息：");
				for (var i = 0; i < masterMessageLi.length; i++) {
					messageLi.push(masterMessageLi[i]);
				}
			}
		} else {
			if (detailMessageDict[dataSet.id] && detailMessageDict[dataSet.id].length > 0) {
				messageLi.push(dataSet.displayName + "错误信息：");
				for (var i = 0; i < detailMessageDict[dataSet.id].length; i++) {
					messageLi.push(detailMessageDict[dataSet.id][i]);
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

/**
 * 编辑分录编辑器
 * @param datasource
 * @param dataSetId
 * @param detailDataLi
 * @returns
 */
FormManager.prototype.dsDetailValidator = function(datasource, dataSetId, detailDataLi) {
	var bo = {};
	bo[dataSetId] = detailDataLi;
	var datasourceIterator = new DatasourceIterator();
	var result = "";
	datasourceIterator.iterateAllDataSet(datasource, result, function(dataSet, result){
		if (dataSet == "A") {
			bo["A"] = {};
		} else if (!bo[dataSet]) {
			bo[dataSet] = [];
		}
	});
	
	var messageLi = [];
	var formManager = new FormManager();
	datasourceIterator.iterateAllFieldBo(datasource, bo, result, function(fieldGroup, data, rowIndex, result){
		if (!fieldGroup.isMasterField() && fieldGroup.getDataSetId() == dataSetId) {
			if (formManager.isMatchDetailEditor(dataSetId)) {
				var formFieldObj = g_popupFormField[fieldGroup.id];
				var value = data[fieldGroup.id];
				if (value !== undefined && formFieldObj) {
					if(!formManager.dsFormFieldValidator(value, formFieldObj)) {
						messageLi.push("序号为" + (rowIndex + 1) + "的分录，" + fieldGroup.displayName + formFieldObj.get("error"));
					}
				}
			}
		}
	});
	
	// apply model.js validateEdit 函数
	var result = "";
	datasourceIterator.iterateAllDataSet(datasource, result, function(dataSet, result){
		if (dataSet.id == dataSetId) {
			if (dataSet.jsConfig && dataSet.jsConfig.validateEdit) {
				var validateEditMessageLi = dataSet.jsConfig.validateEdit(detailDataLi);
				for (var i = 0; i < validateEditMessageLi.length; i++) {
					messageLi.push(validateEditMessageLi[i]);
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

FormManager.prototype.setFormStatus = function(status) {
	var self = this;
	g_formStatus = status;
	self._setMasterFormFieldStatus(status);
	self._setDetailGridStatus(status);
	var toolbarManager = new ToolbarManager();
	toolbarManager.enableDisableToolbarBtn();
	self.updateAllFieldAttr4GlobalParam();
}

FormManager.prototype._setMasterFormFieldStatus = function(status) {
	for (var key in g_masterFormFieldDict) {
		g_masterFormFieldDict[key].set("readonly", status == "view");
	}
}

FormManager.prototype._setDetailGridStatus = function(status) {
	var datasourceIterator = new DatasourceIterator();
	var result = "";
	var datasource = g_datasourceJson;
	datasourceIterator.iterateAllDataSet(datasource, result, function(dataSet, result){
		if (dataSet.id != "A") {
			var tbar = document.getElementById(dataSet.id + "_tbar");
			if (tbar) {
				if (status == "view") {
					tbar.style.display = "none";
				} else {
					tbar.style.display = "";
				}
			}
			var detailGrid = g_gridPanelDict[dataSet.id];
			if (detailGrid) {
				var formTemplateIterator = new FormTemplateIterator();
				formTemplateIterator.iterateAnyTemplateColumn(dataSet.id, result, function(column, result){
					if (column.xmlName == "virtual-column") {
						if (status == "view") {
							g_gridPanelDict[dataSet.id].dt.datagrid("hideColumn", column.name);
						} else {
							g_gridPanelDict[dataSet.id].dt.datagrid("showColumn", column.name);
						}
						return true;
					}
					return false;
				});
			}
		}
	});
}

FormManager.prototype.applyGlobalParamFromAjaxData = function(o) {
	var self = this;
	g_relationBo = o.relationBo;
	g_usedCheck = o.usedCheckBo;
}

/**
 * 需要按datasource的顺序来加载字段值,否则计算表格式时会出错
 */
FormManager.prototype.loadData2Form = function(datasource, bo) {
	var datasourceIterator = new DatasourceIterator();
	var result = "";
	if (bo["A"]) {
		var keyInMaster = [];
		// 遍历主数据集字段,按顺序加载值
		datasourceIterator.iterateAllField(datasource, result, function(fieldGroup, result){
			if (fieldGroup.getDataSetId() == "A") {
				if (g_masterFormFieldDict[fieldGroup.id]) {
					g_masterFormFieldDict[fieldGroup.id].set("value", bo["A"][fieldGroup.id] || "");
				}
				keyInMaster.push(fieldGroup.id);
			}
		});
		for (var key in bo["A"]) {
			var isIn = false;
			for (var i = 0; i < keyInMaster.length; i++) {
				if (keyInMaster[i] == key) {
					isIn = true;
					break;
				}
			}
			if (!isIn) {
				if (g_masterFormFieldDict[key]) {
					g_masterFormFieldDict[key].set("value", bo["A"][key] || "");
				}
			}
		}
	}
	datasourceIterator.iterateAllDataSet(datasource, result, function(dataSet, result){
		if (dataSet.id != "A") {
			if (g_gridPanelDict[dataSet.id]) {
				if (bo[dataSet.id] !== undefined) {
					g_gridPanelDict[dataSet.id].dt.datagrid("loadData", bo[dataSet.id]);
				} else {
					g_gridPanelDict[dataSet.id].dt.datagrid("loadData", []);
				}
			}
		}
	});
}

FormManager.prototype.getFieldDict = function(formObj) {
	var self = formObj;
	var dataSetId = self.get("dataSetId");
	var fieldDict = null;
	if (dataSetId == "A") {
		fieldDict = g_masterFormFieldDict;
	} else {
		var formManager = new FormManager();
		if (formManager.isMatchDetailEditor(dataSetId)) {
			fieldDict = g_popupFormField;
		}
	}
	return fieldDict;
}

FormManager.prototype.setDetailIncId = function(datasource, bo) {
	var datasourceFactory = new DatasourceFactory();
	var datasourceIterator = new DatasourceIterator();
	var result = "";
	datasourceIterator.iterateDataBo(datasource, bo, result, function(fieldGroupLi, data, rowIndex, result) {
		if (!fieldGroupLi[0].isMasterField()) {
			if (data["id"] == "0" || data["id"] == "") {
				data["id"] = datasourceFactory.getSequenceNo();
			}
		}
	});
}

FormManager.prototype.initGridCommondDict = function() {
	var formTemplateIterator = new FormTemplateIterator();
	var result = "";
	formTemplateIterator.iterateAllTemplateColumnModel(result, function(columnModel, result) {
		if (columnModel.dataSetId != "A") {
			if (!g_gridCommondDict[columnModel.dataSetId]) {
				g_gridCommondDict[columnModel.dataSetId] = [];
			}
		}
	});
}
