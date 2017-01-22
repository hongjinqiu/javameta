function QueryParameterManager() {}

/**
 * 设置传入的默认值
 */
QueryParameterManager.prototype.applyQueryDefaultValue = function() {
		if (g_defaultBo) {
			for (var key in g_masterFormFieldDict) {
				if (g_defaultBo[key]) {
					g_masterFormFieldDict[key].set("value", g_defaultBo[key]);
				} else {
					g_masterFormFieldDict[key].set("value", "");
				}
			}
		} else {
			for (var key in g_masterFormFieldDict) {
				g_masterFormFieldDict[key].set("value", "");
			}
		}
}

/**
 * 设置url传入的参数值
 */
QueryParameterManager.prototype.applyFormData = function() {
		if (g_formDataJson) {
			for (var key in g_formDataJson) {
				if (g_masterFormFieldDict[key]) {
					if (g_formDataJson[key]) {
						g_masterFormFieldDict[key].set("value", g_formDataJson[key]);
					} else {
						g_masterFormFieldDict[key].set("value", "");
					}
				}
			}
		}
}

QueryParameterManager.prototype.applyObserveEventBehavior = function() {
	for (var key in g_masterFormFieldDict) {
		var self = g_masterFormFieldDict[key];
		var formTemplateIterator = new FormTemplateIterator();
		var result = "";
		formTemplateIterator.iterateAnyTemplateQueryParameter(result, function(queryParameter, result){
			if (queryParameter.name == self.get("name")) {
				if (queryParameter.parameterAttribute) {
					for (var j = 0; j < queryParameter.parameterAttribute.length; j++) {
						if (queryParameter.parameterAttribute[j].name == "observe") {
							//$("#" + self.get("id")).change(function(self, queryParameter, parameterAttribute) {
							self.set("change", function(self, queryParameter, parameterAttribute) {
								return function(newValue, oldValue) {
									var formTemplateIterator = new FormTemplateIterator();
									formTemplateIterator.iterateAllTemplateQueryParameter(result, function(targetQueryParameter, result){
										var valueLi = parameterAttribute.value.split(",");
										var isIn = false;
										for (var k = 0; k < valueLi.length; k++) {
											if (targetQueryParameter.name == valueLi[k]) {
												isIn = true;
												break;
											}
										}
										if (isIn) {
											var queryParameterManager = new QueryParameterManager();
											var treeUrlAttr = queryParameterManager.findQueryParameterAttr(targetQueryParameter, "treeUrl");
											
											if (treeUrlAttr) {
												var uri = webRoot + "/tree/" + treeUrlAttr.value;
												if (uri.indexOf("?") > -1) {
													uri += "&parentId=" + g_masterFormFieldDict[queryParameter.name].get("value");
												} else {
													uri += "?parentId=" + g_masterFormFieldDict[queryParameter.name].get("value");
												}
												
												ajaxRequest({
													url: uri
													,params: {
													},
													callback: function(data) {
														var choicesLi = [];
														for (var k = 0; k < data.length; k++) {
															choicesLi.push({
																"label": data[k].name,
																"value": data[k].code
															});
														}
														g_masterFormFieldDict[targetQueryParameter.name].set("choices", choicesLi);
														g_masterFormFieldDict[targetQueryParameter.name].set("value", "");
													}
												});
											} else {
												if (g_masterFormFieldDict[targetQueryParameter.name]) {
													g_masterFormFieldDict[targetQueryParameter.name].set("value", "");
												}
											}
										}
									});
								}
							}(self, queryParameter, queryParameter.parameterAttribute[j]));
							
							break;
						}
					}
				}
				return true;
			}
			return false;
		});
	}
}

QueryParameterManager.prototype.findQueryParameterAttr = function(queryParameter, name) {
	if (queryParameter.parameterAttribute) {
		for (var i = 0; i < queryParameter.parameterAttribute.length; i++) {
			if (queryParameter.parameterAttribute[i].name == name) {
				return queryParameter.parameterAttribute[i];
			}
		}
	}
	return null;
}

QueryParameterManager.prototype.getQueryField = function(id, name) {
	var self = this;
//	var queryParameterManager = new QueryParameterManager();
//	var treeUrlAttr = queryParameterManager.findQueryParameterAttr(targetQueryParameter, "treeUrl");
	var formTemplateIterator = new FormTemplateIterator();
	var result = "";
	var field = null;
	formTemplateIterator.iterateAnyTemplateQueryParameter(result, function(queryParameter, result){
		if (queryParameter.name == name) {
			var readOnly = queryParameter.readOnly === true || queryParameter.readOnly === "true";
			if (queryParameter.editor == "hiddenfield") {
				field = new LHiddenField({
					id: id,
					name : name,
					validateInline: true,
					readonly: readOnly
				});
			} else if (queryParameter.editor == "textfield") {
				field = new LTextField({
					id: id,
					name : name,
					validateInline: true,
					readonly: readOnly
				});
			} else if (queryParameter.editor == "textareafield") {
				field = new LTextareaField({
					id: id,
					name : name,
					validateInline: true,
					readonly: readOnly
				});
			} else if (queryParameter.editor == "numberfield") {
				field = new LNumberField({
					id: id,
					name : name,
					validateInline: true,
					readonly: readOnly
				});
			} else if (queryParameter.editor == "datefield") {
				field = new LDateField({
					id: id,
					name : name,
					validateInline: true,
					readonly: readOnly
				});
			} else if (queryParameter.editor == "combofield") {
				field = new LSelectField({
					id: id,
					name : name,
					validateInline: true,
					readonly: readOnly
				});
			} else if (queryParameter.editor == "displayfield") {
				field = new LDisplayField({
					id: id,
					name : name,
					validateInline: true,
					readonly: readOnly
				});
			} else if (queryParameter.editor == "checkboxfield") {
				field = new LChoiceField({
					id: id,
					name : name,
					validateInline: true,
					multiple: true,
					readonly: readOnly
				});
			} else if (queryParameter.editor == "radiofield") {
				field = new LChoiceField({
					id: id,
					name : name,
					validateInline: true,
					readonly: readOnly
				});
			} else if (queryParameter.editor == "triggerfield") {
				field = new LTriggerField({
					id: id,
					name : name,
					validateInline: true,
					readonly: readOnly
				});
			}
			return true;
		}
		return false;
	});
	return field;
}

QueryParameterManager.prototype.getQueryFormData = function() {
	var result = {};
	for (var key in g_masterFormFieldDict) {
		result[key] = g_masterFormFieldDict[key].get("value");
	}
	return result;
}

