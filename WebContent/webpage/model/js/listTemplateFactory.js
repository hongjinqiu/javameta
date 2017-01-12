function ListTemplateFactory() {
}

/**
 * 扩展listTemplate
 */
ListTemplateFactory.prototype.extendListTemplate = function(listTemplate, modelExtraInfo) {
	var formTemplateIterator = new FormTemplateIterator();
	var result = "";
	var queryParameterConfig = listTemplateExtraInfo["queryParameter"];
	formTemplateIterator.iterateAllTemplateQueryParameter(result, function(queryParameter, result){
		if (queryParameterConfig[queryParameter.name]) {
			for (var key in queryParameterConfig[queryParameter.name]) {
				if (!queryParameter.jsConfig) {
					queryParameter.jsConfig = {};
				}
				queryParameter.jsConfig[key] = queryParameterConfig[queryParameter.name][key];
			}
		}
	});
}
