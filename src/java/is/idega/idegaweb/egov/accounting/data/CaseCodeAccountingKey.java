package is.idega.idegaweb.egov.accounting.data;


import com.idega.block.process.data.CaseCode;
import com.idega.data.IDOEntity;

public interface CaseCodeAccountingKey extends IDOEntity {

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKeyBMPBean#getPrimaryKeyClass
	 */
	public Class getPrimaryKeyClass();

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKeyBMPBean#getCaseCode
	 */
	public CaseCode getCaseCode();

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKeyBMPBean#getAccountingKey
	 */
	public String getAccountingKey();

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKeyBMPBean#getDescription
	 */
	public String getDescription();

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKeyBMPBean#setCaseCode
	 */
	public void setCaseCode(CaseCode code);

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKeyBMPBean#setAccountingKey
	 */
	public void setAccountingKey(String key);

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKeyBMPBean#setDescription
	 */
	public void setDescription(String description);
}