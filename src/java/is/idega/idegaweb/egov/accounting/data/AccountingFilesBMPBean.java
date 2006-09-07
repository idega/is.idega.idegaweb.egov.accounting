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

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

import javax.ejb.FinderException;

import com.idega.block.process.data.CaseCode;
import com.idega.core.file.data.ICFile;
import com.idega.data.GenericEntity;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;


public class AccountingFilesBMPBean extends GenericEntity implements AccountingFiles {

	private static final String ENTITY_NAME = "acc_accounting_files";
	
	private static final String COLUMN_CASE_CODE = "case_code";
	private static final String COLUMN_FILE = "file_id";
	private static final String COLUMN_CREATED_DATE = "created_date";
	private static final String COLUMN_MONTH = "month";
	
	public String getEntityName() {
		return ENTITY_NAME;
	}

	public void initializeAttributes() {
		addAttribute(getIDColumnName());
		
		addManyToOneRelationship(COLUMN_CASE_CODE, CaseCode.class);
		addManyToOneRelationship(COLUMN_FILE, ICFile.class);
		
    addAttribute(COLUMN_CREATED_DATE, "Created date", Timestamp.class);
    addAttribute(COLUMN_MONTH, "Accounting month", Date.class);
	}

	//Getters
	public CaseCode getCaseCode() {
		return (CaseCode) getColumnValue(COLUMN_CASE_CODE);
	}
	
	public ICFile getFile() {
		return (ICFile) getColumnValue(COLUMN_FILE);
	}
	
	public Timestamp getCreatedDate() {
		return getTimestampColumnValue(COLUMN_CREATED_DATE);
	}
	
	public Date getMonth() {
		return getDateColumnValue(COLUMN_MONTH);
	}
	
	//Setters
	public void setCaseCode(CaseCode code) {
		setColumn(COLUMN_CASE_CODE, code);
	}

	public void setFile(ICFile file) {
		setColumn(COLUMN_FILE, file);
	}
	
	public void setCreatedDate(Timestamp createdDate) {
		setColumn(COLUMN_CREATED_DATE, createdDate);
	}

	public void setMonth(Date month) {
		setColumn(COLUMN_MONTH, month);
	}

	//Finders
	public Collection ejbFindAllByCaseCode(CaseCode code) throws FinderException {
		Table table = new Table(this);
		
		SelectQuery query = new SelectQuery(table);
		query.addColumn(table, getIDColumnName());
		query.addCriteria(new MatchCriteria(table, COLUMN_CASE_CODE, MatchCriteria.EQUALS, code));
		query.addOrder(table, COLUMN_MONTH, false);
		
		return super.idoFindPKsByQuery(query);
	}
}