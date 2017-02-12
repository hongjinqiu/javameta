package com.javameta.model;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.javameta.JavametaException;
import com.javameta.freemarker.FreemarkerParser;
import com.javameta.model.queryparameter.QueryParameterBuilder;
import com.javameta.model.template.ColumnModel;
import com.javameta.model.template.DataProvider;
import com.javameta.model.template.FormTemplate;
import com.javameta.model.template.QueryParameters.QueryParameter;
import com.javameta.util.ApplicationContextUtil;
import com.javameta.util.New;

public class DataProviderQuery {

	public int queryForInt(String formTemplateId, String dataProviderName, Map<String, Object> paramMap) {
		int pageNo = -1;
		int pageSize = -1;
		String sql = getQuerySql(formTemplateId, dataProviderName, paramMap, pageNo, pageSize);
		FormTemplateDao formTemplateDao = (FormTemplateDao) ApplicationContextUtil.getApplicationContext().getBean("formTemplateDao");
		return formTemplateDao.getNamedParameterJdbcTemplate().queryForInt(sql, paramMap);
	}

	public long queryForLong(String formTemplateId, String dataProviderName, Map<String, Object> paramMap) {
		int pageNo = -1;
		int pageSize = -1;
		String sql = getQuerySql(formTemplateId, dataProviderName, paramMap, pageNo, pageSize);
		FormTemplateDao formTemplateDao = (FormTemplateDao) ApplicationContextUtil.getApplicationContext().getBean("formTemplateDao");
		return formTemplateDao.getNamedParameterJdbcTemplate().queryForLong(sql, paramMap);
	}

