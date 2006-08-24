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

import is.idega.idegaweb.egov.accounting.business.AccountingEntry;
import is.idega.idegaweb.egov.accounting.wsimpl.BillingEntry;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWBundleStartable;
import com.idega.repository.data.ImplementorRepository;


public class IWBundleStarter implements IWBundleStartable {

	RvkAgressoUpdater agressoDaemon;
	
	public void start(IWBundle starterBundle) {
		ImplementorRepository.getInstance().addImplementor(AccountingEntry.class, BillingEntry.class);
		
		startAgressoDaemon(starterBundle);
		
	}

	/**
	 * <p>
	 * TODO tryggvil describe method startSchoolChoiceSenderDaemon
	 * </p>
	 * @param starterBundle
	 */
	private void startAgressoDaemon(IWBundle starterBundle) {
		String prop = starterBundle.getApplication().getSettings().getProperty("rvk.agressoupdate.enable");
		if(prop!=null){
			Boolean enabled = Boolean.valueOf(prop);
			if(enabled.booleanValue()){
				this.agressoDaemon = new RvkAgressoUpdater(starterBundle.getApplication());
				Thread thread = new Thread(this.agressoDaemon);
				thread.setName("RvkAgressoUpdater");
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

}
