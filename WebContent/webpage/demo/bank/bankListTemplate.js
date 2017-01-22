var listTemplateExtraInfo = {
	"columnModel" : {

	},
	"queryParameter" : {
		"datefield8" : {
			listeners : {
				change : function(newValue, oldValue, formObj) {
//					g_masterFormFieldDict["accountId"].set("readonly", true);
					console.log(newValue);
					console.log(oldValue);
				}
			}
		},
		"code" : {
			listeners : {
				change : function(newValue, oldValue, formObj) {
//					g_masterFormFieldDict["accountId"].set("readonly", true);
					console.log(newValue);
					console.log(oldValue);
					console.log(formObj);
				}
			}
		}
	}
};

function main() {
	
}
