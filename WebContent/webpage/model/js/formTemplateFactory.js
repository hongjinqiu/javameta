function FormTemplateFactory() {
}

/**
 * 扩展listTemplate
 */
FormTemplateFactory.prototype.extendFormTemplate = function(modelExtraInfo) {
	var formTemplateIterator = new FormTemplateIterator();
	if (modelExtraInfo.buttonConfig) {
		for (var key in modelExtraInfo.buttonConfig) {
			formTemplateIterator.iterateAnyTemplateButton(result, function(toolbarOrColumnModel, button, result){
				if (button.name == key) {
					if (!button.jsConfig) {
						button.jsConfig = {};
					}
					for (var item in modelExtraInfo.buttonConfig[key]) {
						button.jsConfig[item] = modelExtraInfo.buttonConfig[key][item];
					}
					return true;
				}
				return false;
			});
		}
	}
}
