package com.javameta.web.model.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.javameta.model.DatasourceFactory;
import com.javameta.model.FormTemplateEnum;
import com.javameta.model.FormTemplateFactory;
import com.javameta.model.datasource.Datasource;
import com.javameta.model.datasource.DatasourceInfo;
import com.javameta.model.handler.UsedCheck;
import com.javameta.model.iterate.FormTemplateIterator;
import com.javameta.model.iterate.IFormTemplateDataProviderIterate;
import com.javameta.model.iterate.IFormTemplateQueryParameterIterate;
import com.javameta.model.template.ColumnModel;
import com.javameta.model.template.DataProvider;
import com.javameta.model.template.FormTemplate;
import com.javameta.model.template.FormTemplateInfo;
import com.javameta.model.template.QueryParameters.QueryParameter;
import com.javameta.model.template.Toolbar;
import com.javameta.util.CommonUtil;
import com.javameta.util.New;
import com.javameta.util.ObjectHolder;
import com.javameta.web.model.service.SchemaService;
import com.javameta.web.support.ControllerSupport;

@Scope("prototype")
@Controller
@RequestMapping("/schema")
public class SchemaController extends ControllerSupport {
	private static final long serialVersionUID = 1L;
	@Autowired
	private SchemaService schemaService;
	@Autowired
	private UsedCheck usedCheck;
	
	/**
	 * 模型控制台
	 * @param request
	 * @return
	 */
	@RequestMapping("/summary")
	public ModelAndView summary(HttpServletRequest request) {
		FormTemplateFactory formTemplateFactory = new FormTemplateFactory();
		FormTemplate formTemplate = formTemplateFactory.getFormTemplate("Console", FormTemplateEnum.FORM);
		
		Map<String, Object> toolbarBo = New.hashMap();
		Map<String, Object> dataBo = New.hashMap();
		{
			List<FormTemplateInfo> listTemplateInfoLi = formTemplateFactory.getFormTemplateInfoLi(FormTemplateEnum.LIST);
			dataBo.put("Component", getSummaryListTemplateInfoLi(listTemplateInfoLi));
		}
		{
			List<FormTemplateInfo> selectorTemplateInfoLi = formTemplateFactory.getFormTemplateInfoLi(FormTemplateEnum.SELECTOR);
			dataBo.put("Selector", getSummarySelectorTemplateInfoLi(selectorTemplateInfoLi));
		}
		{
			List<FormTemplateInfo> formTemplateInfoLi = formTemplateFactory.getFormTemplateInfoLi(FormTemplateEnum.FORM);
			dataBo.put("Form", getSummaryFormTemplateInfoLi(formTemplateInfoLi));
		}
		{
			DatasourceFactory datasourceFactory = new DatasourceFactory();
			List<DatasourceInfo> datasourceInfoLi = datasourceFactory.getDatasourceInfoLi();
			dataBo.put("Datasource", getSummaryDatasourceInfoLi(datasourceInfoLi));
		}
		for (Object item: formTemplate.getToolbarOrDataProviderOrColumnModel()) {
			if (item instanceof ColumnModel) {
				ColumnModel columnModel = (ColumnModel)item;
				if (dataBo.get(columnModel.getName()) == null) {
					dataBo.put(columnModel.getName(), new ArrayList<Map<String, Object>>());
				}
				List<Map<String, Object>> items = (List<Map<String, Object>>)dataBo.get(columnModel.getName());
				Map<String, Object> bo = New.hashMap();
				Map<String, Object> itemsDict = formTemplateFactory.getColumnModelDataForColumnModel(columnModel, bo, items);
				items = (List<Map<String, Object>>)itemsDict.get("items");
				dataBo.put(columnModel.getName(), items);
			} else if (item instanceof Toolbar) {
				Toolbar toolbar = (Toolbar)item;
				toolbarBo.put(toolbar.getName(), formTemplateFactory.getToolbarBo(toolbar));
			}
		}
		
		JSONObject formTemplateJsonData = JSONObject.fromObject(formTemplate);
		JSONObject dataBoJson = JSONObject.fromObject(dataBo);
		
		request.setAttribute("formTemplate", formTemplate);
		request.setAttribute("toolbarBo", toolbarBo);
		request.setAttribute("dataBo", dataBo);
		request.setAttribute("formTemplateJsonData", formTemplateJsonData.toString());
		request.setAttribute("dataBoJson", dataBoJson.toString());
		
		{
			FormTemplate gatheringFormTemplate = formTemplateFactory.getFormTemplate("GatheringBillForm", FormTemplateEnum.FORM);
			JSONObject gatheringFormTemplateJsonData = JSONObject.fromObject(gatheringFormTemplate);
			request.setAttribute("gatheringFormTemplateJsonData", gatheringFormTemplateJsonData.toString());
		}
		{
			DatasourceFactory datasourceFactory = new DatasourceFactory();
			Datasource gathering = datasourceFactory.getDatasource("GatheringBill");
			JSONObject datasourceJson = JSONObject.fromObject(gathering);
			request.setAttribute("datasourceJson", datasourceJson.toString());
		}
		
		String view = formTemplate.getViewTemplate().getView();
		if (view.endsWith(".jsp")) {
			view = view.replace(".jsp", "");
		}
		return new ModelAndView(view);
	}
	
