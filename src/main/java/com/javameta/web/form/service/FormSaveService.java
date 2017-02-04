package com.javameta.web.form.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javameta.JavametaException;
import com.javameta.expression.ExpressionParser;
import com.javameta.model.BusinessDataType;
import com.javameta.model.ValueBusinessObject;
import com.javameta.model.datasource.Datasource;
import com.javameta.model.datasource.DetailData;
import com.javameta.model.datasource.Field;
import com.javameta.model.handler.DiffDataRow;
import com.javameta.model.handler.UsedCheck;
import com.javameta.model.iterate.DatasourceIterator;
import com.javameta.model.iterate.IDatasourceDiffLineDataIterate;
import com.javameta.model.iterate.IDatasourceFieldDataIterate;
import com.javameta.model.iterate.IDatasourceFieldIterate;
import com.javameta.model.iterate.IDatasourceLineDataIterate;
import com.javameta.util.ApplicationContextUtil;
import com.javameta.util.New;
import com.javameta.value.Value;
import com.javameta.value.ValueNull;
import com.javameta.web.form.dao.FormDao;
import com.javameta.web.support.ServiceSupport;

@Service
@Transactional
public class FormSaveService extends ServiceSupport {
	@Autowired
	private FormDao formDao;

	public List<DiffDataRow> saveData(final Datasource datasource, final ValueBusinessObject valueBo) {
		Value idValue = valueBo.getMasterData().get("id");
		int id = 0;
		if (idValue != null && !idValue.equals(ValueNull.INSTANCE)) {
			id = idValue.getInt();
		}
		// 主数据集和分录数据校验
		String message = validateBO(datasource, valueBo);
		if (StringUtils.isNotEmpty(message)) {
			throw new JavametaException(message);
		}
		if (id == 0) {// 新增
			// 写数据库,使主数据集和分录id赋值,
			insert(datasource, valueBo);
			// 被用过帐
			UsedCheck usedCheck = (UsedCheck)ApplicationContextUtil.getApplicationContext().getBean("usedCheck");
			final List<DiffDataRow> diffDataRowLi = New.arrayList();
			DatasourceIterator.iterateLineValueBo(datasource, valueBo, new IDatasourceLineDataIterate() {
				@Override
				public void iterate(List<Field> fieldLi, Map<String, Value> data, int rowIndex) {
					DiffDataRow diffDataRow = new DiffDataRow();
					diffDataRow.setFieldLi(fieldLi);
					diffDataRow.setDestBo(valueBo);
					diffDataRow.setDestData(data);
					diffDataRow.setSrcData(null);
					diffDataRow.setSrcBo(null);
					diffDataRowLi.add(diffDataRow);
				}
			});
			for (int i = 0; i < diffDataRowLi.size(); i++) {
				usedCheck.insert(diffDataRowLi.get(i).getFieldLi(), valueBo, diffDataRowLi.get(i).getDestData());
			}
			return diffDataRowLi;
		}
		// 找出srcBo,
		Map<String, Object> param = New.hashMap();
		param.put("id", id);
		final ValueBusinessObject srcValueBo = this.formDao.getValueBoFromDb(datasource, param);
		final List<DiffDataRow> diffDataRowLi = New.arrayList();
		final UsedCheck usedCheck = (UsedCheck)ApplicationContextUtil.getApplicationContext().getBean("usedCheck");
		DatasourceIterator.iterateDiffValueBo(datasource, valueBo, srcValueBo, new IDatasourceDiffLineDataIterate() {
			@Override
			public void iterate(List<Field> fieldLi, Map<String, Value> destData, Map<String, Value> srcData) {
				// 新增,分录+id
				if (destData != null && srcData == null) {
					Value idValue = destData.get("id");
					if (idValue == null || idValue.equals(ValueNull.INSTANCE) || idValue.getInt() == 0) {
						formDao.insert(datasource, fieldLi, valueBo, destData);
					}
				}
				DiffDataRow diffDataRow = new DiffDataRow();
				diffDataRow.setFieldLi(fieldLi);
				diffDataRow.setDestBo(valueBo);
				diffDataRow.setDestData(destData);
				diffDataRow.setSrcData(srcData);
				diffDataRow.setSrcBo(srcValueBo);
				diffDataRowLi.add(diffDataRow);

				// 删除,被用判断
				if (usedCheck.checkDeleteDetailRecordUsed(datasource, valueBo, diffDataRow)) {
					throw new JavametaException("部分分录数据已被用，不可删除");
				}
				if (destData == null && srcData != null) {
					formDao.delete(datasource, fieldLi, srcValueBo, srcData);
				}
				
				// 修改
				if (destData != null && srcData != null) {
					formDao.update(datasource, fieldLi, valueBo, destData);
				}
			}
		});

		// 被用差异行处理,
		for (int i = 0; i < diffDataRowLi.size(); i++) {
			List<Field> fieldLi = diffDataRowLi.get(i).getFieldLi();
			Map<String, Value> destData = diffDataRowLi.get(i).getDestData();
			Map<String, Value> srcData = diffDataRowLi.get(i).getSrcData();
			usedCheck.update(fieldLi, valueBo, destData, srcData);
		}

		return diffDataRowLi;
	}
	
