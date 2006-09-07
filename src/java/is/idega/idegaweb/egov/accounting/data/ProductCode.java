package is.idega.idegaweb.egov.accounting.data;


import com.idega.block.process.data.CaseCode;
import com.idega.data.IDOEntity;

public interface ProductCode extends IDOEntity {

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.ProductCodeBMPBean#getCaseCode
	 */
	public CaseCode getCaseCode();

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.ProductCodeBMPBean#getProductKey
	 */
	public String getProductKey();

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.ProductCodeBMPBean#getAccountingKey
	 */
	public String getAccountingKey();

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.ProductCodeBMPBean#getDescription
	 */
	public String getDescription();

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.ProductCodeBMPBean#setCaseCode
	 */
	public void setCaseCode(CaseCode code);

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.ProductCodeBMPBean#setProductKey
	 */
	public void setProductKey(String key);

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.ProductCodeBMPBean#setAccountingKey
	 */
	public void setAccountingKey(String key);

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.ProductCodeBMPBean#setDescription
	 */
	public void setDescription(String description);
}