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
	var lFormManager = new LFormManager();
	var name = $(fieldElem).attr("name");
	var formObj = g_masterFormFieldDict[name];
	return lFormManager.queryParameterFieldValidator(value, formObj);
}

function validateTextField(value, param) {
	var self = this;
	if (true) {
		console.log("validate");
		$(self).validatebox("options").invalidMessage = "2测试3";
		return false;
	}
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
	if (true) {
		$(self).validatebox("options").invalidMessage = "2测试display";
		return false;
	}
	return commonValidate(self, value, param);
}

$.extend($.fn.validatebox.defaults.rules, {
	validateTextField : {
		validator : validateTextField
	},
	validateHiddenField : {
		validator : validateHiddenField
	},
	validateSelectField : {
		validator : validateSelectField
	},
	validateChoiceField : {
		validator : validateChoiceField
	},
	validateNumberField : {
		validator : validateNumberField
	},
	validateDateField : {
		validator : validateDateField
	},
	validateTextareaField : {
		validator : validateTextareaField
	},
	validateTriggerField : {
		validator : validateTriggerField
	},
	validateDisplayField : {
		validator : validateDisplayField
	}
});

function isValidatebox(id) {
	var cls = $("#" + id).attr("class");
	return cls.indexOf("validatebox") > -1;
}

function formFieldCommonGet(self, key) {
	if (key == "required") {
		if (isValidatebox(self.config.id)) {
			return $("#" + self.config.id).validatebox("options").required;
		}
	}
	if (key == "value") {
		return $("#" + self.config.id).val();
	}
	if (key == "readonly") {
		return $("#" + self.config.id).attr("readonly") == "readonly";
	}
	if (key == "error") {
		if (isValidatebox(self.config.id)) {
			return $("#" + self.config.id).validatebox("options").invalidMessage;
		}
	}
	if (key == "fieldCls") {
		return self.config[key];
	}
	return self.config[key];
}

function formFieldCommonSet(self, key, value) {
	if (key == "required") {
		if (isValidatebox(self.config.id)) {
			$("#" + self.config.id).validatebox("options").required = value;
			return;
		}
	}
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
	if (key == "error") {
		if (isValidatebox(self.config.id)) {
			$("#" + self.config.id).validatebox("options").invalidMessage = value;
			return;
		}
	}
	if (key == "fieldCls") {
		$("#" + self.config.id).addClass(value);
		self.config[key] = value;
		return;
	}

	self.config[key] = value;
}

function LTextField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		self.config[key] = param[key];
	}

	var easyUiConfig = {};
	if (param["cls"]) {
		easyUiConfig["cls"] = param["cls"];
	}
	$("#" + self.config.id).textbox(easyUiConfig);

	$("#" + self.config.id).validatebox({
		validType : "validateTextField"
	});

	for ( var key in param) {
		self.set(key, param[key]);
	}

	var lFormManager = new LFormManager();
	lFormManager.initializeAttr(self);
	lFormManager.applyEventBehavior(self);
}

LTextField.prototype.get = function(key) {
	var self = this;
	return formFieldCommonGet(self, key);
}

LTextField.prototype.set = function(key, value) {
	var self = this;

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
		validType : "validateHiddenField"
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
		multiple : param["multiple"] || false
	};
	if (param["cls"]) {
		easyUiConfig["cls"] = param["cls"];
	}

	$("#" + self.config.id).combobox(easyUiConfig);
	$("#" + self.config.id).validatebox({
		validType : "validateSelectField"
	});
	
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
	
	if (key == "choices") {
		return $("#" + self.get("id")).combobox("getData");
	}
	
	return formFieldCommonGet(self, key);
}

LSelectField.prototype.set = function(key, value) {
	var self = this;
	
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
		multiple : param["multiple"] || false
	};
	if (param["cls"]) {
		easyUiConfig["cls"] = param["cls"];
	}

	$("#" + self.config.id).combobox(easyUiConfig);
	$("#" + self.config.id).validatebox({
		validType : "validateChoiceField"
	});

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
	
	var easyUiConfig = {};
	if (param["cls"]) {
		easyUiConfig["cls"] = param["cls"];
	}

	$("#" + self.config.id).numberbox(easyUiConfig);
	$("#" + self.config.id).validatebox({
		validType : "validateNumberField"
	});
	for ( var key in param) {
		self.set(key, param[key]);
	}

	var lFormManager = new LFormManager();
	lFormManager.initializeAttr(self);
	lFormManager.applyEventBehavior(self);
}

LNumberField.prototype.get = function(key) {
	var self = this;
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

	if (dbPattern == "yyyyMMdd") {
		$("#" + self.config.id).datebox({
			cls: param["cls"] || "",
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
			cls: param["cls"] || "",
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
			cls: param["cls"] || "",
		    showSeconds: false
		});
	}
	
	$("#" + self.config.id).validatebox({
		validType : "validateDateField"
	});

	for ( var key in param) {
		self.set(key, param[key]);
	}

	var lFormManager = new LFormManager();
	lFormManager.initializeAttr(self);
	lFormManager.applyEventBehavior(self);
}

LDateField.prototype.get = function(key) {
	var self = this;
	
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
		validType : "validateTextareaField"
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
	return formFieldCommonGet(self, key);
}

LTextareaField.prototype.set = function(key, value) {
	var self = this;

	formFieldCommonSet(self, key, value);
}

function LDisplayField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		self.config[key] = param[key];
	}
	$("#" + self.config.id).validatebox({
		validType : "validateDisplayField"
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
