//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.08.14 at 07:25:23 AM IDT 
//


package generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
 *         &lt;element ref="{}PRD-population"/>
 *         &lt;element ref="{}PRD-properties"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "prdPopulation",
    "prdProperties"
})
@XmlRootElement(name = "PRD-entity")
public class PRDEntity {

    @XmlElement(name = "PRD-population")
    protected int prdPopulation;
    @XmlElement(name = "PRD-properties", required = true)
    protected PRDProperties prdProperties;
    @XmlAttribute(name = "name", required = true)
    protected String name;

    /**
     * Gets the value of the prdPopulation property.
     * 
     */
    public int getPRDPopulation() {
        return prdPopulation;
    }

    /**
     * Sets the value of the prdPopulation property.
     * 
     */
    public void setPRDPopulation(int value) {
        this.prdPopulation = value;
    }

    /**
     * Gets the value of the prdProperties property.
     * 
     * @return
     *     possible object is
     *     {@link PRDProperties }
     *     
     */
    public PRDProperties getPRDProperties() {
        return prdProperties;
    }

    /**
     * Sets the value of the prdProperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link PRDProperties }
     *     
     */
    public void setPRDProperties(PRDProperties value) {
        this.prdProperties = value;
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

}
