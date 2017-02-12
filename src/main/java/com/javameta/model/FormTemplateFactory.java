package com.javameta.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;

import com.javameta.JavametaException;
import com.javameta.expression.ExpressionParser;
import com.javameta.freemarker.FreemarkerParser;
import com.javameta.model.adapter.IFormTemplateAdapter;
import com.javameta.model.iterate.FormTemplateIterator;
import com.javameta.model.iterate.IFormTemplateButtonIterate;
import com.javameta.model.iterate.IFormTemplateColumnIterate;
import com.javameta.model.iterate.IFormTemplateColumnModelIterate;
import com.javameta.model.iterate.IFormTemplateDataProviderIterate;
import com.javameta.model.iterate.IFormTemplateQueryParameterIterate;
import com.javameta.model.queryparameter.QueryParameterBuilder;
import com.javameta.model.template.AutoColumn;
import com.javameta.model.template.Button;
import com.javameta.model.template.CheckboxColumn;
import com.javameta.model.template.Column;
import com.javameta.model.template.ColumnModel;
import com.javameta.model.template.DataProvider;
import com.javameta.model.template.DictionaryColumn;
import com.javameta.model.template.Expression;
import com.javameta.model.template.FormTemplate;
import com.javameta.model.template.FormTemplateInfo;
import com.javameta.model.template.QueryParameters;
import com.javameta.model.template.QueryParameters.QueryParameter;
import com.javameta.model.template.QueryParameters.QueryParameter.ParameterAttribute;
import com.javameta.model.template.RelationDS;
import com.javameta.model.template.RelationDS.RelationItem;
import com.javameta.model.template.StringColumn;
import com.javameta.model.template.Toolbar;
import com.javameta.model.template.TriggerColumn;
import com.javameta.model.template.VirtualColumn;
import com.javameta.util.ApplicationContextUtil;
import com.javameta.util.JavametaProperties;
import com.javameta.util.New;

public class FormTemplateFactory {
	private static Class[] classes = new Class[] { com.javameta.model.fieldpool.ObjectFactory.class, com.javameta.model.template.ObjectFactory.class,
			com.javameta.model.datasource.ObjectFactory.class, };

	private static JAXBContext context;
	static {
		try {
			context = JAXBContext.newInstance(classes);
		} catch (JAXBException e) {
			throw new JavametaException(e);
		}
	}
	private static Map<String, FormTemplateInfo> gListTemplateInfoMap = new ConcurrentHashMap<String, FormTemplateInfo>();
	private static Map<String, FormTemplateInfo> gFormTemplateInfoMap = new ConcurrentHashMap<String, FormTemplateInfo>();
	private static Map<String, FormTemplateInfo> gQueryTemplateInfoMap = new ConcurrentHashMap<String, FormTemplateInfo>();
	private static Map<String, FormTemplateInfo> gSelectorTemplateInfoMap = new ConcurrentHashMap<String, FormTemplateInfo>();
	private static Object lock = new Object();

	public List<FormTemplateInfo> getFormTemplateInfoLi(FormTemplateEnum formTemplateEnum) {
		synchronized (lock) {
			List<FormTemplateInfo> list = New.arrayList();
			if (getTemplateInfoMap(formTemplateEnum).size() == 0) {
				loadFormTemplate(formTemplateEnum);
			}

			for (Map.Entry<String, FormTemplateInfo> item : getTemplateInfoMap(formTemplateEnum).entrySet()) {
				list.add(SerializationUtils.clone(item.getValue()));
			}
			return list;
		}
	}

	public List<FormTemplateInfo> refretorFormTemplateInfo(FormTemplateEnum formTemplateEnum) {
		clearFormTemplate(formTemplateEnum);
		loadFormTemplate(formTemplateEnum);
		List<FormTemplateInfo> list = New.arrayList();
		for (Map.Entry<String, FormTemplateInfo> item : getTemplateInfoMap(formTemplateEnum).entrySet()) {
			list.add(SerializationUtils.clone(item.getValue()));
		}
		return list;
	}

	public FormTemplate getFormTemplate(String id, FormTemplateEnum formTemplateEnum) {
		return getFormTemplateInfo(id, formTemplateEnum).getFormTemplate();
	}

	public FormTemplateInfo getFormTemplateInfo(String id, FormTemplateEnum formTemplateEnum) {
		if (JavametaProperties.getProperty("debug").equals("true")) {
			FormTemplateInfo formTemplateInfo = findFormTemplateInfo(id, formTemplateEnum);
			if (formTemplateInfo != null) {
				formTemplateInfo = loadSingleFormTemplate(new File(formTemplateInfo.getPath()), formTemplateEnum);
				if (formTemplateInfo.getFormTemplate().getId().equals(id)) {
					return SerializationUtils.clone(formTemplateInfo);
				}
			}
			clearFormTemplate(formTemplateEnum);
			loadFormTemplate(formTemplateEnum);
			formTemplateInfo = findFormTemplateInfo(id, formTemplateEnum);
			if (formTemplateInfo != null) {
				return SerializationUtils.clone(formTemplateInfo);
			}
			throw new JavametaException(id + " not exists in FormTemplate list");
		}
		synchronized (lock) {
			if (getTemplateInfoMap(formTemplateEnum).size() == 0) {
				loadFormTemplate(formTemplateEnum);
			}
		}
		FormTemplateInfo formTemplateInfo = findFormTemplateInfo(id, formTemplateEnum);
		if (formTemplateInfo != null) {
			return SerializationUtils.clone(formTemplateInfo);
		}
		throw new JavametaException(id + " not exists in FormTemplate list");
	}

