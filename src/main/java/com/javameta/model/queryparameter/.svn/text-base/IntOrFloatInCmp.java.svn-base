package com.javameta.model.queryparameter;

import java.util.Map;

import com.javameta.model.template.QueryParameters.QueryParameter;

public class IntOrFloatInCmp implements IRestriction {
	private String operator;

	public IntOrFloatInCmp(String operator) {
		this.operator = operator;
	}

	@Override
	public String operate(QueryParameter queryParameter, String value, Map<String, Object> nameParameterMap) {
		StringInCmp cmp = new StringInCmp(operator);
		return cmp.operate(queryParameter, value, nameParameterMap);
	}

}
