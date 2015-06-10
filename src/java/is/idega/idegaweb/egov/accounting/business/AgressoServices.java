package is.idega.idegaweb.egov.accounting.business;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.directwebremoting.annotations.Param;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.core.business.DefaultSpringBean;
import com.idega.dwr.business.DWRAnnotationPersistance;
import com.idega.presentation.IWContext;
import com.idega.util.CoreUtil;
import com.idega.util.IWTimestamp;

@Service(AgressoServices.BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
@RemoteProxy(creator=SpringCreator.class, creatorParams={
	@Param(name="beanName", value=AgressoServices.BEAN_NAME),
	@Param(name="javascript", value=AgressoServices.DWR_OBJECT)
}, name=AgressoServices.DWR_OBJECT)
public class AgressoServices extends DefaultSpringBean implements DWRAnnotationPersistance {

	static final String BEAN_NAME = "accountingAgressoServices",
						DWR_OBJECT = "AccountingAgressoServices";

	@RemoteMethod
	public boolean doUpdateAgressoEntries(String from, String to) {
		IWContext iwc = CoreUtil.getIWContext();
		if (!iwc.isSuperAdmin()) {
			return false;
		}

		AgressoBusinessBean abb = getServiceInstance(AgressoBusinessBean.class);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date fromDate = null, toDate = null;
			if (from == null) {
				IWTimestamp now = new IWTimestamp();
				now.addDays(-62);
				fromDate = now.getDate();
			} else {
				fromDate = new Date(sdf.parse(from).getTime());
			}
			if (to == null) {
				IWTimestamp now = new IWTimestamp();
				now.addDays(62);
				toDate = now.getDate();
			} else {
				toDate = new Date(sdf.parse(to).getTime());
			}

			getLogger().info("From: " + fromDate + ", to: " + toDate);
			abb.executeAfterSchoolCareUpdate(fromDate, toDate);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@RemoteMethod
	public boolean doUpdateAgressoEntriesForCourses(String from, String to) {
		IWContext iwc = CoreUtil.getIWContext();
		if (!iwc.isSuperAdmin()) {
			return false;
		}

		AgressoBusinessBean abb = getServiceInstance(AgressoBusinessBean.class);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date fromDate = null, toDate = null;
			if (from == null) {
				IWTimestamp now = new IWTimestamp();
				now.setDay(1);
				now.setMonth(1);
				fromDate = now.getDate();
			} else {
				fromDate = new Date(sdf.parse(from).getTime());
			}
			if (to == null) {
				IWTimestamp now = new IWTimestamp();
				now.addMonths(1);
				toDate = now.getDate();
			} else {
				toDate = new Date(sdf.parse(to).getTime());
			}

			getLogger().info("From: " + fromDate + ", to: " + toDate);
			abb.executeCourseUpdate(fromDate, toDate);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}