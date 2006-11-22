/*
 * $Id: NavisionBusinessBean.java,v 1.2 2006/11/22 14:08:55 eiki Exp $
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.accounting.business;

import is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKey;
import is.idega.idegaweb.egov.accounting.wsimpl.U_getQueryXMLResponseU_getQueryXMLResult;
import is.idega.idegaweb.egov.accounting.wsimpl.WiseWebServiceLocator;
import is.idega.idegaweb.egov.accounting.wsimpl.WiseWebServiceSoap_PortType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;

import javax.xml.rpc.ServiceException;

import com.idega.util.IWCalendar;
import com.idega.util.IWTimestamp;

/**
 * This class extends AccountingKeyBusinessBean so we can call a webservice when ever someone creates an accounting file.
 * The application property "maritech.navision.enable" must be set and the bean checks if it is the last day of the month and if that month has been sent before.
 * Other application properties: maritech.nav.lastmonthsent,maritech.nav.lastmonthfailed,maritech.nav.fakecurrentdate . The last one can be used to force a month 
 * to process by setting it to a date of the last day of that month (2006-11-30)
 *  Last modified: $Date: 2006/11/22 14:08:55 $ by $Author: eiki $
 * 
 * @author <a href="mailto:eiki@idega.com">eiki</a>
 * @version $Revision: 1.2 $
 */
public class NavisionBusinessBean extends AccountingKeyBusinessBean implements NavisionBusiness,ActionListener{


	public static final String MARITECH_NAVISION_ENABLE = "maritech.navision.enable";
	public static final String MARITECH_NAV_LASTMONTHSENT = "maritech.nav.lastmonthsent";
	public static final String MARITECH_NAV_LASTMONTHFAILED = "maritech.nav.lastmonthfailed";
	public static final String MARITECH_NAV_FAKE_CURRENT_DATE = "maritech.nav.fakecurrentdate";
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 4432914699065417338L;
	
	private String lastWebServiceURL = null;
	private WiseWebServiceSoap_PortType maritechWebService;

