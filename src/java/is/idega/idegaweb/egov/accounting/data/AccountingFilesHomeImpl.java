package is.idega.idegaweb.egov.accounting.data;


import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.block.process.data.CaseCode;
import javax.ejb.FinderException;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class AccountingFilesHomeImpl extends IDOFactory implements AccountingFilesHome {

	public Class getEntityInterfaceClass() {
		return AccountingFiles.class;
	}

	public AccountingFiles create() throws CreateException {
		return (AccountingFiles) super.createIDO();
	}

	public AccountingFiles findByPrimaryKey(Object pk) throws FinderException {
		return (AccountingFiles) super.findByPrimaryKeyIDO(pk);
	}

	public Collection findAllByCaseCode(CaseCode code) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((AccountingFilesBMPBean) entity).ejbFindAllByCaseCode(code);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}
}