	private FormTemplateInfo findFormTemplateInfo(String id, FormTemplateEnum formTemplateEnum) {
		FormTemplateInfo formTemplateInfo = getTemplateInfoMap(formTemplateEnum).get(id);
		if (formTemplateInfo != null) {
			return formTemplateInfo;
		}
		return null;
	}

	private void clearFormTemplate(FormTemplateEnum formTemplateEnum) {
		synchronized (lock) {
			getTemplateInfoMap(formTemplateEnum).clear();
		}
	}

	private void loadFormTemplate(FormTemplateEnum formTemplateEnum) {
		synchronized (lock) {
			URL url = this.getClass().getClassLoader().getResource("");
			String scanPath = url.getPath();
			recursiveLoadFormTemplate(new File(scanPath), formTemplateEnum);
		}
	}

	private void recursiveLoadFormTemplate(File file, FormTemplateEnum formTemplateEnum) {
		if (file.isDirectory()) {
			for (File subFile : file.listFiles()) {
				recursiveLoadFormTemplate(subFile, formTemplateEnum);
			}
		} else {
			if (file.getName().startsWith(formTemplateEnum.getPrefix()) && file.getName().endsWith(".xml")) {
				loadSingleFormTemplate(file, formTemplateEnum);
			}
		}
	}
	
	public FormTemplate unmarshal(String path) {
		try {
			Unmarshaller unmarshaller = context.createUnmarshaller();
			FormTemplate formTemplate = (FormTemplate) unmarshaller.unmarshal(new File(path));
			return formTemplate;
		} catch (JAXBException e) {
			throw new JavametaException(e);
		}
	}
	
	public String marshal(FormTemplate formTemplate) {
		try {
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			marshaller.marshal(formTemplate, out);
			return out.toString();
		} catch (JAXBException e) {
			throw new JavametaException(e);
		}
	}

	private FormTemplateInfo loadSingleFormTemplate(File file, FormTemplateEnum formTemplateEnum) {
		FormTemplateInfo formTemplateInfo = new FormTemplateInfo();
		try {
			Unmarshaller unmarshaller = context.createUnmarshaller();
			FormTemplate formTemplate = (FormTemplate) unmarshaller.unmarshal(file);

			if (formTemplate.getAdapter() != null && StringUtils.isNotEmpty(formTemplate.getAdapter().getName())) {
				try {
					IFormTemplateAdapter adapter = (IFormTemplateAdapter) (Class.forName(formTemplate.getAdapter().getName()).newInstance());
					adapter.applyAdapter(formTemplate);
				} catch (Exception e) {
					throw new JavametaException(e);
				}
			}

			formTemplateInfo.setPath(file.getAbsolutePath());
			formTemplateInfo.setFormTemplate(formTemplate);

			getTemplateInfoMap(formTemplateEnum).put(formTemplate.getId(), formTemplateInfo);
		} catch (JAXBException e) {
			throw new JavametaException(e);
		}

		return formTemplateInfo;
	}

	private Map<String, FormTemplateInfo> getTemplateInfoMap(FormTemplateEnum formTemplateEnum) {
		switch (formTemplateEnum) {
		case LIST: {
			return gListTemplateInfoMap;
		}
		case FORM: {
			return gFormTemplateInfoMap;
		}
		case SELECTOR: {
			return gSelectorTemplateInfoMap;
		}
		case QUERY: {
			return gQueryTemplateInfoMap;
		}
		}
		return null;
	}

