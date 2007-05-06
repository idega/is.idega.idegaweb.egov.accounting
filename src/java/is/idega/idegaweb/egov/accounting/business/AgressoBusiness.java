package is.idega.idegaweb.egov.accounting.business;


import com.idega.business.IBOService;
import java.rmi.RemoteException;

public interface AgressoBusiness extends IBOService {

	/**
	 * @see is.idega.idegaweb.egov.accounting.business.AgressoBusinessBean#executeAfterSchoolCareUpdate
	 */
	public void executeAfterSchoolCareUpdate() throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.accounting.business.AgressoBusinessBean#executeCourseUpdate
	 */
	public void executeCourseUpdate() throws RemoteException;
}