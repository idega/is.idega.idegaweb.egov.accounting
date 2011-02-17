/*
 * $Id$ Created on Dec 18, 2006
 * 
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to
 * license terms.
 */
package is.idega.idegaweb.egov.accounting.business;

import is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKey;
import is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKeyHome;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Logger;

import com.idega.block.process.data.CaseCode;
import com.idega.business.IBOServiceBean;
import com.idega.data.IDOLookup;
import com.idega.util.IWTimestamp;
import com.idega.util.database.ConnectionBroker;

public class AgressoBusinessBean extends IBOServiceBean implements AgressoBusiness {

	static Logger log = Logger.getLogger(AgressoBusinessBean.class.getName());

	public void executeAfterSchoolCareUpdate() {
		log.info("Starting Agresso after school care update");

		Connection conn = ConnectionBroker.getConnection();
		int prevTransactionLevel = Connection.TRANSACTION_SERIALIZABLE;
		boolean prevAutoComm = true;
		try {
			prevTransactionLevel = conn.getTransactionIsolation();
			prevAutoComm = conn.getAutoCommit();
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
		AccountingEntry entry;
		try {
			String tableName = "RRVK_AGRESSO";

			conn.setAutoCommit(false);

			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

			Statement stmt1 = conn.createStatement();
			stmt1.executeUpdate("delete from " + tableName);
			stmt1.close();

			CaseCodeAccountingKeyHome ccah = (CaseCodeAccountingKeyHome) IDOLookup.getHome(CaseCodeAccountingKey.class);
			CaseCodeAccountingKey accKey = ccah.findByAccountingKey("ITR");
			CaseCode afterSchCare = accKey.getCaseCode();

			AccountingBusiness business = AccountingBusinessManager.getInstance().getAccountingBusinessOrDefault(afterSchCare, this.getIWApplicationContext());

			IWTimestamp fromDateTS = new IWTimestamp();
			fromDateTS.addDays(-62);
			Date fromDate = fromDateTS.getDate();
			String productCode = null;
			IWTimestamp toDateTS = new IWTimestamp();
			toDateTS.addDays(62);
			Date toDate = toDateTS.getDate();
			AccountingEntry[] entries = business.getAccountingEntries(productCode, null, fromDate, toDate);

			PreparedStatement stmt2 = conn.prepareCall("insert into " + tableName + "(PAYER_PERSONAL_ID,PERSONAL_ID,PRODUCT_CODE,PROVIDER_CODE,PAYMENT_TYPE,CARD_NUMBER,CARD_EXPIRATION_MONTH,CARD_EXPIRATION_YEAR,START_DATE,END_DATE,FAMILY_NUMBER,SIBLING_NUMBER) values(?,?,?,?,?,?,?,?,?,?,?,?)");

			for (int i = 0; i < entries.length; i++) {
				entry = entries[i];
				Date startDate = null;
				if (entry.getStartDate() != null) {
					startDate = new IWTimestamp(entry.getStartDate()).getDate();
				}
				Date endDate = null;
				if (entry.getEndDate() != null) {
					endDate = new IWTimestamp(entry.getEndDate()).getDate();
				}

				stmt2.setString(1, entry.getPayerPersonalId());
				stmt2.setString(2, entry.getPersonalId());
				stmt2.setString(3, entry.getProductCode() + "_" + entry.getNumberOfDaysPrWeek());
				stmt2.setString(4, entry.getProviderCode());
				stmt2.setString(5, entry.getPaymentMethod());
				stmt2.setString(6, entry.getCardNumber());
				stmt2.setString(7, Integer.toString(entry.getCardExpirationMonth()));
				stmt2.setString(8, Integer.toString(entry.getCardExpirationYear()));
				stmt2.setDate(9, startDate);
				stmt2.setDate(10, endDate);
				if (entry.getFamilyNumber() != null) {
					stmt2.setString(11, entry.getFamilyNumber());
				} else {
					stmt2.setString(11, "");					
				}
				stmt2.setInt(12, entry.getSiblingNumber());
				stmt2.execute();
			}

			stmt2.close();
			conn.commit();

			log.info("Finished Agresso update successfully");
		}
		catch (Exception e) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			}
			catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally {
			if (conn != null) {

				try {
					conn.setAutoCommit(prevAutoComm);
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					conn.setTransactionIsolation(prevTransactionLevel);
				}
				catch (SQLException e) {
					e.printStackTrace();
				}

				ConnectionBroker.freeConnection(conn);
			}
		}
	}

