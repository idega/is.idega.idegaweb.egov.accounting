/*
 * $Id$ Created on 7.8.2006
 * 
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to
 * license terms.
 */
package is.idega.idegaweb.egov.accounting.presentation;

import is.idega.idegaweb.egov.accounting.business.AccountingConstants;
import is.idega.idegaweb.egov.accounting.business.AccountingEntry;
import is.idega.idegaweb.egov.accounting.business.AccountingEntryComparator;
import is.idega.idegaweb.egov.accounting.business.AccountingEntryWriter;

import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.FinderException;

import com.idega.block.process.data.CaseCode;
import com.idega.block.process.data.CaseCodeHome;
import com.idega.business.IBORuntimeException;
import com.idega.data.IDOLookup;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.Table2;
import com.idega.presentation.TableCell2;
import com.idega.presentation.TableRow;
import com.idega.presentation.TableRowGroup;
import com.idega.presentation.text.DownloadLink;
import com.idega.presentation.text.Heading1;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.DateInput;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.Label;
import com.idega.presentation.ui.SubmitButton;
import com.idega.util.IWTimestamp;
import com.idega.util.PersonalIDFormatter;

public class AccountingEntryStatistics extends AccountingBlock {

	private final static String PARAMETER_DATE_FROM = "prm_date_from";
	private final static String PARAMETER_DATE_TO = "prm_date_to";

	private String caseCode = null;
	private AccountingEntry[] entries;
	private IWTimestamp fromStamp;
	private IWTimestamp toStamp;
	
	private Map products;
	private Map paymentMethod;

	private void parse(IWContext iwc) throws RemoteException {
		if (iwc.isParameterSet(PARAMETER_DATE_FROM) && iwc.isParameterSet(PARAMETER_DATE_TO)) {
			try {
				CaseCodeHome home = (CaseCodeHome) IDOLookup.getHome(CaseCode.class);
				CaseCode code = home.findByPrimaryKey(this.caseCode);
				
				fromStamp = new IWTimestamp(iwc.getParameter(PARAMETER_DATE_FROM));
				toStamp = new IWTimestamp(iwc.getParameter(PARAMETER_DATE_TO));

				entries = getAccountingKeyBusiness(iwc).getAccountingEntries(code, fromStamp.getDate(), toStamp.getDate());
				for (int i = 0; i < entries.length; i++) {
					AccountingEntry entry = entries[i];
					
					addToProductsMap(entry.getProductCode(), entry);
					addToPaymentMap(entry.getPaymentMethod(), entry.getPayerPersonalId());
				}
				
				iwc.setSessionAttribute(AccountingConstants.SESSION_PAYMENT_METHOD_MAP, paymentMethod);
				iwc.setSessionAttribute(AccountingConstants.SESSION_PRODUCT_MAP, products);
			}
			catch (FinderException fe) {
				fe.printStackTrace();
			}
		}
	}