	private void insert(Datasource datasource, ValueBusinessObject valueBo) {
		this.formDao.insert(datasource, valueBo);
	}
	
	private String validateBO(final Datasource datasource, ValueBusinessObject valueBo) {
		final List<String> messageLi = New.arrayList();
		DatasourceIterator.iterateFieldValueBo(datasource, valueBo, new IDatasourceFieldDataIterate() {
			@Override
			public void iterate(Field field, Map<String, Value> data, int rowIndex) {
				List<String> fieldMessageLi = validateField(field, data);
				if (field.isMasterField()) {
					messageLi.addAll(fieldMessageLi);
				} else {
					for (DetailData detailData : datasource.getDetailData()) {
						if (field.getDataSetId().equals(detailData.getId())) {
							for (String item : fieldMessageLi) {
								messageLi.add("分录:" + detailData.getDisplayName() + "序号为" + (rowIndex + 1) + "的数据," + item);
							}
							break;
						}
					}
				}
			}
		});
		// validate detailData.allowEmpty
		if (datasource.getDetailData() != null) {
			for (DetailData detailData : datasource.getDetailData()) {
				if (detailData.getAllowEmpty() != null && detailData.getAllowEmpty() == false) {
					boolean isEmpty = false;
					if (valueBo.getDetailData().get(detailData.getId()) == null) {
						isEmpty = true;
					} else {
						List<Map<String, Value>> lineData = valueBo.getDetailData().get(detailData.getId());
						if (lineData.size() == 0) {
							isEmpty = true;
						}
					}
					if (isEmpty) {
						messageLi.add("分录:" + detailData.getDisplayName() + "不允许为空");
					}
				}

			}
		}
		String duplicateMessage = validateBODuplicate(datasource, valueBo);
		if (StringUtils.isNotEmpty(duplicateMessage)) {
			messageLi.add(duplicateMessage);
		}

		return StringUtils.join(messageLi.toArray(), "<br />");
	}
	
	private String validateBODuplicate(Datasource datasource, ValueBusinessObject valueBo) {
		String result = "";
		result += validateMasterDataDuplicate(datasource, valueBo);
		if (StringUtils.isNotEmpty(result)) {
			result += "<br />";
		}
		result += validateDetailDataDuplicate(datasource, valueBo);
		return result;
	}
	
