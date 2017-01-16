package com.javameta.web.model.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.javameta.model.template.ColumnModel;
import com.javameta.model.template.FormTemplate;
import com.javameta.model.template.FormTemplateInfo;
import com.javameta.model.template.Toolbar;
import com.javameta.util.New;
import com.javameta.web.model.service.SchemaService;
import com.javameta.web.support.ControllerSupport;

@Scope("prototype")
@Controller
@RequestMapping("/schema")
public class SchemaController extends ControllerSupport {
	private static final long serialVersionUID = 1L;
	@Autowired
	private SchemaService schemaService;
	
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
