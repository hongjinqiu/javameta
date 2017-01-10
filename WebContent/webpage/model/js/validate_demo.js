var bsubmit=false;
$(document).ready(function(){
	if($("#SMS_TEMPLATE_ID").val()!=""){
		$("#titletxt").html("编辑短信模版");
	}
	$.validator.addMethod("checkChOrEn",function(value,element){
		return this.optional(element)||/^[\u4e00-\u9fa5|(a-zA-Z)]+$/i.test(value);
	});
	$("#duanxinmodelSave").validate({
		rules:{
			SMS_TEMPLATE_TYPE:{required:true},
			SMS_TEMPLATE_CODE:{required:true,validcharacter:true,rangelength:[0,12],remote:{
		        url:"duanxinmodelValid.go",
		        type:"post",
		        dataType:"json",
		        data:{
					SMS_TEMPLATE_ID:function(){return $("#SMS_TEMPLATE_ID").val();},
					SMS_TEMPLATE_CODE:function(){return $("#SMS_TEMPLATE_CODE").val();}
					}
		        }},
			SMS_TEMPLATE_NAME:{required:true,rangelength:[0,30],checkChOrEn:true},
			SMS_TEMPLATE_CONTENT:{required:true,rangelength:[0,200]}
		},
		messages:{
			SMS_TEMPLATE_TYPE:{required:"请输入模版类型"},
			SMS_TEMPLATE_CODE:{required:"请输入模版代码",validcharacter:"模板代码应为英文或数字",rangelength:"模板代码长度在{0}-{1}字符之间",remote:""},
			SMS_TEMPLATE_NAME:{required:"请输入模版名称",rangelength:"模板名称长度在{0}-{1}字符之间",checkChOrEn:"模板名称应为中文或英文"},
			SMS_TEMPLATE_CONTENT:{required:"请输入模版内容",rangelength:"模板内容长度在{0}-{1}字符之间"}
		},
		submitHandler:function(form){
			bsubmit=true;
            form.submit();
        }   
	});
	
	$("#submitbutton").click(function(){
		if (bsubmit==false){//判断是否已经提交过，提交过点保存按钮无效
			$("#duanxinmodelSave").submit();
		}else {
			parent.closeDialog();
		}
	});
	$("#cancel").click(function(){
		parent.renderPage();
		parent.closeDialog();
	});
});

