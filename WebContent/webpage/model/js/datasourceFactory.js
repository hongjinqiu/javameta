function DatasourceFactory() {
}

/**
 * 建立父子双向关联
 */
DatasourceFactory.prototype._applyReverseRelation = function(datasource) {
	datasource.masterData.parent = datasource;
	if (datasource.detailData) {
		for (var i = 0; i < datasource.detailData.length; i++) {
			datasource.detailData[i].parent = datasource;
		}
	}
	datasource.masterData.fixField.parent = datasource.masterData;
	datasource.masterData.bizField.parent = datasource.masterData;
	var datasourceIterator = new DatasourceIterator();
	var masterFixFieldLi = datasourceIterator.getFixFieldLi(datasource.masterData.fixField);
	for (var i = 0; i < masterFixFieldLi.length; i++) {
		masterFixFieldLi[i].parent = datasource.masterData.fixField;
	}
	for (var i = 0; i < datasource.masterData.bizField.field.length; i++) {
		datasource.masterData.bizField.field[i].parent = datasource.masterData.bizField;
	}
	if (datasource.detailData) {
		for (var i = 0; i < datasource.detailData.length; i++) {
			datasource.detailData[i].fixField.parent = datasource.detailData[i];
			datasource.detailData[i].bizField.parent = datasource.detailData[i];
			
			var detailFixFieldLi = datasourceIterator.getFixFieldLi(datasource.detailData[i].fixField);
			for (var j = 0; j < detailFixFieldLi.length; j++) {
				detailFixFieldLi[j].parent = datasource.detailData[i].fixField;
			}
			
			for (var j = 0; j < datasource.detailData[i].bizField.field.length; j++) {
				datasource.detailData[i].bizField.field[j].parent = datasource.detailData[i].bizField;
			}
		}
	}
}

/**
 * 为字段加入是否主数据集字段的方法
 */
DatasourceFactory.prototype._applyIsMasterField = function(datasource) {
	var datasourceIterator = new DatasourceIterator();
	var masterFixFieldLi = datasourceIterator.getFixFieldLi(datasource.masterData.fixField);
	for (var i = 0; i < masterFixFieldLi.length; i++) {
		masterFixFieldLi[i].isMasterField = function() {
			return true;
		}
	}
	for (var i = 0; i < datasource.masterData.bizField.field.length; i++) {
		datasource.masterData.bizField.field[i].isMasterField = function() {
			return true;
		}
	}
	if (datasource.detailData) {
		for (var i = 0; i < datasource.detailData.length; i++) {
			var detailFixFieldLi = datasourceIterator.getFixFieldLi(datasource.detailData[i].fixField);
			for (var j = 0; j < detailFixFieldLi.length; j++) {
				detailFixFieldLi[j].isMasterField = function() {
					return false;
				}
			}
			
			for (var j = 0; j < datasource.detailData[i].bizField.field.length; j++) {
				datasource.detailData[i].bizField.field[j].isMasterField = function() {
					return false;
				}
			}
		}
	}
}

/**
 * 为字段加入是否关联字段的方法
 */
DatasourceFactory.prototype._applyIsRelationField = function(datasource) {
	var datasourceIterator = new DatasourceIterator();
	var result = {};
	datasourceIterator.iterateAllField(datasource, result, function(fieldGroup, result){
		fieldGroup.isRelationField = function(){
			if (fieldGroup.relationDS && fieldGroup.relationDS.relationItem && fieldGroup.relationDS.relationItem.length > 0) {
				return true;
			}
			return false;
		}
	});
}

/**
 * 默认用第一个关联字段生成关联配置
 */
DatasourceFactory.prototype._applyRelationFieldValue = function(datasource) {
	var datasourceIterator = new DatasourceIterator();
	var result = {};
	var commonUtil = new CommonUtil();
	datasourceIterator.iterateAllField(datasource, result, function(fieldGroup, result){
		if (fieldGroup.isRelationField()) {
			if (!fieldGroup.jsConfig) {
				fieldGroup.jsConfig = {};
			}
			var relationItem = fieldGroup.relationDS.relationItem[0];
			var triggerConfig = {
				displayField: commonUtil.getFuncOrString(relationItem.displayField),
				valueField: commonUtil.getFuncOrString(relationItem.valueField),
				selectorName: commonUtil.getFuncOrString(relationItem.id),
				selectionMode: "single"
			};
			for (var key in triggerConfig) {
				fieldGroup.jsConfig[key] = triggerConfig[key];
			}
		}
	});
}

/**
 * 添加获取主数据集方法
 */
