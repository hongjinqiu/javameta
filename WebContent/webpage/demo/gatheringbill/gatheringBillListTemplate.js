var listTemplateExtraInfo = {
	"columnModel" : {

	},
	"queryParameter" : {
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
		},
		"billNo" : {
			listeners : {
				change : function(newValue, oldValue, formObj) {
					console.log("running, billNo-------------------");
				}
			}
		},
		"billDateBegin": {
			listeners : {
				change : function(newValue, oldValue, formObj) {
					console.log("running, billDateBegin-------------------");
				}
			}
		}
	}
};

function main() {
	listTemplateExtraInfo.queryParameter.property.listeners.change(null, null, g_masterFormFieldDict["property"]);
}
