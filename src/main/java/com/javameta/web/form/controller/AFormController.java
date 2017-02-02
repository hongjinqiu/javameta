package com.javameta.web.form.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javameta.web.form.service.AFormService;
import com.javameta.web.support.ControllerSupport;

@Scope("prototype")
@Controller
public abstract class AFormController extends ControllerSupport {
	private static final long serialVersionUID = 1L;

	@RequestMapping("/newData")
	@ResponseBody
	public JSONObject newData(HttpServletRequest request, HttpServletResponse response) {
		ModelRenderVO modelRenderVO = getService().newDataCommon(request, response);
		return renderCommon(request, response, modelRenderVO);
	}

	@RequestMapping("/getData")
	@ResponseBody
	public JSONObject getData(HttpServletRequest request, HttpServletResponse response) {
		ModelRenderVO modelRenderVO = getService().getDataCommon(request, response);
		return renderCommon(request, response, modelRenderVO);
	}

	@RequestMapping("/copyData")
	@ResponseBody
	public JSONObject copyData(HttpServletRequest request, HttpServletResponse response) {
		ModelRenderVO modelRenderVO = getService().copyDataCommon(request, response);
		return renderCommon(request, response, modelRenderVO);
	}

	/**
	 * 修改,只取数据,没涉及到数据库保存,涉及到数据库保存的方法是SaveData,
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/editData")
	@ResponseBody
	public JSONObject editData(HttpServletRequest request, HttpServletResponse response) {
		ModelRenderVO modelRenderVO = getService().editDataCommon(request, response);
		return renderCommon(request, response, modelRenderVO);
	}

	@RequestMapping("/saveData")
	@ResponseBody
	public JSONObject saveData(HttpServletRequest request, HttpServletResponse response) {
		ModelRenderVO modelRenderVO = getService().saveDataCommon(request, response);
		return renderCommon(request, response, modelRenderVO);
	}

	@RequestMapping("/giveUpData")
	@ResponseBody
	public JSONObject giveUpData(HttpServletRequest request, HttpServletResponse response) {
		ModelRenderVO modelRenderVO = getService().giveUpDataCommon(request, response);
		return renderCommon(request, response, modelRenderVO);
	}

	@RequestMapping("/deleteData")
	@ResponseBody
	public JSONObject deleteData(HttpServletRequest request, HttpServletResponse response) {
		ModelRenderVO modelRenderVO = getService().deleteDataCommon(request, response);
		return renderCommon(request, response, modelRenderVO);
	}

	@RequestMapping("/refreshData")
	@ResponseBody
	public JSONObject refreshData(HttpServletRequest request, HttpServletResponse response) {
		ModelRenderVO modelRenderVO = getService().refreshDataCommon(request, response);
		return renderCommon(request, response, modelRenderVO);
	}

	@RequestMapping("/logList")
	@ResponseBody
	public JSONObject logList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = getService().logListCommon(request, response);
		JSONObject obj = JSONObject.fromObject(result);
		return obj;
	}

	private JSONObject renderCommon(HttpServletRequest request, HttpServletResponse response, ModelRenderVO modelRenderVO) {
		JSONObject result = new JSONObject();

		result.put("bo", modelRenderVO.getBo());
		result.put("relationBo", modelRenderVO.getRelationBo());
		result.put("usedCheckBo", modelRenderVO.getUsedCheckBo());

		return result;
	}

	public abstract AFormService getService();
}
