package com.javameta.model.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javameta.model.FormTemplateDao;
import com.javameta.model.datasource.Datasource;

@Scope("prototype")
@Service
@Transactional
public class UsedCheck {
	@Autowired
	private FormTemplateDao formTemplateDao;
	
//	func (o UsedCheck) CheckUsed(sessionId int, dataSource DataSource, bo map[string]interface{}) bool {
	public boolean checkUsed(Datasource datasource, Map<String, Object> bo) {
//		masterData := bo["A"].(map[string]interface{})
		Map<String, Object> masterData = (Map<String, Object>)bo.get("A");
//		beReferenceQuery
		return true;
	}

	public FormTemplateDao getFormTemplateDao() {
		return formTemplateDao;
	}

	public void setFormTemplateDao(FormTemplateDao formTemplateDao) {
		this.formTemplateDao = formTemplateDao;
	}
	
	
}
