/**
 * BillingEntry.java
 *
 * This file was auto-generated from WSDL by the Apache Axis 1.3 Oct 05, 2005
 * (05:23:37 EDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.accounting.wsimpl;

import is.idega.idegaweb.egov.accounting.business.AccountingEntry;

public class BillingEntry implements java.io.Serializable, AccountingEntry {

	private static final long serialVersionUID = 363466398096672702L;

	private java.lang.String personalId;

	private java.lang.String name;

	private java.lang.String payerPersonalId;

	private java.lang.String providerCode;

	private java.lang.String productCode;

	private java.lang.String projectCode;

	private int amount;

	private java.util.Date startDate;

	private java.util.Date endDate;

	private java.lang.String paymentMethod;

	private float unitPrice;

	private float units;

	private java.lang.String cardNumber;

	private java.lang.String cardType;

	private int cardExpirationMonth;

	private int cardExpirationYear;

	private Object extraInformation;

	private int numberOfDaysPrWeek;

	private String familyNumber;

	private int siblingNumber;

	public BillingEntry() {
	}

	public BillingEntry(java.lang.String personalId, java.lang.String name, java.lang.String payerPersonalId, java.lang.String providerCode, java.lang.String productCode, java.lang.String projectCode, int amount, java.util.Date startDate, java.util.Date endDate, java.lang.String paymentMethod, float unitPrice, java.lang.String cardNumber, java.lang.String cardType, int cardExpirationMonth, int cardExpirationYear) {
		this.personalId = personalId;
		this.name = name;
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
	@Override
	public java.lang.String getPersonalId() {
		return this.personalId;
	}

	/**
	 * Sets the personalId value for this BillingEntry.
	 *
	 * @param personalId
	 */
	@Override
	public void setPersonalId(java.lang.String personalId) {
		this.personalId = personalId;
	}

	/**
	 * Gets the name value for this BillingEntry.
	 *
	 * @return name
	 */
	@Override
	public java.lang.String getName() {
		return this.name;
	}

	/**
	 * Sets the name value for this BillingEntry.
	 *
	 * @param name
	 */
	@Override
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/**
	 * Gets the payerPersonalId value for this BillingEntry.
	 *
	 * @return payerPersonalId
	 */
	@Override
	public java.lang.String getPayerPersonalId() {
		return this.payerPersonalId;
	}

	/**
	 * Sets the payerPersonalId value for this BillingEntry.
	 *
	 * @param payerPersonalId
	 */
	@Override
	public void setPayerPersonalId(java.lang.String payerPersonalId) {
		this.payerPersonalId = payerPersonalId;
	}

	/**
	 * Gets the providerCode value for this BillingEntry.
	 *
	 * @return providerCode
	 */
	@Override
	public java.lang.String getProviderCode() {
		return this.providerCode;
	}

	/**
	 * Sets the providerCode value for this BillingEntry.
	 *
	 * @param providerCode
	 */
	@Override
	public void setProviderCode(java.lang.String providerCode) {
		this.providerCode = providerCode;
	}

	/**
	 * Gets the productCode value for this BillingEntry.
	 *
	 * @return productCode
	 */
	@Override
	public java.lang.String getProductCode() {
		return this.productCode;
	}

	/**
	 * Sets the productCode value for this BillingEntry.
	 *
	 * @param productCode
	 */
	@Override
	public void setProductCode(java.lang.String productCode) {
		this.productCode = productCode;
	}

	/**
	 * Gets the projectCode value for this BillingEntry.
	 *
	 * @return projectCode
	 */
	@Override
	public java.lang.String getProjectCode() {
		return this.projectCode;
	}

	/**
	 * Sets the projectCode value for this BillingEntry.
	 *
	 * @param projectCode
	 */
	@Override
	public void setProjectCode(java.lang.String projectCode) {
		this.projectCode = projectCode;
	}

	/**
	 * Gets the amount value for this BillingEntry.
	 *
	 * @return amount
	 */
	@Override
	public int getAmount() {
		return this.amount;
	}

	/**
	 * Sets the amount value for this BillingEntry.
	 *
	 * @param amount
	 */
	@Override
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * Gets the startDate value for this BillingEntry.
	 *
	 * @return startDate
	 */
	@Override
	public java.util.Date getStartDate() {
		return this.startDate;
	}

	/**
	 * Sets the startDate value for this BillingEntry.
	 *
	 * @param startDate
	 */
	@Override
	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Gets the endDate value for this BillingEntry.
	 *
	 * @return endDate
	 */
	@Override
	public java.util.Date getEndDate() {
		return this.endDate;
	}

	/**
	 * Sets the endDate value for this BillingEntry.
	 *
	 * @param endDate
	 */
	@Override
	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Gets the paymentMethod value for this BillingEntry.
	 *
	 * @return paymentMethod
	 */
	@Override
	public java.lang.String getPaymentMethod() {
		return this.paymentMethod;
	}

	/**
	 * Sets the paymentMethod value for this BillingEntry.
	 *
	 * @param paymentMethod
	 */
	@Override
	public void setPaymentMethod(java.lang.String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * Gets the unitPrice value for this BillingEntry.
	 *
	 * @return unitPrice
	 */
	@Override
	public float getUnitPrice() {
		return this.unitPrice;
	}

	/**
	 * Sets the unitPrice value for this BillingEntry.
	 *
	 * @param unitPrice
	 */
	@Override
	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * Gets the cardNumber value for this BillingEntry.
	 *
	 * @return cardNumber
	 */
	@Override
	public java.lang.String getCardNumber() {
		return this.cardNumber;
	}

	/**
	 * Sets the cardNumber value for this BillingEntry.
	 *
	 * @param cardNumber
	 */
	@Override
	public void setCardNumber(java.lang.String cardNumber) {
		this.cardNumber = cardNumber;
	}

	/**
	 * Gets the cardType value for this BillingEntry.
	 *
	 * @return cardType
	 */
	@Override
	public java.lang.String getCardType() {
		return this.cardType;
	}

	/**
	 * Sets the cardType value for this BillingEntry.
	 *
	 * @param cardType
	 */
	@Override
	public void setCardType(java.lang.String cardType) {
		this.cardType = cardType;
	}

	/**
	 * Gets the cardExpirationMonth value for this BillingEntry.
	 *
	 * @return cardExpirationMonth
	 */
	@Override
	public int getCardExpirationMonth() {
		return this.cardExpirationMonth;
	}

	/**
	 * Sets the cardExpirationMonth value for this BillingEntry.
	 *
	 * @param cardExpirationMonth
	 */
	@Override
	public void setCardExpirationMonth(int cardExpirationMonth) {
		this.cardExpirationMonth = cardExpirationMonth;
	}

	/**
	 * Gets the cardExpirationYear value for this BillingEntry.
	 *
	 * @return cardExpirationYear
	 */
	@Override
	public int getCardExpirationYear() {
		return this.cardExpirationYear;
	}

	/**
	 * Sets the cardExpirationYear value for this BillingEntry.
	 *
	 * @param cardExpirationYear
	 */
	@Override
	public void setCardExpirationYear(int cardExpirationYear) {
		this.cardExpirationYear = cardExpirationYear;
	}

	@Override
	public int getNumberOfDaysPrWeek() {
		return this.numberOfDaysPrWeek;
	}

	@Override
	public void setNumberOfDaysPrWeek(int numberOfDays) {
		this.numberOfDaysPrWeek = numberOfDays;
	}

	@Override
	public String getFamilyNumber() {
		return this.familyNumber;
	}

	@Override
	public void setFamilyNumber(String familyNumber) {
		this.familyNumber = familyNumber;
	}

	@Override
	public int getSiblingNumber() {
		return this.siblingNumber;
	}

	@Override
	public void setSiblingNumber(int siblingNumber) {
		this.siblingNumber = siblingNumber;
	}


	private java.lang.Object __equalsCalc = null;

	@Override
	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof BillingEntry))
			return false;
		BillingEntry other = (BillingEntry) obj;
		if (this == obj)
			return true;
		if (this.__equalsCalc != null) {
			return (this.__equalsCalc == obj);
		}
		this.__equalsCalc = obj;
		boolean _equals;
		_equals = true && ((this.personalId == null && other.getPersonalId() == null) || (this.personalId != null && this.personalId.equals(other.getPersonalId()))) && ((this.payerPersonalId == null && other.getPayerPersonalId() == null) || (this.payerPersonalId != null && this.payerPersonalId.equals(other.getPayerPersonalId()))) && ((this.providerCode == null && other.getProviderCode() == null) || (this.providerCode != null && this.providerCode.equals(other.getProviderCode()))) && ((this.productCode == null && other.getProductCode() == null) || (this.productCode != null && this.productCode.equals(other.getProductCode()))) && ((this.projectCode == null && other.getProjectCode() == null) || (this.projectCode != null && this.projectCode.equals(other.getProjectCode()))) && this.amount == other.getAmount() && ((this.startDate == null && other.getStartDate() == null) || (this.startDate != null && this.startDate.equals(other.getStartDate()))) && ((this.endDate == null && other.getEndDate() == null) || (this.endDate != null && this.endDate.equals(other.getEndDate()))) && ((this.paymentMethod == null && other.getPaymentMethod() == null) || (this.paymentMethod != null && this.paymentMethod.equals(other.getPaymentMethod()))) && this.unitPrice == other.getUnitPrice() && ((this.cardNumber == null && other.getCardNumber() == null) || (this.cardNumber != null && this.cardNumber.equals(other.getCardNumber()))) && ((this.cardType == null && other.getCardType() == null) || (this.cardType != null && this.cardType.equals(other.getCardType()))) && this.cardExpirationMonth == other.getCardExpirationMonth() && this.cardExpirationYear == other.getCardExpirationYear();
		this.__equalsCalc = null;
		return _equals;
	}

	private boolean __hashCodeCalc = false;

	@Override
	public synchronized int hashCode() {
		if (this.__hashCodeCalc) {
			return 0;
		}
		this.__hashCodeCalc = true;
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
		this.__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(BillingEntry.class, true);

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
	public static org.apache.axis.encoding.Serializer getSerializer(java.lang.String mechType, java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanSerializer(_javaType, _xmlType, typeDesc);
	}

	/**
	 * Get Custom Deserializer
	 */
	public static org.apache.axis.encoding.Deserializer getDeserializer(java.lang.String mechType, java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType, _xmlType, typeDesc);
	}

	@Override
	public float getUnits() {
		return this.units;
	}

	@Override
	public void setUnits(float units) {
		this.units = units;
	}

	@Override
	public Object getExtraInformation() {
		return this.extraInformation;
	}

	@Override
	public void setExtraInformation(Object extraInformation) {
		this.extraInformation = extraInformation;
	}

	@Override
	public String toString() {
		return getName() + " (personal ID: " + getPersonalId() + ") amount: " + getAmount();
	}
}