var g_errorLog = {};

function ColumnManager() {
}

ColumnManager.prototype.isZeroShowEmpty = function(o) {
	return o.column.zeroShowEmpty == "true" && (o.value == "0" || o.value == "00000000000000000000.0000000000");
}

ColumnManager.prototype.createIdColumn = function(columnModel) {
	if (columnModel.idColumn.Hideable != "true") {
		return {
			width: columnModel.idColumn.Width || "",
			key: columnModel.idColumn.name,
			label: columnModel.idColumn.Text
		};
	}
	return null;
}

ColumnManager.prototype.createCheckboxColumn = function(columnModel) {
	if (columnModel.checkboxColumn.Hideable != "true") {
		var key = columnModel.checkboxColumn.name;
		if (columnModel.selectionMode == "radio") {
			return {
				width: "40",
				key:        key,
				allowHTML:  true, // to avoid HTML escaping
				label:      '选择',
				//formatter:      '<input type="radio" name="' + key + '" />'
				formatter:function(o) {
					if (o.value === false) {
						return "";
					}
					return '<input type="radio" name="' + key + '" />';
				}
				//,emptyCellValue: '<input type="checkbox"/>'
			};
		} else {
			return {
				width: "40",
				key:        key,
				allowHTML:  true, // to avoid HTML escaping
				label:      '<input type="checkbox" class="protocol-select-all" title="全部选中"/>',
				//formatter:      '<input type="checkbox" />'
				formatter:function(o) {
					if (o.value === false) {
						return "";
					}
					return '<input type="checkbox" />';
				}
				//,emptyCellValue: '<input type="checkbox"/>'
			};
		}
	}
	return null;
}

ColumnManager.prototype.createVirtualColumn = function(columnModelName, columnModel, columnIndex) {
	var self = this;
	var yInst = self.yInst;
	var i = columnIndex;
	if (columnModel.ColumnLi[i].XMLName.Local == "virtual-column" && columnModel.ColumnLi[i].Hideable != "true") {
		var virtualColumn = columnModel.ColumnLi[i];
		return {
			width: columnModel.ColumnLi[i].Width || "",
			key: columnModel.ColumnLi[i].name,
			label: columnModel.ColumnLi[i].Text,
			allowHTML:  true, // to avoid HTML escaping
			formatter:      function(virtualColumn){
				return function(o){
					var htmlLi = [];
//					htmlLi.push("<div class='btnWrapper_" + virtualColumn.name + "'>");
					var buttonBoLi = null;
					if (o.value) {
						buttonBoLi = o.value[virtualColumn.Buttons.XMLName.Local];
					}
					for (var j = 0; j < virtualColumn.Buttons.ButtonLi.length; j++) {
						var btnTemplate = null;
						if (virtualColumn.Buttons.ButtonLi[j].Mode == "fn") {
							btnTemplate = "<a title='{value}' onclick='doVirtualColumnBtnAction(\"{columnModelName}\", this, {handler})' class='{class}' href='javascript:void(0);' style='display:block;' />";
						} else if (virtualColumn.Buttons.ButtonLi[j].Mode == "url") {
							btnTemplate = "<a title='{value}' onclick='location.href=\"{href}\"' class='{class}' href='javascript:void(0);' style='display:block;' />";
						} else if (virtualColumn.Buttons.ButtonLi[j].Mode == "url!") {
							btnTemplate = "<a title='{value}' onclick='openTabOrJump(\"{href}\")' class='{class}' href='javascript:void(0);' style='display:block;' />";
						} else {
							btnTemplate = "<a title='{value}' onclick='window.open(\"{href}\")' class='{class}' href='javascript:void(0);' style='display:block;' />";
						}
						if (!buttonBoLi || buttonBoLi[j]["isShow"]) {
							var id = columnModel.idColumn.name;
							var isUsed = g_usedCheck && g_usedCheck[columnModel.dataSetId] && g_usedCheck[columnModel.dataSetId][o.data[id]];
							if (!(isUsed && virtualColumn.Buttons.ButtonLi[j].name == "btn_delete")) {
								// handler进行值的预替换,
								var Y = yInst;
								var handler = virtualColumn.Buttons.ButtonLi[j].Handler;
								handler = Y.Lang.sub(handler, o.data);
								htmlLi.push(Y.Lang.sub(btnTemplate, {
									value: virtualColumn.Buttons.ButtonLi[j].Text,
									handler: handler,
									"class": virtualColumn.Buttons.ButtonLi[j].IconCls,
									href: handler,
									columnModelName: columnModelName
								}));
							}
							
							/*
							// handler进行值的预替换,
							var Y = yInst;
							var handler = virtualColumn.Buttons.ButtonLi[j].Handler;
							handler = Y.Lang.sub(handler, o.data);
							htmlLi.push(Y.Lang.sub(btnTemplate, {
								value: virtualColumn.Buttons.ButtonLi[j].Text,
								handler: handler,
								"class": virtualColumn.Buttons.ButtonLi[j].IconCls,
								href: handler,
								columnModelName: columnModelName
							}));
							 */
						}
					}
//					htmlLi.push("</div>");
					return htmlLi.join("");
				}
			}(virtualColumn)
		};
	}
	return null;
}

