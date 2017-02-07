var modelExtraInfo = {
	"A" : {
		"property" : {
			listeners : {
				change : function(newValue, oldValue, formObj) {
					if (formObj.get("value") == "" || formObj.get("value") == "3") {// 空(请选择),4:其他
						g_masterFormFieldDict["accountId"].set("readonly", true);
					} else {
						g_masterFormFieldDict["accountId"].set("readonly", false);
					}
				}
			}
		}
	},
	"B" : {
		"accountType" : {
			listeners : {
				change : function(newValue, oldValue, formObj) {
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
	}
};

function main() {
	if (g_id) {
		if (g_copyFlag == "true") {// 复制
			ajaxRequest({
				url: "/" + firstLower(g_datasourceJson.id) + "/copyData.do?format=json"
				,params: {
					"datasourceModelId": g_datasourceJson.id,
					"formTemplateId": g_formTemplateJsonData.id,
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
				url : "/" + firstLower(g_datasourceJson.id) + "/getData.do?format=json",
				params : {
					"datasourceModelId" : g_datasourceJson.id,
					"formTemplateId" : g_formTemplateJsonData.id,
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
			url : "/" + firstLower(g_datasourceJson.id) + "/newData.do?format=json",
			params : {
				"datasourceModelId" : g_datasourceJson.id,
				"formTemplateId" : g_formTemplateJsonData.id
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
		modelExtraInfo.A.property.listeners.change(null, null, g_masterFormFieldDict["property"]);
	}
}
