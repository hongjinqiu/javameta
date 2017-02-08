package com.javameta.web.model.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javameta.model.DatasourceFactory;
import com.javameta.model.datasource.Datasource;
import com.javameta.model.datasource.DetailData;
import com.javameta.model.datasource.Field;
import com.javameta.model.datasource.Field.RelationDS.RelationItem;
import com.javameta.model.iterate.DatasourceIterator;
import com.javameta.model.iterate.IDatasourceFieldIterate;
import com.javameta.model.iterate.IDatasourceLineFieldIterate;
import com.javameta.util.New;
import com.javameta.web.model.dao.SchemaDao;
import com.javameta.web.support.ServiceSupport;

@Scope("prototype")
@Service
@Transactional
public class SchemaService extends ServiceSupport {
	@Autowired
	private SchemaDao schemaDao;

	private String getFieldSql(Field primaryKey, Field field) {
		String line = "`{fieldName}` {fieldType} {isNull} {autoIncrement} COMMENT '{comment}'";
		String fieldName = StringUtils.isNotEmpty(field.getFieldName()) ? field.getFieldName() : field.getId();
		line = line.replace("{fieldName}", fieldName);
		String isNull = field.isAllowEmpty() ? "DEFAULT NULL" : "NOT NULL";
		line = line.replace("{isNull}", isNull);
		String autoIncrement = "";
		if (field == primaryKey) {
			autoIncrement = "AUTO_INCREMENT";
		}
		line = line.replace("{autoIncrement}", autoIncrement);
		String comment = field.getDisplayName();
		line = line.replace("{comment}", comment);
		if (field.getFieldType().equalsIgnoreCase("FLOAT")) {
			String fieldType = "double(" + field.getFieldLength() + "," + field.getDecimalPointLength() + ")";
			line = line.replace("{fieldType}", fieldType);
		}
		if (field.getFieldType().equalsIgnoreCase("DOUBLE")) {
			String fieldType = "double(" + field.getFieldLength() + "," + field.getDecimalPointLength() + ")";
			line = line.replace("{fieldType}", fieldType);
		}
		if (field.getFieldType().equalsIgnoreCase("DECIMAL")) {
			String fieldType = "decimal(" + field.getFieldLength() + "," + field.getDecimalPointLength() + ")";
			line = line.replace("{fieldType}", fieldType);
		}
		if (field.getFieldType().equalsIgnoreCase("SHORT")) {
			String fieldType = "int(" + field.getFieldLength() + ")";
			line = line.replace("{fieldType}", fieldType);
		}
		if (field.getFieldType().equalsIgnoreCase("INT")) {
			String fieldType = "int(" + field.getFieldLength() + ")";
			line = line.replace("{fieldType}", fieldType);
		}
		if (field.getFieldType().equalsIgnoreCase("LONG")) {
			String fieldType = "bigint(" + field.getFieldLength() + ")";
			line = line.replace("{fieldType}", fieldType);
		}
		if (field.getFieldType().equalsIgnoreCase("NULL")) {
		}
		if (field.getFieldType().equalsIgnoreCase("STRING")) {
			String fieldType = "varchar(" + field.getFieldLength() + ")";
			line = line.replace("{fieldType}", fieldType);
		}
		if (field.getFieldType().equalsIgnoreCase("DATE")) {
			String fieldType = "date";
			line = line.replace("{fieldType}", fieldType);
		}
		if (field.getFieldType().equalsIgnoreCase("TIME")) {
			String fieldType = "time";
			line = line.replace("{fieldType}", fieldType);
		}
		if (field.getFieldType().equalsIgnoreCase("TIMESTAMP")) {
			String fieldType = "datetime";
			line = line.replace("{fieldType}", fieldType);
		}
		if (field.getFieldType().equalsIgnoreCase("BYTES")) {
			String fieldType = "blob";
			line = line.replace("{fieldType}", fieldType);
		}
		return line;
	}

