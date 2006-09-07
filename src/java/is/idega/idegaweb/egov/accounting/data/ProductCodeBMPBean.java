/*
 * $Id$
 * Created on Sep 6, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.accounting.data;

import java.util.Collection;

import javax.ejb.FinderException;

import com.idega.block.process.data.CaseCode;
import com.idega.data.GenericEntity;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;


public class ProductCodeBMPBean extends GenericEntity implements ProductCode {

	private static final String ENTITY_NAME = "acc_product_key";
	
	private static final String COLUMN_CASE_CODE = "case_code";
	private static final String COLUMN_PRODUCT_KEY = "product_key";
	private static final String COLUMN_ACCOUNTING_KEY = "accounting_key";
	private static final String COLUMN_DESCRIPTION = "description";
	
	public String getEntityName() {
		return ENTITY_NAME;
	}

	public void initializeAttributes() {
		addAttribute(getIDColumnName());
		addAttribute(COLUMN_CASE_CODE, "Case Code",true,true, String.class,7,GenericEntity.ONE_TO_ONE,CaseCode.class);
    addAttribute(COLUMN_PRODUCT_KEY, "Accounting key", String.class);
    addAttribute(COLUMN_ACCOUNTING_KEY, "Accounting key", String.class);
    addAttribute(COLUMN_DESCRIPTION, "Description", String.class);
	}

	//Getters
	public CaseCode getCaseCode() {
		return (CaseCode) getColumnValue(COLUMN_CASE_CODE);
	}
	
	public String getProductKey() {
		return getStringColumnValue(COLUMN_PRODUCT_KEY);
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

	public void setProductKey(String key) {
		setColumn(COLUMN_PRODUCT_KEY, key);
	}
	
	public void setAccountingKey(String key) {
		setColumn(COLUMN_ACCOUNTING_KEY, key);
	}
	
	public void setDescription(String description) {
		setColumn(COLUMN_DESCRIPTION, description);
	}
	
	public Collection ejbFindAllByCaseCode(CaseCode code) throws FinderException {
		Table table = new Table(this);
		
		SelectQuery query = new SelectQuery(table);
		query.addColumn(table, getIDColumnName());
		query.addCriteria(new MatchCriteria(table, COLUMN_CASE_CODE, MatchCriteria.EQUALS, code));
		
		return super.idoFindPKsByQuery(query);
	}

	public Object ejbFindByProductKey(String productKey) throws FinderException {
		Table table = new Table(this);
		
		SelectQuery query = new SelectQuery(table);
		query.addColumn(table, getIDColumnName());
		query.addCriteria(new MatchCriteria(table, COLUMN_PRODUCT_KEY, MatchCriteria.EQUALS, productKey));
		
		return super.idoFindOnePKByQuery(query);
	}
}