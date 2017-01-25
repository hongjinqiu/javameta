package com.javameta.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;

import com.javameta.JavametaException;
import com.javameta.expression.ExpressionParser;
import com.javameta.model.datasource.Datasource;
import com.javameta.model.datasource.DatasourceInfo;
import com.javameta.model.datasource.DetailData;
import com.javameta.model.datasource.MasterData;
import com.javameta.model.fieldpool.Field;
import com.javameta.model.fieldpool.Fields;
import com.javameta.model.fieldpool.ObjectFactory;
import com.javameta.model.iterate.DatasourceIterator;
import com.javameta.model.iterate.IDatasourceFieldDataIterate;
import com.javameta.model.iterate.IDatasourceFieldIterate;
import com.javameta.model.iterate.IDatasourceFieldTwoDataIterate;
import com.javameta.model.iterate.IDatasourceLineDataIterate;
import com.javameta.model.template.ColumnModel;
import com.javameta.model.template.FormTemplate;
import com.javameta.util.FieldExtendUtil;
import com.javameta.util.JavametaProperties;
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

public class DatasourceFactory {
	private static Class[] classes = new Class[] { com.javameta.model.fieldpool.ObjectFactory.class, com.javameta.model.template.ObjectFactory.class,
			com.javameta.model.datasource.ObjectFactory.class, };

	private static JAXBContext context;
	static {
		try {
			context = JAXBContext.newInstance(classes);
		} catch (JAXBException e) {
			throw new JavametaException(e);
		}
	}

	private static Map<String, DatasourceInfo> gDatasourceInfoMap = new ConcurrentHashMap<String, DatasourceInfo>();
	private static Object lock = new Object();

	public List<DatasourceInfo> getDatasourceInfoLi() {
		synchronized (lock) {
			List<DatasourceInfo> list = New.arrayList();

			if (gDatasourceInfoMap.size() == 0) {
				loadDatasource();
			}

			for (Map.Entry<String, DatasourceInfo> item : gDatasourceInfoMap.entrySet()) {
				list.add(SerializationUtils.clone(item.getValue()));
			}

			return list;
		}
	}

	public List<DatasourceInfo> refretorDatasourceInfo() {
		clearDatasource();
		loadDatasource();
		List<DatasourceInfo> list = New.arrayList();
		for (Map.Entry<String, DatasourceInfo> item : gDatasourceInfoMap.entrySet()) {
			list.add(SerializationUtils.clone(item.getValue()));
		}
		return list;
	}

	public Datasource getDatasource(String id) {
		return getDatasourceInfo(id).getDatasource();
	}

	public DatasourceInfo getDatasourceInfo(String id) {
		if (JavametaProperties.getProperty("debug").equals("true")) {
			DatasourceInfo datasourceInfo = findDatasourceInfo(id);
			if (datasourceInfo != null) {
				datasourceInfo = loadSingleDatasource(new File(datasourceInfo.getPath()));
				applyReverseRelation(datasourceInfo.getDatasource());
				if (datasourceInfo.getDatasource().getId().equals(id)) {
					return SerializationUtils.clone(datasourceInfo);
				}
			}
			clearDatasource();
			loadDatasource();
			datasourceInfo = findDatasourceInfo(id);
			if (datasourceInfo != null) {
				return SerializationUtils.clone(datasourceInfo);
			}
			throw new JavametaException(id + " not exists in Datasource list");
		}
		synchronized (lock) {
			if (gDatasourceInfoMap.size() == 0) {
				loadDatasource();
			}
		}
		DatasourceInfo datasourceInfo = findDatasourceInfo(id);
		if (datasourceInfo != null) {
			return SerializationUtils.clone(datasourceInfo);
		}
		throw new JavametaException(id + " not exists in Datasource list");
	}

	public ValueBusinessObject getInstanceByDS(Datasource datasource) {
		ValueBusinessObject valueBo = getBo(datasource);
		applyDefaultValueExpr(datasource, valueBo);
		applyCalcValueExpr(datasource, valueBo);
		applyRelationFieldValue(datasource, valueBo);
		return valueBo;
	}

	public ValueBusinessObject getInstance(String datasourceModelId) {
		Datasource datasource = getDatasource(datasourceModelId);
		return getInstanceByDS(datasource);
	}