ColumnManager.prototype.createNumberColumn = function(columnConfig, columnModel) {
	var self = this;
	var yInst = self.yInst;
	var decimalPlaces = 2;
	if (columnConfig.DecimalPlaces) {
		decimalPlaces = parseInt(columnConfig.DecimalPlaces, 10);
	}
	var isFormatter = (columnConfig.Prefix || "") != "";
	isFormatter = isFormatter || (columnConfig.DecimalPlaces || "") != "";
	isFormatter = isFormatter || (columnConfig.DecimalSeparator || "") != "";
	isFormatter = isFormatter || (columnConfig.ThousandsSeparator || "") != "";
	isFormatter = isFormatter || (columnConfig.Suffix || "") != "";
	
	// 财务相关字段的判断,以决定是否用 formatter 函数,
	isFormatter = isFormatter || (columnConfig.CurrencyField || "") != "";
	isFormatter = isFormatter || (columnConfig.IsPercent || "") != "";

	var zeroShowEmpty = columnConfig.ZeroShowEmpty == "true";
	
	/*
	if (columnConfig.name == "sequenceNo") {
		console.log("sequence no");
		console.log(isFormatter);
		console.log(columnConfig.Prefix);
		console.log(columnConfig.Prefix != "");
	}
	*/
	if (isFormatter) {
		return {
			width: columnConfig.Width || "",
			key: columnConfig.name,
			label: columnConfig.Text,
			formatter: yInst.bind(self.currencyFormatFunc, self),
			
			prefix: columnConfig.Prefix,
			decimalPlaces: decimalPlaces,
			decimalSeparator: columnConfig.DecimalSeparator,
			thousandsSeparator: columnConfig.ThousandsSeparator,
			suffix: columnConfig.Suffix,
			
			currencyField: columnConfig.CurrencyField,
			isPercent: columnConfig.IsPercent,
			isMoney: columnConfig.IsMoney,
			isUnitPrice: columnConfig.IsUnitPrice,
			isCost: columnConfig.IsCost,
			zeroShowEmpty: columnConfig.ZeroShowEmpty,
			columnModel: columnModel
		};
	}
	if (zeroShowEmpty) {
		return {
			width: columnConfig.Width || "",
			key: columnConfig.name,
			label: columnConfig.Text,
			formatter: function(o) {
				if (o.value == "0") {
					return "";
				}
				return o.value;
			}
		};
	}
	return {
		width: columnConfig.Width || "",
		key: columnConfig.name,
		label: columnConfig.Text
	};
}

ColumnManager.prototype.convertDate2DisplayPattern = function(displayPattern) {
	displayPattern = displayPattern.replace("yyyy", "%Y");
	displayPattern = displayPattern.replace("MM", "%m");
	displayPattern = displayPattern.replace("dd", "%d");
	displayPattern = displayPattern.replace("HH", "%H");
	displayPattern = displayPattern.replace("mm", "%M");
	displayPattern = displayPattern.replace("ss", "%S");
	return displayPattern; 
}

