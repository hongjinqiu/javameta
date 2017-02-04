package com.javameta.web.form.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javameta.JavametaException;
import com.javameta.model.BusinessDataType;
import com.javameta.model.DatasourceFactory;
import com.javameta.model.FormTemplateEnum;
import com.javameta.model.FormTemplateFactory;
import com.javameta.model.ValueBusinessObject;
import com.javameta.model.datasource.Datasource;
import com.javameta.model.datasource.DetailData;
import com.javameta.model.datasource.Field;
import com.javameta.model.handler.DiffDataRow;
import com.javameta.model.handler.UsedCheck;
import com.javameta.model.iterate.DatasourceIterator;
import com.javameta.model.iterate.IDatasourceDiffLineDataIterate;
import com.javameta.model.iterate.IDatasourceLineDataIterate;
import com.javameta.model.template.ColumnModel;
import com.javameta.model.template.FormTemplate;
import com.javameta.util.ApplicationContextUtil;
import com.javameta.util.New;
import com.javameta.value.Value;
import com.javameta.value.ValueInt;
import com.javameta.value.ValueTimestamp;
import com.javameta.web.form.controller.ModelRenderVO;
import com.javameta.web.form.dao.FormDao;
import com.javameta.web.support.ServiceSupport;

@Service
@Transactional
public abstract class AFormService extends ServiceSupport {
	@Autowired
	private FormDao formDao;
	@Autowired
	private FormSaveService formSaveService;

	public ValueBusinessObject getValueBoFromDb(FormTemplate formTemplate, Datasource datasource, Map<String, Object> param) {
		Map<String, Object> bo = getBoFromDb(formTemplate, datasource, param);
		return BusinessDataType.convertMapToValueBusinessObject(datasource, bo);
	}

	private Map<String, Object> getBoFromDb(FormTemplate formTemplate, Datasource datasource, Map<String, Object> param) {
		Map<String, Object> result = New.hashMap();
		FormTemplateFactory formTemplateFactory = new FormTemplateFactory();
		Map<String, String> query = New.hashMap();
		query.put("id", ObjectUtils.toString(param.get("id")));
		int pageNo = 1;
		int pageSize = 1;
		Map<String, Object> mainData = New.hashMap();
		for (ColumnModel columnModel : formTemplate.getColumnModel()) {
			if (columnModel.getDataSetId().equals("A")) {
				Map<String, Object> queryResult = formTemplateFactory.queryDataForColumnModel(formTemplate, columnModel, query, pageNo, pageSize);
				List<Map<String, Object>> items = (List<Map<String, Object>>) queryResult.get("items");
				if (items.size() > 0) {
					mainData.putAll(items.get(0));
				}
			}

		}
		result.put(datasource.getMasterData().getId(), mainData);

		for (ColumnModel columnModel : formTemplate.getColumnModel()) {
			if (!columnModel.getDataSetId().equals("A")) {
				DetailData matchDetailData = null;
				for (DetailData detailData : datasource.getDetailData()) {
					if (detailData.getId().equals(columnModel.getDataSetId())) {
						matchDetailData = detailData;
						break;
					}
				}
				Map<String, String> detailQuery = New.hashMap();
				detailQuery.put(matchDetailData.getParentFieldId(), ObjectUtils.toString(param.get("id")));
				int detailPageNo = 1;
				int detailPageSize = 10000;
				Map<String, Object> queryResult = formTemplateFactory.queryDataForColumnModel(formTemplate, columnModel, detailQuery, detailPageNo, detailPageSize);
				List<Map<String, Object>> items = (List<Map<String, Object>>) queryResult.get("items");
				result.put(columnModel.getDataSetId(), items);
			}
		}

		return result;
	}
	
	public ValueBusinessObject getValueBoFromDb(Datasource datasource, Map<String, Object> param) {
		return formDao.getValueBoFromDb(datasource, param);
	}

