/**
 * 
 */
package is.idega.idegaweb.egov.accounting;

import is.idega.idegaweb.egov.accounting.business.AgressoBusiness;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.logging.Logger;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.idegaweb.IWMainApplication;
import com.idega.util.IWTimestamp;


/**
 * <p>
 * Daemon thread which handles the update of the table RRVK_AGRESSO for 
 * integration of date from the AfterschoolCare module with the Reykjavik Accounting system.
 * </p>
 *  Last modified: $Date: 2006/12/18 13:59:57 $ by $Author: laddi $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.4 $
 */
public class RvkAgressoUpdater implements Runnable {

	private boolean active=true;
	private long nextRun=-1;
	private IWMainApplication iwma;
	private Thread thread;
	static Logger log = Logger.getLogger(RvkAgressoUpdater.class.getName());

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
				this.active=false;
			}
		}
	}

	public void runBatch() {
		if (this.nextRun < System.currentTimeMillis()) {
			executeUpdate();
			// incerment the nextRun to run next after 24 hours:
			IWTimestamp iwts = new IWTimestamp();
			iwts.addDays(1);
			iwts.setHour(4);

			Timestamp ts = iwts.getTimestamp();
			this.nextRun = ts.getTime();
		}
	}
	
	private void executeUpdate() {
		try {
			AgressoBusiness business = (AgressoBusiness) IBOLookup.getServiceInstance(this.iwma.getIWApplicationContext(), AgressoBusiness.class);
			business.executeUpdate();
		}
		catch (IBOLookupException e) {
			e.printStackTrace();
		}
		catch (RemoteException e) {
			e.printStackTrace();
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
		return this.thread;
	}

	/**
	 * @param thread the thread to set
	 */
	public void setThread(Thread thread) {
		this.thread = thread;
	}
}