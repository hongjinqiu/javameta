function FormTemplateIterator() {}

// TODO,待修改,
ListFormTemplateIterator.prototype.recursionGetColumnItem = function(columnModel, columnLi) {// TODO,
	var self = this;
	self._recursionGetColumnItem(columnModel, columnLi);
}

ListFormTemplateIterator.prototype._recursionGetColumnItem = function(columnModel, columnLi) {
	var self = this;
	for (var i = 0; i < columnModel.columnList.length; i++) {
		var columnItem = columnModel.columnList[i];
		if (columnItem.columnModel && columnItem.columnModel.columnList && columnItem.columnModel.columnList.length > 0) {
			self._recursionGetColumnItem(columnItem.columnModel, columnLi);
		}
		columnLi.push(columnModel.columnList[i]);
	}
}
// TODO,待修改,

FormTemplateIterator.prototype._iterateTemplateColumn = function(dataSetId, result, isContinue, iterateFunc) {
	var listFormTemplateIterator = new ListFormTemplateIterator();
	for (var j = 0; j < g_formTemplateJsonData.FormElemLi.length; j++) {
		var formElem = g_formTemplateJsonData.FormElemLi[j];
		if (formElem.xmlName == "column-model") {
			if (formElem.columnModel.dataSetId == dataSetId) {
				if (formElem.columnModel.columnList) {
					var columnLi = [];
					listFormTemplateIterator.recursionGetColumnItem(formElem.columnModel, columnLi);
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
	for (var j = 0; j < g_formTemplateJsonData.FormElemLi.length; j++) {
		var formElem = g_formTemplateJsonData.FormElemLi[j];
		if (formElem.xmlName == "column-model") {
			var iterateResult = iterateFunc(formElem.columnModel, result);
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
	for (var j = 0; j < g_formTemplateJsonData.FormElemLi.length; j++) {
		var formElem = g_formTemplateJsonData.FormElemLi[j];
		if (formElem.xmlName == "toolbar") {
			if (formElem.toolbar && formElem.toolbar.buttonGroupOrButtonOrSplitButton) {
				for (var k = 0; k < formElem.toolbar.buttonGroupOrButtonOrSplitButton.length; k++) {
					var button = formElem.toolbar.buttonGroupOrButtonOrSplitButton[k];
					var iterateResult = iterateFunc(formElem.toolbar, button, result);
					if (!isContinue && iterateResult) {
						return;
					}
				}
			}
		} else if (formElem.xmlName == "column-model") {
			if (formElem.columnModel.toolbar && formElem.columnModel.toolbar.buttonGroupOrButtonOrSplitButton) {
				for (var k = 0; k < formElem.columnModel.toolbar.buttonGroupOrButtonOrSplitButton.length; k++) {
					var button = formElem.columnModel.toolbar.buttonGroupOrButtonOrSplitButton[k];
					var iterateResult = iterateFunc(formElem.columnModel, button, result);
					if (!isContinue && iterateResult) {
						return;
					}
				}
			}
			if (formElem.columnModel.editorToolbar && formElem.columnModel.editorToolbar.buttonGroupOrButtonOrSplitButton) {
				for (var k = 0; k < formElem.columnModel.editorToolbar.buttonGroupOrButtonOrSplitButton.length; k++) {
					var button = formElem.columnModel.editorToolbar.buttonGroupOrButtonOrSplitButton[k];
					var iterateResult = iterateFunc(formElem.columnModel, button, result);
					if (!isContinue && iterateResult) {
						return;
					}
				}
			}
			if (formElem.columnModel.columnList) {
				for (var k = 0; k < formElem.columnModel.columnList.length; k++) {
					var column = formElem.columnModel.columnList[k];
					if (column.xmlName == "virtual-column") {
						if (column.buttons && column.buttons.button) {
							for (var l = 0; l < column.buttons.button.length; l++) {
								var button = column.buttons.button[l];
								var iterateResult = iterateFunc(formElem.columnModel, button, result);
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