	public ModelRenderVO newDataCommon(HttpServletRequest request, HttpServletResponse response) {
		String datasourceModelId = request.getParameter("datasourceModelId");
		String formTemplateId = request.getParameter("formTemplateId");

		DatasourceFactory datasourceFactory = new DatasourceFactory();
		Datasource datasource = datasourceFactory.getDatasource(datasourceModelId);
		FormTemplateFactory formTemplateFactory = new FormTemplateFactory();
		FormTemplate formTemplate = formTemplateFactory.getFormTemplate(formTemplateId, FormTemplateEnum.FORM);
		beforeNewData(request, response, datasource, formTemplate);
		ValueBusinessObject valueBo = datasourceFactory.getInstanceByDS(datasource);
		afterNewData(request, response, datasource, formTemplate, valueBo);

		Map<String, Object> bo = (Map<String, Object>) BusinessDataType.convertValueBusinessObjectToJSONStringObject(valueBo);
		Map<String, Object> columnModelData = formTemplateFactory.getColumnModelDataForFormTemplate(formTemplate, bo);
		bo = (Map<String, Object>) columnModelData.get("bo");
		Map<String, Object> relationBo = (Map<String, Object>) columnModelData.get("relationBo");

		ModelRenderVO modelRenderVO = new ModelRenderVO();
		modelRenderVO.setUserId(getUserId(request));
		modelRenderVO.setBo(bo);
		modelRenderVO.setRelationBo(relationBo);
		modelRenderVO.setDatasource(datasource);
		modelRenderVO.setFormTemplate(formTemplate);

		return modelRenderVO;
	}

	public ModelRenderVO getDataCommon(HttpServletRequest request, HttpServletResponse response) {
		String datasourceModelId = request.getParameter("datasourceModelId");
		String formTemplateId = request.getParameter("formTemplateId");
		String id = request.getParameter("id");

		Map<String, Object> queryMap = New.hashMap();
		queryMap.put("id", id);
		FormTemplateFactory formTemplateFactory = new FormTemplateFactory();
		FormTemplate formTemplate = formTemplateFactory.getFormTemplate(formTemplateId, FormTemplateEnum.FORM);

		DatasourceFactory datasourceFactory = new DatasourceFactory();
		Datasource datasource = datasourceFactory.getDatasource(datasourceModelId);
		beforeGetData(request, response, datasource, formTemplate);
		ValueBusinessObject valueBo = getValueBoFromDb(formTemplate, datasource, queryMap);
		afterGetData(request, response, datasource, formTemplate, valueBo);

		UsedCheck usedCheck = (UsedCheck) ApplicationContextUtil.getApplicationContext().getBean("usedCheck");
		Map<String, Object> usedCheckBo = usedCheck.getFormUsedCheck(datasource, valueBo);

		Map<String, Object> bo = (Map<String, Object>) BusinessDataType.convertValueBusinessObjectToJSONStringObject(valueBo);
		Map<String, Object> columnModelData = formTemplateFactory.getColumnModelDataForFormTemplate(formTemplate, bo);
		bo = (Map<String, Object>) columnModelData.get("bo");
		Map<String, Object> relationBo = (Map<String, Object>) columnModelData.get("relationBo");

		//		modelTemplateFactory.ConvertDataType(datasource, &bo)	// 根据field type,转换值,传给客户端的都是string,这个地方先不用管

		ModelRenderVO modelRenderVO = new ModelRenderVO();
		modelRenderVO.setUserId(getUserId(request));
		modelRenderVO.setBo(bo);
		modelRenderVO.setRelationBo(relationBo);
		modelRenderVO.setUsedCheckBo(usedCheckBo);
		modelRenderVO.setDatasource(datasource);
		modelRenderVO.setFormTemplate(formTemplate);
		return modelRenderVO;
	}

