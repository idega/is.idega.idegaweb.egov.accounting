/**
 * U_LoginResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.accounting.wsimpl;

public class U_LoginResponse  implements java.io.Serializable {
    private java.lang.String u_LoginResult;

    public U_LoginResponse() {
    }

    public U_LoginResponse(
           java.lang.String u_LoginResult) {
           this.u_LoginResult = u_LoginResult;
    }


    /**
     * Gets the u_LoginResult value for this U_LoginResponse.
     * 
     * @return u_LoginResult
     */
    public java.lang.String getU_LoginResult() {
        return u_LoginResult;
    }


    /**
     * Sets the u_LoginResult value for this U_LoginResponse.
     * 
     * @param u_LoginResult
     */
    public void setU_LoginResult(java.lang.String u_LoginResult) {
        this.u_LoginResult = u_LoginResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof U_LoginResponse)) return false;
        U_LoginResponse other = (U_LoginResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.u_LoginResult==null && other.getU_LoginResult()==null) || 
             (this.u_LoginResult!=null &&
              this.u_LoginResult.equals(other.getU_LoginResult())));
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
        if (getU_LoginResult() != null) {
            _hashCode += getU_LoginResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(U_LoginResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">u_LoginResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("u_LoginResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "u_LoginResult"));
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
