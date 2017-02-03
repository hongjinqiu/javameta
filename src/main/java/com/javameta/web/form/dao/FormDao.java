package com.javameta.web.form.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.javameta.model.BusinessDataType;
import com.javameta.model.ValueBusinessObject;
import com.javameta.model.datasource.Datasource;
import com.javameta.model.datasource.DetailData;
import com.javameta.model.datasource.Field;
import com.javameta.model.iterate.DatasourceIterator;
import com.javameta.model.iterate.IDatasourceLineDataIterate;
import com.javameta.util.New;
import com.javameta.value.Value;
import com.javameta.value.ValueInt;
import com.javameta.value.ValueNull;
import com.javameta.web.support.DaoSupport;

@Scope("prototype")
@Component
public class FormDao extends DaoSupport {
	public void insert(final Datasource datasource, final ValueBusinessObject valueBo) {
		DatasourceIterator.iterateLineValueBo(datasource, valueBo, new IDatasourceLineDataIterate() {
			@Override
			public void iterate(List<Field> fieldLi, Map<String, Value> data, int rowIndex) {
				insert(datasource, fieldLi, valueBo, data);
			}
		});
//		insertMaster(datasource, valueBo);
//		insertDetail(datasource, valueBo);
	}
	
	/*private int insertMaster(Datasource datasource, ValueBusinessObject valueBo) {
		String tableName = datasource.getCalcTableName();
		String insertSql = "insert into {tableName}({keyLi}) values ({valueLi})";
		insertSql = insertSql.replace("{tableName}", tableName);
		final List<String> keyLi = New.arrayList();
		final List<String> valueLi = New.arrayList();
		final Map<String, Object> valueDict = New.hashMap();
		DatasourceIterator.iterateLineValueBo(datasource, valueBo, new IDatasourceLineDataIterate() {
			@Override
			public void iterate(List<Field> fieldLi, Map<String, Value> data, int rowIndex) {
				if (fieldLi.get(0).isMasterField()) {
					for (Field field: fieldLi) {
						String fieldName = field.getCalcFieldName();
						keyLi.add(fieldName);
						valueLi.add(":" + field.getId());
						
						if (data.get(field.getId()) != null) {
							valueDict.put(field.getId(), data.get(field.getId()));
						} else {
							valueDict.put(field.getId(), null);
						}
					}
				}
			}
		});
		
		// 主键放0,自动递增
		valueDict.put(datasource.getMasterData().getFixField().getPrimaryKey().getId(), 0);
		insertSql = insertSql.replace("{keyLi}", StringUtils.join(keyLi.toArray(), ","));
		insertSql = insertSql.replace("{valueLi}", StringUtils.join(valueLi.toArray(), ","));
		this.getNamedParameterJdbcTemplate().update(insertSql, valueDict);
		int id = this.getNamedParameterJdbcTemplate().queryForInt("select LAST_INSERT_ID()", new HashMap<String, Object>());
		valueBo.getMasterData().put("id", ValueInt.get(id));
		return id;
	}*/
	
	/*private void insertDetail(final Datasource datasource, final ValueBusinessObject valueBo) {
		DatasourceIterator.iterateLineValueBo(datasource, valueBo, new IDatasourceLineDataIterate() {
			@Override
			public void iterate(List<Field> fieldLi, Map<String, Value> data, int rowIndex) {
				if (!fieldLi.get(0).isMasterField()) {
					String dataSetId = fieldLi.get(0).getDataSetId();
					String tableName = datasource.getCalcDetailTableName(dataSetId);
					String insertSql = "insert into {tableName}({keyLi}) values ({valueLi})";
					insertSql = insertSql.replace("{tableName}", tableName);
					final List<String> keyLi = New.arrayList();
					final List<String> valueLi = New.arrayList();
					final Map<String, Object> valueDict = New.hashMap();

					DetailData detailData = datasource.getDetailDataByDataSetId(dataSetId);
					for (Field field: fieldLi) {
						String fieldName = field.getCalcFieldName();
						keyLi.add(fieldName);
						valueLi.add(":" + field.getId());
						
						if (field.getId().equals(detailData.getParentFieldId())) {
							valueDict.put(field.getId(), valueBo.getMasterData().get("id").getInt());
						} else {
							if (data.get(field.getId()) != null) {
								valueDict.put(field.getId(), data.get(field.getId()));
							} else {
								valueDict.put(field.getId(), null);
							}
						}
					}
					
					// 主键放0,自动递增
					valueDict.put(detailData.getFixField().getPrimaryKey().getId(), 0);
					insertSql = insertSql.replace("{keyLi}", StringUtils.join(keyLi.toArray(), ","));
					insertSql = insertSql.replace("{valueLi}", StringUtils.join(valueLi.toArray(), ","));
					getNamedParameterJdbcTemplate().update(insertSql, valueDict);
					int id = getNamedParameterJdbcTemplate().queryForInt("select LAST_INSERT_ID()", new HashMap<String, Object>());
					data.put("id", ValueInt.get(id));
				}
			}
		});
		
	}*/
	
