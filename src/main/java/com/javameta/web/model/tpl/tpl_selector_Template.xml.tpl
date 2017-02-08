<?xml version="1.0" encoding="UTF-8"?>
<!-- 
<form-template xmlns="https://github.com/hongjinqiu/javameta/template" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="https://github.com/hongjinqiu/javameta/template https://raw.githubusercontent.com/hongjinqiu/javameta/master/src/main/java/schema/template.xsd">
 -->
<form-template xmlns="https://github.com/hongjinqiu/javameta/template" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<id>{template}Selector</id>
	<cookie name="{template}" />
	<data-source-model-id>{template}</data-source-model-id>

	<adapter name="com.javameta.model.adapter.FormTemplateAdapter" />
	<description>银行账户列表</description>
	<scripts></scripts>
	<view-template view="model/selectorSchema.jsp" />

	<!-- <security byUnit="true" /> -->

	<data-provider name="queryDataSetA" size="10">
		<sql>
			select 
			{asNameLi}
			a.* from {tableName} a
			where 1=1
		</sql>
		<query-parameters dataSetId="A">
			<query-parameter name="id" auto="true" text="" editor="hiddenfield"></query-parameter>
			{queryParameterLi}
		</query-parameters>
	</data-provider>

	<toolbar name="toolbar" export="true">
		<button text="新增" mode="url" handler="/schema/formschema.do?@name={template}" iconCls="but_box" />
	</toolbar>

	<column-model name="A_1" dataSetId="A" dataProvider="queryDataSetA" selectionMode="checkbox" rownumber="true" selectionTitle="已选{description}" selectionTemplate="{id}改我,{id}改我" sqlOrderBy="id desc">
		<checkbox-column name="checkboxSelect" hideable="false">
			<expression></expression>
		</checkbox-column>
		<id-column name="id" text="编号" hideable="true" />

		<virtual-column name="FUN_C" text="操作" width="120">
			<buttons>
				<button name="btn_view" text="查看" iconCls="img_look" mode="url" handler="/schema/formschema.do?@name={template}&amp;id={id}&amp;formStatus=view">
				</button>
			</buttons>
		</virtual-column>

		{autoColumnLi}
	</column-model>

</form-template>
