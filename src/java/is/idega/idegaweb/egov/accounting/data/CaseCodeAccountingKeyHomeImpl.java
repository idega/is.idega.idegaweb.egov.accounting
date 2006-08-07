package is.idega.idegaweb.egov.accounting.data;


import javax.ejb.CreateException;
import com.idega.block.process.data.CaseCode;
import javax.ejb.FinderException;
import com.idega.data.IDOCreateException;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class CaseCodeAccountingKeyHomeImpl extends IDOFactory implements CaseCodeAccountingKeyHome {

	public Class getEntityInterfaceClass() {
		return CaseCodeAccountingKey.class;
	}

	public CaseCodeAccountingKey create() throws CreateException {
		return (CaseCodeAccountingKey) super.createIDO();
	}

	public CaseCodeAccountingKey findByPrimaryKey(Object pk) throws FinderException {
		return (CaseCodeAccountingKey) super.findByPrimaryKeyIDO(pk);
	}

	public CaseCodeAccountingKey create(CaseCode code) throws CreateException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((CaseCodeAccountingKeyBMPBean) entity).ejbCreate(code);
		((CaseCodeAccountingKeyBMPBean) entity).ejbPostCreate();
		this.idoCheckInPooledEntity(entity);
		try {
			return findByPrimaryKey(pk);
		}
		catch (FinderException fe) {
			throw new IDOCreateException(fe);
		}
		catch (Exception e) {
			throw new IDOCreateException(e);
		}
	}

	public CaseCodeAccountingKey findByPrimaryKey(CaseCode code) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((CaseCodeAccountingKeyBMPBean) entity).ejbFindByPrimaryKey(code);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}

	public CaseCodeAccountingKey findByAccountingKey(String accountingKey) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((CaseCodeAccountingKeyBMPBean) entity).ejbFindByAccountingKey(accountingKey);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}
}