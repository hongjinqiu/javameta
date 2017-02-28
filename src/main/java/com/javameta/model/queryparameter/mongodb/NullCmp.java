package com.javameta.model.queryparameter.mongodb;

import java.util.Map;

import com.javameta.model.template.QueryParameters.QueryParameter;
import com.javameta.util.New;
import com.mongodb.DBObject;

public class NullCmp implements IRestriction {
	private String operator;
	
	public NullCmp(String operator) {
		this.operator = operator;
	}

	@Override
	public DBObject operate(QueryParameter queryParameter, String value, Map<String, Object> nameParameterMap) {
		StringCmp stringCmp = new StringCmp(operator);
		return stringCmp.operate(queryParameter, "", nameParameterMap);
	}

	public static void main(String[] args) {
		NullCmp cmp = new NullCmp("$ne");
		QueryParameter queryParameter = new QueryParameter();
		queryParameter.setName("beginAge");
		queryParameter.setColumnName("age");
		Map<String, Object> nameParameterMap = New.hashMap();
		String value = "testabc";
		DBObject sql = cmp.operate(queryParameter, value, nameParameterMap);
		System.out.println(sql);
		System.out.println(nameParameterMap);
	}
}
