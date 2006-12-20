/*
 * $Id$ Created on Jul 12, 2006
 * 
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.accounting.business;

import is.idega.idegaweb.egov.accounting.data.AccountingFiles;
import is.idega.idegaweb.egov.accounting.data.AccountingFilesHome;
import is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKey;
import is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKeyHome;
import is.idega.idegaweb.egov.accounting.data.ProductCode;
import is.idega.idegaweb.egov.accounting.data.ProductCodeHome;
import is.idega.idegaweb.egov.accounting.data.SchoolProductCode;
import is.idega.idegaweb.egov.accounting.data.SchoolProductCodeHome;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.rmi.RemoteException;
import java.sql.Date;
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
import com.idega.block.school.business.SchoolBusiness;
import com.idega.block.school.data.School;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.business.IBOServiceBean;
import com.idega.core.file.data.ICFile;
import com.idega.core.file.data.ICFileHome;
import com.idega.data.IDOLookup;
import com.idega.data.IDOLookupException;
import com.idega.util.IWTimestamp;

public class AccountingKeyBusinessBean extends IBOServiceBean implements AccountingKeyBusiness {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 4569648374518077672L;

	protected CaseCodeAccountingKeyHome getCaseCodeAccountingKeyHome() {
		try {
			return (CaseCodeAccountingKeyHome) IDOLookup.getHome(CaseCodeAccountingKey.class);
		}
		catch (IDOLookupException e) {
			throw new IBORuntimeException(e);
		}
	}

	protected ProductCodeHome getProductCodeHome() {
		try {
			return (ProductCodeHome) IDOLookup.getHome(ProductCode.class);
		}
		catch (IDOLookupException e) {
			throw new IBORuntimeException(e);
		}
	}

	protected AccountingFilesHome getAccountingFilesHome() {
		try {
			return (AccountingFilesHome) IDOLookup.getHome(AccountingFiles.class);
		}
		catch (IDOLookupException e) {
			throw new IBORuntimeException(e);
		}
	}

	protected CaseCodeHome getCaseCodeHome() {
		try {
			return (CaseCodeHome) IDOLookup.getHome(CaseCode.class);
		}
		catch (IDOLookupException e) {
			throw new IBORuntimeException(e);
		}
	}

	protected SchoolProductCodeHome getSchoolProductCodeHome() {
		try {
			return (SchoolProductCodeHome) IDOLookup.getHome(SchoolProductCode.class);
		}
		catch (IDOLookupException e) {
			throw new IBORuntimeException(e);
		}
	}

	protected SchoolBusiness getSchoolBusiness() {
		try {
			return (SchoolBusiness) IBOLookup.getServiceInstance(getIWApplicationContext(), SchoolBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	/**
	 * Retrieves the <code>CaseCodeAccountingKey</code> entry for the corresponding <code>CaseCode</code>
	 * 
	 * @param code
	 *          The <code>CaseCode</code> you want to retrieve the accounting key for.
	 * @return Returns a <code>CaseCodeAccountingKey</code> entry or throws a <code>FinderException</code> if it doesn't exist.
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

	public Map getSchoolProductKeyMap() {
		Map map = new HashMap();

		try {
			Collection schools = getSchoolBusiness().findAllSchools();

			Iterator iter = schools.iterator();
			while (iter.hasNext()) {
				School school = (School) iter.next();

				try {
					Collection codes = getSchoolProductCodeHome().findAllBySchool(school);
					if (!codes.isEmpty()) {
						Map schoolMap = new HashMap();
						Iterator iterator = codes.iterator();
						while (iterator.hasNext()) {
							SchoolProductCode code = (SchoolProductCode) iterator.next();
							schoolMap.put(code.getProductCode(), code.getSchoolProductCode());
						}

						map.put(school.getOrganizationNumber(), schoolMap);
					}
				}
				catch (FinderException e) {
					e.printStackTrace();
				}
			}
		}
		catch (RemoteException e) {
			throw new IBORuntimeException(e);
		}

		return map;
	}

	public void createAccountingFile(String caseCode, Date month) {
		generateAccountingString(caseCode, month, true);
	}

	public void generateAccountingString(String caseCode, Date month, boolean createFile) {
		CaseCode code;
		try {
			code = getCaseCodeHome().findByPrimaryKey(caseCode);
			generateAccountingString(code, month, createFile);
		}
		catch (FinderException e) {
			e.printStackTrace();
		}
	}

	public void createAccountingFile(String caseCode, Date from, Date to) {
		generateAccountingString(caseCode, from, to, true);
	}
	
	public void generateAccountingString(String caseCode, Date from, Date to, boolean createFile) {
		CaseCode code;
		try {
			code = getCaseCodeHome().findByPrimaryKey(caseCode);
			generateAccountingString(code, from, to, createFile);
		}
		catch (FinderException e) {
			e.printStackTrace();
		}
	}

	public void generateAccountingString(CaseCode code, Date month, boolean createFile) {
		IWTimestamp fromStamp = new IWTimestamp(month);
		fromStamp.setDay(1);
		IWTimestamp toStamp = new IWTimestamp(fromStamp);
		toStamp.addMonths(1);
		toStamp.addDays(-1);

		generateAccountingString(code, fromStamp.getDate(), toStamp.getDate(), createFile);
	}

	public void generateAccountingString(CaseCode code, Date from, Date to, boolean createFile) {
		if (from != null && to != null) {
			try {
				CaseCodeAccountingKey key = getAccountingKey(code);
				Map productCodes = getProductKeyMap(code);
				Map schoolProductCodes = getSchoolProductKeyMap();
				String accountingSystem = this.getIWApplicationContext().getApplicationSettings().getProperty(AccountingConstants.PROPERTY_ACCOUNTING_SYSTEM, AccountingConstants.ACCOUNTING_SYSTEM_NAVISION);

				AccountingEntry[] accEntry = getAccountingEntries(code, from, to);
				if (accEntry != null && accEntry.length != 0) {

					IWTimestamp fromStamp = new IWTimestamp(from);
					IWTimestamp toStamp = new IWTimestamp(to);
					
					BufferedWriter bWriter = null;
					File tempfile = null;
					if (createFile) {
						tempfile = File.createTempFile(key.getAccountingKey() + "-" + fromStamp.getDateString("dd.MM.yyyy", getIWMainApplication().getDefaultLocale()) + "-" + toStamp.getDateString("dd.MM.yyyy", getIWMainApplication().getDefaultLocale()), ".csv");
						bWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempfile), "ISO-8859-1"));
					}

					for (int i = 0; i < accEntry.length; i++) {
						AccountingEntry entry = accEntry[i];
						ProductCode product = (ProductCode) productCodes.get(entry.getProductCode());
						Map schoolProduct = (Map) schoolProductCodes.get(entry.getProviderCode());

						if (schoolProduct != null && schoolProduct.containsKey(entry.getProductCode())) {
							entry.setProductCode((String) schoolProduct.get(entry.getProductCode()));
						}
						else if (product != null) {
							entry.setProductCode(product.getAccountingKey());
						}

						AccountingStringResult result = AccountingSystemManager.getInstance().getAccountingStringResult(accountingSystem);
						String resultString = result.toString(getIWApplicationContext(), entry, key, fromStamp, toStamp);

						if (createFile) {
							bWriter.write(resultString);
							bWriter.newLine();
						}

						onGeneratedAccountingString(resultString, entry, key, accountingSystem, fromStamp, toStamp);

					}

					if (createFile) {
						bWriter.close();

						ICFile file = ((ICFileHome) IDOLookup.getHome(ICFile.class)).create();
						file.setFileValue(new FileInputStream(tempfile));
						file.setName(tempfile.getName().substring(0, tempfile.getName().length() - 9) + ".csv");
						file.store();

						AccountingFiles files = getAccountingFilesHome().create();
						files.setCaseCode(code);
						files.setFile(file);
						files.setMonth(fromStamp.getDate());
						files.setCreatedDate(new IWTimestamp().getTimestamp());
						files.store();
					}
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
	
	public AccountingEntry[] getAccountingEntries(CaseCode code, Date from, Date to) throws FinderException {
		AccountingBusinessManager manager = AccountingBusinessManager.getInstance();
		AccountingBusiness b = null;
		try {
			b = manager.getAccountingBusiness(code, getIWApplicationContext());
			if (b == null) {
				throw new FinderException("No AccountingBusiness found for case code: " + code.getCode());
			}
		}
		catch (IBOLookupException e) {
			e.printStackTrace();
		}

		CaseCodeAccountingKey key = getAccountingKey(code);

		return b.getAccountingEntries(key.getAccountingKey(), null, from, to);
	}

	/**
	 * You can extend this class and overide this method so for each line of writing to a file or just generating an accountingstring it also calls this
	 * method. Used for example in NavisionBusiness
	 * 
	 * @param resultString
	 */
	protected void onGeneratedAccountingString(String resultString, AccountingEntry entry, CaseCodeAccountingKey key, String accountingSystem, IWTimestamp fromStamp, IWTimestamp toStamp) {

	}

}