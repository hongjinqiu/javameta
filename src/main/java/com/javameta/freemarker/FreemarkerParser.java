package com.javameta.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import com.javameta.JavametaException;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public class FreemarkerParser {
	public static String parse(String text, Map<String, Object> param) {
		try {
			Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);
			cfg.setTemplateLoader(new StringTemplateLoader(text));
			cfg.setDefaultEncoding("UTF-8");
			Template template = cfg.getTemplate("");
			StringWriter writer = new StringWriter();
			template.process(param, writer);
			System.out.println(writer.toString());
			return writer.toString();
		} catch (TemplateNotFoundException e) {
			throw new JavametaException(e);
		} catch (MalformedTemplateNameException e) {
			throw new JavametaException(e);
		} catch (ParseException e) {
			throw new JavametaException(e);
		} catch (IOException e) {
			throw new JavametaException(e);
		} catch (TemplateException e) {
			throw new JavametaException(e);
		}
	}
}
