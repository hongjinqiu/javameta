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
	
		那easyui的validate是怎样的呢?
		t.validatebox("validate")
		t.validatebox("enableValidation")
		t.validatebox("disableValidation")
		t.validatebox("isValid")
		
		
		set("value", "");
		set("error", messageLi.join("<br />"));
		get("error")
		set("readonly", "");
		
		return new PTextField({								finish,
		return new PHiddenField({							finish,
		return new PSelectField({							finish,
		return new PChoiceField({							finish,
		return new PChoiceField({
			self.set("choices", choices);
		
		return new PNumberField({							finish,
		return new PDateField({								finish,
		return new PTextareaField({							finish,
		return new PTriggerField({							running,----------,
		return new PDisplayField({							finish,
*/

function commonValidate(value, param) {
	var formManager = new FormManager();
	var dataSetId = $(this).attr("dataSetId");
	if (dataSetId == "A") {
		var name = $(this).attr("name");
		var formObj = g_masterFormFieldDict[name];
		return formManager.dsFormFieldValidator(value, formObj); 
	} else {
		if (formManager.isMatchDetailEditor(dataSetId)) {
			var name = $(this).attr("name");
			var formObj = g_popupFormField[name];
			return formManager.dsFormFieldValidator(value, formObj);
		}
	}
	return true;
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
	validateTextField: {
		validator: validateTextField
	},
	validateHiddenField: {
		validator: validateHiddenField
	},
	validateSelectField: {
		validator: validateSelectField
	},
	validateChoiceField: {
		validator: validateChoiceField
	},
	validateNumberField: {
		validator: validateNumberField
	},
	validateDateField: {
		validator: validateDateField
	},
	validateTextareaField: {
		validator: validateTextareaField
	},
	validateTriggerField: {
		validator: validateTriggerField
	},
	validateDisplayField: {
		validator: validateDisplayField
	}
});

function formFieldCommonGet(self, key) {
	if (key == "value") {
		return $("#" + self.config.id).val();
	}
	if (key == "readonly") {
		return $("#" + self.config.id).attr("readonly") == "readonly";
	}
	if (key == "error") {
		return $("#" + self.config.id).validatebox("options").invalidMessage;
	}
	return self.config[key];
}

function formFieldCommonSet(self, key, value) {
	// TODO required的判断,先根据class,判断其有无对应的class,"validatebox-text",
	// 若有值，再进行赋值，$(this).validatebox("options").required = true;
	if (key == "value") {
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
		$("#" + self.config.id).validatebox("options").invalidMessage = value;
		return;
	}
	
	self.config[key] = value;
}

function PTextField(param) {
	var self = this;
	this.config = {};
	for (var key in param) {
		config[key] = param;
	}
	var formManager = new FormManager();
	formManager.initializeAttr(self);
	formManager.applyEventBehavior(self);
	// apply readonly,
	$("#" + config.id).validatebox({
		validType:"validateTextField"
	});
	self.error = "";
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
	for (var key in param) {
		config[key] = param;
	}
	var formManager = new FormManager();
	formManager.initializeAttr(self);
	formManager.applyEventBehavior(self);
	// apply readonly,
	$("#" + config.id).validatebox({
		validType:"validateHiddenField"
	});
	self.error = "";
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
	for (var key in param) {
		config[key] = param;
	}
	var formManager = new FormManager();
	formManager.initializeAttr(self);
	formManager.setChoices(self);
	formManager.applyEventBehavior(self);
	// apply readonly,
	$("#" + config.id).validatebox({
		validType:"validateSelectField"
	});
	self.error = "";
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
	for (var key in param) {
		config[key] = param;
	}
	var formManager = new FormManager();
	formManager.initializeAttr(self);
	formManager.setChoices(self);
	formManager.applyEventBehavior(self);
	// apply readonly,
	$("#" + config.id).validatebox({
		validType:"validateChoiceField"
	});
	self.error = "";
}

PChoiceField.prototype.get = function(key) {
	var self = this;
	
	if (key == "choices") {
		return $("#" + self.get("id")).combobox("getData");
	}
	
	return formFieldCommonGet(self, key);
}

PChoiceField.prototype.set = function(key, value) {
	var self = this;
	
	if (key == "choices") {
		$("#" + self.get("id")).combobox("loadData", value);
		return;
	}
	
	formFieldCommonSet(self, key, value);
}

function PNumberField(param) {
	var self = this;
	this.config = {};
	for (var key in param) {
		config[key] = param;
	}
	var formManager = new FormManager();
	formManager.initializeAttr(self);
	formManager.applyEventBehavior(self);
	// apply readonly,
	$("#" + config.id).validatebox({
		validType:"validateNumberField"
	});
	self.error = "";
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

function PDateField(param) {
	var self = this;
	this.config = {};
	for (var key in param) {
		config[key] = param;
	}
	var formManager = new FormManager();
	formManager.initializeAttr(self);
	formManager.applyEventBehavior(self);
	
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
		// apply date field,
	} else if (dbPattern == "yyyyMMddHHmmss") {
		// apply datetime field,
	} else if (dbPattern == "HHmmss") {
		// apply timespinner field,
	}
	
	// apply readonly,
	$("#" + config.id).validatebox({
		validType:"validateDateField"
	});
	self.error = "";
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
	for (var key in param) {
		config[key] = param;
	}
	var formManager = new FormManager();
	formManager.initializeAttr(self);
	formManager.applyEventBehavior(self);
	// apply readonly,
	$("#" + config.id).validatebox({
		validType:"validateTextareaField"
	});
	//<textarea id="conditionId" name="condition" class="easyui-validatebox" data-options="required:true" style="width: 199px; height: 60px;"></textarea>
	// 这个不用text来折腾,
	
	self.error = "";
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
	for (var key in param) {
		config[key] = param;
	}
	var formManager = new FormManager();
	formManager.initializeAttr(self);
	formManager.applyEventBehavior(self);
	// apply readonly,
	$("#" + config.id).validatebox({
		validType:"validateDisplayField"
	});
	//<textarea id="conditionId" name="condition" class="easyui-validatebox" data-options="required:true" style="width: 199px; height: 60px;"></textarea>
	// 这个不用text来折腾,
	
	self.error = "";
}

PDisplayField.prototype.get = function(key) {
	var self = this;
	return formFieldCommonGet(self, key);
}

PDisplayField.prototype.set = function(key, value) {
	var self = this;
	
	formFieldCommonSet(self, key, value);
}



