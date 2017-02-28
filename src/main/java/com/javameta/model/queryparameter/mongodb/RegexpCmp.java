package com.javameta.model.queryparameter.mongodb;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.javameta.model.template.QueryParameters.QueryParameter;
import com.javameta.util.New;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class RegexpCmp implements IRestriction {
	private String operator;

	public RegexpCmp(String operator) {
		this.operator = operator;
	}

	@Override
	public DBObject operate(QueryParameter queryParameter, String value, Map<String, Object> nameParameterMap) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		String regex = "";
		if (queryParameter.getRestriction().equals("like")) {
			regex = "(?i)^.*?" + value + ".*?$";
		} else if (queryParameter.getRestriction().equals("not_like")) {
			regex = "(?i)^((?!" + value + ").)*$";
		} else if (queryParameter.getRestriction().equals("left_like")) {
			regex = "(?i)^" + value + ".*?$";
		} else if (queryParameter.getRestriction().equals("not_left_like")) {
			regex = "(?i)^((?!^" + value + ").)*$";
		} else if (queryParameter.getRestriction().equals("right_like")) {
			regex = "(?i)^.*?" + value + "$";
		} else if (queryParameter.getRestriction().equals("not_right_like")) {
			regex = "(?i)^((?!" + value + "$).)*$";
		}
		DBObject result = new BasicDBObject();
		{
			DBObject eq = new BasicDBObject();
			eq.put(operator, regex);
			
			result.put(queryParameter.getDbQueryName(), eq);
		}
		return result;
	}
	
	public static void main(String[] args) {
		RegexpCmp cmp = new RegexpCmp("$regex");
		QueryParameter queryParameter = new QueryParameter();
		queryParameter.setFieldType("STRING");
		queryParameter.setRestriction("like");
		queryParameter.setName("beginAge");
		queryParameter.setColumnName("age");
		Map<String, Object> nameParameterMap = New.hashMap();
		String value = "testabc,1,3,4,9,,,,";
		DBObject sql = cmp.operate(queryParameter, value, nameParameterMap);
		System.out.println(sql);
		System.out.println(nameParameterMap);
	}

}
