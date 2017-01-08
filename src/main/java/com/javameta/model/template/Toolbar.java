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

import com.javameta.util.New;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{https://github.com/hongjinqiu/javameta/template}button-group"/>
 *         &lt;element ref="{https://github.com/hongjinqiu/javameta/template}button" maxOccurs="unbounded"/>
 *         &lt;element ref="{https://github.com/hongjinqiu/javameta/template}split-button"/>
 *       &lt;/choice>
 *       &lt;attGroup ref="{https://github.com/hongjinqiu/javameta/template}toolbar-attribute"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "buttonGroupOrButtonOrSplitButton"
})
@XmlRootElement(name = "toolbar")
public class Toolbar implements Serializable {
	private static final long serialVersionUID = 1L;

    @XmlElements({
        @XmlElement(name = "button", type = Button.class),
        @XmlElement(name = "button-group", type = ButtonGroup.class),
        @XmlElement(name = "split-button", type = SplitButton.class)
    })
    protected List<Object> buttonGroupOrButtonOrSplitButton;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String name;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String rendererTemplate;
    @XmlAttribute
    protected Boolean export;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String exporter;
    @XmlAttribute
    protected Boolean exportParam;
    @XmlAttribute
    protected Boolean freezedHeader;
    @XmlAttribute
    protected Boolean exportChart;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String excelChart;
    @XmlAttribute
    protected String excelChartType;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String exportTitle;
    @XmlAttribute
    protected String exportSuffix;
    
    @XmlTransient
    private String xmlName = "toolbar";
    
    public List<Button> getButton() {
    	List<Button> list = New.arrayList();
    	for (Object object: getButtonGroupOrButtonOrSplitButton()) {
    		if (object instanceof Button) {
    			list.add((Button)object);
    		}
    	}
    	return list;
    }

    /**
     * Gets the value of the buttonGroupOrButtonOrSplitButton property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the buttonGroupOrButtonOrSplitButton property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getButtonGroupOrButtonOrSplitButton().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Button }
     * {@link ButtonGroup }
     * {@link SplitButton }
     * 
     * 
     */
    public List<Object> getButtonGroupOrButtonOrSplitButton() {
        if (buttonGroupOrButtonOrSplitButton == null) {
            buttonGroupOrButtonOrSplitButton = new ArrayList<Object>();
        }
        return this.buttonGroupOrButtonOrSplitButton;
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
     * Gets the value of the export property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isExport() {
        return export;
    }
    
    public Boolean getExport() {
    	return isExport();
    }

    /**
     * Sets the value of the export property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExport(Boolean value) {
        this.export = value;
    }

    /**
     * Gets the value of the exporter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExporter() {
        return exporter;
    }

    /**
     * Sets the value of the exporter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExporter(String value) {
        this.exporter = value;
    }

    /**
     * Gets the value of the exportParam property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isExportParam() {
        return exportParam;
    }
    
    public Boolean getExportParam() {
    	return isExportParam();
    }

    /**
     * Sets the value of the exportParam property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExportParam(Boolean value) {
        this.exportParam = value;
    }

    /**
     * Gets the value of the freezedHeader property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFreezedHeader() {
        return freezedHeader;
    }
    
    public Boolean getFreezedHeader() {
    	return isFreezedHeader();
    }

    /**
     * Sets the value of the freezedHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFreezedHeader(Boolean value) {
        this.freezedHeader = value;
    }

    /**
     * Gets the value of the exportChart property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isExportChart() {
        return exportChart;
    }
    
    public Boolean getExportChart() {
    	return isExportChart();
    }

    /**
     * Sets the value of the exportChart property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExportChart(Boolean value) {
        this.exportChart = value;
    }

    /**
     * Gets the value of the excelChart property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExcelChart() {
        return excelChart;
    }

    /**
     * Sets the value of the excelChart property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExcelChart(String value) {
        this.excelChart = value;
    }

    /**
     * Gets the value of the excelChartType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExcelChartType() {
        return excelChartType;
    }

    /**
     * Sets the value of the excelChartType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExcelChartType(String value) {
        this.excelChartType = value;
    }

    /**
     * Gets the value of the exportTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExportTitle() {
        return exportTitle;
    }

    /**
     * Sets the value of the exportTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExportTitle(String value) {
        this.exportTitle = value;
    }

    /**
     * Gets the value of the exportSuffix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExportSuffix() {
        return exportSuffix;
    }

    /**
     * Sets the value of the exportSuffix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExportSuffix(String value) {
        this.exportSuffix = value;
    }

	public String getXmlName() {
		return xmlName;
	}

	public void setXmlName(String xmlName) {
		this.xmlName = xmlName;
	}

}
