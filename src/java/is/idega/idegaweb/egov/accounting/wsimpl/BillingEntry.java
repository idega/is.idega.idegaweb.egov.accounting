/**
 * BillingEntry.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.accounting.wsimpl;

public class BillingEntry  implements java.io.Serializable {
    private java.lang.String personalId;

    private java.lang.String payerPersonalId;

    private java.lang.String providerCode;

    private java.lang.String productCode;

    private java.lang.String projectCode;

    private int amount;

    private java.util.Date startDate;

    private java.util.Date endDate;

    private java.lang.String paymentMethod;

    private float unitPrice;

    private java.lang.String cardNumber;

    private java.lang.String cardType;

    private int cardExpirationMonth;

    private int cardExpirationYear;

    public BillingEntry() {
    }

    public BillingEntry(
           java.lang.String personalId,
           java.lang.String payerPersonalId,
           java.lang.String providerCode,
           java.lang.String productCode,
           java.lang.String projectCode,
           int amount,
           java.util.Date startDate,
           java.util.Date endDate,
           java.lang.String paymentMethod,
           float unitPrice,
           java.lang.String cardNumber,
           java.lang.String cardType,
           int cardExpirationMonth,
           int cardExpirationYear) {
           this.personalId = personalId;
           this.payerPersonalId = payerPersonalId;
           this.providerCode = providerCode;
           this.productCode = productCode;
           this.projectCode = projectCode;
           this.amount = amount;
           this.startDate = startDate;
           this.endDate = endDate;
           this.paymentMethod = paymentMethod;
           this.unitPrice = unitPrice;
           this.cardNumber = cardNumber;
           this.cardType = cardType;
           this.cardExpirationMonth = cardExpirationMonth;
           this.cardExpirationYear = cardExpirationYear;
    }


    /**
     * Gets the personalId value for this BillingEntry.
     * 
     * @return personalId
     */
    public java.lang.String getPersonalId() {
        return personalId;
    }


    /**
     * Sets the personalId value for this BillingEntry.
     * 
     * @param personalId
     */
    public void setPersonalId(java.lang.String personalId) {
        this.personalId = personalId;
    }


    /**
     * Gets the payerPersonalId value for this BillingEntry.
     * 
     * @return payerPersonalId
     */
    public java.lang.String getPayerPersonalId() {
        return payerPersonalId;
    }


    /**
     * Sets the payerPersonalId value for this BillingEntry.
     * 
     * @param payerPersonalId
     */
    public void setPayerPersonalId(java.lang.String payerPersonalId) {
        this.payerPersonalId = payerPersonalId;
    }


    /**
     * Gets the providerCode value for this BillingEntry.
     * 
     * @return providerCode
     */
    public java.lang.String getProviderCode() {
        return providerCode;
    }


    /**
     * Sets the providerCode value for this BillingEntry.
     * 
     * @param providerCode
     */
    public void setProviderCode(java.lang.String providerCode) {
        this.providerCode = providerCode;
    }


    /**
     * Gets the productCode value for this BillingEntry.
     * 
     * @return productCode
     */
    public java.lang.String getProductCode() {
        return productCode;
    }


    /**
     * Sets the productCode value for this BillingEntry.
     * 
     * @param productCode
     */
    public void setProductCode(java.lang.String productCode) {
        this.productCode = productCode;
    }


    /**
     * Gets the projectCode value for this BillingEntry.
     * 
     * @return projectCode
     */
    public java.lang.String getProjectCode() {
        return projectCode;
    }


    /**
     * Sets the projectCode value for this BillingEntry.
     * 
     * @param projectCode
     */
    public void setProjectCode(java.lang.String projectCode) {
        this.projectCode = projectCode;
    }


    /**
     * Gets the amount value for this BillingEntry.
     * 
     * @return amount
     */
    public int getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this BillingEntry.
     * 
     * @param amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }


    /**
     * Gets the startDate value for this BillingEntry.
     * 
     * @return startDate
     */
    public java.util.Date getStartDate() {
        return startDate;
    }


    /**
     * Sets the startDate value for this BillingEntry.
     * 
     * @param startDate
     */
    public void setStartDate(java.util.Date startDate) {
        this.startDate = startDate;
    }


    /**
     * Gets the endDate value for this BillingEntry.
     * 
     * @return endDate
     */
    public java.util.Date getEndDate() {
        return endDate;
    }


    /**
     * Sets the endDate value for this BillingEntry.
     * 
     * @param endDate
     */
    public void setEndDate(java.util.Date endDate) {
        this.endDate = endDate;
    }


    /**
     * Gets the paymentMethod value for this BillingEntry.
     * 
     * @return paymentMethod
     */
    public java.lang.String getPaymentMethod() {
        return paymentMethod;
    }


    /**
     * Sets the paymentMethod value for this BillingEntry.
     * 
     * @param paymentMethod
     */
    public void setPaymentMethod(java.lang.String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    /**
     * Gets the unitPrice value for this BillingEntry.
     * 
     * @return unitPrice
     */
    public float getUnitPrice() {
        return unitPrice;
    }


    /**
     * Sets the unitPrice value for this BillingEntry.
     * 
     * @param unitPrice
     */
    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }


    /**
     * Gets the cardNumber value for this BillingEntry.
     * 
     * @return cardNumber
     */
    public java.lang.String getCardNumber() {
        return cardNumber;
    }


    /**
     * Sets the cardNumber value for this BillingEntry.
     * 
     * @param cardNumber
     */
    public void setCardNumber(java.lang.String cardNumber) {
        this.cardNumber = cardNumber;
    }


    /**
     * Gets the cardType value for this BillingEntry.
     * 
     * @return cardType
     */
    public java.lang.String getCardType() {
        return cardType;
    }


    /**
     * Sets the cardType value for this BillingEntry.
     * 
     * @param cardType
     */
    public void setCardType(java.lang.String cardType) {
        this.cardType = cardType;
    }


    /**
     * Gets the cardExpirationMonth value for this BillingEntry.
     * 
     * @return cardExpirationMonth
     */
    public int getCardExpirationMonth() {
        return cardExpirationMonth;
    }


    /**
     * Sets the cardExpirationMonth value for this BillingEntry.
     * 
     * @param cardExpirationMonth
     */
    public void setCardExpirationMonth(int cardExpirationMonth) {
        this.cardExpirationMonth = cardExpirationMonth;
    }


    /**
     * Gets the cardExpirationYear value for this BillingEntry.
     * 
     * @return cardExpirationYear
     */
    public int getCardExpirationYear() {
        return cardExpirationYear;
    }


    /**
     * Sets the cardExpirationYear value for this BillingEntry.
     * 
     * @param cardExpirationYear
     */
    public void setCardExpirationYear(int cardExpirationYear) {
        this.cardExpirationYear = cardExpirationYear;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BillingEntry)) return false;
        BillingEntry other = (BillingEntry) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.personalId==null && other.getPersonalId()==null) || 
             (this.personalId!=null &&
              this.personalId.equals(other.getPersonalId()))) &&
            ((this.payerPersonalId==null && other.getPayerPersonalId()==null) || 
             (this.payerPersonalId!=null &&
              this.payerPersonalId.equals(other.getPayerPersonalId()))) &&
            ((this.providerCode==null && other.getProviderCode()==null) || 
             (this.providerCode!=null &&
              this.providerCode.equals(other.getProviderCode()))) &&
            ((this.productCode==null && other.getProductCode()==null) || 
             (this.productCode!=null &&
              this.productCode.equals(other.getProductCode()))) &&
            ((this.projectCode==null && other.getProjectCode()==null) || 
             (this.projectCode!=null &&
              this.projectCode.equals(other.getProjectCode()))) &&
            this.amount == other.getAmount() &&
            ((this.startDate==null && other.getStartDate()==null) || 
             (this.startDate!=null &&
              this.startDate.equals(other.getStartDate()))) &&
            ((this.endDate==null && other.getEndDate()==null) || 
             (this.endDate!=null &&
              this.endDate.equals(other.getEndDate()))) &&
            ((this.paymentMethod==null && other.getPaymentMethod()==null) || 
             (this.paymentMethod!=null &&
              this.paymentMethod.equals(other.getPaymentMethod()))) &&
            this.unitPrice == other.getUnitPrice() &&
            ((this.cardNumber==null && other.getCardNumber()==null) || 
             (this.cardNumber!=null &&
              this.cardNumber.equals(other.getCardNumber()))) &&
            ((this.cardType==null && other.getCardType()==null) || 
             (this.cardType!=null &&
              this.cardType.equals(other.getCardType()))) &&
            this.cardExpirationMonth == other.getCardExpirationMonth() &&
            this.cardExpirationYear == other.getCardExpirationYear();
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
        if (getPersonalId() != null) {
            _hashCode += getPersonalId().hashCode();
        }
        if (getPayerPersonalId() != null) {
            _hashCode += getPayerPersonalId().hashCode();
        }
        if (getProviderCode() != null) {
            _hashCode += getProviderCode().hashCode();
        }
        if (getProductCode() != null) {
            _hashCode += getProductCode().hashCode();
        }
        if (getProjectCode() != null) {
            _hashCode += getProjectCode().hashCode();
        }
        _hashCode += getAmount();
        if (getStartDate() != null) {
            _hashCode += getStartDate().hashCode();
        }
        if (getEndDate() != null) {
            _hashCode += getEndDate().hashCode();
        }
        if (getPaymentMethod() != null) {
            _hashCode += getPaymentMethod().hashCode();
        }
        _hashCode += new Float(getUnitPrice()).hashCode();
        if (getCardNumber() != null) {
            _hashCode += getCardNumber().hashCode();
        }
        if (getCardType() != null) {
            _hashCode += getCardType().hashCode();
        }
        _hashCode += getCardExpirationMonth();
        _hashCode += getCardExpirationYear();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BillingEntry.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.idega.com/AccountingService/", "BillingEntry"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personalId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PersonalId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payerPersonalId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PayerPersonalId"));
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
        elemField.setFieldName("productCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ProductCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("projectCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ProjectCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Amount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "StartDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "EndDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentMethod");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PaymentMethod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "UnitPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CardNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CardType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardExpirationMonth");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CardExpirationMonth"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardExpirationYear");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CardExpirationYear"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
