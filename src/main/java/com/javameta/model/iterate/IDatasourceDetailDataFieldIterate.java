package com.javameta.model.iterate;

import com.javameta.model.datasource.DetailData;
import com.javameta.model.datasource.Field;

public interface IDatasourceDetailDataFieldIterate {
	public void iterate(DetailData detailData, Field field);
}