/*
DisplayPattern string `xml:"displayPattern,attr"`
DbPattern      string `xml:"dbPattern,attr"`
*/
ColumnManager.prototype.createDateColumn = function(columnConfig) {
	var self = this;
	var yInst = self.yInst;
	var dbPattern = columnConfig.DbPattern;
	var displayPattern = columnConfig.DisplayPattern;
	if (dbPattern && displayPattern) {
		return {
			width: columnConfig.Width || "",
			key: columnConfig.name,
			label: columnConfig.Text,
			dbPattern: dbPattern,
			displayPattern: displayPattern,
			zeroShowEmpty: columnConfig.ZeroShowEmpty,
			formatter: function(o) {
				if (new ColumnManager().isZeroShowEmpty(o)) {
					return "";
				}
				if (o.value !== undefined && o.value !== null) {
					var date = new Date();
					var value = o.value + "";
					if (o.column.dbPattern.indexOf("yyyy") > -1) {
						var start = o.column.dbPattern.indexOf("yyyy");
						var end = o.column.dbPattern.indexOf("yyyy") + "yyyy".length;
						var yyyy = value.substring(start, end);
						date.setYear(parseInt(yyyy, 10));
					}
					if (o.column.dbPattern.indexOf("MM") > -1) {
						var start = o.column.dbPattern.indexOf("MM");
						var end = o.column.dbPattern.indexOf("MM") + "MM".length;
						var mm = value.substring(start, end);
						date.setMonth(parseInt(mm, 10) - 1);
					}
					if (o.column.dbPattern.indexOf("dd") > -1) {
						var start = o.column.dbPattern.indexOf("dd");
						var end = o.column.dbPattern.indexOf("dd") + "dd".length;
						var dd = value.substring(start, end);
						date.setDate(parseInt(dd, 10));
					}
					if (o.column.dbPattern.indexOf("HH") > -1) {
						var start = o.column.dbPattern.indexOf("HH");
						var end = o.column.dbPattern.indexOf("HH") + "HH".length;
						var hh = value.substring(start, end);
						date.setHours(parseInt(hh, 10));
					}
					if (o.column.dbPattern.indexOf("mm") > -1) {
						var start = o.column.dbPattern.indexOf("mm");
						var end = o.column.dbPattern.indexOf("mm") + "mm".length;
						var mm = value.substring(start, end);
						date.setMinutes(mm);
					}
					if (o.column.dbPattern.indexOf("ss") > -1) {
						var start = o.column.dbPattern.indexOf("ss");
						var end = o.column.dbPattern.indexOf("ss") + "ss".length;
						var ss = value.substring(start, end);
						date.setSeconds(ss);
					}
					// js格式参考 http://yuilibrary.com/yui/docs/api/classes/Date.html#method_format
					var columnManager = new ColumnManager();
					var displayPattern = columnManager.convertDate2DisplayPattern(o.column.displayPattern);
					return yInst.DataType.Date.format(date, {
						format: displayPattern
					});
				}
				return o.value;
			}
		};
	} else {
		if (!g_errorLog[columnConfig.name]) {
			g_errorLog[columnConfig.name] = columnConfig.Text;
			console.log(columnConfig);
			console.log("日期字段未同时配置dbPattern和displayPattern");
		}
	}
	var zeroShowEmpty = columnConfig.ZeroShowEmpty == "true";
	if (zeroShowEmpty) {
		return {
			width: columnConfig.Width || "",
			key: columnConfig.name,
			label: columnConfig.Text,
			formatter: function(o) {
				if (o.value == "0") {
					return "";
				}
				return o.value;
			}
		};
	}
	return {
		width: columnConfig.Width || "",
		key: columnConfig.name,
		label: columnConfig.Text
	};
}

ColumnManager.prototype.createBooleanColumn = function(columnConfig) {
	return {
		width: columnConfig.Width || "",
		key: columnConfig.name,
		label: columnConfig.Text,
		formatter: function(o) {
			if (o.value + "" == "true") {
				return "是";
			} else if (o.value + "" == "false") {
				return "否";
			}
			return o.value;
		}
	};
}

ColumnManager.prototype.createDictionaryColumn = function(columnConfig) {
	return {
		width: columnConfig.Width || "",
		key: columnConfig.name,
		label: columnConfig.Text,
		formatter: function(o) {
			if (g_layerBo[columnConfig.Dictionary] && g_layerBo[columnConfig.Dictionary][o.value]) {
				return g_layerBo[columnConfig.Dictionary][o.value].name;
			}
			if (!g_errorLog[columnConfig.name + "_DICTIONARY_NAME"]) {
				g_errorLog[columnConfig.name + "_DICTIONARY_NAME"] = columnConfig.name;
				console.log(o);
				console.log(o.data);
				console.log(columnConfig);
				console.log(columnConfig.name);
				console.log("字典字段没找到,columnName:" + columnConfig.name + ", dictionaryName:" + columnConfig.Dictionary + ",code:" + o.value);
			}
			return o.value;
		}
	};
}

