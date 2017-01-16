package com.javameta.web.model.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javameta.model.datasource.Datasource;
import com.javameta.model.datasource.Field;
import com.javameta.model.iterate.DatasourceIterator;
import com.javameta.model.iterate.IDatasourceFieldIterate;
import com.javameta.util.New;
import com.javameta.web.model.dao.SchemaDao;
import com.javameta.web.support.ServiceSupport;

@Scope("prototype")
@Service
@Transactional
public class SchemaService extends ServiceSupport {
	@Autowired
	private SchemaDao schemaDao;

	/*
	CREATE TABLE `pub_diccomm` (
	`DICCOMM_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '内容序列ID',
	`DICLIST_ID` decimal(8,0) NOT NULL COMMENT '字典清单ID',
	`ITEMCODE` varchar(200) NOT NULL COMMENT '数据项代码',
	`ITEMNAME` varchar(200) NOT NULL COMMENT '数据项名称',
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
		final StringBuilder sb = new StringBuilder();
		String tableName = StringUtils.isNotEmpty(datasource.getTableName()) ? datasource.getTableName() : datasource.getId();
		sb.append(" CREATE TABLE `{tableName}` ( ".replace("{tableName}", tableName));

		DatasourceIterator.iterateField(datasource, new IDatasourceFieldIterate() {
			@Override
			public void iterate(Field field) {
				String line = "`{fieldName}` {fieldType} {isNull} {autoIncrement} COMMENT '{comment}'";
				String fieldName = StringUtils.isNotEmpty(field.getFieldName()) ? field.getFieldName() : field.getId();
				line = line.replace("{fieldName}", fieldName);
				String isNull = field.isAllowEmpty() ? "DEFAULT NULL" : "NOT NULL";
				line = line.replace("{isNull}", isNull);
				String autoIncrement = "";
				if (field == datasource.getMasterData().getFixField().getPrimaryKey()) {
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
				sb.append("\n");
				sb.append("\t");
				sb.append(line);
				sb.append(",");
			}
		});
		String line = "PRIMARY KEY (`{fieldName}`)";
		Field field = datasource.getMasterData().getFixField().getPrimaryKey();
		String fieldName = StringUtils.isNotEmpty(field.getFieldName()) ? field.getFieldName() : field.getId();
		line = line.replace("{fieldName}", fieldName);
		sb.append("\n");
		sb.append("\t");
		sb.append(line);
		sb.append("\n");
		sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='{comment}';".replace("{comment}", datasource.getDisplayName()));
		return sb.toString();
	}

	public String getGenerateInsertSql(final Datasource datasource, int count) {
		String tableName = StringUtils.isNotEmpty(datasource.getTableName()) ? datasource.getTableName() : datasource.getId();
		final List<String> insertLi = New.arrayList();

		for (int i = 0; i < count; i++) {
			final List<String> keyLi = New.arrayList();
			final List<String> valueLi = New.arrayList();
			final int innerI = i;
			String template = "insert into {tableName}({keyLi}) values ({valueLi});";
			template = template.replace("{tableName}", tableName);
			DatasourceIterator.iterateField(datasource, new IDatasourceFieldIterate() {
				@Override
				public void iterate(Field field) {
					String fieldName = StringUtils.isNotEmpty(field.getFieldName()) ? field.getFieldName() : field.getId();
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
					
					if (field == datasource.getMasterData().getFixField().getPrimaryKey()) {
						valuesTemplate = "0";
					}
					
					keyLi.add(keyTemplate);
					valueLi.add("\"" + valuesTemplate + "\"");
				}
			});
			template = template.replace("{keyLi}", StringUtils.join(keyLi.toArray(), ","));
			template = template.replace("{valueLi}", StringUtils.join(valueLi.toArray(), ","));
			insertLi.add(template);
		}

		return StringUtils.join(insertLi.toArray());
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
