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
import is.idega.idegaweb.egov.accounting.business.NavisionBusiness;
import is.idega.idegaweb.egov.accounting.business.NavisionStringResult;
import is.idega.idegaweb.egov.accounting.business.NavisionXMLStringResult;
import is.idega.idegaweb.egov.accounting.business.SFSStringResult;
import is.idega.idegaweb.egov.accounting.wsimpl.BillingEntry;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWBundleStartable;
import com.idega.repository.data.ImplementorRepository;
import com.idega.util.EventTimer;


public class IWBundleStarter implements IWBundleStartable {

	RvkAgressoUpdater agressoDaemon;


	public void start(IWBundle starterBundle) {
		ImplementorRepository.getInstance().addImplementor(AccountingEntry.class, BillingEntry.class);

		AccountingSystemManager.getInstance().addAccountingStringResult(AccountingConstants.ACCOUNTING_SYSTEM_NAVISION, NavisionStringResult.class);		
		AccountingSystemManager.getInstance().addAccountingStringResult(AccountingConstants.ACCOUNTING_SYSTEM_NAVISION_XML, NavisionXMLStringResult.class);
		AccountingSystemManager.getInstance().addAccountingStringResult(AccountingConstants.ACCOUNTING_SYSTEM_SFS, SFSStringResult.class);

		startAgressoDaemon(starterBundle);

		startMaritechNavisionDaemon(starterBundle);

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


	/**
	 * Starts the maritech navision accountingkey webservice updater
	 * @param starterBundle
	 */
	protected void startMaritechNavisionDaemon(IWBundle starterBundle){
		IWApplicationContext iwac = starterBundle.getApplication().getIWApplicationContext();
		try {
			NavisionBusiness navBiz = (NavisionBusiness) IBOLookup.getServiceInstance(iwac, NavisionBusiness.class);
			EventTimer navTimer = new EventTimer(EventTimer.THREAD_SLEEP_24_HOURS/4,AccountingConstants.ACCOUNTING_SYSTEM_NAVISION_XML);
	//testing		EventTimer navTimer = new EventTimer(EventTimer.THREAD_SLEEP_2_MINUTES,AccountingConstants.ACCOUNTING_SYSTEM_NAVISION_XML);

			navTimer.addActionListener(navBiz);
			navTimer.start(EventTimer.THREAD_SLEEP_1_MINUTE*3);

		} catch (IBOLookupException e1) {
			e1.printStackTrace();
		}

	}

}
