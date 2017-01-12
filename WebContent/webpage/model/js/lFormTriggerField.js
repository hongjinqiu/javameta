function LTriggerField(param) {
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

	var lFormManager = new LFormManager();
	lFormManager.initializeAttr(self);
	self.initializeAttr();

	lFormManager.applyEventBehavior(self);// 添加jsConfig中的事件
	self.applyEventBehavior();
	
	self.applyViewBtnStatus();
}

LTriggerField.prototype.applyViewBtnStatus = function() {
	var self = this;
	var multi = self._getBooleanOrFunctionResult(self.get("multi"));
	if (!multi) {
		$("#" + self.config.id + "_view").show();
	} else {
		$("#" + self.config.id + "_view").hide();
	}
}

LTriggerField.prototype.initializeAttr = function() {
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
	var formTemplateIterator = new FormTemplateIterator();
	var result = "";
	
	self._setDefaultSelectAction();
	
	formTemplateIterator.iterateAnyTemplateQueryParameter(result, function(queryParameter, result){
		if (queryParameter.name == self.get("name")) {
			selectFunc = queryParameter.jsConfig.selectFunc;
			unSelectFunc = queryParameter.jsConfig.unSelectFunc;
			queryFunc = queryParameter.jsConfig.queryFunc;
			
			// 从selector里面取,
			selectorName = function() {
				var queryParameterManager = new QueryParameterManager();
				var formData = queryParameterManager.getQueryFormData();
				var relationItem = self._relationFuncTemplate(queryParameter, formData);
				if (relationItem) {
					return relationItem.relationConfig.selectorName;
				}
				return "";
			}
			displayField = function() {
				var queryParameterManager = new QueryParameterManager();
				var formData = queryParameterManager.getQueryFormData();
				var relationItem = self._relationFuncTemplate(queryParameter, formData);
				if (relationItem) {
					return relationItem.relationConfig.displayField;
				}
				return "";
			}
			multi = function() {
				var queryParameterManager = new QueryParameterManager();
				var formData = queryParameterManager.getQueryFormData();
				var relationItem = self._relationFuncTemplate(queryParameter, formData);
				if (relationItem) {
					return relationItem.relationConfig.selectionMode == "multi";
				}
				return false;
			}
			valueField = function() {
				var queryParameterManager = new QueryParameterManager();
				var formData = queryParameterManager.getQueryFormData();
				var relationItem = self._relationFuncTemplate(queryParameter, formData);
				if (relationItem) {
					return relationItem.relationConfig.valueField;
				}
				return "";
			}
			selectorTitle = function() {
				var queryParameterManager = new QueryParameterManager();
				var formData = queryParameterManager.getQueryFormData();
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

LTriggerField.prototype._setDefaultSelectAction = function() {
	var self = this;
	var formTemplateIterator = new FormTemplateIterator();
	var result = "";
	formTemplateIterator.iterateAnyTemplateQueryParameter(result, function(queryParameter, result){
		if (queryParameter.name == self.get("name")) {
			if (!queryParameter.jsConfig) {
				queryParameter.jsConfig = {};
			}
			if (!queryParameter.jsConfig.selectFunc) {
				queryParameter.jsConfig.selectFunc = function(selectValueLi, formObj) {
					if (!selectValueLi || selectValueLi.length == 0) {
						queryParameter.jsConfig.unSelectFunc(formObj);
					} else {
						formObj.set("value", selectValueLi.join(","));
					}
				}
			}
			if (!queryParameter.jsConfig.unSelectFunc) {
				queryParameter.jsConfig.unSelectFunc = function(formObj) {
					formObj.set("value", "");
				}
			}
			if (!queryParameter.jsConfig.queryFunc) {
				queryParameter.jsConfig.queryFunc = function() {
					return {};
				}
			}
			
			return true;
		}
		return false;
	});
}

LTriggerField.prototype.applyEventBehavior = function() {
	var self = this;
	self._applyTextFieldEventBehavior();
	self._applySelectBtnEventBehavior();
	self._applyViewBtnEventBehavior();
	self._applyDeleteBtnEventBehavior();
}

/**
 * copyDestField的联动赋值,
 */
LTriggerField.prototype._applyTextFieldEventBehavior = function() {
	var self = this;
	var id = self.get("id");
	$("#" + id).change(function(self){
		return function(){
			var formTemplateIterator = new FormTemplateIterator();
			var result = "";
			formTemplateIterator.iterateAnyTemplateQueryParameter(result, function(queryParameter, result){
				if (queryParameter.Name == self.get("name")) {
					var queryParameterManager = new QueryParameterManager();
					var formData = queryParameterManager.getQueryFormData();
					
					var relationItem = self._relationFuncTemplate(queryParameter, formData);
					if (relationItem) {
						if (relationItem.copyConfig) {
							var selectorName = self.get("selectorName")();
							if (self.get("value")) {
								var selectorDict = g_relationManager.getRelationBo(selectorName, self.get("value"));
								if (selectorDict) {
									for (var i = 0; i < relationItem.copyConfig.length; i++) {
										var copyDestField = relationItem.copyConfig[i].copyDestField;
										var copySrcField = relationItem.copyConfig[i].copySrcField;
										if (g_masterFormFieldDict[copyDestField]) {
											var valueFieldLi = copySrcField.split(",");
											var valueLi = [];
											for (var j = 0; j < valueFieldLi.length; j++) {
												if (selectorDict[valueFieldLi[j]]) {
													valueLi.push(selectorDict[valueFieldLi[j]]);
												}
											}
											g_masterFormFieldDict[copyDestField].set("value", valueLi.join(","));
										}
									}
								}
							} else {
								for (var i = 0; i < relationItem.copyConfig.length; i++) {
									var copyColumnName = relationItem.copyConfig[i].copyDestField;
									if (g_masterFormFieldDict[copyColumnName]) {
										g_masterFormFieldDict[copyColumnName].set("value", "");
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

LTriggerField.prototype._applySelectBtnEventBehavior = function() {
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
        	
        	var url = "/console/selectorschema?@name={NAME_VALUE}&@id={ID_VALUE}&@multi={MULTI_VALUE}&@displayField={DISPLAY_FIELD_VALUE}&date=" + new Date();
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
		}
	}(self));
}

LTriggerField.prototype._objectReplace = function(text, obj) {
	for (var key in obj) {
		var regExp = new RegExp("{" + key + "}");
		regExp.global = true;
		text = text.replace(regExp, obj[key]);
	}
	return text;
}

LTriggerField.prototype._applyViewBtnEventBehavior = function() {
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

LTriggerField.prototype._applyDeleteBtnEventBehavior = function() {
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

LTriggerField.prototype._relationFuncTemplate = function(queryParameter, formData) {
	var commonUtil = new CommonUtil();
	var bo = {"A": formData};
	return commonUtil.getRelationItem(queryParameter.relationDS, bo, formData);
}

LTriggerField.prototype._getStringOrFunctionResult = function(val){
	if (val) {
		if (typeof(val) == "function") {
			return val();
		}
		return val;
	}
	return "";
}

LTriggerField.prototype._getBooleanOrFunctionResult = function(val){
	if (typeof(val) == "function") {
		return val();
	}
	return val;
}

LTriggerField.prototype._syncDisplayValue = function() {
	var self = this;
	var newValue = $("#" + self.get("id")).attr("idValue");
	var selectorName = self._getStringOrFunctionResult(self.get("selectorName"));
	var relationManager = new RelationManager();
    var li = newValue.split(",");
    var valueLi = [];
    for (var i = 0; i < li.length; i++) {
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
    $("#" + self.get("id")).val(valueLi.join(";"));
}


LTriggerField.prototype.get = function(key) {
	var self = this;
	
	if (key == "value") {
		return $("#" + self.get("id")).attr("idValue");
	}
	
	return formFieldCommonGet(self, key);
}

LTriggerField.prototype.set = function(key, value) {
	var self = this;
	
	if (key == "value") {
		$("#" + self.get("id")).attr("idValue", value);
		self._syncDisplayValue();
		return;
	}
	
	if (key == "readonly") {
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

