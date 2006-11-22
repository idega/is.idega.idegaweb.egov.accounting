package is.idega.idegaweb.egov.accounting.business;


import javax.ejb.CreateException;
import com.idega.business.IBOHome;
import java.rmi.RemoteException;

public interface AccountingKeyBusinessHome extends IBOHome {
	public AccountingKeyBusiness create() throws CreateException, RemoteException;
}