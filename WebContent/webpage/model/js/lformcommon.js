function ChoiceFieldManager(){}

ChoiceFieldManager.prototype.getChoices = function(name) {
	var choices = [];
	var formTemplateIterator = new FormTemplateIterator();
	var result = "";
	formTemplateIterator.iterateAnyTemplateQueryParameter(result, function(queryParameter, result){
		if (queryParameter.name == name) {
			for (var i = 0; i < queryParameter.parameterAttribute.length; i++) {
				if (queryParameter.parameterAttribute[i].name == "dictionary") {
					var dictionaryCode = queryParameter.parameterAttribute[i].value;
					var dictValueLi = g_layerBoLi[dictionaryCode];
					for (var j = 0; j < dictValueLi.length; j++) {
						choices.push({
							"label": dictValueLi[j].name,
							"value": dictValueLi[j].code
						});
					}
					break;
				}
			}
			return true;
		}
		return false;
	});
	return choices;
}

function LFormManager(){}

LFormManager.prototype.applyEventBehavior = function(formObj) {
	var self = formObj;
	var formTemplateIterator = new FormTemplateIterator();
	var result = "";
	formTemplateIterator.iterateAnyTemplateQueryParameter(result, function(queryParameter, result){
		if (queryParameter.name == self.get("name")) {
			if (queryParameter.jsConfig) {
				for (var key in queryParameter.jsConfig.listeners) {
					if (key == "change") {
						self.set("change", function(key) {
							return function(newValue, oldValue) {
								queryParameter.jsConfig.listeners[key](newValue, oldValue, self);
							}
						}(key));
					} else {
						self.set(key, function(key) {
							return function(e) {
								queryParameter.jsConfig.listeners[key](e, self);
							}
						}(key));
					}
				}
			}
			
			return true;
		}
		return false;
	});
}

// 针对numberfield,datefield,验证一下其基本格式
LFormManager.prototype.initializeAttr = function(formObj, Y) {
	var self = formObj;
	var lFormManager = new LFormManager();
//	self.set("validator", lFormManager.queryParameterFieldValidator);
}

LFormManager.prototype.queryParameterFieldValidator = function(value, formFieldObj, param) {
	var self = formFieldObj;
	
	var messageLi = [];
	var formTemplateIterator = new FormTemplateIterator();
	var result = "";
	var lFormManager = new LFormManager();
	formTemplateIterator.iterateAnyTemplateQueryParameter(result, function(queryParameter, result){
		if (queryParameter.name == self.get("name")) {
			messageLi = lFormManager.qpFieldValidator(value, queryParameter);
			return true;
		}
		return false;
	});
	
	if (messageLi.length > 0) {
		formFieldObj.set("error", messageLi.join("<br />"));
		if (param) {
			param[1] = messageLi.join("<br />");// easyui控件配置,message: "{1}",会把这里的message替换进去
		}
		return false;
	} else {
		formFieldObj.set("error", "");
	}
	
	return true;
}

LFormManager.prototype.qpFieldValidator = function(value, queryParameter) {
	var messageLi = [];
	if (queryParameter.editor == "datefield") {
		value = value.replace(/[ :\/-]/g, "");
		var dbPattern = "";
		var displayPattern = "";
		var dateSeperator = "-";
		for (var i = 0; i < queryParameter.parameterAttribute.length; i++) {
			if (queryParameter.parameterAttribute[i].name == "dbPattern") {
				dbPattern = queryParameter.parameterAttribute[i].value;
			} else if (queryParameter.parameterAttribute[i].name == "displayPattern") {
				displayPattern = queryParameter.parameterAttribute[i].value;
			}
		}
		if (displayPattern.indexOf("-") > -1) {
			dateSeperator = "-";
		} else if (displayPattern.indexOf("/") > -1) {
			dateSeperator = "/";
		}
		if (dbPattern == "yyyy") {
			if (!/^\d{4}$/.test(value)) {
				messageLi.push("格式错误，正确格式类似于：1970");
				return messageLi;
			}
		} else if (dbPattern == "yyyyMMdd") {
			var message = "";
			if (dateSeperator == "-") {
				message = "格式错误，正确格式类似于：1970-01-02";
			} else {
				message = "格式错误，正确格式类似于：1970/01/02";
			}
			if (!/^\d{4}\d{2}\d{2}$/.test(value)) {
				messageLi.push(message);
				return messageLi;
			}
		} else if (dbPattern == "HHmmss") {
			if (!/^\d{2}\d{2}\d{2}$/.test(value)) {
				messageLi.push("格式错误，正确格式类似于：03:04:05");
				return messageLi;
			}
		} else if (dbPattern == "yyyyMMddHHmmss") {
			if (value && value.length == 12) {
				value = value + "00";
			}
			var message = "";
			if (dateSeperator == "-") {
				message = "格式错误，正确格式类似于：1970-01-02 03:04:05";
			} else {
				message = "格式错误，正确格式类似于：1970/01/02 03:04:05";
			}
			if (!/^\d{4}\d{2}\d{2}\d{2}\d{2}\d{2}$/.test(value)) {
				messageLi.push(message);
				return messageLi;
			}
		}
	} else if (queryParameter.editor == "numberfield") {
		var regexp = /^-?\d*(\.\d*)?$/;
		if (!regexp.test(value)) {
			messageLi.push("必须由数字小数点组成");
			return messageLi;
		}
	}
	return messageLi;
}

