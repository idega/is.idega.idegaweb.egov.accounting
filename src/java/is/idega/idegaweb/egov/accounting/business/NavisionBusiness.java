package is.idega.idegaweb.egov.accounting.business;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import com.idega.business.IBOService;
import com.idega.util.IWTimestamp;

public interface NavisionBusiness extends IBOService, ActionListener {
	/**
	 * @see is.idega.idegaweb.egov.accounting.business.NavisionBusinessBean#sendAllAccountingEntriesForMonth
	 */
	public void sendAllAccountingEntriesForMonth(IWTimestamp stamp) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.accounting.business.NavisionBusinessBean#actionPerformed
	 */
	public void actionPerformed(ActionEvent e);

	/**
	 * @see is.idega.idegaweb.egov.accounting.business.NavisionBusinessBean#isLastDayOfMonth
	 */
	public boolean isLastDayOfMonth(IWTimestamp stamp) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.accounting.business.NavisionBusinessBean#hasBeenSentForMonth
	 */
	public boolean hasBeenSentForMonth(IWTimestamp stamp) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.accounting.business.NavisionBusinessBean#isActive
	 */
	public boolean isActive() throws RemoteException;
}