DatasourceFactory.prototype._applyGetMasterData = function(datasource) {
	var datasourceIterator = new DatasourceIterator();
	var result = {};
	datasourceIterator.iterateAllField(datasource, result, function(fieldGroup, result){
		fieldGroup.getMasterData = function() {
			if (this.isMasterField()) {
				return this.parent.parent;
			}
			return null;
		}
	});
}

/**
 * 添加获取分录数据集方法
 */
DatasourceFactory.prototype._applyGetDetailData = function(datasource) {
	var datasourceIterator = new DatasourceIterator();
	var result = {};
	datasourceIterator.iterateAllField(datasource, result, function(fieldGroup, result){
		fieldGroup.getDetailData = function() {
			if (this.isMasterField()) {
				return null;
			}
			return this.parent.parent;
		}
	});
}

/**
 * 添加获取数据源方法
 */
DatasourceFactory.prototype._applyGetDatasource = function(datasource) {
	var datasourceIterator = new DatasourceIterator();
	var result = {};
	datasourceIterator.iterateAllField(datasource, result, function(fieldGroup, result){
		fieldGroup.getDatasource = function() {
			if (this.isMasterField()) {
				return this.getMasterData().parent;
			}
			return this.getDetailData().parent;
		}
	});
}

/**
 * 添加获取数据集Id方法
 */
DatasourceFactory.prototype._applyGetDataSetId = function(datasource) {
	var datasourceIterator = new DatasourceIterator();
	var result = {};
	datasourceIterator.iterateAllField(datasource, result, function(fieldGroup, result){
		fieldGroup.getDataSetId = function() {
			if (this.isMasterField()) {
				return this.getMasterData().id;
			}
			return this.getDetailData().id;
		}
	});
}

/**
 * 扩展datasource,
 */
DatasourceFactory.prototype.extendDatasource = function(datasource, modelExtraInfo) {
	var datasourceIterator = new DatasourceIterator();
	var result = {};
	datasourceIterator.iterateAllField(datasource, result, function(fieldGroup, result){
		var dataSetConfig = modelExtraInfo[fieldGroup.getDataSetId()];
		if (dataSetConfig && dataSetConfig[fieldGroup.id]) {
			for (var key in dataSetConfig[fieldGroup.id]) {
				if (!fieldGroup.jsConfig) {
					fieldGroup.jsConfig = {};
				}
				fieldGroup.jsConfig[key] = dataSetConfig[fieldGroup.id][key];
			}
		}
	});
	datasourceIterator.iterateAllDataSet(datasource, result, function(dataSet, result){
		var dataSetConfig = modelExtraInfo[dataSet.id];
		if (dataSetConfig) {
			for (var key in dataSetConfig) {
				var isFieldGroupConfig = false;
				datasourceIterator.iterateAllField(datasource, result, function(fieldGroup, result){
					if (!isFieldGroupConfig && fieldGroup.id == key) {
						isFieldGroupConfig = true;
					}
				});
				if (!isFieldGroupConfig) {
					if (!dataSet.jsConfig) {
						dataSet.jsConfig = {};
					}
					dataSet.jsConfig[key] = dataSetConfig[key];
				}
			}
		}
	});
	if (modelExtraInfo.validate) {
		if (!datasource.jsConfig) {
			datasource.jsConfig = {};
		}
		datasource.jsConfig.validate = modelExtraInfo.validate;
	}
}

/**
 * 扩展datasource,扩展字段的关联关系等等,
 */
DatasourceFactory.prototype.enhanceDatasource = function(datasource) {
	this._applyReverseRelation(datasource);
	this._applyIsMasterField(datasource);
	this._applyIsRelationField(datasource);
	this._applyRelationFieldValue(datasource);
	this._applyGetMasterData(datasource);
	this._applyGetDetailData(datasource);
	this._applyGetDatasource(datasource);
	this._applyGetDataSetId(datasource);
}

DatasourceFactory.prototype.applyDataSetDefaultValue = function(datasource, dataSetId, bo, data) {
	var self = this;
	var datasourceIterator = new DatasourceIterator();
	var result = "";
	datasourceIterator.iterateAllField(datasource, result, function(fieldGroup, result){
		if (fieldGroup.getDataSetId() == dataSetId) {
			var mode = fieldGroup.defaultValueExpr.mode;
			var content = fieldGroup.defaultValueExpr.value;
			if (fieldGroup.jsConfig && fieldGroup.jsConfig.defaultValueExprForJs) {
				data[fieldGroup.id] = fieldGroup.jsConfig.defaultValueExprForJs(bo, data);
			} else if ((mode == "text" || !mode) && content) {
				self.applyFieldGroupValueByString(fieldGroup, data, content);
			}
		}
	});
}

