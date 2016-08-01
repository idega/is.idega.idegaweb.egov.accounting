/*
 * $Id: NavisionBusinessBean.java,v 1.8 2008/05/10 11:52:04 valdas Exp $ Created
 * on Jul 12, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf. Use is subject to
 * license terms.
 */
package is.idega.idegaweb.egov.accounting.business;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;

import javax.xml.rpc.ServiceException;

import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWMainApplication;
import com.idega.idegaweb.IWMainApplicationSettings;
import com.idega.util.IWCalendar;
import com.idega.util.IWTimestamp;

import is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKey;
import is.idega.idegaweb.egov.accounting.wsimpl.WiseWebServiceLocator;
import is.idega.idegaweb.egov.accounting.wsimpl.WiseWebServiceSoap_PortType;

/**
 * This class extends AccountingKeyBusinessBean so we can call a webservice when
 * ever someone creates an accounting file. The application property
 * "maritech.navision.enable" must be set and the bean checks if it is the last
 * day of the month and if that month has been sent before. Other application
 * properties:
 * maritech.nav.lastmonthsent,maritech.nav.lastmonthfailed,maritech.nav.fakecurrentdate .
 * The last one can be used to force a month to process by setting it to a date
 * of the last day of that month (2006-11-30) Last modified: $Date: 2006/11/23
 * 12:07:48 $ by $Author: valdas $
 *
 * @author <a href="mailto:eiki@idega.com">eiki</a>
 * @version $Revision: 1.8 $
 */
public class NavisionBusinessBean extends AccountingKeyBusinessBean implements NavisionBusiness, ActionListener {

	public static final String MARITECH_NAVISION_ENABLE = "maritech.navision.enable";
	public static final String MARITECH_NAV_LASTMONTHSENT = "maritech.nav.lastmonthsent";
	public static final String MARITECH_NAV_LASTMONTHFAILED = "maritech.nav.lastmonthfailed";
	public static final String MARITECH_NAVISION_ALLOWED_CASE_CODES = "maritech.nav.month.casecodes";

