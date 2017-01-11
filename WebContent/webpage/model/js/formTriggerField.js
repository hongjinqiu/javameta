function PTriggerField(param) {
	var self = this;
	this.config = {};
	for (var key in param) {
		config[key] = param;
	}
	$("#" + config.id).textbox({
		readonly: true
	});
	$("#" + config.id).validatebox({
		validType:"validateTriggerField"
	});
	for ( var key in param) {
		self.set(key, param[key]);
	}

	var formManager = new FormManager();
	formManager.initializeAttr(self);
	self.initializeAttr();
	self.applyEventBehavior();
	formManager.applyEventBehavior(self);
}

PTriggerField.prototype.initializeAttr = function() {
	var self = this;
	// 需要配置在extraInfo里面,
	var selectFunc = function(selectValueLi, formObj){
		
	}
	var unSelectFunc = function(formObj){
		
	}
	var queryFunc = function() {
		return {};
	}
	var multi = false;
	var selectorName = "";
	var displayField = "";
	var valueField = "id";
	var selectorTitle = "";
	
	self._setDefaultSelectAction();
	
	var datasourceIterator = new DatasourceIterator();
	var result = "";
	datasourceIterator.iterateAllField(g_dataSourceJson, result, function(fieldGroup, result){
		if (fieldGroup.id == self.get("name") && fieldGroup.getDataSetId() == self.get("dataSetId")) {
			selectFunc = fieldGroup.jsConfig.selectFunc;
			unSelectFunc = fieldGroup.jsConfig.unSelectFunc;
			queryFunc = fieldGroup.jsConfig.queryFunc;
		}
	});
	var formFormTemplateIterator = new FormTemplateIterator();
	var result = "";
	var dataSetId = self.get("dataSetId");
	formFormTemplateIterator.iterateAnyTemplateColumn(dataSetId, result, function IterateFunc(column, result) {
		if (column.name == self.get("name")) {
			selectorName = function() {
				var relationItem = self._relationFuncTemplate(dataSetId, column);
				if (relationItem) {
					return relationItem.relationConfig.selectorName;
				}
				return "";
			}
			displayField = function() {
				var relationItem = self._relationFuncTemplate(dataSetId, column);
				if (relationItem) {
					return relationItem.relationConfig.displayField;
				}
				return "";
			}
			multi = function() {
				var relationItem = self._relationFuncTemplate(dataSetId, column);
				if (relationItem) {
					return relationItem.relationConfig.selectionMode == "multi";
				}
				return false;
			}
			valueField = function() {
				var relationItem = self._relationFuncTemplate(dataSetId, column);
				if (relationItem) {
					return relationItem.relationConfig.valueField;
				}
				return "";
			}
			selectorTitle = function() {
				var name = selectorName();
				if (name) {
					return g_relationBo[name].description;
				}
				return "";
			}
			
			return true;
		}
		return false;
	});
	
	this.set("multi", multi);
	this.set("selectorName", selectorName);
	this.set("displayField", displayField);
	this.set("valueField", valueField);
	this.set("selectFunc", selectFunc);
	this.set("unSelectFunc", unSelectFunc);
	this.set("queryFunc", queryFunc);
	this.set("selectorTitle", selectorTitle);
}

PTriggerField.prototype._setDefaultSelectAction = function() {
	var self = this;
	var datasourceIterator = new DatasourceIterator();
	var result = "";
	datasourceIterator.iterateAllField(g_dataSourceJson, result, function(fieldGroup, result){
		if (fieldGroup.id == self.get("name") && fieldGroup.getDataSetId() == self.get("dataSetId")) {
			if (!fieldGroup.jsConfig) {
				fieldGroup.jsConfig = {};
			}
			if (!fieldGroup.jsConfig.selectFunc) {
				fieldGroup.jsConfig.selectFunc = function(selectValueLi, formObj) {
					if (!selectValueLi || selectValueLi.length == 0) {
						self.get("unSelectFunc")(self);
					} else {
						formObj.set("value", selectValueLi.join(","));
					}
				}
			}
			if (!fieldGroup.jsConfig.unSelectFunc) {
				fieldGroup.jsConfig.unSelectFunc = function(formObj) {
					formObj.set("value", "");
				}
			}
			if (!fieldGroup.jsConfig.queryFunc) {
				fieldGroup.jsConfig.queryFunc = function() {
					return {};
				}
			}
		}
	});
}

