package com.javameta.model.queryparameter.mongodb;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.javameta.model.template.QueryParameters.QueryParameter;
import com.javameta.util.New;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class IntOrFloatInCmp implements IRestriction {
	private String operator;

	public IntOrFloatInCmp(String operator) {
		this.operator = operator;
	}

	@Override
	public DBObject operate(QueryParameter queryParameter, String value, Map<String, Object> nameParameterMap) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		String[] valueLi = value.split(",");
		List<Object> valueCompare = New.arrayList();
		if (queryParameter.getFieldType().equals("FLOAT")) {
			for (int i = 0; i < valueLi.length; i++) {
				valueCompare.add(Float.valueOf(valueLi[i]));
			}
		} else if (queryParameter.getFieldType().equals("DOUBLE")) {
			for (int i = 0; i < valueLi.length; i++) {
				valueCompare.add(Double.valueOf((String)valueLi[i]));
			}
		} else if (queryParameter.getFieldType().equals("DECIMAL")) {
			for (int i = 0; i < valueLi.length; i++) {
				valueCompare.add(new BigDecimal((String)valueLi[i]));
			}
		} else if (queryParameter.getFieldType().equals("SHORT")) {
			for (int i = 0; i < valueLi.length; i++) {
				valueCompare.add(Short.valueOf((String)valueLi[i]));
			}
		} else if (queryParameter.getFieldType().equals("INT")) {
			for (int i = 0; i < valueLi.length; i++) {
				valueCompare.add(Integer.valueOf((String)valueLi[i]));
			}
		} else if (queryParameter.getFieldType().equals("LONG")) {
			for (int i = 0; i < valueLi.length; i++) {
				valueCompare.add(Long.valueOf((String)valueLi[i]));
			}
		}
		DBObject result = new BasicDBObject();
		{
			DBObject eq = new BasicDBObject();
			eq.put(operator, valueCompare.toArray());
			
			result.put(queryParameter.getDbQueryName(), eq);
		}
		return result;
	}

	public static void main(String[] args) {
		IntOrFloatInCmp cmp = new IntOrFloatInCmp("$in");
		QueryParameter queryParameter = new QueryParameter();
		queryParameter.setFieldType("DECIMAL");
		queryParameter.setName("beginAge");
		queryParameter.setColumnName("age");
		Map<String, Object> nameParameterMap = New.hashMap();
		String value = "1,3,4,9,2.7,,,,";
		DBObject sql = cmp.operate(queryParameter, value, nameParameterMap);
		System.out.println(sql);
		System.out.println(nameParameterMap);
	}
}
