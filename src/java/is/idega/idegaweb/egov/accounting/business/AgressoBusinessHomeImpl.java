package is.idega.idegaweb.egov.accounting.business;


import javax.ejb.CreateException;
import com.idega.business.IBOHomeImpl;

public class AgressoBusinessHomeImpl extends IBOHomeImpl implements AgressoBusinessHome {

	public Class getBeanInterfaceClass() {
		return AgressoBusiness.class;
	}

	public AgressoBusiness create() throws CreateException {
		return (AgressoBusiness) super.createIBO();
	}
}