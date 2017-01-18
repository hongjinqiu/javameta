package com.javameta.model.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javameta.JavametaException;
import com.javameta.model.DatasourceFactory;
import com.javameta.model.FormTemplateDao;
import com.javameta.model.ValueBusinessObject;
import com.javameta.model.datasource.Datasource;
import com.javameta.model.datasource.DetailData;
import com.javameta.model.datasource.Field;
import com.javameta.model.datasource.Field.RelationDS.RelationItem;
import com.javameta.model.iterate.DatasourceIterator;
import com.javameta.model.iterate.IDatasourceFieldDataIterate;
import com.javameta.util.New;
import com.javameta.value.Value;

@Scope("prototype")
@Service
@Transactional
public class UsedCheck {
	@Autowired
	private FormTemplateDao formTemplateDao;
	
	/*
<field id="" extends="ref_datasource_id">
<field id="" extends="ref_dataset_id">
<field id="" extends="ref_field_id">
<field id="" extends="ref_field_id_value">

<field id="" extends="be_ref_datasource_id">
<field id="" extends="be_ref_dataset_id">
<field id="" extends="be_ref_field_id">
<field id="" extends="be_ref_field_id_value">
	 */
	/**
	 * 检查被用
	 * @param datasource
	 * @param bo
	 * @return
	 */
	public boolean checkUsed(Datasource datasource, Map<String, Object> bo) {
		Map<String, Object> masterData = (Map<String, Object>)bo.get(datasource.getMasterData().getId());
		Map<String, Object> beReferenceQuery = New.hashMap();

		String id = datasource.getMasterData().getFixField().getPrimaryKey().getId();
		beReferenceQuery.put("be_ref_datasource_id", datasource.getId());
		beReferenceQuery.put("be_ref_dataset_id", datasource.getMasterData().getId());
		beReferenceQuery.put("be_ref_field_id", id);
		beReferenceQuery.put("be_ref_field_id_value", masterData.get(id));

		StringBuilder sb = new StringBuilder();
		sb.append(" select count(1) from PUB_REFERENCE_LOG ");
		sb.append(" where 1=1 ");
		sb.append(" and be_ref_datasource_id=:be_ref_datasource_id ");
		sb.append(" and be_ref_dataset_id=:be_ref_dataset_id ");
		sb.append(" and be_ref_field_id=:be_ref_field_id ");
		sb.append(" and be_ref_field_id_value=:be_ref_field_id_value ");
		sb.append(" limit 1 ");
		
		int count = this.formTemplateDao.getNamedParameterJdbcTemplate().queryForInt(sb.toString(), beReferenceQuery);
		
		return count > 0;
	}
	
	private String getDataSetFieldId(Datasource datasource, Field field) {
		if (datasource.getMasterData().getId().equals(field.getDataSetId())) {
			return datasource.getMasterData().getFixField().getPrimaryKey().getId();
		}
		for (DetailData detailData: datasource.getDetailData()) {
			if (detailData.getId().equals(field.getDataSetId())) {
				return detailData.getFixField().getPrimaryKey().getId();
			}
		}
		return null;
	}

