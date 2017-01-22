/*
	$.extend($.fn.validatebox.defaults.rules, {
	    equals: {
	        validator: function(value,param){
	            return value == $(param[0]).val();
	        },
	        message: 'Field do not match.'
	    }
	});

required:true,
validateOnCreate: false,
validType:['email','length[0,20]']

t.textbox("validate")

	t.validatebox("validate")
	t.validatebox("enableValidation")
	t.validatebox("disableValidation")
	t.validatebox("isValid")
 */

function commonValidate(fieldElem, value, param) {
	var lFormManager = new LFormManager();
	var name = param[0];
	var formObj = g_masterFormFieldDict[name];
	return lFormManager.queryParameterFieldValidator(value, formObj, param);
}

function validateTextField(value, param) {
	var self = this;
	return commonValidate(self, value, param);
}

function validateHiddenField(value, param) {
	var self = this;
	return commonValidate(self, value, param);
}

function validateSelectField(value, param) {
	var self = this;
	return commonValidate(self, value, param);
}

function validateChoiceField(value, param) {
	var self = this;
	return commonValidate(self, value, param);
}

function validateNumberField(value, param) {
	var self = this;
	return commonValidate(self, value, param);
}

function validateDateField(value, param) {
	var self = this;
	return commonValidate(self, value, param);
}

function validateTextareaField(value, param) {
	var self = this;
	return commonValidate(self, value, param);
}

function validateTriggerField(value, param) {
	var self = this;
	return commonValidate(self, value, param);
}

function validateDisplayField(value, param) {
	var self = this;
	return commonValidate(self, value, param);
}

$.extend($.fn.validatebox.defaults.rules, {
	validateTextField : {
		validator : validateTextField
		,message: '{1}'
	},
	validateHiddenField : {
		validator : validateHiddenField
		,message: '{1}'
	},
	validateSelectField : {
		validator : validateSelectField
		,message: '{1}'
	},
	validateChoiceField : {
		validator : validateChoiceField
		,message: '{1}'
	},
	validateNumberField : {
		validator : validateNumberField
		,message: '{1}'
	},
	validateDateField : {
		validator : validateDateField
		,message: '{1}'
	},
	validateTextareaField : {
		validator : validateTextareaField
		,message: '{1}'
	},
	validateTriggerField : {
		validator : validateTriggerField
		,message: '{1}'
	},
	validateDisplayField : {
		validator : validateDisplayField
		,message: '{1}'
	}
});

function formFieldCommonGet(self, key) {
	if (key == "value") {
		return $("#" + self.config.id).val();
	}
	if (key == "readonly") {
		return $("#" + self.config.id).attr("readonly") == "readonly";
	}
	if (key == "fieldCls") {
		return self.config[key];
	}
	return self.config[key];
}

function formFieldCommonSet(self, key, value) {
	if (key == "value") {
		if (self.get("zeroShowEmpty")) {
			if (value === 0 || value === "0") {
				value = "";
			}
		}

		$("#" + self.config.id).val(value);
		return;
	}
	if (key == "readonly") {
		if (value) {
			$("#" + self.config.id).attr("readonly", "readonly");
		} else {
			$("#" + self.config.id).removeAttr("readonly");
		}
		return;
	}
	if (key == "fieldCls") {
		$("#" + self.config.id).addClass(value);
		self.config[key] = value;
		return;
	}

	self.config[key] = value;
}

function getQueryParameterAttrFieldCls(name) {
	var formTemplateIterator = new FormTemplateIterator();
	var result = "";
	var fieldCls = "";
	formTemplateIterator.iterateAnyTemplateQueryParameter(result, function(queryParameter, result){
		if (queryParameter.name == name) {
			var queryParameterManager = new QueryParameterManager();
			var fieldClsAttr = queryParameterManager.findQueryParameterAttr(queryParameter, "fieldCls");
			if (fieldClsAttr) {
				fieldCls = fieldClsAttr.value;
			}
			return true;
		}
		return false;
	});
	return fieldCls;
}

function getQueryParameterAttrRequired(name) {
	var formTemplateIterator = new FormTemplateIterator();
	var result = "";
	var required = "";
	formTemplateIterator.iterateAnyTemplateQueryParameter(result, function(queryParameter, result){
		if (queryParameter.name == name) {
			var queryParameterManager = new QueryParameterManager();
			var attr = queryParameterManager.findQueryParameterAttr(queryParameter, "required");
			if (attr) {
				required = attr.value;
			}
			return true;
		}
		return false;
	});
	return required == "true";
}

