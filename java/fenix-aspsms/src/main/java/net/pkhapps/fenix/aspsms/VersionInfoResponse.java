
package net.pkhapps.fenix.aspsms;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VersionInfoResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "versionInfoResult"
})
@XmlRootElement(name = "VersionInfoResponse")
public class VersionInfoResponse {

    @XmlElement(name = "VersionInfoResult")
    protected String versionInfoResult;

    /**
     * Gets the value of the versionInfoResult property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getVersionInfoResult() {
        return versionInfoResult;
    }

    /**
     * Sets the value of the versionInfoResult property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setVersionInfoResult(String value) {
        this.versionInfoResult = value;
    }

}