	private String validateMasterDataDuplicate(Datasource datasource, ValueBusinessObject valueBo) {
		String message = "";
		
		String strId = null;
		Value idValue = valueBo.getMasterData().get("id");
		if (idValue != null && !idValue.equals(ValueNull.INSTANCE)) {
			strId = idValue.getString();
		}
		
		final StringBuilder sb = new StringBuilder();
		sb.append(" select count(1) from " + datasource.getCalcTableName() + " ");
		sb.append(" where 1=1 ");
		final Map<String, Object> queryParam = New.hashMap();
		
		final List<String> andFieldNameLi = New.arrayList();
		DatasourceIterator.iterateFieldValueBo(datasource, valueBo, new IDatasourceFieldDataIterate() {
			@Override
			public void iterate(Field field, Map<String, Value> data, int rowIndex) {
				if (field.isMasterField()) {
					if (field.getAllowDuplicate() != null && field.getAllowDuplicate() == false && !field.getId().equals("id")) {
						Value fieldValue = data.get(field.getId());
						if (fieldValue != null && !fieldValue.equals(ValueNull.INSTANCE)) {
							sb.append(" and {fieldName}=:{fieldName} ".replace("{fieldName}", field.getCalcFieldName()));
							queryParam.put(field.getCalcFieldName(), data.get(field.getId()).getObject());
							andFieldNameLi.add(field.getDisplayName());
						}
					}
				}
			}
		});
		if (andFieldNameLi.size() > 0) {
			if (StringUtils.isNotEmpty(strId) && !strId.equals("0")) {
				Field idField = datasource.getMasterData().getFixField().getPrimaryKey();
				sb.append(" and {fieldName}<>:{fieldName} ".replace("{fieldName}", idField.getCalcFieldName()));
				queryParam.put(idField.getCalcFieldName(), strId);
			}
			sb.append(" limit 1 ");
			int count = this.formDao.getNamedParameterJdbcTemplate().queryForInt(sb.toString(), queryParam);
			if (count > 0) {
				message = StringUtils.join(andFieldNameLi, "+") + "不允许重复";
			}
		}
		
		return message;
	}
	
	private String validateDetailDataDuplicate(final Datasource datasource, final ValueBusinessObject valueBo) {
		final List<String> messageLi = New.arrayList();
		final List<String> duplicateFieldIdLi = New.arrayList();
		final List<String> duplicateFieldNameLi = New.arrayList();
		DatasourceIterator.iterateField(datasource, new IDatasourceFieldIterate() {
			@Override
			public void iterate(Field field) {
				if (!field.isMasterField()) {
					if (field.getAllowDuplicate() != null && field.getAllowDuplicate() == false && !field.getId().equals("id")) {
						duplicateFieldIdLi.add(field.getId());
						duplicateFieldNameLi.add(field.getDisplayName());
					}
				}
			}
		});
		if (duplicateFieldIdLi.size() == 0) {
			return "";
		}
		final String duplicateFieldNameJoin = StringUtils.join(duplicateFieldNameLi.toArray(), "+") + "不允许重复";
		DatasourceIterator.iterateLineValueBo(datasource, valueBo, new IDatasourceLineDataIterate() {
			@Override
			public void iterate(List<Field> fieldLi, final Map<String, Value> data, final int rowIndex) {
				DatasourceIterator.iterateLineValueBo(datasource, valueBo, new IDatasourceLineDataIterate() {
					@Override
					public void iterate(List<Field> innerFieldLi, Map<String, Value> innerData, int innerRowIndex) {
						if (innerRowIndex > rowIndex) {
							boolean isDuplicate = true;
							if (duplicateFieldIdLi.size() == 0) {
								isDuplicate = false;
							}
							for (String item: duplicateFieldIdLi) {
								if (innerData.get(item) == null && data.get(item) != null) {
									isDuplicate = false;
									break;
								}
								if (innerData.get(item) != null && data.get(item) == null) {
									isDuplicate = false;
									break;
								}
								if (innerData.get(item) != null && data.get(item) != null && !innerData.get(item).equals(data.get(item))) {
									isDuplicate = false;
									break;
								}
							}
							if (isDuplicate) {
								for (DetailData detailData : datasource.getDetailData()) {
									if (innerFieldLi.get(0).getDataSetId().equals(detailData.getId())) {
										messageLi.add("分录:"+detailData.getDisplayName()+"序号为"+(rowIndex+1)+","+(innerRowIndex+1)+"的数据，"+duplicateFieldNameJoin);
									}
								}
							}
						}
					}
				});
			}
		});
		
		return StringUtils.join(messageLi.toArray(), "<br />");
	}