PTriggerField.prototype.applyEventBehavior = function() {
	var self = this;
	var id = self.get("id");
	$("#" + id).change(function(){
		var formFormTemplateIterator = new FormTemplateIterator();
		var result = "";
		var dataSetId = self.get("dataSetId");
    	formFormTemplateIterator.iterateAnyTemplateColumn(dataSetId, result, function IterateFunc(column, result) {
    		if (column.name == self.get("name")) {
    			var relationItem = self._relationFuncTemplate(dataSetId, column);
				if (relationItem) {
					if (relationItem.copyConfig) {
						var selectorName = self.get("selectorName")();
						if (self.get("value")) {
							var selectorDict = g_relationManager.getRelationBo(selectorName, self.get("value"));
							if (selectorDict) {
								for (var i = 0; i < relationItem.copyConfig.length; i++) {
									var copyDestFieldName = relationItem.copyConfig[i].copyDestField;
									var copySrcField = relationItem.copyConfig[i].copySrcField;
									var fieldDict = self._getFieldDict();
									if (fieldDict && fieldDict[copyDestFieldName]) {
										var valueFieldLi = copySrcField.split(",");
										var valueLi = [];
										for (var j = 0; j < valueFieldLi.length; j++) {
											if (selectorDict[valueFieldLi[j]]) {
												valueLi.push(selectorDict[valueFieldLi[j]]);
											}
										}
										fieldDict[copyDestFieldName].set("value", valueLi.join(","));
									}
								}
							}
						} else {
							for (var i = 0; i < relationItem.copyConfig.length; i++) {
								var copyDestFieldName = relationItem.copyConfig[i].copyDestField;
								var fieldDict = self._getFieldDict();
								if (fieldDict && fieldDict[copyDestFieldName]) {
									fieldDict[copyDestFieldName].set("value", "");
								}
							}
						}
					}
				}
    			
    			return true;
    		}
    		return false;
    	});
	});
}

PTriggerField.prototype._getFieldDict = function() {
	var self = this;
	var dataSetId = self.get("dataSetId");
	var fieldDict = null;
	if (dataSetId == "A") {
		fieldDict = g_masterFormFieldDict;
	} else {
		if (g_popupFormField) {
			// 判断弹出框是否存在
			var isExists = false;
//			isMatchDetailEditor
			var formManager = new FormManager();
			for (var key in g_popupFormField) {
				var id = g_popupFormField[key].get("id");
				if ($("#" + id).length > 0 && formManager.isMatchDetailEditor(dataSetId)) {
					isExists = true;
				}
				break;
			}
			if (isExists) {
				fieldDict = g_popupFormField;
			}
		}
	}
	return fieldDict;
}

PTriggerField.prototype._relationFuncTemplate = function(dataSetId, column) {
	var self = this;
	var formManager = new FormManager();
	var bo = formManager.getBo();
	if (dataSetId == "A") {
		var data = bo["A"];
		
		var commonUtil = new CommonUtil();
		return commonUtil.getRelationItem(column.relationDS, bo, data);
	} else {
		var fieldDict = self._getFieldDict();
		
		var data = {};
		if (fieldDict) {
			for (var key in fieldDict) {
				data[key] = fieldDict[key].get("value");
			}
		}
		
		var commonUtil = new CommonUtil();
		return commonUtil.getRelationItem(column.relationDS, bo, data);
	}
}

PTriggerField.prototype._getStringOrFunctionResult = function(val){
	if (val) {
		if (typeof(val) == "function") {
			return val();
		}
		return val;
	}
	return "";
}

PTriggerField.prototype._getBooleanOrFunctionResult = function(val){
	if (typeof(val) == "function") {
		return val();
	}
	return val;
}

PTriggerField.prototype._syncDisplayValue = function() {// TODO,
	var self = this;
	var selectorName = this._getStringOrFunctionResult(this.get("selectorName"));
}


PTriggerField.prototype.get = function(key) {
	var self = this;
	
	if (key == "value") {
		return self.config[key];
	}
	
	return formFieldCommonGet(self, key);
}

PTriggerField.prototype.set = function(key, value) {
	var self = this;
	
	if (key == "value") {
		self.config[key] = value;
		// TODO set display value,
		return;
	}
	
	formFieldCommonSet(self, key, value);
}

