package com.javameta.model.iterate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.javameta.model.ValueBusinessObject;
import com.javameta.model.datasource.BizField;
import com.javameta.model.datasource.Datasource;
import com.javameta.model.datasource.DetailData;
import com.javameta.model.datasource.Field;
import com.javameta.model.datasource.FixField;
import com.javameta.util.New;
import com.javameta.value.Value;
import com.javameta.value.ValueNull;

public class DatasourceIterator {
	public static void iterateFieldValueBo(Datasource datasource, ValueBusinessObject valueBo, final IDatasourceFieldDataIterate iterate) {
		iterateLineValueBo(datasource, valueBo, new IDatasourceLineDataIterate() {
			@Override
			public void iterate(List<Field> fieldLi, Map<String, Value> data, int rowIndex) {
				for (Field field: fieldLi) {
					iterate.iterate(field, data, rowIndex);
				}
			}
		});
	}
	
	public static List<Field> getFixFieldLi(FixField fixField) {
		List<Field> fieldLi = new ArrayList<Field>();
		fieldLi.add(fixField.getPrimaryKey());
		fieldLi.add(fixField.getCreateBy());
		fieldLi.add(fixField.getCreateTime());
		fieldLi.add(fixField.getCreateUnit());
		fieldLi.add(fixField.getModifyBy());
		fieldLi.add(fixField.getModifyUnit());
		fieldLi.add(fixField.getModifyTime());
		fieldLi.add(fixField.getRemark());
		return fieldLi;
	}
	
	private static List<Field> getDataSetFieldLi(FixField fixField, BizField bizField) {
		List<Field> fieldLi = new ArrayList<Field>();
		List<Field> masterFieldLi = getFixFieldLi(fixField);
		fieldLi.addAll(masterFieldLi);
		fieldLi.addAll(bizField.getField());
		return fieldLi;
	}
	
	public static void iterateFieldTwoValueBo(Datasource datasource, ValueBusinessObject destValueBo, ValueBusinessObject srcValueBo, IDatasourceFieldTwoDataIterate iterate) {
		List<Field> masterFieldLi = getDataSetFieldLi(datasource.getMasterData().getFixField(), datasource.getMasterData().getBizField());
		for (Field field: masterFieldLi) {
			iterate.iterate(field, destValueBo.getMasterData(), srcValueBo.getMasterData());
		}
		
		for (DetailData detailData: datasource.getDetailData()) {
			List<Field> detailFieldLi = getDataSetFieldLi(detailData.getFixField(), detailData.getBizField());
			List<Map<String, Value>> destDetailDataLi = destValueBo.getDetailData().get(detailData.getId());
			List<Map<String, Value>> srcDetailDataLi = srcValueBo.getDetailData().get(detailData.getId());
			for (int i = 0; i < destDetailDataLi.size(); i++) {
				for (Field field: detailFieldLi) {
					iterate.iterate(field, destDetailDataLi.get(i), srcDetailDataLi.get(i));
				}
			}
		}
	}
	
	/*
	public static void iterateMasterDataField(Datasource datasource, IDatasourceMasterDataFieldIterate iterate) {
		List<Field> masterFieldLi = getDataSetFieldLi(datasource.getMasterData().getFixField(), datasource.getMasterData().getBizField());
		for (Field field: masterFieldLi) {
			iterate.iterate(datasource.getMasterData(), field);
		}
	}
	
	public static void iterateDetailDataField(Datasource datasource, IDatasourceDetailDataFieldIterate iterate) {
		for (DetailData detailData: datasource.getDetailData()) {
			List<Field> detailFieldLi = getDataSetFieldLi(detailData.getFixField(), detailData.getBizField());
			for (Field field: detailFieldLi) {
				iterate.iterate(detailData, field);
			}
		}
	}
	*/
	
	public static void iterateField(Datasource datasource, IDatasourceFieldIterate iterate) {
		List<Field> masterFieldLi = getDataSetFieldLi(datasource.getMasterData().getFixField(), datasource.getMasterData().getBizField());
		for (Field field: masterFieldLi) {
			iterate.iterate(field);
		}
		for (DetailData detailData: datasource.getDetailData()) {
			List<Field> detailFieldLi = getDataSetFieldLi(detailData.getFixField(), detailData.getBizField());
			for (Field field: detailFieldLi) {
				iterate.iterate(field);
			}
		}
	}
	
	public static void iterateLineField(Datasource datasource, IDatasourceLineFieldIterate iterate) {
		List<Field> masterFieldLi = getDataSetFieldLi(datasource.getMasterData().getFixField(), datasource.getMasterData().getBizField());
		iterate.iterate(masterFieldLi);
		for (DetailData detailData: datasource.getDetailData()) {
			List<Field> detailFieldLi = getDataSetFieldLi(detailData.getFixField(), detailData.getBizField());
			iterate.iterate(detailFieldLi);
		}
	}


