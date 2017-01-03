package com.javameta.model.iterate;

import java.util.Map;

import com.javameta.model.datasource.Field;
import com.javameta.value.Value;

public interface IDatasourceFieldTwoDataIterate {
	public void iterate(Field field, Map<String, Value> destData, Map<String, Value> srcData);
}
