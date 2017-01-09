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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

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
 *       &lt;sequence>
 *         &lt;element ref="{https://github.com/hongjinqiu/javameta/template}id" minOccurs="0"/>
 *         &lt;element ref="{https://github.com/hongjinqiu/javameta/template}cookie" minOccurs="0"/>
 *         &lt;element ref="{https://github.com/hongjinqiu/javameta/template}data-source-model-id" minOccurs="0"/>
 *         &lt;element ref="{https://github.com/hongjinqiu/javameta/template}adapter" minOccurs="0"/>
 *         &lt;element ref="{https://github.com/hongjinqiu/javameta/template}description" minOccurs="0"/>
 *         &lt;element ref="{https://github.com/hongjinqiu/javameta/template}scripts" minOccurs="0"/>
 *         &lt;element ref="{https://github.com/hongjinqiu/javameta/template}view-template" minOccurs="0"/>
 *         &lt;element ref="{https://github.com/hongjinqiu/javameta/template}security" minOccurs="0"/>
 *         &lt;element ref="{https://github.com/hongjinqiu/javameta/template}after-query-data" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element ref="{https://github.com/hongjinqiu/javameta/template}toolbar" minOccurs="0"/>
 *           &lt;element ref="{https://github.com/hongjinqiu/javameta/template}data-provider"/>
 *           &lt;element ref="{https://github.com/hongjinqiu/javameta/template}column-model" maxOccurs="unbounded"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "id",
    "cookie",
    "datasourceModelId",
    "adapter",
    "description",
    "scripts",
    "viewTemplate",
    "security",
    "afterQueryData",
    "toolbarOrDataProviderOrColumnModel"
})
@XmlRootElement(name = "form-template")
public class FormTemplate implements Serializable {
	private static final long serialVersionUID = 1L;

    protected String id;
    protected Cookie cookie;
    @XmlElement(name = "data-source-model-id")
    protected String datasourceModelId;
    protected Adapter adapter;
    protected String description;
    protected String scripts;
    @XmlElement(name = "view-template")
    protected ViewTemplate viewTemplate;
    protected Security security;
    @XmlElement(name = "after-query-data")
    protected String afterQueryData;
    @XmlElements({
        @XmlElement(name = "column-model", type = ColumnModel.class),
        @XmlElement(name = "data-provider", type = DataProvider.class),
        @XmlElement(name = "toolbar", type = Toolbar.class)
    })
    protected List<Object> toolbarOrDataProviderOrColumnModel;

    public DataProvider getDataProvider(String name) {
    	List<Object> list = getToolbarOrDataProviderOrColumnModel();
    	for (Object item: list) {
    		if (item instanceof DataProvider) {
    			DataProvider dataProvider = (DataProvider)item;
    			if (dataProvider.getName().equals(name)) {
    				return dataProvider;
    			}
    		}
    	}
    	return null;
    }
    
    public List<DataProvider> getDataProvider() {
    	List<DataProvider> result = New.arrayList();
    	for (Object item: getToolbarOrDataProviderOrColumnModel()) {
    		if (item instanceof DataProvider) {
    			result.add((DataProvider)item);
    		}
    	}
    	return result;
    }
    
    public List<ColumnModel> getColumnModel() {
    	List<ColumnModel> result = New.arrayList();
    	for (Object item: getToolbarOrDataProviderOrColumnModel()) {
    		if (item instanceof ColumnModel) {
    			result.add((ColumnModel)item);
    		}
    	}
    	return result;
    }
    
    public List<Toolbar> getToolbar() {
    	List<Toolbar> result = New.arrayList();
    	for (Object item: getToolbarOrDataProviderOrColumnModel()) {
    		if (item instanceof Toolbar) {
    			result.add((Toolbar)item);
    		}
    	}
    	return result;
    }
    
    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the cookie property.
     * 
     * @return
     *     possible object is
     *     {@link Cookie }
     *     
     */
    public Cookie getCookie() {
        return cookie;
    }

    /**
     * Sets the value of the cookie property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cookie }
     *     
     */
    public void setCookie(Cookie value) {
        this.cookie = value;
    }

    /**
     * Gets the value of the datasourceModelId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatasourceModelId() {
        return datasourceModelId;
    }

    /**
     * Sets the value of the datasourceModelId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatasourceModelId(String value) {
        this.datasourceModelId = value;
    }

    /**
     * Gets the value of the adapter property.
     * 
     * @return
     *     possible object is
     *     {@link Adapter }
     *     
     */
    public Adapter getAdapter() {
        return adapter;
    }

    /**
     * Sets the value of the adapter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Adapter }
     *     
     */
    public void setAdapter(Adapter value) {
        this.adapter = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the scripts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScripts() {
        return scripts;
    }

    /**
     * Sets the value of the scripts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScripts(String value) {
        this.scripts = value;
    }

    /**
     * Gets the value of the viewTemplate property.
     * 
     * @return
     *     possible object is
     *     {@link ViewTemplate }
     *     
     */
    public ViewTemplate getViewTemplate() {
        return viewTemplate;
    }

    /**
     * Sets the value of the viewTemplate property.
     * 
     * @param value
     *     allowed object is
     *     {@link ViewTemplate }
     *     
     */
    public void setViewTemplate(ViewTemplate value) {
        this.viewTemplate = value;
    }

    /**
     * Gets the value of the security property.
     * 
     * @return
     *     possible object is
     *     {@link Security }
     *     
     */
    public Security getSecurity() {
        return security;
    }

    /**
     * Sets the value of the security property.
     * 
     * @param value
     *     allowed object is
     *     {@link Security }
     *     
     */
    public void setSecurity(Security value) {
        this.security = value;
    }

    /**
     * Gets the value of the afterQueryData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAfterQueryData() {
        return afterQueryData;
    }

    /**
     * Sets the value of the afterQueryData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAfterQueryData(String value) {
        this.afterQueryData = value;
    }

    /**
     * Gets the value of the toolbarOrDataProviderOrColumnModel property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the toolbarOrDataProviderOrColumnModel property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getToolbarOrDataProviderOrColumnModel().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ColumnModel }
     * {@link DataProvider }
     * {@link Toolbar }
     * 
     * 
     */
    public List<Object> getToolbarOrDataProviderOrColumnModel() {
        if (toolbarOrDataProviderOrColumnModel == null) {
            toolbarOrDataProviderOrColumnModel = new ArrayList<Object>();
        }
        return this.toolbarOrDataProviderOrColumnModel;
    }

	@XmlTransient
    private String xmlName = "form-template";

    public String getXmlName() {
		return xmlName;
	}

	public void setXmlName(String xmlName) {
		this.xmlName = xmlName;
	}
}