	public ModelRenderVO copyDataCommon(HttpServletRequest request, HttpServletResponse response) {
		String datasourceModelId = request.getParameter("datasourceModelId");
		String formTemplateId = request.getParameter("formTemplateId");
		String id = request.getParameter("id");

		Map<String, Object> queryMap = New.hashMap();
		queryMap.put("id", id);
		FormTemplateFactory formTemplateFactory = new FormTemplateFactory();
		FormTemplate formTemplate = formTemplateFactory.getFormTemplate(formTemplateId, FormTemplateEnum.FORM);

		DatasourceFactory datasourceFactory = new DatasourceFactory();
		Datasource datasource = datasourceFactory.getDatasource(datasourceModelId);
		ValueBusinessObject srcValueBo = getValueBoFromDb(formTemplate, datasource, queryMap);

		beforeCopyData(request, response, datasource, formTemplate, srcValueBo);
		ValueBusinessObject valueBo = datasourceFactory.GetCopyInstance(datasourceModelId, srcValueBo);
		afterCopyData(request, response, datasource, formTemplate, valueBo);

		Map<String, Object> bo = (Map<String, Object>) BusinessDataType.convertValueBusinessObjectToJSONStringObject(valueBo);
		Map<String, Object> columnModelData = formTemplateFactory.getColumnModelDataForFormTemplate(formTemplate, bo);
		bo = (Map<String, Object>) columnModelData.get("bo");
		Map<String, Object> relationBo = (Map<String, Object>) columnModelData.get("relationBo");

		ModelRenderVO modelRenderVO = new ModelRenderVO();
		modelRenderVO.setUserId(getUserId(request));
		modelRenderVO.setBo(bo);
		modelRenderVO.setRelationBo(relationBo);
		modelRenderVO.setDatasource(datasource);
		modelRenderVO.setFormTemplate(formTemplate);
		return modelRenderVO;
	}

	public ModelRenderVO editDataCommon(HttpServletRequest request, HttpServletResponse response) {
		String datasourceModelId = request.getParameter("datasourceModelId");
		String formTemplateId = request.getParameter("formTemplateId");
		String id = request.getParameter("id");

		Map<String, Object> queryMap = New.hashMap();
		queryMap.put("id", id);
		FormTemplateFactory formTemplateFactory = new FormTemplateFactory();
		FormTemplate formTemplate = formTemplateFactory.getFormTemplate(formTemplateId, FormTemplateEnum.FORM);

		DatasourceFactory datasourceFactory = new DatasourceFactory();
		Datasource datasource = datasourceFactory.getDatasource(datasourceModelId);
		ValueBusinessObject valueBo = getValueBoFromDb(formTemplate, datasource, queryMap);

		/*
		 * result.put("success", true);
		result.put("message", "");
		 */
		Map<String, Object> editValidateResult = editValidate(request, response, datasource, formTemplate, valueBo);
		boolean success = (Boolean) editValidateResult.get("success");
		if (!success) {
			throw new JavametaException((String) editValidateResult.get("message"));
		}

		beforeEditData(request, response, datasource, formTemplate, valueBo);
		afterEditData(request, response, datasource, formTemplate, valueBo);

		UsedCheck usedCheck = (UsedCheck) ApplicationContextUtil.getApplicationContext().getBean("usedCheck");
		Map<String, Object> usedCheckBo = usedCheck.getFormUsedCheck(datasource, valueBo);

		Map<String, Object> bo = (Map<String, Object>) BusinessDataType.convertValueBusinessObjectToJSONStringObject(valueBo);
		Map<String, Object> columnModelData = formTemplateFactory.getColumnModelDataForFormTemplate(formTemplate, bo);
		bo = (Map<String, Object>) columnModelData.get("bo");
		Map<String, Object> relationBo = (Map<String, Object>) columnModelData.get("relationBo");

		ModelRenderVO modelRenderVO = new ModelRenderVO();
		modelRenderVO.setUserId(getUserId(request));
		modelRenderVO.setBo(bo);
		modelRenderVO.setRelationBo(relationBo);
		modelRenderVO.setUsedCheckBo(usedCheckBo);
		modelRenderVO.setDatasource(datasource);
		modelRenderVO.setFormTemplate(formTemplate);
		return modelRenderVO;
	}