	/**
	 * 列表模型数据获取
	 * @param listTemplateInfoLi
	 * @return
	 */
	private List<Map<String, Object>> getSummaryListTemplateInfoLi(List<FormTemplateInfo> listTemplateInfoLi) {
		List<Map<String, Object>> componentItems = New.arrayList();
		for (FormTemplateInfo formTemplateInfo: listTemplateInfoLi) {
			String module = "组件模型";
			if (StringUtils.isNotEmpty(formTemplateInfo.getFormTemplate().getDatasourceModelId()) && StringUtils.isNotEmpty(formTemplateInfo.getFormTemplate().getAdapter().getName())) {
				module = "数据源模型适配";
			}
			Map<String, Object> componentItem = New.hashMap();
			componentItem.put("id", formTemplateInfo.getFormTemplate().getId());
			componentItem.put("name", formTemplateInfo.getFormTemplate().getDescription());
			componentItem.put("module", module);
			componentItem.put("path", formTemplateInfo.getPath());
			componentItems.add(componentItem);
		}
		
		return componentItems;
	}
	
	/**
	 * 选择器模型数据获取
	 * @param listTemplateInfoLi
	 * @return
	 */
	private List<Map<String, Object>> getSummarySelectorTemplateInfoLi(List<FormTemplateInfo> listTemplateInfoLi) {
		List<Map<String, Object>> componentItems = New.arrayList();
		for (FormTemplateInfo formTemplateInfo: listTemplateInfoLi) {
			String module = "组件模型选择器";
			if (StringUtils.isNotEmpty(formTemplateInfo.getFormTemplate().getDatasourceModelId()) && StringUtils.isNotEmpty(formTemplateInfo.getFormTemplate().getAdapter().getName())) {
				module = "数据源模型选择器适配";
			}
			Map<String, Object> componentItem = New.hashMap();
			componentItem.put("id", formTemplateInfo.getFormTemplate().getId());
			componentItem.put("name", formTemplateInfo.getFormTemplate().getDescription());
			componentItem.put("module", module);
			componentItem.put("path", formTemplateInfo.getPath());
			componentItems.add(componentItem);
		}
		
		return componentItems;
	}
	
	/**
	 * 表单模型数据获取
	 * @param listTemplateInfoLi
	 * @return
	 */
	private List<Map<String, Object>> getSummaryFormTemplateInfoLi(List<FormTemplateInfo> listTemplateInfoLi) {
		List<Map<String, Object>> componentItems = New.arrayList();
		for (FormTemplateInfo formTemplateInfo: listTemplateInfoLi) {
			String module = "form模型";
			if (StringUtils.isNotEmpty(formTemplateInfo.getFormTemplate().getDatasourceModelId()) && StringUtils.isNotEmpty(formTemplateInfo.getFormTemplate().getAdapter().getName())) {
				module = "数据源模型适配";
			}
			Map<String, Object> componentItem = New.hashMap();
			componentItem.put("id", formTemplateInfo.getFormTemplate().getId());
			componentItem.put("name", formTemplateInfo.getFormTemplate().getDescription());
			componentItem.put("module", module);
			componentItem.put("path", formTemplateInfo.getPath());
			componentItems.add(componentItem);
		}
		
		return componentItems;
	}
	