	public ValueBusinessObject GetCopyInstance(String datasourceModelId, ValueBusinessObject srcValueBo) {
		Datasource datasource = getDatasource(datasourceModelId);
		ValueBusinessObject valueBo = getInstanceByDS(datasource);
		applyCopy(datasource, valueBo, srcValueBo);
		applyCalcValueExpr(datasource, valueBo);
		applyRelationFieldValue(datasource, valueBo);
		return valueBo;
	}

	public boolean isDataDifferent(List<com.javameta.model.datasource.Field> fieldLi, Map<String, Value> destData, Map<String, Value> srcData) {
		for (com.javameta.model.datasource.Field field : fieldLi) {
			Value destValue = destData.get(field.getId());
			Value srcValue = srcData.get(field.getId());
			if ((destValue == ValueNull.INSTANCE) && (srcValue == ValueNull.INSTANCE)) {

			} else if ((destValue == ValueNull.INSTANCE) && (srcValue != ValueNull.INSTANCE)) {
				return true;
			} else if ((destValue != ValueNull.INSTANCE) && (srcValue == ValueNull.INSTANCE)) {
				return true;
			} else {
				if (!destValue.getObject().equals(srcValue.getObject())) {
					return true;
				}
			}
		}
		return false;
	}

	public List<Map<String, Object>> getRelationLi(Datasource datasource, final ValueBusinessObject valueBo) {
		String idFieldName = datasource.getMasterData().getFixField().getPrimaryKey().getId();
		Value idValue = valueBo.getMasterData().get(idFieldName);
		if (idValue == ValueNull.INSTANCE) {
			return new ArrayList<Map<String, Object>>();
		}
		if (idValue.getInt() == 0) {
			return new ArrayList<Map<String, Object>>();
		}

		final List<Map<String, Object>> list = New.arrayList();
		DatasourceIterator.iterateFieldValueBo(datasource, valueBo, new IDatasourceFieldDataIterate() {
			@Override
			public void iterate(com.javameta.model.datasource.Field field, Map<String, Value> data, int rowIndex) {
				if (field.isRelationField()) {
					JSONObject bo = BusinessDataType.convertValueBusinessObjectToJSONStringObject(valueBo);
					JSONObject dataJson = BusinessDataType.convertValueDataObjectToJSONStringObject(data);
					com.javameta.model.datasource.Field.RelationDS.RelationItem relationItem = field.getRelationItem(bo, dataJson);
					if (relationItem != null) {
						Value fieldValue = data.get(field.getId());
						if (fieldValue != ValueNull.INSTANCE) {
							if (fieldValue.getInt() != 0) {
								boolean isContain = false;
								for (Map<String, Object> item : list) {
									int tmpRelationId = (Integer) (item.get("relationId"));
									String tmpSelectorId = (String) (item.get("selectorId"));
									if (tmpRelationId == fieldValue.getInt() && tmpSelectorId.equals(relationItem.getId())) {
										isContain = true;
										break;
									}
								}
								if (!isContain) {
									Map<String, Object> item = New.hashMap();
									item.put("relationId", fieldValue.getInt());
									item.put("selectorId", relationItem.getId());
									list.add(item);
								}
							}
						}
					}
				}
			}
		});
		return list;
	}

	public Map<String, Value> getDataSetNewData(Datasource datasource, String dataSetId, ValueBusinessObject valueBo) {
		Map<String, Value> data = New.hashMap();
		applyDataSetDefaultValue(datasource, dataSetId, valueBo, data);
		applyDataSetCalcValue(datasource, dataSetId, valueBo, data);
		return data;
	}

	private void applyDataSetDefaultValue(Datasource datasource, final String dataSetId, final ValueBusinessObject valueBo, final Map<String, Value> data) {
		DatasourceIterator.iterateField(datasource, new IDatasourceFieldIterate() {
			@Override
			public void iterate(com.javameta.model.datasource.Field field) {
				if (field.getDataSetId().equals(dataSetId)) {
					String content = "";
					if (field.getDefaultValueExpr() != null) {
						String mode = field.getDefaultValueExpr().getMode();
						if (StringUtils.isEmpty(mode) || mode.equals("text")) {
							content = field.getDefaultValueExpr().getValue();
						} else if (mode.equals("js")) {
							JSONObject bo = BusinessDataType.convertValueBusinessObjectToJSONStringObject(valueBo);
							JSONObject jsonData = BusinessDataType.convertValueDataObjectToJSONStringObject(data);
							content = ExpressionParser.parseModel(bo, jsonData, field.getDefaultValueExpr().getValue());
						}
					}
					applyFieldValueByString(field, data, content);
				}
			}
		});
	}

