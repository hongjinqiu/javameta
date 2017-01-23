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
validType:['email','length[0,20]']
	
	t.validatebox("validate")
	t.validatebox("enableValidation")
	t.validatebox("disableValidation")
	t.validatebox("isValid")
 */

function commonValidate(fieldElem, value, param) {
	var formManager = new FormManager();
	var dataSetId = param[0];
	if (dataSetId == "A") {
		var name = param[1];
		var formObj = g_masterFormFieldDict[name];
		return formManager.dsFormFieldValidator(value, formObj, param);
	} else {
		if (formManager.isMatchDetailEditor(dataSetId)) {
			var name = param[1];
			var formObj = g_popupFormField[name];
			return formManager.dsFormFieldValidator(value, formObj, param);
		}
	}
	return true;
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
		,message: '{2}'
	},
	validateHiddenField : {
		validator : validateHiddenField
		,message: '{2}'
	},
	validateSelectField : {
		validator : validateSelectField
		,message: '{2}'
	},
	validateChoiceField : {
		validator : validateChoiceField
		,message: '{2}'
	},
	validateNumberField : {
		validator : validateNumberField
		,message: '{2}'
	},
	validateDateField : {
		validator : validateDateField
		,message: '{2}'
	},
	validateTextareaField : {
		validator : validateTextareaField
		,message: '{2}'
	},
	validateTriggerField : {
		validator : validateTriggerField
		,message: '{2}'
	},
	validateDisplayField : {
		validator : validateDisplayField
		,message: '{2}'
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

function getEditorAttrFieldCls(dataSetId, name) {
	var formTemplateIterator = new FormTemplateIterator();
	var result = "";
	var fieldCls = "";
	formTemplateIterator.iterateAnyTemplateColumn(dataSetId, result, function(column, result){
		if (column.name == name) {
			if (column.editor && column.editor.editorAttribute) {
				for (var i = 0; i < column.editor.editorAttribute.length; i++) {
					if (column.editor.editorAttribute[i].name == "fieldCls") {
						fieldCls = column.editor.editorAttribute[i].value;
					}
				}
			}
			return true;
		}
		return false;
	});
	return fieldCls;
}

function getFieldAttrAllowEmpty(dataSetId, name) {
	var datasourceIterator = new DatasourceIterator();
	var result = "";
	var allowEmpty = true;
	datasourceIterator.iterateAllField(g_datasourceJson, result, function(fieldGroup, result){
		if (fieldGroup.getDataSetId() == dataSetId && fieldGroup.id == name) {
			if (fieldGroup.allowEmpty != true) {
				allowEmpty = false;
			}
		}
	});
	return allowEmpty;
}

function PTextField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		self.config[key] = param[key];
	}
	
	var easyUiConfig = {
		required: !getFieldAttrAllowEmpty(param["dataSetId"], param["name"]),
		validType : "validateTextField['{dataSetId}', '{name}']".replace(/{dataSetId}/g, param.dataSetId).replace(/{name}/g, param.name)
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
	formManager.applyEventBehavior(self);
}

PTextField.prototype.get = function(key) {
	var self = this;
	
	if (key == "isValid") {
	    return $("#" + self.config.id).textbox("isValid");
	}
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

PTextField.prototype.set = function(key, value) {
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

function PHiddenField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		self.config[key] = param[key];
	}
	
	$("#" + self.config.id).validatebox({
		required: !getFieldAttrAllowEmpty(param["dataSetId"], param["name"]),
		validType : "validateHiddenField['{dataSetId}', '{name}']".replace(/{dataSetId}/g, param.dataSetId).replace(/{name}/g, param.name)
	});

	for ( var key in param) {
		self.set(key, param[key]);
	}

	var formManager = new FormManager();
	formManager.initializeAttr(self);
	formManager.applyEventBehavior(self);
}

PHiddenField.prototype.get = function(key) {
	var self = this;
	
	if (key == "isValid") {
		return $("#" + self.config.id).validatebox("isValid");
	}
	return formFieldCommonGet(self, key);
}

PHiddenField.prototype.set = function(key, value) {
	var self = this;

	if (key == "change") {
		$("#" + self.config.id).change(value);
		return;
	}
	formFieldCommonSet(self, key, value);
}

