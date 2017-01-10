function ListFormTemplateIterator() {}

function IterateFunc(column, result) {
}

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

ListFormTemplateIterator.prototype._iterateTemplateColumn = function(result, isContinue, iterateFunc) {
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

ListFormTemplateIterator.prototype.iterateAllTemplateColumn = function(result, iterateFunc) {// pass,不用管,
	var self = this;
	var isContinue = true;
	self._iterateTemplateColumn(result, isContinue, iterateFunc);
}

ListFormTemplateIterator.prototype.iterateAnyTemplateColumn = function(result, iterateFunc) {// pass,不用管,
	var self = this;
	var isContinue = false;
	self._iterateTemplateColumn(result, isContinue, iterateFunc);
}

function IterateFunc(queryParameter, result) {
}

// toolbarOrDataProviderOrColumnModel
ListFormTemplateIterator.prototype._iterateTemplateQueryParameter = function(result, isContinue, iterateFunc) {// TODO,待转移,
	for (var i = 0; i < listTemplate.queryParameters.fixedParameterOrQueryParameter.length; i++) {
		var queryParameter = listTemplate.queryParameters.fixedParameterOrQueryParameter[i];
		var iterateResult = iterateFunc(queryParameter);
		if (!isContinue && iterateResult) {
			return;
		}
	}
}

ListFormTemplateIterator.prototype.iterateAllTemplateQueryParameter = function(result, iterateFunc) {// TODO,待转移,
	var self = this;
	var isContinue = true;
	self._iterateTemplateQueryParameter(result, isContinue, iterateFunc);
}

ListFormTemplateIterator.prototype.iterateAnyTemplateQueryParameter = function(result, iterateFunc) {// TODO,待转移,
	var self = this;
	var isContinue = false;
	self._iterateTemplateQueryParameter(result, isContinue, iterateFunc);
}