	/*
	CREATE TABLE `pub_diccomm` (
	`DICCOMM_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '内容序列ID',
	`DICLIST_ID` decimal(8,0) NOT NULL COMMENT '字典清单ID',
	`code` varchar(200) NOT NULL COMMENT '数据项代码',
	`name` varchar(200) NOT NULL COMMENT '数据项名称',
	`ITEMNAJP` varchar(200) DEFAULT NULL COMMENT '数据项简拼',
	`ITEMNAQP` varchar(1000) DEFAULT NULL COMMENT '数据项全拼',
	`DISPORDER` decimal(8,0) NOT NULL COMMENT '显示顺序',
	`ENABLED` decimal(8,0) NOT NULL COMMENT '是否启用(0未启用1已启用)',
	`NODED` decimal(8,0) NOT NULL COMMENT '是否有子节点(0没有1有)',
	`PARENTCODE` varchar(200) NOT NULL COMMENT '父数据项代码',
	PRIMARY KEY (`DICCOMM_ID`)
	) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='公共-数据字典内容表'
	 */
	public String getGenerateTableSql(final Datasource datasource) {
		final StringBuilder result = new StringBuilder();
		DatasourceIterator.iterateLineField(datasource, new IDatasourceLineFieldIterate() {
			@Override
			public void iterate(List<Field> fieldLi) {
				String tableName = null;
				Field pField = null;
				String comment = null;

				if (fieldLi.get(0).isMasterField()) {
					tableName = datasource.getCalcTableName();
					pField = datasource.getMasterData().getFixField().getPrimaryKey();
					comment = datasource.getDisplayName();
				} else {
					String dataSetId = fieldLi.get(0).getDataSetId();
					for (DetailData detailData : datasource.getDetailData()) {
						if (detailData.getId().equals(dataSetId)) {
							tableName = datasource.getCalcDetailTableName(dataSetId);
							pField = detailData.getFixField().getPrimaryKey();
							comment = datasource.getDisplayName() + "_" + detailData.getDisplayName();
						}
					}
				}

				final StringBuilder sb = new StringBuilder();
				sb.append(" CREATE TABLE `{tableName}` ( ".replace("{tableName}", tableName));
				for (Field field : fieldLi) {
					String line = getFieldSql(pField, field);
					sb.append("\n");
					sb.append("\t");
					sb.append(line);
					sb.append(",");
				}
				String line = "PRIMARY KEY (`{fieldName}`)";
				String fieldName = pField.getCalcFieldName();
				line = line.replace("{fieldName}", fieldName);
				sb.append("\n");
				sb.append("\t");
				sb.append(line);
				sb.append("\n");
				sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='{comment}';".replace("{comment}", comment));
				result.append(sb.toString());
				result.append("\n");
			}
		});

		return result.toString();
	}
	
	public String getGenerateInsertSql(final Datasource datasource, final int count) {
		final List<String> insertLi = New.arrayList();

		DatasourceIterator.iterateLineField(datasource, new IDatasourceLineFieldIterate() {
			@Override
			public void iterate(List<Field> fieldLi) {
				for (int i = 0; i < count; i++) {
					final List<String> keyLi = New.arrayList();
					final List<String> valueLi = New.arrayList();
					final int innerI = i;

					String template = "insert into {tableName}({keyLi}) values ({valueLi});";

					String tableName = null;
					Field pField = null;

					if (fieldLi.get(0).isMasterField()) {
						tableName = datasource.getCalcTableName();
						pField = datasource.getMasterData().getFixField().getPrimaryKey();
					} else {
						String dataSetId = fieldLi.get(0).getDataSetId();
						for (DetailData detailData : datasource.getDetailData()) {
							if (detailData.getId().equals(dataSetId)) {
								tableName = datasource.getCalcDetailTableName(dataSetId);
								pField = detailData.getFixField().getPrimaryKey();
							}
						}
					}
					template = template.replace("{tableName}", tableName);
					for (Field field : fieldLi) {
						String fieldName = field.getCalcFieldName();
						String keyTemplate = fieldName;
						String valuesTemplate = "";
						if (field.getFieldType().equalsIgnoreCase("FLOAT")) {
							valuesTemplate = "1";
						}
						if (field.getFieldType().equalsIgnoreCase("DOUBLE")) {
							valuesTemplate = "1";
						}
						if (field.getFieldType().equalsIgnoreCase("DECIMAL")) {
							valuesTemplate = "1";
						}
						if (field.getFieldType().equalsIgnoreCase("SHORT")) {
							valuesTemplate = "1";
						}
						if (field.getFieldType().equalsIgnoreCase("INT")) {
							valuesTemplate = "1";
						}
						if (field.getFieldType().equalsIgnoreCase("LONG")) {
							valuesTemplate = "1";
						}
						if (field.getFieldType().equalsIgnoreCase("NULL")) {
						}
						if (field.getFieldType().equalsIgnoreCase("STRING")) {
							valuesTemplate = "测试_" + innerI;
						}
						if (field.getFieldType().equalsIgnoreCase("DATE")) {
							SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
							Calendar calendar = Calendar.getInstance();
							calendar.add(Calendar.DATE, innerI);
							valuesTemplate = format.format(calendar.getTime());
						}
						if (field.getFieldType().equalsIgnoreCase("TIME")) {
							SimpleDateFormat format = new SimpleDateFormat("HHmmss");
							Calendar calendar = Calendar.getInstance();
							calendar.add(Calendar.MINUTE, innerI);
							valuesTemplate = format.format(calendar.getTime());
						}
						if (field.getFieldType().equalsIgnoreCase("TIMESTAMP")) {
							SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
							Calendar calendar = Calendar.getInstance();
							calendar.add(Calendar.DATE, innerI);
							valuesTemplate = format.format(calendar.getTime());
						}
						if (field.getFieldType().equalsIgnoreCase("BYTES")) {
						}

						if (field == pField) {
							valuesTemplate = "0";
						}

						keyLi.add(keyTemplate);
						valueLi.add("\"" + valuesTemplate + "\"");
					}
					
					template = template.replace("{keyLi}", StringUtils.join(keyLi.toArray(), ","));
					template = template.replace("{valueLi}", StringUtils.join(valueLi.toArray(), ","));
					insertLi.add(template);
				}
				insertLi.add("\n\n\n");
			}
		});


		return StringUtils.join(insertLi.toArray());
	}
	
