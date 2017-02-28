package com.javameta.model.queryparameter.mongodb;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.javameta.model.template.QueryParameters.QueryParameter;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class IntOrFloatCmp implements IRestriction {
	private String operator;
	
	public IntOrFloatCmp(String operator) {
		this.operator = operator;
	}

	@Override
	public DBObject operate(QueryParameter queryParameter, String value, Map<String, Object> nameParameterMap) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		Object valueObj = null;
		if (queryParameter.getFieldType().equals("FLOAT")) {
			valueObj = Float.valueOf(value);
		} else if (queryParameter.getFieldType().equals("DOUBLE")) {
			valueObj = Double.valueOf(value);
		} else if (queryParameter.getFieldType().equals("DECIMAL")) {
			valueObj = new BigDecimal(value);
		} else if (queryParameter.getFieldType().equals("SHORT")) {
			valueObj = Short.valueOf(value);
		} else if (queryParameter.getFieldType().equals("INT")) {
			valueObj = Integer.valueOf(value);
		} else if (queryParameter.getFieldType().equals("LONG")) {
			valueObj = Long.valueOf(value);
		}
		if (operator.equals("$eq")) {
			DBObject result = new BasicDBObject();
			result.put(queryParameter.getDbQueryName(), valueObj);
			return result;
		}
		DBObject result = new BasicDBObject();
		{
			DBObject eq = new BasicDBObject();
			eq.put(operator, valueObj);
			
			result.put(queryParameter.getDbQueryName(), eq);
		}
		return result;
	}

}
