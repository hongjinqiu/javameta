package com.javameta.model;

public enum FormTemplateEnum {
	LIST("list_"), FORM("form_"), SELECTOR("selector_"), QUERY("query_");

	private String prefix;

	FormTemplateEnum(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
