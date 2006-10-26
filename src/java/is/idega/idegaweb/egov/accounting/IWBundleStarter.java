/*
 * $Id$
 * Created on Jul 25, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.accounting;

import is.idega.idegaweb.egov.accounting.business.AccountingConstants;
import is.idega.idegaweb.egov.accounting.business.AccountingEntry;
import is.idega.idegaweb.egov.accounting.business.AccountingSystemManager;
import is.idega.idegaweb.egov.accounting.business.NavisionStringResult;
import is.idega.idegaweb.egov.accounting.business.NavisionXMLStringResult;
import is.idega.idegaweb.egov.accounting.business.SFSStringResult;
import is.idega.idegaweb.egov.accounting.wsimpl.BillingEntry;
import is.idega.idegaweb.egov.accounting.wsimpl.U_getQueryXMLResponseU_getQueryXMLResult;
import is.idega.idegaweb.egov.accounting.wsimpl.WiseWebServiceLocator;
import is.idega.idegaweb.egov.accounting.wsimpl.WiseWebServiceSoap_PortType;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWBundleStartable;
import com.idega.repository.data.ImplementorRepository;
import com.idega.util.timer.PastDateException;
import com.idega.util.timer.TimerEntry;
import com.idega.util.timer.TimerListener;
import com.idega.util.timer.TimerManager;


public class IWBundleStarter implements IWBundleStartable {

	RvkAgressoUpdater agressoDaemon;
	private TimerEntry maritechTimerEntry = null;


	public void start(IWBundle starterBundle) {
		ImplementorRepository.getInstance().addImplementor(AccountingEntry.class, BillingEntry.class);

		AccountingSystemManager.getInstance().addAccountingStringResult(AccountingConstants.ACCOUNTING_SYSTEM_NAVISION, NavisionStringResult.class);		
		AccountingSystemManager.getInstance().addAccountingStringResult(AccountingConstants.ACCOUNTING_SYSTEM_NAVISION_XML, NavisionXMLStringResult.class);
		AccountingSystemManager.getInstance().addAccountingStringResult(AccountingConstants.ACCOUNTING_SYSTEM_SFS, SFSStringResult.class);

		startAgressoDaemon(starterBundle);

		//startMaritechNavisionDaemon(starterBundle);

	}

	/**
	 * <p>
	 * TODO tryggvil describe method startSchoolChoiceSenderDaemon
	 * </p>
	 * @param starterBundle
	 */
	protected void startAgressoDaemon(IWBundle starterBundle) {
		String prop = starterBundle.getApplication().getSettings().getProperty("rvk.agressoupdate.enable");
		if(prop!=null){
			Boolean enabled = Boolean.valueOf(prop);
			if(enabled.booleanValue()){
				this.agressoDaemon = new RvkAgressoUpdater(starterBundle.getApplication());
				Thread thread = new Thread(this.agressoDaemon);
				thread.setName("RvkAgressoUpdater");
				thread.setDaemon(true);
				this.agressoDaemon.setThread(thread);
				thread.start();
			}
		}
	}

	public void stop(IWBundle starterBundle) {
		if(this.agressoDaemon!=null){
			this.agressoDaemon.stop();
			this.agressoDaemon=null;
		}
	}


	protected void startMaritechNavisionDaemon(IWBundle starterBundle){
		String prop = starterBundle.getApplication().getSettings().getProperty("maritech.navision.enable");
		if(prop!=null){

			TimerManager tManager = new TimerManager();
			try {
				this.maritechTimerEntry = tManager.addTimer(0,4,-1,-1,-1,-1, new TimerListener() {


					public void handleTimer(TimerEntry entry) {
						//check if today is the last day of the month

						//if so call the webservice for each casecodekey and account entry for that
						String maritechAddress = "http://postur.arborg.is:81/ApprovalsWS/Wisews.asmx";

						try {
							WiseWebServiceLocator wwLocator = new WiseWebServiceLocator();
							wwLocator.setWiseWebServiceSoapEndpointAddress(maritechAddress);

							WiseWebServiceSoap_PortType maritechService = wwLocator.getWiseWebServiceSoap();
							//maritechService.u_getQueryXML("", sPort, stUseCaseCode, stUser, stSessionKey, stCompanyName, stXML)
							
							//get all accountkeycodes and iterate the method
							
							U_getQueryXMLResponseU_getQueryXMLResult result = maritechService.u_getQueryXML("172.20.1.11", "8049", "", "TM", "", "�Árborg-EKKI NOTA-Afrit","");

							System.out.println("Navision sync result :"+result.toString());


							//set last finished update in app property

							//send user message / email to cashier at maritech, email as bundle property?


						} catch (RemoteException e) {
							e.printStackTrace();
							System.err.println("Navision syncing FAILED!! ERROR:"+e.getMessage());
						} catch (ServiceException e) {
							e.printStackTrace();
							System.err.println("Navision syncing FAILED!! ERROR:"+e.getMessage());
						}





					}
				});
			}
			catch (PastDateException e) {
				//kill the timerentry pollTimerEntry = null;
				e.printStackTrace();
			}

		}
	}

}
