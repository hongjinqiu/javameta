package com.javameta.util;

public class CommonUtil {
	public static String filterJsonEmptyAttr(String jsonString) {
		String result = jsonString;
		//,"[^"]*?":(""|null)
		result = result.replaceAll(",\"[^\"]*?\":(\"\"|null)", "");
		//"[^"]*?":(""|null),?
		result = result.replaceAll("\"[^\"]*?\":(\"\"|null),?", "");

		return result;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
