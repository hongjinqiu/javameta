package com.javameta.model.queryparameter;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.javameta.model.template.QueryParameters.QueryParameter;
import com.javameta.util.New;

public class StringInCmp implements IRestriction {
	private String operator;
	
	public StringInCmp(String operator) {
		this.operator = operator;
	}

	@Override
	public String operate(QueryParameter queryParameter, String value, Map<String, Object> nameParameterMap) {
		if (StringUtils.isEmpty(value)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(" %s %s ( ");
		String[] valueLi = value.split(",");
		for (int i = 0; i < valueLi.length; i++) {
			if (StringUtils.isNotEmpty(valueLi[i])) {
				String key = queryParameter.getName() + "__" + i;
				sb.append(":" + key);
				nameParameterMap.put(key, valueLi[i]);
			}
			if (i != valueLi.length - 1) {
				sb.append(",");
			}
		}
		sb.append(" ) ");
		return String.format(sb.toString(), queryParameter.getDbQueryName(), operator);
	}

	public static void main(String[] args) {
		StringInCmp cmp = new StringInCmp("in");
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