function LTextField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		self.config[key] = param[key];
	}

	var easyUiConfig = {
		required: getQueryParameterAttrRequired(param["name"]),
		validType : "validateTextField['{name}']".replace(/{name}/g, param.name)
	};
	var fieldCls = getQueryParameterAttrFieldCls(param["name"]);
	if (fieldCls) {
	    easyUiConfig["cls"] = fieldCls;
	}
	$("#" + self.config.id).textbox(easyUiConfig);

	for ( var key in param) {
		self.set(key, param[key]);
	}

	var lFormManager = new LFormManager();
	lFormManager.initializeAttr(self);
	lFormManager.applyEventBehavior(self);
}

LTextField.prototype.get = function(key) {
	var self = this;
	
	if (key == "readonly") {
	    return $("#" + self.config.id).textbox("options").readonly;
	}
	if (key == "required") {
	    return $("#" + self.config.id).textbox("options").required;
	}
	if (key == "error") {
	    return $("#" + self.config.id).textbox("options").invalidMessage;
	}
	
	return formFieldCommonGet(self, key);
}

LTextField.prototype.set = function(key, value) {
	var self = this;
	
	if (key == "readonly") {
	    $("#" + self.config.id).textbox("readonly", value);
		return;
	}
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
		$("#" + self.config.id).textbox("setValue", value);
		return;
	}
	
	formFieldCommonSet(self, key, value);
}

function LHiddenField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		self.config[key] = param[key];
	}
	
	$("#" + self.config.id).validatebox({
		required: getQueryParameterAttrRequired(param["name"]),
		validType : "validateHiddenField['{name}']".replace(/{name}/g, param.name)
	});

	for ( var key in param) {
		self.set(key, param[key]);
	}

	var lFormManager = new LFormManager();
	lFormManager.initializeAttr(self);
	lFormManager.applyEventBehavior(self);
}

LHiddenField.prototype.get = function(key) {
	var self = this;
	
	return formFieldCommonGet(self, key);
}

LHiddenField.prototype.set = function(key, value) {
	var self = this;
	
	if (key == "change") {
		$("#" + self.config.id).change(value);
		return;
	}

	formFieldCommonSet(self, key, value);
}

function LSelectField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		self.config[key] = param[key];
	}
	
	var easyUiConfig = {
		valueField : 'value',
		textField : 'label',
		limitToList: true,
		required: getQueryParameterAttrRequired(param["name"]),
		validType : "validateSelectField['{name}']".replace(/{name}/g, param.name),
		multiple : param["multiple"] || false
	};
	var fieldCls = getQueryParameterAttrFieldCls(param["name"]);
	if (fieldCls) {
	    easyUiConfig["cls"] = fieldCls;
	}

	$("#" + self.config.id).combobox(easyUiConfig);
	
	for ( var key in param) {
		self.set(key, param[key]);
	}

	var lFormManager = new LFormManager();
	lFormManager.initializeAttr(self);

	var choiceFieldManager = new ChoiceFieldManager();
	self.set("choices", choiceFieldManager.getChoices(self.get("name")));
	
	lFormManager.applyEventBehavior(self);
}

LSelectField.prototype.get = function(key) {
	var self = this;
	
	if (key == "readonly") {
	    return $("#" + self.config.id).combobox("options").readonly;
	}
	if (key == "required") {
	    return $("#" + self.config.id).combobox("options").required;
	}
	if (key == "error") {
	    return $("#" + self.config.id).combobox("options").invalidMessage;
	}
	if (key == "choices") {
		return $("#" + self.get("id")).combobox("getData");
	}
	
	return formFieldCommonGet(self, key);
}

LSelectField.prototype.set = function(key, value) {
	var self = this;
	
	if (key == "readonly") {
	    $("#" + self.config.id).combobox("readonly", value);
		return;
	}
	if (key == "required") {
	    $("#" + self.config.id).combobox("options").required = value;
		return;
	}
	if (key == "error") {
	    $("#" + self.config.id).combobox("options").invalidMessage = value;
	    return;
	}
	if (key == "change") {
		$("#" + self.config.id).combobox({"onChange": value});
		return;
	}
	if (key == "choices") {
		$("#" + self.get("id")).combobox("loadData", value);
		return;
	}
	if (key == "value") {
		$("#" + self.config.id).combobox("setValue", value);
		return;
	}

	formFieldCommonSet(self, key, value);
}

