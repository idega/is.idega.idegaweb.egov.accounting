package is.idega.idegaweb.egov.accounting.timer;

import is.idega.idegaweb.egov.accounting.business.AgressoBusiness;

import java.rmi.RemoteException;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.idegaweb.IWMainApplication;
import com.idega.util.timer.TimerEntry;
import com.idega.util.timer.TimerListener;

public class AgressoUpdateTimer implements TimerListener {

	private IWMainApplication iwma;
	
	public AgressoUpdateTimer(IWMainApplication iwma) {
		super();
		this.iwma = iwma;
	}
	
	@Override
	public void handleTimer(TimerEntry entry) {
		try {
			AgressoBusiness business = (AgressoBusiness) IBOLookup
					.getServiceInstance(this.iwma.getIWApplicationContext(),
							AgressoBusiness.class);
			business.executeAfterSchoolCareUpdate();
			business.executeCourseUpdate();
		} catch (IBOLookupException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}