	public int insert(Datasource datasource, List<Field> fieldLi, ValueBusinessObject valueBo, Map<String, Value> data) {
		String dataSetId = fieldLi.get(0).getDataSetId();
		DetailData detailData = null;
		String tableName = null;
		if (fieldLi.get(0).isMasterField()) {
			tableName = datasource.getCalcTableName();
		} else {
			detailData = datasource.getDetailDataByDataSetId(dataSetId);
			tableName = datasource.getCalcDetailTableName(dataSetId);
		}
		String insertSql = "insert into {tableName}({keyLi}) values ({valueLi})";
		insertSql = insertSql.replace("{tableName}", tableName);
		final List<String> keyLi = New.arrayList();
		final List<String> valueLi = New.arrayList();
		final Map<String, Object> valueDict = New.hashMap();
		
		for (Field field: fieldLi) {
			
			if (detailData != null && field.getId().equals(detailData.getParentFieldId())) {
				String fieldName = field.getCalcFieldName();
				keyLi.add(fieldName);
				valueLi.add(":" + field.getId());
				valueDict.put(field.getId(), valueBo.getMasterData().get("id").getInt());
			} else {
				if (data.get(field.getId()) != null && !data.get(field.getId()).equals(ValueNull.INSTANCE)) {
					String fieldName = field.getCalcFieldName();
					keyLi.add(fieldName);
					valueLi.add(":" + field.getId());
					valueDict.put(field.getId(), data.get(field.getId()).getObject());
				} else {
					/*	放null,jdbc template会报异常,不知道是不是mysql的问题,但是也可以插入,但是控制台不想出现错误信息,因此,null的就暂时不赋值,直接写到sql里面去,
					[org.springframework.jdbc.core.StatementCreatorUtils]Setting SQL statement parameter value: column index 6, parameter value [null], value class [null], SQL type unknown
					[org.springframework.jdbc.core.StatementCreatorUtils]Could not check database or driver name
					java.sql.SQLException: Parameter metadata not available for the given statement
					 */
					String fieldName = field.getCalcFieldName();
					keyLi.add(fieldName);
					valueLi.add("null");
//					valueLi.add(":" + field.getId());
//					valueDict.put(field.getId(), null);
				}
			}
		}
		
		// 主键放0,自动递增
		if (detailData == null) {
			valueDict.put(datasource.getMasterData().getFixField().getPrimaryKey().getId(), 0);
		} else {
			valueDict.put(detailData.getFixField().getPrimaryKey().getId(), 0);
		}
		insertSql = insertSql.replace("{keyLi}", StringUtils.join(keyLi.toArray(), ","));
		insertSql = insertSql.replace("{valueLi}", StringUtils.join(valueLi.toArray(), ","));
		getNamedParameterJdbcTemplate().update(insertSql, valueDict);
		int id = getNamedParameterJdbcTemplate().queryForInt("select LAST_INSERT_ID()", new HashMap<String, Object>());
		data.put("id", ValueInt.get(id));
		return id;
	}
	
	public void update(Datasource datasource, List<Field> fieldLi, ValueBusinessObject valueBo, Map<String, Value> data) {
		String dataSetId = fieldLi.get(0).getDataSetId();
		DetailData detailData = null;
		String idFieldName = null;
		String tableName = null;
		if (fieldLi.get(0).isMasterField()) {
			tableName = datasource.getCalcTableName();
			idFieldName = datasource.getMasterData().getFixField().getPrimaryKey().getCalcFieldName();
		} else {
			detailData = datasource.getDetailDataByDataSetId(dataSetId);
			tableName = datasource.getCalcDetailTableName(dataSetId);
			idFieldName = datasource.getDetailDataByDataSetId(fieldLi.get(0).getDataSetId()).getFixField().getPrimaryKey().getCalcFieldName();
		}
		String updateSql = "update {tableName} set {keyValueLi} where 1=1 and {idFieldName}=:{idFieldName}";
		updateSql = updateSql.replace("{tableName}", tableName);
		updateSql = updateSql.replace("{idFieldName}", idFieldName);
		final List<String> keyValueLi = New.arrayList();
		final Map<String, Object> valueDict = New.hashMap();
		valueDict.put(idFieldName, data.get("id").getObject());
		
		for (Field field: fieldLi) {
			
			if (detailData != null && field.getId().equals(detailData.getParentFieldId())) {
				String keyValue = "{fieldName}=:{fieldName}";
				String fieldName = field.getCalcFieldName();
				keyValue = keyValue.replace("{fieldName}", fieldName);
				keyValueLi.add(keyValue);
				valueDict.put(fieldName, valueBo.getMasterData().get("id").getInt());
			} else {
				if (data.get(field.getId()) != null && !data.get(field.getId()).equals(ValueNull.INSTANCE)) {
					String keyValue = "{fieldName}=:{fieldName}";
					String fieldName = field.getCalcFieldName();
					keyValue = keyValue.replace("{fieldName}", fieldName);
					keyValueLi.add(keyValue);
					valueDict.put(fieldName, data.get(field.getId()).getObject());
				} else {
					/*	放null,jdbc template会报异常,不知道是不是mysql的问题,但是也可以插入,但是控制台不想出现错误信息,因此,null的就暂时不赋值,直接写到sql里面去,
					[org.springframework.jdbc.core.StatementCreatorUtils]Setting SQL statement parameter value: column index 6, parameter value [null], value class [null], SQL type unknown
					[org.springframework.jdbc.core.StatementCreatorUtils]Could not check database or driver name
					java.sql.SQLException: Parameter metadata not available for the given statement
					 */
					String keyValue = "{fieldName}=null";
					String fieldName = field.getCalcFieldName();
					keyValue = keyValue.replace("{fieldName}", fieldName);
					keyValueLi.add(keyValue);
//					valueDict.put(fieldName, null);
				}
			}
		}

		updateSql = updateSql.replace("{keyValueLi}", StringUtils.join(keyValueLi.toArray(), ","));
		getNamedParameterJdbcTemplate().update(updateSql, valueDict);
	}
	
