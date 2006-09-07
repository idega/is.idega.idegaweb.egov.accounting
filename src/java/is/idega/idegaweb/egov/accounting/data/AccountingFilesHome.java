package is.idega.idegaweb.egov.accounting.data;


import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import com.idega.block.process.data.CaseCode;
import javax.ejb.FinderException;

public interface AccountingFilesHome extends IDOHome {

	public AccountingFiles create() throws CreateException;

	public AccountingFiles findByPrimaryKey(Object pk) throws FinderException;

	public Collection findAllByCaseCode(CaseCode code) throws FinderException;
}