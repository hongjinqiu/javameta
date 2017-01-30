package com.javameta.web.form.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javameta.expression.ExpressionParser;
import com.javameta.model.BusinessDataType;
import com.javameta.model.DatasourceFactory;
import com.javameta.model.ValueBusinessObject;
import com.javameta.model.datasource.Datasource;
import com.javameta.model.datasource.DetailData;
import com.javameta.model.datasource.Field;
import com.javameta.model.handler.DiffDataRow;
import com.javameta.model.iterate.DatasourceIterator;
import com.javameta.model.iterate.IDatasourceFieldDataIterate;
import com.javameta.model.iterate.IDatasourceFieldIterate;
import com.javameta.util.New;
import com.javameta.value.Value;
import com.javameta.value.ValueNull;
import com.javameta.web.support.DaoSupport;
import com.javameta.web.support.ServiceSupport;

@Service
@Transactional
public class FormSaveService extends ServiceSupport {
	private DaoSupport daoSupport;

	//	func (o FinanceService) SaveData(sessionId int, dataSource DataSource, bo *map[string]interface{}) *[]DiffDataRow {
	public List<DiffDataRow> saveData(Datasource datasource, ValueBusinessObject valueBo) {
		//		modelTemplateFactory := ModelTemplateFactory{}
		DatasourceFactory datasourceFactory = new DatasourceFactory();
		int id = valueBo.getMasterData().get("id").getInt();// TODO 需要验证空,
		// 主数据集和分录数据校验
		//		message := o.validateBO(sessionId, dataSource, (*bo))

		// TODO
		return null;
	}

	//	func (o FinanceService) validateBO(sessionId int, dataSource DataSource, bo map[string]interface{}) string {
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
	
//	func (o FinanceService) validateBODuplicate(sessionId int, dataSource DataSource, bo map[string]interface{}) string {
	private String validateBODuplicate(Datasource datasource, ValueBusinessObject valueBo) {
		String result = "";
//		result += o.validateMasterDataDuplicate(sessionId, dataSource, bo)
/*
		if result != "" {
			result += "<br />"
		}
		result += o.validateDetailDataDuplicate(sessionId, dataSource, bo)
		return result
*/
		
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
				sb.append(" and {fieldName}=:{fieldName} ".replace("{fieldName}", idField.getCalcFieldName()));
				queryParam.put(idField.getCalcFieldName(), strId);
			}
			sb.append(" limit 1 ");
			int count = this.daoSupport.getNamedParameterJdbcTemplate().queryForInt(sb.toString(), queryParam);
			if (count > 0) {
				message = StringUtils.join(andFieldNameLi, "+") + "不允许重复";
			}
		}
		
		return message;
	}
	
//	func (o FinanceService) validateDetailDataDuplicate(sessionId int, dataSource DataSource, bo map[string]interface{}) string {
	private String validateDetailDataDuplicate(Datasource datasource, ValueBusinessObject valueBo) {
//		messageLi := []string{}
		List<String> messageLi = New.arrayList();
		/*
		 * duplicateFieldIdLi := []string{}
	duplicateFieldNameLi := []string{}
		 */
		List<String> duplicateFieldIdLi = New.arrayList();
		List<String> duplicateFieldNameLi = New.arrayList();
		DatasourceIterator.iterateField(datasource, new IDatasourceFieldIterate() {
			@Override
			public void iterate(Field field) {
				
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
		String fieldValue = data.get(field.getId()).getString();
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

	public void setDaoSupport(DaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}
}
