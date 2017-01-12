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

function commonValidate(value, param) {
	var lFormManager = new LFormManager();
	var name = $(this).attr("name");
	var formObj = g_masterFormFieldDict[name];
	return lFormManager.queryParameterFieldValidator(value, formObj);
}

function validateTextField(value, param) {
	return commonValidate(value, param);
}

function validateHiddenField(value, param) {
	return commonValidate(value, param);
}

function validateSelectField(value, param) {
	return commonValidate(value, param);
}

function validateChoiceField(value, param) {
	return commonValidate(value, param);
}

function validateNumberField(value, param) {
	return commonValidate(value, param);
}

function validateDateField(value, param) {
	return commonValidate(value, param);
}

function validateTextareaField(value, param) {
	return commonValidate(value, param);
}

function validateTriggerField(value, param) {
	return commonValidate(value, param);
}

function validateDisplayField(value, param) {
	return commonValidate(value, param);
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
		config[key] = param[key];
	}

	$("#" + config.id).textbox({});

	$("#" + config.id).validatebox({
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

	formFieldCommonSet(self, key, value);
}

function LHiddenField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		config[key] = param[key];
	}
	
	$("#" + config.id).validatebox({
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
		config[key] = param[key];
	}

	$("#" + config.id).combobox({
		valueField : 'value',
		textField : 'label',
		multiple : param["multiple"] || false
	});
	$("#" + config.id).validatebox({
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
	return formFieldCommonGet(self, key);
}

LSelectField.prototype.set = function(key, value) {
	var self = this;

	formFieldCommonSet(self, key, value);
}

function LChoiceField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		config[key] = param[key];
	}

	$("#" + config.id).combobox({
		valueField : 'value',
		textField : 'label',
		multiple : param["multiple"] || false
	});
	$("#" + config.id).validatebox({
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

	formFieldCommonSet(self, key, value);
}

function LNumberField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		config[key] = param[key];
	}

	$("#" + config.id).numberbox({});
	$("#" + config.id).validatebox({
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
		config[key] = param[key];
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
		$("#" + config.id).datebox({
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
		$("#" + config.id).datetimebox({
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
		$("#" + config.id).timespinner({
		    showSeconds: false
		});
	}
	
	$("#" + config.id).validatebox({
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
	
	return formFieldCommonGet(self, key);
}

LDateField.prototype.set = function(key, value) {
	var self = this;

	formFieldCommonSet(self, key, value);
}

function LTextareaField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		config[key] = param[key];
	}
	
	$("#" + config.id).validatebox({
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
		config[key] = param[key];
	}
	$("#" + config.id).validatebox({
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
	return formFieldCommonGet(self, key);
}

LDisplayField.prototype.set = function(key, value) {
	var self = this;

	formFieldCommonSet(self, key, value);
}
