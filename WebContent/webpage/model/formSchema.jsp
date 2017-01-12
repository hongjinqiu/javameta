<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据源模型控制台</title>
<link rel="stylesheet" type="text/css" href="${webRoot}/webpage/css/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${webRoot}/webpage/css/themes/icon.css">
<script type="text/javascript" src="${webRoot}/webpage/js/jquery.min.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/js/stringifyjson.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/js/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	var sysParam = {};
</script>
<script type="text/javascript" src="${webRoot}/webpage/${formTemplate.scripts}"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/common.js"></script>
<script type="text/javascript" src="${webRoot}/webpage/model/js/dataTableExtend.js"></script>
<!-- 
<script type="text/javascript" src="

/app/comboview?
js/moduleConfig.js							finish,直接跳过
js/common.js								finish,
js/dataTableExtend.js						finish,但是还是需要大量修改,
js/dataTableDataSourceExtend.js				finish,但是还是需要结合easyui做大量修改,				
js/columnManager.js							finish,
js/columnDataSourceManager.js				finish,
js/ds_formtoolbar.js						finish,
js/datasourceService.js						finish,
js/datasourceFactory.js						finish,
js/ds_formFieldFactory.js					finish,
	getRowIndex	[row]	Return the specified row index, the row parameter can be a row record or an id field value.
	selectRow	index	Select a row, the row index start with 0.
	selectRecord	idValue	Select a row by passing id value parameter.
	
	unselectRow	index	Unselect a row.
	
	返回PTextField之类的,那么,能不能返回easyui的实现呢?
js/formTemplateFactory.js					finish,
js/defaultAction.js							finish,
js/relationManager.js						finish,
js/listTemplateService.js					finish,相应的方法转移到templateService.js里面,这个类没用了,
js/templateService.js						finish,
	listTemplateService.js里面的东东是要干掉的,需要把相应的方法和值给弄到,templateService.js里面去,
js/columnSequenceService.js					finish,
js/formField.js								finish,
js/formTriggerField.js						finish,
js/formManager.js							finish,
dt.getRecord的相关修改,						finish,
g_gridPanelDict[dataSet.id].dt				finish,
	再整合一个,整合r-trigger-field到trigger-field中,		finish,
	单选时,有查看按钮,多选时,没有查看按钮,

"></script>
 -->
<script type="text/javascript">
	var g_dataBo = ${dataBoJson};
	var g_formTemplateJsonData = ${formTemplateJsonData};
	<c:if test="${not empty datasourceJson}">
		var g_datasourceJson = ${datasourceJson};
	</c:if>
	<c:if test="${empty datasourceJson}">
		var g_datasourceJson = null;
	</c:if>
	var gatheringFormTemplateJsonData = ${gatheringFormTemplateJsonData};
	
	
	var g_popupFormField = {};// 用于表格弹出时,存放弹出框控件数据引用,
	
	$.extend($.fn.validatebox.defaults.rules, {
		alwaysTrue: {
	        validator: function(value,param){
	        	console.log("always true");
	        	console.log(this);
	        	$(this).validatebox("options").invalidMessage = Math.random() + "<br />aaaa<br />eeee";
	        	//$(this).validatebox("options").required = true;
	        	//$(this).validatebox({invalidMessage: Math.random() + ""});
	            return false;
	        }
	    },
	    alwaysFalse: {
	        validator: function(value,param){
	        	console.log("always false");
	        	console.log(this);
	        	//$(this).validatebox({invalidMessage: Math.random() + ""});
	            return false;
	        }
	    }
	});
	$(document).ready(function(){
		$("#i_a").validatebox({
			validType: "alwaysTrue",
			invalidMessage: "test222"
		});
		$('#nn').numberbox({
		    min:0,
		    prefix:"$",
		    precision:"3",
		    decimalSeparator: ".",
		    groupSeparator: ",",
		    suffix: "%"
		    /* self.set("decimalSeparator", ".");
		self.set("groupSeparator", "");
		self.set("suffix", "%"); */
		});
		$('#dd').datebox({
		    required:true,
		    formatter: function(date) {
				var y = date.getFullYear();
				var m = date.getMonth()+1;
				if (m < 10) {
					m = "0" + m;
				}
				var d = date.getDate();
				if (d < 10) {
					d = "0" + d;
				}
				return y + "/" + m + "/" + d;
			}
		});
		$('#dt').datetimebox({
		    value: '3/4/2010 2:3',
		    required: true,
		    showSeconds: true
		});
		test();
	});
	
	function test() {
		var testValue = "testBy";
		/* 
		window.s_f = function(testValue) {
			return function(){
				console.log("test2_" + testValue);
			}
		}(testValue);
		*/
		$("#myTestText").change(function() {
			console.log("test1_" + testValue);
		});
	}
	
	//window.s_f();
</script>
</head>

<body>
ddddddddddddddd_wwwwwwwwwww
eeeeeea
dd
<input id="i_a" type="text" name="name1" value="name1" />
<br />
<input type="text" id="nn" />
<br />
<input id="dd" type="text" />
<br />
<input id="dt" type="text" name="birthday">
<br />
<input id="myTestText" type="text" name="myTestText" />
</body>
</html>
