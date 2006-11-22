/**
 * 
 */
package is.idega.idegaweb.egov.accounting;

import is.idega.idegaweb.egov.accounting.wsimpl.U_getQueryXMLResponseU_getQueryXMLResult;
import is.idega.idegaweb.egov.accounting.wsimpl.WiseWebServiceLocator;
import is.idega.idegaweb.egov.accounting.wsimpl.WiseWebServiceSoap_PortType;

import org.apache.axis.message.MessageElement;

/**
 * <p>
 * TODO tryggvil Describe Type WSTester
 * </p>
 * Last modified: $Date: 2006/11/22 14:06:34 $ by $Author: eiki $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.3 $
 */
public class WSTester {

	/**
	 * <p>
	 * TODO tryggvil describe method main
	 * </p>
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		String resultString = "<Parameters><Customer_No>1110744299</Customer_No><HeaderDescription>IdegaWeb eGov</HeaderDescription><Posting_Date>02.11.2006</Posting_Date><Child_No>1702973939</Child_No><Item_No>SK�LM-04216</Item_No><Quantity>20</Quantity><Unit_Price>210</Unit_Price><Customer_Invoice>SK�LMAT</Customer_Invoice><Date_To>31.10.2006</Date_To><Date_From>01.10.2006</Date_From><School_No>216</School_No><Card_No></Card_No><Payment_Method_Code></Payment_Method_Code><Card_Expire_Month>0</Card_Expire_Month><Card_Expire_Year>0</Card_Expire_Year><Duration_Month>11</Duration_Month><Duration_Day>30</Duration_Day></Parameters>";
		callWebService(resultString);

		// other test service
		// String address =
		// "http://localhost:8090/services/AccountingServiceSOAP";
		//		
		// AccountingService_ServiceLocator locator = new
		// AccountingService_ServiceLocator();
		// locator.setAccountingServiceSOAPEndpointAddress(address);
		// AccountingService_PortType service =
		// locator.getAccountingServiceSOAP();
		//		
		// IWTimestamp fromDateTS = new IWTimestamp();
		// fromDateTS.addDays(-62);
		// Date fromDate = fromDateTS.getDate();
		// IWTimestamp toDateTS = new IWTimestamp();
		// toDateTS.addDays(62);
		// Date toDate = toDateTS.getDate();
		//		
		//		
		// BillingEntriesRequest getBillingEntriesRequest = new
		// BillingEntriesRequest("ITR",null,fromDate,toDate);
		// BillingEntry[] entries =
		// service.getBillingEntries(getBillingEntriesRequest);
		//		
		// //BillingEntry[] entries =
		// service.getBillingEntriesFromServiceCode("ITR");
		// System.out.println("Got "+entries.length+" entries");
	}

	protected static void callWebService(String resultString) {
		// if so call the webservice for each casecodekey and account entry for
		// that
		 String maritechAddress = "http://postur.arborg.is:81/ApprovalsWS/Wisews.asmx";
		 //String maritechAddress = "http://172.20.1.11:81/ApprovalsWS/Wisews.asmx";
			// tcpmon address
		//String maritechAddress = "http://127.0.0.1:9091/ApprovalsWS/Wisews.asmx";
		
		try {
			WiseWebServiceLocator wwLocator = new WiseWebServiceLocator();
			wwLocator.setWiseWebServiceSoapEndpointAddress(maritechAddress);

			WiseWebServiceSoap_PortType maritechService = wwLocator
					.getWiseWebServiceSoap();
			// maritechService.u_getQueryXML("", sPort, stUseCaseCode, stUser,
			// stSessionKey, stCompanyName, stXML)

			// get all accountkeycodes and iterate the method

			U_getQueryXMLResponseU_getQueryXMLResult result;
			//resultString.replaceAll("&", "&amp;");
			//result = maritechService.u_getQueryXML("172.20.1.11", "8049", "","TM", "", "Á�rborg-EKKI NOTA-Afrit", URLEncoder.encode(resultString, "ISO-8859-1"));
			
			//usersessionid must be there!!!!
			result = maritechService.u_getQueryXML("172.20.1.11", "8049","SAVE_NEW_INVOICE_LINE","TM", "", "�Árborg-EKKI NOTA-Afrit", resultString);

			System.out.println("Navision sync result : ");
			
			MessageElement[] msg = result.get_any();
			
			for (int i = 0; i < msg.length; i++) {
				MessageElement element = msg[i];
				System.out.println(element);
			}
 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// set last finished update in app property

		// send user message / email to cashier at maritech, email as bundle
		// property?

	}
}