	private void applyDataSetCalcValue(Datasource datasource, final String dataSetId, final ValueBusinessObject valueBo, final Map<String, Value> data) {
		DatasourceIterator.iterateField(datasource, new IDatasourceFieldIterate() {
			@Override
			public void iterate(com.javameta.model.datasource.Field field) {
				if (field.getDataSetId().equals(dataSetId)) {
					String content = "";
					if (field.getCalcValueExpr() != null) {
						String mode = field.getCalcValueExpr().getMode();
						if (StringUtils.isEmpty(mode) || mode.equals("text")) {
							content = field.getCalcValueExpr().getValue();
						} else if (mode.equals("js")) {
							JSONObject bo = BusinessDataType.convertValueBusinessObjectToJSONStringObject(valueBo);
							JSONObject jsonData = BusinessDataType.convertValueDataObjectToJSONStringObject(data);
							content = ExpressionParser.parseModel(bo, jsonData, field.getCalcValueExpr().getValue());
						}
					}
					applyFieldValueByString(field, data, content);
				}
			}
		});
	}

	private void applyCopy(Datasource datasource, final ValueBusinessObject destBo, ValueBusinessObject srcBo) {
		// 加上对应的行数
		DatasourceIterator.iterateLineValueBo(datasource, srcBo, new IDatasourceLineDataIterate() {
			@Override
			public void iterate(List<com.javameta.model.datasource.Field> fieldLi, Map<String, Value> data, int rowIndex) {
				if (!fieldLi.get(0).isMasterField()) {
					if (destBo.getDetailData().get(fieldLi.get(0).getDataSetId()) == null) {
						destBo.getDetailData().put(fieldLi.get(0).getDataSetId(), new ArrayList<Map<String, Value>>());
					}
					List<Map<String, Value>> detailDataLi = destBo.getDetailData().get(fieldLi.get(0).getDataSetId());
					detailDataLi.add(new HashMap<String, Value>());
				}
			}
		});
		applyDefaultValueExpr(datasource, destBo);
		DatasourceIterator.iterateFieldTwoValueBo(datasource, destBo, srcBo, new IDatasourceFieldTwoDataIterate() {
			@Override
			public void iterate(com.javameta.model.datasource.Field field, Map<String, Value> destData, Map<String, Value> srcData) {
				boolean dataSetAllowCopy = true;
				if (field.isMasterField()) {
					MasterData masterData = getMasterData(field);
					if (!masterData.getAllowCopy()) {
						dataSetAllowCopy = false;
					}
				} else {
					DetailData detailData = getDetailData(field);
					if (!detailData.getAllowCopy()) {
						dataSetAllowCopy = false;
					}
				}
				if (dataSetAllowCopy && (field.getAllowCopy() == null || field.getAllowCopy())) {
					destData.put(field.getId(), srcData.get(field.getId()));
				}
			}
		});
		// dataSet.allowCopy
		DatasourceIterator.iterateLineValueBo(datasource, destBo, new IDatasourceLineDataIterate() {
			@Override
			public void iterate(List<com.javameta.model.datasource.Field> fieldLi, Map<String, Value> data, int rowIndex) {
				if (!fieldLi.get(0).isMasterField()) {
					DetailData detailData = getDetailData(fieldLi.get(0));
					if (!detailData.getAllowCopy()) {
						destBo.getDetailData().put(detailData.getId(), new ArrayList<Map<String, Value>>());
					}
				}
			}
		});
	}

	private MasterData getMasterData(com.javameta.model.datasource.Field field) {
		Datasource datasource = this.getDatasource(field.getDatasourceId());
		if (field.isMasterField()) {
			return datasource.getMasterData();
		}
		return null;
	}

	private DetailData getDetailData(com.javameta.model.datasource.Field field) {
		if (field.isMasterField()) {
			return null;
		}
		Datasource datasource = getDatasource(field.getDatasourceId());
		for (DetailData detailData : datasource.getDetailData()) {
			if (detailData.getId().equals(field.getDataSetId())) {
				return detailData;
			}
		}
		return null;
	}

