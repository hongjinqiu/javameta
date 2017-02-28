package com.javameta.model.queryparameter.mongodb;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.javameta.model.template.QueryParameters.QueryParameter;
import com.javameta.util.New;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class StringInCmp implements IRestriction {
	private String operator;
	
	public StringInCmp(String operator) {
		this.operator = operator;
	}

	@Override
	public DBObject operate(QueryParameter queryParameter, String value, Map<String, Object> nameParameterMap) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		String[] valueLi = value.split(",");
		DBObject result = new BasicDBObject();
		{
			DBObject eq = new BasicDBObject();
			eq.put(operator, valueLi);
			
			result.put(queryParameter.getDbQueryName(), eq);
		}
		return result;
	}

	public static void main(String[] args) {
		StringInCmp cmp = new StringInCmp("$in");
		QueryParameter queryParameter = new QueryParameter();
		queryParameter.setName("beginAge");
		queryParameter.setColumnName("age");
		Map<String, Object> nameParameterMap = New.hashMap();
		String value = "testabc,1,3,4,9,,,,";
		DBObject sql = cmp.operate(queryParameter, value, nameParameterMap);
		System.out.println(sql);
		System.out.println(nameParameterMap);
	}
}