	public String getGenerateController(String datasourceName) throws IOException {
		String path = "com/javameta/web/model/tpl/TemplateController.tpl";
		return getGenerateCommon(path, datasourceName);
	}
	
	public String getGenerateListJs(String datasourceName) throws IOException {
		String path = "com/javameta/web/model/tpl/list.js";
		String content = getGenerateCommon(path, datasourceName);
		content = "推荐文件名:" + datasourceName.substring(0, 1).toLowerCase() + datasourceName.substring(1) + "ListTemplate.js\n\n" + content;
		return content;
	}
	
	public String getGenerateModelJs(String datasourceName) throws IOException {
		String path = "com/javameta/web/model/tpl/model.js";
		String content = getGenerateCommon(path, datasourceName);
		content = "推荐文件名:" + datasourceName.substring(0, 1).toLowerCase() + datasourceName.substring(1) + "Model.js\n\n" + content;
		return content;
	}
	
	public String getGenerateListXml(String datasourceName) throws IOException {
		String path = "com/javameta/web/model/tpl/tpl_list_Template.xml.tpl";
		String content = getGenerateCommon(path, datasourceName);
		content = "注意文件名要以list_开头,推荐文件名:list_" + datasourceName + ".xml\n\n" + content;
		return getGenerateListSelectorCommonXml(content, datasourceName);
	}
	
	public String getGenerateSelectorXml(String datasourceName) throws IOException {
		String path = "com/javameta/web/model/tpl/tpl_selector_Template.xml.tpl";
		String content = getGenerateCommon(path, datasourceName);
		content = "注意文件名要以selector_开头,推荐文件名:selector_" + datasourceName + ".xml\n\n" + content;
		return getGenerateListSelectorCommonXml(content, datasourceName);
	}
	
