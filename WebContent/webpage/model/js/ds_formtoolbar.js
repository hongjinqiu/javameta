function getIdUrl(id) {
	return id.substring(0,1).toLowerCase() + id.substring(1);
}

function editData() {//修改
	var formManager = new FormManager();
	var bo = formManager.getBo();
	ajaxRequest({
		url: "/" + getIdUrl(g_datasourceJson.id) + "/editData?format=json"
		,params: {
			"datasourceModelId": g_datasourceJson.id,
			"formTemplateId": g_formTemplateJsonData.id,
			"id": bo["id"]
		},
		callback: function(o) {
			formManager.applyGlobalParamFromAjaxData(o);
			formManager.loadData2Form(g_datasourceJson, o.bo);
			formManager.setFormStatus("edit");
		}
	});
}

function saveData() {//保存
	var formManager = new FormManager();
	var bo = formManager.getBo();
	var validateResult = formManager.dsFormValidator(g_datasourceJson, bo);
	
	if (!validateResult.result) {
		showError(validateResult.message);
	} else {
		ajaxRequest({
			url: "/" + getIdUrl(g_datasourceJson.id) + "/saveData?format=json"
			,params: {
				"datasourceModelId": g_datasourceJson.id,
				"formTemplateId": g_formTemplateJsonData.id,
				"jsonData": bo
			},
			callback: function(o) {
				showSuccess("保存数据成功");
				formManager.setFormStatus("view");
				formManager.applyGlobalParamFromAjaxData(o);
				formManager.loadData2Form(g_datasourceJson, o.bo);
			}
		});
	}
}

function newData() {
	var formManager = new FormManager();
	var bo = formManager.getBo();
	ajaxRequest({
		url: "/" + getIdUrl(g_datasourceJson.id) + "/newData?format=json"
		,params: {
			"datasourceModelId": g_datasourceJson.id,
			"formTemplateId": g_formTemplateJsonData.id
		},
		callback: function(o) {
			formManager.setDetailIncId(g_datasourceJson, o.bo);
			formManager.applyGlobalParamFromAjaxData(o);
			formManager.loadData2Form(g_datasourceJson, o.bo);
			formManager.setFormStatus("edit");
		}
	});
}

function copyData() {
	var formManager = new FormManager();
	var bo = formManager.getBo();
	ajaxRequest({
		url: "/" + getIdUrl(g_datasourceJson.id) + "/copyData?format=json"
		,params: {
			"datasourceModelId": g_datasourceJson.id,
			"formTemplateId": g_formTemplateJsonData.id,
			"id": bo["id"]
		},
		callback: function(o) {
			formManager.setDetailIncId(g_datasourceJson, o.bo);
			formManager.applyGlobalParamFromAjaxData(o);
			formManager.loadData2Form(g_datasourceJson, o.bo);
			formManager.setFormStatus("edit");
		}
	});
}

function giveUpData() {
	var formManager = new FormManager();
	var bo = formManager.getBo();
	showConfirm("您确定要放弃吗？", function(){
		if (!bo["id"] || bo["id"] == "0") {
			location.href = webRoot + "/schema/listschema.do?@name=" + g_datasourceJson.id;
		} else {
			ajaxRequest({
				url: "/" + getIdUrl(g_datasourceJson.id) + "/giveUpData?format=json"
				,params: {
					"datasourceModelId": g_datasourceJson.id,
					"formTemplateId": g_formTemplateJsonData.id,
					"id": bo["id"]
				},
				callback: function(o) {
					formManager.applyGlobalParamFromAjaxData(o);
					formManager.loadData2Form(g_datasourceJson, o.bo);
					formManager.setFormStatus("view");
				}
			});
		}
	});
}

function deleteData() {
	showConfirm("您确定要删除吗？", function(){
		var formManager = new FormManager();
		var bo = formManager.getBo();
		ajaxRequest({
			url: "/" + getIdUrl(g_datasourceJson.id) + "/deleteData?format=json"
			,params: {
				"datasourceModelId": g_datasourceJson.id,
				"formTemplateId": g_formTemplateJsonData.id,
				"id": bo["id"]
			},
			callback: function(o) {
				location.href = webRoot + "/schema/listschema.do?@name=" + g_datasourceJson.id;
			}
		});
	})
}

function refreshData() {
	var formManager = new FormManager();
	var bo = formManager.getBo();
	ajaxRequest({
		url: "/" + getIdUrl(g_datasourceJson.id) + "/refreshData?format=json"
		,params: {
			"datasourceModelId": g_datasourceJson.id,
			"formTemplateId": g_formTemplateJsonData.id,
			"id": bo["id"]
		},
		callback: function(o) {
			formManager.applyGlobalParamFromAjaxData(o);
			formManager.loadData2Form(g_datasourceJson, o.bo);
			formManager.setFormStatus("view");
		}
	});
}

