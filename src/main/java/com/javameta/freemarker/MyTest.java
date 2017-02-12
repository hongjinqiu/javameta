package com.javameta.freemarker;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class MyTest {

	public static void main(String[] args) throws Exception {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);
//		cfg.setTemplateLoader(new StringTemplateLoader("hello：${user}"));
		cfg.setTemplateLoader(new StringTemplateLoader("<#if name?exists && name != ''>${name}<#else>test1</#if>"));
		cfg.setDefaultEncoding("UTF-8");
		Template template = cfg.getTemplate("");
		Map root = new HashMap();
		root.put("user", "lunzi");
		root.put("name", "lunzi");
		StringWriter writer = new StringWriter();
		template.process(root, writer);
		System.out.println(writer.toString());
	}
}
