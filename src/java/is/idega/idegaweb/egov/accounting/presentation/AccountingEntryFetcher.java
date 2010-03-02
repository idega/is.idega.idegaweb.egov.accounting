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
import is.idega.idegaweb.egov.accounting.data.AccountingFiles;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;

import com.idega.business.IBORuntimeException;
import com.idega.core.file.data.ICFile;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.Table2;
import com.idega.presentation.TableCell2;
import com.idega.presentation.TableRow;
import com.idega.presentation.TableRowGroup;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.DateInput;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.Label;
import com.idega.presentation.ui.SubmitButton;
import com.idega.util.IWTimestamp;
import com.idega.util.PresentationUtil;

public class AccountingEntryFetcher extends AccountingBlock {

	private final static String PARAMETER_DATE_FROM = "prm_date_from";
	private final static String PARAMETER_DATE_TO = "prm_date_to";
	private static final String PARAMETER_ACCOUNTING_FILE_PK = "prm_accounting_file_pk";

	private String caseCode = null;

	private void parse(IWContext iwc) throws RemoteException {
		if (iwc.isParameterSet(PARAMETER_DATE_FROM)) {
			
			IWTimestamp month = new IWTimestamp(iwc.getParameter(PARAMETER_DATE_FROM));
			if (iwc.isParameterSet(PARAMETER_DATE_TO)) {
				IWTimestamp to = new IWTimestamp(iwc.getParameter(PARAMETER_DATE_TO));
				
				getAccountingKeyBusiness(iwc).createAccountingFile(this.caseCode, month.getDate(), to.getDate());
			}
			else {
				getAccountingKeyBusiness(iwc).createAccountingFile(this.caseCode, month.getDate());
			}
		}
		
		if (iwc.isParameterSet(PARAMETER_ACCOUNTING_FILE_PK)) {
			getAccountingKeyBusiness(iwc).removeAccountingFile(iwc.getParameter(PARAMETER_ACCOUNTING_FILE_PK));
		}
	}