DatasourceFactory.prototype.applyDataSetCalcValue = function(datasource, dataSetId, bo, data) {
	var self = this;
	var datasourceIterator = new DatasourceIterator();
	var result = "";
	datasourceIterator.iterateAllField(datasource, result, function(fieldGroup, result){
		if (fieldGroup.getDataSetId() == dataSetId) {
			var mode = fieldGroup.calcValueExpr.mode;
			var content = fieldGroup.calcValueExpr.value;
			if (fieldGroup.jsConfig && fieldGroup.jsConfig.calcValueExprForJs) {
				data[fieldGroup.id] = fieldGroup.jsConfig.calcValueExprForJs(bo, data);
			} else if ((mode == "text" || !mode) && content) {
				self.applyFieldGroupValueByString(fieldGroup, data, content);
			}
		}
	});
}

DatasourceFactory.prototype.applyDataSetCopyValue = function(datasource, dataSetId, srcData, destData) {
	var self = this;
	var datasourceIterator = new DatasourceIterator();
	var result = "";
	datasourceIterator.iterateAllField(datasource, result, function(fieldGroup, result){
		if (fieldGroup.getDataSetId() == dataSetId) {
			if (fieldGroup.allowCopy === undefined || fieldGroup.allowCopy === "" || fieldGroup.allowCopy) {// false == "",返回true,因此用===
				if (srcData[fieldGroup.id]) {
					destData[fieldGroup.id] = srcData[fieldGroup.id];
				}
			}
		}
	});
}

DatasourceFactory.prototype.applyFieldGroupValueByString = function(fieldGroup, data, content) {
	var stringArray = ["STRING"];
	for (var i = 0; i < stringArray.length; i++) {
		if (stringArray[i] == fieldGroup.fieldType) {
			data[fieldGroup.id] = content;
			return;
		}
	}
	var intArray = ["SHORT", "INT", "LONG"];
	for (var i = 0; i < intArray.length; i++) {
		if (intArray[i] == fieldGroup.fieldType) {
			if (content == undefined || content == "") {
				data[fieldGroup.id] = 0;
			} else {
				if (isNumber(content)) {
					data[fieldGroup.id] = parseInt(content, 10);
				} else {
					console.log("赋值错误,fieldGroup.dataSetId=" + fieldGroup.getDataSetId() + ", fieldGroup.id=" + fieldGroup.id + ", expect type is:" + intArray.join(",") + ", but value=" + content);
				}
			}
			return
		}
	}
	var floatArray = ["FLOAT", "DOUBLE", "DECIMAL"];
	for (var i = 0; i < floatArray.length; i++) {
		if (floatArray[i] == fieldGroup.fieldType) {
			if (content == undefined || content == "") {
				data[fieldGroup.id] = "0";
			} else {
				if (isNumber(content)) {
					//data[fieldGroup.id] = parseFloat(content);
					data[fieldGroup.id] = content;
				} else {
					console.log("赋值错误,fieldGroup.dataSetId=" + fieldGroup.getDataSetId() + ", fieldGroup.id=" + fieldGroup.id + ", expect type is:" + floatArray.join(",") + ", but value=" + content);
				}
			}
			return
		}
	}
	if (fieldGroup.fieldType == "DATE") {
		if (content == undefined || content == "") {
			data[fieldGroup.id] = "";
		} else {
			var tmpContent = content;
			tmpContent = tmpContent.replace(/-|\//g, "");
			data[fieldGroup.id] = tmpContent;
		}
		return;
	}
	if (fieldGroup.fieldType == "TIME") {
		if (content == undefined || content == "") {
			data[fieldGroup.id] = "";
		} else {
			var tmpContent = content;
			tmpContent = tmpContent.replace(/-|\/|:/g, "");
			data[fieldGroup.id] = tmpContent;
		}
		return;
	}
	if (fieldGroup.fieldType == "TIMESTAMP") {
		if (content == undefined || content == "") {
			data[fieldGroup.id] = "";
		} else {
			var tmpContent = content;
			tmpContent = tmpContent.replace(/-|\/|:| /g, "");
			data[fieldGroup.id] = tmpContent;
		}
		return;
	}
}

var g_sequenceNo = 1;
DatasourceFactory.prototype.getSequenceNo = function() {
	return "gridId" + (++g_sequenceNo);
}