	public String getGenerateFormXml(String datasourceName) throws IOException {
		String path = "com/javameta/web/model/tpl/tpl_form_Template.xml.tpl";
		String content = getGenerateCommon(path, datasourceName);
		content = "注意文件名要以form_开头,推荐文件名:form_" + datasourceName + ".xml\n\n" + content;
		
		DatasourceFactory datasourceFactory = new DatasourceFactory();
		Datasource datasource = datasourceFactory.getDatasource(datasourceName);
		String tableName = datasource.getCalcTableName();
		content = content.replace("{tableName}", tableName);
		content = content.replace("{description}", datasource.getDisplayName());

		final List<String> asNameLi = New.arrayList();
		DatasourceIterator.iterateField(datasource, new IDatasourceFieldIterate() {
			@Override
			public void iterate(Field field) {
				if (field.isMasterField()) {
					if (!field.getId().equals(field.getCalcFieldName())) {
						asNameLi.add("a.{fieldName} as {id},".replace("{fieldName}", field.getCalcFieldName()).replace("{id}", field.getId()));
					}
				}
			}
		});
		content = content.replace("{asNameLi}", StringUtils.join(asNameLi.toArray(), "\n"));
		
		final List<String> autoColumnLi = New.arrayList();
		DatasourceIterator.iterateField(datasource, new IDatasourceFieldIterate() {
			@Override
			public void iterate(Field field) {
				if (field.isMasterField()) {
					if (field.getId().equals("id")) {
						return;
					}
					autoColumnLi.add("<auto-column name=\"{id}\" colSpan=\"2\" labelWidth=\"13%\" columnWidth=\"20%\" />".replace("{id}", field.getId()));
				}
			}
		});
		content = content.replace("{autoColumnLi}", StringUtils.join(autoColumnLi.toArray(), "\n"));
		
		final List<String> detailDataProviderLi = New.arrayList();
		for (final DetailData detailData: datasource.getDetailData()) {
			String detailDataProvider = "";
			detailDataProvider += "<data-provider name=\"queryDataSet{dataSetId}\" size=\"10\">\n";
			detailDataProvider += "	<sql>\n";
			detailDataProvider += "		select \n";
			detailDataProvider += "		{asNameLi}\n";
			detailDataProvider += "		a.* from {tableName} a\n";
			detailDataProvider += "		where 1=1\n";
			detailDataProvider += "	</sql>\n";
			detailDataProvider += "	<query-parameters dataSetId=\"{dataSetId}\">\n";
			detailDataProvider += "		<query-parameter name=\"id\" auto=\"true\" text=\"\"></query-parameter>\n";
			detailDataProvider += "		<query-parameter name=\"{parentFieldId}\" auto=\"true\" text=\"\"></query-parameter>\n";
			detailDataProvider += "	</query-parameters>\n";
			detailDataProvider += "</data-provider>\n";
			
			detailDataProvider = detailDataProvider.replace("{dataSetId}", detailData.getId());
			detailDataProvider = detailDataProvider.replace("{tableName}", datasource.getCalcDetailTableName(detailData.getId()));
			detailDataProvider = detailDataProvider.replace("{parentFieldId}", detailData.getParentFieldId());
			
			final List<String> dAsNameLi = New.arrayList();
			DatasourceIterator.iterateField(datasource, new IDatasourceFieldIterate() {
				@Override
				public void iterate(Field field) {
					if (field.getDataSetId().equals(detailData.getId())) {
						if (!field.getId().equals(field.getCalcFieldName())) {
							dAsNameLi.add("a.{fieldName} as {id},".replace("{fieldName}", field.getCalcFieldName()).replace("{id}", field.getId()));
						}
					}
				}
			});
			detailDataProvider = detailDataProvider.replace("{asNameLi}", StringUtils.join(dAsNameLi.toArray(), "\n"));
			detailDataProviderLi.add(detailDataProvider);
		}
		content = content.replace("{detailDataProviderLi}", StringUtils.join(detailDataProviderLi.toArray(), "\n"));
		
		
		final List<String> detailColumnModelLi = New.arrayList();
		for (int i =0; i < datasource.getDetailData().size(); i++) {
			final DetailData detailData = datasource.getDetailData().get(i);
			String detailColumnModel = "";
			detailColumnModel += "<column-model name=\"{dataSetId}_{index}\" dataSetId=\"{dataSetId}\" rownumber=\"true\" colSpan=\"4\" dataProvider=\"queryDataSet{dataSetId}\">\n";
			detailColumnModel += "	<toolbar name=\"{dataSetId}_{index}_toolbar\">\n";
			detailColumnModel += "		<button name=\"selectRowBtn\" text=\"选择\" mode=\"fn\" handler=\"g_selectRow\" iconCls=\"but_box\">\n";
			detailColumnModel += "			<relationDS>\n";
			detailColumnModel += "				<relationItem name=\"NullSelectorItem\">\n";
			detailColumnModel += "					<relationExpr>true</relationExpr>\n";
			detailColumnModel += "					<relationConfig selectorName=\"NullSelector\" displayField=\"code,name\" valueField=\"id\" selectionMode=\"multi\" />\n";
			detailColumnModel += "					<copyConfig copyDestField=\"id\" copySrcField=\"id\" />\n";
			detailColumnModel += "				</relationItem>\n";
			detailColumnModel += "			</relationDS>\n";
			detailColumnModel += "		</button>\n";
			detailColumnModel += "		<button text=\"新增\" mode=\"fn\" handler=\"g_addRow\" iconCls=\"but_box\"></button>\n";
			detailColumnModel += "		<button text=\"编辑\" mode=\"fn\" handler=\"g_editRow\" iconCls=\"but_box\"></button>\n";
			detailColumnModel += "		<button text=\"删除\" mode=\"fn\" handler=\"g_removeRow\" iconCls=\"but_box\"></button>\n";
			detailColumnModel += "	</toolbar>\n";
			detailColumnModel += "	<checkbox-column name=\"checkboxSelect\" hideable=\"false\" />\n";
			detailColumnModel += "	<id-column name=\"id\" text=\"编号\" hideable=\"true\" />\n";
			detailColumnModel += "	<virtual-column name=\"FUN_C\" text=\"操作\" width=\"90\">\n";
			detailColumnModel += "		<buttons>\n";
			detailColumnModel += "			<button text=\"编辑\" mode=\"fn\" handler=\"g_editSingleRow\" iconCls=\"img_edit\"></button>\n";
			detailColumnModel += "			<button text=\"复制\" mode=\"fn\" handler=\"g_copySingleRow\" iconCls=\"img_add\"></button>\n";
			detailColumnModel += "			<button name=\"btn_delete\" text=\"删除\" mode=\"fn\" handler=\"g_removeSingleRow\" iconCls=\"img_delete\"></button>\n";
			detailColumnModel += "		</buttons>\n";
			detailColumnModel += "	</virtual-column>\n";
			detailColumnModel += "	{autoColumnLi}\n";
			detailColumnModel += "</column-model>\n";
			
			detailColumnModel = detailColumnModel.replace("{dataSetId}", detailData.getId());
			detailColumnModel = detailColumnModel.replace("{index}", String.valueOf(i + 1));
			
			final List<String> dAutoColumnLi = New.arrayList();
			DatasourceIterator.iterateField(datasource, new IDatasourceFieldIterate() {
				@Override
				public void iterate(Field field) {
					if (field.getDataSetId().equals(detailData.getId())) {
						if (field.getId().equals("id")) {
							return;
						}
						dAutoColumnLi.add("<auto-column name=\"{id}\" width=\"140\" />".replace("{id}", field.getId()));
					}
				}
			});
			detailColumnModel = detailColumnModel.replace("{autoColumnLi}", StringUtils.join(dAutoColumnLi.toArray(), "\n"));
			
			detailColumnModelLi.add(detailColumnModel);
		}
		content = content.replace("{detailColumnModelLi}", StringUtils.join(detailColumnModelLi.toArray(), "\n"));
		
		return content;
	}
	
