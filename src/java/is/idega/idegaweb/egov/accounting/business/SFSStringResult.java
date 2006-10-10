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

import java.rmi.RemoteException;
import java.text.NumberFormat;

import javax.ejb.FinderException;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.user.business.UserBusiness;
import com.idega.user.data.User;
import com.idega.util.IWTimestamp;


public class SFSStringResult implements AccountingStringResult {

	public String toString(IWApplicationContext iwac, AccountingEntry entry, CaseCodeAccountingKey key, IWTimestamp fromStamp, IWTimestamp toStamp) {
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(0);
		format.setMinimumFractionDigits(0);
		format.setGroupingUsed(false);

		StringBuffer buffer = new StringBuffer();

		buffer.append(entry.getPayerPersonalId());
		buffer.append(",");
		buffer.append(key.getAccountingKey());
		buffer.append(",");
		buffer.append(entry.getProductCode());
		buffer.append(",");
		if (entry.getUnits() > 0) {
			buffer.append(String.valueOf(entry.getUnits()));
		}
		else {
			buffer.append(format.format(entry.getAmount()));
		}
		buffer.append(",");
		buffer.append(entry.getPersonalId());

		try {
			User user = getUserBusiness(iwac).getUser(entry.getPersonalId());
			buffer.append(",").append(user.getFirstName());
			if (user.getMiddleName() != null && user.getMiddleName().length() > 0) {
				buffer.append(" ").append(user.getMiddleName());
			}
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
		catch (FinderException e) {
			e.printStackTrace();
		}
		
		return buffer.toString();
	}
	
	private UserBusiness getUserBusiness(IWApplicationContext iwac) {
		try {
			return (UserBusiness) IBOLookup.getServiceInstance(iwac, UserBusiness.class);
		}
		catch (IBOLookupException e) {
			throw new IBORuntimeException(e);
		}
	}
}