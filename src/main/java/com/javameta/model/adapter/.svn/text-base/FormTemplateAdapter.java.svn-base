package com.javameta.model.adapter;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.javameta.JavametaException;
import com.javameta.model.DatasourceFactory;
import com.javameta.model.datasource.Datasource;
import com.javameta.model.datasource.Field;
import com.javameta.model.iterate.DatasourceIterator;
import com.javameta.model.iterate.IDatasourceFieldIterate;
import com.javameta.model.template.AutoColumn;
import com.javameta.model.template.Column;
import com.javameta.model.template.ColumnModel;
import com.javameta.model.template.DateColumn;
import com.javameta.model.template.DictionaryColumn;
import com.javameta.model.template.FormTemplate;
import com.javameta.model.template.NumberColumn;
import com.javameta.model.template.RelationDS;
import com.javameta.model.template.RelationDS.RelationItem;
import com.javameta.model.template.StringColumn;
import com.javameta.model.template.TriggerColumn;
import com.javameta.util.New;

public class FormTemplateAdapter implements IFormTemplateAdapter {

	@Override
	public void applyAdapter(FormTemplate formTemplate) {
		// TODO Auto-generated method stub

	}

	/**
	 * 从datasource中,找出与columnModel中AutoColumn或auto=true的Column,生成配置
	 * @param datasource
	 * @param columnModel
	 */
	public void recursionApplyColumnModel(Datasource datasource, final ColumnModel columnModel) {
		List<Column> columnLi = columnModel.getColumnList();
		for (final Column column: columnLi) {
			if ((column instanceof AutoColumn) || (column.getAuto() == null || column.getAuto())) {
				if (StringUtils.isEmpty(column.getDsFieldMap())) {
					DatasourceIterator.iterateField(datasource, new IDatasourceFieldIterate() {
						@Override
						public void iterate(Field field) {
							boolean isApplyColumn = false;
							String columnModelDataSetId = columnModel.getDataSetId();
							if (StringUtils.isEmpty(columnModelDataSetId)) {
								columnModelDataSetId = "A";
							}
							isApplyColumn = isApplyColumn || (field.getDataSetId().equals(columnModelDataSetId) && column.getName().equals(field.getId()));
							if (isApplyColumn) {
								applyColumnExtend(field, columnModel, column);
							}
						}
					});
				} else {
					String[] textLi = column.getDsFieldMap().split(".");
					if (textLi.length != 3) {
						throw new JavametaException("dataSet:" + columnModel.getDataSetId() + ", column.Name:" + column.getName() + ", dsFieldMap:" + column.getDsFieldMap() + " apply failed, dsFieldMap.len != 3");
					} else {
						String dataSourceId = textLi[0];
						final String dataSetId = textLi[1];
						final String fieldId = textLi[2];
						DatasourceFactory datasourceFactory = new DatasourceFactory();
						Datasource outSideDatasource = datasourceFactory.getDatasource(dataSourceId);
						DatasourceIterator.iterateField(outSideDatasource, new IDatasourceFieldIterate() {
							@Override
							public void iterate(Field field) {
								if (field.getDataSetId().equals(dataSetId) && field.getId().equals(fieldId)) {
									applyColumnExtend(field, columnModel, column);
								}
							}
						});
					}
				}
			}
			if (column instanceof AutoColumn) {
				AutoColumn autoColumn = (AutoColumn) column;
				if (!(autoColumn.getColumnModel() != null && autoColumn.getColumnModel().getColumnList().size() > 0)) {
					recursionApplyColumnModel(datasource, autoColumn.getColumnModel());
				}
			} else if (column instanceof StringColumn) {
				StringColumn stringColumn = (StringColumn) column;
				if (!(stringColumn.getColumnModel() != null && stringColumn.getColumnModel().getColumnList().size() > 0)) {
					recursionApplyColumnModel(datasource, stringColumn.getColumnModel());
				}
			}
		}
	}
	
	/**
	 * 用Field.RelationDS.RelationItem中的配置,生成TriggerColumn.RelationDS.RelationItem的配置,
	 * @param relationItem
	 * @return
	 */
	private RelationItem getRelationItemFromModel(Field.RelationDS.RelationItem relationItem) {
		RelationItem item = new RelationItem();
		item.setName(relationItem.getName());
		if (relationItem.getRelationExpr() != null) {
			if (item.getRelationExpr() == null) {
				item.setRelationExpr(new RelationItem.RelationExpr());
			}
			item.getRelationExpr().setMode(relationItem.getRelationExpr().getMode());
			item.getRelationExpr().setValue(relationItem.getRelationExpr().getValue());
		}
		
		if (item.getRelationConfig() == null) {
			item.setRelationConfig(new RelationItem.RelationConfig());
		}
		item.getRelationConfig().setSelectorName(relationItem.getId());
		item.getRelationConfig().setDisplayField(relationItem.getDisplayField());
		item.getRelationConfig().setValueField(relationItem.getValueField());
		item.getRelationConfig().setSelectionMode("single");
		
		return item;
	}
	