function LChoiceField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		self.config[key] = param[key];
	}
	
	var easyUiConfig = {
		valueField : 'value',
		textField : 'label',
		limitToList: true,
		required: getQueryParameterAttrRequired(param["name"]),
		validType : "validateChoiceField['{name}']".replace(/{name}/g, param.name),
		multiple : param["multiple"] || false
	};
	var fieldCls = getQueryParameterAttrFieldCls(param["name"]);
	if (fieldCls) {
	    easyUiConfig["cls"] = fieldCls;
	}

	$("#" + self.config.id).combobox(easyUiConfig);

	for ( var key in param) {
		self.set(key, param[key]);
	}

	var lFormManager = new LFormManager();
	lFormManager.initializeAttr(self);
	
	var choiceFieldManager = new ChoiceFieldManager();
	self.set("choices", choiceFieldManager.getChoices(self.get("name")));
	
	lFormManager.applyEventBehavior(self);
}

LChoiceField.prototype.get = function(key) {
	var self = this;

	if (key == "readonly") {
	    return $("#" + self.config.id).combobox("options").readonly;
	}
	if (key == "required") {
	    return $("#" + self.config.id).combobox("options").required;
	}
	if (key == "error") {
	    return $("#" + self.config.id).combobox("options").invalidMessage;
	}
	if (key == "multiple") {
		return $("#" + self.get("id")).combobox("options").multiple;
	}
	if (key == "choices") {
		return $("#" + self.get("id")).combobox("getData");
	}

	return formFieldCommonGet(self, key);
}

LChoiceField.prototype.set = function(key, value) {
	var self = this;

	if (key == "readonly") {
	    $("#" + self.config.id).combobox("readonly", value);
		return;
	}
	if (key == "required") {
	    $("#" + self.config.id).combobox("options").required = value;
		return;
	}
	if (key == "error") {
	    $("#" + self.config.id).combobox("options").invalidMessage = value;
	    return;
	}
	if (key == "change") {
		$("#" + self.config.id).combobox({"onChange": value});
		return;
	}
	if (key == "multiple") {
		$("#" + self.get("id")).combobox("options").multiple = value;
		return;
	}
	if (key == "choices") {
		$("#" + self.get("id")).combobox("loadData", value);
		return;
	}
	if (key == "value") {
		$("#" + self.config.id).combobox("setValue", value);
		return;
	}

	formFieldCommonSet(self, key, value);
}

function LNumberField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		self.config[key] = param[key];
	}
	
	var easyUiConfig = {
		required: getQueryParameterAttrRequired(param["name"]),
		validType : "validateNumberField['{name}']".replace(/{name}/g, param.name)
	};
	var fieldCls = getQueryParameterAttrFieldCls(param["name"]);
	if (fieldCls) {
	    easyUiConfig["cls"] = fieldCls;
	}

	$("#" + self.config.id).numberbox(easyUiConfig);
	for ( var key in param) {
		self.set(key, param[key]);
	}

	var lFormManager = new LFormManager();
	lFormManager.initializeAttr(self);
	lFormManager.applyEventBehavior(self);
}

LNumberField.prototype.get = function(key) {
	var self = this;
	
	if (key == "readonly") {
	    return $("#" + self.config.id).numberbox("options").readonly;
	}
	if (key == "required") {
	    return $("#" + self.config.id).numberbox("options").required;
	}
	if (key == "error") {
	    return $("#" + self.config.id).numberbox("options").invalidMessage;
	}
	if (key == "prefix") {
		return $("#" + self.config.id).numberbox("options").prefix;
	}
	if (key == "precision") {
		return $("#" + self.config.id).numberbox("options").precision;
	}
	if (key == "decimalSeparator") {
		return $("#" + self.config.id).numberbox("options").decimalSeparator;
	}
	if (key == "groupSeparator") {
		return $("#" + self.config.id).numberbox("options").groupSeparator;
	}
	if (key == "suffix") {
		return $("#" + self.config.id).numberbox("options").suffix;
	}

	return formFieldCommonGet(self, key);
}