	/**
	 * 检查分录某一条记录是否被用
	 * @param datasource
	 * @param bo
	 * @param diffDataRow
	 * @return
	 */
	public boolean checkDeleteDetailRecordUsed(Datasource datasource, ValueBusinessObject bo, DiffDataRow diffDataRow) {
		List<Field> fieldLi = diffDataRow.getFieldLi();
		Map<String, Value> destData = diffDataRow.getDestData();
		Map<String, Value> srcData = diffDataRow.getSrcData();
		String primaryKeyFieldId = getDataSetFieldId(datasource, fieldLi.get(0));
		if (destData == null && srcData != null) {// 删除的分录
			if (fieldLi.get(0).isMasterField()) {
				Map<String, Object> beReferenceQuery = New.hashMap();
				beReferenceQuery.put("be_ref_datasource_id", datasource.getId());
				beReferenceQuery.put("be_ref_dataset_id", fieldLi.get(0).getDataSetId());
				beReferenceQuery.put("be_ref_field_id", primaryKeyFieldId);
				beReferenceQuery.put("be_ref_field_id_value", srcData.get(primaryKeyFieldId).getObject());
				
				StringBuilder sb = new StringBuilder();
				sb.append(" select count(1) from PUB_REFERENCE_LOG ");
				sb.append(" where 1=1 ");
				sb.append(" and be_ref_datasource_id=:be_ref_datasource_id ");
				sb.append(" and be_ref_dataset_id=:be_ref_dataset_id ");
				sb.append(" and be_ref_field_id=:be_ref_field_id ");
				sb.append(" and be_ref_field_id_value=:be_ref_field_id_value ");
				sb.append(" limit 1 ");
				
				int count = this.formTemplateDao.getNamedParameterJdbcTemplate().queryForInt(sb.toString(), beReferenceQuery);
				
				return count > 0;
			} else {
				Map<String, Object> beReferenceQuery = New.hashMap();
				beReferenceQuery.put("be_ref_datasource_id", datasource.getId());
				beReferenceQuery.put("be_ref_dataset_id_1", fieldLi.get(0).getDataSetId());
				beReferenceQuery.put("be_ref_field_id_1", primaryKeyFieldId);
				beReferenceQuery.put("be_ref_field_id_value_1", srcData.get(primaryKeyFieldId).getObject());
				
				StringBuilder sb = new StringBuilder();
				sb.append(" select count(1) from PUB_REFERENCE_LOG ");
				sb.append(" where 1=1 ");
				sb.append(" and be_ref_datasource_id=:be_ref_datasource_id ");
				sb.append(" and be_ref_dataset_id_1=:be_ref_dataset_id_1 ");
				sb.append(" and be_ref_field_id_1=:be_ref_field_id_1 ");
				sb.append(" and be_ref_field_id_value_1=:be_ref_field_id_value_1 ");
				sb.append(" limit 1 ");
				
				int count = this.formTemplateDao.getNamedParameterJdbcTemplate().queryForInt(sb.toString(), beReferenceQuery);
				
				return count > 0;
			}
		}
		return false;
	}
	
