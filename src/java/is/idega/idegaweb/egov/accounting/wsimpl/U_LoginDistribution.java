/**
 * U_LoginDistribution.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.accounting.wsimpl;

public class U_LoginDistribution  implements java.io.Serializable {
    private java.lang.String sHost;

    private java.lang.String sPort;

    private java.lang.String stUser;

    private java.lang.String stPass;

    private java.lang.String stComp;

    public U_LoginDistribution() {
    }

    public U_LoginDistribution(
           java.lang.String sHost,
           java.lang.String sPort,
           java.lang.String stUser,
           java.lang.String stPass,
           java.lang.String stComp) {
           this.sHost = sHost;
           this.sPort = sPort;
           this.stUser = stUser;
           this.stPass = stPass;
           this.stComp = stComp;
    }


    /**
     * Gets the sHost value for this U_LoginDistribution.
     * 
     * @return sHost
     */
    public java.lang.String getSHost() {
        return sHost;
    }


    /**
     * Sets the sHost value for this U_LoginDistribution.
     * 
     * @param sHost
     */
    public void setSHost(java.lang.String sHost) {
        this.sHost = sHost;
    }


    /**
     * Gets the sPort value for this U_LoginDistribution.
     * 
     * @return sPort
     */
    public java.lang.String getSPort() {
        return sPort;
    }


    /**
     * Sets the sPort value for this U_LoginDistribution.
     * 
     * @param sPort
     */
    public void setSPort(java.lang.String sPort) {
        this.sPort = sPort;
    }


    /**
     * Gets the stUser value for this U_LoginDistribution.
     * 
     * @return stUser
     */
    public java.lang.String getStUser() {
        return stUser;
    }


    /**
     * Sets the stUser value for this U_LoginDistribution.
     * 
     * @param stUser
     */
    public void setStUser(java.lang.String stUser) {
        this.stUser = stUser;
    }


    /**
     * Gets the stPass value for this U_LoginDistribution.
     * 
     * @return stPass
     */
    public java.lang.String getStPass() {
        return stPass;
    }


    /**
     * Sets the stPass value for this U_LoginDistribution.
     * 
     * @param stPass
     */
    public void setStPass(java.lang.String stPass) {
        this.stPass = stPass;
    }


    /**
     * Gets the stComp value for this U_LoginDistribution.
     * 
     * @return stComp
     */
    public java.lang.String getStComp() {
        return stComp;
    }


    /**
     * Sets the stComp value for this U_LoginDistribution.
     * 
     * @param stComp
     */
    public void setStComp(java.lang.String stComp) {
        this.stComp = stComp;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof U_LoginDistribution)) return false;
        U_LoginDistribution other = (U_LoginDistribution) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.sHost==null && other.getSHost()==null) || 
             (this.sHost!=null &&
              this.sHost.equals(other.getSHost()))) &&
            ((this.sPort==null && other.getSPort()==null) || 
             (this.sPort!=null &&
              this.sPort.equals(other.getSPort()))) &&
            ((this.stUser==null && other.getStUser()==null) || 
             (this.stUser!=null &&
              this.stUser.equals(other.getStUser()))) &&
            ((this.stPass==null && other.getStPass()==null) || 
             (this.stPass!=null &&
              this.stPass.equals(other.getStPass()))) &&
            ((this.stComp==null && other.getStComp()==null) || 
             (this.stComp!=null &&
              this.stComp.equals(other.getStComp())));
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
        if (getSHost() != null) {
            _hashCode += getSHost().hashCode();
        }
        if (getSPort() != null) {
            _hashCode += getSPort().hashCode();
        }
        if (getStUser() != null) {
            _hashCode += getStUser().hashCode();
        }
        if (getStPass() != null) {
            _hashCode += getStPass().hashCode();
        }
        if (getStComp() != null) {
            _hashCode += getStComp().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(U_LoginDistribution.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">u_LoginDistribution"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SHost");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "sHost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SPort");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "sPort"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stUser");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "stUser"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stPass");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "stPass"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stComp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "stComp"));
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
