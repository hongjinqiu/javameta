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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


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
 *         &lt;element name="relationItem" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="relationExpr" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute name="mode" default="text">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                 &lt;enumeration value="text"/>
 *                                 &lt;enumeration value="js"/>
 *                                 &lt;enumeration value="function"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="relationConfig" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="selectorName" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                           &lt;attribute name="displayField" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                           &lt;attribute name="valueField" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                           &lt;attribute name="selectionMode">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                 &lt;enumeration value="single"/>
 *                                 &lt;enumeration value="multi"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                           &lt;attribute name="selectorTitle" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="copyConfig" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="copySrcField" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                           &lt;attribute name="copyDescField" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "relationItem"
})
@XmlRootElement(name = "relationDS")
public class RelationDS implements Serializable {
	private static final long serialVersionUID = 1L;

    protected List<RelationDS.RelationItem> relationItem;

    /**
     * Gets the value of the relationItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relationItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelationItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelationDS.RelationItem }
     * 
     * 
     */
    public List<RelationDS.RelationItem> getRelationItem() {
        if (relationItem == null) {
            relationItem = new ArrayList<RelationDS.RelationItem>();
        }
        return this.relationItem;
    }


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
     *         &lt;element name="relationExpr" minOccurs="0">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                 &lt;attribute name="mode" default="text">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                       &lt;enumeration value="text"/>
     *                       &lt;enumeration value="js"/>
     *                       &lt;enumeration value="function"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="relationConfig" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="selectorName" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *                 &lt;attribute name="displayField" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *                 &lt;attribute name="valueField" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *                 &lt;attribute name="selectionMode">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                       &lt;enumeration value="single"/>
     *                       &lt;enumeration value="multi"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
     *                 &lt;attribute name="selectorTitle" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="copyConfig" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="copySrcField" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *                 &lt;attribute name="copyDescField" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "relationExpr",
        "relationConfig",
        "copyConfig"
    })
    public static class RelationItem implements Serializable {
		private static final long serialVersionUID = 1L;

        @XmlElement(defaultValue = "true")
        protected RelationDS.RelationItem.RelationExpr relationExpr;
        protected RelationDS.RelationItem.RelationConfig relationConfig;
        protected List<RelationDS.RelationItem.CopyConfig> copyConfig;
        @XmlAttribute(required = true)
        @XmlSchemaType(name = "anySimpleType")
        protected String name;

        /**
         * Gets the value of the relationExpr property.
         * 
         * @return
         *     possible object is
         *     {@link RelationDS.RelationItem.RelationExpr }
         *     
         */
        public RelationDS.RelationItem.RelationExpr getRelationExpr() {
            return relationExpr;
        }

        /**
         * Sets the value of the relationExpr property.
         * 
         * @param value
         *     allowed object is
         *     {@link RelationDS.RelationItem.RelationExpr }
         *     
         */
        public void setRelationExpr(RelationDS.RelationItem.RelationExpr value) {
            this.relationExpr = value;
        }

        /**
         * Gets the value of the relationConfig property.
         * 
         * @return
         *     possible object is
         *     {@link RelationDS.RelationItem.RelationConfig }
         *     
         */
        public RelationDS.RelationItem.RelationConfig getRelationConfig() {
            return relationConfig;
        }

        /**
         * Sets the value of the relationConfig property.
         * 
         * @param value
         *     allowed object is
         *     {@link RelationDS.RelationItem.RelationConfig }
         *     
         */
        public void setRelationConfig(RelationDS.RelationItem.RelationConfig value) {
            this.relationConfig = value;
        }

        /**
         * Gets the value of the copyConfig property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the copyConfig property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCopyConfig().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RelationDS.RelationItem.CopyConfig }
         * 
         * 
         */
        public List<RelationDS.RelationItem.CopyConfig> getCopyConfig() {
            if (copyConfig == null) {
                copyConfig = new ArrayList<RelationDS.RelationItem.CopyConfig>();
            }
            return this.copyConfig;
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
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="copySrcField" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
         *       &lt;attribute name="copyDescField" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class CopyConfig implements Serializable {
		private static final long serialVersionUID = 1L;

            @XmlAttribute
            @XmlSchemaType(name = "anySimpleType")
            protected String copySrcField;
            @XmlAttribute
            @XmlSchemaType(name = "anySimpleType")
            protected String copyDescField;

            /**
             * Gets the value of the copySrcField property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCopySrcField() {
                return copySrcField;
            }

            /**
             * Sets the value of the copySrcField property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCopySrcField(String value) {
                this.copySrcField = value;
            }

            /**
             * Gets the value of the copyDescField property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCopyDescField() {
                return copyDescField;
            }

            /**
             * Sets the value of the copyDescField property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCopyDescField(String value) {
                this.copyDescField = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="selectorName" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
         *       &lt;attribute name="displayField" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
         *       &lt;attribute name="valueField" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
         *       &lt;attribute name="selectionMode">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *             &lt;enumeration value="single"/>
         *             &lt;enumeration value="multi"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *       &lt;attribute name="selectorTitle" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class RelationConfig implements Serializable {
		private static final long serialVersionUID = 1L;

            @XmlAttribute
            @XmlSchemaType(name = "anySimpleType")
            protected String selectorName;
            @XmlAttribute
            @XmlSchemaType(name = "anySimpleType")
            protected String displayField;
            @XmlAttribute
            @XmlSchemaType(name = "anySimpleType")
            protected String valueField;
            @XmlAttribute
            protected String selectionMode;
            @XmlAttribute
            @XmlSchemaType(name = "anySimpleType")
            protected String selectorTitle;

            /**
             * Gets the value of the selectorName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSelectorName() {
                return selectorName;
            }

            /**
             * Sets the value of the selectorName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSelectorName(String value) {
                this.selectorName = value;
            }

            /**
             * Gets the value of the displayField property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDisplayField() {
                return displayField;
            }

            /**
             * Sets the value of the displayField property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDisplayField(String value) {
                this.displayField = value;
            }

            /**
             * Gets the value of the valueField property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValueField() {
                return valueField;
            }

            /**
             * Sets the value of the valueField property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValueField(String value) {
                this.valueField = value;
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
             * Gets the value of the selectorTitle property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSelectorTitle() {
                return selectorTitle;
            }

            /**
             * Sets the value of the selectorTitle property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSelectorTitle(String value) {
                this.selectorTitle = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *       &lt;attribute name="mode" default="text">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *             &lt;enumeration value="text"/>
         *             &lt;enumeration value="js"/>
         *             &lt;enumeration value="function"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class RelationExpr implements Serializable {
		private static final long serialVersionUID = 1L;

            @XmlValue
            protected String value;
            @XmlAttribute
            protected String mode;

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
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


		public void setCopyConfig(List<RelationDS.RelationItem.CopyConfig> copyConfig) {
			this.copyConfig = copyConfig;
		}

    }


	public void setRelationItem(List<RelationDS.RelationItem> relationItem) {
		this.relationItem = relationItem;
	}

	@XmlTransient
    private String xmlName = "relationDS";

    public String getXmlName() {
		return xmlName;
	}

	public void setXmlName(String xmlName) {
		this.xmlName = xmlName;
	}
}
