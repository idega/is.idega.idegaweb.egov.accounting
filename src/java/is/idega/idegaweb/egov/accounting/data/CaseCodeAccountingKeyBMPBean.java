/*
 * $Id$
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.accounting.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.idega.block.process.data.CaseCode;
import com.idega.block.process.data.CaseCodeHome;
import com.idega.data.GenericEntity;
import com.idega.data.IDOLookupException;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;


public class CaseCodeAccountingKeyBMPBean extends GenericEntity implements CaseCodeAccountingKey {

	private static final String ENTITY_NAME = "acc_accounting_key";
	
	private static final String COLUMN_CASE_CODE = "case_code";
	private static final String COLUMN_ACCOUNTING_KEY = "accounting_key";
	private static final String COLUMN_DESCRIPTION = "description";
	
  public Class getPrimaryKeyClass(){
    return String.class;
  }

  public String getIDColumnName(){
    return COLUMN_CASE_CODE;
  }

	public String getEntityName() {
		return ENTITY_NAME;
	}

	public void initializeAttributes() {
		addAttribute(COLUMN_CASE_CODE, "Case Code",true,true, String.class,7,GenericEntity.ONE_TO_ONE,CaseCode.class);
	    setAsPrimaryKey(COLUMN_CASE_CODE, true);
	    //addOneToOneRelationship(COLUMN_CASE_CODE, CaseCode.class);
	    addAttribute(COLUMN_ACCOUNTING_KEY, "Accounting key", String.class);
	    addAttribute(COLUMN_DESCRIPTION, "Description", String.class);
	}
	
	public void insertStartData() {
		try {
			CaseCodeHome home = (CaseCodeHome) getIDOHome(CaseCode.class);
			Collection codes = home.findAllCaseCodes();
			
			CaseCodeAccountingKeyHome aHome = (CaseCodeAccountingKeyHome) getIDOHome(CaseCodeAccountingKey.class);
			Iterator iter = codes.iterator();
			while (iter.hasNext()) {
				CaseCode code = (CaseCode) iter.next();
				
				try {
					CaseCodeAccountingKey key = aHome.create(code);
					key.store();
				}
				catch (CreateException e) {
					e.printStackTrace();
				}
			}
		}
		catch (IDOLookupException e) {
			e.printStackTrace();
		}
		catch (FinderException e) {
			e.printStackTrace();
		}
	}
	
	//Getters
	public CaseCode getCaseCode() {
		return (CaseCode) getColumnValue(COLUMN_CASE_CODE);
	}
	
	public String getAccountingKey() {
		return getStringColumnValue(COLUMN_ACCOUNTING_KEY);
	}
	
	public String getDescription() {
		return getStringColumnValue(COLUMN_DESCRIPTION);
	}
	
	//Setters
	public void setCaseCode(CaseCode code) {
		setColumn(COLUMN_CASE_CODE, code);
	}

	public void setAccountingKey(String key) {
		setColumn(COLUMN_ACCOUNTING_KEY, key);
	}
	
	public void setDescription(String description) {
		setColumn(COLUMN_DESCRIPTION, description);
	}
	
	public Object ejbCreate(CaseCode code) throws CreateException {
		setPrimaryKey(code.getCode());
		return super.ejbCreate();
	}

	public Object ejbFindByPrimaryKey(CaseCode code) throws FinderException {
		return super.ejbFindByPrimaryKey(code.getCode());
	}
	
	public Object ejbFindByAccountingKey(String accountingKey) throws FinderException {
		Table table = new Table(this);
		
		SelectQuery query = new SelectQuery(table);
		query.addColumn(table, getIDColumnName());
		query.addCriteria(new MatchCriteria(table, COLUMN_ACCOUNTING_KEY, MatchCriteria.EQUALS, accountingKey));
		
		return super.idoFindOnePKByQuery(query);
	}
	
	public Collection ejbFindAllCaseCodeAccountingKeys() throws FinderException {
		return super.idoFindAllIDsBySQL();
	}
}