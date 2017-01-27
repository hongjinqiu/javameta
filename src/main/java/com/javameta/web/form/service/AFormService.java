package com.javameta.web.form.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javameta.model.ValueBusinessObject;
import com.javameta.model.datasource.Datasource;
import com.javameta.model.handler.DiffDataRow;
import com.javameta.model.template.FormTemplate;
import com.javameta.util.New;
import com.javameta.web.form.controller.ModelRenderVO;
import com.javameta.web.support.ServiceSupport;

@Service
@Transactional
public abstract class AFormService extends ServiceSupport {
	public ModelRenderVO newDataCommon(HttpServletRequest request, HttpServletResponse response) {
		
		
		ModelRenderVO modelRenderVO = new ModelRenderVO();
		return modelRenderVO;
	}

	public void beforeNewData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate) {

	}

	public void afterNewData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {

	}

	public void beforeGetData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate) {

	}

	public void afterGetData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {

	}

	public void beforeCopyData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject srcValueBo) {

	}

	public void afterCopyData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {

	}

	public Map<String, Object> editValidate(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {
		Map<String, Object> result = New.hashMap();
		result.put("success", true);
		result.put("message", "");
		return result;
	}

	public void beforeEditData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {

	}

	public void afterEditData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {

	}

	public void beforeSaveData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {

	}

	public void afterSaveData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo, List<DiffDataRow> diffDateRowLi) {

	}

	public void beforeGiveUpData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {

	}

	public void afterGiveUpData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {

	}

	public void beforeDeleteData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {

	}

	public void afterDeleteData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {

	}

	public void beforeRefreshData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {

	}

	public void afterRefreshData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {

	}
}
