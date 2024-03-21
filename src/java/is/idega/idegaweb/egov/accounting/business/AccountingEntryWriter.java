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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.idega.core.file.util.MimeTypeUtil;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.io.DownloadWriter;
import com.idega.io.MediaWritable;
import com.idega.io.MemoryFileBuffer;
import com.idega.io.MemoryInputStream;
import com.idega.io.MemoryOutputStream;
import com.idega.presentation.IWContext;
import com.idega.util.IOUtil;
import com.idega.util.IWTimestamp;
import com.idega.util.PersonalIDFormatter;
import com.idega.util.StringHandler;
import com.idega.util.text.TextSoap;


public class AccountingEntryWriter extends DownloadWriter implements MediaWritable {

	private MemoryFileBuffer buffer = null;
	private Locale locale;
	private IWResourceBundle iwrb;
	private String caseCode = null;

	public static final String PARAMETER_CASE_CODE = "prm_case_code";

	public AccountingEntryWriter() {
	}

	@Override
	public void init(HttpServletRequest req, IWContext iwc) {
		if (iwc == null || !iwc.isLoggedOn()) {
			return;
		}

		try {
			this.locale = iwc.getApplicationSettings().getApplicationLocale();
			this.iwrb = iwc.getIWMainApplication().getBundle(AccountingConstants.IW_BUNDLE_IDENTIFIER).getResourceBundle(this.locale);
			this.caseCode = iwc.getParameter(PARAMETER_CASE_CODE);

			Map paymentMethod = (Map) iwc.getSessionAttribute(AccountingConstants.SESSION_PAYMENT_METHOD_MAP);
			Map products = (Map) iwc.getSessionAttribute(AccountingConstants.SESSION_PRODUCT_MAP);

			this.buffer = writeXLS(iwc, paymentMethod, products);
			setAsDownload(iwc,"students.xls",this.buffer.length());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getMimeType() {
		if (this.buffer != null) {
			return this.buffer.getMimeType();
		}
		return super.getMimeType();
	}

	@Override
	public void writeTo(IWContext iwc, OutputStream out) throws IOException {
		if (this.buffer != null) {
			MemoryInputStream mis = new MemoryInputStream(this.buffer);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while (mis.available() > 0) {
				baos.write(mis.read());
			}
			baos.writeTo(out);
			IOUtil.close(mis);
		}
		else {
			System.err.println("buffer is null");
		}
	}

	public MemoryFileBuffer writeXLS(IWContext iwc, Map paymentMethod, Map products) throws Exception {
		MemoryFileBuffer buffer = new MemoryFileBuffer();
		MemoryOutputStream mos = new MemoryOutputStream(buffer);

		if (paymentMethod != null && products != null) {
	    HSSFWorkbook wb = new HSSFWorkbook();

	    HSSFSheet sheet = wb.createSheet(TextSoap.encodeToValidExcelSheetName(StringHandler.shortenToLength(this.iwrb.getLocalizedString("accounting_statistics.statistics", "Statistics"), 30)));
	    sheet.setColumnWidth((short)0, (short) (30 * 256));
	    sheet.setColumnWidth((short)1, (short) (14 * 256));

		HSSFFont font = wb.createFont();
	    font.setBold(true);
	    font.setFontHeightInPoints((short)12);
	    HSSFCellStyle style = wb.createCellStyle();
	    style.setFont(font);

	    int cellRow = 0;

			HSSFRow row = sheet.createRow(cellRow++);
	    HSSFCell cell = row.createCell((short)0);
	    cell.setCellValue(this.iwrb.getLocalizedString("accounting_statistics.item", "Item"));
	    cell.setCellStyle(style);

	    cell = row.createCell((short)1);
	    cell.setCellValue(this.iwrb.getLocalizedString("accounting_statistics.total", "Total"));
	    cell.setCellStyle(style);

			Collection keys = paymentMethod.keySet();
			Iterator iter = keys.iterator();
			while (iter.hasNext()) {
				row = sheet.createRow(cellRow++);
				String key = (String) iter.next();
				int count = ((Collection) paymentMethod.get(key)).size();

				cell = row.createCell((short)0);
		    cell.setCellValue(this.iwrb.getLocalizedString("accounting_statistics.payment_method." + key, key));

				cell = row.createCell((short)1);
		    cell.setCellValue(String.valueOf(count));
			}

			keys = products.keySet();
			iter = keys.iterator();
			while (iter.hasNext()) {
				row = sheet.createRow(cellRow++);
				String key = (String) iter.next();
				int count = ((Collection) products.get(key)).size();

				cell = row.createCell((short)0);
		    cell.setCellValue(this.iwrb.getLocalizedString("accounting_statistics." + this.caseCode + "." + key, key));

				cell = row.createCell((short)1);
		    cell.setCellValue(String.valueOf(count));
			}

			keys = products.keySet();
			iter = keys.iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				createNewSheet(iwc, wb, iwrb.getLocalizedString("accounting_statistics." + this.caseCode + "." + key, key), (List) products.get(key));
			}

			wb.write(mos);
			wb.close();
		}
		buffer.setMimeType(MimeTypeUtil.MIME_TYPE_EXCEL_2);
		return buffer;
	}

	private void createNewSheet(IWContext iwc, HSSFWorkbook workbook, String sheetName, List entries) {
		Collections.sort(entries, new AccountingEntryComparator(iwc.getCurrentLocale()));

		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(0);
		format.setMinimumFractionDigits(0);
		format.setGroupingUsed(false);

    HSSFSheet sheet = workbook.createSheet(TextSoap.encodeToValidExcelSheetName(StringHandler.shortenToLength(sheetName, 30)));
    sheet.setColumnWidth((short)0, (short) (30 * 256));
    sheet.setColumnWidth((short)1, (short) (14 * 256));
    sheet.setColumnWidth((short)2, (short) (14 * 256));
    sheet.setColumnWidth((short)3, (short) (14 * 256));
	sheet.setColumnWidth((short)4, (short) (14 * 256));

	HSSFFont font = workbook.createFont();
    font.setBold(true);
    font.setFontHeightInPoints((short)12);
    HSSFCellStyle style = workbook.createCellStyle();
    style.setFont(font);

    int cellRow = 0;
    short cellColumn = 0;
		HSSFRow row = sheet.createRow(cellRow++);

		HSSFCell cell = row.createCell(cellColumn++);
    cell.setCellValue(this.iwrb.getLocalizedString("accounting_statistics.name","Name"));
		cell.setCellStyle(style);

		cell = row.createCell(cellColumn++);
    cell.setCellValue(this.iwrb.getLocalizedString("accounting_statistics.personal_id","Personal ID"));
    cell.setCellStyle(style);

    cell = row.createCell(cellColumn++);
    cell.setCellValue(this.iwrb.getLocalizedString("accounting_statistics.valid_from", "Valid from"));
    cell.setCellStyle(style);

    cell = row.createCell(cellColumn++);
    cell.setCellValue(this.iwrb.getLocalizedString("accounting_statistics.valid_to", "Valid to"));
    cell.setCellStyle(style);

    cell = row.createCell(cellColumn++);
    cell.setCellValue(this.iwrb.getLocalizedString("accounting_statistics.units", "Units"));
    cell.setCellStyle(style);

		Iterator iter = entries.iterator();
		while (iter.hasNext()) {
			AccountingEntry entry = (AccountingEntry) iter.next();
			row = sheet.createRow(cellRow++);
			cellColumn = 0;

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

	    cell = row.createCell(cellColumn++);
	    cell.setCellValue(entry.getName());

	    cell = row.createCell(cellColumn++);
	    cell.setCellValue(PersonalIDFormatter.format(entry.getPersonalId(), iwc.getCurrentLocale()));

	    cell = row.createCell(cellColumn++);
	    cell.setCellValue(startDate.getLocaleDate(iwc.getCurrentLocale(), IWTimestamp.SHORT));

	    cell = row.createCell(cellColumn++);
			if (endDate != null) {
		    cell.setCellValue(endDate.getLocaleDate(iwc.getCurrentLocale(), IWTimestamp.SHORT));
			}
			else {
		    cell.setCellValue("-");
			}

	    cell = row.createCell(cellColumn++);
	    cell.setCellValue(format.format(units));
		}
	}
}