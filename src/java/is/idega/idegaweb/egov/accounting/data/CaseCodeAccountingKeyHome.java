package is.idega.idegaweb.egov.accounting.data;


import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import com.idega.block.process.data.CaseCode;
import javax.ejb.FinderException;

public interface CaseCodeAccountingKeyHome extends IDOHome {

	public CaseCodeAccountingKey create() throws CreateException;

	public CaseCodeAccountingKey findByPrimaryKey(Object pk) throws FinderException;

	public CaseCodeAccountingKey create(CaseCode code) throws CreateException;

	public CaseCodeAccountingKey findByPrimaryKey(CaseCode code) throws FinderException;

	public CaseCodeAccountingKey findByAccountingKey(String accountingKey) throws FinderException;
}