	public static void iterateDiffValueBo(Datasource datasource, ValueBusinessObject destValueBo, ValueBusinessObject srcValueBo, IDatasourceDiffLineDataIterate iterate) {
		iterateDiffMasterDataValueBo(datasource, destValueBo, srcValueBo, iterate);
		iterateDiffDetailDataValueBo(datasource, destValueBo, srcValueBo, iterate);
	}
	
	private static void iterateDiffMasterDataValueBo(Datasource datasource, ValueBusinessObject destValueBo, ValueBusinessObject srcValueBo, IDatasourceDiffLineDataIterate iterate) {
		List<Field> fieldLi = getDataSetFieldLi(datasource.getMasterData().getFixField(), datasource.getMasterData().getBizField());
		iterate.iterate(fieldLi, destValueBo.getMasterData(), srcValueBo.getMasterData());
	}
	
	private static void iterateDiffDetailDataValueBo(Datasource datasource, ValueBusinessObject destValueBo, ValueBusinessObject srcValueBo, IDatasourceDiffLineDataIterate iterate) {
		for (DetailData detailData: datasource.getDetailData()) {
			List<Field> detailFieldLi = getDataSetFieldLi(detailData.getFixField(), detailData.getBizField());
			
			List<Map<String, Value>> destDataLi = destValueBo.getDetailData().get(detailData.getId());
			List<Map<String, Value>> srcDataLi = srcValueBo.getDetailData().get(detailData.getId());
			
			String idFieldName = detailData.getFixField().getPrimaryKey().getId();
			Map<Integer, Map<String, Value>> destDataIdDict = getDataIdDict(idFieldName, destDataLi);
			Map<Integer, Map<String, Value>> srcDataIdDict = getDataIdDict(idFieldName, srcDataLi);
			
			// delete, destData == null, srcData != null
			for (Map<String, Value> srcData: srcDataLi) {
				if (destDataIdDict.get(srcData.get(idFieldName).getInt()) == null) {
					Map<String, Value> destData = null;
					iterate.iterate(detailFieldLi, destData, srcData);
				}
			}
			
			// insert, destData != null, srcData == null, (destData.id == ValueNull || destData.id == 0)
			for (Map<String, Value> destData: destDataLi) {
				Value id = destData.get(idFieldName);
				if (id.equals(ValueNull.INSTANCE) || id.getInt() == 0) {
					Map<String, Value> srcData = null;
					iterate.iterate(detailFieldLi, destData, srcData);
				}
			}
			
			// update, destData != null, srcData != null
			for (Map<String, Value> destData: destDataLi) {
				Value id = destData.get(idFieldName);
				if (!(id.equals(ValueNull.INSTANCE) || id.getInt() == 0)) {
					Map<String, Value> srcData = srcDataIdDict.get(id.getInt());
					if (srcData != null) {
						iterate.iterate(detailFieldLi, destData, srcData);
					}
				}
			}
		}
	}
	
	private static Map<Integer, Map<String, Value>> getDataIdDict(String idFieldName, List<Map<String, Value>> dataLi) {
		Map<Integer, Map<String, Value>> result = New.hashMap();
		for (Map<String, Value> dataItem: dataLi) {
			result.put(dataItem.get(idFieldName).getInt(), dataItem);
		}
		return result;
	}
	
	public static void iterateLineValueBo(Datasource datasource, ValueBusinessObject valueBo, IDatasourceLineDataIterate iterate) {
		iterateMasterDataValueBo(datasource, valueBo, iterate);
		iterateDetailDataValueBo(datasource, valueBo, iterate);
	}
	
	private static void iterateMasterDataValueBo(Datasource datasource, ValueBusinessObject valueBo, IDatasourceLineDataIterate iterate) {
		List<Field> dataSetFieldLi = getDataSetFieldLi(datasource.getMasterData().getFixField(), datasource.getMasterData().getBizField());
		int rowIndex = 0;
		iterate.iterate(dataSetFieldLi, valueBo.getMasterData(), rowIndex);
	}
	
	private static void iterateDetailDataValueBo(Datasource datasource, ValueBusinessObject valueBo, IDatasourceLineDataIterate iterate) {
		for (DetailData detailData: datasource.getDetailData()) {
			List<Field> detailFieldLi = getDataSetFieldLi(detailData.getFixField(), detailData.getBizField());
			List<Map<String, Value>> detailValueLi = valueBo.getDetailData().get(detailData.getId());
			for (int j = 0; j < detailValueLi.size(); j++) {
				iterate.iterate(detailFieldLi, detailValueLi.get(j), j);
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
