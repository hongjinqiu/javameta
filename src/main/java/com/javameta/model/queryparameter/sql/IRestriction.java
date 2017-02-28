package com.javameta.model.queryparameter.sql;

import java.util.Map;

import com.javameta.model.template.QueryParameters.QueryParameter;

public interface IRestriction {
	public String operate(QueryParameter queryParameter, String value, Map<String, Object> nameParameterMap);
}