LNumberField.prototype.set = function(key, value) {
	var self = this;

	if (key == "readonly") {
	    $("#" + self.config.id).numberbox("readonly", value);
		return;
	}
	if (key == "required") {
	    $("#" + self.config.id).numberbox("options").required = value;
		return;
	}
	if (key == "error") {
	    $("#" + self.config.id).numberbox("options").invalidMessage = value;
	    return;
	}
	if (key == "change") {
		$("#" + self.config.id).numberbox({"onChange": value});
		return;
	}
	if (key == "prefix") {
		$("#" + self.config.id).numberbox("options").prefix = value;
		return;
	}
	if (key == "precision") {
		$("#" + self.config.id).numberbox("options").precision = value;
		return;
	}
	if (key == "decimalSeparator") {
		$("#" + self.config.id).numberbox("options").decimalSeparator = value;
		return;
	}
	if (key == "groupSeparator") {
		$("#" + self.config.id).numberbox("options").groupSeparator = value;
		return;
	}
	if (key == "suffix") {
		$("#" + self.config.id).numberbox("options").suffix = value;
		return;
	}
	if (key == "value") {
		$("#" + self.config.id).numberbox("setValue", value);
		return;
	}

	formFieldCommonSet(self, key, value);
}

function addPrefixZero(num) {
	if (num < 10) {
		return "0" + num;
	}
	return num;
}

function LDateField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		self.config[key] = param[key];
	}

	var dbPattern = "";
	var displayPattern = "";
	var formTemplateIterator = new FormTemplateIterator();
	var result = "";
	formTemplateIterator.iterateAnyTemplateQueryParameter(result, function(queryParameter, result){
		if (queryParameter.name == self.get("name")) {
			for (var i = 0; i < queryParameter.parameterAttribute.length; i++) {
				if (queryParameter.parameterAttribute[i].name == "dbPattern") {
					dbPattern = queryParameter.parameterAttribute[i].value;
				} else if (queryParameter.parameterAttribute[i].name == "displayPattern") {
					displayPattern = queryParameter.parameterAttribute[i].value;
				}
			}
			return true;
		}
		return false;
	});
	this.set("dbPattern", dbPattern);
	this.set("displayPattern", displayPattern);
	
	var fieldCls = getQueryParameterAttrFieldCls(param["name"]);

	if (dbPattern == "yyyyMMdd") {
		$("#" + self.config.id).datebox({
			cls: fieldCls,
			required: getQueryParameterAttrRequired(param["name"]),
			validType : "validateDateField['{name}']".replace(/{name}/g, param.name),
			formatter: function(date) {
				var y = date.getFullYear();
				var m = date.getMonth()+1;
				if (m < 10) {
					m = "0" + m;
				}
				var d = date.getDate();
				if (d < 10) {
					d = "0" + d;
				}
				if (displayPattern.indexOf("-") > -1) {
					return y + "-" + m + "-" + d;
				}
				return y + "/" + m + "/" + d;
			}
		});
	} else if (dbPattern == "yyyyMMddHHmmss") {
		$("#" + self.config.id).datetimebox({
			cls: fieldCls,
		    showSeconds: false,
		    required: getQueryParameterAttrRequired(param["name"]),
		    validType : "validateDateField['{name}']".replace(/{name}/g, param.name),
		    formatter: function(date) {
				var y = date.getFullYear();
				var m = date.getMonth()+1;
				m = addPrefixZero(m);
				var d = date.getDate();
				d = addPrefixZero(d);
				var yyyyMMdd = "";
				if (displayPattern.indexOf("-") > -1) {
					yyyyMMdd = y + "-" + m + "-" + d;
				} else {
					yyyyMMdd = y + "/" + m + "/" + d;
				}
				var hour = date.getHours();
				hour = addPrefixZero(hour);
				var minute = date.getMinutes();
				minute = addPrefixZero(minute);
				
				return yyyyMMdd + " " + hour + ":" + minute;
			}
		});
	} else if (dbPattern == "HHmmss") {
		$("#" + self.config.id).timespinner({
			cls: fieldCls,
			required: getQueryParameterAttrRequired(param["name"]),
			validType : "validateDateField['{name}']".replace(/{name}/g, param.name),
		    showSeconds: false
		});
	}
	
	for ( var key in param) {
		self.set(key, param[key]);
	}

	var lFormManager = new LFormManager();
	lFormManager.initializeAttr(self);
	lFormManager.applyEventBehavior(self);
}

