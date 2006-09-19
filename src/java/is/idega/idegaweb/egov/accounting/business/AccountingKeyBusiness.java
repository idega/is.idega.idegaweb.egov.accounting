package is.idega.idegaweb.egov.accounting.business;


import java.util.Collection;
import java.util.Map;
import com.idega.block.process.data.CaseCode;
import javax.ejb.FinderException;
import com.idega.business.IBOService;
import java.sql.Date;
import java.rmi.RemoteException;
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
	 * @see is.idega.idegaweb.egov.accounting.business.AccountingKeyBusinessBean#createAccountingFile
	 */
	public void createAccountingFile(String caseCode, Date month) throws RemoteException;
}