function PSelectField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		self.config[key] = param[key];
	}
	
	var easyUiConfig = {
		valueField : 'value',
		textField : 'label',
		validType : "validateSelectField['{dataSetId}', '{name}']".replace(/{dataSetId}/g, param.dataSetId).replace(/{name}/g, param.name),
		required: !getFieldAttrAllowEmpty(param["dataSetId"], param["name"]),
		limitToList: true,
		multiple : param["multiple"] || false
	};
	var fieldCls = getEditorAttrFieldCls(param["dataSetId"], param["name"]);
	if (fieldCls) {
	    easyUiConfig["cls"] = fieldCls;
	}

	$("#" + self.config.id).combobox(easyUiConfig);
	
	for ( var key in param) {
		self.set(key, param[key]);
	}

	var formManager = new FormManager();
	formManager.initializeAttr(self);
	formManager.setChoices(self);
	formManager.applyEventBehavior(self);
}

PSelectField.prototype.get = function(key) {
	var self = this;
	
	if (key == "isValid") {
	    return $("#" + self.config.id).combobox("isValid");
	}
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

PSelectField.prototype.set = function(key, value) {
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

function PChoiceField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		self.config[key] = param[key];
	}
	
	var easyUiConfig = {
		valueField : 'value',
		textField : 'label',
		validType : "validateChoiceField['{dataSetId}', '{name}']".replace(/{dataSetId}/g, param.dataSetId).replace(/{name}/g, param.name),
		required: !getFieldAttrAllowEmpty(param["dataSetId"], param["name"]),
		limitToList: true,
		multiple : param["multiple"] || false
	};
	var fieldCls = getEditorAttrFieldCls(param["dataSetId"], param["name"]);
	if (fieldCls) {
	    easyUiConfig["cls"] = fieldCls;
	}

	$("#" + self.config.id).combobox(easyUiConfig);

	for ( var key in param) {
		self.set(key, param[key]);
	}

	var formManager = new FormManager();
	formManager.initializeAttr(self);
	formManager.setChoices(self);
	formManager.applyEventBehavior(self);
}

PChoiceField.prototype.get = function(key) {
	var self = this;

	if (key == "isValid") {
	    return $("#" + self.config.id).combobox("isValid");
	}
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

PChoiceField.prototype.set = function(key, value) {
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

function PNumberField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		self.config[key] = param[key];
	}
	
	var easyUiConfig = {
		required: !getFieldAttrAllowEmpty(param["dataSetId"], param["name"]),
		validType : "validateNumberField['{dataSetId}', '{name}']".replace(/{dataSetId}/g, param.dataSetId).replace(/{name}/g, param.name)
	};
	var fieldCls = getEditorAttrFieldCls(param["dataSetId"], param["name"]);
	if (fieldCls) {
	    easyUiConfig["cls"] = fieldCls;
	}

	$("#" + self.config.id).numberbox(easyUiConfig);
	for ( var key in param) {
		self.set(key, param[key]);
	}

	var formManager = new FormManager();
	formManager.initializeAttr(self);
	formManager.applyEventBehavior(self);
}