	/**
	 * FormTemplate存在对应的RelationExpr配置,则返回FormTemplate中的配置,否则,返回Field.RelationDS.RelationItem中的配置
	 * @param columnRelationItem
	 * @param item
	 * @return
	 */
	private RelationItem.RelationExpr getRelationExpr(RelationItem columnRelationItem, Field.RelationDS.RelationItem item) {
		if (columnRelationItem.getRelationExpr() == null) {
			if (item.getRelationExpr() != null) {
				RelationItem.RelationExpr relationExpr = new RelationItem.RelationExpr();
				relationExpr.setMode(item.getRelationExpr().getMode());
				relationExpr.setValue(item.getRelationExpr().getValue());
				return relationExpr;
			}
		} else {
			if (item.getRelationExpr() == null) {
				return columnRelationItem.getRelationExpr();
			} else {
				RelationItem.RelationExpr relationExpr = new RelationItem.RelationExpr();
				if (StringUtils.isNotEmpty(columnRelationItem.getRelationExpr().getMode())) {
					relationExpr.setMode(columnRelationItem.getRelationExpr().getMode());
				} else {
					relationExpr.setMode(item.getRelationExpr().getMode());
				}
				if (StringUtils.isNotEmpty(columnRelationItem.getRelationExpr().getValue())) {
					relationExpr.setValue(columnRelationItem.getRelationExpr().getValue());
				} else {
					relationExpr.setValue(item.getRelationExpr().getValue());
				}
				return relationExpr;
			}
		}
		return null;
	}
	
	/**
	 * FormTemplate存在对应的RelationConfig配置,则返回FormTemplate中的配置,否则,返回Field.RelationDS.RelationItem中的配置
	 * @param columnRelationItem
	 * @param item
	 * @return
	 */
	private RelationItem.RelationConfig getRelationConfig(RelationItem columnRelationItem, Field.RelationDS.RelationItem item) {
		RelationItem.RelationConfig relationConfig = new RelationItem.RelationConfig();
		if (columnRelationItem.getRelationConfig() != null && StringUtils.isNotEmpty(columnRelationItem.getRelationConfig().getSelectorName())) {
			relationConfig.setSelectorName(columnRelationItem.getRelationConfig().getSelectorName());
		} else {
			relationConfig.setSelectorName(item.getId());
		}
		if (columnRelationItem.getRelationConfig() != null && StringUtils.isNotEmpty(columnRelationItem.getRelationConfig().getDisplayField())) {
			relationConfig.setDisplayField(columnRelationItem.getRelationConfig().getDisplayField());
		} else {
			relationConfig.setDisplayField(item.getDisplayField());
		}
		if (columnRelationItem.getRelationConfig() != null && StringUtils.isNotEmpty(columnRelationItem.getRelationConfig().getValueField())) {
			relationConfig.setValueField(columnRelationItem.getRelationConfig().getValueField());
		} else {
			relationConfig.setValueField(item.getValueField());
		}
		if (columnRelationItem.getRelationConfig() != null && StringUtils.isNotEmpty(columnRelationItem.getRelationConfig().getSelectionMode())) {
			relationConfig.setSelectionMode(columnRelationItem.getRelationConfig().getSelectionMode());
		} else {
			relationConfig.setSelectionMode("single");
		}
		
		
		return relationConfig;
	}
	
	/**
	 * 用Field.RelationDS.RelationItem中的配置,生成TriggerColumn.RelationDS.RelationItem的配置,
	 * 如果TriggerColumn.RelationDS.RelationItem中存在部分配置,会继承并覆盖Field.RelationDS.RelationItem,
	 * @param cRelationDS
	 * @param relationDS
	 * @return
	 */
	private RelationDS getRelationDS(RelationDS cRelationDS, Field.RelationDS relationDS) {
		if (cRelationDS == null) {
			cRelationDS = new RelationDS();
		}
		
		RelationDS resultRelationDS = new RelationDS();
		
		if (cRelationDS.getRelationItem().size() == 0 && relationDS.getRelationItem().size() > 0) {
			List<RelationItem> cRelationItemLi = New.arrayList();
			for (Field.RelationDS.RelationItem item: relationDS.getRelationItem()) {
				RelationItem cRelationItem = getRelationItemFromModel(item);
				cRelationItemLi.add(cRelationItem);
			}
			resultRelationDS.setRelationItem(cRelationItemLi);
		} else if (cRelationDS.getRelationItem().size() > 0 && relationDS.getRelationItem().size() > 0) {
			List<RelationItem> cRelationItemLi = New.arrayList();
			for (Field.RelationDS.RelationItem item: relationDS.getRelationItem()) {
				RelationItem cRelationItem = new RelationItem();
				boolean isInherit = false;
				
				RelationItem columnRelationItem = new RelationItem();
				for (RelationItem subItem: cRelationDS.getRelationItem()) {
					if (subItem.getName().equals(item.getName())) {
						isInherit = true;
						columnRelationItem = subItem;
						break;
					}
				}
				if (isInherit) {
					cRelationItem.setName(columnRelationItem.getName());
					RelationItem.RelationExpr relationExpr = getRelationExpr(columnRelationItem, item);
					if (relationExpr != null) {
						cRelationItem.setRelationExpr(relationExpr);
					}
					RelationItem.RelationConfig relationConfig = getRelationConfig(columnRelationItem, item);
					if (relationConfig != null) {
						cRelationItem.setRelationConfig(relationConfig);
					}
					if (columnRelationItem.getCopyConfig().size() > 0) {
						cRelationItem.setCopyConfig(columnRelationItem.getCopyConfig());
					}
				} else {
					cRelationItem = getRelationItemFromModel(item);
				}
				cRelationItemLi.add(cRelationItem);
			}
			resultRelationDS.setRelationItem(cRelationItemLi);
		}
		
		return resultRelationDS;
	}
	
