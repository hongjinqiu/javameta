package com.javameta.model.queryparameter;

import java.util.Map;

import com.javameta.model.template.QueryParameters.QueryParameter;

public class IntOrFloatOrStringCmp implements IRestriction {
	private String operator;
	
	public IntOrFloatOrStringCmp(String operator) {
		this.operator = operator;
	}

	@Override
	public String operate(QueryParameter queryParameter, String value, Map<String, Object> nameParameterMap) {
		StringCmp cmp = new StringCmp(operator);
		return cmp.operate(queryParameter, value, nameParameterMap);
	}

}
