var g_errorLog = {};

// 对Date的扩展，将 Date 转化为指定格式的String   
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
// 例子：   
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18   
Date.prototype.Format = function(fmt)   
{ //author: meizz   
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "H+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}

function ColumnManager() {
}

ColumnManager.prototype.isZeroShowEmpty = function(zeroShowEmpty, o) {
	return zeroShowEmpty && (o == "0" || o == "00000000000000000000.0000000000");
}

ColumnManager.prototype.createIdColumn = function(columnModel) {
	return {
		width: columnModel.idColumn.width || "",
		field: columnModel.idColumn.name,
		title: columnModel.idColumn.text,
		colspan: columnModel.idColumn.colSpan || 1,
		rowspan: columnModel.idColumn.rowSpan || 1,
		hidden: columnModel.idColumn.hideable,
		fixed: true
	};
}

ColumnManager.prototype.createCheckboxColumn = function(columnModel) {
	return {
		width: columnModel.checkboxColumn.width || "",
		field: columnModel.checkboxColumn.name,
		title: columnModel.checkboxColumn.text,
		colspan: columnModel.checkboxColumn.colSpan || 1,
		rowspan: columnModel.checkboxColumn.rowSpan || 1,
		hidden: columnModel.checkboxColumn.hideable,
		fixed: true,
		checkbox: true
	};
}

ColumnManager.prototype.createVirtualColumn = function(columnModelName, columnModel, columnIndex) {
	var i = columnIndex;
	if (columnModel.columnList[i].xmlName == "virtual-column") {
		var virtualColumn = columnModel.columnList[i];
		return {
			width: columnModel.columnList[i].width || "",
			field: columnModel.columnList[i].name,
			title: columnModel.columnList[i].text,
			colspan: columnModel.columnList[i].colSpan || 1,
			rowspan: columnModel.columnList[i].rowSpan || 1,
			hidden: columnModel.columnList[i].hideable,
			fixed: true,
			formatter:      function(virtualColumn){
				return function(o, row, index){
					var htmlLi = [];
//					htmlLi.push("<div class='btnWrapper_" + virtualColumn.name + "'>");
					var buttonBoLi = null;
					if (o) {
						buttonBoLi = o[virtualColumn.buttons.xmlName];
					}
					for (var j = 0; j < virtualColumn.buttons.button.length; j++) {
						var btnTemplate = null;
						if (virtualColumn.buttons.button[j].mode == "fn") {
							btnTemplate = "<a title='{value}' onclick='doVirtualColumnBtnAction(\"{dataSetIdOrcolumnModelName}\", \"{id}\", {handler})' class='{class}' href='javascript:void(0);' style='margin-left:3px;'>{value}</a>";
						} else if (virtualColumn.buttons.button[j].mode == "url") {
							btnTemplate = "<a title='{value}' onclick='location.href=\"{href}\"' class='{class}' href='javascript:void(0);' style='margin-left:3px;'>{value}</a>";
						} else if (virtualColumn.buttons.button[j].mode == "url!") {
							btnTemplate = "<a title='{value}' onclick='openTabOrJump(\"{href}\")' class='{class}' href='javascript:void(0);' style='margin-left:3px;'>{value}</a>";
						} else {
							btnTemplate = "<a title='{value}' onclick='window.open(\"{href}\")' class='{class}' href='javascript:void(0);' style='margin-left:3px;'>{value}</a>";
						}
						if (!buttonBoLi || buttonBoLi[j]["isShow"]) {
							var id = columnModel.idColumn.name;
							var isUsed;
							if (row["javameta_used"] !== undefined) {// 列表页,easyui用的是懒加载,g_usedCheck没值,值放到row.javameta_used中,
								isUsed = row["javameta_used"];
							} else {
								isUsed = g_usedCheck && g_usedCheck[columnModel.dataSetId] && g_usedCheck[columnModel.dataSetId][row[id]];
							}
							if (!(isUsed && virtualColumn.buttons.button[j].name == "btn_delete")) {
								// handler进行值的预替换,
								var handler = virtualColumn.buttons.button[j].handler;
								for (var key in row) {
									var regExp = new RegExp("{" + key + "}");
									regExp.global = true;
									handler = handler.replace(regExp, row[key]);
								}
								var href = handler;
								if (!href.startsWith("http")) {
									href = webRoot + href;
								}
								btnTemplate = btnTemplate.replace(/{id}/g, row[id]);
								btnTemplate = btnTemplate.replace(/{value}/g, virtualColumn.buttons.button[j].text);
								btnTemplate = btnTemplate.replace(/{handler}/g, handler);
								btnTemplate = btnTemplate.replace(/{class}/g, virtualColumn.buttons.button[j].iconCls);
								btnTemplate = btnTemplate.replace(/{href}/g, href);
								btnTemplate = btnTemplate.replace(/{dataSetIdOrcolumnModelName}/g, columnModel.dataSetId || columnModelName);
								htmlLi.push(btnTemplate);
							}
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
	var zeroShowEmpty = columnConfig.zeroShowEmpty;
	if (zeroShowEmpty) {
		return {
			width: columnConfig.width || "",
			field: columnConfig.name,
			title: columnConfig.text,
			colspan: columnConfig.colSpan || 1,
			rowspan: columnConfig.rowSpan || 1,
			hidden: columnConfig.hideable,
			fixed: true,
			formatter: function(value, row, index) {
				if (value == "0") {
					return "";
				}
				return value;
			}
		};
	}
	return {
		width: columnConfig.width || "",
		field: columnConfig.name,
		title: columnConfig.text,
		colspan: columnConfig.colSpan || 1,
		rowspan: columnConfig.rowSpan || 1,
		hidden: columnConfig.hideable,
		fixed: true
	};
}

ColumnManager.prototype.convertDate2DisplayPattern = function(displayPattern) {
//	displayPattern = displayPattern.replace("yyyy", "%Y");
//	displayPattern = displayPattern.replace("MM", "%m");
//	displayPattern = displayPattern.replace("dd", "%d");
//	displayPattern = displayPattern.replace("HH", "%H");
//	displayPattern = displayPattern.replace("mm", "%M");
//	displayPattern = displayPattern.replace("ss", "%S");
	
	displayPattern = displayPattern.replace("yyyy", "yyyy");
	displayPattern = displayPattern.replace("MM", "MM");
	displayPattern = displayPattern.replace("dd", "dd");
	displayPattern = displayPattern.replace("HH", "HH");
	displayPattern = displayPattern.replace("mm", "mm");
	displayPattern = displayPattern.replace("ss", "ss");
	return displayPattern; 
}

/*
DisplayPattern string `xml:"displayPattern,attr"`
DbPattern      string `xml:"dbPattern,attr"`
*/
ColumnManager.prototype.createDateColumn = function(columnConfig) {
	var dbPattern = columnConfig.dbPattern;
	var displayPattern = columnConfig.displayPattern;
	if (dbPattern && displayPattern) {
		return {
			width: columnConfig.width || "",
			field: columnConfig.name,
			title: columnConfig.text,
			dbPattern: dbPattern,
			displayPattern: displayPattern,
			zeroShowEmpty: columnConfig.zeroShowEmpty,
			colspan: columnConfig.colSpan || 1,
			rowspan: columnConfig.rowSpan || 1,
			hidden: columnConfig.hideable,
			fixed: true,
			formatter: function(o, row, index) {
				if (new ColumnManager().isZeroShowEmpty(columnConfig.zeroShowEmpty, o)) {
					return "";
				}
				if (o !== undefined && o !== null) {
					var date = new Date();
					var value = o + "";
					if (dbPattern.indexOf("yyyy") > -1) {
						var start = dbPattern.indexOf("yyyy");
						var end = dbPattern.indexOf("yyyy") + "yyyy".length;
						var yyyy = value.substring(start, end);
						date.setYear(parseInt(yyyy, 10));
					}
					if (dbPattern.indexOf("MM") > -1) {
						var start = dbPattern.indexOf("MM");
						var end = dbPattern.indexOf("MM") + "MM".length;
						var mm = value.substring(start, end);
						date.setMonth(parseInt(mm, 10) - 1);
					}
					if (dbPattern.indexOf("dd") > -1) {
						var start = dbPattern.indexOf("dd");
						var end = dbPattern.indexOf("dd") + "dd".length;
						var dd = value.substring(start, end);
						date.setDate(parseInt(dd, 10));
					}
					if (dbPattern.indexOf("HH") > -1) {
						var start = dbPattern.indexOf("HH");
						var end = dbPattern.indexOf("HH") + "HH".length;
						var hh = value.substring(start, end);
						date.setHours(parseInt(hh, 10));
					}
					if (dbPattern.indexOf("mm") > -1) {
						var start = dbPattern.indexOf("mm");
						var end = dbPattern.indexOf("mm") + "mm".length;
						var mm = value.substring(start, end);
						date.setMinutes(mm);
					}
					if (dbPattern.indexOf("ss") > -1) {
						var start = dbPattern.indexOf("ss");
						var end = dbPattern.indexOf("ss") + "ss".length;
						var ss = value.substring(start, end);
						date.setSeconds(ss);
					}
					// js格式参考 http://yuilibrary.com/yui/docs/api/classes/Date.html#method_format
					var columnManager = new ColumnManager();
					var displayPattern = columnManager.convertDate2DisplayPattern(columnConfig.displayPattern);
					return date.Format(displayPattern);
				}
				return o;
			}
		};
	} else {
		if (!g_errorLog[columnConfig.name]) {
			g_errorLog[columnConfig.name] = columnConfig.text;
			console.log(columnConfig);
			console.log("日期字段未同时配置dbPattern和displayPattern");
		}
	}
	var zeroShowEmpty = columnConfig.zeroShowEmpty;
	if (zeroShowEmpty) {
		return {
			width: columnConfig.width || "",
			field: columnConfig.name,
			title: columnConfig.text,
			colspan: columnConfig.colSpan || 1,
			rowspan: columnConfig.rowSpan || 1,
			hidden: columnConfig.hideable,
			fixed: true,
			formatter: function(o, row, index) {
				if (o == "0") {
					return "";
				}
				return o;
			}
		};
	}
	return {
		width: columnConfig.width || "",
		field: columnConfig.name,
		title: columnConfig.text,
		colspan: columnConfig.colSpan || 1,
		rowspan: columnConfig.rowSpan || 1,
		hidden: columnConfig.hideable,
		fixed: true
	};
}

ColumnManager.prototype.createBooleanColumn = function(columnConfig) {
	return {
		width: columnConfig.width || "",
		field: columnConfig.name,
		title: columnConfig.text,
		colspan: columnConfig.colSpan || 1,
		rowspan: columnConfig.rowSpan || 1,
		hidden: columnConfig.hideable,
		fixed: true,
		formatter: function(o, row, index) {
			if (o + "" == "true") {
				return "是";
			} else if (o + "" == "false") {
				return "否";
			}
			return o;
		}
	};
}

ColumnManager.prototype.createDictionaryColumn = function(columnConfig) {
	return {
		width: columnConfig.width || "",
		field: columnConfig.name,
		title: columnConfig.text,
		colspan: columnConfig.colSpan || 1,
		rowspan: columnConfig.rowSpan || 1,
		hidden: columnConfig.hideable,
		fixed: true,
		formatter: function(o, row, index) {
			if (g_layerBo[columnConfig.dictionary] && g_layerBo[columnConfig.dictionary][o]) {
				return g_layerBo[columnConfig.dictionary][o].name;
			}
			if (!g_errorLog[columnConfig.name + "_DICTIONARY_NAME"]) {
				g_errorLog[columnConfig.name + "_DICTIONARY_NAME"] = columnConfig.name;
				console.log(o);
				console.log(row);
				console.log(columnConfig);
				console.log(columnConfig.name);
				console.log("字典字段没找到,columnName:" + columnConfig.name + ", dictionaryName:" + columnConfig.dictionary + ",code:" + o);
			}
			return o;
		}
	};
}

ColumnManager.prototype.createTriggerColumn = function(columnConfig) {
	var self = this;
	return {
		width: columnConfig.width || "",
		field: columnConfig.name,
		title: columnConfig.text,
		colspan: columnConfig.colSpan || 1,
		rowspan: columnConfig.rowSpan || 1,
		hidden: columnConfig.hideable,
		fixed: true,
		zeroShowEmpty: columnConfig.zeroShowEmpty,
		formatter: function(o, row, index) {
			if (new ColumnManager().isZeroShowEmpty(columnConfig.zeroShowEmpty, o)) {
				return "";
			}
			var commonUtil = new CommonUtil();
			var bo = {"A": row};
			var relationItem = commonUtil.getRelationItem(columnConfig.relationDS, bo, row);
			if (!relationItem) {
				if (!g_errorLog[columnConfig.name]) {
					g_errorLog[columnConfig.name] = columnConfig.name;
					console.log(o);
					console.log(row);
					console.log(columnConfig);
					console.log(columnConfig.name);
					console.log("未找到匹配的relationItem，有可能配置错误，目标referenceDatasourceModelId为:" + row.referenceDatasourceModelId);
				}
			}
			var selectorName = relationItem.relationConfig.selectorName;
			var displayField = relationItem.relationConfig.displayField;
			var selectorData = g_relationManager.getRelationBo(selectorName, o);
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
        		url = objectReplace(url, selectorData);
        		var jsAction = "triggerShowModalDialog({'title': '" + selectorTitle + "','url': '" + url + "'})";
        		html.push('<a class="etrigger_view selectIndent" href="javascript:void(0);" title="查看" onclick="' + jsAction + '"></a>');
        		return html.join("");
			} else {
				if (!g_errorLog[columnConfig.name]) {
					g_errorLog[columnConfig.name] = columnConfig.name;
					console.log(o);
					console.log(row);
					console.log(columnConfig);
					console.log(columnConfig.name);
					console.log("关联object未找到,columnName:" + columnConfig.name + ", selectorName:" + selectorName + ",id:" + o);
				}
			}
			return o;
		}
	};
}

ColumnManager.prototype.createColumn = function(columnConfig, columnModel) {
	var self = this;
	if (columnConfig.xmlName != "virtual-column") {
		if (columnConfig.columnModel && columnConfig.columnModel.columnList && columnConfig.columnModel.columnList.length > 0) {
			var result = {
				field: columnConfig.name,
				title: columnConfig.text,
				colspan: columnConfig.colSpan || 1,
				rowspan: columnConfig.rowSpan || 1,
				hidden: columnConfig.hideable,
				fixed: true,
				"children": []
			};
			for (var i = 0; i < columnConfig.columnModel.columnList.length; i++) {
				var childColumn = self.createColumn(columnConfig.columnModel.columnList[i], columnModel);
				if (childColumn) {
					result.children.push(childColumn);
				}
			}
			return result;
		}
		
		if (columnConfig.xmlName == "number-column") {
			return self.createNumberColumn(columnConfig, columnModel);
		} else if (columnConfig.xmlName == "date-column") {
			return self.createDateColumn(columnConfig);
		} else if (columnConfig.xmlName == "boolean-column") {
			return self.createBooleanColumn(columnConfig);
		} else if (columnConfig.xmlName == "dictionary-column") {
			return self.createDictionaryColumn(columnConfig);
		} else if (columnConfig.xmlName == "trigger-column") {
			return self.createTriggerColumn(columnConfig);
		}
		return {
			width: columnConfig.width || "",
			field: columnConfig.name,
			title: columnConfig.text,
			colspan: columnConfig.colSpan || 1,
			rowspan: columnConfig.rowSpan || 1,
			hidden: columnConfig.hideable,
			fixed: true
		};
	}
	return null;
}

ColumnManager.prototype._getColumnsCommon = function(columnModelName, columnModel, virtualColumnMatchFunc) {
	var self = this;
	var columns = [];
	var columns1 = [];
	var columns2 = [];
	var checkboxColumn = self.createCheckboxColumn(columnModel);
	if (checkboxColumn) {
		columns1.push(checkboxColumn);
	}
	var idColumn = self.createIdColumn(columnModel);
	if (idColumn) {
		columns1.push(idColumn);
	}
	
	for (var i = 0; i < columnModel.columnList.length; i++) {
		var column = self.createColumn(columnModel.columnList[i], columnModel);
		if (column) {
			if (column.children && column.children.length > 0) {
				for (var j = 0; j < column.children.length; j++) {
					columns2.push(column.children[j]);
				}
				if (column.colspan == 1) {
					column.colspan = column.children.length;
					column.noModifyRowspan = true;
					column.noModifyColspan = true;
				}
				columns1.push(column);
			} else {
				columns1.push(column);
			}
		} else {
			if (virtualColumnMatchFunc(columnModel.columnList[i])) {
				var virtualColumn = self.createVirtualColumn(columnModelName, columnModel, i);
				if (virtualColumn) {
					columns1.push(virtualColumn);
				}
			}
		}
	}
	if (columns1.length > 0) {
		columns.push(columns1);
	}
	if (columns2.length > 0) {
		for (var i = 0; i < columns1.length; i++) {
			if (!columns1[i].noModifyRowspan && columns1[i].rowspan == 1) {
				columns1[i].rowspan = 2;
			}
		}
		columns.push(columns2);
	}
	return columns;
}

ColumnManager.prototype.getColumns = function(columnModelName, columnModel) {
	var self = this;
	return self._getColumnsCommon(columnModelName, columnModel, function(column){
		return true;
	});
}



