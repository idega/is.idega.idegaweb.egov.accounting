/*
 * $Id$
 * Created on Oct 9, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.accounting.business;

import java.util.HashMap;
import java.util.Map;


public class AccountingSystemManager {

	private static AccountingSystemManager handicapClassManager = null;
	
	private Map accountingStringMap = null;

	public static AccountingSystemManager getInstance() { 
		if (AccountingSystemManager.handicapClassManager == null) {
			AccountingSystemManager.handicapClassManager = new AccountingSystemManager();
		}
		return AccountingSystemManager.handicapClassManager;
	}
	
	public void addAccountingStringResult(String accountingSystem, Class accountingStringResultImplementation) {
		getAccountingStringMap().put(accountingSystem, accountingStringResultImplementation);
	}
	
	public AccountingStringResult getAccountingStringResult(String accountingSystem) {
		Class accountingStringResultImplementation = (Class) getAccountingStringMap().get(accountingSystem);
		if (accountingStringResultImplementation != null) {
			try {
				return (AccountingStringResult) accountingStringResultImplementation.newInstance();
			}
			catch (IllegalAccessException iae) {
				iae.printStackTrace();
			}
			catch (InstantiationException ie) {
				ie.printStackTrace();
			}
		}
		
		return null;
	}

	private Map getAccountingStringMap() {
		if (this.accountingStringMap == null) {
			this.accountingStringMap = new HashMap();
		}
		return this.accountingStringMap;
	}
}