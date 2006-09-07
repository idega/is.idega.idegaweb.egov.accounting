package is.idega.idegaweb.egov.accounting.data;


import com.idega.core.file.data.ICFile;
import com.idega.block.process.data.CaseCode;
import java.sql.Date;
import java.sql.Timestamp;
import com.idega.data.IDOEntity;

public interface AccountingFiles extends IDOEntity {

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.AccountingFilesBMPBean#getCaseCode
	 */
	public CaseCode getCaseCode();

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.AccountingFilesBMPBean#getFile
	 */
	public ICFile getFile();

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.AccountingFilesBMPBean#getCreatedDate
	 */
	public Timestamp getCreatedDate();

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.AccountingFilesBMPBean#getMonth
	 */
	public Date getMonth();

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.AccountingFilesBMPBean#setCaseCode
	 */
	public void setCaseCode(CaseCode code);

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.AccountingFilesBMPBean#setFile
	 */
	public void setFile(ICFile file);

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.AccountingFilesBMPBean#setCreatedDate
	 */
	public void setCreatedDate(Timestamp createdDate);

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.AccountingFilesBMPBean#setMonth
	 */
	public void setMonth(Date month);
}