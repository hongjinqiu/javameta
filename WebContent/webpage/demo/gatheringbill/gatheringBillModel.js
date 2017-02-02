var modelExtraInfo = {
	"A" : {
		"property" : {
			listeners : {
				change : function(newValue, oldValue) {
					if (formObj.get("value") == "" || formObj.get("value") == "3") {// 空(请选择),4:其他
						g_masterFormFieldDict["accountId"].set("readonly", true);
					} else {
						g_masterFormFieldDict["accountId"].set("readonly", false);
					}
				}
			}
		},
		"chamberlainType" : {
			listeners : {
				change : function(newValue, oldValue) {
					if (formObj.get("value") == "" || formObj.get("value") == "4") {// 空(请选择),4:其他
						g_masterFormFieldDict["chamberlainId"].set("readonly", true);
					} else {
						g_masterFormFieldDict["chamberlainId"].set("readonly", false);
					}
				}
			}
		}
	},
	"B" : {
		"accountType" : {
			listeners : {
				change : function(newValue, oldValue) {
					var formManager = new FormManager();
					var fieldDict = formManager.getFieldDict(formObj);
					if (fieldDict["accountId"]) {
						if (formObj.get("value") == "") {// 空(请选择)
							fieldDict["accountId"].set("readonly", true);
						} else {
							fieldDict["accountId"].set("readonly", false);
						}
					}
				}
			}
		}
	},
	validate : function(bo, masterMessageLi, detailMessageDict) {
		// 验证联动,
		// 业务属性
		if (bo.A.property == "1" || bo.A.property == "2") {
			var accountIdValue = g_masterFormFieldDict["accountId"].get("value");
			if (!accountIdValue || accountIdValue == "0") {
				masterMessageLi.push("收款账户不允许为空");
				g_masterFormFieldDict["accountId"].set("error", "不允许为空");
			}
		}
		// 收款对象类型
		if (bo.A.chamberlainType == "1" || bo.A.chamberlainType == "2" || bo.A.chamberlainType == "3") {
			var chamberlainIdValue = g_masterFormFieldDict["chamberlainId"].get("value");
			if (!chamberlainIdValue || chamberlainIdValue == "0") {
				masterMessageLi.push("收款对象不允许为空");
				g_masterFormFieldDict["chamberlainId"].set("error", "不允许为空");
			}
		}
	}
};

function main(Y) {
	if (g_id) {
		if (g_copyFlag == "true") {// 复制
			ajaxRequest({
				url: "/" + firstLower(g_datasourceJson.id) + "/copyData?format=json"
				,params: {
					"datasourceModelId": g_datasourceJson.Id,
					"formTemplateId": g_formTemplateJsonData.Id,
					"id": g_id
				},
				callback: function(o) {
					var formManager = new FormManager();
					formManager.setDetailIncId(g_datasourceJson, o.bo);
					formManager.applyGlobalParamFromAjaxData(o);
					formManager.loadData2Form(g_datasourceJson, o.bo);
					formManager.setFormStatus("edit");
				}
			});
		} else {
			ajaxRequest({
				url : "/" + firstLower(g_datasourceJson.id) + "/getData?format=json",
				params : {
					"datasourceModelId" : g_datasourceJson.Id,
					"formTemplateId" : g_formTemplateJsonData.Id,
					"id" : g_id
				},
				callback : function(o) {
					var formManager = new FormManager();
					formManager.applyGlobalParamFromAjaxData(o);
					formManager.loadData2Form(g_datasourceJson, o.bo);
					formManager.setFormStatus(g_formStatus);
				}
			});
		}
	} else {
		ajaxRequest({
			url : "/" + firstLower(g_datasourceJson.id) + "/newData?format=json",
			params : {
				"datasourceModelId" : g_datasourceJson.Id,
				"formTemplateId" : g_formTemplateJsonData.Id
			},
			callback : function(o) {
				var formManager = new FormManager();
				formManager.applyGlobalParamFromAjaxData(o);
				formManager.loadData2Form(g_datasourceJson, o.bo);
				formManager.setFormStatus(g_formStatus);
			}
		});
	}
	if (g_formStatus != "view") {
		modelExtraInfo.A.property.listeners.change(null, g_masterFormFieldDict["property"]);
		modelExtraInfo.A.chamberlainType.listeners.change(null, g_masterFormFieldDict["chamberlainType"]);
	}
}
