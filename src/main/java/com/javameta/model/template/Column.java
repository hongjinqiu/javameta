package com.javameta.model.template;

import com.javameta.model.template.Editor.EditorAttribute;

public abstract class Column {
	public abstract String getName();

	public abstract Boolean getAuto();

	public abstract String getDsFieldMap();

	public abstract void setAuto(Boolean value);

	public abstract String getText();

	public abstract void setText(String value);

	public abstract Boolean getHideable();

	public abstract void setHideable(Boolean value);

	public abstract Boolean getFixReadOnly();

	public abstract void setFixReadOnly(Boolean value);

	public abstract Boolean getZeroShowEmpty();

	public abstract void setZeroShowEmpty(Boolean value);

	public abstract String getXmlName();

	public abstract void setXmlName(String xmlName);

	public abstract Integer getColSpan();

	public abstract String getColumnWidth();

	public abstract String getLabelWidth();

	private Editor getSpecEditor() {
		Editor editor = null;
		if (this instanceof AutoColumn) {
			editor = ((AutoColumn) this).getEditor();
		} else if (this instanceof BooleanColumn) {
			editor = ((BooleanColumn) this).getEditor();
		} else if (this instanceof DateColumn) {
			editor = ((DateColumn) this).getEditor();
		} else if (this instanceof DictionaryColumn) {
			editor = ((DictionaryColumn) this).getEditor();
		} else if (this instanceof IdColumn) {
		} else if (this instanceof NumberColumn) {
			editor = ((NumberColumn) this).getEditor();
		} else if (this instanceof StringColumn) {
			editor = ((StringColumn) this).getEditor();
		} else if (this instanceof TriggerColumn) {
			editor = ((TriggerColumn) this).getEditor();
		} else if (this instanceof VirtualColumn) {
		}
		return editor;
	}

	public Integer getEditorColSpan() {
		Editor editor = getSpecEditor();

		if (editor != null) {
			for (EditorAttribute editorAttribute : editor.getEditorAttribute()) {
				if (editorAttribute.getName().equals("colSpan")) {
					return Integer.parseInt(editorAttribute.getValue());
				}
			}
		}
		return 0;
	}

	public String getEditorColumnWidth() {
		Editor editor = getSpecEditor();

		if (editor != null) {
			for (EditorAttribute editorAttribute : editor.getEditorAttribute()) {
				if (editorAttribute.getName().equals("columnWidth")) {
					return editorAttribute.getValue();
				}
			}
		}
		return "";
	}

	public String getEditorLabelWidth() {
		Editor editor = getSpecEditor();

		if (editor != null) {
			for (EditorAttribute editorAttribute : editor.getEditorAttribute()) {
				if (editorAttribute.getName().equals("labelWidth")) {
					return editorAttribute.getValue();
				}
			}
		}
		return "";
	}
}