	public ModelRenderVO saveDataCommon(HttpServletRequest request, HttpServletResponse response) {
		String datasourceModelId = request.getParameter("datasourceModelId");
		String formTemplateId = request.getParameter("formTemplateId");
		String jsonData = request.getParameter("jsonData");
		Map<String, Object> bo = JSONObject.fromObject(jsonData);

		DatasourceFactory datasourceFactory = new DatasourceFactory();
		Datasource datasource = datasourceFactory.getDatasource(datasourceModelId);
		ValueBusinessObject valueBo = BusinessDataType.convertMapToValueBusinessObject(datasource, bo);

		FormTemplateFactory formTemplateFactory = new FormTemplateFactory();
		FormTemplate formTemplate = formTemplateFactory.getFormTemplate(formTemplateId, FormTemplateEnum.FORM);

		String strId = datasourceFactory.getStrId(bo);
		if (StringUtils.isEmpty(strId) || "0".equals(strId)) {
			setCreateFixFieldValue(request, datasource, valueBo);
		} else {
			setModifyFixFieldValue(request, datasource, valueBo);
			Map<String, Object> editValidateResult = editValidate(request, response, datasource, formTemplate, valueBo);
			boolean success = (Boolean) editValidateResult.get("success");
			if (!success) {
				throw new JavametaException((String) editValidateResult.get("message"));
			}
		}
		beforeSaveData(request, response, datasource, formTemplate, valueBo);
		
		List<DiffDataRow> diffDataRowLi = formSaveService.saveData(datasource, valueBo);
				
		afterSaveData(request, response, datasource, formTemplate, valueBo, diffDataRowLi);
		
		// 从数据库里重新读取一遍
		Map<String, Object> param = New.hashMap();
		param.put("id", valueBo.getMasterData().get("id").getObject());
		valueBo = formDao.getValueBoFromDb(datasource, param);
		
		UsedCheck usedCheck = (UsedCheck) ApplicationContextUtil.getApplicationContext().getBean("usedCheck");
		Map<String, Object> usedCheckBo = usedCheck.getFormUsedCheck(datasource, valueBo);

		bo = (Map<String, Object>) BusinessDataType.convertValueBusinessObjectToJSONStringObject(valueBo);
		Map<String, Object> columnModelData = formTemplateFactory.getColumnModelDataForFormTemplate(formTemplate, bo);
		bo = (Map<String, Object>) columnModelData.get("bo");
		Map<String, Object> relationBo = (Map<String, Object>) columnModelData.get("relationBo");

		ModelRenderVO modelRenderVO = new ModelRenderVO();
		modelRenderVO.setUserId(getUserId(request));
		modelRenderVO.setBo(bo);
		modelRenderVO.setRelationBo(relationBo);
		modelRenderVO.setUsedCheckBo(usedCheckBo);
		modelRenderVO.setDatasource(datasource);
		modelRenderVO.setFormTemplate(formTemplate);
		return modelRenderVO;
	}
	
	public ModelRenderVO giveUpDataCommon(HttpServletRequest request, HttpServletResponse response) {
		String datasourceModelId = request.getParameter("datasourceModelId");
		String formTemplateId = request.getParameter("formTemplateId");
		String id = request.getParameter("id");

		Map<String, Object> queryMap = New.hashMap();
		queryMap.put("id", id);
		FormTemplateFactory formTemplateFactory = new FormTemplateFactory();
		FormTemplate formTemplate = formTemplateFactory.getFormTemplate(formTemplateId, FormTemplateEnum.FORM);

		DatasourceFactory datasourceFactory = new DatasourceFactory();
		Datasource datasource = datasourceFactory.getDatasource(datasourceModelId);

		ValueBusinessObject valueBo = getValueBoFromDb(formTemplate, datasource, queryMap);
		beforeGiveUpData(request, response, datasource, formTemplate, valueBo);
		afterGiveUpData(request, response, datasource, formTemplate, valueBo);
		
		UsedCheck usedCheck = (UsedCheck) ApplicationContextUtil.getApplicationContext().getBean("usedCheck");
		Map<String, Object> usedCheckBo = usedCheck.getFormUsedCheck(datasource, valueBo);

		Map<String, Object> bo = (Map<String, Object>) BusinessDataType.convertValueBusinessObjectToJSONStringObject(valueBo);
		Map<String, Object> columnModelData = formTemplateFactory.getColumnModelDataForFormTemplate(formTemplate, bo);
		bo = (Map<String, Object>) columnModelData.get("bo");
		Map<String, Object> relationBo = (Map<String, Object>) columnModelData.get("relationBo");

		//		modelTemplateFactory.ConvertDataType(datasource, &bo)	// 根据field type,转换值,传给客户端的都是string,这个地方先不用管

		ModelRenderVO modelRenderVO = new ModelRenderVO();
		modelRenderVO.setUserId(getUserId(request));
		modelRenderVO.setBo(bo);
		modelRenderVO.setRelationBo(relationBo);
		modelRenderVO.setUsedCheckBo(usedCheckBo);
		modelRenderVO.setDatasource(datasource);
		modelRenderVO.setFormTemplate(formTemplate);
		return modelRenderVO;
	}
	
