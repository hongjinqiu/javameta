package com.javameta.web.model.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javameta.model.datasource.Datasource;
import com.javameta.model.datasource.Field;
import com.javameta.model.iterate.DatasourceIterator;
import com.javameta.model.iterate.IDatasourceFieldIterate;
import com.javameta.web.model.dao.SchemaDao;
import com.javameta.web.support.ServiceSupport;

@Scope("prototype")
@Service
@Transactional
public class SchemaService extends ServiceSupport {
	@Autowired
	private SchemaDao schemaDao;

	/*
	 * 
	 */
	public String getGenerateTableSql(Datasource datasource) {
		final StringBuilder sb = new StringBuilder();

		DatasourceIterator.iterateField(datasource, new IDatasourceFieldIterate() {
			@Override
			public void iterate(Field field) {
				if (field.getFieldType().equalsIgnoreCase("FLOAT")) {
				}
				if (field.getFieldType().equalsIgnoreCase("DOUBLE")) {
				}
				if (field.getFieldType().equalsIgnoreCase("DECIMAL")) {
				}
				if (field.getFieldType().equalsIgnoreCase("SHORT")) {
				}
				if (field.getFieldType().equalsIgnoreCase("INT")) {
				}
				if (field.getFieldType().equalsIgnoreCase("LONG")) {
				}
				if (field.getFieldType().equalsIgnoreCase("NULL")) {
				}
				if (field.getFieldType().equalsIgnoreCase("STRING")) {
				}
				if (field.getFieldType().equalsIgnoreCase("DATE")) {
				}
				if (field.getFieldType().equalsIgnoreCase("TIME")) {
				}
				if (field.getFieldType().equalsIgnoreCase("TIMESTAMP")) {
				}
				if (field.getFieldType().equalsIgnoreCase("BYTES")) {
				}
			}
		});
		return sb.toString();
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
