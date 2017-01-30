package com.javameta.model.handler;

import java.util.List;
import java.util.Map;

import com.javameta.model.ValueBusinessObject;
import com.javameta.model.datasource.Field;
import com.javameta.value.Value;

public class DiffDataRow {
	//ADD("新增"),BEFORE_UPDATE("修改前"),AFTER_UPDATE("修改后"),DELETE("删除")
	public static final int ADD = 1;
	public static final int BEFORE_UPDATE = 2;
	public static final int AFTER_UPDATE = 3;
	public static final int DELETE = 4;

	private List<Field> fieldLi;
	private ValueBusinessObject destBo;
	private Map<String, Value> destData;// 页面上传入
	private ValueBusinessObject srcBo;
	private Map<String, Value> srcData;// 数据库中

	public List<Field> getFieldLi() {
		return fieldLi;
	}

	public void setFieldLi(List<Field> fieldLi) {
		this.fieldLi = fieldLi;
	}

	public Map<String, Value> getDestData() {
		return destData;
	}

	public void setDestData(Map<String, Value> destData) {
		this.destData = destData;
	}

	public Map<String, Value> getSrcData() {
		return srcData;
	}

	public void setSrcData(Map<String, Value> srcData) {
		this.srcData = srcData;
	}

	public ValueBusinessObject getDestBo() {
		return destBo;
	}

	public void setDestBo(ValueBusinessObject destBo) {
		this.destBo = destBo;
	}

	public ValueBusinessObject getSrcBo() {
		return srcBo;
	}

	public void setSrcBo(ValueBusinessObject srcBo) {
		this.srcBo = srcBo;
	}

}
