package com.javameta.model.queryparameter.mongodb;

import java.util.Map;

import com.javameta.JavametaException;
import com.javameta.model.template.QueryParameters.QueryParameter;
import com.javameta.util.New;
import com.mongodb.DBObject;

public class QueryParameterBuilder {
	
	/**
	 * {
	 * 	textfield: {
	 * 		"eq": new StringCmp("$eq"),
	 * 		"nq": new StringCmp("$ne"),
	 * 	}
	 * }
	 * @param queryParameter
	 * @param value
	 * @param nameParameterMap
	 * @return
	 */
	public DBObject buildQuery(QueryParameter queryParameter, String value, Map<String, Object> nameParameterMap) {
		Map<String, Map<String, IRestriction>> funcMap = New.hashMap();
		
		funcMap.put("FLOAT", intOrFloatCmpMap());
		funcMap.put("DOUBLE", intOrFloatCmpMap());
		funcMap.put("DECIMAL", intOrFloatCmpMap());
		funcMap.put("SHORT", intOrFloatCmpMap());
		funcMap.put("INT", intOrFloatCmpMap());
		funcMap.put("LONG", intOrFloatCmpMap());
		funcMap.put("STRING", stringCmpMap());
	
		if (funcMap.get(queryParameter.getFieldType()) != null) {
			if (funcMap.get(queryParameter.getFieldType()).get(queryParameter.getRestriction()) != null) {
				IRestriction restriction = funcMap.get(queryParameter.getFieldType()).get(queryParameter.getRestriction());
				return restriction.operate(queryParameter, value, nameParameterMap);
			}
		}
		if (funcMap.get(queryParameter.getFieldType()) != null) {
			if (funcMap.get(queryParameter.getFieldType()).get(queryParameter.getRestriction()) == null) {
				throw new JavametaException(queryParameter.getName() + ",editor is:" + queryParameter.getFieldType() + ",restriction is:" + queryParameter.getRestriction() + ", value is:" + value);
			}
		}
		
		return null;
	}
	
	private Map<String, IRestriction> stringCmpMap() {
		Map<String, IRestriction> map = New.hashMap();
		map.put("eq", new StringCmp("$eq"));
		map.put("nq", new StringCmp("$ne"));
		map.put("ge", new StringCmp("$gte"));
		map.put("le", new StringCmp("$lte"));
		map.put("gt", new StringCmp("$gt"));
		map.put("lt", new StringCmp("$lt"));
		map.put("null", new NullCmp("$eq"));
		map.put("not_null", new NullCmp("$ne"));
		map.put("in", new StringInCmp("$in"));
		map.put("not_in", new StringInCmp("$nin"));
		map.put("like", new RegexpCmp("$regex"));
		map.put("left_like", new RegexpCmp("$regex"));
		map.put("right_like", new RegexpCmp("$regex"));
		map.put("not_like", new RegexpCmp("$regex"));
		map.put("not_left_like", new RegexpCmp("$regex"));
		map.put("not_right_like", new RegexpCmp("$regex"));
		return map;
	}
	
	private Map<String, IRestriction> intOrFloatCmpMap() {
		Map<String, IRestriction> map = New.hashMap();
		map.put("eq", new IntOrFloatCmp("$eq"));
		map.put("nq", new IntOrFloatCmp("$ne"));
		map.put("ge", new IntOrFloatCmp("$gte"));
		map.put("le", new IntOrFloatCmp("$lte"));
		map.put("gt", new IntOrFloatCmp("$gt"));
		map.put("lt", new IntOrFloatCmp("$lt"));
		map.put("null", null);
		map.put("not_null", null);
		map.put("in", new IntOrFloatInCmp("$in"));
		map.put("not_in", new IntOrFloatInCmp("$nin"));
		map.put("like", null);
		map.put("left_like", null);
		map.put("right_like", null);
		map.put("not_like", null);
		map.put("not_left_like", null);
		map.put("not_right_like", null);
		return map;
	}
	
}
