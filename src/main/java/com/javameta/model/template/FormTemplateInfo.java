package com.javameta.model.template;

import java.io.Serializable;

public class FormTemplateInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String path;
	private FormTemplate formTemplate;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public FormTemplate getFormTemplate() {
		return formTemplate;
	}

	public void setFormTemplate(FormTemplate formTemplate) {
		this.formTemplate = formTemplate;
	}

}