	/**
	 * 
	 * @param formTemplateId
	 * @param dataProviderName
	 * @param paramMap
	 * @param rowMapper 可以用new BeanPropertyRowMapper<T>(requiredType)
	 * @return
	 */
	public <T> T queryForObjectWrapper(String formTemplateId, String dataProviderName, Map<String, Object> paramMap, Class<T> requiredType) {
		int pageNo = -1;
		int pageSize = -1;
		String sql = getQuerySql(formTemplateId, dataProviderName, paramMap, pageNo, pageSize);
		FormTemplateDao formTemplateDao = (FormTemplateDao) ApplicationContextUtil.getApplicationContext().getBean("formTemplateDao");
		return formTemplateDao.getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, new BeanPropertyRowMapper<T>(requiredType));
	}

	public Map<String, Object> queryForMap(String formTemplateId, String dataProviderName, Map<String, Object> paramMap) {
		int pageNo = -1;
		int pageSize = -1;
		String sql = getQuerySql(formTemplateId, dataProviderName, paramMap, pageNo, pageSize);
		FormTemplateDao formTemplateDao = (FormTemplateDao) ApplicationContextUtil.getApplicationContext().getBean("formTemplateDao");
		return formTemplateDao.getNamedParameterJdbcTemplate().queryForMap(sql, paramMap);
	}

	public List<Map<String, Object>> queryForList(String formTemplateId, String dataProviderName, Map<String, Object> paramMap) {
		int pageNo = -1;
		int pageSize = -1;
		String sql = getQuerySql(formTemplateId, dataProviderName, paramMap, pageNo, pageSize);
		FormTemplateDao formTemplateDao = (FormTemplateDao) ApplicationContextUtil.getApplicationContext().getBean("formTemplateDao");
		return formTemplateDao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
	}

	/**
	 * 
	 * @param formTemplateId
	 * @param dataProviderName
	 * @param paramMap
	 * @param pageNo 以1起始
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> queryForList(String formTemplateId, String dataProviderName, Map<String, Object> paramMap, int pageNo, int pageSize) {
		String sql = getQuerySql(formTemplateId, dataProviderName, paramMap, pageNo, pageSize);
		FormTemplateDao formTemplateDao = (FormTemplateDao) ApplicationContextUtil.getApplicationContext().getBean("formTemplateDao");
		return formTemplateDao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
	}

	public <T> List<T> queryForListWrapper(String formTemplateId, String dataProviderName, Map<String, Object> paramMap, Class<T> elementType) {
		List<Map<String, Object>> list = this.queryForList(formTemplateId, dataProviderName, paramMap);
		List<T> result = New.arrayList();
		for (Map<String, Object> item: list) {
			T t;
			try {
				t = elementType.newInstance();
				BeanUtils.copyProperties(t, item);
			} catch (InstantiationException e) {
				throw new JavametaException(e);
			} catch (IllegalAccessException e) {
				throw new JavametaException(e);
			} catch (InvocationTargetException e) {
				throw new JavametaException(e);
			}
			result.add(t);
		}
		return result;
	}

	/**
	 * 
	 * @param formTemplateId
	 * @param dataProviderName
	 * @param paramMap
	 * @param elementType
	 * @param pageNo 以1起始
	 * @param pageSize
	 * @return
	 */
	public <T> List<T> queryForListWrapper(String formTemplateId, String dataProviderName, Map<String, Object> paramMap, Class<T> elementType, int pageNo, int pageSize) {
		String sql = getQuerySql(formTemplateId, dataProviderName, paramMap, pageNo, pageSize);
		FormTemplateDao formTemplateDao = (FormTemplateDao) ApplicationContextUtil.getApplicationContext().getBean("formTemplateDao");
		List<Map<String, Object>> list = formTemplateDao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
		List<T> result = New.arrayList();
		for (Map<String, Object> item: list) {
			T t;
			try {
				t = elementType.newInstance();
				BeanUtils.copyProperties(t, item);
			} catch (InstantiationException e) {
				throw new JavametaException(e);
			} catch (IllegalAccessException e) {
				throw new JavametaException(e);
			} catch (InvocationTargetException e) {
				throw new JavametaException(e);
			}
			result.add(t);
		}
		return result;
	}

	private String getQuerySql(String formTemplateId, String dataProviderName, Map<String, Object> paramMap, int pageNo, int pageSize) {
		FormTemplateFactory formTemplateFactory = new FormTemplateFactory();
		FormTemplate formTemplate = formTemplateFactory.getFormTemplate(formTemplateId, FormTemplateEnum.QUERY);
		DataProvider dataProvider = formTemplate.getDataProvider(dataProviderName);

		if (dataProvider == null) {
			throw new JavametaException("formTemplateId:" + formTemplateId + " can't found dataProvider:" + dataProviderName);
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
				ColumnModel columnModel = null;
				Map<String, String> tmpParamMap = New.hashMap();
				for (String key : paramMap.keySet()) {
					tmpParamMap.put(key, ObjectUtils.toString(paramMap.get(key)));
				}
				bodySql = sqlIntercept.getQuerySql(bodySql, columnModel, dataProvider, tmpParamMap, nameParameterMap);
			} catch (Exception e) {
				throw new JavametaException(e);
			}
		}
		if (dataProvider.getQueryParameters() != null) {
			QueryParameterBuilder queryParameterBuilder = new QueryParameterBuilder();
			for (QueryParameter queryParameter : dataProvider.getQueryParameters().getQueryParameter()) {
				if (StringUtils.isEmpty(queryParameter.getEditor())) {
					queryParameter.setEditor("hiddenfield");
				}
				if (StringUtils.isNotEmpty(queryParameter.getRestriction())) {
					String value = ObjectUtils.toString(paramMap.get(queryParameter.getName()));
					String queryParameterSql = queryParameterBuilder.buildQuery(queryParameter, value, nameParameterMap);
					if (StringUtils.isEmpty(queryParameter.getUseIn()) || !queryParameter.getUseIn().equals("none")) {
						bodySql += queryParameterSql;
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

		if (pageNo > 0) {
			int offset = (pageNo - 1) * pageSize;
			bodySql += " limit " + offset + "," + pageSize;
		}

		return bodySql;
	}

	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "spring-mvc.xml" });
		DataProviderQuery dataProviderQuery = new DataProviderQuery();
		{
			Map<String, Object> paramMap = New.hashMap();
			paramMap.put("id", 1);
//			paramMap.put("id2", 2);
			List<Map<String, Object>> result = dataProviderQuery.queryForList("GatheringBill", "queryDataSetA", paramMap);
			System.out.println(result.size());
			System.out.println(result);
		}
		{
			Map<String, Object> paramMap = New.hashMap();
			paramMap.put("gatheringbill_id", 1);
//			paramMap.put("id2", 2);
			List<Map<String, Object>> result = dataProviderQuery.queryForList("GatheringBill", "queryDataSetB", paramMap);
			System.out.println(result.size());
			System.out.println(result);
		}
		{
			Map<String, Object> paramMap = New.hashMap();
			paramMap.put("id", 1);
			List<Map<String, Object>> result = dataProviderQuery.queryForList("Bank2", "queryDataSetA", paramMap);
			System.out.println(result.size());
			System.out.println(result);
		}
		{
			Map<String, Object> paramMap = New.hashMap();
			paramMap.put("id", "1");
			Map<String, Object> result = dataProviderQuery.queryForMap("Bank2", "queryDataSetA", paramMap);
			System.out.println(result);
		}
		{
			Map<String, Object> paramMap = New.hashMap();
			List<Map<String, Object>> list = dataProviderQuery.queryForList("Bank2", "queryDataSetB", paramMap);
			System.out.println(list);
		}
		{
			Map<String, Object> paramMap = New.hashMap();
			int pageNo = 1;
			int pageSize = 10;
			List<Map<String, Object>> list = dataProviderQuery.queryForList("Bank2", "queryDataSetB", paramMap, pageNo, pageSize);
			System.out.println(list);
		}
	}
}