	protected void present(IWContext iwc) {
		try {
			parse(iwc);
			PresentationUtil.addStyleSheetToHeader(iwc, iwc.getIWMainApplication().getBundle("is.idega.idegaweb.egov.application").getVirtualPathWithFileNameString("style/application.css"));
	
			if (this.caseCode == null) {
				add(new Text("Please set the case code"));
				return;
			}
			
			boolean showFullInputs = iwc.getApplicationSettings().getBoolean(AccountingConstants.PROPERTY_ACCOUNTING_FETCHER_SHOW_INPUTS, false);
	
			Form form = new Form();
			form.setID("accountingEntryFetcher");
			form.setStyleClass("adminForm");
	
			Layer layer = new Layer(Layer.DIV);
			layer.setStyleClass("formSection");
			form.add(layer);
			
			Layer helpLayer = new Layer(Layer.DIV);
			helpLayer.setStyleClass("helperText");
			helpLayer.add(new Text(this.iwrb.getLocalizedString("accounting_fetcher.help_text", "Select the month you want to create a file for and click 'Create'.  The created file is then added to the list below.")));
			layer.add(helpLayer);

			IWTimestamp fromStamp = new IWTimestamp();
			fromStamp.setDay(1);

			IWTimestamp toStamp = new IWTimestamp(fromStamp);
			toStamp.addMonths(1);
			toStamp.addDays(-1);

			DateInput from = new DateInput(PARAMETER_DATE_FROM);
			from.setStyleClass("dateInput");
			from.setDate(fromStamp.getDate());
			from.setYearRange(fromStamp.getYear() - 1, fromStamp.getYear() + 4);
			from.keepStatusOnAction(true);
			if (!showFullInputs) {
				from.setToShowDay(false);
			}
			
			DateInput to = new DateInput(PARAMETER_DATE_TO);
			to.setStyleClass("dateInput");
			to.setDate(toStamp.getDate());
			to.setYearRange(fromStamp.getYear() - 1, fromStamp.getYear() + 4);
			to.keepStatusOnAction(true);
	
			Layer formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			Label label = new Label(showFullInputs ? this.iwrb.getLocalizedString("accounting_fetcher.from", "From") : this.iwrb.getLocalizedString("accounting_fetcher.month", "Month"), from);
			formItem.add(label);
			formItem.add(from);
			layer.add(formItem);
	
			formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			label = new Label(this.iwrb.getLocalizedString("accounting_fetcher.to", "To"), to);
			formItem.add(label);
			formItem.add(to);
			if (showFullInputs) {
				layer.add(formItem);
			}
	
			SubmitButton fetch = new SubmitButton(this.iwrb.getLocalizedString("accounting_fetcher.create", "Create"));
			fetch.setStyleClass("indentedButton");
			fetch.setStyleClass("button");
			formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			formItem.add(fetch);
			layer.add(formItem);
			
			Layer clearLayer = new Layer(Layer.DIV);
			clearLayer.setStyleClass("Clear");
			layer.add(clearLayer);
	
			form.add(getFileTable(iwc));
			
			add(form);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private Table2 getFileTable(IWContext iwc) throws RemoteException {
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
		cell.setStyleClass("fileName");
		cell.add(new Text(this.iwrb.getLocalizedString("accounting_fetcher.file_name", "File name")));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("createdDate");
		cell.add(new Text(this.iwrb.getLocalizedString("accounting_fetcher.created_date", "Created date")));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("month");
		cell.add(new Text(this.iwrb.getLocalizedString("accounting_fetcher.month", "Month")));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("remove");
		cell.setStyleClass("lastColumn");
		cell.add(Text.getNonBrakingSpace());

		group = table.createBodyRowGroup();
		int iRow = 1;
		
		Collection files = getAccountingKeyBusiness(iwc).getAccountingFiles(this.caseCode);
		Iterator iter = files.iterator();
		while (iter.hasNext()) {
			AccountingFiles file = (AccountingFiles) iter.next();
			row = group.createRow();
			
			ICFile icFile = file.getFile();

			Link link = new Link(icFile.getName());
			link.setTarget(Link.TARGET_BLANK_WINDOW);
			link.setFile(icFile);
			
			Link delete = new Link(this.iwb.getImage("delete.png", this.iwrb.getLocalizedString("remove", "Remove")));
			delete.addParameter(PARAMETER_ACCOUNTING_FILE_PK, file.getPrimaryKey().toString());
			delete.setClickConfirmation(this.iwrb.getLocalizedString("accounting_fetcher.confirm_remove", "Are you sure you want to remove this file?"));

			IWTimestamp createdDate = new IWTimestamp(file.getCreatedDate());
			IWTimestamp month = new IWTimestamp(file.getMonth());
			
			if (iRow % 2 == 0) {
				row.setStyleClass("evenRow");
			}
			else {
				row.setStyleClass("oddRow");
			}

			cell = row.createCell();
			cell.setStyleClass("firstColumn");
			cell.setStyleClass("fileName");
			cell.add(link);

			cell = row.createCell();
			cell.setStyleClass("createdDate");
			cell.add(new Text(createdDate.getLocaleDateAndTime(iwc.getCurrentLocale(), IWTimestamp.SHORT, IWTimestamp.SHORT)));

			cell = row.createCell();
			cell.setStyleClass("month");
			cell.add(new Text(month.getDateString("MM-yyyy", iwc.getCurrentLocale())));

			cell = row.createCell();
			cell.setStyleClass("lastColumn");
			cell.setStyleClass("remove");
			cell.add(delete);

			iRow++;
		}

		return table;
	}
	
	public void setCaseCode(String code) {
		this.caseCode = code;
	}

	public String getCaseCode() {
		return this.caseCode;
	}
}