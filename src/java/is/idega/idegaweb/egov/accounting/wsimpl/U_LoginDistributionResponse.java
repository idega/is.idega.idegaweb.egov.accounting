/**
 * U_LoginDistributionResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.accounting.wsimpl;

public class U_LoginDistributionResponse  implements java.io.Serializable {
    private java.lang.String u_LoginDistributionResult;

    public U_LoginDistributionResponse() {
    }

    public U_LoginDistributionResponse(
           java.lang.String u_LoginDistributionResult) {
           this.u_LoginDistributionResult = u_LoginDistributionResult;
    }


    /**
     * Gets the u_LoginDistributionResult value for this U_LoginDistributionResponse.
     * 
     * @return u_LoginDistributionResult
     */
    public java.lang.String getU_LoginDistributionResult() {
        return u_LoginDistributionResult;
    }


    /**
     * Sets the u_LoginDistributionResult value for this U_LoginDistributionResponse.
     * 
     * @param u_LoginDistributionResult
     */
    public void setU_LoginDistributionResult(java.lang.String u_LoginDistributionResult) {
        this.u_LoginDistributionResult = u_LoginDistributionResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof U_LoginDistributionResponse)) return false;
        U_LoginDistributionResponse other = (U_LoginDistributionResponse) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.u_LoginDistributionResult==null && other.getU_LoginDistributionResult()==null) || 
             (this.u_LoginDistributionResult!=null &&
              this.u_LoginDistributionResult.equals(other.getU_LoginDistributionResult())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getU_LoginDistributionResult() != null) {
            _hashCode += getU_LoginDistributionResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(U_LoginDistributionResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">u_LoginDistributionResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("u_LoginDistributionResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "u_LoginDistributionResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
