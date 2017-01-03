package com.javameta.model.iterate;

import java.util.List;
import java.util.Map;

import com.javameta.model.datasource.Field;
import com.javameta.value.Value;

public interface IDatasourceDiffLineDataIterate {
	/**
	 * delete, destData == null, srcData != null
	 * insert, destData != null, srcData == null, (destData.id == ValueNull || destData.id == 0)
	 * update, destData != null, srcData != null
	 * @param fieldLi
	 * @param destData
	 * @param srcData
	 */
	public void iterate(List<Field> fieldLi, Map<String, Value> destData, Map<String, Value> srcData);
}
