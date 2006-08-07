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

	public void start(IWBundle starterBundle) {
		ImplementorRepository.getInstance().addImplementor(AccountingEntry.class, BillingEntry.class);
	}

	public void stop(IWBundle starterBundle) {
	}

}
