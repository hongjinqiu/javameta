package com.javameta.model.queryparameter.sql;

import java.util.Map;

import com.javameta.JavametaException;
import com.javameta.model.template.QueryParameters.QueryParameter;
import com.javameta.util.New;

public class QueryParameterBuilder {
	
	/**
	 * {
	 * 	textfield: {
	 * 		"eq": new StringCmp("="),
	 * 		"nq": new StringCmp("!="),
	 * 	}
	 * }
	 * @param queryParameter
	 * @param value
	 * @param nameParameterMap
	 * @return
	 */
	public String buildQuery(QueryParameter queryParameter, String value, Map<String, Object> nameParameterMap) {
		Map<String, Map<String, IRestriction>> funcMap = New.hashMap();
		funcMap.put("textfield", stringCmpMap());
		funcMap.put("textareafield", stringCmpMap());
		funcMap.put("numberfield", intOrFloatCmpMap());
		funcMap.put("datefield", dateCmpMap());
		funcMap.put("combofield", intOrFloatOrStringCmpMap());
		funcMap.put("combotree", intOrFloatOrStringCmpMap());
		funcMap.put("displayfield", null);
		funcMap.put("hiddenfield", intOrFloatOrStringOrLikeCmpMap());
		funcMap.put("htmleditor", stringCmpMap());
		funcMap.put("checkboxfield", intOrFloatOrStringCmpMap());
		funcMap.put("checkboxgroup", intOrFloatOrStringCmpMap());
		funcMap.put("radiofield", intOrFloatOrStringCmpMap());
		funcMap.put("radiogroup", intOrFloatOrStringCmpMap());
		funcMap.put("triggerfield", intOrFloatCmpMap());
	
		if (funcMap.get(queryParameter.getEditor()) != null) {
			if (funcMap.get(queryParameter.getEditor()).get(queryParameter.getRestriction()) != null) {
				IRestriction restriction = funcMap.get(queryParameter.getEditor()).get(queryParameter.getRestriction());
				return restriction.operate(queryParameter, value, nameParameterMap);
			}
		}
		if (funcMap.get(queryParameter.getEditor()) != null) {
			if (funcMap.get(queryParameter.getEditor()).get(queryParameter.getRestriction()) == null) {
				throw new JavametaException(queryParameter.getName() + ",editor is:" + queryParameter.getEditor() + ",restriction is:" + queryParameter.getRestriction() + ", value is:" + value);
			}
		}
		
		return "";
	}
	
	private Map<String, IRestriction> stringCmpMap() {
		Map<String, IRestriction> map = New.hashMap();
		map.put("eq", new StringCmp("="));
		map.put("nq", new StringCmp("!="));
		map.put("ge", new StringCmp(">="));
		map.put("le", new StringCmp("<="));
		map.put("gt", new StringCmp(">"));
		map.put("lt", new StringCmp("<"));
		map.put("null", new NullCmp("is null"));
		map.put("not_null", new NullCmp("is not null"));
		map.put("in", new StringInCmp("in"));
		map.put("not_in", new StringInCmp("not in"));
		map.put("like", new RegexpCmp("like"));
		map.put("left_like", new RegexpCmp("left_like"));
		map.put("right_like", new RegexpCmp("right_like"));
		map.put("not_like", new RegexpCmp("not_like"));
		map.put("not_left_like", new RegexpCmp("not_left_like"));
		map.put("not_right_like", new RegexpCmp("not_right_like"));
		return map;
	}
	
	private Map<String, IRestriction> intOrFloatCmpMap() {
		Map<String, IRestriction> map = New.hashMap();
		map.put("eq", new IntOrFloatCmp("="));
		map.put("nq", new IntOrFloatCmp("!="));
		map.put("ge", new IntOrFloatCmp(">="));
		map.put("le", new IntOrFloatCmp("<="));
		map.put("gt", new IntOrFloatCmp(">"));
		map.put("lt", new IntOrFloatCmp("<"));
		map.put("null", null);
		map.put("not_null", null);
		map.put("in", new IntOrFloatInCmp("in"));
		map.put("not_in", new IntOrFloatInCmp("not in"));
		map.put("like", null);
		map.put("left_like", null);
		map.put("right_like", null);
		map.put("not_like", null);
		map.put("not_left_like", null);
		map.put("not_right_like", null);
		return map;
	}
	
	private Map<String, IRestriction> dateCmpMap() {
		Map<String, IRestriction> map = New.hashMap();
		map.put("eq", new DateCmp("="));
		map.put("nq", new DateCmp("!="));
		map.put("ge", new DateCmp(">="));
		map.put("le", new DateCmp("<="));
		map.put("gt", new DateCmp(">"));
		map.put("lt", new DateCmp("<"));
		map.put("null", null);
		map.put("not_null", null);
		map.put("in", null);
		map.put("not_in", null);
		map.put("like", null);
		map.put("left_like", null);
		map.put("right_like", null);
		map.put("not_like", null);
		map.put("not_left_like", null);
		map.put("not_right_like", null);
		return map;
	}
	
	private Map<String, IRestriction> intOrFloatOrStringCmpMap() {
		Map<String, IRestriction> map = New.hashMap();
		map.put("eq", new IntOrFloatOrStringCmp("="));
		map.put("nq", new IntOrFloatOrStringCmp("!="));
		map.put("ge", new IntOrFloatOrStringCmp(">="));
		map.put("le", new IntOrFloatOrStringCmp("<="));
		map.put("gt", new IntOrFloatOrStringCmp(">"));
		map.put("lt", new IntOrFloatOrStringCmp("<"));
		map.put("null", null);
		map.put("not_null", null);
		map.put("in", new IntOrFloatOrStringInCmp("in"));
		map.put("not_in", new IntOrFloatOrStringInCmp("not in"));
		map.put("like", null);
		map.put("left_like", null);
		map.put("right_like", null);
		map.put("not_like", null);
		map.put("not_left_like", null);
		map.put("not_right_like", null);
		return map;
	}
	
	private Map<String, IRestriction> intOrFloatOrStringOrLikeCmpMap() {
		Map<String, IRestriction> map = New.hashMap();
		map.put("eq", new IntOrFloatOrStringCmp("="));
		map.put("nq", new IntOrFloatOrStringCmp("!="));
		map.put("ge", new IntOrFloatOrStringCmp(">="));
		map.put("le", new IntOrFloatOrStringCmp("<="));
		map.put("gt", new IntOrFloatOrStringCmp(">"));
		map.put("lt", new IntOrFloatOrStringCmp("<"));
		map.put("null", new NullCmp("="));
		map.put("not_null", new NullCmp("!="));
		map.put("in", new IntOrFloatOrStringInCmp("in"));
		map.put("not_in", new IntOrFloatOrStringInCmp("not in"));
		map.put("like", new RegexpCmp("like"));
		map.put("left_like", new RegexpCmp("left_like"));
		map.put("right_like", new RegexpCmp("right_like"));
		map.put("not_like", new RegexpCmp("not_like"));
		map.put("not_left_like", new RegexpCmp("not_left_like"));
		map.put("not_right_like", new RegexpCmp("not_right_like"));
		return map;
	}
}
