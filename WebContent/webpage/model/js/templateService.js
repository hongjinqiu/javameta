function FormTemplateIterator() {}

FormTemplateIterator.prototype.recursionGetColumnItem = function(columnModel, columnLi) {
	var self = this;
	self._recursionGetColumnItem(columnModel, columnLi);
}

FormTemplateIterator.prototype._recursionGetColumnItem = function(columnModel, columnLi) {
	var self = this;
	for (var i = 0; i < columnModel.columnList.length; i++) {
		var columnItem = columnModel.columnList[i];
		if (columnItem.columnModel && columnItem.columnModel.columnList && columnItem.columnModel.columnList.length > 0) {
			self._recursionGetColumnItem(columnItem.columnModel, columnLi);
		}
		columnLi.push(columnModel.columnList[i]);
	}
}

FormTemplateIterator.prototype._iterateTemplateColumn = function(dataSetId, result, isContinue, iterateFunc) {
	var self = this;
	for (var j = 0; j < g_formTemplateJsonData.toolbarOrDataProviderOrColumnModel.length; j++) {
		var formElem = g_formTemplateJsonData.toolbarOrDataProviderOrColumnModel[j];
		if (formElem.xmlName == "column-model") {
			if (formElem.dataSetId == dataSetId) {
				if (formElem.columnList) {
					var columnLi = [];
					self.recursionGetColumnItem(formElem, columnLi);
					for (var k = 0; k < columnLi.length; k++) {
						var column = columnLi[k];
						var iterateResult = iterateFunc(column, result);
						if (!isContinue && iterateResult) {
							return;
						}
					}
				}
			}
		}
	}
}

function IterateFunc(column, result) {
}

FormTemplateIterator.prototype.iterateAllTemplateColumn = function(dataSetId, result, iterateFunc) {
	var self = this;
	var isContinue = true;
	self._iterateTemplateColumn(dataSetId, result, isContinue, iterateFunc);
}

function IterateFunc(column, result) {
}

FormTemplateIterator.prototype.iterateAnyTemplateColumn = function(dataSetId, result, iterateFunc) {
	var self = this;
	var isContinue = false;
	self._iterateTemplateColumn(dataSetId, result, isContinue, iterateFunc);
}

FormTemplateIterator.prototype._iterateTemplateColumnModel = function(result, isContinue, iterateFunc) {
	for (var j = 0; j < g_formTemplateJsonData.toolbarOrDataProviderOrColumnModel.length; j++) {
		var formElem = g_formTemplateJsonData.toolbarOrDataProviderOrColumnModel[j];
		if (formElem.xmlName == "column-model") {
			var iterateResult = iterateFunc(formElem, result);
			if (!isContinue && iterateResult) {
				return;
			}
		}
	}
}

function IterateFunc(columnModel, result) {
}

FormTemplateIterator.prototype.iterateAllTemplateColumnModel = function(result, iterateFunc) {
	var self = this;
	var isContinue = true;
	self._iterateTemplateColumnModel(result, isContinue, iterateFunc);
}

function IterateFunc(columnModel, result) {
}

FormTemplateIterator.prototype.iterateAnyTemplateColumnModel = function(result, iterateFunc) {
	var self = this;
	var isContinue = false;
	self._iterateTemplateColumnModel(result, isContinue, iterateFunc);
}

/**
 * 按钮的分布,toolbar/button,columnModel/toolbar,columnModel/editor-toolbar,columnModel/virtual-column/buttons/button
 */
FormTemplateIterator.prototype._iterateTemplateButton = function(result, isContinue, iterateFunc) {
	for (var j = 0; j < g_formTemplateJsonData.toolbarOrDataProviderOrColumnModel.length; j++) {
		var formElem = g_formTemplateJsonData.toolbarOrDataProviderOrColumnModel[j];
		if (formElem.xmlName == "toolbar") {
			if (formElem && formElem.buttonGroupOrButtonOrSplitButton) {
				for (var k = 0; k < formElem.buttonGroupOrButtonOrSplitButton.length; k++) {
					var button = formElem.buttonGroupOrButtonOrSplitButton[k];
					var iterateResult = iterateFunc(formElem, button, result);
					if (!isContinue && iterateResult) {
						return;
					}
				}
			}
		} else if (formElem.xmlName == "column-model") {
			if (formElem.toolbar && formElem.toolbar.buttonGroupOrButtonOrSplitButton) {
				for (var k = 0; k < formElem.toolbar.buttonGroupOrButtonOrSplitButton.length; k++) {
					var button = formElem.toolbar.buttonGroupOrButtonOrSplitButton[k];
					var iterateResult = iterateFunc(formElem, button, result);
					if (!isContinue && iterateResult) {
						return;
					}
				}
			}
			if (formElem.editorToolbar && formElem.editorToolbar.buttonGroupOrButtonOrSplitButton) {
				for (var k = 0; k < formElem.editorToolbar.buttonGroupOrButtonOrSplitButton.length; k++) {
					var button = formElem.editorToolbar.buttonGroupOrButtonOrSplitButton[k];
					var iterateResult = iterateFunc(formElem, button, result);
					if (!isContinue && iterateResult) {
						return;
					}
				}
			}
			if (formElem.columnList) {
				for (var k = 0; k < formElem.columnList.length; k++) {
					var column = formElem.columnList[k];
					if (column.xmlName == "virtual-column") {
						if (column.buttons && column.buttons.button) {
							for (var l = 0; l < column.buttons.button.length; l++) {
								var button = column.buttons.button[l];
								var iterateResult = iterateFunc(formElem, button, result);
								if (!isContinue && iterateResult) {
									return;
								}
							}
						}
					}
				}
			}
		}
	}
}

function IterateFunc(toolbarOrColumnModel, button, result) {
}

FormTemplateIterator.prototype.iterateAllTemplateButton = function(result, iterateFunc) {
	var self = this;
	var isContinue = true;
	self._iterateTemplateButton(result, isContinue, iterateFunc);
}

function IterateFunc(toolbarOrColumnModel, button, result) {
}

FormTemplateIterator.prototype.iterateAnyTemplateButton = function(result, iterateFunc) {
	var self = this;
	var isContinue = false;
	self._iterateTemplateButton(result, isContinue, iterateFunc);
}


function IterateFunc(queryParameter, result) {
}

FormTemplateIterator.prototype._iterateTemplateQueryParameter = function(result, isContinue, iterateFunc) {
	var self = this;
	for (var j = 0; j < g_formTemplateJsonData.toolbarOrDataProviderOrColumnModel.length; j++) {
		var formElem = g_formTemplateJsonData.toolbarOrDataProviderOrColumnModel[j];
		if (formElem.xmlName == "data-provider") {
			for (var i = 0; i < formElem.queryParameters.fixedParameterOrQueryParameter.length; i++) {
				var queryParameter = formElem.queryParameters.fixedParameterOrQueryParameter[i];
				var iterateResult = iterateFunc(queryParameter);
				if (!isContinue && iterateResult) {
					break;
				}
			}
		}
	}
	
}

FormTemplateIterator.prototype.iterateAllTemplateQueryParameter = function(result, iterateFunc) {
	var self = this;
	var isContinue = true;
	self._iterateTemplateQueryParameter(result, isContinue, iterateFunc);
}

FormTemplateIterator.prototype.iterateAnyTemplateQueryParameter = function(result, iterateFunc) {
	var self = this;
	var isContinue = false;
	self._iterateTemplateQueryParameter(result, isContinue, iterateFunc);
}
