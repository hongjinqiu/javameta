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
 *       &lt;attGroup ref="{https://github.com/hongjinqiu/javameta/template}button-attribute"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "split-button")
public class SplitButton implements Serializable {
	private static final long serialVersionUID = 1L;

    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String xtype;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String name;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String rendererTemplate;
    @XmlAttribute(required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String text;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String iconCls;
    @XmlAttribute
    protected String iconAlign;
    @XmlAttribute
    protected Boolean disabled;
    @XmlAttribute
    protected Boolean hidden;
    @XmlAttribute
    protected String arrowAlign;
    @XmlAttribute
    protected String scale;
    @XmlAttribute
    protected Integer rowspan;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String handler;
    @XmlAttribute
    protected String mode;

    /**
     * Gets the value of the xtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXtype() {
        return xtype;
    }

    /**
     * Sets the value of the xtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXtype(String value) {
        this.xtype = value;
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
     * Gets the value of the iconCls property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIconCls() {
        return iconCls;
    }

    /**
     * Sets the value of the iconCls property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIconCls(String value) {
        this.iconCls = value;
    }

    /**
     * Gets the value of the iconAlign property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIconAlign() {
        return iconAlign;
    }

    /**
     * Sets the value of the iconAlign property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIconAlign(String value) {
        this.iconAlign = value;
    }

    /**
     * Gets the value of the disabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDisabled() {
        return disabled;
    }
    
    public Boolean getDisabled() {
    	return isDisabled();
    }

    /**
     * Sets the value of the disabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDisabled(Boolean value) {
        this.disabled = value;
    }

    /**
     * Gets the value of the hidden property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHidden() {
        return hidden;
    }
    
    public Boolean getHidden() {
    	return isHidden();
    }

    /**
     * Sets the value of the hidden property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHidden(Boolean value) {
        this.hidden = value;
    }

    /**
     * Gets the value of the arrowAlign property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArrowAlign() {
        return arrowAlign;
    }

    /**
     * Sets the value of the arrowAlign property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArrowAlign(String value) {
        this.arrowAlign = value;
    }

    /**
     * Gets the value of the scale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScale() {
        return scale;
    }

    /**
     * Sets the value of the scale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScale(String value) {
        this.scale = value;
    }

    /**
     * Gets the value of the rowspan property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getRowspan() {
        return rowspan;
    }

    /**
     * Sets the value of the rowspan property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRowspan(Integer value) {
        this.rowspan = value;
    }

    /**
     * Gets the value of the handler property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandler() {
        return handler;
    }

    /**
     * Sets the value of the handler property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandler(String value) {
        this.handler = value;
    }

    /**
     * Gets the value of the mode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMode() {
        return mode;
    }

    /**
     * Sets the value of the mode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMode(String value) {
        this.mode = value;
    }

}
