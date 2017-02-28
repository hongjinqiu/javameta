package com.javameta.model.queryparameter.mongodb;

import java.util.Map;

import com.javameta.model.template.QueryParameters.QueryParameter;
import com.mongodb.DBObject;

public interface IRestriction {
	public DBObject operate(QueryParameter queryParameter, String value, Map<String, Object> nameParameterMap);
}
