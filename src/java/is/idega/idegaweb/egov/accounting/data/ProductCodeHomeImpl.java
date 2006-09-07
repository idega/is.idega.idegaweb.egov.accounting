package is.idega.idegaweb.egov.accounting.data;


import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.block.process.data.CaseCode;
import javax.ejb.FinderException;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class ProductCodeHomeImpl extends IDOFactory implements ProductCodeHome {

	public Class getEntityInterfaceClass() {
		return ProductCode.class;
	}

	public ProductCode create() throws CreateException {
		return (ProductCode) super.createIDO();
	}

	public ProductCode findByPrimaryKey(Object pk) throws FinderException {
		return (ProductCode) super.findByPrimaryKeyIDO(pk);
	}

	public Collection findAllByCaseCode(CaseCode code) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ProductCodeBMPBean) entity).ejbFindAllByCaseCode(code);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public ProductCode findByProductKey(String productKey) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((ProductCodeBMPBean) entity).ejbFindByProductKey(productKey);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}
}