	private String getGenerateListSelectorCommonXml(String content, String datasourceName) throws IOException {
		DatasourceFactory datasourceFactory = new DatasourceFactory();
		Datasource datasource = datasourceFactory.getDatasource(datasourceName);
		String tableName = datasource.getCalcTableName();
		content = content.replace("{tableName}", tableName);
		content = content.replace("{description}", datasource.getDisplayName());

		final List<String> asNameLi = New.arrayList();
		DatasourceIterator.iterateField(datasource, new IDatasourceFieldIterate() {
			@Override
			public void iterate(Field field) {
				if (field.isMasterField()) {
					if (!field.getId().equals(field.getCalcFieldName())) {
						asNameLi.add("a.{fieldName} as {id},".replace("{fieldName}", field.getCalcFieldName()).replace("{id}", field.getId()));
					}
				}
			}
		});
		content = content.replace("{asNameLi}", StringUtils.join(asNameLi.toArray(), "\n"));
		
		final List<String> queryParameterLi = New.arrayList();
		DatasourceIterator.iterateField(datasource, new IDatasourceFieldIterate() {
			@Override
			public void iterate(Field field) {
				if (field.isMasterField()) {
					if (field.getId().equals("id")) {
						return;
					}
					boolean isBeginEnd = false;
					isBeginEnd = isBeginEnd || field.getFieldType().equals("DATE") || field.getFieldType().equals("TIME") || field.getFieldType().equals("TIMESTAMP");
					isBeginEnd = isBeginEnd || field.getFieldType().equals("FLOAT");
					isBeginEnd = isBeginEnd || field.getFieldType().equals("DOUBLE");
					isBeginEnd = isBeginEnd || field.getFieldType().equals("DECIMAL");
					isBeginEnd = isBeginEnd || field.getFieldType().equals("SHORT");
					isBeginEnd = isBeginEnd || field.getFieldType().equals("INT");
					isBeginEnd = isBeginEnd || field.getFieldType().equals("LONG");
					isBeginEnd = isBeginEnd && StringUtils.isEmpty(field.getDictionary());
					if (field.isRelationField()) {
						queryParameterLi.add("<query-parameter name=\"{id}\" auto=\"true\" text=\"\">".replace("{id}", field.getId()));
						queryParameterLi.add("<relationDS>");
						for (RelationItem relationItem: field.getRelationDS().getRelationItem()) {
							queryParameterLi.add("<relationItem name=\"{name}\">".replace("{name}", relationItem.getName()));
							queryParameterLi.add("<relationConfig selectionMode=\"multi\" />");
							queryParameterLi.add("</relationItem>");
						}
						queryParameterLi.add("</relationDS>");
						queryParameterLi.add("</query-parameter>");
					} else if (isBeginEnd) {
						String begin = "<query-parameter name=\"{fieldName}Begin\" columnName=\"{fieldName}\" auto=\"true\" text=\"{displayName}从\" restriction=\"ge\" />";
						begin = begin.replace("{fieldName}", field.getId());
						begin = begin.replace("{displayName}", field.getDisplayName());
						queryParameterLi.add(begin);
						
						String end = "<query-parameter name=\"{fieldName}End\" columnName=\"{fieldName}\" auto=\"true\" text=\"{displayName}到\" restriction=\"le\" />";
						end = end.replace("{fieldName}", field.getId());
						end = end.replace("{displayName}", field.getDisplayName());
						queryParameterLi.add(end);
					} else {
						queryParameterLi.add("<query-parameter name=\"{id}\" auto=\"true\" text=\"\"></query-parameter>".replace("{id}", field.getId()));
					}
				}
			}
		});
		content = content.replace("{queryParameterLi}", StringUtils.join(queryParameterLi.toArray(), "\n"));

		final List<String> autoColumnLi = New.arrayList();
		DatasourceIterator.iterateField(datasource, new IDatasourceFieldIterate() {
			@Override
			public void iterate(Field field) {
				if (field.isMasterField()) {
					if (field.getId().equals("id")) {
						return;
					}
					autoColumnLi.add("<auto-column name=\"{id}\" />".replace("{id}", field.getId()));
				}
			}
		});
		content = content.replace("{autoColumnLi}", StringUtils.join(autoColumnLi.toArray(), "\n"));
		
		return content;
	}
	