	/**
	 * 保存时记被用
	 * @param fieldLi
	 * @param bo
	 * @param data
	 */
	public void insert(List<Field> fieldLi, ValueBusinessObject bo, Map<String, Value> data) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String createTime = format.format(new Date());
		DatasourceFactory datasourceFactory = new DatasourceFactory();
		Datasource datasource = datasourceFactory.getDatasource(fieldLi.get(0).getDatasourceId());
		for (Field field: fieldLi) {
			if (field.isRelationField()) {
				RelationItem relationItem = datasourceFactory.parseRelationExpr(field, bo, data);
				if (relationItem == null) {
					throw new JavametaException("数据源:" + field.getDatasourceId() + ",数据集:" + field.getDataSetId() + ",字段:" + field.getId() + ",配置的关联模型列表,不存在返回true的记录");
				}
				Map<String, Object> referenceData = New.hashMap();
				referenceData.put("createBy", data.get("createBy").getObject());
				referenceData.put("createTime", createTime);
				referenceData.put("createUnit", data.get("createUnit").getObject());

				Map<String, Object> referenceMap = getSourceReferenceLi(field, bo, data);
				referenceData.putAll(referenceMap);
				
				Map<String, Object> beReferenceMap = getBeReferenceLi(field, relationItem, data);
				referenceData.putAll(beReferenceMap);
				
				this.formTemplateDao.insert(datasource, datasource.getMasterData(), referenceData);
			}
		}
	}

	public void update(List<Field> fieldLi, ValueBusinessObject bo, Map<String, Value> destData, Map<String, Value> srcData) {
		if (destData != null && srcData == null) {
			insert(fieldLi, bo, destData);
		} else if (destData == null && srcData != null) {
			delete(fieldLi, srcData);
		} else if (destData != null && srcData != null) {
			DatasourceFactory datasourceFactory = new DatasourceFactory();
			// 分析字段,如果字段都相等,不过帐,
			if (datasourceFactory.isDataDifferent(fieldLi, destData, srcData)) {
				delete(fieldLi, srcData);
				insert(fieldLi, bo, destData);
			}
		}
	}

	/**
	 * 如果是主数据集,不会连分录一起删掉
	 * 主数据集字段不能直接用主数据集["ds", "A", "id", id]来直接删除,
	 * 因为会连同分录的一起被删,而分录差异行数据做了different的判断,未修改的分录不会再补上被用记录,就漏掉了
	 * @param fieldLi
	 * @param data
	 */
	public void delete(List<Field> fieldLi, Map<String, Value> data) {
		if (fieldLi.get(0).isMasterField()) {
			for (Field field: fieldLi) {
				if (field.isRelationField()) {
					String srcDatasourceId = field.getDatasourceId();
					String srcDataSetId = field.getDataSetId();
					String srcFieldName = field.getId();
					int fieldValue = data.get(srcFieldName).getInt();
					
					StringBuilder sb = new StringBuilder();
					sb.append(" delete from PUB_REFERENCE_LOG ");
					sb.append(" where 1=1 ");
					sb.append(" and ref_datasource_id=:ref_datasource_id ");
					sb.append(" and ref_dataset_id_1=:ref_dataset_id_1 ");
					sb.append(" and ref_field_id_1=:ref_field_id_1 ");
					sb.append(" and ref_field_id_value_1=:ref_field_id_value_1 ");
					
					Map<String, Object> paramMap = New.hashMap();
					paramMap.put("ref_datasource_id", srcDatasourceId);
					paramMap.put("ref_dataset_id_1", srcDataSetId);
					paramMap.put("ref_field_id_1", srcFieldName);
					paramMap.put("ref_field_id_value_1", fieldValue);
					
					this.formTemplateDao.getNamedParameterJdbcTemplate().update(sb.toString(), paramMap);
				}
			}
		} else {
			String srcDatasourceId = fieldLi.get(0).getDatasourceId();
			String srcDataSetId = fieldLi.get(0).getDataSetId();
			String srcFieldName = "id";
			int fieldValue = data.get(srcFieldName).getInt();
			
			StringBuilder sb = new StringBuilder();
			sb.append(" delete from PUB_REFERENCE_LOG ");
			sb.append(" where 1=1 ");
			sb.append(" and ref_datasource_id=:ref_datasource_id ");
			sb.append(" and ref_dataset_id_1=:ref_dataset_id_1 ");
			sb.append(" and ref_field_id_1=:ref_field_id_1 ");
			sb.append(" and ref_field_id_value_1=:ref_field_id_value_1 ");
			
			Map<String, Object> paramMap = New.hashMap();
			paramMap.put("ref_datasource_id", srcDatasourceId);
			paramMap.put("ref_dataset_id_1", srcDataSetId);
			paramMap.put("ref_field_id_1", srcFieldName);
			paramMap.put("ref_field_id_value_1", fieldValue);
			
			this.formTemplateDao.getNamedParameterJdbcTemplate().update(sb.toString(), paramMap);
		}
	}

	/**
	 * 如果是主数据集,则会连同分录一起删除
	 * @param fieldLi
	 * @param data
	 */
	public void deleteAll(List<Field> fieldLi, Map<String, Value> data) {
		if (fieldLi.get(0).isMasterField()) {
			String srcDatasourceId = fieldLi.get(0).getDatasourceId();
			String srcDataSetId = fieldLi.get(0).getDataSetId();
			String srcFieldName = "id";
			int fieldValue = data.get(srcFieldName).getInt();
			
			StringBuilder sb = new StringBuilder();
			sb.append(" delete from PUB_REFERENCE_LOG ");
			sb.append(" where 1=1 ");
			sb.append(" and ref_datasource_id=:ref_datasource_id ");
			sb.append(" and ref_dataset_id=:ref_dataset_id ");
			sb.append(" and ref_field_id=:ref_field_id ");
			sb.append(" and ref_field_id_value=:ref_field_id_value ");
			
			Map<String, Object> paramMap = New.hashMap();
			paramMap.put("ref_datasource_id", srcDatasourceId);
			paramMap.put("ref_dataset_id", srcDataSetId);
			paramMap.put("ref_field_id", srcFieldName);
			paramMap.put("ref_field_id_value", fieldValue);
			
			this.formTemplateDao.getNamedParameterJdbcTemplate().update(sb.toString(), paramMap);
		} else {
			String srcDatasourceId = fieldLi.get(0).getDatasourceId();
			String srcDataSetId = fieldLi.get(0).getDataSetId();
			String srcFieldName = "id";
			int fieldValue = data.get(srcFieldName).getInt();
			
			StringBuilder sb = new StringBuilder();
			sb.append(" delete from PUB_REFERENCE_LOG ");
			sb.append(" where 1=1 ");
			sb.append(" and ref_datasource_id=:ref_datasource_id ");
			sb.append(" and ref_dataset_id_1=:ref_dataset_id_1 ");
			sb.append(" and ref_field_id_1=:ref_field_id_1 ");
			sb.append(" and ref_field_id_value_1=:ref_field_id_value_1 ");
			
			Map<String, Object> paramMap = New.hashMap();
			paramMap.put("ref_datasource_id", srcDatasourceId);
			paramMap.put("ref_dataset_id_1", srcDataSetId);
			paramMap.put("ref_field_id_1", srcFieldName);
			paramMap.put("ref_field_id_value_1", fieldValue);
			
			this.formTemplateDao.getNamedParameterJdbcTemplate().update(sb.toString(), paramMap);
		}
	}
	
