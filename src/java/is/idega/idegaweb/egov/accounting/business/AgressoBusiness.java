package is.idega.idegaweb.egov.accounting.business;


import com.idega.business.IBOService;
import java.rmi.RemoteException;

public interface AgressoBusiness extends IBOService {

	/**
	 * @see is.idega.idegaweb.egov.accounting.business.AgressoBusinessBean#executeUpdate
	 */
	public void executeUpdate() throws RemoteException;
}