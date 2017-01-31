var modelExtraInfo = {
		
};

function main(Y) {
		if (g_id) {
			if (g_copyFlag == "true") {// 复制
				ajaxRequest({
					url: "/" + g_dataSourceJson.Id + "/copyData?format=json"
					,params: {
						"dataSourceModelId": g_dataSourceJson.Id,
						"formTemplateId": g_formTemplateJsonData.Id,
						"id": g_id
					},
					callback: function(o) {
						var formManager = new FormManager();
						formManager.setDetailIncId(g_dataSourceJson, o.bo);
						formManager.applyGlobalParamFromAjaxData(o);
						formManager.loadData2Form(g_dataSourceJson, o.bo);
						formManager.setFormStatus("edit");
					}
				});
			} else {
				ajaxRequest({
					url: "/" + g_dataSourceJson.Id + "/getData?format=json"
					,params: {
						"dataSourceModelId": g_dataSourceJson.Id,
						"formTemplateId": g_formTemplateJsonData.Id,
						"id": g_id
					},
					callback: function(o) {
						var formManager = new FormManager();
						formManager.applyGlobalParamFromAjaxData(o);
						formManager.loadData2Form(g_dataSourceJson, o.bo);
						formManager.setFormStatus(g_formStatus);
					}
				});
			}
		} else {
			ajaxRequest({
				url: "/" + g_dataSourceJson.Id + "/newData?format=json"
				,params: {
					"dataSourceModelId": g_dataSourceJson.Id,
					"formTemplateId": g_formTemplateJsonData.Id
				},
				callback: function(o) {
					var formManager = new FormManager();
					formManager.applyGlobalParamFromAjaxData(o);
					formManager.loadData2Form(g_dataSourceJson, o.bo);
					formManager.setFormStatus(g_formStatus);
				}
			});
		}
	}