LDateField.prototype.get = function(key) {
	var self = this;
	
	if (key == "readonly") {
		var dbPattern = self.get("dbPattern");
		if (dbPattern == "yyyyMMdd") {
			return $("#" + self.config.id).datebox("options").readonly;
		} else if (dbPattern == "yyyyMMddHHmmss") {
			return $("#" + self.config.id).datetimebox("options").readonly;
		} else if (dbPattern == "HHmmss") {
			return $("#" + self.config.id).timespinner("options").readonly;
		}
	}
	if (key == "required") {
		var dbPattern = self.get("dbPattern");
		if (dbPattern == "yyyyMMdd") {
			return $("#" + self.config.id).datebox("options").required;
		} else if (dbPattern == "yyyyMMddHHmmss") {
			return $("#" + self.config.id).datetimebox("options").required;
		} else if (dbPattern == "HHmmss") {
			return $("#" + self.config.id).timespinner("options").required;
		}
	}
	if (key == "error") {
		var dbPattern = self.get("dbPattern");
		if (dbPattern == "yyyyMMdd") {
			return $("#" + self.config.id).datebox("options").invalidMessage;
		} else if (dbPattern == "yyyyMMddHHmmss") {
			return $("#" + self.config.id).datetimebox("options").invalidMessage;
		} else if (dbPattern == "HHmmss") {
			return $("#" + self.config.id).timespinner("options").invalidMessage;
		}
	}
	if (key == "value") {
		var value = $("#" + self.config.id).val();
		var dbPattern = self.get("dbPattern");
		if (dbPattern == "yyyyMMdd") {
			
		} else if (dbPattern == "yyyyMMddHHmmss") {
			if (value.length == 16) {
				value = value + ":00";
			}
		} else if (dbPattern == "HHmmss") {
			if (value.length == 5) {
				value = value + ":00";
			}
		}
		return value;
	}
	
	return formFieldCommonGet(self, key);
}

LDateField.prototype.set = function(key, value) {
	var self = this;
	
	if (key == "readonly") {
		var dbPattern = self.get("dbPattern");
		if (dbPattern == "yyyyMMdd") {
			$("#" + self.config.id).datebox("readonly", value);
			return;
		} else if (dbPattern == "yyyyMMddHHmmss") {
			$("#" + self.config.id).datetimebox("readonly", value);
			return;
		} else if (dbPattern == "HHmmss") {
			$("#" + self.config.id).timespinner("readonly", value);
			return;
		}
	}
	if (key == "required") {
		var dbPattern = self.get("dbPattern");
		if (dbPattern == "yyyyMMdd") {
			$("#" + self.config.id).datebox("options").required = value;
			return;
		} else if (dbPattern == "yyyyMMddHHmmss") {
			$("#" + self.config.id).datetimebox("options").required = value;
			return;
		} else if (dbPattern == "HHmmss") {
			$("#" + self.config.id).timespinner("options").required = value;
			return;
		}
	}
	if (key == "error") {
		var dbPattern = self.get("dbPattern");
		if (dbPattern == "yyyyMMdd") {
			$("#" + self.config.id).datebox("options").invalidMessage = value;
			return;
		} else if (dbPattern == "yyyyMMddHHmmss") {
			$("#" + self.config.id).datetimebox("options").invalidMessage = value;
			return;
		} else if (dbPattern == "HHmmss") {
			$("#" + self.config.id).timespinner("options").invalidMessage = value;
			return;
		}
	}
	if (key == "change") {
		var dbPattern = self.get("dbPattern");
		if (dbPattern == "yyyyMMdd") {
			$("#" + self.config.id).datebox({"onChange": value});
			return;
		} else if (dbPattern == "yyyyMMddHHmmss") {
			$("#" + self.config.id).datetimebox({"onChange": value});
			return;
		} else if (dbPattern == "HHmmss") {
			$("#" + self.config.id).timespinner({"onChange": value});
			return;
		}
	}
	if (key == "value") {
		var dbPattern = self.get("dbPattern");
		if (dbPattern == "yyyyMMdd") {
			$("#" + self.config.id).datebox("setValue", self.getFormatValue(value));
			return;
		} else if (dbPattern == "yyyyMMddHHmmss") {
			$("#" + self.config.id).datetimebox("setValue", self.getFormatValue(value));
			return;
		} else if (dbPattern == "HHmmss") {
			$("#" + self.config.id).timespinner("setValue", self.getFormatValue(value));
			return;
		}
	}

	formFieldCommonSet(self, key, value);
}