	/**
	 * 1.bodySql = columnModel.dataProvider->dataProvider.sql
	 * 2.bodySql += dataProvider.queryParameters
	 * 3.bodySql += columnModel.column-item + bodySql
	 * @param formTemplate
	 * @param columnModel
	 * @param paramMap
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> queryDataForColumnModel(FormTemplate formTemplate, ColumnModel columnModel, Map<String, String> paramMap, int pageNo, int pageSize) {
		Map<String, Object> result = New.hashMap();

		DataProvider dataProvider = formTemplate.getDataProvider(columnModel.getDataProvider());

		if (dataProvider == null) {
			throw new JavametaException("columnModel name:" + columnModel.getName() + " can't found dataProvider:" + columnModel.getDataProvider());
		}

		String bodySql = dataProvider.getSql();
		if (bodySql.indexOf("<#") > -1) {
			Map<String, Object> parseMap = New.hashMap();
			parseMap.putAll(paramMap);
			bodySql = FreemarkerParser.parse(bodySql, parseMap);
		}
		Map<String, Object> nameParameterMap = New.hashMap();
		nameParameterMap.putAll(paramMap);
		if (StringUtils.isNotEmpty(dataProvider.getSqlIntercept())) {
			try {
				SqlIntercept sqlIntercept = (SqlIntercept) Class.forName(dataProvider.getSqlIntercept()).newInstance();
				bodySql = sqlIntercept.getQuerySql(bodySql, columnModel, dataProvider, paramMap, nameParameterMap);
			} catch (Exception e) {
				throw new JavametaException(e);
			}
		}
		bodySql = " ( " + bodySql + " ) s ";
		bodySql += " where 1=1 ";
		if (formTemplate.getSecurity() != null) {
			FormTemplateDao formTemplateDao = (FormTemplateDao) ApplicationContextUtil.getApplicationContext().getBean("formTemplateDao");
			String securitySql = formTemplateDao.getSecurity(formTemplate.getSecurity());
			if (StringUtils.isNotEmpty(securitySql)) {
				bodySql += " " + securitySql;
			}
		}
		if (dataProvider.getQueryParameters() != null) {
			QueryParameterBuilder queryParameterBuilder = new QueryParameterBuilder();
			for (QueryParameter queryParameter : dataProvider.getQueryParameters().getQueryParameter()) {
				if (StringUtils.isNotEmpty(queryParameter.getEditor())) {
					if (StringUtils.isNotEmpty(queryParameter.getRestriction())) {
						String value = paramMap.get(queryParameter.getName());
						String queryParameterSql = queryParameterBuilder.buildQuery(queryParameter, value, nameParameterMap);
						if (StringUtils.isEmpty(queryParameter.getUseIn()) || !queryParameter.getUseIn().equals("none")) {
							bodySql += queryParameterSql;
						}
					}
				}
			}
		}
		if (StringUtils.isNotEmpty(dataProvider.getSuffix())) {
			if (dataProvider.getSuffix().indexOf("<#") > -1) {
				Map<String, Object> parseMap = New.hashMap();
				parseMap.putAll(paramMap);
				bodySql += " " + FreemarkerParser.parse(dataProvider.getSuffix(), parseMap) + " ";
			} else {
				bodySql += " " + dataProvider.getSuffix() + " ";
			}
		}

		FormTemplateDao formTemplateDao = (FormTemplateDao) ApplicationContextUtil.getApplicationContext().getBean("formTemplateDao");

		List<String> columnNameLi = New.arrayList();
		List<Column> columnLi = New.arrayList();
		recursionGetColumnItem(columnModel, columnLi);
		for (Column column : columnLi) {
			if (!(column instanceof VirtualColumn)) {
				if (column instanceof AutoColumn) {
					AutoColumn autoColumn = (AutoColumn) column;
					if (!(autoColumn.getColumnModel() != null && autoColumn.getColumnModel().getColumnList().size() > 0)) {
						columnNameLi.add(column.getName());
					}
				} else if (column instanceof StringColumn) {
					StringColumn stringColumn = (StringColumn) column;
					if (!(stringColumn.getColumnModel() != null && stringColumn.getColumnModel().getColumnList().size() > 0)) {
						columnNameLi.add(column.getName());
					}
				} else {
					columnNameLi.add(column.getName());
				}
			}
		}

		int offset = (pageNo - 1) * pageSize;
		String orderBy = "";
		if (StringUtils.isNotEmpty(columnModel.getSqlOrderBy())) {
				orderBy = " order by " + columnModel.getSqlOrderBy() + " ";
		}
		String selectSql = "select " + StringUtils.join(columnNameLi.toArray(), ",") + " from " + bodySql + orderBy + " limit " + offset + "," + pageSize;
		List<Map<String, Object>> items = formTemplateDao.getNamedParameterJdbcTemplate().queryForList(selectSql, nameParameterMap);

		int totalResults = 0;
		if (pageSize > 1) {// 只取一条时,不计算count(*)
			if ((pageNo == 1) && (items.size() < pageSize)) {
				totalResults = items.size();
			} else {
				String countSql = "select count(*) from " + bodySql;
				totalResults = formTemplateDao.getNamedParameterJdbcTemplate().queryForInt(countSql, nameParameterMap);
			}
		} else if (pageSize == 1) {
			totalResults = items.size();
		}

		result.put("totalResults", totalResults);
		result.put("items", items);

		return result;
	}

	/**
	 * @param columnModel
	 * @param bo 没用到主数据集字段时,放空Map,
	 * @param items,
	 * 			checkboxColumn.name: true|false,
	 * 			virtualColumn.name: {
	 * 				buttons: [{isShow: true}, {isShow: false}, {}, ....]
	 * 			}
	 * @return
	 */
	public Map<String, Object> getColumnModelDataForColumnModel(ColumnModel columnModel, Map<String, Object> bo, List<Map<String, Object>> items) {
		List<Column> columnLi = New.arrayList();
		recursionGetColumnItem(columnModel, columnLi);
		Map<String, Object> relationBo = New.hashMap();
		List<Map<String, Object>> columnModelItems = New.arrayList();

		for (Map<String, Object> record : items) {
			Map<String, Object> loopItem = New.hashMap();

			CheckboxColumn checkboxColumn = columnModel.getCheckboxColumn();
			if (checkboxColumn != null) {// form页面的column-model有可能没有checkbox,
				JSONObject recordJson = convertMapToJSONObject(record);
				loopItem.put(checkboxColumn.getName(), parseExpressionBoolean(recordJson, checkboxColumn.getExpression()));
			}
			if (columnModel.getIdColumn() != null) {// form页面的column-model有可能没有id-column,
				loopItem.put(columnModel.getIdColumn().getName(), record.get(columnModel.getIdColumn().getName()));
			}
			for (Column column : columnLi) {
				getColumnModelDataForColumnItem(column, bo, record, relationBo, loopItem);
			}
			columnModelItems.add(loopItem);
		}

		Map<String, Object> result = New.hashMap();
		result.put("items", columnModelItems);
		result.put("relationBo", relationBo);
		return result;
	}
	
	private JSONObject convertMapToJSONObject(Map<String, Object> object) {
		Map<String, Object> result = New.hashMap();
		for (String key: object.keySet()) {
			Object value = object.get(key);
			result.put(key, convertDateObjectToString(value));
		}
		return JSONObject.fromObject(result);
	}
	