ColumnManager.prototype.createSelectColumn = function(columnConfig) {
	var self = this;
	return {
		width: columnConfig.Width || "",
		key: columnConfig.name,
		label: columnConfig.Text,
		allowHTML:  true,
		zeroShowEmpty: columnConfig.ZeroShowEmpty,
		formatter: function(o) {
			if (new ColumnManager().isZeroShowEmpty(o)) {
				return "";
			}
			var commonUtil = new CommonUtil();
			var bo = {"A": o.data};
			var relationItem = commonUtil.getCRelationItem(columnConfig.relationDS, bo, o.data);
			if (!relationItem) {
				if (!g_errorLog[columnConfig.name]) {
					g_errorLog[columnConfig.name] = columnConfig.name;
					console.log(o);
					console.log(o.data);
					console.log(columnConfig);
					console.log(columnConfig.name);
					console.log("未找到匹配的relationItem，有可能配置错误，目标referenceDataSourceModelId为:" + o.data.referenceDataSourceModelId);
				}
			}
			var selectorName = relationItem.relationConfig.selectorName;
			var displayField = relationItem.relationConfig.displayField;
			var selectorData = g_relationManager.getRelationBo(selectorName, o.value);
			if (selectorData) {
				var valueLi = [];
				var keyLi = displayField.split(',');
        		for (var j = 0; j < keyLi.length; j++) {
        			if (selectorData[keyLi[j]]) {
        				valueLi.push(selectorData[keyLi[j]]);
        			}
        		}
        		var html = [];
        		html.push("<span class='floatLeft'>" + valueLi.join(",") + "</span>");
        		var selectorTitle = g_relationBo[selectorName].description;
        		var url = g_relationBo[selectorName].url || "";
        		url = self.yInst.Lang.sub(url, selectorData);
        		var jsAction = "triggerShowModalDialog({'title': '" + selectorTitle + "','url': '" + url + "'})";
        		html.push('<a class="etrigger_view selectIndent" href="javascript:void(0);" title="查看" onclick="' + jsAction + '"></a>');
        		return html.join("");
			} else {
				if (!g_errorLog[columnConfig.name]) {
					g_errorLog[columnConfig.name] = columnConfig.name;
					console.log(o);
					console.log(o.data);
					console.log(columnConfig);
					console.log(columnConfig.name);
					console.log("关联object未找到,columnName:" + columnConfig.name + ", selectorName:" + selectorName + ",id:" + o.value);
				}
			}
			return o.value;
		}
	};
}

ColumnManager.prototype.createRowIndexColumn = function(columnModel) {
	if (columnModel.Rownumber == "true") {
		return {
			width: 40,
			key: "",
			label: "序号",
			formatter: function(o) {
				return o.rowIndex + 1;
			}
		};
	}
	return null;
}

ColumnManager.prototype.createColumn = function(columnConfig, columnModel) {
	var self = this;
	if (columnConfig.XMLName.Local != "virtual-column" && columnConfig.Hideable != "true") {
		if (columnConfig.ColumnModel.ColumnLi) {
			var result = {
				label: columnConfig.Text,
				"children": []
			};
			for (var i = 0; i < columnConfig.ColumnModel.ColumnLi.length; i++) {
				var childColumn = self.createColumn(columnConfig.ColumnModel.ColumnLi[i], columnModel);
				if (childColumn) {
					result.children.push(childColumn);
				}
			}
			return result;
		}
		
		if (columnConfig.XMLName.Local == "number-column") {
			return self.createNumberColumn(columnConfig, columnModel);
		} else if (columnConfig.XMLName.Local == "date-column") {
			return self.createDateColumn(columnConfig);
		} else if (columnConfig.XMLName.Local == "boolean-column") {
			return self.createBooleanColumn(columnConfig);
		} else if (columnConfig.XMLName.Local == "dictionary-column") {
			return self.createDictionaryColumn(columnConfig);
		} else if (columnConfig.XMLName.Local == "select-column") {
			return self.createSelectColumn(columnConfig);
		}
		return {
			width: columnConfig.Width || "",
			key: columnConfig.name,
			label: columnConfig.Text
		};
	}
	return null;
}

ColumnManager.prototype._getColumnsCommon = function(columnModelName, columnModel, Y, virtualColumnMatchFunc) {
	var self = this;
	self.yInst = Y;
	var columns = [];
	var checkboxColumn = self.createCheckboxColumn(columnModel);
	if (checkboxColumn) {
		columns.push(checkboxColumn);
	}
	var idColumn = self.createIdColumn(columnModel);
	if (idColumn) {
		columns.push(idColumn);
	}
	var rowIndexColumn = self.createRowIndexColumn(columnModel);
	if (rowIndexColumn) {
		columns.push(rowIndexColumn);
	}
	
	for (var i = 0; i < columnModel.ColumnLi.length; i++) {
		var column = self.createColumn(columnModel.ColumnLi[i], columnModel);
		if (column) {
			columns.push(column);
		} else {
			if (virtualColumnMatchFunc(columnModel.ColumnLi[i])) {
				var virtualColumn = self.createVirtualColumn(columnModelName, columnModel, i);
				if (virtualColumn) {
					columns.push(virtualColumn);
				}
			}
		}
	}
	return columns;
}

ColumnManager.prototype.getColumns = function(columnModelName, columnModel, Y) {
	var self = this;
	return self._getColumnsCommon(columnModelName, columnModel, Y, function(column){
		return column.UseIn == undefined || column.UseIn == "" || column.UseIn == "list";
	});
}