	public void delete(Datasource datasource, List<Field> fieldLi, ValueBusinessObject srcValueBo, Map<String, Value> srcData) {
		String deleteSql = "delete from {tableName} where 1=1 and {idFieldName}=:{idFieldName}";
		String idFieldName = null;
		String tableName = null;
		if (fieldLi.get(0).isMasterField()) {
			tableName = datasource.getCalcTableName();
			idFieldName = datasource.getMasterData().getFixField().getPrimaryKey().getCalcFieldName();
		} else {
			tableName = datasource.getCalcDetailTableName(fieldLi.get(0).getDataSetId());
			idFieldName = datasource.getDetailDataByDataSetId(fieldLi.get(0).getDataSetId()).getFixField().getPrimaryKey().getCalcFieldName();
		}
		deleteSql = deleteSql.replace("{tableName}", tableName);
		deleteSql = deleteSql.replace("{idFieldName}", idFieldName);
		Map<String, Object> param = New.hashMap();
		param.put(idFieldName, srcData.get("id").getObject());
		this.getNamedParameterJdbcTemplate().update(deleteSql, param);
	}
	
	public void delete(Datasource datasource, ValueBusinessObject valueBo) {
		Object id = valueBo.getMasterData().get("id").getObject();
		
		String idField = datasource.getMasterData().getFixField().getPrimaryKey().getCalcFieldName();
		String sql = "delete from {tableName} where 1=1 and {idField}=?";
		sql = sql.replace("{tableName}", datasource.getCalcTableName());
		sql = sql.replace("{idField}", idField);
		this.getJdbcTemplate().update(sql, id);
		
		for (DetailData detailData : datasource.getDetailData()) {
			String detailSql = "delete from {tableName} where 1=1 and {parentFieldId}=?";
			detailSql = detailSql.replace("{tableName}", datasource.getCalcDetailTableName(detailData.getId()));
			detailSql = detailSql.replace("{parentFieldId}", detailData.getParentFieldId());
			this.getJdbcTemplate().update(detailSql, id);
		}
	}

	public ValueBusinessObject getValueBoFromDb(Datasource datasource, Map<String, Object> param) {
		Map<String, Object> bo = getBoFromDb(datasource, param);
		return BusinessDataType.convertMapToValueBusinessObject(datasource, bo);
	}

	public Map<String, Object> getBoFromDb(Datasource datasource, Map<String, Object> param) {
		Map<String, Object> result = New.hashMap();
		Map<String, String> query = New.hashMap();
		query.put("id", ObjectUtils.toString(param.get("id")));
		String idField = datasource.getMasterData().getFixField().getPrimaryKey().getCalcFieldName();
		String sql = "select * from {tableName} where 1=1 and {idField}=?";
		sql = sql.replace("{tableName}", datasource.getCalcTableName());
		sql = sql.replace("{idField}", idField);
		Map<String, Object> mainData = this.getJdbcTemplate().queryForMap(sql, param.get("id"));
		mainData.put("id", mainData.get(idField));
		result.put(datasource.getMasterData().getId(), mainData);

		for (DetailData detailData : datasource.getDetailData()) {
			String detailSql = "select * from {tableName} where 1=1 and {parentFieldId}=?";
			detailSql = detailSql.replace("{tableName}", datasource.getCalcDetailTableName(detailData.getId()));
			detailSql = detailSql.replace("{parentFieldId}", detailData.getParentFieldId());
			String detailIdField = detailData.getFixField().getPrimaryKey().getCalcFieldName();
			List<Map<String, Object>> items = this.getJdbcTemplate().queryForList(detailSql, param.get("id"));
			for (Map<String, Object> item: items) {
				item.put("id", item.get(detailIdField));
			}
			result.put(detailData.getId(), items);
		}
		return result;
	}
}