	private ValueBusinessObject getBo(Datasource datasource) {
		ValueBusinessObject valueBo = new ValueBusinessObject();

		final Map<String, Value> masterData = New.hashMap();
		valueBo.setMasterData(masterData);

		for (DetailData detailData : datasource.getDetailData()) {
			List<Map<String, Value>> list = New.arrayList();
			valueBo.getDetailData().put(detailData.getId(), list);
		}

		DatasourceIterator.iterateField(datasource, new IDatasourceFieldIterate() {
			@Override
			public void iterate(com.javameta.model.datasource.Field field) {
				if (field.isMasterField()) {
					String content = "";
					applyFieldValueByString(field, masterData, content);
				}
			}
		});

		return valueBo;
	}

	private void applyDefaultValueExpr(Datasource datasource, final ValueBusinessObject valueBo) {
		DatasourceIterator.iterateFieldValueBo(datasource, valueBo, new IDatasourceFieldDataIterate() {
			@Override
			public void iterate(com.javameta.model.datasource.Field field, Map<String, Value> data, int rowIndex) {
				String content = "";
				if (field.getDefaultValueExpr() != null) {
					String mode = field.getDefaultValueExpr().getMode();
					if (StringUtils.isEmpty(mode) || mode.equals("text")) {
						content = field.getDefaultValueExpr().getValue();
					} else if (mode.equals("js")) {
						JSONObject bo = BusinessDataType.convertValueBusinessObjectToJSONStringObject(valueBo);
						JSONObject jsonData = BusinessDataType.convertValueDataObjectToJSONStringObject(data);
						content = ExpressionParser.parseModel(bo, jsonData, field.getDefaultValueExpr().getValue());
					}
				}
				applyFieldValueByString(field, data, content);
			}
		});
	}

	private void applyCalcValueExpr(Datasource datasource, final ValueBusinessObject valueBo) {
		DatasourceIterator.iterateFieldValueBo(datasource, valueBo, new IDatasourceFieldDataIterate() {
			@Override
			public void iterate(com.javameta.model.datasource.Field field, Map<String, Value> data, int rowIndex) {
				String content = "";
				if (field.getCalcValueExpr() != null) {
					String mode = field.getCalcValueExpr().getMode();
					if (StringUtils.isEmpty(mode) || mode.equals("text")) {
						content = field.getCalcValueExpr().getValue();
					} else if (mode.equals("js")) {
						JSONObject bo = BusinessDataType.convertValueBusinessObjectToJSONStringObject(valueBo);
						JSONObject jsonData = BusinessDataType.convertValueDataObjectToJSONStringObject(data);
						content = ExpressionParser.parseModel(bo, jsonData, field.getCalcValueExpr().getValue());
					}
				}
				applyFieldValueByString(field, data, content);
			}
		});
	}

	private void applyRelationFieldValue(Datasource datasource, final ValueBusinessObject valueBo) {
		DatasourceIterator.iterateFieldValueBo(datasource, valueBo, new IDatasourceFieldDataIterate() {
			@Override
			public void iterate(com.javameta.model.datasource.Field field, Map<String, Value> data, int rowIndex) {
				if (field.isRelationField()) {
					com.javameta.model.datasource.Field.RelationDS.RelationItem relationItem = parseRelationExpr(field, valueBo, data);
					if (relationItem != null) {
						JSONObject obj = new JSONObject();
						obj.put("id", relationItem.getId());
						obj.put("relationExpr", true);
						obj.put("relationModelId", relationItem.getRelationModelId());
						obj.put("relationDataSetId", relationItem.getRelationDataSetId());
						obj.put("displayField", relationItem.getDisplayField());
						obj.put("valueField", relationItem.getValueField());
						data.put(field.getId() + "_ref", ValueString.get(obj.toString()));
					}
				}
			}
		});
	}

