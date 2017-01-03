package com.javameta.model.queryparameter;

import java.util.Map;

import com.javameta.model.template.QueryParameters.QueryParameter;
import com.javameta.util.New;

public class NullCmp implements IRestriction {
	private String operator;
	
	public NullCmp(String operator) {
		this.operator = operator;
	}

	@Override
	public String operate(QueryParameter queryParameter, String value, Map<String, Object> nameParameterMap) {
		String sql = " and %s %s ";// and age is null| and age is not null
		return String.format(sql, queryParameter.getDbQueryName(), operator);
	}

	public static void main(String[] args) {
		NullCmp cmp = new NullCmp("is not null");
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