function logList() {
	var formManager = new FormManager();
	var bo = formManager.getBo();
	var dialog = showModalDialog({
		"title": "被用查询",
		"url": webRoot + "/schema/listschema.do?@name=PubReferenceLog&beReferenceDatasourceModelId=" + g_datasourceJson.id + "&beReferenceId=" + bo["id"] + "&date=" + new Date()
	});
}

function cancelData() {
	var formManager = new FormManager();
	var bo = formManager.getBo();
	ajaxRequest({
		url: "/" + getIdUrl(g_datasourceJson.id) + "/cancelData?format=json"
		,params: {
			"datasourceModelId": g_datasourceJson.id,
			"formTemplateId": g_formTemplateJsonData.id,
			"id": bo["id"]
		},
		callback: function(o) {
			showSuccess("作废数据成功");
			formManager.applyGlobalParamFromAjaxData(o);
			formManager.loadData2Form(g_datasourceJson, o.bo);
			formManager.setFormStatus("view");
		}
	});
}

function unCancelData() {
	var formManager = new FormManager();
	var bo = formManager.getBo();
	ajaxRequest({
		url: "/" + getIdUrl(g_datasourceJson.id) + "/unCancelData?format=json"
		,params: {
			"datasourceModelId": g_datasourceJson.id,
			"formTemplateId": g_formTemplateJsonData.id,
			"id": bo["id"]
		},
		callback: function(o) {
			showSuccess("反作废数据成功");
			formManager.applyGlobalParamFromAjaxData(o);
			formManager.loadData2Form(g_datasourceJson, o.bo);
			formManager.setFormStatus("view");
		}
	});
}

/*
function getData() {
	ajaxRequest({
		url: "/ActionTest/getData?format=json"
		,params: {
			"id": 26,
			"datasourceModelId": "ActionTest"
		},
		callback: function(o) {
			console.log(o);
			showSuccess(o.responseText);
		}
	});
}
*/
function test() {
	var relationManager = new RelationManager();
	relationManager.getRelationBo("SysUserSelector", 16);
	return;
}

function ToolbarManager(){}

function setBorderTmp(btn, status) {
//	if (true) {
//		return;
//	}
	if (status == "disabled") {
		if (!btn.origClassName) {
			btn.origClassName = btn.className;
		}
//		btn.style.border = "1px solid black";
		btn.className = "disable_but_box";
	} else {
//		btn.style.border = "1px solid red";
		if (btn.className == "disable_but_box") {
			btn.className = btn.origClassName;
		}
	}
}

ToolbarManager.prototype.enableDisableToolbarBtn = function() {
	if (g_formStatus == "view") {
		var viewEnableBtnLi = ["listBtn","newBtn","copyBtn","refreshBtn","usedQueryBtn"];
		var viewDisableBtnLi = ["saveBtn","giveUpBtn"];
		
		// delBtn,
		var isUsed = false;
		if (g_usedCheck) {
			if (g_usedCheck["A"]) {
				var id = g_masterFormFieldDict["id"].get("value");
				if (g_usedCheck["A"][id]) {
					isUsed = true;
				}
			}
		}
		if (isUsed) {
			viewDisableBtnLi.push("delBtn");
		} else {
			viewEnableBtnLi.push("delBtn");
		}
		
		
		for (var i = 0; i < viewEnableBtnLi.length; i++) {
			var btn = document.getElementById(viewEnableBtnLi[i]);
			if (btn) {
				btn.disabled = "";
				setBorderTmp(btn, "");
			}
		}
		
		for (var i = 0; i < viewDisableBtnLi.length; i++) {
			var btn = document.getElementById(viewDisableBtnLi[i]);
			if (btn) {
				btn.disabled = "disabled";
				setBorderTmp(btn, "disabled");
			}
		}
	} else {
		var editEnableBtnLi = ["listBtn","saveBtn","giveUpBtn"];
		var editDisableBtnLi = ["newBtn","copyBtn","editBtn","delBtn","refreshBtn","usedQueryBtn"];
		for (var i = 0; i < editEnableBtnLi.length; i++) {
			var btn = document.getElementById(editEnableBtnLi[i]);
			if (btn) {
				btn.disabled = "";
				setBorderTmp(btn, "");
			}
		}
		for (var i = 0; i < editDisableBtnLi.length; i++) {
			var btn = document.getElementById(editDisableBtnLi[i]);
			if (btn) {
				btn.disabled = "disabled";
				setBorderTmp(btn, "disabled");
			}
		}
	}
}





