function PTriggerField(param) {
	var self = this;
	this.config = {};
	for (var key in param) {
		self.config[key] = param[key];
	}
	var easyUiConfig = {
		required: !getFieldAttrAllowEmpty(param["dataSetId"], param["name"]),
		readonly: true,
		validType:"validateTriggerField['{dataSetId}', '{name}']".replace(/{dataSetId}/g, param.dataSetId).replace(/{name}/g, param.name)
	};
	var fieldCls = getEditorAttrFieldCls(param["dataSetId"], param["name"]);
	if (fieldCls) {
	    easyUiConfig["cls"] = fieldCls;
	}
	$("#" + self.config.id).textbox(easyUiConfig);
	for ( var key in param) {
		self.set(key, param[key]);
	}

	var formManager = new FormManager();
	formManager.initializeAttr(self);
	self.initializeAttr();

	formManager.applyEventBehavior(self);// 添加jsConfig中的事件
	self.applyEventBehavior();
	
	self.applyViewBtnStatus();
}

PTriggerField.prototype.applyViewBtnStatus = function() {
	var self = this;
	var multi = self._getBooleanOrFunctionResult(self.get("multi"));
	if (!multi) {
		$("#" + self.config.id + "_view").show();
	} else {
		$("#" + self.config.id + "_view").hide();
	}
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
	datasourceIterator.iterateAllField(g_datasourceJson, result, function(fieldGroup, result){
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
	datasourceIterator.iterateAllField(g_datasourceJson, result, function(fieldGroup, result){
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
	self._applyTextFieldEventBehavior();
	self._applySelectBtnEventBehavior();
	self._applyViewBtnEventBehavior();
	self._applyDeleteBtnEventBehavior();
}

/**
 * copyDestField的联动赋值,
 */
PTriggerField.prototype._applyTextFieldEventBehavior = function() {
	var self = this;
	var id = self.get("id");
	self.set("change", function(self){
		return function(){
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
		}
	}(self));
}

PTriggerField.prototype._applySelectBtnEventBehavior = function() {
	var self = this;
	var id = self.get("id");
	$("#" + id + "_select").click(function(self){
		return function() {
			window.s_selectFunc = function(selectValueLi) {
        		var selection = self.get("selectFunc");
        		if (selection) {
        			selection(selectValueLi, self);
        		}
        	};
        	window.s_queryFunc = function() {
        		var queryFunc = self.get("queryFunc");
        		if (queryFunc) {
        			return queryFunc(self);
        		}
        		return {};
        	};
        	
        	var url = webRoot + "/schema/selectorschema.do?@name={NAME_VALUE}&@id={ID_VALUE}&@multi={MULTI_VALUE}&@displayField={DISPLAY_FIELD_VALUE}&date=" + new Date();
            var selectorName = self._getStringOrFunctionResult(self.get("selectorName"));
            if (!selectorName || selectorName == "NullSelector") {
            	showAlert("无法打开选择器");
            } else {
            	url = url.replace("{NAME_VALUE}", selectorName);
            	url = url.replace("{ID_VALUE}", self.get('value'));
            	var multi = self._getBooleanOrFunctionResult(self.get("multi"));
            	url = url.replace("{MULTI_VALUE}", multi);
            	var displayField = self._getStringOrFunctionResult(self.get("displayField"));
            	url = url.replace("{DISPLAY_FIELD_VALUE}", displayField);
            	var selectorTitle = self._getStringOrFunctionResult(self.get("selectorTitle"));
            	window.s_dialog = showModalDialog({
            		"title": selectorTitle,
            		"url": url
            	});
            	window.s_closeDialog = function() {
            		if (window.s_dialog) {
            			window.s_dialog.dialog('destroy');
            		}
            		window.s_dialog = null;
            		window.s_selectFunc = null;
            		window.s_queryFunc = null;
            	}
            }
		}
	}(self));
}

PTriggerField.prototype._objectReplace = function(text, obj) {
	for (var key in obj) {
		var regExp = new RegExp("{" + key + "}");
		regExp.global = true;
		text = text.replace(regExp, obj[key]);
	}
	return text;
}

PTriggerField.prototype._applyViewBtnEventBehavior = function() {
	var self = this;
	var id = self.get("id");
	var multi = self._getBooleanOrFunctionResult(self.get("multi"));
	if (!multi) {
		$("#" + id + "_view").click(function(self){
			return function() {
    			var value = self.get("value");
    			if (!value || value == "0") {
    				showAlert("没有数据，无法查看详情");
    			} else {
    				var selectorName = self._getStringOrFunctionResult(self.get("selectorName"));
    				var relationManager = new RelationManager();
    				var relationItem = relationManager.getRelationBo(selectorName, value);
    				var url = g_relationBo[selectorName]["url"] || "";
    				url = self._objectReplace(url, relationItem);
    				if (url) {
    					var selectorTitle = self._getStringOrFunctionResult(self.get("selectorTitle"));
    					triggerShowModalDialog({
    						"title": selectorTitle,
    						"url": url
    					});
    				} else {
    					showAlert("url为空，无法打开详情页面");
    				}
    			}
			}
		}(self));
	}
}

PTriggerField.prototype._applyDeleteBtnEventBehavior = function() {
	var self = this;
	var id = self.get("id");
	$("#" + id + "_delete").click(function(self){
		return function() {
			var unSelectFunc = self.get("unSelectFunc");
    		if (unSelectFunc) {
    			unSelectFunc(self);
    		}
		}
	}(self));
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

PTriggerField.prototype._syncDisplayValue = function() {
	var self = this;
	var newValue = $("#" + self.get("id")).attr("idValue");
	var selectorName = self._getStringOrFunctionResult(self.get("selectorName"));
	var relationManager = new RelationManager();
    var li = newValue.split(",");
    var valueLi = [];
    for (var i = 0; i < li.length; i++) {
    	if (!li[i]) {
    		continue;
    	}
    	var value = "";
    	var relationItem = relationManager.getRelationBo(selectorName, li[i]);
    	if (!relationItem) {
    		console.log("selectorName:" + selectorName + ", id:" + li[i] + " can't found relationItem");
    	}
    	var displayField = self._getStringOrFunctionResult(self.get("displayField"));
    	if (displayField.indexOf("{") > -1) {
    		value = displayField;
    		self._objectReplace(value, relationItem);
    	} else {
    		var keyLi = displayField.split(',');
    		for (var j = 0; j < keyLi.length; j++) {
    			if (relationItem[keyLi[j]]) {
    				value += relationItem[keyLi[j]] + ",";
    			}
    		}
    		if (value) {
    			value = value.substr(0, value.length - 1);
    		}
    	}
    	valueLi.push(value);
    }
    $("#" + self.get("id")).textbox("setValue", valueLi.join(";"));
    $("#" + self.get("id")).textbox("validate");
}


PTriggerField.prototype.get = function(key) {
	var self = this;
	
	if (key == "isValid") {
	    return $("#" + self.config.id).textbox("isValid");
	}
	if (key == "required") {
	    return $("#" + self.config.id).textbox("options").required;
	}
	if (key == "error") {
	    return $("#" + self.config.id).textbox("options").invalidMessage;
	}
	if (key == "value") {
		return $("#" + self.get("id")).attr("idValue");
	}
	
	return formFieldCommonGet(self, key);
}

PTriggerField.prototype.set = function(key, value) {
	var self = this;
	
	if (key == "required") {
	    $("#" + self.config.id).textbox("options").required = value;
		return;
	}
	if (key == "error") {
	    $("#" + self.config.id).textbox("options").invalidMessage = value;
	    return;
	}
	if (key == "change") {
		$("#" + self.config.id).textbox({"onChange": value});
		return;
	}
	
	if (key == "value") {
		$("#" + self.get("id")).attr("idValue", value);
		self._syncDisplayValue();
		return;
	}
	
	if (key == "readonly") {
		var formManager = new FormManager();
		if (formManager.isReadonlyForEver(self)) {
			value = true;
		}
		if (value) {
			$("#" + self.config.id).attr("readonly", "readonly");
			$("#" + self.config.id + "_select").hide();
			//$("#" + self.config.id + "_view").hide();
			$("#" + self.config.id + "_delete").hide();
		} else {
			$("#" + self.config.id).removeAttr("readonly");
			$("#" + self.config.id + "_select").show();
			/*
			var multi = self._getBooleanOrFunctionResult(self.get("multi"));
			if (!multi) {
				$("#" + self.config.id + "_view").show();
			}
			 */
			$("#" + self.config.id + "_delete").show();
		}
		return;
	}
	
	formFieldCommonSet(self, key, value);
}