	/**
	 * auto-column变换成具体的column,并替换掉columnModel中的原column,
	 * @param field
	 * @param columnModel
	 * @param column
	 */
	private void applyColumnExtend(Field field, ColumnModel columnModel, Column column) {
		if (column instanceof AutoColumn) {
			Column oldColumn = column;
			column = getColumn(field);
			try {
				BeanUtils.copyProperties(column, oldColumn);
			} catch (Exception e) {
				throw new JavametaException(e);
			}
			column.setAuto(true);
			columnModel.replaceColumn(oldColumn, column);
		}
		if (StringUtils.isEmpty(column.getText())) {
			column.setText(field.getDisplayName());
		}
		if (column.getHideable() == null) {
			column.setHideable(field.getFixHide());
		}
		if (column.getFixReadOnly() == null) {
			column.setFixReadOnly(field.getFixReadOnly());
		}
		if (column.getZeroShowEmpty() == null) {
			column.setZeroShowEmpty(field.getZeroShowEmpty());
		}
		if (column instanceof TriggerColumn) {
			TriggerColumn triggerColumn = (TriggerColumn)column;
			triggerColumn.setRelationDS(getRelationDS(triggerColumn.getRelationDS(), field.getRelationDS()));
		} else if (column instanceof StringColumn) {
			// do nothing
		} else if (column instanceof NumberColumn) {
			// do nothing
		} else if (column instanceof DateColumn) {
			DateColumn dateColumn = (DateColumn)column;
			if (field.getFieldType().equalsIgnoreCase("date")) {
				if (StringUtils.isEmpty(dateColumn.getDisplayPattern())) {
					dateColumn.setDisplayPattern("yyyy-MM-dd");
				}
				if (StringUtils.isEmpty(dateColumn.getDbPattern())) {
					dateColumn.setDbPattern("yyyyMMdd");
				}
			} else if (field.getFieldType().equalsIgnoreCase("time")) {
				if (StringUtils.isEmpty(dateColumn.getDisplayPattern())) {
					dateColumn.setDisplayPattern("HH:mm:ss");
				}
				if (StringUtils.isEmpty(dateColumn.getDbPattern())) {
					dateColumn.setDbPattern("HHmmss");
				}
			} else if (field.getFieldType().equalsIgnoreCase("timestamp")) {
				if (StringUtils.isEmpty(dateColumn.getDisplayPattern())) {
					dateColumn.setDisplayPattern("yyyy-MM-dd HH:mm:ss");
				}
				if (StringUtils.isEmpty(dateColumn.getDbPattern())) {
					dateColumn.setDbPattern("yyyyMMddHHmmss");
				}
			}
		} else if (column instanceof DictionaryColumn) {
			DictionaryColumn dictionaryColumn = (DictionaryColumn)column;
			if (StringUtils.isEmpty(dictionaryColumn.getDictionary())) {
				dictionaryColumn.setDictionary(field.getDictionary());
			}
		}
	}
	
	private Column getColumn(Field field) {
		boolean isRelationField = field.isRelationField();
		boolean isStringField = field.getFieldType().equalsIgnoreCase("string");
		isStringField = isStringField || field.getFieldType().equalsIgnoreCase("null");
		boolean isDateField = field.getFieldType().equalsIgnoreCase("date");
		isDateField = isDateField || field.getFieldType().equalsIgnoreCase("time");
		isDateField = isDateField || field.getFieldType().equalsIgnoreCase("timestamp");
		boolean isDictionaryField = StringUtils.isNotEmpty(field.getDictionary());
		boolean isNumberField = field.getFieldType().equalsIgnoreCase("short");
		isNumberField = isNumberField || field.getFieldType().equalsIgnoreCase("int");
		isNumberField = isNumberField || field.getFieldType().equalsIgnoreCase("long");
		isNumberField = isNumberField || field.getFieldType().equalsIgnoreCase("float");
		isNumberField = isNumberField || field.getFieldType().equalsIgnoreCase("double");
		isNumberField = isNumberField || field.getFieldType().equalsIgnoreCase("decimal");
		
		if (isRelationField) {
			return new TriggerColumn();
		} else if (isStringField) {
			return new StringColumn();
		} else if (isDateField) {
			return new DateColumn();
		} else if (isDictionaryField) {
			return new DictionaryColumn();
		} else if (isNumberField) {
			return new NumberColumn();
		}
		return new StringColumn();
	}
}