	private Object convertDateObjectToString(Object value) {
		if (value != null && value instanceof java.sql.Date) {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			return format.format((java.sql.Date)value);
		} else if (value != null && value instanceof java.sql.Timestamp) {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			return format.format((java.sql.Timestamp)value);
		} else if (value != null && value instanceof java.sql.Time) {
			SimpleDateFormat format = new SimpleDateFormat("HHmmss");
			return format.format((java.sql.Timestamp)value);
		} else {
			return value;
		}
	}
	
//	func (o TemplateManager) GetColumnModelDataForFormTemplate(sessionId int, formTemplate FormTemplate, bo map[string]interface{}) map[string]interface{} {
	/**
	 * @param formTemplate
	 * @param bo {A: {}, B:[{}, {}, ...], C:[{}, {}, ...]}
	 * @return
	 */
	public Map<String, Object> getColumnModelDataForFormTemplate(FormTemplate formTemplate, final Map<String, Object> bo) {
		final Map<String, Object> relationBo = New.hashMap();
		FormTemplateIterator.iterateFormTemplateColumnModel(formTemplate, new IFormTemplateColumnModelIterate() {
			@Override
			public void iterate(ColumnModel columnModel) {
				if (bo.get(columnModel.getDataSetId()) != null) {
					if (columnModel.getDataSetId().equals("A")) {
						List<Map<String, Object>> items = New.arrayList();
						items.add((Map<String, Object>)bo.get("A"));
						Map<String, Object> columnModelData = getColumnModelDataForColumnModel(columnModel, bo, items);
						List<Map<String, Object>> columnModelItems = (List<Map<String, Object>>)columnModelData.get("items");
						if (columnModelItems.size() > 0) {
							// 主数据集可以有多个,所以需要进行累加,
							if (bo.get("A") == null) {
								bo.put("A", new HashMap<String, Object>());
							}
							Map<String, Object> aDict = (Map<String, Object>)bo.get("A");
							aDict.putAll(columnModelItems.get(0));
							Map<String, Object> columnModelRelationBo = (Map<String, Object>)columnModelData.get("relationBo");
							mergeRelationBo(relationBo, columnModelRelationBo);
						}
					} else {
						List<Map<String, Object>> items = (List<Map<String, Object>>)bo.get(columnModel.getDataSetId());
						Map<String, Object> columnModelData = getColumnModelDataForColumnModel(columnModel, bo, items);
						items = (List<Map<String, Object>>)columnModelData.get("items");
						bo.put(columnModel.getDataSetId(), items);
						Map<String, Object> columnModelRelationBo = (Map<String, Object>)columnModelData.get("relationBo");
						mergeRelationBo(relationBo, columnModelRelationBo);
					}
				}
			}
		});
		this.mergeSelectorInfo2RelationBo(formTemplate, relationBo);
		
		Map<String, Object> result = New.hashMap();
		result.put("bo", bo);
		result.put("relationBo", relationBo);
		return result;
	}
	
	/**
	 * @param relationDS
	 * @param relationBo {
				BankAccountSelector:{
					1: {id:xxx, field1:xxxx,},
					2: {id:xxx, field1:xxxx,},
					url:
					description:
				},
				CashAccountSelector:{
					1: {id:xxx, field1:xxxx,},
					2: {id:xxx, field1:xxxx,},
					url:
					description:
				},
			}
	 */
	private void mergeSelectorInfo2RelationBoFromRelationDS(RelationDS relationDS, Map<String, Object> relationBo) {
		if (relationDS != null) {
			for (RelationItem relationItem: relationDS.getRelationItem()) {
				String selectorName = relationItem.getRelationConfig().getSelectorName();
				if (relationBo.get(selectorName) != null) {
					Map<String, Object> selectorDict = (Map<String, Object>)relationBo.get(selectorName);
					if (selectorDict.get("description") == null) {
						FormTemplate formTemplate = this.getFormTemplate(selectorName, FormTemplateEnum.SELECTOR);
						selectorDict.put("description", formTemplate.getDescription());
					}
					if (selectorDict.get("url") == null) {
						FormTemplate formTemplate = this.getFormTemplate(selectorName, FormTemplateEnum.SELECTOR);
						selectorDict.put("url", getViewUrl(formTemplate));
					}
				} else {
					Map<String, Object> selectorDict = New.hashMap();
					FormTemplate formTemplate = this.getFormTemplate(selectorName, FormTemplateEnum.SELECTOR);
					selectorDict.put("description", formTemplate.getDescription());
					selectorDict.put("url", getViewUrl(formTemplate));
					relationBo.put(selectorName, selectorDict);
				}
			}
		}
	}
	
	/**
	 * @param formTemplate
	 * @param relationBo {
				BankAccountSelector:{
					1: {id:xxx, field1:xxxx,},
					2: {id:xxx, field1:xxxx,},
					url:
					description:
				},
				CashAccountSelector:{
					1: {id:xxx, field1:xxxx,},
					2: {id:xxx, field1:xxxx,},
					url:
					description:
				},
			}
	 */
	private void mergeSelectorInfo2RelationBo(FormTemplate formTemplate, final Map<String, Object> relationBo) {
		FormTemplateIterator.iterateFormTemplateColumn(formTemplate, new IFormTemplateColumnIterate() {
			@Override
			public void iterate(Column column) {
				if (column instanceof AutoColumn) {
					AutoColumn autoColumn = (AutoColumn)column;
					mergeSelectorInfo2RelationBoFromRelationDS(autoColumn.getRelationDS(), relationBo);
				} else if (column instanceof TriggerColumn) {
					TriggerColumn autoColumn = (TriggerColumn)column;
					mergeSelectorInfo2RelationBoFromRelationDS(autoColumn.getRelationDS(), relationBo);
				}
			}
		});
		FormTemplateIterator.iterateFormTemplateButton(formTemplate, new IFormTemplateButtonIterate() {
			@Override
			public void iterate(Toolbar toolbar, ColumnModel columnModel, Button button) {
				mergeSelectorInfo2RelationBoFromRelationDS(button.getRelationDS(), relationBo);
			}
		});
		FormTemplateIterator.iterateFormTemplateDataProvider(formTemplate, new IFormTemplateDataProviderIterate() {
			@Override
			public void iterate(DataProvider dataProvider) {
				FormTemplateIterator.iterateFormTemplateQueryParameter(dataProvider, new IFormTemplateQueryParameterIterate() {
					@Override
					public void iterate(DataProvider dataProvider, QueryParameter queryParameter) {
						mergeSelectorInfo2RelationBoFromRelationDS(queryParameter.getRelationDS(), relationBo);
					}
				});
			}
		});
	}