	public com.javameta.model.datasource.Field.RelationDS.RelationItem parseRelationExpr(com.javameta.model.datasource.Field field, ValueBusinessObject valueBo,
			Map<String, Value> data) {
		for (com.javameta.model.datasource.Field.RelationDS.RelationItem relationItem : field.getRelationDS().getRelationItem()) {
			String mode = relationItem.getRelationExpr().getMode();
			String value = relationItem.getRelationExpr().getValue();
			if (StringUtils.isNotEmpty(value)) {
				String content = "";
				if (StringUtils.isEmpty(mode) || mode.equals("text")) {
					content = value;
				} else if (mode.equals("js")) {
					JSONObject bo = BusinessDataType.convertValueBusinessObjectToJSONStringObject(valueBo);
					JSONObject dataJson = BusinessDataType.convertValueDataObjectToJSONStringObject(data);
					content = ExpressionParser.parseModel(bo, dataJson, value);
				}
				if (content.equals("true")) {
					return relationItem;
				}
			}
		}

		return null;
	}

	private void applyFieldValueByString(com.javameta.model.datasource.Field field, Map<String, Value> valueData, String content) {
		Value value = BusinessDataType.convertStringToValue(field, content);
		valueData.put(field.getId(), value);
	}

	private DatasourceInfo findDatasourceInfo(String id) {
		DatasourceInfo datasourceInfo = gDatasourceInfoMap.get(id);
		if (datasourceInfo != null) {
			applyReverseRelation(datasourceInfo.getDatasource());
			return datasourceInfo;
		}
		return null;
	}

	/**
	 * 设field的datasourceId,dataSetId,指向父节点
	 * @param datasource
	 */
	private void applyReverseRelation(Datasource datasource) {
		List<com.javameta.model.datasource.Field> masterFixFieldLi = DatasourceIterator.getFixFieldLi(datasource.getMasterData().getFixField());
		for (com.javameta.model.datasource.Field field : masterFixFieldLi) {
			field.setDatasourceId(datasource.getId());
			field.setDataSetId(datasource.getMasterData().getId());
		}
		for (com.javameta.model.datasource.Field field : datasource.getMasterData().getBizField().getField()) {
			field.setDatasourceId(datasource.getId());
			field.setDataSetId(datasource.getMasterData().getId());
		}

		for (DetailData detailData : datasource.getDetailData()) {
			List<com.javameta.model.datasource.Field> detailFixFieldLi = DatasourceIterator.getFixFieldLi(detailData.getFixField());
			for (com.javameta.model.datasource.Field field : detailFixFieldLi) {
				field.setDatasourceId(datasource.getId());
				field.setDataSetId(detailData.getId());
			}
			for (com.javameta.model.datasource.Field field : detailData.getBizField().getField()) {
				field.setDatasourceId(datasource.getId());
				field.setDataSetId(detailData.getId());
			}
		}
	}

	private void clearDatasource() {
		synchronized (lock) {
			gDatasourceInfoMap.clear();
		}
	}

	private void loadDatasource() {
		synchronized (lock) {
			URL url = this.getClass().getClassLoader().getResource("");
			String scanPath = url.getPath();
			recursiveLoadDatasource(new File(scanPath));
		}
	}

	private void recursiveLoadDatasource(File file) {
		if (file.isDirectory()) {
			for (File subFile : file.listFiles()) {
				recursiveLoadDatasource(subFile);
			}
		} else {
			if (file.getName().startsWith("ds_") && file.getName().endsWith(".xml")) {
				loadSingleDatasource(file);
			}
		}
	}

	private DatasourceInfo loadSingleDatasource(File file) {
		DatasourceInfo datasourceInfo = new DatasourceInfo();
		try {
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Datasource datasource = (Datasource) unmarshaller.unmarshal(file);

			if (StringUtils.isEmpty(datasource.getTableName())) {
				datasource.setTableName(datasource.getId());
			}
			applyFieldExtend(datasource);

			datasourceInfo.setPath(file.getAbsolutePath());
			datasourceInfo.setDatasource(datasource);

			gDatasourceInfoMap.put(datasource.getId(), datasourceInfo);

		} catch (JAXBException e) {
			throw new JavametaException(e);
		}

		return datasourceInfo;
	}

	private Fields getPoolFields() {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("xml/fieldpool.xml");
		try {
			Unmarshaller u = context.createUnmarshaller();
			Fields fields = (Fields) u.unmarshal(in);

			for (Field field : fields.getField()) {
				FieldExtendUtil.extendFieldPoolField(field, fields.getField());
			}

			return fields;
		} catch (JAXBException e) {
			throw new JavametaException(e);
		}
	}

