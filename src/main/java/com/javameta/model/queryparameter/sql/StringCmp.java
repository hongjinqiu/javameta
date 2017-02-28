package com.javameta.model.queryparameter.sql;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.javameta.model.template.QueryParameters.QueryParameter;
import com.javameta.util.New;

public class StringCmp implements IRestriction {
	private String operator;

	public StringCmp(String operator) {
		this.operator = operator;
	}

	@Override
	public String operate(QueryParameter queryParameter, String value, Map<String, Object> nameParameterMap) {
		if (StringUtils.isEmpty(value)) {
			return "";
		}
		String sql = " %s %s %s ";// age >= :beginAge
		nameParameterMap.put(queryParameter.getName(), value);
		return String.format(sql, queryParameter.getDbQueryName(), operator, ":" + queryParameter.getName());
	}

	public static void main(String[] args) {
		StringCmp cmp = new StringCmp("=");
		QueryParameter queryParameter = new QueryParameter();
		queryParameter.setName("beginAge");
		queryParameter.setColumnName("age");
		Map<String, Object> nameParameterMap = New.hashMap();
		String value = "testabc";
		String sql = cmp.operate(queryParameter, value, nameParameterMap);
		System.out.println(sql);
		System.out.println(nameParameterMap);
	}
}
