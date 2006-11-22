package is.idega.idegaweb.egov.accounting.business;


import javax.ejb.CreateException;

import com.idega.business.IBOHomeImpl;

public class NavisionBusinessHomeImpl extends IBOHomeImpl implements NavisionBusinessHome {
	public Class getBeanInterfaceClass() {
		return NavisionBusiness.class;
	}

	public NavisionBusiness create() throws CreateException {
		return (NavisionBusiness) super.createIBO();
	}
}