PNumberField.prototype.get = function(key) {
	var self = this;
	
	if (key == "isValid") {
	    return $("#" + self.config.id).numberbox("isValid");
	}
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

PNumberField.prototype.set = function(key, value) {
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

function PDateField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		self.config[key] = param[key];
	}

	var dbPattern = "";
	var displayPattern = "";
	var formTemplateIterator = new FormTemplateIterator();
	var result = "";
	formTemplateIterator.iterateAnyTemplateColumn(self.get("dataSetId"), result, function(column, result) {
		if (column.name == self.get("name")) {
			dbPattern = column.dbPattern;
			displayPattern = column.displayPattern;
			return true;
		}
		return false;
	});
	this.set("dbPattern", dbPattern);
	this.set("displayPattern", displayPattern);
	
	var fieldCls = getEditorAttrFieldCls(param["dataSetId"], param["name"]);

	if (dbPattern == "yyyyMMdd") {
		$("#" + self.config.id).datebox({
			cls: fieldCls,
			required: !getFieldAttrAllowEmpty(param["dataSetId"], param["name"]),
			validType : "validateDateField['{dataSetId}', '{name}']".replace(/{dataSetId}/g, param.dataSetId).replace(/{name}/g, param.name),
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
			required: !getFieldAttrAllowEmpty(param["dataSetId"], param["name"]),
			validType : "validateDateField['{dataSetId}', '{name}']".replace(/{dataSetId}/g, param.dataSetId).replace(/{name}/g, param.name),
		    showSeconds: false,
		    formatter: function(date) {
				var y = date.getFullYear();
				var m = date.getMonth()+1;
				m = addPrefixZero(m);
				var d = date.getDate();
				d = addPrefixZero(d);
				var yyyyMMdd = "";
				if (displayPattern.indexOf("-") > -1) {
					yyyyMMdd = y + "-" + m + "-" + d;
				}
				yyyyMMdd = y + "/" + m + "/" + d;
				var hour = date.getHours();
				hour = addPrefixZero(hour);
				var minute = date.getMinutes();
				minute = addPrefixZero(minute);
				
				return yyyyMMdd + " " + hour + ":" + minute;
			}
		});
	} else if (dbPattern == "HHmmss") {
		$("#" + self.config.id).timespinner({
			required: !getFieldAttrAllowEmpty(param["dataSetId"], param["name"]),
			validType : "validateDateField['{dataSetId}', '{name}']".replace(/{dataSetId}/g, param.dataSetId).replace(/{name}/g, param.name),
			cls: fieldCls,
		    showSeconds: false
		});
	}
	
	for ( var key in param) {
		self.set(key, param[key]);
	}

	var formManager = new FormManager();
	formManager.initializeAttr(self);
	formManager.applyEventBehavior(self);
}

PDateField.prototype.get = function(key) {
	var self = this;
	
	if (key == "isValid") {
		var dbPattern = self.get("dbPattern");
		if (dbPattern == "yyyyMMdd") {
			return $("#" + self.config.id).datebox("isValid");
		} else if (dbPattern == "yyyyMMddHHmmss") {
			return $("#" + self.config.id).datetimebox("isValid");
		} else if (dbPattern == "HHmmss") {
			return $("#" + self.config.id).timespinner("isValid");
		}
	}
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

PDateField.prototype.set = function(key, value) {
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

PDateField.prototype.getFormatValue = function(value) {
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

function PTextareaField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		self.config[key] = param[key];
	}
	
	$("#" + self.config.id).validatebox({
		required: !getFieldAttrAllowEmpty(param["dataSetId"], param["name"]),
		validType : "validateTextareaField['{dataSetId}', '{name}']".replace(/{dataSetId}/g, param.dataSetId).replace(/{name}/g, param.name)
	});
	for ( var key in param) {
		self.set(key, param[key]);
	}
	
	var formManager = new FormManager();
	formManager.initializeAttr(self);
	formManager.applyEventBehavior(self);
}

PTextareaField.prototype.get = function(key) {
	var self = this;
	
	if (key == "isValid") {
	    return $("#" + self.config.id).validatebox("isValid");
	}
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

PTextareaField.prototype.set = function(key, value) {
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

function PDisplayField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		self.config[key] = param[key];
	}
	$("#" + self.config.id).validatebox({
		required: !getFieldAttrAllowEmpty(param["dataSetId"], param["name"]),
		validType : "validateDisplayField['{dataSetId}', '{name}']".replace(/{dataSetId}/g, param.dataSetId).replace(/{name}/g, param.name)
	});
	for ( var key in param) {
		self.set(key, param[key]);
	}

	var formManager = new FormManager();
	formManager.initializeAttr(self);
	formManager.applyEventBehavior(self);
}

PDisplayField.prototype.get = function(key) {
	var self = this;
	
	if (key == "isValid") {
	    return $("#" + self.config.id).validatebox("isValid");
	}
	if (key == "value") {
		return $("#" + self.config.id).html();
	}
	return formFieldCommonGet(self, key);
}

PDisplayField.prototype.set = function(key, value) {
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
