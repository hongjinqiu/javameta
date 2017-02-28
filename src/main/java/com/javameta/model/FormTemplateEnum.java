package com.javameta.model;

public enum FormTemplateEnum {
	LIST("jm_list_"), FORM("jm_form_"), SELECTOR("jm_selector_"), QUERY("jm_query_");

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
