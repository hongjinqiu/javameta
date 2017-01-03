package com.javameta.model;

import java.util.Map;

import com.javameta.model.template.ColumnModel;
import com.javameta.model.template.DataProvider;

public interface SqlIntercept {
	/**
	 * @param sql
	 * @param columnModel
	 * @param dataProvider
	 * @param paramMap
	 * @return 组装后的SQL语句
	 */
	public String getQuerySql(String sql, ColumnModel columnModel, DataProvider dataProvider, Map<String, String> paramMap, Map<String, Object> nameParameterMap);

}