	/**
	 * Overrides the method and calls a webservice for each accountingstring
	 */
	protected void onGeneratedAccountingString(String generatedAccountingString, AccountingEntry entry, CaseCodeAccountingKey key, String accountingSystem, IWTimestamp fromStamp, IWTimestamp toStamp) {

		if(isActive()){
			try {
				callWebService(generatedAccountingString);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}

	}


	/**
	 * @param generatedAccountingString
	 * @throws ServiceException 
	 */
	private void callWebService(String generatedAccountingString) throws ServiceException {
		//if so call the webservice for each casecodekey and account entry for that
		String maritechAddress = "http://postur.arborg.is:81/ApprovalsWS/Wisews.asmx";
		String webServiceAddress = (String)this.getIWApplicationContext().getApplicationSettings().getProperty("maritech.nav.ws", maritechAddress);
		

		try {
			WiseWebServiceSoap_PortType maritechService = getNavisionWebService(webServiceAddress);
			//maritechService.u_getQueryXML("", sPort, stUseCaseCode, stUser, stSessionKey, stCompanyName, stXML)

			//get all accountkeycodes and iterate the method 

			String internalNavisionServer = (String)this.getIWApplicationContext().getApplicationSettings().getProperty("maritech.nav.internal.ip", "172.20.1.11");
			String internalNavisionServerPort = (String)this.getIWApplicationContext().getApplicationSettings().getProperty("maritech.nav.internal.port", "8049");
			String companyName = (String)this.getIWApplicationContext().getApplicationSettings().getProperty("maritech.nav.companyName", "Árborg-EKKI NOTA-Afrit");
			String userName = (String)this.getIWApplicationContext().getApplicationSettings().getProperty("maritech.nav.user", "TM");

			U_getQueryXMLResponseU_getQueryXMLResult result = maritechService.u_getQueryXML(internalNavisionServer, internalNavisionServerPort, "SAVE_NEW_INVOICE_LINE", userName, "",companyName,generatedAccountingString);

			
		} catch (RemoteException e) {
			e.printStackTrace();
		} 
	}


	/**
	 * @param webServiceAddress
	 * @return
	 * @throws ServiceException
	 */
	protected synchronized WiseWebServiceSoap_PortType getNavisionWebService(String webServiceAddress) throws ServiceException {
		boolean serviceAddressChanged = false;
		if(this.lastWebServiceURL==null || !webServiceAddress.equals(lastWebServiceURL)){
			this.lastWebServiceURL = webServiceAddress;
			serviceAddressChanged = true;
		}
		
		if(maritechWebService==null || serviceAddressChanged){
			WiseWebServiceLocator wwLocator = new WiseWebServiceLocator();
			wwLocator.setWiseWebServiceSoapEndpointAddress(this.lastWebServiceURL);

			maritechWebService = wwLocator.getWiseWebServiceSoap();
		}
				
		return maritechWebService;
	}


	/**
	 * Gets all account entries for all casecodes for this month and sends them with the webservice to maritech
	 * @param now
	 * @return
	 */
	public void sendAllAccountingEntriesForMonth(IWTimestamp stamp){

		//get all the case codes and for each do:
		Collection caseCodes;
		try {
			caseCodes = this.getCaseCodeAccountingKeyHome().findAllCaseCodeAccountingKeys();
			
			for (Iterator iter = caseCodes.iterator(); iter.hasNext();) {
				CaseCodeAccountingKey key = (CaseCodeAccountingKey) iter.next();
				this.generateAccountingString(key.getCaseCode(), stamp.getDate(), false);
			}
			
			setMonthAsLastSent(stamp);
			
		} catch (Exception e) {
			//probably never reached because exception are buried!
			setMonthAsLastFailed(stamp);
			e.printStackTrace();
		}

	}


	/**
	 * @param stamp
	 */
	protected void setMonthAsLastSent(IWTimestamp stamp) {
		this.getIWApplicationContext().setApplicationAttribute(MARITECH_NAV_LASTMONTHSENT, Integer.toString(stamp.getMonth()));
	}
	
	/**
	 * @param stamp
	 */
	protected void setMonthAsLastFailed(IWTimestamp stamp) {
		this.getIWApplicationContext().setApplicationAttribute(MARITECH_NAV_LASTMONTHFAILED, Integer.toString(stamp.getMonth()));
	}


	public void actionPerformed(ActionEvent e) {
		IWTimestamp now = null;
		//fake current date option so we can force a month to process
		String fakeCurrentDate = (String)this.getIWApplicationContext().getApplicationSettings().getProperty(MARITECH_NAV_FAKE_CURRENT_DATE);
		if(fakeCurrentDate!=null){
			now = new IWTimestamp(fakeCurrentDate);
		}
		else{
			now = new IWTimestamp();
		}
		
		if(e.getActionCommand().equals(AccountingConstants.ACCOUNTING_SYSTEM_NAVISION_XML) && isActive() && isLastDayOfMonth(now) && !hasBeenSentForMonth(now)){
			sendAllAccountingEntriesForMonth(now);
		}
	}

	public boolean isLastDayOfMonth(IWTimestamp stamp) {
		IWCalendar calendar = new IWCalendar(stamp);
		int daysInMonth = calendar.getLengthOfMonth(stamp.getMonth(), stamp.getYear());
		int thisDayOfMonth = stamp.getDay();
		
		return (daysInMonth==thisDayOfMonth);
	}


	public boolean hasBeenSentForMonth(IWTimestamp stamp) {
		String lastMonthSent = (String)this.getIWApplicationContext().getApplicationSettings().getProperty(MARITECH_NAV_LASTMONTHSENT);
		//its a java.sql.date!
		int month = stamp.getMonth();
		
		if(lastMonthSent!=null){
			return Integer.parseInt(lastMonthSent)<=month;
		}
		else{
			return false;
		}	
	}

	
	/**
	 * @return
	 */
	public boolean isActive() {
		String accountingSystem = this.getIWApplicationContext().getApplicationSettings().getProperty(AccountingConstants.PROPERTY_ACCOUNTING_SYSTEM);
		String enabled = this.getIWApplicationContext().getApplicationSettings().getProperty(MARITECH_NAVISION_ENABLE);
		
		
		return (AccountingConstants.ACCOUNTING_SYSTEM_NAVISION_XML).equals(accountingSystem) && enabled!=null;
	}



}