	/**
	 * The date of the last month sent "ahead" in time e.g. december records will be sent on the 1.december for the whole month of December
	 */
	public static final String MARITECH_NAV_LAST_FUTURE_MONTHSENT = "maritech.nav.lastFutureMSent";
	public static final String MARITECH_NAV_LAST_FUTURE_MONTHFAILED = "maritech.nav.lastFutureMFail";
	public static final String MARITECH_NAVISION_ALLOWED_FUTURE_MONTH_CASE_CODES = "maritech.nav.futuremonthccodes";


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
	@Override
	protected void onGeneratedAccountingString(String generatedAccountingString, AccountingEntry entry, CaseCodeAccountingKey key, String accountingSystem, IWTimestamp fromStamp, IWTimestamp toStamp) {

		if (isActive()) {
			try {
				callWebService(generatedAccountingString);
			}
			catch (ServiceException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * @param generatedAccountingString
	 * @throws ServiceException
	 */
	private void callWebService(String generatedAccountingString) throws ServiceException {
		// if so call the webservice for each casecodekey and account entry for that
		String maritechAddress = "http://postur.arborg.is:81/ApprovalsWS/Wisews.asmx";
		String webServiceAddress = this.getIWApplicationContext().getApplicationSettings().getProperty("maritech.nav.ws", maritechAddress);

		try {
			WiseWebServiceSoap_PortType maritechService = getNavisionWebService(webServiceAddress);
			// maritechService.u_getQueryXML("", sPort, stUseCaseCode, stUser,
			// stSessionKey, stCompanyName, stXML)

			// get all accountkeycodes and iterate the method

			String internalNavisionServer = this.getIWApplicationContext().getApplicationSettings().getProperty("maritech.nav.internal.ip", "172.20.1.11");
			String internalNavisionServerPort = this.getIWApplicationContext().getApplicationSettings().getProperty("maritech.nav.internal.port", "8049");
			String companyName = this.getIWApplicationContext().getApplicationSettings().getProperty("maritech.nav.companyName", "Árborg-EKKI NOTA-Afrit");
			String userName = this.getIWApplicationContext().getApplicationSettings().getProperty("maritech.nav.user", "TM");

			maritechService.u_getQueryXML(internalNavisionServer, internalNavisionServerPort, "SAVE_NEW_INVOICE_LINE", userName, "", companyName, generatedAccountingString);

		}
		catch (RemoteException e) {
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
		if (this.lastWebServiceURL == null || !webServiceAddress.equals(lastWebServiceURL)) {
			this.lastWebServiceURL = webServiceAddress;
			serviceAddressChanged = true;
		}

		if (maritechWebService == null || serviceAddressChanged) {
			WiseWebServiceLocator wwLocator = new WiseWebServiceLocator();
			wwLocator.setWiseWebServiceSoapEndpointAddress(this.lastWebServiceURL);

			maritechWebService = wwLocator.getWiseWebServiceSoap();
		}

		return maritechWebService;
	}

	/**
	 * Gets all account entries for all casecodes for this month and sends them
	 * with the webservice to maritech. Filters the allowed casecodes from an application property
	 * "maritech.nav.month.casecodes" which is a comma seperated case code string of allowed case codes.
	 * If the string is empty it sends ALL case codes.
	 *
	 * @param now
	 * @return
	 */
	@Override
	public void sendAllAccountingEntriesForMonth(IWTimestamp stamp) {

		log("STARTING to send account entries to Navision (" + stamp.toString() + ")");
		String allowedCaseCodes = this.getIWApplicationContext().getApplicationSettings().getProperty(MARITECH_NAVISION_ALLOWED_CASE_CODES, "");
		boolean anythingSent = false;
		// get all the case codes and for each do:
		Collection caseCodes;
		try {
			caseCodes = this.getCaseCodeAccountingKeyHome().findAllCaseCodeAccountingKeys();

			for (Iterator iter = caseCodes.iterator(); iter.hasNext();) {
				CaseCodeAccountingKey key = (CaseCodeAccountingKey) iter.next();
				if(!"".equals(allowedCaseCodes)){
					if(allowedCaseCodes.indexOf(key.getCaseCodeString())>=0){
						this.generateAccountingString(key.getCaseCode(), stamp.getDate(), false);
						anythingSent = true;
					}
				}
				else{
					this.generateAccountingString(key.getCaseCode(), stamp.getDate(), false);
					anythingSent = true;
				}
			}

			if(anythingSent){
				setMonthAsLastSent(stamp);
			}

			log("FINISHED sending account entries to Navision (" + stamp.toString() + ")");

		}
		catch (Exception e) {
			// probably never reached because exception are buried!
			setMonthAsLastFailed(stamp);
			e.printStackTrace();

			log("FAILED ON sending account entries to Navision (" + stamp.toString() + ")");
		}

	}

	/**
	 * Gets all account entries for all casecodes for a future month (on the 1st of every month) and sends them
	 * with the webservice to maritech. Filters the allowed casecodes from an application property
	 * "maritech.nav.futuremonthccodes" which is a comma seperated case code string of allowed case codes.
	 * If the string is empty it sends NO case codes this is the biggest difference from the current month method
	 *
	 * @param now
	 * @return
	 */
	public void sendAllAccountingEntriesForFutureMonth(IWTimestamp stamp) {

		log("STARTING to send future account entries to Navision (" + stamp.toString() + ")");
		String futureCaseCodes = this.getIWApplicationContext().getApplicationSettings().getProperty(MARITECH_NAVISION_ALLOWED_FUTURE_MONTH_CASE_CODES, "");
		boolean anythingSent = false;

		// get all the case codes and for each do:
		Collection caseCodes;
		try {
			if(!"".equals(futureCaseCodes)){
				//todo when not lazy, find exactly the case codes in futureCaseCodes...
				caseCodes = this.getCaseCodeAccountingKeyHome().findAllCaseCodeAccountingKeys();

				for (Iterator iter = caseCodes.iterator(); iter.hasNext();) {
					CaseCodeAccountingKey key = (CaseCodeAccountingKey) iter.next();

					//only send for valid codes.
					if(futureCaseCodes.indexOf(key.getCaseCodeString())>=0){
						this.generateAccountingString(key.getCaseCode(), stamp.getDate(), false);
						anythingSent = true;
					}

				}

				if(anythingSent){
					setMonthAsLastFutureMonthSent(stamp);
				}
			}
			log("FINISHED sending future account entries to Navision (" + stamp.toString() + ")");

		}
		catch (Exception e) {
			// probably never reached because exception are buried!
			setMonthAsLastFutureMonthFailed(stamp);
			e.printStackTrace();

			log("FAILED ON sending future account entries to Navision (" + stamp.toString() + ")");
		}

	}

	/**
	 * @param stamp
	 */
	protected void setMonthAsLastSent(IWTimestamp stamp) {
		this.getIWApplicationContext().getApplicationSettings().setProperty(MARITECH_NAV_LASTMONTHSENT, Integer.toString(stamp.getMonth()));
	}

	/**
	 * @param stamp
	 */
	protected void setMonthAsLastFutureMonthSent(IWTimestamp stamp) {
		this.getIWApplicationContext().getApplicationSettings().setProperty(MARITECH_NAV_LAST_FUTURE_MONTHSENT, Integer.toString(stamp.getMonth()));
	}

	/**
	 * @param stamp
	 */
	protected void setMonthAsLastFailed(IWTimestamp stamp) {
		this.getIWApplicationContext().getApplicationSettings().setProperty(MARITECH_NAV_LASTMONTHFAILED, Integer.toString(stamp.getMonth()));
	}

	/**
	 * @param stamp
	 */
	protected void setMonthAsLastFutureMonthFailed(IWTimestamp stamp) {
		this.getIWApplicationContext().getApplicationSettings().setProperty(MARITECH_NAV_LAST_FUTURE_MONTHFAILED, Integer.toString(stamp.getMonth()));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			IWApplicationContext iwac = IWMainApplication.getDefaultIWApplicationContext();
			if (iwac == null) {
				getLogger().warning(IWApplicationContext.class.getName() + " can not be resolved");
				return;
			}

			IWMainApplicationSettings settings = iwac.getApplicationSettings();
			if (settings == null) {
				getLogger().warning(IWMainApplicationSettings.class.getName() + " can not be resolved");
				return;
			}

			IWTimestamp now = null;
			// fake current date option so we can force a month to process
			String fakeCurrentDate = settings.getProperty(MARITECH_NAV_FAKE_CURRENT_DATE);
			if (fakeCurrentDate != null) {
				now = new IWTimestamp(fakeCurrentDate);
			}
			else {
				now = new IWTimestamp();
			}

			if(e.getActionCommand().equals(AccountingConstants.ACCOUNTING_SYSTEM_NAVISION_XML) && isActive() ){
				if (canSendForMonth(now)) {
					//todo possible create a new thread that waits till the last minute of the day to send?
					sendAllAccountingEntriesForMonth(now);
				}
				else if (canSendForFutureMonth(now)) {
					sendAllAccountingEntriesForFutureMonth(now);
				}
			}
		} catch (Exception ex) {
			getLogger().log(Level.WARNING, "Error performing event: " + e, e);
		}
	}

	/**
	 * @param now
	 * @return
	 */
	protected boolean canSendForFutureMonth(IWTimestamp now) {
		return isFirstDayOfMonth(now) && !hasBeenSentForFutureMonth(now);
	}

	/**
	 * @param now
	 * @return
	 */
	protected boolean canSendForMonth(IWTimestamp now) {
		return isLastDayOfMonth(now) && !hasBeenSentForMonth(now);
	}

	@Override
	public boolean isLastDayOfMonth(IWTimestamp stamp) {
		IWCalendar calendar = new IWCalendar(stamp);
		int daysInMonth = calendar.getLengthOfMonth(stamp.getMonth(), stamp.getYear());
		int thisDayOfMonth = stamp.getDay();

		return (daysInMonth == thisDayOfMonth);
	}

	public boolean isFirstDayOfMonth(IWTimestamp stamp) {
		int thisDayOfMonth = stamp.getDay();
		return (1==thisDayOfMonth);
	}

	@Override
	public boolean hasBeenSentForMonth(IWTimestamp stamp) {
		String lastMonthSent = this.getIWApplicationContext().getApplicationSettings().getProperty(MARITECH_NAV_LASTMONTHSENT);
		// its a java.sql.date!
		return isMonthDoneOrANewYearBeginning(stamp, lastMonthSent);
	}

	/**
	 * @param stamp
	 * @param lastMonthSent
	 * @return
	 */
	protected boolean isMonthDoneOrANewYearBeginning(IWTimestamp stamp, String lastMonthSent) {
		int month = stamp.getMonth();

		if (lastMonthSent != null) {
			int last = Integer.parseInt(lastMonthSent);
			if(last==12 && month!=last){
				return false;
			}
			else{
				return last >= month;
			}
		}
		else {
			return false;
		}
	}

	public boolean hasBeenSentForFutureMonth(IWTimestamp stamp) {
		String lastMonthSent = this.getIWApplicationContext().getApplicationSettings().getProperty(MARITECH_NAV_LAST_FUTURE_MONTHSENT);
		// its a java.sql.date!
		return isMonthDoneOrANewYearBeginning(stamp, lastMonthSent);
	}

	/**
	 * @return
	 */
	@Override
	public boolean isActive() {
		String accountingSystem = this.getIWApplicationContext().getApplicationSettings().getProperty(AccountingConstants.PROPERTY_ACCOUNTING_SYSTEM);
		String enabled = this.getIWApplicationContext().getApplicationSettings().getProperty(MARITECH_NAVISION_ENABLE);

		return (AccountingConstants.ACCOUNTING_SYSTEM_NAVISION_XML).equals(accountingSystem) && enabled != null;
	}

	protected IWApplicationContext getDefaultIWApplicationContext() {
		return IWMainApplication.getDefaultIWApplicationContext();
	}

	protected IWMainApplicationSettings getApplicationSettings() {
		IWApplicationContext context = getDefaultIWApplicationContext();
		if (context != null) {
			return context.getApplicationSettings();
		}

		return null;
	}

	protected String getProperty(String propertyName) {
		IWMainApplicationSettings applicationSettings = getApplicationSettings();
		if (applicationSettings != null) {
			return applicationSettings.getProperty(propertyName);
		}

		return null;
	}
}