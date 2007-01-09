/**
 * 
 */
package is.idega.idegaweb.egov;

import is.idega.idegaweb.egov.accounting.presentation.UpdateAgresso;

import java.util.ArrayList;
import java.util.Collection;

import com.idega.core.view.DefaultViewNode;
import com.idega.core.view.ViewManager;
import com.idega.core.view.ViewNode;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWMainApplication;
import com.idega.workspace.view.WorkspaceApplicationNode;
import com.idega.workspace.view.WorkspaceClassViewNode;


/**
 * <p>
 * TODO tryggvil Describe Type SchoolViewManager
 * </p>
 *  Last modified: $Date$ by $Author$
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision$
 */
public class AccountingViewManager {

	private ViewNode accountingViewNode;
	private IWMainApplication iwma;
	/**
	 * <p>
	 * TODO tryggvil describe method getInstance
	 * </p>
	 * @param iwma
	 * @return
	 */
	public static AccountingViewManager getInstance(IWMainApplication iwma) {
		AccountingViewManager instance = (AccountingViewManager) iwma.getAttribute("accountingviewmanager");
		if(instance==null){
			instance = new AccountingViewManager();
			instance.iwma=iwma;
			iwma.setAttribute("accountingviewmanager",instance);
		}
		return instance;
	}
	
	public ViewManager getViewManager(){
		return ViewManager.getInstance(this.iwma);
	}
	
	
	public ViewNode getAccountingViewNode(){
		IWBundle iwb = this.iwma.getBundle("is.idega.idegaweb.egov.accounting");
		if(this.accountingViewNode==null){
			this.accountingViewNode = initalizeAccountingNode(iwb);
		}
		return this.accountingViewNode;
	}

	/**
	 * <p>
	 * TODO tryggvil describe method initalizeSchoolNode
	 * </p>
	 * @param iwb
	 * @return
	 */
	private ViewNode initalizeAccountingNode(IWBundle iwb) {
		ViewManager viewManager = ViewManager.getInstance(this.iwma);
		ViewNode workspace = viewManager.getWorkspaceRoot();
		
		Collection roles = new ArrayList();
		roles.add("accounting_admin");
		
		DefaultViewNode accountingNode = new WorkspaceApplicationNode("accounting",workspace,roles);
		accountingNode.setName("#{localizedStrings['is.idega.idegaweb.egov.accounting']['accounting']}");

		DefaultViewNode agressoNode = new WorkspaceClassViewNode("agresso",accountingNode);
		agressoNode.setName("#{localizedStrings['is.idega.idegaweb.egov.accounting']['agresso']}");
		
		WorkspaceClassViewNode seasons = new WorkspaceClassViewNode("updateagresso",agressoNode);
		seasons.setName("#{localizedStrings['is.idega.idegaweb.egov.accounting']['manual_update_agresso']}");
		seasons.setComponentClass(UpdateAgresso.class);
		
		return accountingNode;
	}
	
}
