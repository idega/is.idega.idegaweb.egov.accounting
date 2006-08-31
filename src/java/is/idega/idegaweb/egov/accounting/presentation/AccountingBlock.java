package is.idega.idegaweb.egov.accounting.presentation;

import is.idega.idegaweb.egov.accounting.business.AccountingBusiness;
import is.idega.idegaweb.egov.accounting.business.AccountingKeyBusiness;

import com.idega.block.process.data.CaseCode;
import com.idega.block.process.data.CaseCodeHome;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.data.IDOLookup;
import com.idega.data.IDOLookupException;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.Block;
import com.idega.presentation.IWContext;

public abstract class AccountingBlock extends Block {
	protected IWResourceBundle iwrb = null;

	protected IWBundle iwb = null;

	public static final String ACCOUNTING_BLOCK_BUNDLE_IDENTIFIER = "is.idega.idegaweb.egov.healthcare";

	public String getBundleIdentifier() {
		return AccountingBlock.ACCOUNTING_BLOCK_BUNDLE_IDENTIFIER;
	}

	public void main(IWContext iwc) {
		this.iwb = getBundle(iwc);
		this.iwrb = getResourceBundle(iwc);
		present(iwc);
	}
	
	protected abstract void present(IWContext iwc);
	
	protected AccountingBusiness getAccountingBusiness(IWContext iwc) {
		try {
			return (AccountingBusiness) IBOLookup.getServiceInstance(iwc, AccountingBusiness.class);
		}
		catch (IBOLookupException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	protected AccountingKeyBusiness getAccountingKeyBusiness(IWContext iwc) {
		try {
			return (AccountingKeyBusiness) IBOLookup.getServiceInstance(iwc, AccountingKeyBusiness.class);
		}
		catch (IBOLookupException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	protected CaseCodeHome getCaseCodeHome() {
		try {
			return (CaseCodeHome) IDOLookup.getHome(CaseCode.class);
		}
		catch (IDOLookupException e) {
			throw new IBORuntimeException(e);
		}
	}
}