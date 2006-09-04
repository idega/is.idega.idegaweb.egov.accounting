/*
 * $Id$ Created on 7.8.2006
 * 
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to
 * license terms.
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
import com.idega.presentation.Layer;
import com.idega.presentation.text.DownloadLink;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.DateInput;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.Label;
import com.idega.presentation.ui.SubmitButton;
import com.idega.util.IWTimestamp;

public class AccountingEntryFetcher extends AccountingBlock {

	private final static String DATE_FROM = "date_from";

	private String caseCode = null;

	private String fromDate = null;

	private void parse(IWContext iwc) {
		if (iwc.isParameterSet(DATE_FROM)) {
			this.fromDate = iwc.getParameter(DATE_FROM);
		}
	}

	protected void present(IWContext iwc) {
		parse(iwc);

		if (this.caseCode == null) {
			add(new Text("Please set the case code"));
			return;
		}

		Form form = new Form();
		form.setID("accountingEntryFetcher");
		form.setStyleClass("adminForm");

		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("formSection");
		form.add(layer);
		
		DateInput from = new DateInput(DATE_FROM);
		from.setStyleClass("dateInput");
		from.setToShowDay(false);

		Layer formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		Label label = new Label(this.iwrb.getLocalizedString("month", "Month"), from);
		formItem.add(label);
		formItem.add(from);
		layer.add(formItem);

		SubmitButton fetch = new SubmitButton(this.iwrb.getLocalizedString("get", "Get"));
		fetch.setStyleClass("indentedButton");
		fetch.setStyleClass("button");
		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		formItem.add(fetch);
		layer.add(formItem);
		
		add(form);

		AccountingBusinessManager manager = AccountingBusinessManager.getInstance();
		AccountingBusiness b = null;
		try {
			b = manager.getAccountingBusiness(this.caseCode, iwc.getApplicationContext());
		}
		catch (IBOLookupException e) {
			e.printStackTrace();
		}

		if (b != null && this.fromDate != null) {
			IWTimestamp fromStamp = new IWTimestamp(this.fromDate);
			IWTimestamp toStamp = new IWTimestamp(fromStamp);
			toStamp.addMonths(1);
			toStamp.addDays(-1);

			AccountingKeyBusiness accKeyBiz = this.getAccountingKeyBusiness(iwc);

			try {
				CaseCode code = getCaseCodeHome().findByPrimaryKey(this.caseCode);
				CaseCodeAccountingKey key = accKeyBiz.getAccountingKey(code);

				AccountingEntry[] accEntry = b.getAccountingEntries(key.getAccountingKey(), null, fromStamp.getDate(), toStamp.getDate());
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
							
							if (entry.getProductCode().equals("MEAL")) {
								bWriter.write("MATUR");
							}
							else if (entry.getProductCode().equals("MILK")) {
								bWriter.write("MJîLK");
							}
							else {
								bWriter.write(entry.getProductCode());
							}
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
							}
							else {
								bWriter.write(startDate.getDateString("dd-MM-yyyy"));
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
							bWriter.write("BRAUÜ"); // TODO get in table
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
							bWriter.newLine();
						}

						bWriter.close();

						// TMP sollution
						ICFile file = ((ICFileHome) IDOLookup.getHome(ICFile.class)).create();
						file.setFileValue(new FileInputStream(tempfile));
						file.setName(tempfile.getName());
						file.store();

						DownloadLink link = new DownloadLink(((Integer) file.getPrimaryKey()).intValue());
						link.setAlternativeFileName(tempfile.getName());
						link.setText(tempfile.getName());

						add(link);
					}
					else if ("MBANBOP".equals(this.caseCode)) {
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
							bWriter.write("LEIK"); // TODO get in table
							bWriter.write(",");
							bWriter.write(entry.getProviderCode());
							bWriter.newLine();
						}

						bWriter.close();

						// TMP sollution
						ICFile file = ((ICFileHome) IDOLookup.getHome(ICFile.class)).create();
						file.setFileValue(new FileInputStream(tempfile));
						file.setName(tempfile.getName());
						file.store();

						DownloadLink link = new DownloadLink(((Integer) file.getPrimaryKey()).intValue());
						link.setAlternativeFileName(tempfile.getName());
						link.setText(tempfile.getName());

						add(link);
					}
					else if ("MUSICCH".equals(this.caseCode)) {
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
							}
							else {
								bWriter.write(startDate.getDateString("dd-MM-yyyy"));
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
							bWriter.write("TONO"); // TODO get in table
							bWriter.write(",");
							bWriter.write(entry.getProviderCode());
							bWriter.newLine();
						}

						bWriter.close();

						// TMP sollution
						ICFile file = ((ICFileHome) IDOLookup.getHome(ICFile.class)).create();
						file.setFileValue(new FileInputStream(tempfile));
						file.setName(tempfile.getName());
						file.store();

						DownloadLink link = new DownloadLink(((Integer) file.getPrimaryKey()).intValue());
						link.setAlternativeFileName(tempfile.getName());
						link.setText(tempfile.getName());

						add(link);
					}
					else if ("MBFRITV".equals(this.caseCode)) {
						File tempfile = File.createTempFile("CARE" + fromStamp.getDateString("MMyyyy"), ".csv");
						FileWriter writer = new FileWriter(tempfile);
						BufferedWriter bWriter = new BufferedWriter(writer);

						NumberFormat format = NumberFormat.getInstance();
						format.setMaximumFractionDigits(0);
						format.setMinimumFractionDigits(0);
						format.setGroupingUsed(false);

						for (int i = 0; i < accEntry.length; i++) {
							AccountingEntry entry = accEntry[i];

							if (entry.getProductCode().equals("CARE")) {
								bWriter.write("SKOL");
							}
							else if (entry.getProductCode().equals("REFRESHMENTS")) {
								bWriter.write("HRESSING");
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
							bWriter.write("SKOL"); // TODO get in table
							bWriter.write(",");
							bWriter.write(entry.getProviderCode());
							bWriter.newLine();
						}

						bWriter.close();

						// TMP sollution
						ICFile file = ((ICFileHome) IDOLookup.getHome(ICFile.class)).create();
						file.setFileValue(new FileInputStream(tempfile));
						file.setName(tempfile.getName());
						file.store();

						DownloadLink link = new DownloadLink(((Integer) file.getPrimaryKey()).intValue());
						link.setAlternativeFileName(tempfile.getName());
						link.setText(tempfile.getName());

						add(link);
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

	public void setCaseCode(String code) {
		this.caseCode = code;
	}

	public String getCaseCode() {
		return this.caseCode;
	}
}