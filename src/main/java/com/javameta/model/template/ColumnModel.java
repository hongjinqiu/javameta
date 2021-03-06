//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.12.16 at 04:39:16 PM CST 
//


package com.javameta.model.template;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


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
 *         &lt;element ref="{https://github.com/hongjinqiu/javameta/template}toolbar" minOccurs="0"/>
 *         &lt;element ref="{https://github.com/hongjinqiu/javameta/template}editor-toolbar" minOccurs="0"/>
 *         &lt;element ref="{https://github.com/hongjinqiu/javameta/template}checkbox-column" minOccurs="0"/>
 *         &lt;element ref="{https://github.com/hongjinqiu/javameta/template}id-column" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element ref="{https://github.com/hongjinqiu/javameta/template}auto-column"/>
 *           &lt;element ref="{https://github.com/hongjinqiu/javameta/template}string-column"/>
 *           &lt;element ref="{https://github.com/hongjinqiu/javameta/template}number-column"/>
 *           &lt;element ref="{https://github.com/hongjinqiu/javameta/template}date-column"/>
 *           &lt;element ref="{https://github.com/hongjinqiu/javameta/template}boolean-column"/>
 *           &lt;element ref="{https://github.com/hongjinqiu/javameta/template}dictionary-column"/>
 *           &lt;element ref="{https://github.com/hongjinqiu/javameta/template}virtual-column"/>
 *           &lt;element ref="{https://github.com/hongjinqiu/javameta/template}trigger-column"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{https://github.com/hongjinqiu/javameta/template}column-model-attribute"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "toolbar",
    "editorToolbar",
    "checkboxColumn",
    "idColumn",
    "columnList"
})
@XmlRootElement(name = "column-model")
public class ColumnModel implements Serializable {
	private static final long serialVersionUID = 1L;

    protected Toolbar toolbar;
    @XmlElement(name = "editor-toolbar")
    protected EditorToolbar editorToolbar;
    @XmlElement(name = "checkbox-column")
    protected CheckboxColumn checkboxColumn;
    @XmlElement(name = "id-column")
    protected IdColumn idColumn;
    @XmlElements({
        @XmlElement(name = "virtual-column", type = VirtualColumn.class),
        @XmlElement(name = "date-column", type = DateColumn.class),
        @XmlElement(name = "trigger-column", type = TriggerColumn.class),
        @XmlElement(name = "dictionary-column", type = DictionaryColumn.class),
        @XmlElement(name = "string-column", type = StringColumn.class),
        @XmlElement(name = "boolean-column", type = BooleanColumn.class),
        @XmlElement(name = "auto-column", type = AutoColumn.class),
        @XmlElement(name = "number-column", type = NumberColumn.class)
    })
    protected List<Column> columnList;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String name;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String text;
    @XmlAttribute
    protected Boolean autoLoad;
    @XmlAttribute
    protected Boolean summaryLoad;
    @XmlAttribute
    protected Boolean summaryStat;
    @XmlAttribute
    protected Boolean summation;
    @XmlAttribute
    protected Boolean groupSummation;
    @XmlAttribute
    protected Boolean groupMerge;
    @XmlAttribute
    protected Boolean showGroupFilter;
    @XmlAttribute
    protected Boolean showAggregationFilter;
    @XmlAttribute
    protected Boolean autoRowHeight;
    @XmlAttribute
    protected Boolean nowrap;
    @XmlAttribute
    protected Boolean rownumber;
    @XmlAttribute
    protected String selectionMode;
    @XmlAttribute
    protected Boolean showClearBtn;
    @XmlAttribute
    protected Boolean selectionSupport;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String groupField;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String sqlOrderBy;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String saveUrl;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String deleteUrl;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String storeIntercept;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String recordIntercept;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String selectionTemplate;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String selectionTitle;
    @XmlAttribute
    protected String displayMode;
    @XmlAttribute
    protected String editorDisplayMode;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String dataSetId;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String dataProvider;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String rendererTemplate;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String editorRendererTemplate;
    @XmlAttribute
    protected Integer colSpan;
    @XmlAttribute
    protected Integer rowSpan;
    
    @XmlTransient
    private String xmlName = "column-model";
    
    /**
     * 把columnList中的column换掉,一般是替换auto-column,用于新生成的column,替换掉auto-column,
     * @param oldColumn
     * @param column
     */
    public void replaceColumn(Column oldColumn, Column column) {
    	for (int i = 0; i < columnList.size(); i++) {
    		if (columnList.get(i) == oldColumn) {
    			columnList.set(i, column);
    		}
    	}
    }

    /**
     * Gets the value of the toolbar property.
     * 
     * @return
     *     possible object is
     *     {@link Toolbar }
     *     
     */
    public Toolbar getToolbar() {
        return toolbar;
    }

