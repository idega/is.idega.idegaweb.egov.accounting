/*
 * $Id$
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.accounting.business;

import is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKey;
import is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKeyHome;

import javax.ejb.FinderException;

import com.idega.block.process.data.CaseCode;
import com.idega.business.IBORuntimeException;
import com.idega.business.IBOServiceBean;
import com.idega.data.IDOLookup;
import com.idega.data.IDOLookupException;


public class AccountingKeyBusinessBean extends IBOServiceBean implements AccountingKeyBusiness {

	private CaseCodeAccountingKeyHome getCaseCodeAccountingKeyHome() {
		try {
			return (CaseCodeAccountingKeyHome) IDOLookup.getHome(CaseCodeAccountingKey.class);
		}
		catch (IDOLookupException e) {
			throw new IBORuntimeException(e);
		}
	}
	
	/**
	 * Retrieves the <code>CaseCodeAccountingKey</code> entry for the corresponding <code>CaseCode</code>
	 * 
	 * @param code The <code>CaseCode</code> you want to retrieve the accounting key for.
	 * @return	Returns a <code>CaseCodeAccountingKey</code> entry or throws a <code>FinderException</code> if it doesn't exist.
	 * @throws FinderException
	 */
	public CaseCodeAccountingKey getAccountingKey(CaseCode code) throws FinderException {
		return getCaseCodeAccountingKeyHome().findByPrimaryKey(code);
	}
}