package is.idega.idegaweb.egov.accounting.business;


import com.idega.block.school.data.School;
import java.util.Map;
import javax.ejb.CreateException;
import com.idega.block.process.data.CaseCode;
import java.sql.Date;
import java.rmi.RemoteException;
import is.idega.idegaweb.egov.accounting.data.SchoolCode;
import java.util.Collection;
import javax.ejb.FinderException;
import com.idega.block.school.data.SchoolType;
import com.idega.business.IBOService;
import is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKey;

public interface AccountingKeyBusiness extends IBOService {

	/**
	 * @see is.idega.idegaweb.egov.accounting.business.AccountingKeyBusinessBean#getAccountingKey
	 */
	public CaseCodeAccountingKey getAccountingKey(CaseCode code) throws FinderException, RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.accounting.business.AccountingKeyBusinessBean#getAccountingFiles
	 */
	public Collection getAccountingFiles(String caseCode) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.accounting.business.AccountingKeyBusinessBean#removeAccountingFile
	 */
	public void removeAccountingFile(Object accountingFilePK) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.accounting.business.AccountingKeyBusinessBean#getProductKeyMap
	 */
	public Map getProductKeyMap(CaseCode code) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.accounting.business.AccountingKeyBusinessBean#getSchoolProductKeyMap
	 */
	public Map getSchoolProductKeyMap() throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.accounting.business.AccountingKeyBusinessBean#storeSchoolCode
	 */
	public void storeSchoolCode(Object schoolPK, Object typePK, String accountingKey) throws CreateException, RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.accounting.business.AccountingKeyBusinessBean#storeSchoolCode
	 */
	public void storeSchoolCode(School school, SchoolType type, String accountingKey) throws CreateException, RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.accounting.business.AccountingKeyBusinessBean#getSchoolCodes
	 */
	public Collection getSchoolCodes(School school) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.accounting.business.AccountingKeyBusinessBean#getSchoolCode
	 */
	public SchoolCode getSchoolCode(School school, SchoolType type) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.accounting.business.AccountingKeyBusinessBean#createAccountingFile
	 */
	public void createAccountingFile(String caseCode, Date month) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.accounting.business.AccountingKeyBusinessBean#generateAccountingString
	 */
	public void generateAccountingString(String caseCode, Date month, boolean createFile) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.accounting.business.AccountingKeyBusinessBean#createAccountingFile
	 */
	public void createAccountingFile(String caseCode, Date from, Date to) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.accounting.business.AccountingKeyBusinessBean#generateAccountingString
	 */
	public void generateAccountingString(String caseCode, Date from, Date to, boolean createFile) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.accounting.business.AccountingKeyBusinessBean#generateAccountingString
	 */
	public void generateAccountingString(CaseCode code, Date month, boolean createFile) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.accounting.business.AccountingKeyBusinessBean#generateAccountingString
	 */
	public void generateAccountingString(CaseCode code, Date from, Date to, boolean createFile) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.accounting.business.AccountingKeyBusinessBean#getAccountingEntries
	 */
	public AccountingEntry[] getAccountingEntries(CaseCode code, Date from, Date to) throws FinderException, RemoteException;
}