package com.javameta.expression;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONObject;
import sun.org.mozilla.javascript.internal.Context;
import sun.org.mozilla.javascript.internal.Function;
import sun.org.mozilla.javascript.internal.Scriptable;

public class ExpressionParser {
	private static ThreadLocal<Context> context = new ThreadLocal<Context>();

	private ExpressionParser() {
	}

	public static Context getContext() {
		Context cx = context.get();
		if (cx == null) {
			cx = Context.enter();
			context.set(cx);
		}
		return cx;
	}

	public static void exit() {
		Context cx = context.get();
		if (cx != null) {
			Context.exit();
			context.set(null);
		}
	}
	
	public static boolean parseModelBoolean(JSONObject bo, JSONObject data, String content) {
		if (StringUtils.isEmpty(content)) {
			return true;
		}
		return parseModel(bo, data, content).equals("true");
	}

	public static String parseModel(JSONObject bo, JSONObject data, String content) {
		Context cx = Context.enter();

		Scriptable scope = cx.initStandardObjects();
		StringBuilder sb = new StringBuilder();
		sb.append(" function(){ ");
		sb.append(" 	var bo = {BO}; ");
		sb.append(" 	var data = {RECORD_JSON}; ");
		sb.append(" 	return {EXPRESSION}; ");
		sb.append(" } ");
		String text = sb.toString();
		text = text.replace("{BO}", bo.toString());
		text = text.replace("{RECORD_JSON}", data.toString());
		text = text.replace("{EXPRESSION}", content);
		Function f = cx.compileFunction(scope, text, null, 1, null);
		Object result = f.call(cx, scope, scope, new Object[] {});
		return Context.toString(result);
	}
	
	public static boolean parseBoolean(JSONObject data, String content) {
		if (StringUtils.isEmpty(content)) {
			return true;
		}
		return parse(data, content).equals("true");
	}
	
	public static String parse(JSONObject data, String content) {
		Context cx = Context.enter();

		Scriptable scope = cx.initStandardObjects();
		StringBuilder sb = new StringBuilder();
		sb.append(" function(){ ");
		sb.append(" 	var data = {RECORD_JSON}; ");
		sb.append(" 	return {EXPRESSION}; ");
		sb.append(" } ");
		String text = sb.toString();
		text = text.replace("{RECORD_JSON}", data.toString());
		text = text.replace("{EXPRESSION}", content);
		Function f = cx.compileFunction(scope, text, null, 1, null);
		Object result = f.call(cx, scope, scope, new Object[] {});
		return Context.toString(result);
	}

	public static void test1() {
		Context cx = getContext();

		try {
			Scriptable scope = cx.initStandardObjects();
			String str = "3/(1+2)";
			Object result = cx.evaluateString(scope, str, null, 1, null);
			System.out.println(str + "=" + Context.toNumber(result));
		} finally {
			exit();
		}
	}

	public static void test2() {
		Context cx = Context.enter();

		try {
			Scriptable scope = cx.initStandardObjects();
			StringBuilder sb = new StringBuilder();
			sb.append(" function(){ ");
			sb.append(" 	var data = {RECORD_JSON}; ");
			sb.append(" 	return {EXPRESSION}; ");
			sb.append(" } ");
			String text = sb.toString();
			text = text.replace("{RECORD_JSON}", "{\"name\": \"testHjq\"}");
			text = text.replace("{EXPRESSION}", "data[\"name\"] == 'testHjq'");
			Function f = cx.compileFunction(scope, text, null, 1, null);
			Object result = f.call(cx, scope, scope, new Object[] {});
			//			System.out.println(str + "=" + Context.toNumber(result));
			System.out.println(text + "=" + Context.toBoolean(result));
		} finally {
			Context.exit();
		}
	}

	public static void test3() {
		Context cx = Context.enter();

		try {
			Scriptable scope = cx.initStandardObjects();
			StringBuilder sb = new StringBuilder();
			sb.append(" function(){ ");
			sb.append(" 	var bo = {BO}; ");
			sb.append(" 	var data = {RECORD_JSON}; ");
			sb.append(" 	return {EXPRESSION}; ");
			sb.append(" } ");
			String text = sb.toString();
			text = text.replace("{BO}", "{\"A\": {\"name\": \"boName\"}}");
			text = text.replace("{RECORD_JSON}", "{\"name\": \"testHjq\"}");
			text = text.replace("{EXPRESSION}", "bo.A.name + \"_\" + data[\"name\"]");
			Function f = cx.compileFunction(scope, text, null, 1, null);
			Object result = f.call(cx, scope, scope, new Object[] {});
			//			System.out.println(str + "=" + Context.toNumber(result));
			System.out.println(text + "=" + Context.toString(result));
		} finally {
			Context.exit();
		}
	}
	
	public static void test4() {
		JSONObject bo = new JSONObject();
		JSONObject master = new JSONObject();
		master.put("name", "testHjq");
		bo.put("A", master.toString());
		
		JSONObject data = new JSONObject();
		data.put("name", "boName");
		
		String content = "bo.A.name + data.name";
		
		String result = parseModel(bo, data, content);
		System.out.println(result);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		test1();
//		test2();
//		test3();
		test4();
	}
}
