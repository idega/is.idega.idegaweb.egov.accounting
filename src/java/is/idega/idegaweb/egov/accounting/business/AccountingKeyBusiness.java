package is.idega.idegaweb.egov.accounting.business;


import com.idega.block.process.data.CaseCode;
import javax.ejb.FinderException;
import com.idega.business.IBOService;
import java.rmi.RemoteException;
import is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKey;

public interface AccountingKeyBusiness extends IBOService {

	/**
	 * @see is.idega.idegaweb.egov.accounting.business.AccountingKeyBusinessBean#getAccountingKey
	 */
	public CaseCodeAccountingKey getAccountingKey(CaseCode code) throws FinderException, RemoteException;
}