/**
 * AccountingServiceSOAPImpl.java
 * 
 * This file was auto-generated from WSDL by the Apache Axis 1.3 Oct 05, 2005
 * (05:23:37 EDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.accounting.wsimpl;

import is.idega.idegaweb.egov.accounting.business.AccountingBusiness;
import is.idega.idegaweb.egov.accounting.business.AccountingBusinessManager;
import is.idega.idegaweb.egov.accounting.business.AccountingEntry;
import is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKey;
import is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKeyHome;

import java.rmi.RemoteException;
import java.util.Date;

import javax.ejb.FinderException;

import com.idega.block.process.data.CaseCode;
import com.idega.data.IDOLookup;
import com.idega.data.IDOLookupException;
import com.idega.idegaweb.IWMainApplication;
import com.idega.util.IWTimestamp;

public class AccountingServiceSOAPImpl implements is.idega.idegaweb.egov.accounting.wsimpl.AccountingService_PortType {

	public BillingEntry[] getBillingEntries(is.idega.idegaweb.egov.accounting.wsimpl.BillingEntriesRequest getBillingEntriesRequest) throws java.rmi.RemoteException {
		try {
			CaseCodeAccountingKeyHome home = (CaseCodeAccountingKeyHome) IDOLookup.getHome(CaseCodeAccountingKey.class);
			
			CaseCodeAccountingKey key = null;
			CaseCode code = null;
			if(getBillingEntriesRequest.getServiceCode()!=null){
				try{
					key=home.findByAccountingKey(getBillingEntriesRequest.getServiceCode());
					code=key.getCaseCode();
				}
				catch(FinderException fe){
					fe.printStackTrace();
				}
			}
			AccountingBusiness business = AccountingBusinessManager.getInstance().getAccountingBusinessOrDefault(code, IWMainApplication.getDefaultIWApplicationContext());
			
			
			IWTimestamp periodStart = null;
			if(getBillingEntriesRequest.getPeriodStart()!=null){
				periodStart = new IWTimestamp(getBillingEntriesRequest.getPeriodStart());
			}
			IWTimestamp periodEnd = null;
			if(getBillingEntriesRequest.getPeriodEnd()!=null){
				periodEnd=new IWTimestamp(getBillingEntriesRequest.getPeriodEnd());
			}
			if (business != null) {
				
				AccountingEntry[] entries = business.getAccountingEntries(getBillingEntriesRequest.getServiceCode(), getBillingEntriesRequest.getProviderCode(), periodStart.getDate(), periodEnd.getDate());
				BillingEntry[] bEntries = convertToBillingEntries(entries);
				return bEntries;
			}
		}
		catch (IDOLookupException ile) {
			ile.printStackTrace();
		}
		
		return null;
		//return generateDummyData();
	}

	/**
	 * <p>
	 * TODO tryggvil describe method convertToBillingEntries
	 * </p>
	 * @param entries
	 * @return
	 */
	private BillingEntry[] convertToBillingEntries(AccountingEntry[] entries) {
		
		BillingEntry[] returnArray = new BillingEntry[entries.length];
		for (int i = 0; i < entries.length; i++) {
			
			AccountingEntry oldEntry = entries[i];
			
			BillingEntry entry = new BillingEntry();
			entry.setPersonalId(oldEntry.getPayerPersonalId());
			entry.setPayerPersonalId(oldEntry.getPayerPersonalId());
			entry.setProviderCode(oldEntry.getProviderCode());
			entry.setProjectCode(oldEntry.getProductCode());
			entry.setProjectCode(oldEntry.getProjectCode());
			entry.setAmount(oldEntry.getAmount());
			entry.setStartDate(oldEntry.getStartDate());
			entry.setPaymentMethod(oldEntry.getPaymentMethod());
			entry.setUnitPrice(oldEntry.getUnitPrice());
			entry.setCardNumber(oldEntry.getCardNumber());
			entry.setCardType(oldEntry.getCardType());
			entry.setCardExpirationMonth(oldEntry.getCardExpirationMonth());
			entry.setCardExpirationYear(oldEntry.getCardExpirationYear());
			returnArray[i]=entry;
		}
		return returnArray;
	}
	
	/*private BillingEntry[] generateDummyData() {
		
		BillingEntry[] returnArray = new BillingEntry[2];
			
			BillingEntry entry = new BillingEntry();
			entry.setPersonalId("180800-3280");
			entry.setPayerPersonalId("251067-5039");
			entry.setProviderCode("i7138");
			entry.setProjectCode("AFTER_SCHOOL_CARE");
			entry.setProjectCode("R18856");
			entry.setProductCode("product1");
			entry.setAmount(1);
			Date start0 = IWTimestamp.getTimestampRightNow();
			start0.setMonth(GregorianCalendar.JUNE);
			entry.setStartDate(start0);
			Date end0 = IWTimestamp.getTimestampRightNow();
			end0.setMonth(GregorianCalendar.AUGUST);
			entry.setEndDate(end0);
			entry.setPaymentMethod("visa");
			entry.setUnitPrice(5000);
			entry.setCardNumber("5411399203191208");
			entry.setCardType("visa");
			entry.setCardExpirationMonth(10);
			entry.setCardExpirationYear(2006);
			returnArray[0]=entry;

			BillingEntry entry1 = new BillingEntry();
			entry1.setPersonalId("180800-3280");
			entry1.setPayerPersonalId("251067-5039");
			entry1.setProviderCode("i7138");
			entry1.setProjectCode("REFRESHMENTS");
			entry1.setProjectCode("R18856");
			entry1.setProductCode("product1");
			entry1.setAmount(1);
			Date start1 = IWTimestamp.getTimestampRightNow();
			start1.setMonth(GregorianCalendar.JUNE);
			entry1.setStartDate(start1);
			Date end1 = IWTimestamp.getTimestampRightNow();
			end1.setMonth(GregorianCalendar.AUGUST);
			entry1.setEndDate(end1);
			entry1.setPaymentMethod("euro");
			entry1.setUnitPrice(5000);
			entry1.setCardNumber("5411399203191208");
			entry1.setCardType("euro");
			entry1.setCardExpirationMonth(12);
			entry1.setCardExpirationYear(2007);
			returnArray[1]=entry1;
			
		return returnArray;
	}*/

	/* (non-Javadoc)
	 * @see is.idega.idegaweb.egov.accounting.wsimpl.AccountingService_PortType#getBillingEntriesFromServiceCode(java.lang.String)
	 */
	public BillingEntry[] getBillingEntriesFromServiceCode(String serviceCode) throws RemoteException {
		//GregorianCalendar calendar = new GregorianCalendar();
		Date start = IWTimestamp.RightNow().getDate();
		Date end = IWTimestamp.RightNow().getDate();	
		BillingEntriesRequest getBillingEntriesRequest = new BillingEntriesRequest(serviceCode,null,start,end);
		return getBillingEntries(getBillingEntriesRequest);
	}

	/* (non-Javadoc)
	 * @see is.idega.idegaweb.egov.accounting.wsimpl.AccountingService_PortType#getAllBillingEntries()
	 */
	public BillingEntry[] getAllBillingEntries() throws RemoteException {
		//GregorianCalendar calendar = new GregorianCalendar();
		Date start = IWTimestamp.RightNow().getDate();
		Date end = IWTimestamp.RightNow().getDate();
		BillingEntriesRequest getBillingEntriesRequest = new BillingEntriesRequest(null,null,start,end);
		return getBillingEntries(getBillingEntriesRequest);
	}

}