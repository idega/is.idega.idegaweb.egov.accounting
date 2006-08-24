/**
 * 
 */
package is.idega.idegaweb.egov.accounting;

import is.idega.idegaweb.egov.accounting.business.AccountingBusiness;
import is.idega.idegaweb.egov.accounting.business.AccountingBusinessManager;
import is.idega.idegaweb.egov.accounting.business.AccountingEntry;
import is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKey;
import is.idega.idegaweb.egov.accounting.data.CaseCodeAccountingKeyHome;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Logger;
import com.idega.block.process.business.CaseBusiness;
import com.idega.block.process.data.CaseCode;
import com.idega.business.IBOLookup;
import com.idega.data.IDOLookup;
import com.idega.idegaweb.IWMainApplication;
import com.idega.util.IWTimestamp;
import com.idega.util.database.ConnectionBroker;


/**
 * <p>
 * Daemon thread which handles the update of the table RRVK_AGRESSO for 
 * integration of date from the AfterschoolCare module with the Reykjavik Accounting system.
 * </p>
 *  Last modified: $Date: 2006/08/24 12:33:42 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.2 $
 */
public class RvkAgressoUpdater implements Runnable {

	private boolean active=true;
	private long nextRun=-1;
	private IWMainApplication iwma;
	private Thread thread;
	static Logger log = Logger.getLogger(RvkAgressoUpdater.class.getName());

	
	/**
	 * 
	 */
	public RvkAgressoUpdater(IWMainApplication iwma) {
		this.iwma=iwma;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		log.info("Initializing Agresso update manager");
		while (this.active) {
			try {
				//Hack to make the necessary bundles load before we run:
				Thread.sleep(1000*60);
				runBatch();
				//sleep for 30 minutes:
				Thread.sleep(30*60*1000);
			}
			catch (InterruptedException e) {
				log.info("Caught InterrupptedException. Shutting down);");
				active=false;
			}
		}
	}

	/**
	 * <p>
	 * TODO tryggvil describe method checkRunBatch
	 * </p>
	 */
	public void runBatch() {
		if (this.nextRun < System.currentTimeMillis()) {
			executeUpdate();
			// incerment the nextRun to run next after 24 hours:
			IWTimestamp iwts = new IWTimestamp();
			iwts.addDays(1);
			iwts.setHour(4);

			//iwts.addHours(1);
			
			Timestamp ts = iwts.getTimestamp();
			this.nextRun = ts.getTime();//System.currentTimeMillis() + 1000 * 60 * 60 * 24;
		}
	}
	
	/**
	 * <p>
	 * TODO tryggvil describe method executeUpdate
	 * </p>
	 */
	private void executeUpdate() {

		log.info("Starting Agresso update");
		
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
		try{
			String tableName = "RRVK_AGRESSO";
			
			conn.setAutoCommit(false);
			 
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			

			Statement stmt1 = conn.createStatement();
			stmt1.executeUpdate("delete from "+tableName);
			stmt1.close();
			
			//CaseCode code = AfterSchoolCareConstants.CASE_CODE;
			CaseBusiness cb = (CaseBusiness) IBOLookup.getServiceInstance(iwma.getIWApplicationContext(), CaseBusiness.class);
			//CaseCode afterSchCare = cb.getCaseCode("MBFRITV");
			
			CaseCodeAccountingKeyHome ccah = (CaseCodeAccountingKeyHome) IDOLookup.getHome(CaseCodeAccountingKey.class);
			CaseCodeAccountingKey accKey = ccah.findByAccountingKey("ITR");
			CaseCode afterSchCare = accKey.getCaseCode();
			
			AccountingBusiness business = AccountingBusinessManager.getInstance().getAccountingBusinessOrDefault(afterSchCare, iwma.getIWApplicationContext());
			
			IWTimestamp fromDateTS = new IWTimestamp();
			fromDateTS.addDays(-62);
			Date fromDate = fromDateTS.getDate();
			String productCode = null;
			IWTimestamp toDateTS = new IWTimestamp();
			toDateTS.addDays(62);
			Date toDate = toDateTS.getDate();
			AccountingEntry[] entries = business.getAccountingEntries(productCode, null, fromDate, toDate);
			
			PreparedStatement stmt2 = conn.prepareCall("insert into "+tableName+"(PAYER_PERSONAL_ID,PERSONAL_ID,PRODUCT_CODE,PROVIDER_CODE,PAYMENT_TYPE,CARD_NUMBER,CARD_EXPIRATION_MONTH,CARD_EXPIRATION_YEAR,START_DATE,END_DATE) values(?,?,?,?,?,?,?,?,?,?)");
			
			for (int i = 0; i < entries.length; i++) {

				entry = entries[i];
				Date startDate=null;
				if(entry.getStartDate()!=null){
					startDate=new IWTimestamp(entry.getStartDate()).getDate();
				}
				Date endDate=null;
				if(entry.getEndDate()!=null){
					endDate= new IWTimestamp(entry.getEndDate()).getDate();
				}
				stmt2.setString(1, entry.getPayerPersonalId());
				stmt2.setString(2, entry.getPersonalId());
				stmt2.setString(3, entry.getProductCode());
				stmt2.setString(4,entry.getProviderCode());
				stmt2.setString(5, entry.getPaymentMethod());
				stmt2.setString(6, entry.getCardNumber());
				stmt2.setString(7, Integer.toString(entry.getCardExpirationMonth()));
				stmt2.setString(8, Integer.toString(entry.getCardExpirationYear()));
				stmt2.setDate(9, startDate);
				stmt2.setDate(10,endDate);
				stmt2.execute();
			}
			
			stmt2.close();
			
			conn.commit();
			
			log.info("Finished Agresso update successfully");
			
		}
		catch(Exception e){
			try {
				if(conn!=null){
					conn.rollback();
				}
			}
			catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally{
			if(conn!=null){

				try {
					conn.setAutoCommit(prevAutoComm);
				}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					conn.setTransactionIsolation(prevTransactionLevel);
				}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				ConnectionBroker.freeConnection(conn);
			}
		}
	}

	public void stop() {
		this.active = false;
		getThread().interrupt();
	}

	
	/**
	 * @return the thread
	 */
	public Thread getThread() {
		return thread;
	}

	
	/**
	 * @param thread the thread to set
	 */
	public void setThread(Thread thread) {
		this.thread = thread;
	}
}
