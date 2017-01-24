if (typeof(console) == "undefined") {
	console = {
		log: function() {},
		info: function() {},
		error: function() {},
		fatal: function() {}
	};
}

function CommonUtil() {
}

CommonUtil.prototype.getFuncOrString = function(text) {
	if (/^[a-zA-Z\d_]*$/.test(text)) {
		if (eval("typeof(" + text + ")") == "function") {
			return eval(text);
		}
	}
	return text;
}

/*
 * "relationDS" : {
		"relationItem" : [ {
			"copyConfig" : [],
			"name" : "BillType",
			"relationConfig" : {
				"displayField" : "code,name",
				"selectionMode" : "single",
				"selectorName" : "BillTypeSelector",
				"selectorTitle" : "",
				"valueField" : "id"
			},
			"relationExpr" : {
				"mode" : "text",
				"value" : "true"
			}
		} ]
	}
 */
CommonUtil.prototype.getRelationItem = function(cRelationDS, bo, formData) {
	for (var i = 0; i < cRelationDS.relationItem.length; i++) {
		var relationItem = cRelationDS.relationItem[i];
		var mode = relationItem.relationExpr.mode;
		var content = relationItem.relationExpr.value;
		if (mode == undefined || mode == "" || mode == "text") {
			if (content == "true") {
				return relationItem;
			}
		} else if (mode == "js") {
			var data = formData;
			if (eval(content) === true) {
				return relationItem;
			}
		} else if (mode == "function") {
			var data = formData;
			eval("var f=" + content);
			if (f(data) === true) {
				return relationItem;
			}
		} else if (mode == "functionName") {
			var data = formData;
			if (eval(content + "(data)") === true) {
				return relationItem;
			}
		}
	}
	return null;
}

var panelZIndex = 6;

/**
 * 包装 jquery.easyui.dialog
 */

function onDialogMove(x,y) {
	//alert("(left,top)==>("+x+","+y+")");
	if (y<0) {
		$("#div_for_easyui_window").dialog('move',{left:x, top:0});
	}
}

function showDialog(options){
	if ($("#div_for_easyui_window")){
		$("#div_for_easyui_window").dialog('destroy');
	};
	//隐藏最小化、收起、最大化按钮
	options = $.extend(options, {collapsible: false, minimizable : false, maximizable: false, resizable: true});
	//标题处理
	if(options.title == null || options.title == '') {
		options.title = '.'; 
	}
	//移动事件
	if(options.onMove == null || options.onMove == '') {
		options.onMove = onDialogMove; 
	}
	//自适应大小
	var clientWidth = $(this).width();
	var clientHeight = $(this).height();
	if(options.width && options.width > clientWidth-15) {
		options.width = clientWidth-15; 
	} else if (!options.width) {
		options.width = clientWidth-15;
	}
	if(options.height && options.height > clientHeight-20) {
		options.height = clientHeight-20; 
	} else if (!options.height) {
		options.height = clientHeight-20;
	}
	var href = options.href;
	options.href = '';
	if(href == null || href == '') {
		href = options.url; 
	}
	if(href) {
		var date = new Date();
		if(href.indexOf('?') != -1) {
			href = href + '&ms_=' + date.getTime();
		} else {
			href = href + '?ms_=' + date.getTime();
		}
		$("<div id='div_for_easyui_window'><iframe class='iframe_for_easyui_window' frameborder='0' src='"+href+"'></iframe></div>").dialog(options);
	}
}

/**
 * config: title,url,width,height
 */
function showModalDialog(config) {
	var title = config.title;
	var url = config.url;
	var width = config.width;
	var height = config.height;
	showDialog({
		title: title, 
		url: url, 
		width: width, 
		height: height,
		modal:true
	});
}

function closeDialog(options){
	if ($("#div_for_easyui_window")){
		$("#div_for_easyui_window").dialog('destroy');
	};
}

function triggerShowModalDialog(config) {
	if (top && top.putTabIfAbsent) {
		top.putTabIfAbsent(config.title.replace("列表", ""), config.url);
	} else {
		showModalDialog(config);
	}
}

function showAlert(msg, callback, width, height){
	var title = "提示信息";
	var icon = "";
	
	$.messager.alert(title, msg, icon, callback);
}

function showSuccess(msg, callback, width, height){
	var title = "成功信息";
	var icon = "info";
	
	$.messager.alert(title, msg, icon, callback);
}

function showError(msg, callback, width, height){
	var title = "错误信息";
	var icon = "error";
	
	$.messager.alert(title, msg, icon, callback);
}

function showWarning(msg, callback, width, height){
	var title = "警告信息";
	var icon = "warning";
	
	$.messager.alert(title, msg, icon, callback);
}

