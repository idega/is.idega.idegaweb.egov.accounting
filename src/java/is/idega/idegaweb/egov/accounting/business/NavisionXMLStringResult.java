/*
 * $Id: NavisionXMLStringResult.java,v 1.3 2006/11/22 14:13:06 eiki Exp $
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
import com.idega.util.IWCalendar;
import com.idega.util.IWTimestamp;

/**
 * Writes one accountentry as an xml string. In the format:
<Parameters>
� <Customer_No>2402624449</Customer_No>
� <HeaderDescription>Idegaweb eGov</HeaderDescription>
� <Posting_Date>01.11.06</Posting_Date>
� <Child_No>0805952639</Child_No>
� <Item_No>FOOD</Item_No>
� <Quantity>20</Quantity>
� <Unit_Price>235</Unit_Price>
� <Customer_Invoice>BREAD</Customer_Invoice>
� <Date_To>31.10.2006</Date_To>
� <Date_From>01.10.2006</Date_From>
� <School_No>04-211</School_No>
� <Card_No>1111222233334444</Card_No>
  <Card_Expire_Month>2</Card_Expire_Month>
  <Card_Expire_Year>2006</Card_Expire_Year>
� <Payment_Method_Code>V</Payment_Method_Code>
� <Duration_Month>10</Duration_Month>
<Duration_Day>31</Duration_Day>
</Parameters>
 *  Last modified: $Date: 2006/11/22 14:13:06 $ by $Author: eiki $
 * 
 * @author <a href="mailto:eiki@idega.com">eiki</a>
 * @version $Revision: 1.3 $
 */
public class NavisionXMLStringResult implements AccountingStringResult {

	protected static final String DURATION_DAY = "Duration_Day";
	protected static final String DURATION_MONTH = "Duration_Month";
	protected static final String PARAMETERS = "Parameters";
	protected static final String CARD_EXPIRE_YEAR = "Card_Expire_Year";
	protected static final String CARD_EXPIRE_MONTH = "Card_Expire_Month";
	protected static final String PAYMENT_METHOD_CODE = "Payment_Method_Code";
	protected static final String CARD_NO = "Card_No";
	protected static final String SCHOOL_NO = "School_No";
	protected static final String DATE_FROM = "Date_From";
	protected static final String DATE_TO = "Date_To";
	protected static final String CUSTOMER_INVOICE = "Customer_Invoice";
	protected static final String UNIT_PRICE = "Unit_Price";
	protected static final String QUANTITY = "Quantity";
	protected static final String ITEM_NO = "Item_No";
	protected static final String CHILD_NO = "Child_No";
	protected static final String POSTING_DATE = "Posting_Date";
	protected static final String HEADER_DESCRIPTION = "HeaderDescription";
	protected static final String CUSTOMER_NO = "Customer_No";
	private static final String DATE_FORMAT_DD_MM_YYYY = "dd.MM.yyyy";
	
	public String toString(IWApplicationContext iwac, AccountingEntry entry, CaseCodeAccountingKey key, IWTimestamp fromStamp, IWTimestamp toStamp) {
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(0);
		format.setMinimumFractionDigits(0);
		format.setGroupingUsed(false);

		StringBuffer buffer = new StringBuffer();
		StringBuffer returner = new StringBuffer();
		IWTimestamp now = new IWTimestamp();
		
		
		//customer pin
		
		addXMLTagWithValue(buffer,CUSTOMER_NO,entry.getPayerPersonalId().replaceAll("-","") );
		///description header
		addXMLTagWithValue(buffer,HEADER_DESCRIPTION,"IdegaWeb eGov");
		//posting date...today
		addXMLTagWithValue(buffer,POSTING_DATE,now.getDateString(DATE_FORMAT_DD_MM_YYYY));
		//child pin
		addXMLTagWithValue(buffer,CHILD_NO,entry.getPersonalId().replaceAll("-",""));
		//item no
		addXMLTagWithValue(buffer,ITEM_NO,entry.getProductCode());
		
		
		//Quantity
		String quantity;
		if (entry.getUnits() > 0) {
			quantity = String.valueOf(entry.getUnits());
		}
		else {
			quantity = format.format(entry.getAmount());
		}
		addXMLTagWithValue(buffer,QUANTITY,quantity);
		
		//unit price
		addXMLTagWithValue(buffer,UNIT_PRICE,format.format(entry.getUnitPrice()));
		
		//Customer_invoice
		addXMLTagWithValue(buffer,CUSTOMER_INVOICE,key.getAccountingKey());
		
		//date to
		String endDateString;
		if (entry.getEndDate() == null) {
			endDateString = toStamp.getDateString(DATE_FORMAT_DD_MM_YYYY);
		}
		else {
			IWTimestamp endDate = new IWTimestamp(entry.getEndDate());
			if (endDate.isLaterThan(toStamp)) {
				endDateString = toStamp.getDateString(DATE_FORMAT_DD_MM_YYYY);
			}
			else {
				endDateString = endDate.getDateString(DATE_FORMAT_DD_MM_YYYY);
			}
		}
		addXMLTagWithValue(buffer,DATE_TO,endDateString);
		
		
		//date from
		String startDateString;
		if (entry.getStartDate() == null) {
			startDateString = fromStamp.getDateString(DATE_FORMAT_DD_MM_YYYY);
		}
		else {
			IWTimestamp startDate = new IWTimestamp(entry.getStartDate());
			if (startDate.isEarlierThan(fromStamp)) {
				startDateString = fromStamp.getDateString(DATE_FORMAT_DD_MM_YYYY);
			}
			else {
				startDateString = startDate.getDateString(DATE_FORMAT_DD_MM_YYYY);
			}
		}
		addXMLTagWithValue(buffer,DATE_FROM,startDateString);
		
		//school number
		addXMLTagWithValue(buffer,SCHOOL_NO,entry.getProviderCode());
		
		//card number
		addXMLTagWithValue(buffer,CARD_NO,entry.getCardNumber());
	
		addXMLTagWithValue(buffer,PAYMENT_METHOD_CODE,entry.getCardType());
		
		addXMLTagWithValue(buffer,CARD_EXPIRE_MONTH,Integer.toString(entry.getCardExpirationMonth()));
		
		addXMLTagWithValue(buffer,CARD_EXPIRE_YEAR,Integer.toString(entry.getCardExpirationYear()));
		
		
		addXMLTagWithValue(buffer,DURATION_MONTH,Integer.toString(now.getMonth()));
		IWCalendar calendar = new IWCalendar();
		int daysInMonth = calendar.getLengthOfMonth(now.getMonth(), now.getYear());
		
		addXMLTagWithValue(buffer,DURATION_DAY,Integer.toString(daysInMonth));
	
//		if (entry.getExtraInformation() != null) {
//			buffer.append(",");
//			buffer.append(entry.getExtraInformation().toString());
//		}
		
		
		addXMLTagWithValue(returner,PARAMETERS,buffer.toString());
		
		return returner.toString();
	}
	
	
	protected void addXMLTagWithValue(StringBuffer buffer, String tagName,String value){
		if(value==null){
			value = "";
		}
		
		buffer.append('<')
		.append(tagName)
		.append('>')		
		.append(value)
		.append("</")
		.append(tagName)
		.append('>');
	}
}