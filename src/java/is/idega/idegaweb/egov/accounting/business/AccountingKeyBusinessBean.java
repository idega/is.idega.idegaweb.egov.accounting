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

import is.idega.idegaweb.egov.accounting.data.AccountingFiles;
import is.idega.idegaweb.egov.accounting.data.AccountingFilesHome;
import is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKey;
import is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKeyHome;
import is.idega.idegaweb.egov.accounting.data.ProductCode;
import is.idega.idegaweb.egov.accounting.data.ProductCodeHome;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.rmi.RemoteException;
import java.sql.Date;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.idega.block.process.data.CaseCode;
import com.idega.block.process.data.CaseCodeHome;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.business.IBOServiceBean;
import com.idega.core.file.data.ICFile;
import com.idega.core.file.data.ICFileHome;
import com.idega.data.IDOLookup;
import com.idega.data.IDOLookupException;
import com.idega.util.IWTimestamp;


public class AccountingKeyBusinessBean extends IBOServiceBean implements AccountingKeyBusiness {

	private CaseCodeAccountingKeyHome getCaseCodeAccountingKeyHome() {
		try {
			return (CaseCodeAccountingKeyHome) IDOLookup.getHome(CaseCodeAccountingKey.class);
		}
		catch (IDOLookupException e) {
			throw new IBORuntimeException(e);
		}
	}
	
	private ProductCodeHome getProductCodeHome() {
		try {
			return (ProductCodeHome) IDOLookup.getHome(ProductCode.class);
		}
		catch (IDOLookupException e) {
			throw new IBORuntimeException(e);
		}
	}
	
	private AccountingFilesHome getAccountingFilesHome() {
		try {
			return (AccountingFilesHome) IDOLookup.getHome(AccountingFiles.class);
		}
		catch (IDOLookupException e) {
			throw new IBORuntimeException(e);
		}
	}

