package com.javameta.model.iterate;

import com.javameta.model.datasource.Field;
import com.javameta.model.datasource.MasterData;

public interface IDatasourceMasterDataFieldIterate {
	public void iterate(MasterData masterData, Field field);
}
