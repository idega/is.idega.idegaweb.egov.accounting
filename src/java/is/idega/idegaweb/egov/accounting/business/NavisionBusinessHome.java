package is.idega.idegaweb.egov.accounting.business;


import java.rmi.RemoteException;

import javax.ejb.CreateException;

import com.idega.business.IBOHome;

public interface NavisionBusinessHome extends IBOHome {
	public NavisionBusiness create() throws CreateException, RemoteException;
}