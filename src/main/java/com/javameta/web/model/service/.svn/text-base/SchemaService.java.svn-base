package com.javameta.web.model.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javameta.web.model.dao.SchemaDao;
import com.javameta.web.support.ServiceSupport;

@Scope("prototype")
@Service
@Transactional
public class SchemaService extends ServiceSupport {
	@Autowired
	private SchemaDao schemaDao;
	
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