	public void executeCourseUpdate() {
		log.info("Starting Agresso course update");

		Connection conn = ConnectionBroker.getConnection();
		int prevTransactionLevel = Connection.TRANSACTION_SERIALIZABLE;
		boolean prevAutoComm = true;
		try {
			prevTransactionLevel = conn.getTransactionIsolation();
			prevAutoComm = conn.getAutoCommit();
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
		AccountingEntry entry;
		try {
			String tableName = "RRVK_AGRESSO_COURSE";

			conn.setAutoCommit(false);

			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

			Statement stmt1 = conn.createStatement();
			stmt1.executeUpdate("delete from " + tableName);
			stmt1.close();

			CaseCodeAccountingKeyHome ccah = (CaseCodeAccountingKeyHome) IDOLookup.getHome(CaseCodeAccountingKey.class);
			CaseCodeAccountingKey accKey = ccah.findByAccountingKey("ITRNAMSK");
			CaseCode afterSchCare = accKey.getCaseCode();

			AccountingBusiness business = AccountingBusinessManager.getInstance().getAccountingBusinessOrDefault(afterSchCare, this.getIWApplicationContext());

			IWTimestamp fromDateTS = new IWTimestamp();
			fromDateTS.setDay(1);
			fromDateTS.setMonth(1);
			Date fromDate = fromDateTS.getDate();
			String productCode = null;
			IWTimestamp toDateTS = new IWTimestamp();
			toDateTS.addMonths(1);
			Date toDate = toDateTS.getDate();

			AccountingEntry[] entries = business.getAccountingEntries(productCode, null, fromDate, toDate);

			PreparedStatement stmt2 = conn.prepareCall("insert into " + tableName + "(PAYER_PERSONAL_ID,PERSONAL_ID,PRODUCT_CODE,PROVIDER_CODE,TYPE_CODE,CENTER_CODE,PAYMENT_TYPE,PRICE,PAYMENT_DATE,BATCH_NUMBER,COURSE_NAME,UNIQUE_ID,START_DATE,END_DATE,CARD_TYPE,CARD_NUMBER,CARD_VALIDITY) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			for (int i = 0; i < entries.length; i++) {
				entry = entries[i];
				Object extra = entry.getExtraInformation();
				stmt2.setString(1, entry.getPayerPersonalId());
				stmt2.setString(2, entry.getPersonalId());
				stmt2.setString(3, entry.getProductCode());
				stmt2.setString(4, entry.getProviderCode());
				stmt2.setString(5, entry.getProjectCode());
				stmt2.setString(7, entry.getPaymentMethod());
				stmt2.setInt(8, entry.getAmount());
				stmt2.setDate(13, new IWTimestamp(entry.getStartDate()).getDate());
				stmt2.setDate(14, new IWTimestamp(entry.getEndDate()).getDate());
				stmt2.setString(15, entry.getCardType());
				stmt2.setString(16, entry.getCardNumber());
				if (entry.getCardNumber() != null) {
					IWTimestamp stamp = new IWTimestamp();
					stamp.setYear(entry.getCardExpirationYear());
					stamp.setMonth(entry.getCardExpirationMonth());
					stmt2.setString(17, stamp.getDateString("MM/yyyy"));
				}
				else {
					stmt2.setString(17, null);
				}

				if (extra instanceof AccountingEntry) {
					AccountingEntry extraEntry = (AccountingEntry) extra;
					stmt2.setString(6, extraEntry.getProviderCode());
					stmt2.setTimestamp(9, (Timestamp) extraEntry.getStartDate());
					if (extraEntry.getProjectCode() != null) {
						stmt2.setString(10, extraEntry.getProjectCode());
					}
					else {
						stmt2.setString(10, "");
					}
					stmt2.setString(11, extraEntry.getProductCode());
					stmt2.setString(12, extraEntry.getExtraInformation().toString());
				}
				else {
					stmt2.setString(6, extra.toString());
				}
				stmt2.execute();
			}

			stmt2.close();
			conn.commit();

			log.info("Finished Agresso update successfully");
		}
		catch (Exception e) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			}
			catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally {
			if (conn != null) {

				try {
					conn.setAutoCommit(prevAutoComm);
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					conn.setTransactionIsolation(prevTransactionLevel);
				}
				catch (SQLException e) {
					e.printStackTrace();
				}

				ConnectionBroker.freeConnection(conn);
			}
		}
	}
}