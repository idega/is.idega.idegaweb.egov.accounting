package is.idega.idegaweb.egov.accounting.business;


import javax.ejb.CreateException;
import com.idega.business.IBOHomeImpl;

public class AccountingKeyBusinessHomeImpl extends IBOHomeImpl implements AccountingKeyBusinessHome {

	public Class getBeanInterfaceClass() {
		return AccountingKeyBusiness.class;
	}

	public AccountingKeyBusiness create() throws CreateException {
		return (AccountingKeyBusiness) super.createIBO();
	}
}