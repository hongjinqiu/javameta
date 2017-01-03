package com.javameta.model.iterate;

import java.util.List;

import com.javameta.model.template.AutoColumn;
import com.javameta.model.template.BooleanColumn;
import com.javameta.model.template.Button;
import com.javameta.model.template.Column;
import com.javameta.model.template.ColumnModel;
import com.javameta.model.template.DataProvider;
import com.javameta.model.template.DateColumn;
import com.javameta.model.template.DictionaryColumn;
import com.javameta.model.template.FormTemplate;
import com.javameta.model.template.IdColumn;
import com.javameta.model.template.NumberColumn;
import com.javameta.model.template.QueryParameters;
import com.javameta.model.template.QueryParameters.QueryParameter;
import com.javameta.model.template.StringColumn;
import com.javameta.model.template.Toolbar;
import com.javameta.model.template.TriggerColumn;
import com.javameta.model.template.VirtualColumn;
import com.javameta.model.template.VirtualColumn.Buttons;
import com.javameta.util.New;

public class FormTemplateIterator {
	public static void recursionGetColumnItem(ColumnModel columnModel, List<Column> columnList) {
		List<Column> modelColumnList = columnModel.getColumnList();
		if (modelColumnList != null && modelColumnList.size() > 0) {
			for (Column modelColumn : modelColumnList) {
				if (modelColumn instanceof IdColumn) {
				} else if (modelColumn instanceof DictionaryColumn) {
				} else if (modelColumn instanceof StringColumn) {
					ColumnModel subColumnModel = ((StringColumn) modelColumn).getColumnModel();
					if (subColumnModel != null) {
						recursionGetColumnItem(subColumnModel, columnList);
					}
				} else if (modelColumn instanceof VirtualColumn) {
				} else if (modelColumn instanceof BooleanColumn) {
				} else if (modelColumn instanceof DateColumn) {
				} else if (modelColumn instanceof AutoColumn) {
					ColumnModel subColumnModel = ((AutoColumn) modelColumn).getColumnModel();
					if (subColumnModel != null) {
						recursionGetColumnItem(subColumnModel, columnList);
					}
				} else if (modelColumn instanceof TriggerColumn) {
				} else if (modelColumn instanceof NumberColumn) {
				}
				columnList.add(modelColumn);
			}
		}
	}

	public static void iterateFormTemplateColumn(FormTemplate formTemplate, IFormTemplateColumnIterate iterate) {
		for (Object object : formTemplate.getToolbarOrDataProviderOrColumnModel()) {
			if (object instanceof ColumnModel) {
				List<Column> columnList = New.arrayList();
				recursionGetColumnItem((ColumnModel) object, columnList);
				for (Column column : columnList) {
					iterate.iterate(column);
				}
			}
		}
	}

	public static void iterateFormTemplateColumnModel(FormTemplate formTemplate, IFormTemplateColumnModelIterate iterate) {
		for (Object object : formTemplate.getToolbarOrDataProviderOrColumnModel()) {
			if (object instanceof ColumnModel) {
				iterate.iterate((ColumnModel) object);
			}
		}
	}

	/**
	 * 迭代
	 * 1.formTemplate->toolbar
	 * 2.formTemplate->columnModel->toolbar
	 * 3.formTemplate->columnModel->editorToolbar
	 * 4.formTemplate->columnModel->column->VirtualColumn
	 * @param formTemplate
	 * @param iterate
	 */
	public static void iterateFormTemplateButton(FormTemplate formTemplate, IFormTemplateButtonIterate iterate) {
		for (Object object : formTemplate.getToolbarOrDataProviderOrColumnModel()) {
			if (object instanceof Toolbar) {
				Toolbar toolbar = (Toolbar) object;
				List<Object> buttonLi = toolbar.getButtonGroupOrButtonOrSplitButton();
				if (buttonLi != null && buttonLi.size() > 0) {
					for (Object buttonObj : buttonLi) {
						if (buttonObj instanceof Button) {
							iterate.iterate(toolbar, null, (Button) buttonObj);
						}
					}
				}
			} else if (object instanceof ColumnModel) {
				ColumnModel columnModel = (ColumnModel) object;
				Toolbar toolbar = columnModel.getToolbar();
				if (toolbar != null) {
					List<Object> buttonLi = toolbar.getButtonGroupOrButtonOrSplitButton();
					if (buttonLi != null && buttonLi.size() > 0) {
						for (Object buttonObj : buttonLi) {
							if (buttonObj instanceof Button) {
								iterate.iterate(toolbar, columnModel, (Button) buttonObj);
							}
						}
					}
				}
				Toolbar editorToolbar = columnModel.getEditorToolbar();
				if (editorToolbar != null) {
					List<Object> buttonLi = editorToolbar.getButtonGroupOrButtonOrSplitButton();
					if (buttonLi != null && buttonLi.size() > 0) {
						for (Object buttonObj : buttonLi) {
							if (buttonObj instanceof Button) {
								iterate.iterate(editorToolbar, columnModel, (Button) buttonObj);
							}
						}
					}
				}
				List<Column> columnLi = columnModel.getColumnList();
				if (columnLi != null && columnLi.size() > 0) {
					for (Column column : columnLi) {
						if (column instanceof VirtualColumn) {
							VirtualColumn virtualColumn = (VirtualColumn) column;
							Buttons buttons = virtualColumn.getButtons();
							if (buttons != null && buttons.getButton() != null) {
								for (Button button : buttons.getButton()) {
									iterate.iterate(null, columnModel, button);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static void iterateFormTemplateDataProvider(FormTemplate formTemplate, IFormTemplateDataProviderIterate iterate) {
		for (Object object : formTemplate.getToolbarOrDataProviderOrColumnModel()) {
			if (object instanceof DataProvider) {
				iterate.iterate((DataProvider) object);
			}
		}
	}
	
	public static void iterateFormTemplateQueryParameter(DataProvider dataProvider, IFormTemplateQueryParameterIterate iterate) {
		QueryParameters queryParameters = dataProvider.getQueryParameters();
		if (queryParameters != null) {
			List<Object> list = queryParameters.getFixedParameterOrQueryParameter();
			if (list != null && list.size() > 0) {
				for (Object object: list) {
					if (object instanceof QueryParameter) {
						iterate.iterate(dataProvider, (QueryParameter)object);
					}
				}
			}
		}
	}
}
