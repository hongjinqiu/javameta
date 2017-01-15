function doRefretorComponent() {
	doRefretor("Component");
}

function doRefretorSelector() {
	doRefretor("Selector");
}

function doRefretorForm() {
	doRefretor("Form");
}

function doRefretorDatasource() {
	doRefretor("Datasource");
}

function doRefretor(name) {
	var dtManager = g_gridPanelDict[name];
	var uri = webRoot + "/schema/refretor.do?type=" + name;
	ajaxRequest({
		url: uri
		,params: {
		},
		callback: function(o) {
			dtManager.dt.datagrid("loadData", o.data);
		}
	});
	
	/*
	YUI(g_financeModule).use("finance-module", function(Y){
		Y.on('io:complete', function(id, o, args) {
			var id = id; // Transaction ID.
			var data = Y.JSON.parse(o.responseText);
			dtManager.dt.set("data", data.items);
			dtManager.hideLoadingImg();
		}, Y, []);
		dtManager.showLoadingImg();
		var request = Y.io(uri);
	});
	*/
}

function main() {
	$('#tt').tabs({
	    border:false
	    /*,onSelect:function(title){
	        alert(title+' is selected');
	    }*/
	});
}



////////////////////////test
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
