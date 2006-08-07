/**
 * AccountingServiceSOAPImpl.java
 * 
 * This file was auto-generated from WSDL by the Apache Axis 1.3 Oct 05, 2005
 * (05:23:37 EDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.accounting.wsimpl;

import is.idega.idegaweb.egov.accounting.business.AccountingBusiness;
import is.idega.idegaweb.egov.accounting.business.AccountingBusinessManager;
import is.idega.idegaweb.egov.accounting.business.AccountingEntry;
import is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKey;
import is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKeyHome;

import javax.ejb.FinderException;

import com.idega.block.process.data.CaseCode;
import com.idega.data.IDOLookup;
import com.idega.data.IDOLookupException;
import com.idega.idegaweb.IWMainApplication;
import com.idega.util.IWTimestamp;

public class AccountingServiceSOAPImpl implements is.idega.idegaweb.egov.accounting.wsimpl.AccountingService_PortType {

	public AccountingEntry[] getBillingEntries(is.idega.idegaweb.egov.accounting.wsimpl.BillingEntriesRequest getBillingEntriesRequest) throws java.rmi.RemoteException {
		try {
			CaseCodeAccountingKeyHome home = (CaseCodeAccountingKeyHome) IDOLookup.getHome(CaseCodeAccountingKey.class);
			CaseCodeAccountingKey key = home.findByAccountingKey(getBillingEntriesRequest.getServiceCode());
			
			CaseCode code = key.getCaseCode();
			AccountingBusiness business = AccountingBusinessManager.getInstance().getAccountingBusinessOrDefault(code, IWMainApplication.getDefaultIWApplicationContext());
			
			IWTimestamp periodStart = new IWTimestamp(getBillingEntriesRequest.getPeriodStart());
			IWTimestamp periodEnd = new IWTimestamp(getBillingEntriesRequest.getPeriodEnd());
			
			if (business != null) {
				return business.getAccountingEntries(getBillingEntriesRequest.getServiceCode(), getBillingEntriesRequest.getProviderCode(), periodStart.getDate(), periodEnd.getDate());
			}
		}
		catch (FinderException fe) {
			fe.printStackTrace();
		}
		catch (IDOLookupException ile) {
			ile.printStackTrace();
		}
		
		return null;
	}
}