function FormFieldFactory() {
}

FormFieldFactory.prototype.getFormField = function(Y, name, dataSetId) {
	var self = this;
	var field = null;
	// 从column-attribute里面读取
	for (var i = 0; i < g_formTemplateJsonData.FormElemLi.length; i++) {
		var formElem = g_formTemplateJsonData.FormElemLi[i];
		if (formElem.xmlName == "column-model") {
			if (formElem.columnModel.dataSetId == dataSetId) {
				var flag = false;
				if (formElem.columnModel.idColumn.name == name) {
					field = new Y.PHiddenField({
						name : name,
						dataSetId: dataSetId,
						validateInline: true
					});
				} else if (formElem.columnModel.columnList) {
					for (var j = 0; j < formElem.columnModel.columnList.length; j++) {
						var column = formElem.columnModel.columnList[j];
						if (column.name == name) {
							if (column.hideable) {
								field = new Y.PHiddenField({
									name : name,
									dataSetId: dataSetId,
									validateInline: true
								});
							} else {
								if (column.ColumnAttributeLi) {
									for (var k = 0; k < column.ColumnAttributeLi.length; k++) {
										var attribute = column.ColumnAttributeLi[k];
										if (attribute.name == "editor") {
											field = self._getFieldByAttributeValue(Y, attribute.Value, name, dataSetId);
											break;
										}
									}
								}
								
								if (field == null) {
									field = self._getFieldByColumnName(Y, column.xmlName, name, dataSetId);
								}
							}
							
							flag = true;
							break;
						}
					}
				}
				if (flag) {
					break;
				}
			}
		}
	}
	
	if (field == null) {
		field = new Y.PTextField({
			name : name, 
			dataSetId: dataSetId, 
			validateInline: true
		});
	}
	return field;
}

FormFieldFactory.prototype._getFieldByAttributeValue = function(Y, value, name, dataSetId) {
	if (value == "textfield") {
		return new Y.PTextField({
			name : name,
			dataSetId: dataSetId,
			validateInline: true
		});
	} else if (value == "hiddenfield") {
		return new Y.PHiddenField({
			name : name,
			dataSetId: dataSetId,
			validateInline: true
		});
	} else if (value == "combofield") {
		return new Y.PSelectField({
			name : name,
			dataSetId: dataSetId,
			validateInline: true
		});
	} else if (value == "checkboxfield") {
		return new Y.PChoiceField({
			name : name,
			dataSetId: dataSetId,
			validateInline: true,
			multi: true
		});
	} else if (value == "radiofield") {
		return new Y.PChoiceField({
			name : name,
			dataSetId: dataSetId,
			validateInline: true
		});
	} else if (value == "numberfield") {
		return new Y.PNumberField({
			name : name,
			dataSetId: dataSetId,
			validateInline: true
		});
	} else if (value == "datefield") {
		return new Y.PDateField({
			name : name,
			dataSetId: dataSetId,
			validateInline: true
		});
	} else if (value == "textareafield") {
		return new Y.PTextareaField({
			name : name,
			dataSetId: dataSetId,
			validateInline: true
		});
	} else if (value == "triggerfield") {
		return new Y.PTriggerField({
			name : name,
			dataSetId: dataSetId,
			validateInline: true
//			,multi: true
		});
	} else if (value == "displayfield") {
		return new Y.PDisplayField({
			name : name,
			dataSetId: dataSetId,
			validateInline: true
		});
	}
	return null;
}

FormFieldFactory.prototype._getFieldByColumnName = function(Y, columnName, name, dataSetId) {
	if (columnName == "trigger-column") {
		return new Y.PTriggerField({
			name : name, 
			dataSetId: dataSetId, 
			validateInline: true
		});
	} else if (columnName == "string-column") {
		return new Y.PTextField({
			name : name, 
			dataSetId: dataSetId, 
			validateInline: true
		});
	} else if (columnName == "number-column") {
		return new Y.PNumberField({
			name : name, 
			dataSetId: dataSetId, 
			validateInline: true
		});
	} else if (columnName == "date-column") {
		return new Y.PDateField({
			name : name, 
			dataSetId: dataSetId, 
			validateInline: true
		});
	} else if (columnName == "dictionary-column") {
		return new Y.PSelectField({
			name : name, 
			dataSetId: dataSetId, 
			validateInline: true
		});
	}
	return null;
}
