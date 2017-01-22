var listTemplateExtraInfo = {
	"columnModel" : {

	},
	"queryParameter" : {
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
	}
};

function main(Y) {
	
		listTemplateExtraInfo.QueryParameter.property.listeners.change(null, g_masterFormFieldDict["property"]);
		listTemplateExtraInfo.QueryParameter.chamberlainType.listeners.change(null, g_masterFormFieldDict["chamberlainType"]);
}

