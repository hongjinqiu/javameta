var modelExtraInfo = {
	"A" : {
		"code" : {
			selectorName : "",// 可以为函数
			listeners: {
			}
		}
	}
};

function main(Y) {
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
					url: "/" + firstLower(g_datasourceJson.id) + "/getData.do?format=json"
					,params: {
						"datasourceModelId": g_datasourceJson.id,
						"formTemplateId": g_formTemplateJsonData.id,
						"id": g_id
					},
					callback: function(o) {
						var formManager = new FormManager();
						formManager.applyGlobalParamFromAjaxData(o);
						formManager.loadData2Form(g_datasourceJson, o.bo);
						formManager.setFormStatus(g_formStatus);
					}
				});
			}
		} else {
			ajaxRequest({
				url: "/" + firstLower(g_datasourceJson.id) + "/newData.do?format=json"
				,params: {
					"datasourceModelId": g_datasourceJson.id,
					"formTemplateId": g_formTemplateJsonData.id
				},
				callback: function(o) {
					var formManager = new FormManager();
					formManager.applyGlobalParamFromAjaxData(o);
					formManager.loadData2Form(g_datasourceJson, o.bo);
					formManager.setFormStatus(g_formStatus);
				}
			});
		}
	}