	/**
	 * 取得带<relationDS>元素中选择器相关信息
	 * @param formTemplate
	 * @return
	 */
	public Map<String, Object> getSelectorInfoForFormTemplate(FormTemplate formTemplate) {
		Map<String, Object> relationBo = New.hashMap();
		mergeSelectorInfo2RelationBo(formTemplate, relationBo);
		return relationBo;
	}
	
	/**
	 * 列表页通常只有一个toolbar
	 * @param formTemplate
	 * @return
	 */
	public List<Map<String, Object>> getFirstToolbarBoForFormTemplate(FormTemplate formTemplate) {
		List<Toolbar> list = formTemplate.getToolbar();
		if (list.size() > 0) {
			return getToolbarBo(list.get(0));
		}
		return New.arrayList();
	}
	
	/**
	 * 列表页通常只有一个dataProvider
	 * @param formTemplate
	 * @return
	 */
	public DataProvider getFirstDataProviderForFormTemplate(FormTemplate formTemplate) {
		for (Object object: formTemplate.getToolbarOrDataProviderOrColumnModel()) {
			if (object instanceof DataProvider) {
				return (DataProvider)object;
			}
		}
		return null;
	}
	
	/**
	 * 返回每个按钮的显示或隐藏,[{isShow: true|false}, {isShow: true|false}, {isShow: true|false}, ...]
	 * @param toolbar
	 * @return
	 */
	public List<Map<String, Object>> getToolbarBo(Toolbar toolbar) {
		List<Map<String, Object>> result = New.arrayList();
		for (Button button: toolbar.getButton()) {
			Map<String, Object> line = New.hashMap();
			boolean isShow = parseExpressionBoolean(new JSONObject(), button.getExpression());
			line.put("name", button.getName());
			line.put("isShow", isShow);
			result.add(line);
		}
		return result;
	}
	
	/**
	 * 列表页获取业务对象
	 * @param formTemplate
	 * @param paramMap
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getFirstBoForFormTemplate(FormTemplate formTemplate, Map<String, String> paramMap, int pageNo, int pageSize) {
		ColumnModel columnModel = formTemplate.getColumnModel().get(0);
		Map<String, Object> queryResult = queryDataForColumnModel(formTemplate, columnModel, paramMap, pageNo, pageSize);
		List<Map<String, Object>> items = (List<Map<String, Object>>)queryResult.get("items");
		Map<String, Object> emptyBo = New.hashMap();
		Map<String, Object> itemsDict = getColumnModelDataForColumnModel(columnModel, emptyBo, items);
		Map<String, Object> relationBo = (Map<String, Object>)itemsDict.get("relationBo");
		Map<String, Object> selectorInfo = getSelectorInfoForFormTemplate(formTemplate);
		mergeRelationBo(relationBo, selectorInfo);
		
		Map<String, Object> result = New.hashMap();
		result.put("totalResults", queryResult.get("totalResults"));
		result.put("items", itemsDict.get("items"));
		result.put("relationBo", relationBo);
		return result;
	}
	
	/**
	 * 为列表页提供的方法,取得editor != hiddenfield的queryParameter
	 * @param formTemplate
	 * @return
	 */
	public List<QueryParameter> getFirstShowParameterLiForFormTemplate(FormTemplate formTemplate) {
		List<QueryParameter> queryParameterLi = New.arrayList();
		QueryParameters queryParameters = formTemplate.getDataProvider().get(0).getQueryParameters();
		if (queryParameters != null) {
			for (QueryParameter queryParameter: queryParameters.getQueryParameter()) {
				if (!queryParameter.getEditor().equals("hiddenfield")) {
					queryParameterLi.add(queryParameter);
				}
			}
		}
		return queryParameterLi;
	}

