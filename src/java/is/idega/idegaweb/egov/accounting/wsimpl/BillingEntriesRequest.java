/**
 * BillingEntriesRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.accounting.wsimpl;

public class BillingEntriesRequest  implements java.io.Serializable {
    private java.lang.String serviceCode;

    private java.lang.String providerCode;

    private java.util.Date periodStart;

    private java.util.Date periodEnd;

    public BillingEntriesRequest() {
    }

    public BillingEntriesRequest(
           java.lang.String serviceCode,
           java.lang.String providerCode,
           java.util.Date periodStart,
           java.util.Date periodEnd) {
           this.serviceCode = serviceCode;
           this.providerCode = providerCode;
           this.periodStart = periodStart;
           this.periodEnd = periodEnd;
    }


    /**
     * Gets the serviceCode value for this BillingEntriesRequest.
     * 
     * @return serviceCode
     */
    public java.lang.String getServiceCode() {
        return this.serviceCode;
    }


    /**
     * Sets the serviceCode value for this BillingEntriesRequest.
     * 
     * @param serviceCode
     */
    public void setServiceCode(java.lang.String serviceCode) {
        this.serviceCode = serviceCode;
    }


    /**
     * Gets the providerCode value for this BillingEntriesRequest.
     * 
     * @return providerCode
     */
    public java.lang.String getProviderCode() {
        return this.providerCode;
    }


    /**
     * Sets the providerCode value for this BillingEntriesRequest.
     * 
     * @param providerCode
     */
    public void setProviderCode(java.lang.String providerCode) {
        this.providerCode = providerCode;
    }


    /**
     * Gets the periodStart value for this BillingEntriesRequest.
     * 
     * @return periodStart
     */
    public java.util.Date getPeriodStart() {
        return this.periodStart;
    }


    /**
     * Sets the periodStart value for this BillingEntriesRequest.
     * 
     * @param periodStart
     */
    public void setPeriodStart(java.util.Date periodStart) {
        this.periodStart = periodStart;
    }


    /**
     * Gets the periodEnd value for this BillingEntriesRequest.
     * 
     * @return periodEnd
     */
    public java.util.Date getPeriodEnd() {
        return this.periodEnd;
    }


    /**
     * Sets the periodEnd value for this BillingEntriesRequest.
     * 
     * @param periodEnd
     */
    public void setPeriodEnd(java.util.Date periodEnd) {
        this.periodEnd = periodEnd;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BillingEntriesRequest)) return false;
        BillingEntriesRequest other = (BillingEntriesRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (this.__equalsCalc != null) {
            return (this.__equalsCalc == obj);
        }
        this.__equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.serviceCode==null && other.getServiceCode()==null) || 
             (this.serviceCode!=null &&
              this.serviceCode.equals(other.getServiceCode()))) &&
            ((this.providerCode==null && other.getProviderCode()==null) || 
             (this.providerCode!=null &&
              this.providerCode.equals(other.getProviderCode()))) &&
            ((this.periodStart==null && other.getPeriodStart()==null) || 
             (this.periodStart!=null &&
              this.periodStart.equals(other.getPeriodStart()))) &&
            ((this.periodEnd==null && other.getPeriodEnd()==null) || 
             (this.periodEnd!=null &&
              this.periodEnd.equals(other.getPeriodEnd())));
        this.__equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (this.__hashCodeCalc) {
            return 0;
        }
        this.__hashCodeCalc = true;
        int _hashCode = 1;
        if (getServiceCode() != null) {
            _hashCode += getServiceCode().hashCode();
        }
        if (getProviderCode() != null) {
            _hashCode += getProviderCode().hashCode();
        }
        if (getPeriodStart() != null) {
            _hashCode += getPeriodStart().hashCode();
        }
        if (getPeriodEnd() != null) {
            _hashCode += getPeriodEnd().hashCode();
        }
        this.__hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BillingEntriesRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.idega.com/AccountingService/", "BillingEntriesRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ServiceCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("providerCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ProviderCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("periodStart");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PeriodStart"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("periodEnd");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PeriodEnd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
