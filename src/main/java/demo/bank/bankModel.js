var modelExtraInfo = {
		
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
					url: "/" + firstLower(g_datasourceJson.id) + "/getData?format=json"
					,params: {
						"datasourceModelId": g_datasourceJson.Id,
						"formTemplateId": g_formTemplateJsonData.Id,
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
				url: "/" + firstLower(g_datasourceJson.id) + "/newData?format=json"
				,params: {
					"datasourceModelId": g_datasourceJson.Id,
					"formTemplateId": g_formTemplateJsonData.Id
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
