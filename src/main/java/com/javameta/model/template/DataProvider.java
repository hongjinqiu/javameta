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
 *         &lt;choice>
 *           &lt;element name="table" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="sql" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="procedure" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="class" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;/choice>
 *         &lt;element name="template-sql" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="sql" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                 &lt;attribute name="template" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="summary-sql" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="sql" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                 &lt;attribute name="template" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                 &lt;attribute name="position">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;enumeration value="top"/>
 *                       &lt;enumeration value="bottom"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="security" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{https://github.com/hongjinqiu/javameta/template}query-parameters" minOccurs="0"/>
 *         &lt;element name="suffix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="size" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="dialect" default="mysql">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="oracle"/>
 *             &lt;enumeration value="mysql"/>
 *             &lt;enumeration value="mongodb"/>
 *             &lt;enumeration value="hbase"/>
 *             &lt;enumeration value="hsql"/>
 *             &lt;enumeration value="sqlserver"/>
 *             &lt;enumeration value="sqlite"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="localPaging" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="pageList" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="datasource" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="sqlIntercept" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="orderSqlIntercept" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "table",
    "sql",
    "procedure",
    "clazz",
    "templateSql",
    "summarySql",
    "queryParameters",
    "suffix"
})
@XmlRootElement(name = "data-provider")
public class DataProvider implements Serializable {
	private static final long serialVersionUID = 1L;

    protected String table;
    protected String sql;
    protected String procedure;
    @XmlElement(name = "class")
    protected String clazz;
    @XmlElement(name = "template-sql")
    protected List<DataProvider.TemplateSql> templateSql;
    @XmlElement(name = "summary-sql")
    protected List<DataProvider.SummarySql> summarySql;
    @XmlElement(name = "query-parameters")
    protected QueryParameters queryParameters;
    protected String suffix;
    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String name;
    @XmlAttribute
    protected Integer size;
    @XmlAttribute
    protected String dialect;
    @XmlAttribute
    protected Boolean localPaging;
    @XmlAttribute
    protected String pageList;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String datasource;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String sqlIntercept;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String orderSqlIntercept;
    
    @XmlTransient
    private String xmlName = "data-provider";

    /**
     * Gets the value of the table property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTable() {
        return table;
    }

    /**
     * Sets the value of the table property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTable(String value) {
        this.table = value;
    }

    /**
     * Gets the value of the sql property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSql() {
        return sql;
    }

    /**
     * Sets the value of the sql property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSql(String value) {
        this.sql = value;
    }

    /**
     * Gets the value of the procedure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcedure() {
        return procedure;
    }

    /**
     * Sets the value of the procedure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcedure(String value) {
        this.procedure = value;
    }

    /**
     * Gets the value of the clazz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * Sets the value of the clazz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClazz(String value) {
        this.clazz = value;
    }

    /**
     * Gets the value of the templateSql property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the templateSql property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTemplateSql().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataProvider.TemplateSql }
     * 
     * 
     */
    public List<DataProvider.TemplateSql> getTemplateSql() {
        if (templateSql == null) {
            templateSql = new ArrayList<DataProvider.TemplateSql>();
        }
        return this.templateSql;
    }

    /**
     * Gets the value of the summarySql property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the summarySql property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSummarySql().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataProvider.SummarySql }
     * 
     * 
     */
    public List<DataProvider.SummarySql> getSummarySql() {
        if (summarySql == null) {
            summarySql = new ArrayList<DataProvider.SummarySql>();
        }
        return this.summarySql;
    }

    /**
     * Gets the value of the queryParameters property.
     * 
     * @return
     *     possible object is
     *     {@link QueryParameters }
     *     
     */
    public QueryParameters getQueryParameters() {
        return queryParameters;
    }

    /**
     * Sets the value of the queryParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryParameters }
     *     
     */
    public void setQueryParameters(QueryParameters value) {
        this.queryParameters = value;
    }
    
