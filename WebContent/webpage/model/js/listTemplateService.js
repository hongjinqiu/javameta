function ListTemplateIterator() {}

function IterateFunc(column, result) {
}

ListTemplateIterator.prototype.recursionGetColumnItem = function(columnModel, columnLi) {
	var self = this;
	self._recursionGetColumnItem(columnModel, columnLi);
}

ListTemplateIterator.prototype._recursionGetColumnItem = function(columnModel, columnLi) {
	var self = this;
	for (var i = 0; i < columnModel.columnList.length; i++) {
		var columnItem = columnModel.columnList[i];
		if (columnItem.columnModel && columnItem.columnModel.columnList && columnItem.columnModel.columnList.length > 0) {
			self._recursionGetColumnItem(columnItem.columnModel, columnLi);
		}
		columnLi.push(columnModel.columnList[i]);
	}
}

ListTemplateIterator.prototype._iterateTemplateColumn = function(result, isContinue, iterateFunc) {
	var self = this;
	var columnLi = [];
	self._recursionGetColumnItem(listTemplate.columnModel, columnLi);
	for (var i = 0; i < columnLi.length; i++) {
		var column = columnLi[i];
		var iterateResult = iterateFunc(column);
		if (!isContinue && iterateResult) {
			return;
		}
	}
}

ListTemplateIterator.prototype.iterateAllTemplateColumn = function(result, iterateFunc) {
	var self = this;
	var isContinue = true;
	self._iterateTemplateColumn(result, isContinue, iterateFunc);
}

ListTemplateIterator.prototype.iterateAnyTemplateColumn = function(result, iterateFunc) {
	var self = this;
	var isContinue = false;
	self._iterateTemplateColumn(result, isContinue, iterateFunc);
}

function IterateFunc(queryParameter, result) {
}

ListTemplateIterator.prototype._iterateTemplateQueryParameter = function(result, isContinue, iterateFunc) {
	for (var i = 0; i < listTemplate.QueryParameterGroup.QueryParameterLi.length; i++) {
		var queryParameter = listTemplate.QueryParameterGroup.QueryParameterLi[i];
		var iterateResult = iterateFunc(queryParameter);
		if (!isContinue && iterateResult) {
			return;
		}
	}
}

ListTemplateIterator.prototype.iterateAllTemplateQueryParameter = function(result, iterateFunc) {
	var self = this;
	var isContinue = true;
	self._iterateTemplateQueryParameter(result, isContinue, iterateFunc);
}

ListTemplateIterator.prototype.iterateAnyTemplateQueryParameter = function(result, iterateFunc) {
	var self = this;
	var isContinue = false;
	self._iterateTemplateQueryParameter(result, isContinue, iterateFunc);
}
