package com.javameta.web.model.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.javameta.web.model.service.SchemaService;
import com.javameta.web.support.ControllerSupport;

@Scope("prototype")
@Controller
@RequestMapping("/schema")
public class SchemaController extends ControllerSupport {
	private static final long serialVersionUID = 1L;
	@Autowired
	private SchemaService schemaService;
	
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		Map<String, Object> obj = schemaService.testObject();
		request.setAttribute("name", obj.get("name"));
		return new ModelAndView("model/list");
	}
	
	@RequestMapping("/testDB")
	@ResponseBody
	public String testDB(HttpServletRequest request) {
		Map<String, Object> obj = schemaService.testObject();
		if (obj != null) {
//			SerializationUtils.clone(this)-实现深拷贝
//			SerializationUtils.clone(this);
//			return ObjectUtils.toString(obj, "");
			System.out.println(obj.get("name"));
//			return obj.toString();
			return obj.get("name").toString();
		}
		return "gogogogog";
	}
	
	@RequestMapping("/ajaxJson")
	@ResponseBody
	public JSONObject ajaxJson(HttpServletRequest request) {
		Map<String, Object> obj = schemaService.testObject();
		JSONObject json = new JSONObject();
		json.put("name", obj);
		return json;
	}
	
	@RequestMapping("/testInsert1")
	@ResponseBody
	public String testInsert1(HttpServletRequest request) {
		schemaService.testInsert1();
		return "testInsert1";
	}
	
	@RequestMapping("/testInsert2")
	@ResponseBody
	public String testInsert2(HttpServletRequest request) {
		schemaService.testInsert2();
		return "testInsert2";
	}

	public SchemaService getSchemaService() {
		return schemaService;
	}

	public void setSchemaService(SchemaService schemaService) {
		this.schemaService = schemaService;
	}
}
