package com.javameta.model.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
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
		String fieldId = getDataSetFieldId(datasource, fieldLi.get(0));
		if (destData == null && srcData != null) {// 删除的分录
			Map<String, Object> beReferenceQuery = New.hashMap();
			beReferenceQuery.put("be_ref_datasource_id", datasource.getId());
			beReferenceQuery.put("be_ref_dataset_id", fieldLi.get(0).getDataSetId());
			beReferenceQuery.put("be_ref_field_id", fieldId);
			beReferenceQuery.put("be_ref_field_id_value", srcData.get(fieldId).getObject());
			
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
	
//	func (o UsedCheck) GetBeReferenceLi(db *mgo.Database, fieldGroup FieldGroup, relationItem RelationItem, data *map[string]interface{}) []interface{} {
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
		String detailTableName = datasourceFactory.getDetailTableName(relationItem.getRelationModelId(), relationItem.getRelationDataSetId());
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from " + detailTableName + " ");
		sb.append(" where 1=1 ");
		sb.append(" and id=? ");
		
		Map<String, Object> refData = this.formTemplateDao.getJdbcTemplate().queryForMap(sb.toString(), relationId);
		Datasource datasource = datasourceFactory.getDatasource(relationItem.getRelationModelId());
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
