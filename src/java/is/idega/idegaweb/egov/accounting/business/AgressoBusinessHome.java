package is.idega.idegaweb.egov.accounting.business;


import javax.ejb.CreateException;
import com.idega.business.IBOHome;
import java.rmi.RemoteException;

public interface AgressoBusinessHome extends IBOHome {

	public AgressoBusiness create() throws CreateException, RemoteException;
}