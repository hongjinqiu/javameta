package com.javameta.web.form.controller;

import java.util.Map;

import com.javameta.model.ValueBusinessObject;
import com.javameta.model.datasource.Datasource;
import com.javameta.model.template.FormTemplate;

public class ModelRenderVO {
	private int userId;
	private Map<String, Object> bo;
	private Map<String, Object> relationBo;
	private Map<String, Object> usedCheckBo;
	private Datasource datasource;
	private FormTemplate formTemplate;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Map<String, Object> getRelationBo() {
		return relationBo;
	}

	public void setRelationBo(Map<String, Object> relationBo) {
		this.relationBo = relationBo;
	}

	public Map<String, Object> getUsedCheckBo() {
		return usedCheckBo;
	}

	public void setUsedCheckBo(Map<String, Object> usedCheckBo) {
		this.usedCheckBo = usedCheckBo;
	}

	public Datasource getDatasource() {
		return datasource;
	}

	public void setDatasource(Datasource datasource) {
		this.datasource = datasource;
	}

	public FormTemplate getFormTemplate() {
		return formTemplate;
	}

	public void setFormTemplate(FormTemplate formTemplate) {
		this.formTemplate = formTemplate;
	}

	public Map<String, Object> getBo() {
		return bo;
	}

	public void setBo(Map<String, Object> bo) {
		this.bo = bo;
	}

}
