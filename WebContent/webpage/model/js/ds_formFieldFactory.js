function FormFieldFactory() {
}

FormFieldFactory.prototype.getFormField = function(id, name, dataSetId) {
	var self = this;
	var field = null;
	// 从eidtor.name读取
	for (var i = 0; i < g_formTemplateJsonData.toolbarOrDataProviderOrColumnModel.length; i++) {
		var formElem = g_formTemplateJsonData.toolbarOrDataProviderOrColumnModel[i];
		if (formElem.xmlName == "column-model") {
			if (formElem.dataSetId == dataSetId) {
				var flag = false;
				if (formElem.idColumn && formElem.idColumn.name == name) {
					field = new PHiddenField({
						id: id,
						name : name,
						dataSetId: dataSetId,
						validateInline: true
					});
				} else if (formElem.columnList) {
					for (var j = 0; j < formElem.columnList.length; j++) {
						var column = formElem.columnList[j];
						if (column.name == name) {
							if (column.hideable) {
								field = new PHiddenField({
									id: id,
									name : name,
									dataSetId: dataSetId,
									validateInline: true
								});
							} else {
								if (column.editor && column.editor.name) {
									field = self._getFieldByAttributeValue(id, column.editor.name, name, dataSetId);
								}
								
								if (field == null) {
									field = self._getFieldByColumnName(id, column.xmlName, name, dataSetId);
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
		field = new PTextField({
			id: id,
			name : name, 
			dataSetId: dataSetId, 
			validateInline: true
		});
	}
	return field;
}

FormFieldFactory.prototype._getFieldByAttributeValue = function(id, value, name, dataSetId) {
	if (value == "textfield") {
		return new PTextField({
			id: id,
			name : name,
			dataSetId: dataSetId,
			validateInline: true
		});
	} else if (value == "hiddenfield") {
		return new PHiddenField({
			id: id,
			name : name,
			dataSetId: dataSetId,
			validateInline: true
		});
	} else if (value == "combofield") {
		return new PSelectField({
			id: id,
			name : name,
			dataSetId: dataSetId,
			validateInline: true
		});
	} else if (value == "checkboxfield") {
		return new PChoiceField({
			id: id,
			name : name,
			dataSetId: dataSetId,
			validateInline: true,
			multiple: true
		});
	} else if (value == "radiofield") {
		return new PChoiceField({
			id: id,
			name : name,
			dataSetId: dataSetId,
			validateInline: true
		});
	} else if (value == "numberfield") {
		return new PNumberField({
			id: id,
			name : name,
			dataSetId: dataSetId,
			validateInline: true
		});
	} else if (value == "datefield") {
		return new PDateField({
			id: id,
			name : name,
			dataSetId: dataSetId,
			validateInline: true
		});
	} else if (value == "textareafield") {
		return new PTextareaField({
			id: id,
			name : name,
			dataSetId: dataSetId,
			validateInline: true
		});
	} else if (value == "triggerfield") {
		return new PTriggerField({
			id: id,
			name : name,
			dataSetId: dataSetId,
			validateInline: true
		});
	} else if (value == "displayfield") {
		return new PDisplayField({
			id: id,
			name : name,
			dataSetId: dataSetId,
			validateInline: true
		});
	}
	return null;
}

FormFieldFactory.prototype._getFieldByColumnName = function(id, columnName, name, dataSetId) {
	if (columnName == "trigger-column") {
		return new PTriggerField({
			id: id,
			name : name, 
			dataSetId: dataSetId, 
			validateInline: true
		});
	} else if (columnName == "string-column") {
		return new PTextField({
			id: id,
			name : name, 
			dataSetId: dataSetId, 
			validateInline: true
		});
	} else if (columnName == "number-column") {
		return new PNumberField({
			id: id,
			name : name, 
			dataSetId: dataSetId, 
			validateInline: true
		});
	} else if (columnName == "date-column") {
		return new PDateField({
			id: id,
			name : name, 
			dataSetId: dataSetId, 
			validateInline: true
		});
	} else if (columnName == "dictionary-column") {
		return new PSelectField({
			id: id,
			name : name, 
			dataSetId: dataSetId, 
			validateInline: true
		});
	}
	return null;
}
