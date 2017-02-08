/*
// example
var modelExtraInfo = {
	"A" : {// 主数据集字段没有defaultValueExprForJs,calcValueExprForJs函数,如果需要自定义值,在服务端afterNewData方法中添加
		"property" : {
			listeners : {
				change : function(newValue, oldValue, formObj) {// 值变化, formObj 当前这个element对应的PTextField,PSelectField,......
				}
			},
			selectFunc: function(selectValueLi, formObj) {// ,弹窗点确定按钮后的回调,没有返回值
			},
			unSelectFunc: function(formObj) {// ,点击删除按钮触发,没有返回值
			},
			queryFunc: function(formObj) {// ,return {},开窗回选函数,
				return {};
			}
		}
	},
	"B" : {
		"accountType" : {
			listeners : {
				change : function(newValue, oldValue, formObj) {// 对应弹出编辑框,值变化, formObj 当前这个element对应的PTextField,PSelectField,......
				}
			},
			defaultValueExprForJs: function(bo, data) {// return "";
			},
			calcValueExprForJs: function(bo, data) {// return "";
			},
			selectFunc: function(selectValueLi, formObj) {// ,弹窗点确定按钮后的回调,没有返回值
			},
			unSelectFunc: function(formObj) {// ,点击删除按钮触发,没有返回值
			},
			queryFunc: function(formObj) {// ,return {},开窗回选函数,
				return {};
			}
		},
		afterNewData: function(bo, data) {// 没有返回值,newData,copyData都会调用这个函数,
		},
		validateEdit: function(dataSetDataLi) {// dataSetDataLi: [{}, {}],返回validateEditMessageLi:['', ''],全部验证通过返回[],
			// dsFormValidator,dsDetailValidator,会调用这个函数
		}
	},
	validate : function(bo, masterMessageLi, detailMessageDict) {// masterMessageLi:[], detailMessageDict:{"B": [], "C": []},往message参数里面添加信息
		// dsFormValidator会调用这个函数,
		// 验证联动,业务属性
		if (bo.A.property == "1" || bo.A.property == "2") {
			var accountIdValue = g_masterFormFieldDict["accountId"].get("value");
			if (!accountIdValue || accountIdValue == "0") {
				masterMessageLi.push("收款账户不允许为空");
				g_masterFormFieldDict["accountId"].set("error", "不允许为空");
			}
		}
	},
	buttonConfig: {// 按钮配置,toolbar/button,columnModel/toolbar,columnModel/editor-toolbar,columnModel/virtual-column/buttons/button
		selectRowBtn: {// key为button.name
			selectFunc: function(selectValueLi) {// ,弹窗点确定按钮后的回调,没有返回值
			},
			queryFunc: function() {// ,return {},开窗回选函数,
				return {};
			}
		}
	}
};
*/

var modelExtraInfo = {
	"A" : {
		
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
}