	private Fields getBusinessPoolFields() {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("business_fieldpool.xml");
		try {
			Unmarshaller u = context.createUnmarshaller();
			Fields fields = (Fields) u.unmarshal(in);

			// extend from poolFields
			Fields poolFields = getPoolFields();
			for (Field field : fields.getField()) {
				FieldExtendUtil.extendFieldPoolField(field, poolFields.getField());
			}

			// extend from self
			for (Field field : fields.getField()) {
				FieldExtendUtil.extendFieldPoolField(field, fields.getField());
			}

			return fields;
		} catch (JAXBException e) {
			throw new JavametaException(e);
		}
	}

	private void applyFieldExtend(Datasource datasource) {
		final Fields fields = getBusinessPoolFields();

		// extend from business_fieldpool
		DatasourceIterator.iterateField(datasource, new IDatasourceFieldIterate() {
			@Override
			public void iterate(com.javameta.model.datasource.Field field) {
				FieldExtendUtil.extendFieldPoolField(field, fields.getField());
			}
		});

		// extend from fieldpool
		final Fields pFields = getPoolFields();
		DatasourceIterator.iterateField(datasource, new IDatasourceFieldIterate() {
			@Override
			public void iterate(com.javameta.model.datasource.Field field) {
				FieldExtendUtil.extendFieldPoolField(field, pFields.getField());
			}
		});
	}
	
	public Datasource unmarshal(String path) {
		try {
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Datasource datasource = (Datasource) unmarshaller.unmarshal(new File(path));
			return datasource;
		} catch (JAXBException e) {
			throw new JavametaException(e);
		}
	}
	
	public String marshal(Datasource datasource) {
		try {
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			marshaller.marshal(datasource, out);
			return out.toString();
		} catch (JAXBException e) {
			throw new JavametaException(e);
		}
	}
	
	private static void unMarshalTest() throws Exception {
		JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
		Unmarshaller u = context.createUnmarshaller();
		//		u.setProperty("com.sun.xml.bind.ObjectFactory",new ObjectFactoryEx());// 可以传数组进去,如果有多个,
		InputStream in = DatasourceFactory.class.getClassLoader().getResourceAsStream("xml/fieldpool.xml");// 从classpath目录读取,以/开头反而读取不到,
		Fields fields = (Fields) u.unmarshal(in);

		Marshaller m = context.createMarshaller();
		m.marshal(fields, System.out);
	}

	private static void unMarshalTest2() throws Exception {
		Class[] classes = new Class[] { com.javameta.model.fieldpool.ObjectFactory.class, com.javameta.model.template.ObjectFactory.class,
				com.javameta.model.datasource.ObjectFactory.class, };

		JAXBContext context = JAXBContext.newInstance(classes);
		Unmarshaller u = context.createUnmarshaller();
		//		u.setProperty("com.sun.xml.bind.ObjectFactory",new ObjectFactoryEx());// 可以传数组进去,如果有多个,
		/*
		{
			InputStream in = DatasourceFactory.class.getClassLoader().getResourceAsStream("xml/fieldpool.xml");// 从classpath目录读取,以/开头反而读取不到,
			Fields fields = (Fields) u.unmarshal(in);

			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(fields, System.out);
		}
		 */
		/*
		{
			InputStream in = ModelTemplateFactory.class.getClassLoader().getResourceAsStream("demo/gatheringbill/ds_gatheringbill.xml");// 从classpath目录读取,以/开头反而读取不到,
			Datasource fields = (Datasource)u.unmarshal(in);
			Datasource datasource2 = SerializationUtils.clone(fields);
			
			JSONObject obj = JSONObject.fromObject(fields);
			System.out.println(obj);

			fields.setId("tteset_2");
			fields.getMasterData().setId("test_master");
			
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(fields, System.out);
			System.out.println("\ndatasource2");
			m.marshal(datasource2, System.out);
		}
		 */
		{
			InputStream in = FormTemplateFactory.class.getClassLoader().getResourceAsStream("demo/gatheringbill/form_GatheringBill.xml");// 从classpath目录读取,以/开头反而读取不到,
			FormTemplate fields = (FormTemplate) u.unmarshal(in);

			FormTemplate formTemplate = SerializationUtils.clone(fields);

			ColumnModel columnModel = (ColumnModel) fields.getToolbarOrDataProviderOrColumnModel().get(3);
			columnModel.getIdColumn().setName("test_by_me");

			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(fields, System.out);
			System.out.println("\n-------------\n");
			m.marshal(formTemplate, System.out);
		}
		/*
		*/
		/*
		{
			InputStream in = ModelTemplateFactory.class.getClassLoader().getResourceAsStream("demo/gatheringbill/list_GatheringBill.xml");// 从classpath目录读取,以/开头反而读取不到,
			FormTemplate fields = (FormTemplate)u.unmarshal(in);
			
			Marshaller m = context.createMarshaller();
			m.marshal(fields, System.out);
		}
		{
			InputStream in = ModelTemplateFactory.class.getClassLoader().getResourceAsStream("demo/gatheringbill/selector_GatheringBill.xml");// 从classpath目录读取,以/开头反而读取不到,
			FormTemplate fields = (FormTemplate)u.unmarshal(in);
			
			Marshaller m = context.createMarshaller();
			m.marshal(fields, System.out);
		}
		 */
	}

