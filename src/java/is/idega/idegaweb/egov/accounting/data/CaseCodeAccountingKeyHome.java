package is.idega.idegaweb.egov.accounting.data;


import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.block.process.data.CaseCode;
import com.idega.data.IDOHome;

public interface CaseCodeAccountingKeyHome extends IDOHome {

	public CaseCodeAccountingKey create() throws CreateException;

	public CaseCodeAccountingKey findByPrimaryKey(Object pk) throws FinderException;

	public CaseCodeAccountingKey create(CaseCode code) throws CreateException;

	public CaseCodeAccountingKey findByPrimaryKey(CaseCode code) throws FinderException;

	public CaseCodeAccountingKey findByAccountingKey(String accountingKey) throws FinderException;
	
	public Collection findAllCaseCodeAccountingKeys() throws FinderException;
}