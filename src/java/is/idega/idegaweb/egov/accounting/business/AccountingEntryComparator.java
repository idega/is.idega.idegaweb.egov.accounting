/*
 * $Id$
 * Created on Dec 19, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.accounting.business;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;


public class AccountingEntryComparator implements Comparator {

	private Locale locale;
	
	public AccountingEntryComparator(Locale locale) {
		this.locale = locale;
	}
	
	public int compare(Object obj1, Object obj2) {
		AccountingEntry entry1 = (AccountingEntry) obj1;
		AccountingEntry entry2 = (AccountingEntry) obj2;
		
		if (entry1.getName() == null && entry2.getName() != null) {
			return 1;
		}
		else if (entry1.getName() != null && entry2.getName() == null) {
			return -1;
		}
		else if (entry1.getName() == null && entry2.getName() == null) {
			return 0;
		}
		
		return Collator.getInstance(this.locale).compare(entry1.getName(), entry2.getName());
	}

}