	public String getGenerateService(String datasourceName) throws IOException {
		String path = "com/javameta/web/model/tpl/TemplateService.tpl";
		return getGenerateCommon(path, datasourceName);
	}
	
	public String getGenerateDao(String datasourceName) throws IOException {
		String path = "com/javameta/web/model/tpl/TemplateDao.tpl";
		return getGenerateCommon(path, datasourceName);
	}
	
	public String getGenerateCommon(String path, String datasourceName) throws IOException {
		InputStream in = null;
		try {
			in = this.getClass().getClassLoader().getResourceAsStream(path);
			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			byte[] buffer = new byte[4 * 1024];
			int read = -1;
			while ((read = in.read(buffer)) > -1) {
				bOut.write(buffer, 0, read);
			}
			String content = bOut.toString("UTF-8");
			content = content.replace("{lowerTemplate}", datasourceName.substring(0, 1).toLowerCase() + datasourceName.substring(1));
			content = content.replace("{template}", datasourceName);
			return content;
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	public Map<String, Object> testObject() {
		return schemaDao.getObject();
	}

	public void testInsert1() {
		schemaDao.insert1();
		schemaDao.insert2();
	}

	public void testInsert2() {
		schemaDao.insert1();
		schemaDao.insert2();
		if (true) {
			throw new RuntimeException("rollback test");
		}
	}

	public SchemaDao getSchemaDao() {
		return schemaDao;
	}

	public void setSchemaDao(SchemaDao schemaDao) {
		this.schemaDao = schemaDao;
	}
}