	protected void present(IWContext iwc) {
		try {
			if (this.caseCode == null) {
				add(new Text("Please set the case code"));
				return;
			}
			
			parse(iwc);
	
			Form form = new Form();
			form.setID("accountingEntryStatistics");
			form.setStyleClass("adminForm");
	
			Layer layer = new Layer(Layer.DIV);
			layer.setStyleClass("formSection");
			form.add(layer);
			
			Layer helpLayer = new Layer(Layer.DIV);
			helpLayer.setStyleClass("helperText");
			helpLayer.add(new Text(this.iwrb.getLocalizedString("accounting_statistics.help_text", "Select the from/to dates you want to get statistics for.  Please note that this action can take a while to complete.")));
			layer.add(helpLayer);

			IWTimestamp fromStamp = new IWTimestamp();
			fromStamp.setDay(1);

			IWTimestamp toStamp = new IWTimestamp(fromStamp);
			toStamp.addMonths(1);
			toStamp.addDays(-1);

			DateInput from = new DateInput(PARAMETER_DATE_FROM);
			from.setStyleClass("dateInput");
			from.setDate(fromStamp.getDate());
			from.keepStatusOnAction(true);
			
			DateInput to = new DateInput(PARAMETER_DATE_TO);
			to.setStyleClass("dateInput");
			to.setDate(toStamp.getDate());
			to.keepStatusOnAction(true);
	
			Layer formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			Label label = new Label(this.iwrb.getLocalizedString("accounting_statistics.from", "From"), from);
			formItem.add(label);
			formItem.add(from);
			layer.add(formItem);
	
			formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			label = new Label(this.iwrb.getLocalizedString("accounting_statistics.to", "To"), to);
			formItem.add(label);
			formItem.add(to);
			layer.add(formItem);
	
			SubmitButton fetch = new SubmitButton(this.iwrb.getLocalizedString("accounting_statistics.get", "Get"));
			fetch.setStyleClass("indentedButton");
			fetch.setStyleClass("button");
			formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			formItem.add(fetch);
			layer.add(formItem);
			
			Layer clearLayer = new Layer(Layer.DIV);
			clearLayer.setStyleClass("Clear");
			layer.add(clearLayer);
	
			if (this.entries != null && this.paymentMethod != null && this.products != null) {
				form.add(getPrintouts(iwc));
				form.add(getStatisticsTable(iwc));

				Collection keys = this.products.keySet();
				Iterator iter = keys.iterator();
				while (iter.hasNext()) {
					String key = (String) iter.next();
					
					Heading1 heading = new Heading1(iwrb.getLocalizedString("accounting_statistics." + this.caseCode + "." + key, key));
					heading.setStyleClass("productHeading");
					form.add(heading);
					
					form.add(getProductTable(iwc, (List) this.products.get(key)));
				}
			}
			
			add(form);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private Table2 getStatisticsTable(IWContext iwc) throws RemoteException {
		Table2 table = new Table2();
		table.setWidth("100%");
		table.setCellpadding(0);
		table.setCellspacing(0);
		table.setStyleClass("ruler");
		table.setStyleClass("adminTable");
		
		TableRowGroup group = table.createHeaderRowGroup();
		TableRow row = group.createRow();
		TableCell2 cell = row.createHeaderCell();
		cell.setStyleClass("firstColumn");
		cell.setStyleClass("item");
		cell.add(new Text(this.iwrb.getLocalizedString("accounting_statistics.item", "Item")));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("lastColumn");
		cell.setStyleClass("total");
		cell.add(new Text(this.iwrb.getLocalizedString("accounting_statistics.total", "Total")));

		group = table.createBodyRowGroup();
		int iRow = 1;
			
		Collection keys = this.paymentMethod.keySet();
		Iterator iter = keys.iterator();
		while (iter.hasNext()) {
			row = group.createRow();
			String key = (String) iter.next();
			int count = ((Collection) this.paymentMethod.get(key)).size();
			
			if (iRow % 2 == 0) {
				row.setStyleClass("evenRow");
			}
			else {
				row.setStyleClass("oddRow");
			}

			cell = row.createCell();
			cell.setStyleClass("firstColumn");
			cell.setStyleClass("item");
			cell.add(new Text(this.iwrb.getLocalizedString("accounting_statistics.payment_method." + key, key)));

			cell = row.createCell();
			cell.setStyleClass("lastColumn");
			cell.setStyleClass("total");
			cell.add(new Text(String.valueOf(count)));

			iRow++;
		}

		keys = this.products.keySet();
		iter = keys.iterator();
		while (iter.hasNext()) {
			row = group.createRow();
			String key = (String) iter.next();
			int count = ((Collection) this.products.get(key)).size();
			
			if (iRow % 2 == 0) {
				row.setStyleClass("evenRow");
			}
			else {
				row.setStyleClass("oddRow");
			}

			cell = row.createCell();
			cell.setStyleClass("firstColumn");
			cell.setStyleClass("item");
			cell.add(new Text(this.iwrb.getLocalizedString("accounting_statistics." + this.caseCode + "." + key, key)));

			cell = row.createCell();
			cell.setStyleClass("lastColumn");
			cell.setStyleClass("total");
			cell.add(new Text(String.valueOf(count)));

			iRow++;
		}

		return table;
	}
	
	private Table2 getProductTable(IWContext iwc, List entries) throws RemoteException {
		Collections.sort(entries, new AccountingEntryComparator(iwc.getCurrentLocale()));
		
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(0);
		format.setMinimumFractionDigits(0);
		format.setGroupingUsed(false);

		Table2 table = new Table2();
		table.setWidth("100%");
		table.setCellpadding(0);
		table.setCellspacing(0);
		table.setStyleClass("ruler");
		table.setStyleClass("adminTable");
		
		TableRowGroup group = table.createHeaderRowGroup();
		TableRow row = group.createRow();
		TableCell2 cell = row.createHeaderCell();
		cell.setStyleClass("firstColumn");
		cell.setStyleClass("name");
		cell.add(new Text(this.iwrb.getLocalizedString("accounting_statistics.name", "Name")));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("personalID");
		cell.add(new Text(this.iwrb.getLocalizedString("accounting_statistics.personal_id", "Personal ID")));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("validFrom");
		cell.add(new Text(this.iwrb.getLocalizedString("accounting_statistics.valid_from", "Valid from")));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("validTo");
		cell.add(new Text(this.iwrb.getLocalizedString("accounting_statistics.valid_to", "Valid to")));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("units");
		cell.setStyleClass("lastColumn");
		cell.add(new Text(this.iwrb.getLocalizedString("accounting_statistics.units", "Units")));
		
		group = table.createBodyRowGroup();
		int iRow = 1;
		
		Iterator iter = entries.iterator();
		while (iter.hasNext()) {
			AccountingEntry entry = (AccountingEntry) iter.next();
			row = group.createRow();
			
			IWTimestamp startDate = new IWTimestamp(entry.getStartDate());
			
			IWTimestamp endDate = null;
			if (entry.getEndDate() != null) {
				endDate = new IWTimestamp(entry.getEndDate());
			}

			float units = 0;
			if (entry.getUnits() > 0) {
				units = entry.getUnits();
			}
			else {
				units = entry.getAmount();
			}

			if (iRow % 2 == 0) {
				row.setStyleClass("evenRow");
			}
			else {
				row.setStyleClass("oddRow");
			}

			cell = row.createCell();
			cell.setStyleClass("firstColumn");
			cell.setStyleClass("name");
			cell.add(new Text(entry.getName()));

			cell = row.createCell();
			cell.setStyleClass("personalID");
			cell.add(new Text(PersonalIDFormatter.format(entry.getPersonalId(), iwc.getCurrentLocale())));

			cell = row.createCell();
			cell.setStyleClass("validFrom");
			cell.add(new Text(startDate.getDateString("MM-yyyy", iwc.getCurrentLocale())));

			cell = row.createCell();
			cell.setStyleClass("validTo");
			if (endDate != null) {
				cell.add(new Text(endDate.getDateString("MM-yyyy", iwc.getCurrentLocale())));
			}
			else {
				cell.add(new Text("-"));
			}

			cell = row.createCell();
			cell.setStyleClass("lastColumn");
			cell.setStyleClass("units");
			cell.add(new Text(format.format(units)));

			iRow++;
		}

		return table;
	}
	
	public Layer getPrintouts(IWContext iwc) throws RemoteException {
		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("printIcons");
		
		layer.add(getXLSLink(iwc));
		
		return layer;
	}
	
	protected Link getXLSLink(IWContext iwc) throws RemoteException {
    DownloadLink link = new DownloadLink(this.iwb.getImage("xls.gif"));
    link.setStyleClass("xls");
    link.setTarget(Link.TARGET_NEW_WINDOW);
    link.addParameter(AccountingEntryWriter.PARAMETER_CASE_CODE, this.caseCode);
    link.setMediaWriterClass(AccountingEntryWriter.class);
		
		return link;
	}

	private void addToProductsMap(String key, AccountingEntry entry) {
		if (this.products == null) {
			this.products = new HashMap();
		}
		
		Collection collection = null;
		if (this.products.size() > 0) {
			collection = (Collection) this.products.get(key);
		}
		else {
			collection = new ArrayList();
		}
		
		collection.add(entry);
		this.products.put(key, collection);
	}
	
	private void addToPaymentMap(String key, String personalID) {
		if (this.paymentMethod == null) {
			this.paymentMethod = new HashMap();
		}
		
		List collection = null;
		if (this.paymentMethod.size() > 0) {
			collection = (List) this.paymentMethod.get(key);
		}
		else {
			collection = new ArrayList();
		}
		
		if (!collection.contains(personalID)) {
			collection.add(personalID);
			this.paymentMethod.put(key, collection);
		}
	}
	
	public void setCaseCode(String code) {
		this.caseCode = code;
	}

	public String getCaseCode() {
		return this.caseCode;
	}
}