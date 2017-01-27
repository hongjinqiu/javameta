package com.javameta.model;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.javameta.JavametaException;
import com.javameta.model.datasource.Datasource;
import com.javameta.model.datasource.Field;
import com.javameta.model.iterate.DatasourceIterator;
import com.javameta.model.iterate.IDatasourceLineFieldIterate;
import com.javameta.util.New;
import com.javameta.value.Value;
import com.javameta.value.ValueBytes;
import com.javameta.value.ValueDate;
import com.javameta.value.ValueDecimal;
import com.javameta.value.ValueDouble;
import com.javameta.value.ValueFloat;
import com.javameta.value.ValueInt;
import com.javameta.value.ValueLong;
import com.javameta.value.ValueNull;
import com.javameta.value.ValueShort;
import com.javameta.value.ValueString;
import com.javameta.value.ValueTime;
import com.javameta.value.ValueTimestamp;

public class BusinessDataType {
	public static ValueBusinessObject convertMapToValueBusinessObject(Datasource datasource, Map<String, Object> o) {
		final Map<String, Object> o2 = o;
		ValueBusinessObject valueBo = new ValueBusinessObject();

		final Map<String, Value> masterDataVo = New.hashMap();
		final Map<String, List<Map<String, Value>>> detailDataVo = New.hashMap();
		DatasourceIterator.iterateLineField(datasource, new IDatasourceLineFieldIterate() {
			@Override
			public void iterate(List<Field> fieldLi) {
				Field firstField = fieldLi.get(0);
				if (firstField.isMasterField()) {
					Map<String, Object> masterObject = (Map<String, Object>)o2.get(firstField.getDataSetId());
					for (Field field: fieldLi) {
						String value = ObjectUtils.toString(masterObject.get(field.getFieldName()), "");
						masterDataVo.put(field.getFieldName(), convertStringToValue(field, value));
					}
					putExtraField(masterDataVo, masterObject);
				} else {
					if (detailDataVo.get(firstField.getDataSetId()) == null) {
						detailDataVo.put(firstField.getFieldName(), new ArrayList<Map<String, Value>>());
					}
					List<Map<String, Value>> detailDataVoLi = detailDataVo.get(firstField.getFieldName());
					List<Map<String, Object>> detailObject = (List<Map<String, Object>>)o2.get(firstField.getDataSetId());
					for (int i = 0; i < detailObject.size(); i++) {
						Map<String, Value> detailData = New.hashMap();
						Map<String, Object> detailLineObject = detailObject.get(i);
						for (Field field: fieldLi) {
							String value = ObjectUtils.toString(detailLineObject.get(field.getFieldName()), "");
							detailData.put(field.getFieldName(), convertStringToValue(field, value));
						}
						putExtraField(detailData, detailLineObject);
						detailDataVoLi.add(detailData);
					}
				}
			}
		});
		valueBo.setMasterData(masterDataVo);
		valueBo.setDetailData(detailDataVo);

		return valueBo;
	}
	
	public static JSONObject convertValueBusinessObjectToJSONStringObject(ValueBusinessObject valueBo) {
		JSONObject result = new JSONObject();
		
		JSONObject masterData = new JSONObject();
		for (Map.Entry<String, Value> entry: valueBo.getMasterData().entrySet()) {
			masterData.put(entry.getKey(), entry.getValue().getString());
		}
		result.put("A", masterData);

		for (Map.Entry<String, List<Map<String, Value>>> entry: valueBo.getDetailData().entrySet()) {
			String dataSetId = entry.getKey();
			JSONArray detailDataLi = new JSONArray();
			for (Map<String, Value> line: entry.getValue()) {
				JSONObject lineObject = new JSONObject();
				for (Map.Entry<String, Value> subEntry: line.entrySet()) {
					lineObject.put(subEntry.getKey(), subEntry.getValue().getString());
				}
				detailDataLi.add(lineObject);
			}
			result.put(dataSetId, detailDataLi);
		}

		return result;
	}
	
	public static JSONObject convertValueDataObjectToJSONStringObject(Map<String, Value> valueData) {
		JSONObject result = new JSONObject();
		
		for (Map.Entry<String, Value> entry: valueData.entrySet()) {
			result.put(entry.getKey(), entry.getValue().getString());
		}

		return result;
	}
	
	private static void putExtraField(Map<String, Value> vo, Map<String, Object> object) {
		for (Iterator<String> it = object.keySet().iterator(); it.hasNext(); ) {
			String key = it.next();
			if (vo.get(key) == null) {
				String value = ObjectUtils.toString(object.get(key), "");
				vo.put(key, ValueString.get(value));
			}
		}
	}

	public static Value convertStringToValue(Field field, String value) {
		if (StringUtils.isEmpty(value)) {
			return ValueNull.INSTANCE;
		}
		if (field.getFieldType().equalsIgnoreCase("FLOAT")) {
			return ValueFloat.get(Float.parseFloat(value));
		}
		if (field.getFieldType().equalsIgnoreCase("DOUBLE")) {
			return ValueDouble.get(Double.parseDouble(value));
		}
		if (field.getFieldType().equalsIgnoreCase("DECIMAL")) {
			return ValueDecimal.get(new BigDecimal(value));
		}
		if (field.getFieldType().equalsIgnoreCase("SHORT")) {
			return ValueShort.get(Short.parseShort(value));
		}
		if (field.getFieldType().equalsIgnoreCase("INT")) {
			return ValueInt.get(Integer.parseInt(value));
		}
		if (field.getFieldType().equalsIgnoreCase("LONG")) {
			return ValueLong.get(Long.parseLong(value));
		}
		if (field.getFieldType().equalsIgnoreCase("NULL")) {
			return ValueNull.INSTANCE;
		}
		if (field.getFieldType().equalsIgnoreCase("STRING")) {
			return ValueString.get(value);
		}
		if (field.getFieldType().equalsIgnoreCase("DATE")) {
			String tmpValue = value;
			tmpValue = tmpValue.replaceAll("-|/", "");
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			try {
				Date date = format.parse(tmpValue);
				return ValueDate.get(date);
			} catch (ParseException e) {
				throw new JavametaException(e);
			}
		}
		if (field.getFieldType().equalsIgnoreCase("TIME")) {
			String tmpValue = value;
			tmpValue = tmpValue.replaceAll("-|/|:", "");
			if (tmpValue.length() == 6) {
				tmpValue = "20010203" + tmpValue;
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			try {
				Date date = format.parse(tmpValue);
				return ValueTime.get(date);
			} catch (ParseException e) {
				throw new JavametaException(e);
			}
		}
		if (field.getFieldType().equalsIgnoreCase("TIMESTAMP")) {
			String tmpValue = value;
			tmpValue = tmpValue.replaceAll("-|/|:| ", "");
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			try {
				Date date = format.parse(tmpValue);
				return ValueTimestamp.get(date);
			} catch (ParseException e) {
				throw new JavametaException(e);
			}
		}
		if (field.getFieldType().equalsIgnoreCase("BYTES")) {
			return ValueBytes.get(value.getBytes());
		}
		return ValueString.get(value);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JSONObject obj = JSONObject.fromObject("{'name': 'test'}");
		System.out.println(obj);
		System.out.println(obj.get("name"));
	}

}