	public ModelRenderVO deleteDataCommon(HttpServletRequest request, HttpServletResponse response) {
		String datasourceModelId = request.getParameter("datasourceModelId");
		String formTemplateId = request.getParameter("formTemplateId");
		String id = request.getParameter("id");
		
		Map<String, Object> queryMap = New.hashMap();
		queryMap.put("id", id);
		FormTemplateFactory formTemplateFactory = new FormTemplateFactory();
		FormTemplate formTemplate = null;
		if (StringUtils.isNotEmpty(formTemplateId)) {
			formTemplate = formTemplateFactory.getFormTemplate(formTemplateId, FormTemplateEnum.FORM);
		}
		
		DatasourceFactory datasourceFactory = new DatasourceFactory();
		Datasource datasource = datasourceFactory.getDatasource(datasourceModelId);
		// 列表页也调用这个删除方法,但是列表页又没有传递formTemplateId,只有 gatheringBill等要做赤字判断,走与form相同的逻辑,才会传 formTemplateId,
		ValueBusinessObject valueBo = null;
		if (formTemplate != null) {
			valueBo = getValueBoFromDb(formTemplate, datasource, queryMap);
		} else {
			valueBo = getValueBoFromDb(datasource, queryMap);
		}
		beforeDeleteData(request, response, datasource, formTemplate, valueBo);
		
		final UsedCheck usedCheck = (UsedCheck) ApplicationContextUtil.getApplicationContext().getBean("usedCheck");
		Map<String, Object> bo = (Map<String, Object>) BusinessDataType.convertValueBusinessObjectToJSONStringObject(valueBo);
		if (usedCheck.checkUsed(datasource, bo)) {
			throw new JavametaException("已被用，不能删除");
		}
		
		DatasourceIterator.iterateLineValueBo(datasource, valueBo, new IDatasourceLineDataIterate() {
			@Override
			public void iterate(List<Field> fieldLi, Map<String, Value> data, int rowIndex) {
				usedCheck.deleteAll(fieldLi, data);
			}
		});
		formDao.delete(datasource, valueBo);
		
		afterDeleteData(request, response, datasource, formTemplate, valueBo);
		
		// 列表页也调用这个删除方法,但是列表页又没有传递formTemplateId
		Map<String, Object> relationBo = New.hashMap();
		if (StringUtils.isNotEmpty(formTemplateId)) {
			Map<String, Object> columnModelData = formTemplateFactory.getColumnModelDataForFormTemplate(formTemplate, bo);
			bo = (Map<String, Object>) columnModelData.get("bo");
			relationBo = (Map<String, Object>) columnModelData.get("relationBo");
		}
		
		ModelRenderVO modelRenderVO = new ModelRenderVO();
		modelRenderVO.setUserId(getUserId(request));
		modelRenderVO.setBo(bo);
		modelRenderVO.setRelationBo(relationBo);
		modelRenderVO.setDatasource(datasource);
		modelRenderVO.setFormTemplate(formTemplate);
		return modelRenderVO;
	}
	