	/**
	 * 为列表页提供的方法,取得editor == hiddenfield的queryParameter
	 * @param formTemplate
	 * @return
	 */
	public List<QueryParameter> getFirstHiddenShowParameterLiForFormTemplate(FormTemplate formTemplate) {
		List<QueryParameter> queryParameterLi = New.arrayList();
		QueryParameters queryParameters = formTemplate.getDataProvider().get(0).getQueryParameters();
		if (queryParameters != null) {
			for (QueryParameter queryParameter: queryParameters.getQueryParameter()) {
				if (queryParameter.getEditor().equals("hiddenfield")) {
					queryParameterLi.add(queryParameter);
				}
			}
		}
		return queryParameterLi;
	}

//	func (o TemplateManager) GetLayerForFormTemplate(sId int, formTemplate FormTemplate) map[string]interface{} {
	/**
	 * 找出formTemplate中所有的字典
	 * @param formTemplate
	 * @return {
	 * 		dictionaryBo: {
	 * 			D_YESNO: {
	 * 				0:{code:0, name:否, diclist_id:1},
	 * 				1:{code:1, name:是, diclist_id:1}
	 * 			},
	 * 			D_BILLSTATUS: {
	 * 				0:{code:0, name:否, diclist_id:1},
	 * 				1:{code:1, name:是, diclist_id:1}
	 * 			}
	 * 		},
	 * 		dictionaryBoLi: {
	 * 			D_YESNO: [{code:0, name:否, diclist_id:1}, {code:1, name:是, diclist_id:1}, ...],
	 * 			D_BILLSTATUS: [{code:0, name:否, diclist_id:1}, {code:1, name:是, diclist_id:1}, ...]
	 * 		}
	 * }
	 */
	public Map<String, Object> getDictionaryForFormTemplate(FormTemplate formTemplate) {
		final Map<String, Object> dictionaryBo = New.hashMap();
		final Map<String, Object> dictionaryBoLi = New.hashMap();
		final DictionaryFactory dictionaryFactory = new DictionaryFactory();
		final Set<String> dictionaryLi = New.hashSet();
		FormTemplateIterator.iterateFormTemplateColumnModel(formTemplate, new IFormTemplateColumnModelIterate() {
			@Override
			public void iterate(ColumnModel columnModel) {
				List<Column> columnLi = New.arrayList();
				recursionGetColumnItem(columnModel, columnLi);
				for (Column column: columnLi) {
					if (column instanceof DictionaryColumn) {
						DictionaryColumn dictionaryColumn = (DictionaryColumn)column;
						dictionaryLi.add(dictionaryColumn.getDictionary());
					}
				}
			}
		});
		FormTemplateIterator.iterateFormTemplateDataProvider(formTemplate, new IFormTemplateDataProviderIterate() {
			@Override
			public void iterate(DataProvider dataProvider) {
				FormTemplateIterator.iterateFormTemplateQueryParameter(dataProvider, new IFormTemplateQueryParameterIterate() {
					@Override
					public void iterate(DataProvider dataProvider, QueryParameter queryParameter) {
						List<ParameterAttribute> parameterAttributeLi = queryParameter.getParameterAttribute();
						if (parameterAttributeLi != null && parameterAttributeLi.size() > 0) {
							for (ParameterAttribute parameterAttribute: parameterAttributeLi) {
								if (parameterAttribute.getName().equals("dictionary")) {
									dictionaryLi.add(parameterAttribute.getValue());
								}
							}
						}
					}
				});
			}
		});
		for (String dictionary: dictionaryLi) {
			List<Map<String, Object>> dictionaryItems = dictionaryFactory.getDictionary(dictionary);
			Map<String, Object> dictMap = New.hashMap();
			for (Map<String, Object> dictionaryItem: dictionaryItems) {
				String code = (String)dictionaryItem.get("code");
				dictMap.put(code, dictionaryItem);
			}
			dictionaryBo.put(dictionary, dictMap);
			dictionaryBoLi.put(dictionary, dictionaryItems);
		}
		
		Map<String, Object> result = New.hashMap();
		result.put("dictionaryBo", dictionaryBo);
		result.put("dictionaryBoLi", dictionaryBoLi);
		return result;
	}
	
	/**
	 * 合并关联信息
	 * @param relationBo {
				BankAccountSelector:{
					1: {id:xxx, field1:xxxx,},
					2: {id:xxx, field1:xxxx,},
					url:
					description:
				},
				CashAccountSelector:{
					1: {id:xxx, field1:xxxx,},
					2: {id:xxx, field1:xxxx,},
					url:
					description:
				},
			}
	 * @param columnModelRelationBo 结构与relationBo相同
	 */
	private void mergeRelationBo(Map<String, Object> relationBo, Map<String, Object> columnModelRelationBo) {
		for (Map.Entry<String, Object> entry: columnModelRelationBo.entrySet()) {
			if (relationBo.get(entry.getKey()) == null) {
				relationBo.put(entry.getKey(), entry.getValue());
			} else {
				Map<String, Object> relationBoItem = (Map<String, Object>)relationBo.get(entry.getKey());
				Map<String, Object> columnModelRelationBoItem = (Map<String, Object>)entry.getValue();
				for (Map.Entry<String, Object> subEntry: columnModelRelationBoItem.entrySet()) {
					if (relationBoItem.get(subEntry.getKey()) == null) {
						relationBoItem.put(subEntry.getKey(), subEntry.getValue());
					}
				}
			}
		}
	}

