<?xml version="1.0" encoding="UTF-8"?>
<!-- 
<form-template xmlns="https://github.com/hongjinqiu/javameta/template" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="https://github.com/hongjinqiu/javameta/template https://raw.githubusercontent.com/hongjinqiu/javameta/master/src/main/java/schema/template.xsd">
 -->
<form-template xmlns="https://github.com/hongjinqiu/javameta/template" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<id>{template}</id>
	<data-source-model-id>{template}</data-source-model-id>
	<adapter name="com.javameta.model.adapter.FormTemplateAdapter" />
	<description>{description}表单</description>
	<scripts>demo/{lowerTemplate}/{lowerTemplate}Model.js</scripts>
	<view-template view="model/formSchema.jsp"/>
	<!-- <security byUnit="true" /> -->

	<toolbar name="toolbar">
		<button name="listBtn" text="列表页" mode="url" handler="/schema/listschema.do?@name={template}" iconCls="but_box"></button>
		<button name="newBtn" text="新增" mode="fn" handler="newData" iconCls="but_box"></button>
		<button name="copyBtn" text="复制" mode="fn" handler="copyData" iconCls="but_box"></button>
		<button name="editBtn" text="修改" mode="fn" handler="editData" iconCls="but_box"></button>
		<button name="saveBtn" text="保存" mode="fn" handler="saveData" iconCls="but_box"></button>
		<button name="giveUpBtn" text="放弃" mode="fn" handler="giveUpData" iconCls="but_box"></button>
		<button name="delBtn" text="删除" mode="fn" handler="deleteData" iconCls="but_box"></button>
		<button name="refreshBtn" text="刷新" mode="fn" handler="refreshData" iconCls="but_box"></button>
		<button name="usedQueryBtn" text="被用查询" mode="fn" handler="logList" iconCls="but_box"></button>
	</toolbar>
	
	<data-provider name="queryDataSetA" size="10">
		<sql>
			select 
			{asNameLi}
			a.* from {tableName} a
			where 1=1
 		</sql>
 		<query-parameters dataSetId="A">
 			<query-parameter name="id" auto="true" text=""></query-parameter>
 		</query-parameters>
	</data-provider>
	
	{detailDataProviderLi}

	<column-model name="A_1" dataSetId="A" colSpan="6" dataProvider="queryDataSetA">
		<id-column name="id" text="编号" hideable="true" />

		{autoColumnLi}
	</column-model>

	{detailColumnModelLi}
</form-template>