function showConfirm(msg, callback, width, height){
	var title = "确认信息";
	
	$.messager.confirm(title, msg, function(r){
		if (r){
			if (callback) {
				callback();
			}
		}
	});
}

function objectReplace(text, obj) {
	for (var key in obj) {
		if (key == "0") {
			console.log("ggg");
		}
		var regExp = new RegExp("{" + key + "}");
		regExp.global = true;
		text = text.replace(regExp, obj[key]);
	}
	return text;
}

/**
 * 配置demo:
 * {
 * 	sync: true | false,
 * 	method: GET | POST,
 * 	params: post data,object type,eg:{"name": "xxxx"}
 * 	callback: success callback function,
 *  failCallback: fail callback function,
 * }
 */
function ajaxRequest(option){
	var dofailure=option.failCallback;
	var doError=option.doError;// 用户自定义 出错后处理的方法
	var callback=option.callback;
	
	var paramData = {};
	if (option.params) {
		for (var k in option.params) {
			if (typeof(option.params[k]) == "object") {
				paramData[k] = stringifyJSON(option.params[k]);
			} else {
				paramData[k] = option.params[k];
			}
		}
	}
	option.data = paramData;
	
	option.async = option.sync !== undefined ? option.sync : false,
	option.cache=option.cache||false;
	option.type=option.method||"POST";
	option.dataType=option.dataType||"json";
	option.success=function(data){
		if (data.success === false) {
			if (option.failCallback) {
				option.failCallback(data);
			} else {
				showError(data.message);
			}
		} else {
			if (option.callback) {
				option.callback(data);
			}
		}
	};
	option.error=function(result){
		if(doError && $.isFunction(doError)){
			doError(result);
			return;
		}
		showError("ajax请求失败");
		return;
	};
	$.ajax(option);
}

function g_setMasterFormFieldStatus(status) {
	if (status == "view") {
		for (var key in g_masterFormFieldDict) {
			
		}
	}
}

function isNumber(value) {
	return /^-?\d*(\.\d*)?$/.test(value);
}

function getFormJsonData(formName) {
	var form = document.forms[formName];
	return getChildFormFieldValueMap(form);
}

function getChildFormFieldValueMap(elem, seperator) {
	if (!seperator) {
		seperator = ",";
	}
	var result = {};
	var inputLi = elem.getElementsByTagName("input");
	for (var i = 0; i < inputLi.length; i++) {
		if (inputLi[i].type.toLowerCase() == "text" || inputLi[i].type.toLowerCase() == "hidden") {
			var name = inputLi[i].name;
			putOrAppend(result, inputLi[i].name, inputLi[i].value, seperator);
		} else if (inputLi[i].type.toLowerCase() == "radio") {
			if (inputLi[i].checked) {
				putOrAppend(result, inputLi[i].name, inputLi[i].value, seperator);
			}
		} else if (inputLi[i].type.toLowerCase() == "checkbox") {
			if (inputLi[i].checked) {
				putOrAppend(result, inputLi[i].name, inputLi[i].value, seperator);
			}
		}
	}
	
	var selectLi = elem.getElementsByTagName("select");
	for (var i = 0; i < selectLi.length; i++) {
		var name = selectLi[i].name;
		putOrAppend(result, selectLi[i].name, selectLi[i].value, seperator);
	}
	
	var textareaLi = elem.getElementsByTagName("textarea");
	for (var i = 0; i < textareaLi.length; i++) {
		var name = textareaLi[i].name;
		putOrAppend(result, textareaLi[i].name, textareaLi[i].value, seperator);
	}
	
	return result;
}

function getCheckboxValue(checkboxName) {
	var seperator = ",";
	var result = {};
	var inputLi = document.getElementsByTagName("input");
	for (var i = 0; i < inputLi.length; i++) {
		if (inputLi[i].name == checkboxName && inputLi[i]["type"].toLowerCase() == "radio") {
			if (inputLi[i].checked) {
				putOrAppend(result, inputLi[i].name, inputLi[i].value, seperator);
			}
		} else if (inputLi[i].type.toLowerCase() == "checkbox") {
			if (inputLi[i].checked) {
				putOrAppend(result, inputLi[i].name, inputLi[i].value, seperator);
			}
		}
	}
	return result[checkboxName] || "";
}

function putOrAppend(dictObj, name, value, seperator) {
	if (dictObj[name] !== undefined) {
		if (seperator) {
			dictObj[name] += seperator + value;
		} else {
			dictObj[name] += "," + value;
		}
	} else {
		dictObj[name] = value || "";
	}
}

function openTabOrJump(url) {
	if (top && top.putTabIfAbsent) {
		var name = listTemplate.description.replace("列表", "");
		var isRefresh = true;
		top.putTabIfAbsent(name, url, isRefresh);
	} else {
		location.href = url;
	}
}