	/**
	 * 数据源模型数据获取
	 * @param listTemplateInfoLi
	 * @return
	 */
	private List<Map<String, Object>> getSummaryDatasourceInfoLi(List<DatasourceInfo> datasourceInfoLi) {
		List<Map<String, Object>> componentItems = New.arrayList();
		String module = "数据源模型";
		for (DatasourceInfo item: datasourceInfoLi) {
			Map<String, Object> componentItem = New.hashMap();
			componentItem.put("id", item.getDatasource().getId());
			componentItem.put("name", item.getDatasource().getDisplayName());
			componentItem.put("module", module);
			componentItem.put("path", item.getPath());
			componentItems.add(componentItem);
		}
		
		return componentItems;
	}
	
	@RequestMapping("/getGenerateTableSql")
	@ResponseBody
	public void getGenerateTableSql(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.addHeader("Content-Type", "text/html;charset=UTF-8");
		String datasourceName = request.getParameter("@name");
		DatasourceFactory datasourceFactory = new DatasourceFactory();
		Datasource datasource = datasourceFactory.getDatasource(datasourceName);
		String content = schemaService.getGenerateTableSql(datasource);
		
		content += "<br /><br /><br />";
		
		String insertContent = schemaService.getGenerateInsertSql(datasource, 25);
		content += insertContent;
		
		content = content.replace("\n", "<br />");
		content = content.replace(";", ";<br />");
		
		response.getOutputStream().write(content.getBytes());
	}
	