	private List<String> validateField(Field field, Map<String, Value> data) {
		List<String> messageLi = New.arrayList();

		if (field.getAllowEmpty() != null && field.getAllowEmpty() == false) {
			Value value = data.get(field.getId());
			if (value != null && !value.equals(ValueNull.INSTANCE)) {
				if (StringUtils.isEmpty(value.getString())) {
					messageLi.add(field.getId() + "," + field.getDisplayName() + "不允许空值");
					return messageLi;
				}
			} else {
				messageLi.add(field.getId() + "," + field.getDisplayName() + "不允许空值");
				return messageLi;
			}
		}
		String fieldValue = ObjectUtils.toString(data.get(field.getId()).getString(), "");
		if (field.getValidateExpr() != null) {
			String mode = field.getValidateExpr().getMode();
			String value = field.getValidateExpr().getValue();
			if (StringUtils.isEmpty(mode) || mode.equals("text")) {
				if (!value.equals("true")) {
					messageLi.add(field.getDisplayName() + field.getValidateMessage());
					return messageLi;
				}
			}
			JSONObject record = BusinessDataType.convertValueDataObjectToJSONStringObject(data);
			if (!ExpressionParser.parseBoolean(record, fieldValue)) {
				messageLi.add(field.getDisplayName() + field.getValidateMessage());
				return messageLi;
			}
		}
		// FLOAT,DOUBLE,DECIMAL,SHORT,INT,LONG,NULL,STRING,DATE,TIME,TIMESTAMP,BYTES
		boolean isDataTypeNumber = false;
		isDataTypeNumber = isDataTypeNumber || field.getFieldType().equals("FLOAT");
		isDataTypeNumber = isDataTypeNumber || field.getFieldType().equals("DOUBLE");
		isDataTypeNumber = isDataTypeNumber || field.getFieldType().equals("DECIMAL");
		isDataTypeNumber = isDataTypeNumber || field.getFieldType().equals("SHORT");
		isDataTypeNumber = isDataTypeNumber || field.getFieldType().equals("INT");
		isDataTypeNumber = isDataTypeNumber || field.getFieldType().equals("LONG");
		boolean isUnLimit = StringUtils.isNotEmpty(field.getLimitOption()) && !field.getLimitOption().equals("unLimit");
		if (isDataTypeNumber && isUnLimit) {
			BigDecimal fieldValueBigDecimal = new BigDecimal(fieldValue);
			if (field.getLimitOption().equals("limitMax")) {
				BigDecimal maxValue = new BigDecimal(field.getLimitMax());
				if (maxValue.compareTo(fieldValueBigDecimal) < 0) {
					messageLi.add(field.getDisplayName() + "超出最大值" + field.getLimitMax());
				}
			} else if (field.getLimitOption().equals("limitMin")) {
				BigDecimal minValue = new BigDecimal(field.getLimitMin());
				if (fieldValueBigDecimal.compareTo(minValue) < 0) {
					messageLi.add(field.getDisplayName() + "小于最小值" + field.getLimitMin());
				}
			} else if (field.getLimitOption().equals("limitRange")) {
				BigDecimal minValue = new BigDecimal(field.getLimitMin());
				BigDecimal maxValue = new BigDecimal(field.getLimitMax());
				if (fieldValueBigDecimal.compareTo(minValue) < 0 || maxValue.compareTo(fieldValueBigDecimal) < 0) {
					messageLi.add(field.getDisplayName() + "超出范围(" + field.getLimitMin() + "~" + field.getLimitMax() + ")");
				}
			}
		} else {
			boolean isDataTypeString = false;
			isDataTypeString = isDataTypeString || field.getFieldType().equals("STRING");
			boolean isFieldLengthLimit = field.getFieldLength() != null && field.getFieldLength() > 0;
			if (isDataTypeString && isFieldLengthLimit) {
				int fieldValueLength = fieldValue.length();
				if (fieldValueLength > field.getFieldLength()) {
					messageLi.add(field.getDisplayName() + "长度超出最大值" + field.getFieldLength());
				}
			}
		}

		return messageLi;
	}

	public FormDao getFormDao() {
		return formDao;
	}

	public void setFormDao(FormDao formDao) {
		this.formDao = formDao;
	}

}