	public ModelRenderVO refreshDataCommon(HttpServletRequest request, HttpServletResponse response) {
		String datasourceModelId = request.getParameter("datasourceModelId");
		String formTemplateId = request.getParameter("formTemplateId");
		String id = request.getParameter("id");

		Map<String, Object> queryMap = New.hashMap();
		queryMap.put("id", id);
		FormTemplateFactory formTemplateFactory = new FormTemplateFactory();
		FormTemplate formTemplate = formTemplateFactory.getFormTemplate(formTemplateId, FormTemplateEnum.FORM);

		DatasourceFactory datasourceFactory = new DatasourceFactory();
		Datasource datasource = datasourceFactory.getDatasource(datasourceModelId);
		ValueBusinessObject valueBo = getValueBoFromDb(formTemplate, datasource, queryMap);
		beforeRefreshData(request, response, datasource, formTemplate, valueBo);
		afterRefreshData(request, response, datasource, formTemplate, valueBo);

		UsedCheck usedCheck = (UsedCheck) ApplicationContextUtil.getApplicationContext().getBean("usedCheck");
		Map<String, Object> usedCheckBo = usedCheck.getFormUsedCheck(datasource, valueBo);

		Map<String, Object> bo = (Map<String, Object>) BusinessDataType.convertValueBusinessObjectToJSONStringObject(valueBo);
		Map<String, Object> columnModelData = formTemplateFactory.getColumnModelDataForFormTemplate(formTemplate, bo);
		bo = (Map<String, Object>) columnModelData.get("bo");
		Map<String, Object> relationBo = (Map<String, Object>) columnModelData.get("relationBo");

		//		modelTemplateFactory.ConvertDataType(datasource, &bo)	// 根据field type,转换值,传给客户端的都是string,这个地方先不用管

		ModelRenderVO modelRenderVO = new ModelRenderVO();
		modelRenderVO.setUserId(getUserId(request));
		modelRenderVO.setBo(bo);
		modelRenderVO.setRelationBo(relationBo);
		modelRenderVO.setUsedCheckBo(usedCheckBo);
		modelRenderVO.setDatasource(datasource);
		modelRenderVO.setFormTemplate(formTemplate);
		return modelRenderVO;
	}
	
	public Map<String, Object> logListCommon(HttpServletRequest request, HttpServletResponse response) {
		String datasourceModelId = request.getParameter("datasourceModelId");
		String id = request.getParameter("id");
		
		int pageNo = 1;
		int pageSize = 10;
		String orderBy = "";
		Map<String, Object> query = New.hashMap();
		query.put("be_ref_datasource_id", datasourceModelId);
		query.put("be_ref_dataset_id", "A");
		query.put("be_ref_field_id", "id");
		query.put("be_ref_field_id_value", id);
		UsedCheck usedCheck = (UsedCheck) ApplicationContextUtil.getApplicationContext().getBean("usedCheck");
		return usedCheck.queryReference(query, pageNo, pageSize, orderBy);
	}

	public void setCreateFixFieldValue(HttpServletRequest request, Datasource datasource, ValueBusinessObject valueBo) {
		final int userId = getUserId(request);
		final Date modifyTime = new Date();

		DatasourceFactory datasourceFactory = new DatasourceFactory();
		Datasource sysUserDatasource = datasourceFactory.getDatasource("SysUser");
		String sysUserTableName = sysUserDatasource.getCalcTableName();
		String sysUserIdField = sysUserDatasource.getMasterData().getFixField().getPrimaryKey().getCalcFieldName();
		String sql = "select * from {tableName} where 1=1 and {idField}=?";
		sql = sql.replace("{tableName}", sysUserTableName);
		sql = sql.replace("{idField}", sysUserIdField);
		final Map<String, Object> sysUserMaster = this.formDao.getJdbcTemplate().queryForMap(sql, userId);

		DatasourceIterator.iterateLineValueBo(sysUserDatasource, valueBo, new IDatasourceLineDataIterate() {
			@Override
			public void iterate(List<Field> fieldLi, Map<String, Value> data, int rowIndex) {
				data.put("createBy", ValueInt.get(userId));
				data.put("createTime", ValueTimestamp.get(modifyTime));
				String createUnit = ObjectUtils.toString(sysUserMaster.get("createUnit"), "0");
				data.put("createUnit", ValueInt.get(Integer.valueOf(createUnit)));
			}
		});
	}

