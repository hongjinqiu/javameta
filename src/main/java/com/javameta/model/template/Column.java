package com.javameta.model.template;

public abstract class Column {
	public abstract String getName();
	public abstract Boolean getAuto();
	public abstract String getDsFieldMap();
	public abstract void setAuto(Boolean value);
	public abstract String getText();
	public abstract void setText(String value);
	public abstract Boolean getHideable();
	public abstract void setHideable(Boolean value);
	public abstract Boolean getFixReadOnly();
	public abstract void setFixReadOnly(Boolean value);
	public abstract Boolean getZeroShowEmpty();
	public abstract void setZeroShowEmpty(Boolean value);
	public abstract String getXmlName();
	public abstract void setXmlName(String xmlName);
	public abstract Integer getColSpan();
	public abstract String getColumnWidth();
	public abstract String getLabelWidth();
}
