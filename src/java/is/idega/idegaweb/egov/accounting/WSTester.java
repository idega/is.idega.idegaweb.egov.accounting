/**
 * 
 */
package is.idega.idegaweb.egov.accounting;

import java.sql.Date;
import com.idega.util.IWTimestamp;
import is.idega.idegaweb.egov.accounting.wsimpl.AccountingService_PortType;
import is.idega.idegaweb.egov.accounting.wsimpl.AccountingService_ServiceLocator;
import is.idega.idegaweb.egov.accounting.wsimpl.BillingEntriesRequest;
import is.idega.idegaweb.egov.accounting.wsimpl.BillingEntry;


/**
 * <p>
 * TODO tryggvil Describe Type WSTester
 * </p>
 *  Last modified: $Date: 2006/08/24 12:23:46 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.1 $
 */
public class WSTester {

	/**
	 * <p>
	 * TODO tryggvil describe method main
	 * </p>
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		String address = "http://localhost:8090/services/AccountingServiceSOAP";
		
		AccountingService_ServiceLocator locator = new AccountingService_ServiceLocator();
		locator.setAccountingServiceSOAPEndpointAddress(address);
		AccountingService_PortType service = locator.getAccountingServiceSOAP();
		
		IWTimestamp fromDateTS = new IWTimestamp();
		fromDateTS.addDays(-62);
		Date fromDate = fromDateTS.getDate();
		IWTimestamp toDateTS = new IWTimestamp();
		toDateTS.addDays(62);
		Date toDate = toDateTS.getDate();
		
		
		BillingEntriesRequest getBillingEntriesRequest = new BillingEntriesRequest("ITR",null,fromDate,toDate);
		BillingEntry[] entries = service.getBillingEntries(getBillingEntriesRequest);
		
		//BillingEntry[] entries = service.getBillingEntriesFromServiceCode("ITR");
		System.out.println("Got "+entries.length+" entries");
	}
}