//	func (o UsedCheck) GetFormUsedCheck(sessionId int, dataSource DataSource, bo map[string]interface{}) map[string]interface{} {
	
	
	/**
	 * 引用方需要写多个字段,某个字段引用时,也保存这个字段所在数据源主数据集引用,这个字段所在分录数据集引用,
		`ref_datasource_id` varchar(100) DEFAULT NULL COMMENT '关联方数据源ID',
		`ref_dataset_id` varchar(50) DEFAULT NULL COMMENT '关联方主数据集ID',
		`ref_field_id` varchar(50) DEFAULT NULL COMMENT '关联方主数据集主键ID',
		`ref_field_id_value` int(11) DEFAULT NULL COMMENT '关联方主数据集主键ID值',
		`ref_dataset_id_1` varchar(50) DEFAULT NULL COMMENT '关联方关联字段所在数据集ID',
		`ref_field_id_1` varchar(50) DEFAULT NULL COMMENT '关联方关联字段所在数据集主键ID',
		`ref_field_id_value_1` int(11) DEFAULT NULL COMMENT '关联方关联字段所在数据集主键ID值',
		`ref_dataset_id_2` varchar(50) DEFAULT NULL COMMENT '关联方关联字段所在数据集ID',
		`ref_field_id_2` varchar(50) DEFAULT NULL COMMENT '关联方关联字段ID',
		`ref_field_id_value_2` int(11) DEFAULT NULL COMMENT '关联方关联字段ID值',
	 * @return
	 */
	public Map<String, Object> getSourceReferenceLi(Field field, ValueBusinessObject bo, Map<String, Value> data) {
		Map<String, Value> masterData = bo.getMasterData();
		Map<String, Object> sourceRefMap = New.hashMap();
		
		String srcDatasourceId = field.getDatasourceId();
		String srcDataSetId = "A";
		String srcFieldName = "id";
		int id = masterData.get(srcFieldName).getInt();
		
		sourceRefMap.put("ref_datasource_id", srcDatasourceId);
		sourceRefMap.put("ref_dataset_id", srcDataSetId);
		sourceRefMap.put("ref_field_id", srcFieldName);
		sourceRefMap.put("ref_field_id_value", id);
		
		if (field.isMasterField()) {
			srcDataSetId = field.getDataSetId();
			srcFieldName = field.getId();
			id = masterData.get(srcFieldName).getInt();
			
			sourceRefMap.put("ref_dataset_id_1", srcDataSetId);
			sourceRefMap.put("ref_field_id_1", srcFieldName);
			sourceRefMap.put("ref_field_id_value_1", id);
		} else {
			srcDataSetId = field.getDataSetId();
			srcFieldName = "id";
			id = data.get(srcFieldName).getInt();
			
			sourceRefMap.put("ref_dataset_id_1", srcDataSetId);
			sourceRefMap.put("ref_field_id_1", srcFieldName);
			sourceRefMap.put("ref_field_id_value_1", id);
			
			srcDataSetId = field.getDataSetId();
			srcFieldName = field.getId();
			id = data.get(srcFieldName).getInt();
			
			sourceRefMap.put("ref_dataset_id_2", srcDataSetId);
			sourceRefMap.put("ref_field_id_2", srcFieldName);
			sourceRefMap.put("ref_field_id_value_2", id);
		}
		
		return sourceRefMap;
	}

	/**
	 * 
	 * @param datasource
	 * @param bo
	 * @return {
	 * 	"A": {
	 * 		1: true
	 * 	},
	 * 	"B": {
	 * 		1: true,
	 * 		2: false,
	 * 		3: true
	 * 	}
	 * }
	 */
	public Map<String, Object> getFormUsedCheck(final Datasource datasource, ValueBusinessObject bo) {
		final Map<String, Object> result = New.hashMap();
		
		DatasourceIterator.iterateFieldValueBo(datasource, bo, new IDatasourceFieldDataIterate() {
			@Override
			public void iterate(Field field, Map<String, Value> data, int rowIndex) {
				if (field.getId().equals("id")) {
					StringBuilder sb = new StringBuilder();
					Map<String, Object> referenceQuery = New.hashMap();
					if (field.isMasterField()) {
						referenceQuery.put("be_ref_datasource_id", datasource.getId());
						referenceQuery.put("be_ref_dataset_id", field.getDataSetId());
						referenceQuery.put("be_ref_field_id", field.getId());
						referenceQuery.put("be_ref_field_id_value", data.get(field.getId()).getInt());
						
						sb.append(" select count(1) from PUB_REFERENCE_LOG ");
						sb.append(" where 1=1 ");
						sb.append(" and be_ref_datasource_id=:be_ref_datasource_id ");
						sb.append(" and be_ref_dataset_id=:be_ref_dataset_id ");
						sb.append(" and be_ref_field_id=:be_ref_field_id ");
						sb.append(" and be_ref_field_id_value=:be_ref_field_id_value ");
						sb.append(" limit 1 ");
					} else {
						referenceQuery.put("be_ref_datasource_id", datasource.getId());
						referenceQuery.put("be_ref_dataset_id_1", field.getDataSetId());
						referenceQuery.put("be_ref_field_id_1", field.getId());
						referenceQuery.put("be_ref_field_id_value_1", data.get(field.getId()).getInt());
						
						sb.append(" select count(1) from PUB_REFERENCE_LOG ");
						sb.append(" where 1=1 ");
						sb.append(" and be_ref_datasource_id=:be_ref_datasource_id ");
						sb.append(" and be_ref_dataset_id_1=:be_ref_dataset_id_1 ");
						sb.append(" and be_ref_field_id_1=:be_ref_field_id_1 ");
						sb.append(" and be_ref_field_id_value_1=:be_ref_field_id_value_1 ");
						sb.append(" limit 1 ");
					}
					int count = getFormTemplateDao().getNamedParameterJdbcTemplate().queryForInt(sb.toString(), referenceQuery);
					boolean isUsed = count > 0;
					if (result.get(field.getDataSetId()) == null) {
						result.put(field.getDataSetId(), new HashMap<String, Object>());
					}
					Map<String, Object> dataSetUsedMap = (Map<String, Object>)result.get(field.getDataSetId());
					dataSetUsedMap.put(data.get(field.getId()).getString(), isUsed);
				}
			}
		});
		
		return result;
	}
	
	/**
	 * 
	 * @param datasource
	 * @param items
	 * @param dataSetId
	 * @return {
	 * 	"A": {
	 * 		1: true
	 * 	},
	 * 	"B": {
	 * 		1: true,
	 * 		2: false,
	 * 		3: true
	 * 	}
	 * }
	 */
	public Map<String, Object> getListUsedCheck(Datasource datasource, List<Map<String, Object>> items, String dataSetId) {
		Map<String, Object> result = New.hashMap();
		
		for (Map<String, Object> item: items) {
			StringBuilder sb = new StringBuilder();
			Map<String, Object> referenceQuery = New.hashMap();
			if (dataSetId.equals("A")) {
				referenceQuery.put("be_ref_datasource_id", datasource.getId());
				referenceQuery.put("be_ref_dataset_id", dataSetId);
				referenceQuery.put("be_ref_field_id", "id");
				referenceQuery.put("be_ref_field_id_value", item.get("id"));
				
				sb.append(" select count(1) from PUB_REFERENCE_LOG ");
				sb.append(" where 1=1 ");
				sb.append(" and be_ref_datasource_id=:be_ref_datasource_id ");
				sb.append(" and be_ref_dataset_id=:be_ref_dataset_id ");
				sb.append(" and be_ref_field_id=:be_ref_field_id ");
				sb.append(" and be_ref_field_id_value=:be_ref_field_id_value ");
				sb.append(" limit 1 ");
			} else {
				referenceQuery.put("be_ref_datasource_id", datasource.getId());
				referenceQuery.put("be_ref_dataset_id_1", dataSetId);
				referenceQuery.put("be_ref_field_id_1", "id");
				referenceQuery.put("be_ref_field_id_value_1", item.get("id"));
				
				sb.append(" select count(1) from PUB_REFERENCE_LOG ");
				sb.append(" where 1=1 ");
				sb.append(" and be_ref_datasource_id=:be_ref_datasource_id ");
				sb.append(" and be_ref_dataset_id_1=:be_ref_dataset_id_1 ");
				sb.append(" and be_ref_field_id_1=:be_ref_field_id_1 ");
				sb.append(" and be_ref_field_id_value_1=:be_ref_field_id_value_1 ");
				sb.append(" limit 1 ");
			}
			int count = getFormTemplateDao().getNamedParameterJdbcTemplate().queryForInt(sb.toString(), referenceQuery);
			boolean isUsed = count > 0;
			if (result.get(dataSetId) == null) {
				result.put(dataSetId, new HashMap<String, Object>());
			}
			Map<String, Object> dataSetUsedMap = (Map<String, Object>)result.get(dataSetId);
			dataSetUsedMap.put(item.get("id").toString(), isUsed);
		}
		
		return result;
	}
	
	/**
	 * 被引用方需要写多个字段,保存这个字段所在数据源主数据集引用,这个字段所在分录数据集引用,
		`be_ref_datasource_id` varchar(100) DEFAULT NULL COMMENT '被关联方数据源ID',
		`be_ref_dataset_id` varchar(50) DEFAULT NULL COMMENT '被关联方主数据集ID',
		`be_ref_field_id` varchar(50) DEFAULT NULL COMMENT '被关联方主数据集主键ID',
		`be_ref_field_id_value` int(11) DEFAULT NULL COMMENT '被关联方主数据集主键ID值',
		`be_ref_dataset_id_1` varchar(50) DEFAULT NULL COMMENT '被关联方关联字段所主数据集ID',
		`be_ref_field_id_1` varchar(50) DEFAULT NULL COMMENT '被关联方关联字段所在数据集主键ID',
		`be_ref_field_id_value_1` int(11) DEFAULT NULL COMMENT '被关联方关联字段所在主数据集主键ID值',
	 * @param field 主数据集id或分录数据集id
	 * @param relationItem
	 * @param data
	 * @return
	 */
	public Map<String, Object> getBeReferenceLi(Field field, RelationItem relationItem, Map<String, Value> data) {
		Map<String, Object> sourceRefMap = New.hashMap();
		
		int relationId = data.get(field.getId()).getInt();
		if (relationItem.getRelationDataSetId().equals("A")) {
			sourceRefMap.put("be_ref_datasource_id", relationItem.getRelationModelId());
			sourceRefMap.put("be_ref_dataset_id", relationItem.getRelationDataSetId());
			sourceRefMap.put("be_ref_field_id", relationItem.getValueField());
			sourceRefMap.put("be_ref_field_id_value", relationId);
			return sourceRefMap;
		}
		
		DatasourceFactory datasourceFactory = new DatasourceFactory();
		Datasource datasource = datasourceFactory.getDatasource(relationItem.getRelationModelId());
		String detailTableName = datasource.getCalcDetailTableName(relationItem.getRelationDataSetId());
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from " + detailTableName + " ");
		sb.append(" where 1=1 ");
		sb.append(" and id=? ");
		
		Map<String, Object> refData = this.formTemplateDao.getJdbcTemplate().queryForMap(sb.toString(), relationId);
		Object masterDataId = 0;
		for (DetailData detailData: datasource.getDetailData()) {
			if (detailData.getId().equals(relationItem.getRelationDataSetId())) {
				masterDataId = refData.get(detailData.getParentFieldId());
				break;
			}
		}
		
		sourceRefMap.put("be_ref_datasource_id", relationItem.getRelationModelId());
		sourceRefMap.put("be_ref_dataset_id", "A");
		sourceRefMap.put("be_ref_field_id", "id");
		sourceRefMap.put("be_ref_field_id_value", masterDataId);
		
		sourceRefMap.put("be_ref_dataset_id_1", relationItem.getRelationDataSetId());
		sourceRefMap.put("be_ref_field_id_1", "id");
		sourceRefMap.put("be_ref_field_id_value_1", relationId);
		
		return sourceRefMap;
	}

	public FormTemplateDao getFormTemplateDao() {
		return formTemplateDao;
	}

	public void setFormTemplateDao(FormTemplateDao formTemplateDao) {
		this.formTemplateDao = formTemplateDao;
	}
	
	
}
