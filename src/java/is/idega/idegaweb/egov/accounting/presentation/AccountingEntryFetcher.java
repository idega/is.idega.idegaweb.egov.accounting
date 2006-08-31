/*
 * $Id$
 * Created on 7.8.2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.accounting.presentation;

import is.idega.idegaweb.egov.accounting.business.AccountingBusiness;
import is.idega.idegaweb.egov.accounting.business.AccountingBusinessManager;
import is.idega.idegaweb.egov.accounting.business.AccountingEntry;
import is.idega.idegaweb.egov.accounting.business.AccountingKeyBusiness;
import is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKey;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.NumberFormat;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.block.process.data.CaseCode;
import com.idega.business.IBOLookupException;
import com.idega.core.file.data.ICFile;
import com.idega.core.file.data.ICFileHome;
import com.idega.data.IDOLookup;
import com.idega.presentation.IWContext;
import com.idega.presentation.text.DownloadLink;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.DateInput;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.SubmitButton;
import com.idega.util.IWTimestamp;


public class AccountingEntryFetcher extends AccountingBlock {

	private final static String DATE_FROM = "date_from";
	
	private String caseCode = null;
	
	private String fromDate = null;
	
	private void parse(IWContext iwc) {
		if (iwc.isParameterSet(DATE_FROM)) {
			fromDate = iwc.getParameter(DATE_FROM);
		}
	}
	
	protected void present(IWContext iwc) {
		parse(iwc);
		
		if (caseCode == null) {
			add(new Text("Please set the case code"));
			//SCHMEAL
			return;
		} 
		
		Form form = new Form();
		form.setID("accountingEntryFetcher");
		form.setStyleClass("accountingEntryFetcherForm");
		
		DateInput from = new DateInput(DATE_FROM);
		from.setToShowDay(false);
				
		form.add(from);
		
		form.add(new SubmitButton());
		
		add(form);
		
		AccountingBusinessManager manager = AccountingBusinessManager.getInstance();
		AccountingBusiness b = null;
		try {
			b = manager.getAccountingBusiness(caseCode, iwc.getApplicationContext());
		} catch (IBOLookupException e) {
			e.printStackTrace();
		}

		if (b != null && fromDate != null ) {
			IWTimestamp fromStamp = new IWTimestamp(fromDate);
			IWTimestamp toStamp = new IWTimestamp(fromStamp);
			toStamp.addMonths(1);
			toStamp.addDays(-1);
			
			System.out.println("from = " + fromStamp.getDateString("dd-MM-yyyy"));
			System.out.println("to = " + toStamp.getDateString("dd-MM-yyyy"));
			
			AccountingKeyBusiness accKeyBiz = this.getAccountingKeyBusiness(iwc);
			
			try {
				CaseCode code = getCaseCodeHome().findByPrimaryKey(caseCode);
				CaseCodeAccountingKey key = accKeyBiz.getAccountingKey(code);
				
				AccountingEntry [] accEntry = b.getAccountingEntries(key.getAccountingKey(), null, fromStamp.getDate(), toStamp.getDate());
				if (accEntry != null && accEntry.length != 0) {
					if ("SCHMEAL".equals(this.caseCode)) {
						File tempfile = File.createTempFile("MEAL" + fromStamp.getDateString("MMyyyy"), ".csv");
		                FileWriter writer = new FileWriter(tempfile);
		                BufferedWriter bWriter = new BufferedWriter(writer);
						
		                NumberFormat format = NumberFormat.getInstance();
		                format.setMaximumFractionDigits(0);
		                format.setMinimumFractionDigits(0);
		                format.setGroupingUsed(false);

						for (int i = 0; i < accEntry.length; i++) {
							AccountingEntry entry = accEntry[i];
							bWriter.write(entry.getProductCode());
							bWriter.write(",");
							bWriter.write(entry.getPayerPersonalId());
							bWriter.write(",");
							bWriter.write(entry.getPersonalId());
							bWriter.write(",");
							bWriter.write(format.format(entry.getAmount()));
							bWriter.write(",");
							bWriter.write(format.format(entry.getUnitPrice()));
							bWriter.write(",");
							IWTimestamp startDate = new IWTimestamp(entry.getStartDate());
							if (startDate.isEarlierThan(fromStamp)) {
								bWriter.write(fromStamp.getDateString("dd-MM-yyyy"));					
							} else {
								bWriter.write(startDate.getDateString("dd-MM-yyyy"));
							}
							bWriter.write(",");
							if (entry.getEndDate() == null) {
								bWriter.write(toStamp.getDateString("dd-MM-yyyy"));								
							} else {
								IWTimestamp endDate = new IWTimestamp(entry.getEndDate());
								if (endDate.isLaterThan(toStamp)) {
									bWriter.write(toStamp.getDateString("dd-MM-yyyy"));					
								} else {
									bWriter.write(endDate.getDateString("dd-MM-yyyy"));
								}								
							}
							bWriter.write(",");
							bWriter.write("BRAUD"); //TODO get in table
							bWriter.write(",");
							bWriter.write(entry.getProviderCode());
							bWriter.newLine();
						}		
						
						bWriter.close();
						
						//TMP sollution
						ICFile file = ((ICFileHome) IDOLookup.getHome(ICFile.class))
                        .create();
						file.setFileValue(new FileInputStream(tempfile));
						file.setName(tempfile.getName());
						file.store();

                        DownloadLink link = new DownloadLink(((Integer) file.getPrimaryKey()).intValue());
                        link.setAlternativeFileName(tempfile.getName());
                        link.setText(tempfile.getName());
                        
                        add(link);

					} else if ("MBANBOP".equals(this.caseCode)) {
						File tempfile = File.createTempFile("CHILD" + fromStamp.getDateString("MMyyyy"), ".csv");
		                FileWriter writer = new FileWriter(tempfile);
		                BufferedWriter bWriter = new BufferedWriter(writer);
						
		                NumberFormat format = NumberFormat.getInstance();
		                format.setMaximumFractionDigits(0);
		                format.setMinimumFractionDigits(0);
		                format.setGroupingUsed(false);

						for (int i = 0; i < accEntry.length; i++) {
							AccountingEntry entry = accEntry[i];
							bWriter.write(entry.getProductCode());
							bWriter.write(",");
							bWriter.write(entry.getPayerPersonalId());
							bWriter.write(",");
							bWriter.write(entry.getPersonalId());
							bWriter.write(",");
							bWriter.write(format.format(entry.getAmount()));
							bWriter.write(",");
							bWriter.write(format.format(entry.getUnitPrice()));
							bWriter.write(",");
							bWriter.write("LEIK"); //TODO get in table
							bWriter.write(",");
							bWriter.write(entry.getProviderCode());
							bWriter.newLine();
						}		
						
						bWriter.close();
						
						//TMP sollution
						ICFile file = ((ICFileHome) IDOLookup.getHome(ICFile.class))
                        .create();
						file.setFileValue(new FileInputStream(tempfile));
						file.setName(tempfile.getName());
						file.store();

                        DownloadLink link = new DownloadLink(((Integer) file.getPrimaryKey()).intValue());
                        link.setAlternativeFileName(tempfile.getName());
                        link.setText(tempfile.getName());
                        
                        add(link);						
					} else if ("MUSICCH".equals(this.caseCode)) {
						File tempfile = File.createTempFile("MUSIC" + fromStamp.getDateString("MMyyyy"), ".csv");
		                FileWriter writer = new FileWriter(tempfile);
		                BufferedWriter bWriter = new BufferedWriter(writer);
						
		                NumberFormat format = NumberFormat.getInstance();
		                format.setMaximumFractionDigits(0);
		                format.setMinimumFractionDigits(0);
		                format.setGroupingUsed(false);

						for (int i = 0; i < accEntry.length; i++) {
							AccountingEntry entry = accEntry[i];
							bWriter.write(entry.getProductCode());
							bWriter.write(",");
							bWriter.write(entry.getPayerPersonalId());
							bWriter.write(",");
							bWriter.write(entry.getPersonalId());
							bWriter.write(",");
							IWTimestamp startDate = new IWTimestamp(entry.getStartDate());
							if (startDate.isEarlierThan(fromStamp)) {
								bWriter.write(fromStamp.getDateString("dd-MM-yyyy"));					
							} else {
								bWriter.write(startDate.getDateString("dd-MM-yyyy"));
							}
							bWriter.write(",");
							if (entry.getEndDate() == null) {
								bWriter.write(toStamp.getDateString("dd-MM-yyyy"));								
							} else {
								IWTimestamp endDate = new IWTimestamp(entry.getEndDate());
								if (endDate.isLaterThan(toStamp)) {
									bWriter.write(toStamp.getDateString("dd-MM-yyyy"));					
								} else {
									bWriter.write(endDate.getDateString("dd-MM-yyyy"));
								}								
							}
							bWriter.write(",");
							bWriter.write("TONO"); //TODO get in table
							bWriter.write(",");
							bWriter.write(entry.getProviderCode());
							bWriter.newLine();
						}		
						
						bWriter.close();
						
						//TMP sollution
						ICFile file = ((ICFileHome) IDOLookup.getHome(ICFile.class))
                        .create();
						file.setFileValue(new FileInputStream(tempfile));
						file.setName(tempfile.getName());
						file.store();

                        DownloadLink link = new DownloadLink(((Integer) file.getPrimaryKey()).intValue());
                        link.setAlternativeFileName(tempfile.getName());
                        link.setText(tempfile.getName());
                        
                        add(link);						
					} else if ("MBFRITV".equals(this.caseCode)) {
						File tempfile = File.createTempFile("CARE" + fromStamp.getDateString("MMyyyy"), ".csv");
		                FileWriter writer = new FileWriter(tempfile);
		                BufferedWriter bWriter = new BufferedWriter(writer);
						
		                NumberFormat format = NumberFormat.getInstance();
		                format.setMaximumFractionDigits(0);
		                format.setMinimumFractionDigits(0);
		                format.setGroupingUsed(false);

						for (int i = 0; i < accEntry.length; i++) {
							AccountingEntry entry = accEntry[i];
							bWriter.write(entry.getProductCode());
							bWriter.write(",");
							bWriter.write(entry.getPayerPersonalId());
							bWriter.write(",");
							bWriter.write(entry.getPersonalId());
							bWriter.write(",");
							bWriter.write(format.format(entry.getAmount()));
							bWriter.write(",");
							bWriter.write(format.format(entry.getUnitPrice()));
							bWriter.write(",");
							bWriter.write("SKOL"); //TODO get in table
							bWriter.write(",");
							bWriter.write(entry.getProviderCode());
							bWriter.newLine();
						}		
						
						bWriter.close();
						
						//TMP sollution
						ICFile file = ((ICFileHome) IDOLookup.getHome(ICFile.class))
                        .create();
						file.setFileValue(new FileInputStream(tempfile));
						file.setName(tempfile.getName());
						file.store();

                        DownloadLink link = new DownloadLink(((Integer) file.getPrimaryKey()).intValue());
                        link.setAlternativeFileName(tempfile.getName());
                        link.setText(tempfile.getName());
                        
                        add(link);
					} 
				}
			} catch (FinderException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (CreateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setCaseCode(String code) {
		this.caseCode = code;
	}
	
	public String getCaseCode() {
		return this.caseCode;
	}
}