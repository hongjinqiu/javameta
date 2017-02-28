package com.javameta.model.queryparameter.sql;

import java.util.Map;

import com.javameta.model.template.QueryParameters.QueryParameter;

public class IntOrFloatOrStringInCmp implements IRestriction {
	private String operator;
	
	public IntOrFloatOrStringInCmp(String operator) {
		this.operator = operator;
	}

	@Override
	public String operate(QueryParameter queryParameter, String value, Map<String, Object> nameParameterMap) {
		StringInCmp cmp = new StringInCmp(operator);
		return cmp.operate(queryParameter, value, nameParameterMap);
	}

}