    /**
     * Sets the value of the toolbar property.
     * 
     * @param value
     *     allowed object is
     *     {@link Toolbar }
     *     
     */
    public void setToolbar(Toolbar value) {
        this.toolbar = value;
    }

    /**
     * Gets the value of the editorToolbar property.
     * 
     * @return
     *     possible object is
     *     {@link EditorToolbar }
     *     
     */
    public EditorToolbar getEditorToolbar() {
        return editorToolbar;
    }

    /**
     * Sets the value of the editorToolbar property.
     * 
     * @param value
     *     allowed object is
     *     {@link EditorToolbar }
     *     
     */
    public void setEditorToolbar(EditorToolbar value) {
        this.editorToolbar = value;
    }

    /**
     * Gets the value of the checkboxColumn property.
     * 
     * @return
     *     possible object is
     *     {@link CheckboxColumn }
     *     
     */
    public CheckboxColumn getCheckboxColumn() {
        return checkboxColumn;
    }

    /**
     * Sets the value of the checkboxColumn property.
     * 
     * @param value
     *     allowed object is
     *     {@link CheckboxColumn }
     *     
     */
    public void setCheckboxColumn(CheckboxColumn value) {
        this.checkboxColumn = value;
    }

    /**
     * Gets the value of the idColumn property.
     * 
     * @return
     *     possible object is
     *     {@link IdColumn }
     *     
     */
    public IdColumn getIdColumn() {
        return idColumn;
    }

    /**
     * Sets the value of the idColumn property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdColumn }
     *     
     */
    public void setIdColumn(IdColumn value) {
        this.idColumn = value;
    }