	public void setModifyFixFieldValue(HttpServletRequest request, Datasource datasource, ValueBusinessObject valueBo) {
		final int userId = getUserId(request);
		final Date modifyTime = new Date();

		final DatasourceFactory datasourceFactory = new DatasourceFactory();
		Datasource sysUserDatasource = datasourceFactory.getDatasource("SysUser");
		String sysUserTableName = sysUserDatasource.getCalcTableName();
		String sysUserIdField = sysUserDatasource.getMasterData().getFixField().getPrimaryKey().getCalcFieldName();
		String sql = "select * from {tableName} where 1=1 and {idField}=?";
		sql = sql.replace("{tableName}", sysUserTableName);
		sql = sql.replace("{idField}", sysUserIdField);
		final Map<String, Object> sysUserMaster = this.formDao.getJdbcTemplate().queryForMap(sql, userId);

		Map<String, Object> param = New.hashMap();
		param.put("id", valueBo.getMasterData().get("id").getInt());
		ValueBusinessObject srcValueBo = getValueBoFromDb(datasource, param);
		
		DatasourceIterator.iterateDiffValueBo(datasource, valueBo, srcValueBo, new IDatasourceDiffLineDataIterate() {
			@Override
			public void iterate(List<Field> fieldLi, Map<String, Value> destData, Map<String, Value> srcData) {
				if (destData != null && srcData == null) {
					destData.put("createBy", ValueInt.get(userId));
					destData.put("createTime", ValueTimestamp.get(modifyTime));
					String createUnit = ObjectUtils.toString(sysUserMaster.get("createUnit"), "0");
					destData.put("createUnit", ValueInt.get(Integer.valueOf(createUnit)));
				} else if (destData == null && srcData != null) {
					// 删除,不处理
				} else if (destData != null && srcData != null) {
					boolean isMasterData = fieldLi.get(0).isMasterField();
					boolean isDetailDataDiff = (!isMasterData) && datasourceFactory.isDataDifferent(fieldLi, destData, srcData);
					if (isMasterData || isDetailDataDiff) {
						destData.put("createBy", srcData.get("createBy"));
						destData.put("createTime", srcData.get("createTime"));
						destData.put("createUnit", srcData.get("createUnit"));
		
						destData.put("modifyBy", ValueInt.get(userId));
						destData.put("modifyTime", ValueTimestamp.get(modifyTime));
						String createUnit = ObjectUtils.toString(sysUserMaster.get("createUnit"), "0");
						destData.put("modifyUnit", ValueInt.get(Integer.valueOf(createUnit)));
					}
				}
			}
		});
	}

	private int getUserId(HttpServletRequest request) {
		String userIdString = ObjectUtils.toString(request.getSession().getAttribute("userId"), "");
		int userId = 0;
		if (StringUtils.isNotEmpty(userIdString)) {
			userId = Integer.valueOf(userIdString);
		}
		return userId;
	}

	public void beforeNewData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate) {

	}

	public void afterNewData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {

	}

	public void beforeGetData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate) {

	}

	public void afterGetData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {

	}

	public void beforeCopyData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject srcValueBo) {

	}

	public void afterCopyData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {

	}

	public Map<String, Object> editValidate(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {
		Map<String, Object> result = New.hashMap();
		result.put("success", true);
		result.put("message", "");
		return result;
	}

	public void beforeEditData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {

	}

	public void afterEditData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {

	}

	public void beforeSaveData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {

	}

	public void afterSaveData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo,
			List<DiffDataRow> diffDateRowLi) {

	}

	public void beforeGiveUpData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {

	}

	public void afterGiveUpData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {

	}

	public void beforeDeleteData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {

	}

	public void afterDeleteData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {

	}

	public void beforeRefreshData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {

	}

	public void afterRefreshData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {

	}

	public FormDao getFormDao() {
		return formDao;
	}

	public void setFormDao(FormDao formDao) {
		this.formDao = formDao;
	}

	public FormSaveService getFormSaveService() {
		return formSaveService;
	}

	public void setFormSaveService(FormSaveService formSaveService) {
		this.formSaveService = formSaveService;
	}
}
