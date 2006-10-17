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

import java.text.NumberFormat;

import com.idega.idegaweb.IWApplicationContext;
import com.idega.util.IWTimestamp;


public class NavisionStringResult implements AccountingStringResult {

	public String toString(IWApplicationContext iwac, AccountingEntry entry, CaseCodeAccountingKey key, IWTimestamp fromStamp, IWTimestamp toStamp) {
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(0);
		format.setMinimumFractionDigits(0);
		format.setGroupingUsed(false);

		StringBuffer buffer = new StringBuffer();
		
		buffer.append(entry.getProductCode());
		buffer.append(",");
		buffer.append(entry.getPayerPersonalId());
		buffer.append(",");
		buffer.append(entry.getPersonalId());
		buffer.append(",");
		if (entry.getUnits() > 0) {
			buffer.append(String.valueOf(entry.getUnits()));
		}
		else {
			buffer.append(format.format(entry.getAmount()));
		}
		buffer.append(",");
		buffer.append(format.format(entry.getUnitPrice()));
		buffer.append(",");
		if (entry.getStartDate() == null) {
			buffer.append(fromStamp.getDateString("dd-MM-yyyy"));
		}
		else {
			IWTimestamp startDate = new IWTimestamp(entry.getStartDate());
			if (startDate.isEarlierThan(fromStamp)) {
				buffer.append(fromStamp.getDateString("dd-MM-yyyy"));
			}
			else {
				buffer.append(startDate.getDateString("dd-MM-yyyy"));
			}
		}
		buffer.append(",");
		if (entry.getEndDate() == null) {
			buffer.append(toStamp.getDateString("dd-MM-yyyy"));
		}
		else {
			IWTimestamp endDate = new IWTimestamp(entry.getEndDate());
			if (endDate.isLaterThan(toStamp)) {
				buffer.append(toStamp.getDateString("dd-MM-yyyy"));
			}
			else {
				buffer.append(endDate.getDateString("dd-MM-yyyy"));
			}
		}
		buffer.append(",");
		buffer.append(key.getAccountingKey());
		buffer.append(",");
		buffer.append(entry.getProviderCode());
		
		if (entry.getCardNumber() != null) {
			buffer.append(",");
			buffer.append(entry.getCardNumber());
			buffer.append(",");
			buffer.append(entry.getCardType());
			buffer.append(",");
			buffer.append(Integer.toString(entry.getCardExpirationMonth()));
			buffer.append(",");
			buffer.append(Integer.toString(entry.getCardExpirationYear()));
		}
		else {
			buffer.append(",,,,");
		}
		
		if (entry.getExtraInformation() != null) {
			buffer.append(",");
			buffer.append(entry.getExtraInformation().toString());
		}
		
		return buffer.toString();
	}
}