	/**
	 * 日期字段会被转为无格式string
	 * @param column
	 * @param bo 没用到主数据集字段时,放空Map,
	 * @param record
	 * @param relationBo {
				BankAccountSelector:{
					1: {id:xxx, field1:xxxx,},
					2: {id:xxx, field1:xxxx,},
					url:
					description:
				},
				CashAccountSelector:{
					1: {id:xxx, field1:xxxx,},
					2: {id:xxx, field1:xxxx,},
					url:
					description:
				},
			}
	 * @param loopItem 
	 * 			virtualColumn.name: {
	 * 				buttons: [{isShow: true}, {isShow: false}, {}, ....]
	 * 			}
	 */
	private void getColumnModelDataForColumnItem(Column column, Map<String, Object> bo, Map<String, Object> record, Map<String, Object> relationBo, Map<String, Object> loopItem) {
		if (column instanceof VirtualColumn) {
			String key = ((VirtualColumn) column).getButtons().getXmlName();
			if (loopItem.get(column.getName()) == null) {
				Map<String, Object> virtualColumnMap = New.hashMap();
				List<Object> buttons = New.arrayList();
				virtualColumnMap.put(key, buttons);
				loopItem.put(column.getName(), virtualColumnMap);
			}
			Map<String, Object> virtualColumnMap = (Map<String, Object>) loopItem.get(column.getName());
			List<Object> buttons = (List<Object>) virtualColumnMap.get(key);
			VirtualColumn virtualColumn = (VirtualColumn) column;
			if (virtualColumn.getButtons() != null) {
				for (Button button : virtualColumn.getButtons().getButton()) {
					Map<String, Object> buttonMap = New.hashMap();
//					JSONObject recordJson = JSONObject.fromObject(record);
					JSONObject recordJson = convertMapToJSONObject(record);
					buttonMap.put("isShow", parseExpressionBoolean(recordJson, button.getExpression()));
					buttons.add(buttonMap);
				}
			}
		} else if (column instanceof TriggerColumn) {
			loopItem.put(column.getName(), record.get(column.getName()));
			if (loopItem.get(column.getName()) != null) {
				String value = ObjectUtils.toString(record.get(column.getName()), "");
				if (StringUtils.isNotEmpty(value)) {
					for (String valueItem : value.split(",")) {
						applyRelationBoByTriggerField((TriggerColumn) column, valueItem, bo, record, relationBo);
					}
				}
			}
		} else {
			Object convertObject = convertDateObjectToString(record.get(column.getName()));
			loopItem.put(column.getName(), convertObject);
		}
	}

	/**
	 * 取得关联字段的关联记录值
	 * @param column
	 * @param value
	 * @param bo 没用到主数据集字段时,放空Map,
	 * @param record
	 * @param relationBo {
				BankAccountSelector:{
					1: {id:xxx, field1:xxxx,},
					2: {id:xxx, field1:xxxx,},
					url:
					description:
				},
				CashAccountSelector:{
					1: {id:xxx, field1:xxxx,},
					2: {id:xxx, field1:xxxx,},
					url:
					description:
				},
			}
	 */
	private void applyRelationBoByTriggerField(TriggerColumn column, String value, Map<String, Object> bo, Map<String, Object> record, Map<String, Object> relationBo) {
		JSONObject boJson = JSONObject.fromObject(bo);
//		JSONObject recordJson = JSONObject.fromObject(record);
		JSONObject recordJson = convertMapToJSONObject(record);
		if (column.getRelationDS() != null && column.getRelationDS().getRelationItem().size() > 0) {
			for (RelationDS.RelationItem relationItem : column.getRelationDS().getRelationItem()) {
				boolean relationExpr = parseRelationExprBoolean(boJson, recordJson, relationItem.getRelationExpr());
				if (relationExpr) {
					String selectorName = relationItem.getRelationConfig().getSelectorName();
					if (relationBo.get(selectorName) != null) {
						Map<String, Object> selectorDict = (Map<String, Object>) relationBo.get(selectorName);
						if (selectorDict.get(value) != null) {
							continue;
						}
					}
					List<Map<String, Object>> li = New.arrayList();
					Map<String, Object> liItem = New.hashMap();
					liItem.put("relationId", value);
					liItem.put("selectorId", selectorName);
					liItem.put("valueField", relationItem.getRelationConfig().getValueField());
					li.add(liItem);
					Map<String, Object> singleRelationBo = getRelationBo(li);
					if (singleRelationBo.get(selectorName) != null) {
						Map<String, Object> singleRelationItem = (Map<String, Object>) singleRelationBo.get(selectorName);
						if (relationBo.get(selectorName) == null) {
							relationBo.put(selectorName, new HashMap<String, Object>());
						}
						Map<String, Object> relationItemMap = (Map<String, Object>) relationBo.get(selectorName);
						relationItemMap.putAll(singleRelationItem);
					}
				}
			}
		}
	}

	/**
	 * @param relationLi [{relationId: 123, selectorId: BankAccountSelector, valueField: bank_account_id}, {}, ...]
	 * @return BankAccountSelector: {
					1: {id:xxx, field1:xxxx,},
					2: {id:xxx, field1:xxxx,}
					url:
					description:
				}
	 */
	public Map<String, Object> getRelationBo(List<Map<String, Object>> relationLi) {
		Map<String, Object> result = New.hashMap();
		for (Map<String, Object> item : relationLi) {
			String relationId = (String) item.get("relationId");
			if (StringUtils.isEmpty(relationId) || Integer.parseInt(relationId) == 0) {
				continue;
			}
			String selectorId = (String) item.get("selectorId");
			String valueField = (String) item.get("valueField");
			if (StringUtils.isEmpty(valueField)) {
				valueField = "id";
			}
			FormTemplate selectorTemplate = getFormTemplate(selectorId, FormTemplateEnum.SELECTOR);
			// 选择器只有一个data-provider,一个column-model,因此,直接取一个即可
			ColumnModel columnModel = selectorTemplate.getColumnModel().get(0);
			Map<String, String> paramMap = New.hashMap();
			paramMap.put(valueField, relationId);
			int pageNo = 1;
			int pageSize = 1;
			Map<String, Object> queryDataResult = queryDataForColumnModel(selectorTemplate, columnModel, paramMap, pageNo, pageSize);
			List<Map<String, Object>> items = (List<Map<String, Object>>) queryDataResult.get("items");
			if (items.size() > 0) {
				Map<String, Object> bo = New.hashMap();
				Map<String, Object> columnModelData = getColumnModelDataForColumnModel(columnModel, bo, items);
				items = (List<Map<String, Object>>)columnModelData.get("items");
				if (StringUtils.isNotEmpty(selectorTemplate.getAfterQueryData())) {
					try {
						IAfterQueryData afterQueryData = (IAfterQueryData) Class.forName(selectorTemplate.getAfterQueryData()).newInstance();
						afterQueryData.execute(queryDataResult);
					} catch (Exception e) {
						throw new JavametaException(e);
					}
				}
				if (result.get(selectorId) == null) {
					result.put(selectorId, new HashMap<String, Object>());
				}
				Map<String, Object> selectorDict = (Map<String, Object>) result.get(selectorId);
				selectorDict.put(relationId, items.get(0));
				if (selectorDict.get("url") == null) {
					selectorDict.put("url", getViewUrl(selectorTemplate));
				}
				if (selectorDict.get("description") == null) {
					selectorDict.put("description", selectorTemplate.getDescription());
				}
			} else {
				continue;
			}
		}
		return result;
	}