	@RequestMapping("/refretor")
	@ResponseBody
	public JSONObject refretor(HttpServletRequest request) {
		String type = request.getParameter("type");
		FormTemplateFactory formTemplateFactory = new FormTemplateFactory();
		FormTemplate formTemplate = formTemplateFactory.getFormTemplate("Console", FormTemplateEnum.FORM);
		
		if (type.equals("Component")) {
			List<FormTemplateInfo> listTemplateInfoLi = formTemplateFactory.refretorFormTemplateInfo(FormTemplateEnum.LIST);
			List<Map<String, Object>> items = getSummaryListTemplateInfoLi(listTemplateInfoLi);
			Map<String, Object> bo = New.hashMap();
			for (Object object: formTemplate.getToolbarOrDataProviderOrColumnModel()) {
				if (object instanceof ColumnModel) {
					ColumnModel columnModel = (ColumnModel)object;
					if (columnModel.getName().equals("Component")) {
						Map<String, Object> itemsDict = formTemplateFactory.getColumnModelDataForColumnModel(columnModel, bo, items);
						items = (List<Map<String, Object>>)itemsDict.get("items");
						break;
					}
				}
			}
			JSONObject json = new JSONObject();
			json.put("data", items);
			return json;
		} else if (type.equals("Selector")) {
			List<FormTemplateInfo> listTemplateInfoLi = formTemplateFactory.refretorFormTemplateInfo(FormTemplateEnum.SELECTOR);
			List<Map<String, Object>> items = getSummarySelectorTemplateInfoLi(listTemplateInfoLi);
			Map<String, Object> bo = New.hashMap();
			for (Object object: formTemplate.getToolbarOrDataProviderOrColumnModel()) {
				if (object instanceof ColumnModel) {
					ColumnModel columnModel = (ColumnModel)object;
					if (columnModel.getName().equals("Selector")) {
						Map<String, Object> itemsDict = formTemplateFactory.getColumnModelDataForColumnModel(columnModel, bo, items);
						items = (List<Map<String, Object>>)itemsDict.get("items");
						break;
					}
				}
			}
			JSONObject json = new JSONObject();
			json.put("data", items);
			return json;
		} else if (type.equals("Form")) {
			List<FormTemplateInfo> listTemplateInfoLi = formTemplateFactory.refretorFormTemplateInfo(FormTemplateEnum.FORM);
			List<Map<String, Object>> items = getSummaryFormTemplateInfoLi(listTemplateInfoLi);
			Map<String, Object> bo = New.hashMap();
			for (Object object: formTemplate.getToolbarOrDataProviderOrColumnModel()) {
				if (object instanceof ColumnModel) {
					ColumnModel columnModel = (ColumnModel)object;
					if (columnModel.getName().equals("Form")) {
						Map<String, Object> itemsDict = formTemplateFactory.getColumnModelDataForColumnModel(columnModel, bo, items);
						items = (List<Map<String, Object>>)itemsDict.get("items");
						break;
					}
				}
			}
			JSONObject json = new JSONObject();
			json.put("data", items);
			return json;
		} else if (type.equals("Datasource")) {
			DatasourceFactory datasourceFactory = new DatasourceFactory();
			List<DatasourceInfo> listTemplateInfoLi = datasourceFactory.refretorDatasourceInfo();
			List<Map<String, Object>> items = getSummaryDatasourceInfoLi(listTemplateInfoLi);
			Map<String, Object> bo = New.hashMap();
			for (Object object: formTemplate.getToolbarOrDataProviderOrColumnModel()) {
				if (object instanceof ColumnModel) {
					ColumnModel columnModel = (ColumnModel)object;
					if (columnModel.getName().equals("Datasource")) {
						Map<String, Object> itemsDict = formTemplateFactory.getColumnModelDataForColumnModel(columnModel, bo, items);
						items = (List<Map<String, Object>>)itemsDict.get("items");
						break;
					}
				}
			}
			JSONObject json = new JSONObject();
			json.put("data", items);
			return json;
		}
		JSONObject json = new JSONObject();
		json.put("message", "可能传入了错误的refretorType:" + type);
		return json;
	}
	
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		Map<String, Object> obj = schemaService.testObject();
		request.setAttribute("name", obj.get("name"));
		return new ModelAndView("model/list");
	}
	
	@RequestMapping("/listschema")
	public ModelAndView listschema(HttpServletRequest request, HttpServletResponse response) {
		FormTemplateFactory formTemplateFactory = new FormTemplateFactory();
		String datasourceName = request.getParameter("@name");
		FormTemplate formTemplate = formTemplateFactory.getFormTemplate(datasourceName, FormTemplateEnum.LIST);
		
		boolean isGetBo = true;
		boolean isFromList = true;
		Map<String, Object> result = listSelectorCommon(request, response, formTemplate, isGetBo, isFromList);
		
		String format = request.getParameter("format");
		if (StringUtils.isNotEmpty(format) && format.equalsIgnoreCase("json")) {
			String dataBoText = (String)result.get("dataBoText");
			request.setAttribute("json", dataBoText);
			return new ModelAndView("model/json");
		}
		
		/*
		tmplResult := map[string]interface{}{
			"result": result,
		}
		result["ListPageContent"] = template.HTML(self.getListPageContent(tmplResult))	// 没用,不用管,
		result["ListQueryParameterContent"] = template.HTML(self.getListQueryParameterContent(tmplResult))	// 直接页面上处理即可,
		 */
		for (String key: result.keySet()) {
			request.setAttribute(key, result.get(key));
		}
		
		String view = formTemplate.getViewTemplate().getView();
		if (view.endsWith(".jsp")) {
			view = view.replace(".jsp", "");
		}
		return new ModelAndView(view);
	}
	
	private Map<String, Object> listSelectorCommon(HttpServletRequest request, HttpServletResponse response, FormTemplate formTemplate, boolean isGetBo, boolean isFromList) {
		FormTemplateFactory formTemplateFactory = new FormTemplateFactory();
		List<Map<String, Object>> toolbarBo = formTemplateFactory.getFirstToolbarBoForFormTemplate(formTemplate);
		Map<String, String> paramMap = New.hashMap();
		
		Map<String, String> defaultBo = formTemplateFactory.getQueryDefaultValue(formTemplate);
		paramMap.putAll(defaultBo);
		String defaultBoJson = JSONObject.fromObject(defaultBo).toString();
		defaultBoJson = CommonUtil.filterJsonEmptyAttr(defaultBoJson);
		
		getCookieDataAndParamMap(request, response, formTemplate, isFromList, paramMap);
		String formDataJson = JSONObject.fromObject(paramMap).toString();
		formDataJson = CommonUtil.filterJsonEmptyAttr(formDataJson);
		
		int pageNo = 1;
		int pageSize = 10;
		DataProvider dataProvider = formTemplateFactory.getFirstDataProviderForFormTemplate(formTemplate);
		if (dataProvider.getSize() != null) {
			pageSize = dataProvider.getSize();
		}
		String pageNoText = request.getParameter("pageNo");
		if (StringUtils.isNotEmpty(pageNoText)) {
			if (Integer.parseInt(pageNoText) > 1) {
				pageNo = Integer.parseInt(pageNoText);
			}
		}
		String pageSizeText = request.getParameter("pageSize");
		if (StringUtils.isNotEmpty(pageSizeText)) {
			if (Integer.parseInt(pageSizeText) > 10) {
				pageSize = Integer.parseInt(pageSizeText);
			}
		}
		Map<String, Object> dataBo = New.hashMap();
		dataBo.put("totalResults", 0);
		dataBo.put("items", new ArrayList<Map<String, Object>>());
		
		Map<String, Object> relationBo = New.hashMap();
		Map<String, Object> usedCheckBo = New.hashMap();
		if (isGetBo) {
			dataBo = formTemplateFactory.getFirstBoForFormTemplate(formTemplate, paramMap, pageNo, pageSize);
			relationBo = (Map<String, Object>)dataBo.get("relationBo");
			
			// usedCheck的修改,
			if (StringUtils.isNotEmpty(formTemplate.getDatasourceModelId())) {
				DatasourceFactory datasourceFactory = new DatasourceFactory();
				Datasource datasource = datasourceFactory.getDatasource(formTemplate.getDatasourceModelId());
				List<Map<String, Object>> items = (List<Map<String, Object>>)dataBo.get("items");
				if (StringUtils.isNotEmpty(formTemplate.getColumnModel().get(0).getDataSetId())) {
					UsedCheck usedCheck = new UsedCheck();
					usedCheckBo = usedCheck.getListUsedCheck(datasource, items, formTemplate.getColumnModel().get(0).getDataSetId());
				}
			}
		}
		dataBo.put("usedCheckBo", usedCheckBo);
		
		String dataBoJson = JSONObject.fromObject(dataBo).toString();
		dataBoJson = CommonUtil.filterJsonEmptyAttr(dataBoJson);
		
		String relationBoJson = JSONObject.fromObject(relationBo).toString();
		relationBoJson = CommonUtil.filterJsonEmptyAttr(relationBoJson);
		
		String listTemplateJson = JSONObject.fromObject(formTemplate).toString();
		listTemplateJson = CommonUtil.filterJsonEmptyAttr(listTemplateJson);
		
		String usedCheckBoJson = JSONObject.fromObject(usedCheckBo).toString();
		usedCheckBoJson = CommonUtil.filterJsonEmptyAttr(usedCheckBoJson);
		
		List<List<Map<String, Object>>> queryParameterRenderLi = getQueryParameterRenderLi(formTemplate);
//		showParameterLi := []QueryParameter{}
		List<QueryParameter> showParameterLi = New.arrayList();
		List<QueryParameter> hiddenParameterLi = formTemplateFactory.getFirstHiddenShowParameterLiForFormTemplate(formTemplate);
		Map<String, Object> layerBo = formTemplateFactory.getDictionaryForFormTemplate(formTemplate);
		
		Map<String, Object> iLayerBo = (Map<String, Object>)layerBo.get("dictionaryBo");
		String layerBoJson = JSONObject.fromObject(iLayerBo).toString();
		layerBoJson = CommonUtil.filterJsonEmptyAttr(layerBoJson);
		
		Map<String, Object> iLayerBoLi = (Map<String, Object>)layerBo.get("dictionaryBoLi");
		String layerBoLiJson = JSONObject.fromObject(iLayerBoLi).toString();
		layerBoLiJson = CommonUtil.filterJsonEmptyAttr(layerBoLiJson);
		
		Map<String, Object> result = New.hashMap();
		result.put("pageSize", pageSize);
		result.put("listTemplate", formTemplate);
		result.put("toolbarBo", toolbarBo);
		result.put("showParameterLi", showParameterLi);
		result.put("hiddenParameterLi", hiddenParameterLi);
		result.put("queryParameterRenderLi", queryParameterRenderLi);
		result.put("dataBo", dataBo);
		result.put("dataBoText", dataBoJson);
		result.put("dataBoJson", dataBoJson);
		result.put("relationBoJson", relationBoJson);
		result.put("listTemplateJson", listTemplateJson);
		result.put("layerBoJson", layerBoJson);
		result.put("layerBoLiJson", layerBoLiJson);
		result.put("defaultBoJson", defaultBoJson);
		result.put("formDataJson", formDataJson);
		result.put("usedCheckJson", usedCheckBoJson);
		return result;
	}
	
	/**
	 * 每行6个元素,按行分隔
	 * @param formTemplate
	 * @return
	 */
	private List<List<Map<String, Object>>> getQueryParameterRenderLi(FormTemplate formTemplate) {
		final int lineColSpan = 6;
		final List<List<Map<String, Object>>> container = New.arrayList();
		final ObjectHolder<List<Map<String, Object>>> containerItem = new ObjectHolder<List<Map<String, Object>>>();
		containerItem.obj = New.arrayList();
		final ObjectHolder<Integer> lineColSpanSum = new ObjectHolder<Integer>(0);
		FormTemplateIterator.iterateFormTemplateDataProvider(formTemplate, new IFormTemplateDataProviderIterate() {
			@Override
			public void iterate(DataProvider dataProvider) {
				FormTemplateIterator.iterateFormTemplateQueryParameter(dataProvider, new IFormTemplateQueryParameterIterate() {
					@Override
					public void iterate(DataProvider dataProvider, QueryParameter queryParameter) {
						if (!queryParameter.getEditor().equals("hiddenfield")) {
							int columnColSpan = 2;
							if (queryParameter.getColSpan() != null && queryParameter.getColSpan() > 2) {
								columnColSpan = queryParameter.getColSpan();
							}
							Map<String, Object> item = New.hashMap();
							item.put("label", queryParameter.getText());
							item.put("name", queryParameter.getName());
							containerItem.obj.add(item);
							lineColSpanSum.obj += columnColSpan;
							if (lineColSpanSum.obj >= lineColSpan) {
								container.add(containerItem.obj);
								containerItem.obj = New.arrayList();
								lineColSpanSum.obj = lineColSpanSum.obj - lineColSpan;
							}
						}
					}
				});
			}
		});
		if (0 < lineColSpanSum.obj && lineColSpanSum.obj < lineColSpan) {
			container.add(containerItem.obj);
		}
		
		return container;
	}
	
	private Map<String, String> getCookieDataAndParamMap(HttpServletRequest request, HttpServletResponse response, FormTemplate formTemplate, boolean isFromList, Map<String, String> paramMap) {
		boolean isHasCookie = false;
		String cookieFormValue = request.getParameter("cookie");
		if (StringUtils.isNotEmpty(cookieFormValue) && !cookieFormValue.equals("false")) {
			isHasCookie = true;
		}
		boolean isConfigCookie = false;
		if (StringUtils.isNotEmpty(formTemplate.getCookie().getName())) {
			isConfigCookie = true;
		}
		Map<String, String> cookieData = New.hashMap();
		
		if (isFromList && isHasCookie && isConfigCookie) {
			cookieData = getCookieKeyValueMap(request, formTemplate.getCookie().getName());
			paramMap.putAll(cookieData);
		}
		Map<String, String> formQueryData = New.hashMap();
		for (Enumeration enumeration = request.getParameterNames(); enumeration.hasMoreElements(); ) {
			String key = (String)enumeration.nextElement();
			String value = StringUtils.join(request.getParameterValues(key), ",");
			paramMap.put(key, value);
			formQueryData.put(key, value);
		}
		if (isFromList && isConfigCookie && !isHasCookie) {
			Cookie cookie = new Cookie(formTemplate.getCookie().getName(), "");
			cookie.setMaxAge(3600);
			//设置路径，这个路径即该工程下都可以访问该cookie 如果不设置路径，那么只有设置该cookie路径及其子路径可以访问
			cookie.setPath("/");
			response.addCookie(cookie);
		} else if (isFromList && isConfigCookie && isHasCookie) {
			Map<String, String> cookieFormQueryData = New.hashMap();
			cookieFormQueryData.putAll(cookieData);
			cookieFormQueryData.putAll(formQueryData);
			
			JSONObject object = JSONObject.fromObject(cookieFormQueryData);
			String value = object.toString().replace("\"", "&quote");
			Cookie cookie = new Cookie(formTemplate.getCookie().getName(), value);
			cookie.setMaxAge(3600);
			//设置路径，这个路径即该工程下都可以访问该cookie 如果不设置路径，那么只有设置该cookie路径及其子路径可以访问
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		cookieData = getCookieKeyValueMap(request, formTemplate.getCookie().getName());
		
		return cookieData;
	}
	
	private Map<String, String> getCookieKeyValueMap(HttpServletRequest request, String cookieName) {
		Map<String, String> cookieData = New.hashMap();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie: cookies) {
				if (cookie.getName().equals(cookieName)) {
					 String value = cookie.getValue().replace("&quote", "\"");
					JSONObject object = JSONObject.fromObject(value);
					cookieData.putAll(object);
				}
			}
		}
		return cookieData;
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

	public UsedCheck getUsedCheck() {
		return usedCheck;
	}

	public void setUsedCheck(UsedCheck usedCheck) {
		this.usedCheck = usedCheck;
	}
}
