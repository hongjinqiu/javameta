package com.javameta.util;

import net.sf.json.JSONObject;

public class CommonUtil {
	public static String filterJsonEmptyAttr(String jsonString) {
		String result = jsonString;
		//,"[^"]*?":(""|null)
		result = result.replaceAll(",\"[^\"]*?\":(\"\"|null)", "");
		//"[^"]*?":(""|null),?
		result = result.replaceAll("\"[^\"]*?\":(\"\"|null),?", "");

		return result;
	}
	
	public static JSONObject filterNullInJSONObject(JSONObject obj) {
		String json = obj.toString();
		json = filterJsonEmptyAttr(json);
		return JSONObject.fromObject(json);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
