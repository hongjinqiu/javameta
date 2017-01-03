package com.javameta.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.javameta.JavametaException;

public class JavametaProperties {
	private static Properties prop = new Properties();
	static {
		InputStream is = JavametaProperties.class.getClassLoader().getResourceAsStream("javameta.properties");
		try {
			prop.load(is);
		} catch (IOException e) {
			throw new JavametaException(e);
		}
	}
	
	public static String getProperty(String key) {
		return prop.getProperty(key);
	}
}
