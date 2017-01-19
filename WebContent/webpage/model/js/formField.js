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
	var dataSetId = $(fieldElem).attr("dataSetId");
	if (dataSetId == "A") {
		var name = $(this).attr("name");
		var formObj = g_masterFormFieldDict[name];
		return formManager.dsFormFieldValidator(value, formObj);
	} else {
		if (formManager.isMatchDetailEditor(dataSetId)) {
			var name = $(fieldElem).attr("name");
			var formObj = g_popupFormField[name];
			return formManager.dsFormFieldValidator(value, formObj);
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

function PTextField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		self.config[key] = param[key];
	}

	$("#" + self.config.id).textbox({});

	$("#" + self.config.id).validatebox({
		validType : "validateTextField"
	});

	for ( var key in param) {
		self.set(key, param[key]);
	}

	var formManager = new FormManager();
	formManager.initializeAttr(self);
	formManager.applyEventBehavior(self);
}

PTextField.prototype.get = function(key) {
	var self = this;
	return formFieldCommonGet(self, key);
}

PTextField.prototype.set = function(key, value) {
	var self = this;

	formFieldCommonSet(self, key, value);
}

function PHiddenField(param) {
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

	var formManager = new FormManager();
	formManager.initializeAttr(self);
	formManager.applyEventBehavior(self);
}

PHiddenField.prototype.get = function(key) {
	var self = this;
	return formFieldCommonGet(self, key);
}

PHiddenField.prototype.set = function(key, value) {
	var self = this;

	formFieldCommonSet(self, key, value);
}

function PSelectField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		self.config[key] = param[key];
	}

	$("#" + self.config.id).combobox({
		valueField : 'value',
		textField : 'label',
		multiple : param["multiple"] || false
	});
	$("#" + self.config.id).validatebox({
		validType : "validateSelectField"
	});
	
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
	return formFieldCommonGet(self, key);
}

PSelectField.prototype.set = function(key, value) {
	var self = this;

	formFieldCommonSet(self, key, value);
}

function PChoiceField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		self.config[key] = param[key];
	}

	$("#" + self.config.id).combobox({
		valueField : 'value',
		textField : 'label',
		multiple : param["multiple"] || false
	});
	$("#" + self.config.id).validatebox({
		validType : "validateChoiceField"
	});

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

function PNumberField(param) {
	var self = this;
	this.config = {};
	for ( var key in param) {
		self.config[key] = param[key];
	}

	$("#" + self.config.id).numberbox({});
	$("#" + self.config.id).validatebox({
		validType : "validateNumberField"
	});
	for ( var key in param) {
		self.set(key, param[key]);
	}

	var formManager = new FormManager();
	formManager.initializeAttr(self);
	formManager.applyEventBehavior(self);
}

PNumberField.prototype.get = function(key) {
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

PNumberField.prototype.set = function(key, value) {
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

	if (dbPattern == "yyyyMMdd") {
		$("#" + self.config.id).datebox({
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
		    showSeconds: false
		});
	}
	
	$("#" + self.config.id).validatebox({
		validType : "validateDateField"
	});

	for ( var key in param) {
		self.set(key, param[key]);
	}

	var formManager = new FormManager();
	formManager.initializeAttr(self);
	formManager.applyEventBehavior(self);
}

PDateField.prototype.get = function(key) {
	var self = this;
	
	return formFieldCommonGet(self, key);
}

PDateField.prototype.set = function(key, value) {
	var self = this;

	formFieldCommonSet(self, key, value);
}

function PTextareaField(param) {
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
	
	var formManager = new FormManager();
	formManager.initializeAttr(self);
	formManager.applyEventBehavior(self);
}

PTextareaField.prototype.get = function(key) {
	var self = this;
	return formFieldCommonGet(self, key);
}

PTextareaField.prototype.set = function(key, value) {
	var self = this;

	formFieldCommonSet(self, key, value);
}

function PDisplayField(param) {
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

	var formManager = new FormManager();
	formManager.initializeAttr(self);
	formManager.applyEventBehavior(self);
}

PDisplayField.prototype.get = function(key) {
	var self = this;
	return formFieldCommonGet(self, key);
}

PDisplayField.prototype.set = function(key, value) {
	var self = this;

	formFieldCommonSet(self, key, value);
}