	private CaseCodeHome getCaseCodeHome() {
		try {
			return (CaseCodeHome) IDOLookup.getHome(CaseCode.class);
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
	
	public Collection getAccountingFiles(String caseCode) {
		try {
			CaseCode code = getCaseCodeHome().findByPrimaryKey(caseCode);
			return getAccountingFilesHome().findAllByCaseCode(code);
		}
		catch (FinderException fe) {
			fe.printStackTrace();
			return new ArrayList();
		}
	}
	
	public void removeAccountingFile(Object accountingFilePK) {
		try {
			getAccountingFilesHome().findByPrimaryKey(accountingFilePK).remove();
		}
		catch (FinderException fe) {
			fe.printStackTrace();
		}
		catch (RemoveException re) {
			re.printStackTrace();
		}
	}
	
	public Map getProductKeyMap(CaseCode code) {
		Map map = new HashMap();
		
		try {
			Collection keys = getProductCodeHome().findAllByCaseCode(code);
			Iterator iter = keys.iterator();
			while (iter.hasNext()) {
				ProductCode product = (ProductCode) iter.next();
				map.put(product.getProductKey(), product);
			}
		}
		catch (FinderException fe) {
			fe.printStackTrace();
		}
		
		return map;
	}
	
	public void createAccountingFile(String caseCode, Date month) {
		try {
			CaseCode code = getCaseCodeHome().findByPrimaryKey(caseCode);
			
			AccountingBusinessManager manager = AccountingBusinessManager.getInstance();
			AccountingBusiness b = null;
			try {
				b = manager.getAccountingBusiness(code, getIWApplicationContext());
			}
			catch (IBOLookupException e) {
				e.printStackTrace();
			}
	
			if (b != null && month != null) {
				IWTimestamp fromStamp = new IWTimestamp(month);
				IWTimestamp toStamp = new IWTimestamp(fromStamp);
				toStamp.addMonths(1);
				toStamp.addDays(-1);
	
				try {
					CaseCodeAccountingKey key = getAccountingKey(code);
					Map productCodes = getProductKeyMap(code);
	
					AccountingEntry[] accEntry = b.getAccountingEntries(key.getAccountingKey(), null, fromStamp.getDate(), toStamp.getDate());
					if (accEntry != null && accEntry.length != 0) {
						File tempfile = File.createTempFile(key.getAccountingKey() + "-" + fromStamp.getDateString("MM-yyyy", getIWMainApplication().getDefaultLocale()), ".csv");
						BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempfile), "ISO-8859-1"));
	
						NumberFormat format = NumberFormat.getInstance();
						format.setMaximumFractionDigits(0);
						format.setMinimumFractionDigits(0);
						format.setGroupingUsed(false);
	
						for (int i = 0; i < accEntry.length; i++) {
							AccountingEntry entry = accEntry[i];
							ProductCode product = (ProductCode) productCodes.get(entry.getProductCode());
							
							if (product != null) {
								bWriter.write(product.getAccountingKey());
							}
							else {
								bWriter.write(entry.getProductCode());
							}
							bWriter.write(",");
							bWriter.write(entry.getPayerPersonalId());
							bWriter.write(",");
							bWriter.write(entry.getPersonalId());
							bWriter.write(",");
							if (entry.getUnits() > 0) {
								bWriter.write(String.valueOf(entry.getUnits()));
							}
							else {
								bWriter.write(format.format(entry.getAmount()));
							}
							bWriter.write(",");
							bWriter.write(format.format(entry.getUnitPrice()));
							bWriter.write(",");
							if (entry.getStartDate() == null) {
								bWriter.write(fromStamp.getDateString("dd-MM-yyyy"));
							}
							else {
								IWTimestamp startDate = new IWTimestamp(entry.getStartDate());
								if (startDate.isEarlierThan(fromStamp)) {
									bWriter.write(fromStamp.getDateString("dd-MM-yyyy"));
								}
								else {
									bWriter.write(startDate.getDateString("dd-MM-yyyy"));
								}
							}
							bWriter.write(",");
							if (entry.getEndDate() == null) {
								bWriter.write(toStamp.getDateString("dd-MM-yyyy"));
							}
							else {
								IWTimestamp endDate = new IWTimestamp(entry.getEndDate());
								if (endDate.isLaterThan(toStamp)) {
									bWriter.write(toStamp.getDateString("dd-MM-yyyy"));
								}
								else {
									bWriter.write(endDate.getDateString("dd-MM-yyyy"));
								}
							}
							bWriter.write(",");
							bWriter.write(key.getAccountingKey());
							bWriter.write(",");
							bWriter.write(entry.getProviderCode());
							
							if (entry.getCardNumber() != null) {
								bWriter.write(",");
								bWriter.write(entry.getCardNumber());
								bWriter.write(",");
								bWriter.write(entry.getCardType());
								bWriter.write(",");
								bWriter.write(entry.getCardExpirationMonth());
								bWriter.write(",");
								bWriter.write(entry.getCardExpirationYear());
							}
							else {
								bWriter.write(",,,,");
							}
							
							if (entry.getExtraInformation() != null) {
								bWriter.write(",");
								bWriter.write(entry.getExtraInformation().toString());
							}
							
							bWriter.newLine();
						}
	
						bWriter.close();
	
						ICFile file = ((ICFileHome) IDOLookup.getHome(ICFile.class)).create();
						file.setFileValue(new FileInputStream(tempfile));
						file.setName(tempfile.getName());
						file.store();
	
						AccountingFiles files = getAccountingFilesHome().create();
						files.setCaseCode(code);
						files.setFile(file);
						files.setMonth(fromStamp.getDate());
						files.setCreatedDate(new IWTimestamp().getTimestamp());
						files.store();
					}
				}
				catch (FinderException e) {
					e.printStackTrace();
				}
				catch (RemoteException e) {
					e.printStackTrace();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				catch (CreateException e) {
					e.printStackTrace();
				}
			}
		}
		catch (FinderException fe) {
			fe.printStackTrace();
		}
	}
}