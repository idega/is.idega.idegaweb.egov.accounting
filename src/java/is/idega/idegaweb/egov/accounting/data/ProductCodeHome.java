package is.idega.idegaweb.egov.accounting.data;


import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import com.idega.block.process.data.CaseCode;
import javax.ejb.FinderException;

public interface ProductCodeHome extends IDOHome {

	public ProductCode create() throws CreateException;

	public ProductCode findByPrimaryKey(Object pk) throws FinderException;

	public Collection findAllByCaseCode(CaseCode code) throws FinderException;

	public ProductCode findByProductKey(String productKey) throws FinderException;
}