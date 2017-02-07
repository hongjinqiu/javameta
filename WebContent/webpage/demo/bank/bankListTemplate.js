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
		},
		numberfield7: {
			listeners : {
				change : function(newValue, oldValue, formObj) {
					console.log("numer7");
				}
			}
		},
		datefield10: {
			listeners : {
				change : function(newValue, oldValue, formObj) {
					console.log("dateField10");
				}
			}
		}
	}
};

function main() {
	
}