	/**
	 * 取选择器中的view_url,只取第一个出现的
	 * @param formTemplate
	 * @return
	 */
	public String getViewUrl(FormTemplate formTemplate) {
		for (ColumnModel columnModel : formTemplate.getColumnModel()) {
			for (Column column : columnModel.getColumnList()) {
				if (column instanceof VirtualColumn) {
					VirtualColumn virtualColumn = (VirtualColumn) column;
					if (virtualColumn.getButtons() != null) {
						for (Button button : virtualColumn.getButtons().getButton()) {
							if (button.getName() != null && button.getName().equals("btn_view")) {
								return button.getHandler();
							}
						}
					}
				}
			}
		}
		return "";
	}
	
	/**
	 * 取得列表FormTemplate的query-parameter的默认值,程序是全部遍历,但是FormTemplate通常只有一个data-provider,包含一个query-parameters
	 * @param formTemplate
	 * @return
	 */
	public Map<String, String> getQueryDefaultValue(FormTemplate formTemplate) {
		final Map<String, String> defaultDict = New.hashMap();
		FormTemplateIterator.iterateFormTemplateDataProvider(formTemplate, new IFormTemplateDataProviderIterate() {
			@Override
			public void iterate(DataProvider dataProvider) {
				FormTemplateIterator.iterateFormTemplateQueryParameter(dataProvider, new IFormTemplateQueryParameterIterate() {
					@Override
					public void iterate(DataProvider dataProvider, QueryParameter queryParameter) {
						String value = parseQueryParameterExpression(queryParameter.getDefaultValueExpr());
						if (StringUtils.isNotEmpty(value)) {
							defaultDict.put(queryParameter.getName(), value);
						}
					}
				});
			}
		});
		return defaultDict;
	}

	/**
	 * 这里的bo可以只包含主数据集,不必包含全部数据,表单页有可能会用到主数据集字段
	 * 列表页如果也用到主数据集字段，可以在列表的查询中，关联出主数据集字段，再用表达式
	 * @param bo
	 * @param data
	 * @param relationExpr
	 * @return
	 */
	private boolean parseRelationExprBoolean(JSONObject bo, JSONObject data, RelationDS.RelationItem.RelationExpr relationExpr) {
		if (relationExpr != null) {
			if (StringUtils.isEmpty(relationExpr.getMode()) || relationExpr.getMode().equals("text")) {
				if (StringUtils.isNotEmpty(relationExpr.getValue())) {
					return relationExpr.getValue().equals("true");
				} else {
					return true;
				}
			} else {
				return ExpressionParser.parseBoolean(data, relationExpr.getValue());
			}
		} else {
			return true;
		}
	}

	private boolean parseExpressionBoolean(JSONObject record, Expression expression) {
		if (expression != null) {
			if (StringUtils.isEmpty(expression.getMode()) || expression.getMode().equals("text")) {
				if (StringUtils.isNotEmpty(expression.getValue())) {
					return expression.getValue().equals("true");
				} else {
					return true;
				}
			} else {
				return ExpressionParser.parseBoolean(record, expression.getValue());
			}
		} else {
			return true;
		}
	}
	
	private String parseQueryParameterExpression(QueryParameters.QueryParameter.DefaultValueExpr defaultValueExpr) {
		if (defaultValueExpr != null) {
			if (StringUtils.isEmpty(defaultValueExpr.getMode()) || defaultValueExpr.getMode().equals("text")) {
				if (StringUtils.isNotEmpty(defaultValueExpr.getValue())) {
					return defaultValueExpr.getValue();
				} else {
					return "";
				}
			} else {
				return ExpressionParser.parse(new JSONObject(), defaultValueExpr.getValue());
			}
		} else {
			return "";
		}
	}

	public void recursionGetColumnItem(ColumnModel columnModel, List<Column> columnLi) {
		if (columnModel.getIdColumn() != null) {
			columnLi.add(columnModel.getIdColumn());
		}
		for (Column column : columnModel.getColumnList()) {
			columnLi.add(column);
			// 只有StringColumn,AutoColumn有ColumnModel
			if (column instanceof StringColumn) {
				StringColumn stringColumn = (StringColumn) column;
				if (stringColumn.getColumnModel() != null && stringColumn.getColumnModel().getColumnList().size() > 0) {
					recursionGetColumnItem(stringColumn.getColumnModel(), columnLi);
				}
			} else if (column instanceof AutoColumn) {
				AutoColumn autoColumn = (AutoColumn) column;
				if (autoColumn.getColumnModel() != null && autoColumn.getColumnModel().getColumnList().size() > 0) {
					recursionGetColumnItem(autoColumn.getColumnModel(), columnLi);
				}
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
	}

}
