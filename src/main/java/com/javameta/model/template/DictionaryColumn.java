//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.12.16 at 04:39:16 PM CST 
//


package com.javameta.model.template;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.StringUtils;

import com.javameta.model.template.Editor.EditorAttribute;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{https://github.com/hongjinqiu/javameta/template}editor" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{https://github.com/hongjinqiu/javameta/template}column-attributes"/>
 *       &lt;attribute name="dictionary" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="complex" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "editor"
})
@XmlRootElement(name = "dictionary-column")
public class DictionaryColumn extends Column implements Serializable {
	private static final long serialVersionUID = 1L;

    protected Editor editor;
    @XmlAttribute(required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String dictionary;
    @XmlAttribute
    protected Boolean complex;
    @XmlAttribute(required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String name;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String text;
    @XmlAttribute
    protected String align;
    @XmlAttribute
    protected Boolean graggable;
    @XmlAttribute
    protected Boolean groupable;
    @XmlAttribute
    protected Boolean hideable;
    @XmlAttribute
    protected Boolean editable;
    @XmlAttribute
    protected Boolean sortable;
    @XmlAttribute
    protected Boolean comparable;
    @XmlAttribute
    protected Boolean locked;
    @XmlAttribute
    protected Boolean auto;
    @XmlAttribute
    protected Integer colSpan;
    @XmlAttribute
    protected Integer rowSpan;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String width;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String fieldWidth;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String fieldHeight;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String fieldCls;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String columnWidth;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String labelWidth;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String excelWidth;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String renderer;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String rendererTemplate;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String summaryText;
    @XmlAttribute
    protected String summaryType;
    @XmlAttribute
    protected String cycle;
    @XmlAttribute
    protected Boolean exported;
    @XmlAttribute
    protected String dsFieldMap;
    @XmlAttribute
    protected Boolean readOnly;
    @XmlAttribute
    protected Boolean fixReadOnly;
    @XmlAttribute
    protected Boolean zeroShowEmpty;
    
    @XmlTransient
    private String xmlName = "dictionary-column";
    
    public String getEditorRendererTemplate() {
    	if (editor != null) {
    		for (EditorAttribute editorAttribute: editor.getEditorAttribute()) {
    			if (editorAttribute.getName().equals("rendererTemplate")) {
    				return editorAttribute.getValue();
    			}
    		}
    	}
    	return "";
    }
    
    public String getEditorFieldCls() {
    	if (editor != null) {
    		for (EditorAttribute editorAttribute: editor.getEditorAttribute()) {
    			if (editorAttribute.getName().equals("fieldCls")) {
    				return editorAttribute.getValue();
    			}
    		}
    	}
    	return "";
    }
    
	public String getEditorStyle() {
		if (editor != null) {
    		for (EditorAttribute editorAttribute: editor.getEditorAttribute()) {
    			if (editorAttribute.getName().equals("style")) {
    				return editorAttribute.getValue();
    			}
    		}
    	}
    	return "";
	}

    /**
     * Gets the value of the editor property.
     * 
     * @return
     *     possible object is
     *     {@link Editor }
     *     
     */
    public Editor getEditor() {
        return editor;
    }

    /**
     * Sets the value of the editor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Editor }
     *     
     */
    public void setEditor(Editor value) {
        this.editor = value;
    }

    /**
     * Gets the value of the dictionary property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDictionary() {
        return dictionary;
    }

    /**
     * Sets the value of the dictionary property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDictionary(String value) {
        this.dictionary = value;
    }

    /**
     * Gets the value of the complex property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isComplex() {
        return complex;
    }

	public Boolean getComplex() {
        return complex;
    }

    /**
     * Sets the value of the complex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setComplex(Boolean value) {
        this.complex = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText(String value) {
        this.text = value;
    }

    /**
     * Gets the value of the align property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlign() {
        return align;
    }

    /**
     * Sets the value of the align property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlign(String value) {
        this.align = value;
    }

    /**
     * Gets the value of the graggable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isGraggable() {
        return graggable;
    }

	public Boolean getGraggable() {
        return graggable;
    }

    /**
     * Sets the value of the graggable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setGraggable(Boolean value) {
        this.graggable = value;
    }

    /**
     * Gets the value of the groupable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isGroupable() {
        return groupable;
    }

	public Boolean getGroupable() {
        return groupable;
    }

    /**
     * Sets the value of the groupable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setGroupable(Boolean value) {
        this.groupable = value;
    }

    /**
     * Gets the value of the hideable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHideable() {
        return hideable;
    }

	public Boolean getHideable() {
        return hideable;
    }

    /**
     * Sets the value of the hideable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHideable(Boolean value) {
        this.hideable = value;
    }

    /**
     * Gets the value of the editable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEditable() {
        return editable;
    }

	public Boolean getEditable() {
        return editable;
    }

    /**
     * Sets the value of the editable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEditable(Boolean value) {
        this.editable = value;
    }

    /**
     * Gets the value of the sortable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSortable() {
        return sortable;
    }

	public Boolean getSortable() {
        return sortable;
    }

    /**
     * Sets the value of the sortable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSortable(Boolean value) {
        this.sortable = value;
    }

    /**
     * Gets the value of the comparable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isComparable() {
        return comparable;
    }

	public Boolean getComparable() {
        return comparable;
    }

    /**
     * Sets the value of the comparable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setComparable(Boolean value) {
        this.comparable = value;
    }

    /**
     * Gets the value of the locked property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLocked() {
        return locked;
    }

	public Boolean getLocked() {
        return locked;
    }

    /**
     * Sets the value of the locked property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLocked(Boolean value) {
        this.locked = value;
    }

    /**
     * Gets the value of the auto property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAuto() {
        return auto;
    }

	public Boolean getAuto() {
        return auto;
    }

    /**
     * Sets the value of the auto property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAuto(Boolean value) {
        this.auto = value;
    }

    /**
     * Gets the value of the colSpan property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getColSpan() {
        return colSpan;
    }

    /**
     * Sets the value of the colSpan property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setColSpan(Integer value) {
        this.colSpan = value;
    }

    /**
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWidth(String value) {
        this.width = value;
    }

    /**
     * Gets the value of the fieldWidth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldWidth() {
        return fieldWidth;
    }

    /**
     * Sets the value of the fieldWidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldWidth(String value) {
        this.fieldWidth = value;
    }

    /**
     * Gets the value of the fieldHeight property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldHeight() {
        return fieldHeight;
    }

    /**
     * Sets the value of the fieldHeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldHeight(String value) {
        this.fieldHeight = value;
    }

    /**
     * Gets the value of the fieldCls property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldCls() {
        return fieldCls;
    }

    /**
     * Sets the value of the fieldCls property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldCls(String value) {
        this.fieldCls = value;
    }

    /**
     * Gets the value of the columnWidth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColumnWidth() {
        return columnWidth;
    }

    /**
     * Sets the value of the columnWidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColumnWidth(String value) {
        this.columnWidth = value;
    }

    /**
     * Gets the value of the labelWidth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabelWidth() {
        return labelWidth;
    }

    /**
     * Sets the value of the labelWidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabelWidth(String value) {
        this.labelWidth = value;
    }

    /**
     * Gets the value of the excelWidth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExcelWidth() {
        return excelWidth;
    }

    /**
     * Sets the value of the excelWidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExcelWidth(String value) {
        this.excelWidth = value;
    }

    /**
     * Gets the value of the renderer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRenderer() {
        return renderer;
    }

    /**
     * Sets the value of the renderer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRenderer(String value) {
        this.renderer = value;
    }

    /**
     * Gets the value of the rendererTemplate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRendererTemplate() {
        return rendererTemplate;
    }

    /**
     * Sets the value of the rendererTemplate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRendererTemplate(String value) {
        this.rendererTemplate = value;
    }

    /**
     * Gets the value of the summaryText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSummaryText() {
        return summaryText;
    }

    /**
     * Sets the value of the summaryText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSummaryText(String value) {
        this.summaryText = value;
    }

    /**
     * Gets the value of the summaryType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSummaryType() {
        return summaryType;
    }

    /**
     * Sets the value of the summaryType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSummaryType(String value) {
        this.summaryType = value;
    }

    /**
     * Gets the value of the cycle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCycle() {
        return cycle;
    }

    /**
     * Sets the value of the cycle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCycle(String value) {
        this.cycle = value;
    }

    /**
     * Gets the value of the exported property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isExported() {
        return exported;
    }
    
    public Boolean getExported() {
    	return isExported();
    }

    /**
     * Sets the value of the exported property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExported(Boolean value) {
        this.exported = value;
    }

    /**
     * Gets the value of the dsFieldMap property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDsFieldMap() {
        return dsFieldMap;
    }

    /**
     * Sets the value of the dsFieldMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDsFieldMap(String value) {
        this.dsFieldMap = value;
    }

    /**
     * Gets the value of the readOnly property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isReadOnly() {
        return readOnly;
    }
    
    public Boolean getReadOnly() {
    	return isReadOnly();
    }

    /**
     * Sets the value of the readOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReadOnly(Boolean value) {
        this.readOnly = value;
    }

    /**
     * Gets the value of the fixReadOnly property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFixReadOnly() {
        return fixReadOnly;
    }
    
    public Boolean getFixReadOnly() {
    	return isFixReadOnly();
    }

    /**
     * Sets the value of the fixReadOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFixReadOnly(Boolean value) {
        this.fixReadOnly = value;
    }

    /**
     * Gets the value of the zeroShowEmpty property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isZeroShowEmpty() {
        return zeroShowEmpty;
    }
    
    public Boolean getZeroShowEmpty() {
    	return isZeroShowEmpty();
    }

    /**
     * Sets the value of the zeroShowEmpty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setZeroShowEmpty(Boolean value) {
        this.zeroShowEmpty = value;
    }

	public String getXmlName() {
		return xmlName;
	}

	public void setXmlName(String xmlName) {
		this.xmlName = xmlName;
	}

	public Integer getRowSpan() {
		return rowSpan;
	}

	public void setRowSpan(Integer rowSpan) {
		this.rowSpan = rowSpan;
	}
}