    /**
     * Gets the value of the suffix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * Sets the value of the suffix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuffix(String value) {
        this.suffix = value;
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
     * Gets the value of the size property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSize(Integer value) {
        this.size = value;
    }

    /**
     * Gets the value of the dialect property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDialect() {
        return dialect;
    }

    /**
     * Sets the value of the dialect property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDialect(String value) {
        this.dialect = value;
    }

    /**
     * Gets the value of the localPaging property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLocalPaging() {
        return localPaging;
    }

	public Boolean getLocalPaging() {
        return localPaging;
    }

    /**
     * Sets the value of the localPaging property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLocalPaging(Boolean value) {
        this.localPaging = value;
    }

    /**
     * Gets the value of the pageList property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPageList() {
        return pageList;
    }

    /**
     * Sets the value of the pageList property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPageList(String value) {
        this.pageList = value;
    }

    /**
     * Gets the value of the datasource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatasource() {
        return datasource;
    }

    /**
     * Sets the value of the datasource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatasource(String value) {
        this.datasource = value;
    }

    /**
     * Gets the value of the sqlIntercept property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSqlIntercept() {
        return sqlIntercept;
    }

    /**
     * Sets the value of the sqlIntercept property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSqlIntercept(String value) {
        this.sqlIntercept = value;
    }

    /**
     * Gets the value of the orderSqlIntercept property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderSqlIntercept() {
        return orderSqlIntercept;
    }

    /**
     * Sets the value of the orderSqlIntercept property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderSqlIntercept(String value) {
        this.orderSqlIntercept = value;
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
     *         &lt;element name="sql" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *       &lt;attribute name="template" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *       &lt;attribute name="position">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;enumeration value="top"/>
     *             &lt;enumeration value="bottom"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *       &lt;attribute name="security" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "sql"
    })
    public static class SummarySql implements Serializable {
		private static final long serialVersionUID = 1L;

        @XmlElement(required = true)
        protected String sql;
        @XmlAttribute(required = true)
        @XmlSchemaType(name = "anySimpleType")
        protected String name;
        @XmlAttribute
        @XmlSchemaType(name = "anySimpleType")
        protected String template;
        @XmlAttribute
        protected String position;
        @XmlAttribute
        protected Boolean security;

        /**
         * Gets the value of the sql property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSql() {
            return sql;
        }

        /**
         * Sets the value of the sql property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSql(String value) {
            this.sql = value;
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
         * Gets the value of the template property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTemplate() {
            return template;
        }

        /**
         * Sets the value of the template property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTemplate(String value) {
            this.template = value;
        }

        /**
         * Gets the value of the position property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPosition() {
            return position;
        }

        /**
         * Sets the value of the position property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPosition(String value) {
            this.position = value;
        }

        /**
         * Gets the value of the security property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isSecurity() {
            return security;
        }

	public Boolean getSecurity() {
            return security;
        }

        /**
         * Sets the value of the security property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setSecurity(Boolean value) {
            this.security = value;
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
     *       &lt;sequence>
     *         &lt;element name="sql" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *       &lt;attribute name="template" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "sql"
    })
    public static class TemplateSql implements Serializable {
		private static final long serialVersionUID = 1L;

        @XmlElement(required = true)
        protected String sql;
        @XmlAttribute(required = true)
        @XmlSchemaType(name = "anySimpleType")
        protected String name;
        @XmlAttribute
        @XmlSchemaType(name = "anySimpleType")
        protected String template;

        /**
         * Gets the value of the sql property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSql() {
            return sql;
        }

        /**
         * Sets the value of the sql property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSql(String value) {
            this.sql = value;
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
         * Gets the value of the template property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTemplate() {
            return template;
        }

        /**
         * Sets the value of the template property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTemplate(String value) {
            this.template = value;
        }

    }


	public String getXmlName() {
		return xmlName;
	}

	public void setXmlName(String xmlName) {
		this.xmlName = xmlName;
	}

}
