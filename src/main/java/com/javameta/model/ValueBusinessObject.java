package com.javameta.model;

import java.util.List;
import java.util.Map;

import com.javameta.util.New;
import com.javameta.value.Value;

/**
 * 业务对象
 * @author hongjinqiu
 *
 */
public class ValueBusinessObject {
	private Map<String, Value> masterData;
	private Map<String, List<Map<String, Value>>> detailData = New.hashMap();

	public Map<String, Value> getMasterData() {
		return masterData;
	}

	public void setMasterData(Map<String, Value> masterData) {
		this.masterData = masterData;
	}

	public Map<String, List<Map<String, Value>>> getDetailData() {
		return detailData;
	}

	public void setDetailData(Map<String, List<Map<String, Value>>> detailData) {
		this.detailData = detailData;
	}

}
