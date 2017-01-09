function DatasourceIterator() {
}

function IterateFunc(fieldGroup, data, rowIndex, result) {
}

DatasourceIterator.prototype.iterateAllFieldBo = function(datasource, bo, result, iterateFunc) {
	var self = this;
	self.iterateDataBo(datasource, bo, result, function(fieldGroupLi, data, rowIndex, result){
		for (var i = 0; i < fieldGroupLi.length; i++) {
			iterateFunc(fieldGroupLi[i], data, rowIndex, result);
		}
	})
}

function IterateFieldFunc(fieldGroup, result){}

DatasourceIterator.prototype.iterateAllField = function(datasource, result, iterateFunc) {
	var self = this;
	var fieldGroupLi = self._getDataSetFieldGroupLi(datasource.masterData.fixField, datasource.masterData.bizField)
	for (var i = 0; i < fieldGroupLi.length; i++) {
		iterateFunc(fieldGroupLi[i], result);
	}
	if (datasource.detailData) {
		for (var i = 0; i < datasource.detailData.length; i++) {
			var fieldGroupLi = self._getDataSetFieldGroupLi(datasource.detailData[i].fixField, datasource.detailData[i].bizField);
			for (var j = 0; j < fieldGroupLi.length; j++) {
				iterateFunc(fieldGroupLi[j], result);
			}
		}
	}
}


DatasourceIterator.prototype.getFixFieldLi = function(fixField) {
	var fixFieldLi = [];
	fixFieldLi.push(fixField.primaryKey);
	fixFieldLi.push(fixField.createBy);
	fixFieldLi.push(fixField.createTime);
	fixFieldLi.push(fixField.createUnit);
	fixFieldLi.push(fixField.modifyBy);
	fixFieldLi.push(fixField.modifyTime);
	fixFieldLi.push(fixField.modifyUnit);
	fixFieldLi.push(fixField.remark);
	return fixFieldLi;
}

DatasourceIterator.prototype._getDataSetFieldGroupLi = function(fixField, bizField) {
	var self = this;
	var fieldGroupLi = self.getFixFieldLi(fixField);
	for (var i = 0; i < bizField.field.length; i++) {
		fieldGroupLi.push(bizField.field[i]);
	}
	return fieldGroupLi;
}

function IterateDataFunc(fieldGroupLi, data, rowIndex, result) {}

DatasourceIterator.prototype.iterateDataBo = function(datasource, bo, result, iterateFunc) {
	var self = this;
	self._iterateMasterDataBo(datasource, bo, result, iterateFunc);
	self._iterateDetailDataBo(datasource, bo, result, iterateFunc)
}

DatasourceIterator.prototype._iterateMasterDataBo = function(datasource, bo, result, iterateFunc) {
	var self = this;
	var data = bo["A"];
	var fieldGroupLi = self._getDataSetFieldGroupLi(datasource.masterData.fixField, datasource.masterData.bizField)
	var rowIndex = 0;
	iterateFunc(fieldGroupLi, data, rowIndex, result)
}

DatasourceIterator.prototype._iterateDetailDataBo = function(datasource, bo, result, iterateFunc) {
	var self = this;
	if (datasource.detailData) {
		for (var i = 0; i < datasource.detailData.length; i++) {
			var item = datasource.detailData[i];
			var fieldGroupLi = self._getDataSetFieldGroupLi(item.fixField, item.bizField);
			var dataLi = bo[item.id];
			for (var j = 0; j < dataLi.length; j++) {
				var data = dataLi[j];
				iterateFunc(fieldGroupLi, data, j, result);
			}
		}
	}
}

function IterateFunc(dataSet, result){}

DatasourceIterator.prototype.iterateAllDataSet = function(datasource, result, iterateFunc) {
	var self = this;
	iterateFunc(datasource.masterData, result);
	if (datasource.detailData) {
		for (var i = 0; i < datasource.detailData.length; i++) {
			iterateFunc(datasource.detailData[i], result);
		}
	}
}

//function IterateFunc(btnName, btnValue, result){}
//
//DatasourceIterator.prototype.iterateAllButton = function(datasource, result, iterateFunc) {
//	var self = this;
//	if (datasource.buttonConfig) {
//		for (var key in datasource.buttonConfig) {
//			iterateFunc(key, datasource.buttonConfig[key], result);
//		}
//	}
//}



