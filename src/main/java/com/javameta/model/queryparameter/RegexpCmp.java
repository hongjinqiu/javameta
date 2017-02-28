package com.javameta.model.queryparameter;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.javameta.model.template.QueryParameters.QueryParameter;
import com.javameta.util.New;

public class RegexpCmp implements IRestriction {
	private String operator;

	public RegexpCmp(String operator) {
		this.operator = operator;
	}

	@Override
	public String operate(QueryParameter queryParameter, String value, Map<String, Object> nameParameterMap) {
		if (StringUtils.isEmpty(value)) {
			return "";
		}
		String sql = " %s %s %s ";// age >= :beginAge
		String tmpOperator = "";
		if (operator.equals("like")) {
			tmpOperator = "like";
			nameParameterMap.put(queryParameter.getName(), "%" + value + "%");
		} else if (operator.equals("left_like")) {
			tmpOperator = "like";
			nameParameterMap.put(queryParameter.getName(), value + "%");
		} else if (operator.equals("right_like")) {
			tmpOperator = "like";
			nameParameterMap.put(queryParameter.getName(), "%" + value);
		} else if (operator.equals("not_like")) {
			tmpOperator = "not like";
			nameParameterMap.put(queryParameter.getName(), "%" + value + "%");
		} else if (operator.equals("not_left_like")) {
			tmpOperator = "not like";
			nameParameterMap.put(queryParameter.getName(), value + "%");
		} else if (operator.equals("not_right_like")) {
			tmpOperator = "not like";
			nameParameterMap.put(queryParameter.getName(), "%" + value);
		}
		return String.format(sql, queryParameter.getDbQueryName(), tmpOperator, ":" + queryParameter.getName());
	}
	
	public static void main(String[] args) {
		RegexpCmp cmp = new RegexpCmp("not_left_like");
		QueryParameter queryParameter = new QueryParameter();
		queryParameter.setName("beginAge");
		queryParameter.setColumnName("age");
		Map<String, Object> nameParameterMap = New.hashMap();
		String value = "testabc,1,3,4,9,,,,";
		String sql = cmp.operate(queryParameter, value, nameParameterMap);
		System.out.println(sql);
		System.out.println(nameParameterMap);
	}

}
