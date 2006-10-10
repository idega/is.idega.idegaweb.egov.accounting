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

import is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKey;

import com.idega.idegaweb.IWApplicationContext;
import com.idega.util.IWTimestamp;


public interface AccountingStringResult {

	public String toString(IWApplicationContext iwac, AccountingEntry entry, CaseCodeAccountingKey key, IWTimestamp fromStamp, IWTimestamp toStamp);

}