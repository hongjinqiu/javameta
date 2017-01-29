package com.javameta.web.form.controller;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javameta.model.BusinessDataType;
import com.javameta.model.datasource.Field;
import com.javameta.model.iterate.DatasourceIterator;
import com.javameta.model.iterate.IDatasourceFieldDataIterate;
import com.javameta.util.ApplicationContextUtil;
import com.javameta.util.New;
import com.javameta.value.Value;
import com.javameta.web.form.service.AFormService;
import com.javameta.web.support.ControllerSupport;

@Scope("prototype")
@Controller
public abstract class AFormAction extends ControllerSupport {
	private static final long serialVersionUID = 1L;

	@RequestMapping("/newData")
	@ResponseBody
	public JSONObject newData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> param = New.hashMap();
		ModelRenderVO modelRenderVO = getService().newDataCommon(request, response);
		return renderCommon(request, response, modelRenderVO);
	}

	/*
	private Map<String, String> getFormQueryData(HttpServletRequest request) {
		Map<String, String> formQueryData = New.hashMap();
		for (Enumeration enumeration = request.getParameterNames(); enumeration.hasMoreElements();) {
			String key = (String) enumeration.nextElement();
			String value = StringUtils.join(request.getParameterValues(key), ",");
			formQueryData.put(key, value);
		}
		return formQueryData;
	}
	*/

	private JSONObject renderCommon(HttpServletRequest request, HttpServletResponse response, ModelRenderVO modelRenderVO) {
		JSONObject result = new JSONObject();

		String format = request.getParameter("format");
		if (format.equals("json")) {
			result.put("bo", modelRenderVO.getBo());
			result.put("relationBo", modelRenderVO.getRelationBo());
			result.put("usedCheckBo", modelRenderVO.getUsedCheckBo());
		}

		return result;
	}

	public abstract AFormService getService();
}