    /**
     * Gets the value of the columnList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the columnList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAutoColumnOrStringColumnOrNumberColumn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VirtualColumn }
     * {@link DateColumn }
     * {@link TriggerColumn }
     * {@link DictionaryColumn }
     * {@link StringColumn }
     * {@link BooleanColumn }
     * {@link AutoColumn }
     * {@link NumberColumn }
     * 
     * 
     */
    public List<Column> getColumnList() {
        if (columnList == null) {
        	columnList = new ArrayList<Column>();
        }
        return this.columnList;
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
     * Gets the value of the autoLoad property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAutoLoad() {
        return autoLoad;
    }

	public Boolean getAutoLoad() {
        return autoLoad;
    }

    /**
     * Sets the value of the autoLoad property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAutoLoad(Boolean value) {
        this.autoLoad = value;
    }

    /**
     * Gets the value of the summaryLoad property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSummaryLoad() {
        return summaryLoad;
    }

	public Boolean getSummaryLoad() {
        return summaryLoad;
    }

    /**
     * Sets the value of the summaryLoad property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSummaryLoad(Boolean value) {
        this.summaryLoad = value;
    }

    /**
     * Gets the value of the summaryStat property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSummaryStat() {
        return summaryStat;
    }

	public Boolean getSummaryStat() {
        return summaryStat;
    }

    /**
     * Sets the value of the summaryStat property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSummaryStat(Boolean value) {
        this.summaryStat = value;
    }

    /**
     * Gets the value of the summation property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSummation() {
        return summation;
    }

	public Boolean getSummation() {
        return summation;
    }

    /**
     * Sets the value of the summation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSummation(Boolean value) {
        this.summation = value;
    }

    /**
     * Gets the value of the groupSummation property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isGroupSummation() {
        return groupSummation;
    }

	public Boolean getGroupSummation() {
        return groupSummation;
    }

    /**
     * Sets the value of the groupSummation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setGroupSummation(Boolean value) {
        this.groupSummation = value;
    }

    /**
     * Gets the value of the groupMerge property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isGroupMerge() {
        return groupMerge;
    }

	public Boolean getGroupMerge() {
        return groupMerge;
    }

    /**
     * Sets the value of the groupMerge property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setGroupMerge(Boolean value) {
        this.groupMerge = value;
    }

    /**
     * Gets the value of the showGroupFilter property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isShowGroupFilter() {
        return showGroupFilter;
    }

	public Boolean getShowGroupFilter() {
        return showGroupFilter;
    }

    /**
     * Sets the value of the showGroupFilter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setShowGroupFilter(Boolean value) {
        this.showGroupFilter = value;
    }

    /**
     * Gets the value of the showAggregationFilter property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isShowAggregationFilter() {
        return showAggregationFilter;
    }

	public Boolean getShowAggregationFilter() {
        return showAggregationFilter;
    }

    /**
     * Sets the value of the showAggregationFilter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setShowAggregationFilter(Boolean value) {
        this.showAggregationFilter = value;
    }

    /**
     * Gets the value of the autoRowHeight property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAutoRowHeight() {
        return autoRowHeight;
    }

	public Boolean getAutoRowHeight() {
        return autoRowHeight;
    }

    /**
     * Sets the value of the autoRowHeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAutoRowHeight(Boolean value) {
        this.autoRowHeight = value;
    }

    /**
     * Gets the value of the nowrap property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNowrap() {
        return nowrap;
    }

	public Boolean getNowrap() {
        return nowrap;
    }

    /**
     * Sets the value of the nowrap property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNowrap(Boolean value) {
        this.nowrap = value;
    }

    /**
     * Gets the value of the rownumber property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRownumber() {
        return rownumber;
    }

	public Boolean getRownumber() {
        return rownumber;
    }

    /**
     * Sets the value of the rownumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRownumber(Boolean value) {
        this.rownumber = value;
    }

    /**
     * Gets the value of the selectionMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelectionMode() {
        return selectionMode;
    }

    /**
     * Sets the value of the selectionMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelectionMode(String value) {
        this.selectionMode = value;
    }

    /**
     * Gets the value of the showClearBtn property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isShowClearBtn() {
        return showClearBtn;
    }
    
    public Boolean getShowClearBtn() {
    	return isShowClearBtn();
    }

    /**
     * Sets the value of the showClearBtn property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setShowClearBtn(Boolean value) {
        this.showClearBtn = value;
    }

    /**
     * Gets the value of the selectionSupport property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSelectionSupport() {
        return selectionSupport;
    }
    
    public Boolean getSelectionSupport() {
    	return isSelectionSupport();
    }

    /**
     * Sets the value of the selectionSupport property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSelectionSupport(Boolean value) {
        this.selectionSupport = value;
    }

    /**
     * Gets the value of the groupField property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupField() {
        return groupField;
    }

    /**
     * Sets the value of the groupField property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupField(String value) {
        this.groupField = value;
    }

    /**
     * Gets the value of the sqlOrderBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSqlOrderBy() {
        return sqlOrderBy;
    }

    /**
     * Sets the value of the sqlOrderBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSqlOrderBy(String value) {
        this.sqlOrderBy = value;
    }

    /**
     * Gets the value of the saveUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSaveUrl() {
        return saveUrl;
    }

    /**
     * Sets the value of the saveUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSaveUrl(String value) {
        this.saveUrl = value;
    }

    /**
     * Gets the value of the deleteUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeleteUrl() {
        return deleteUrl;
    }

    /**
     * Sets the value of the deleteUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeleteUrl(String value) {
        this.deleteUrl = value;
    }

    /**
     * Gets the value of the storeIntercept property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStoreIntercept() {
        return storeIntercept;
    }

    /**
     * Sets the value of the storeIntercept property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStoreIntercept(String value) {
        this.storeIntercept = value;
    }

    /**
     * Gets the value of the recordIntercept property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordIntercept() {
        return recordIntercept;
    }

    /**
     * Sets the value of the recordIntercept property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordIntercept(String value) {
        this.recordIntercept = value;
    }

    /**
     * Gets the value of the selectionTemplate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelectionTemplate() {
        return selectionTemplate;
    }

    /**
     * Sets the value of the selectionTemplate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelectionTemplate(String value) {
        this.selectionTemplate = value;
    }

    /**
     * Gets the value of the selectionTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelectionTitle() {
        return selectionTitle;
    }

    /**
     * Sets the value of the selectionTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelectionTitle(String value) {
        this.selectionTitle = value;
    }

    /**
     * Gets the value of the displayMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayMode() {
        return displayMode;
    }

    /**
     * Sets the value of the displayMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayMode(String value) {
        this.displayMode = value;
    }

    /**
     * Gets the value of the editorDisplayMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEditorDisplayMode() {
        return editorDisplayMode;
    }

    /**
     * Sets the value of the editorDisplayMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEditorDisplayMode(String value) {
        this.editorDisplayMode = value;
    }

    /**
     * Gets the value of the dataSetId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataSetId() {
        return dataSetId;
    }

    /**
     * Sets the value of the dataSetId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataSetId(String value) {
        this.dataSetId = value;
    }

    /**
     * Gets the value of the dataProvider property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataProvider() {
        return dataProvider;
    }

    /**
     * Sets the value of the dataProvider property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataProvider(String value) {
        this.dataProvider = value;
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
     * Gets the value of the editorRendererTemplate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEditorRendererTemplate() {
        return editorRendererTemplate;
    }

    /**
     * Sets the value of the editorRendererTemplate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEditorRendererTemplate(String value) {
        this.editorRendererTemplate = value;
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

	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
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