	private static void valueTest1() throws Exception {
		ValueBytes bytes = ValueBytes.get(new byte[] { 1, 2, 3, 4 });
		System.out.println(bytes);
		ValueDate date1 = ValueDate.get(new Date());
		System.out.println(date1);
		System.out.println(date1.getTimestamp());
		//		ValueDate date3 = ValueDate.parse("2016-12-15");
		//		System.out.println(date3);

		ValueTime time1 = ValueTime.get(new Date());
		System.out.println(time1);
		//		ValueTime time2 = ValueTime.parse("10:48:35");
		//		System.out.println(time2);
		ValueTimestamp timestamp = ValueTimestamp.get(new Date());
		System.out.println(timestamp);
		System.out.println("--" + timestamp.getDate() + ":" + timestamp.getDate().getMinutes());
		System.out.println(timestamp.getTimestamp());
		ValueTimestamp timestamp2 = ValueTimestamp.parse("2016-12-15 10:48:35");
		System.out.println(timestamp2);

		ValueDecimal decimal = ValueDecimal.get(new BigDecimal("3.1415926"));
		System.out.println(decimal);
		ValueDouble d = ValueDouble.get(3.1415926);
		System.out.println(d);
		ValueFloat f = ValueFloat.get(3.1415926F);
		System.out.println(f);
		ValueInt valueInt = ValueInt.get(3);
		System.out.println(valueInt);
		ValueLong valueLong = ValueLong.get(5555);
		System.out.println(valueLong);
		ValueNull valueNull = ValueNull.INSTANCE;
		System.out.println(valueNull);
		ValueShort valueShort = ValueShort.get((short) 55);
		System.out.println(valueShort);
		ValueString valueString = ValueString.get("test by me");
		System.out.println(valueString);
	}

	private static void testResource() {
		URL url = DatasourceFactory.class.getClassLoader().getResource("");
		System.out.println(url.getPath());
		System.out.println(url.getFile());
		File file = new File(url.getPath());
		System.out.println(file.getPath());
		System.out.println(file.getAbsolutePath());
		System.out.println(file.getName());
	}

	private static void testPoolFields() throws Exception {
		DatasourceFactory factory = new DatasourceFactory();
		Fields fields = factory.getPoolFields();
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(fields, System.out);
	}

	private static void testBusinessPoolFields() throws Exception {
		DatasourceFactory factory = new DatasourceFactory();
		Fields fields = factory.getBusinessPoolFields();
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(fields, System.out);
	}

	//	getDatasourceInfoLi
	private static void testGetDatasourceInfoLi() throws Exception {
		DatasourceFactory factory = new DatasourceFactory();
		List<DatasourceInfo> list = factory.getDatasourceInfoLi();
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getPath());
			m.marshal(list.get(i).getDatasource(), System.out);
		}
		//		for (DatasourceInfo datasourceInfo: list) {
		//			m.marshal(datasourceInfo.getDatasource(), System.out);
		//		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		//		unMarshalTest();
		unMarshalTest2();
		//		valueTest1();
		//		testPoolFields();
		//		testBusinessPoolFields();
		//		testGetDatasourceInfoLi();
	}

}
