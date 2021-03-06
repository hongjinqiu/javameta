//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.12.15 at 04:43:56 PM CST 
//


package com.javameta.model.datasource;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="id">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[A-A]"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="displayName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="allowCopy" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element ref="{https://github.com/hongjinqiu/javameta/datasource}fixField"/>
 *         &lt;element ref="{https://github.com/hongjinqiu/javameta/datasource}bizField"/>
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
    "displayName",
    "allowCopy",
    "fixField",
    "bizField"
})
@XmlRootElement(name = "masterData")
public class MasterData implements Serializable {
	private static final long serialVersionUID = 1L;

    @XmlElement(required = true, defaultValue = "A")
    protected String id;
    @XmlElement(required = true, defaultValue = "\u4e3b\u6570\u636e\u96c6")
    protected String displayName;
    @XmlElement(defaultValue = "true")
    protected boolean allowCopy;
    @XmlElement(required = true)
    protected FixField fixField;
    @XmlElement(required = true)
    protected BizField bizField;

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
     * Gets the value of the displayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the value of the displayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayName(String value) {
        this.displayName = value;
    }

    /**
     * Gets the value of the allowCopy property.
     * 
     */
    public Boolean isAllowCopy() {
        return allowCopy;
    }

	public Boolean getAllowCopy() {
        return allowCopy;
    }

    /**
     * Sets the value of the allowCopy property.
     * 
     */
    public void setAllowCopy(boolean value) {
        this.allowCopy = value;
    }

    /**
     * Gets the value of the fixField property.
     * 
     * @return
     *     possible object is
     *     {@link FixField }
     *     
     */
    public FixField getFixField() {
        return fixField;
    }

    /**
     * Sets the value of the fixField property.
     * 
     * @param value
     *     allowed object is
     *     {@link FixField }
     *     
     */
    public void setFixField(FixField value) {
        this.fixField = value;
    }

    /**
     * Gets the value of the bizField property.
     * 
     * @return
     *     possible object is
     *     {@link BizField }
     *     
     */
    public BizField getBizField() {
        return bizField;
    }

    /**
     * Sets the value of the bizField property.
     * 
     * @param value
     *     allowed object is
     *     {@link BizField }
     *     
     */
    public void setBizField(BizField value) {
        this.bizField = value;
    }

	@XmlTransient
    private String xmlName = "masterData";

    public String getXmlName() {
		return xmlName;
	}

	public void setXmlName(String xmlName) {
		this.xmlName = xmlName;
	}
}
