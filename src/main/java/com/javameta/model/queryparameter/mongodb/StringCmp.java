package com.javameta.model.queryparameter.mongodb;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.javameta.model.template.QueryParameters.QueryParameter;
import com.javameta.util.New;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class StringCmp implements IRestriction {
	private String operator;

	public StringCmp(String operator) {
		this.operator = operator;
	}

	@Override
	public DBObject operate(QueryParameter queryParameter, String value, Map<String, Object> nameParameterMap) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		if (operator.equals("$eq")) {
			DBObject result = new BasicDBObject();
			result.put(queryParameter.getDbQueryName(), value);
			return result;
		}
		DBObject result = new BasicDBObject();
		{
			DBObject eq = new BasicDBObject();
			eq.put(operator, value);
			
			result.put(queryParameter.getDbQueryName(), eq);
		}
		return result;
	}

	public static void main(String[] args) {
		StringCmp cmp = new StringCmp("$gt");
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