LDateField.prototype.getFormatValue = function(value) {
	var self = this;
	if (!value) {
		return "";
	}
	value = value.replace(/[ :\/-]/g, "");
	var displayPattern = self.get("displayPattern");
	var dbPattern = self.get("dbPattern");
	if (dbPattern == "yyyyMMdd") {
		if (displayPattern.indexOf("-") > -1) {
			if (value.length >= 6) {
				return value.substring(0, 4) + "-" + value.substring(4, 6) + "-" + value.substring(6, 8);
			}
		} else {
			if (value.length >= 6) {
				return value.substring(0, 4) + "/" + value.substring(4, 6) + "/" + value.substring(6, 8);
			}
		}
	} else if (dbPattern == "yyyyMMddHHmmss") {
		if (displayPattern.indexOf("-") > -1) {
			if (value.length >= 14) {
				return value.substring(0, 4) + "-" + value.substring(4, 6) + "-" + value.substring(6, 8) + " " + value.substring(8, 10) + ":" + value.substring(10, 12) + ":" + value.substring(12, 14);
			} else if (value.length >= 12) {
				return value.substring(0, 4) + "-" + value.substring(4, 6) + "-" + value.substring(6, 8) + " " + value.substring(8, 10) + ":" + value.substring(10, 12) + ":00";
			}
		} else {
			if (value.length >= 14) {
				return value.substring(0, 4) + "/" + value.substring(4, 6) + "/" + value.substring(6, 8) + " " + value.substring(8, 10) + ":" + value.substring(10, 12) + ":" + value.substring(12, 14);
			} else if (value.length >= 12) {
				return value.substring(0, 4) + "/" + value.substring(4, 6) + "/" + value.substring(6, 8) + " " + value.substring(8, 10) + ":" + value.substring(10, 12) + ":00";
			}
		}
	} else if (dbPattern == "HHmmss") {
		if (value.length >= 6) {
			return value.substring(0, 2) + ":" + value.substring(2, 4) + ":" + value.substring(4, 6);
		} else if (value.length >= 4) {
			return value.substring(0, 2) + ":" + value.substring(2, 4) + ":00";
		}
	}
	return "";
}

function LTextareaField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		self.config[key] = param[key];
	}
	
	$("#" + self.config.id).validatebox({
		required: getQueryParameterAttrRequired(param["name"]),
		validType : "validateTextareaField['{name}']".replace(/{name}/g, param.name)
	});
	for ( var key in param) {
		self.set(key, param[key]);
	}
	
	var lFormManager = new LFormManager();
	lFormManager.initializeAttr(self);
	lFormManager.applyEventBehavior(self);
}

LTextareaField.prototype.get = function(key) {
	var self = this;
	
	if (key == "readonly") {
	    return $("#" + self.config.id).validatebox("options").readonly;
	}
	if (key == "required") {
	    return $("#" + self.config.id).validatebox("options").required;
	}
	if (key == "error") {
	    return $("#" + self.config.id).validatebox("options").invalidMessage;
	}
	return formFieldCommonGet(self, key);
}

LTextareaField.prototype.set = function(key, value) {
	var self = this;

	if (key == "readonly") {
	    $("#" + self.config.id).validatebox("readonly", value);
		return;
	}
	if (key == "required") {
	    $("#" + self.config.id).validatebox("options").required = value;
		return;
	}
	if (key == "error") {
	    $("#" + self.config.id).validatebox("options").invalidMessage = value;
	    return;
	}
	if (key == "change") {
		$("#" + self.config.id).change(value);
		return;
	}
	
	formFieldCommonSet(self, key, value);
}

function LDisplayField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		self.config[key] = param[key];
	}
	$("#" + self.config.id).validatebox({
		required: getQueryParameterAttrRequired(param["name"]),
		validType : "validateDisplayField['{name}']".replace(/{name}/g, param.name)
	});
	for ( var key in param) {
		self.set(key, param[key]);
	}

	var lFormManager = new LFormManager();
	lFormManager.initializeAttr(self);
	lFormManager.applyEventBehavior(self);
}

LDisplayField.prototype.get = function(key) {
	var self = this;
	
	if (key == "value") {
		return $("#" + self.config.id).html();
	}
	return formFieldCommonGet(self, key);
}

LDisplayField.prototype.set = function(key, value) {
	var self = this;

	if (key == "value") {
		if (self.get("zeroShowEmpty")) {
			if (value === 0 || value === "0") {
				value = "";
			}
		}

		$("#" + self.config.id).html(value);
		return;
	}
	formFieldCommonSet(self, key, value);
}
