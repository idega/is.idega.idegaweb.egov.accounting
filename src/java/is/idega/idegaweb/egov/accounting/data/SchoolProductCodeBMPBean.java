package is.idega.idegaweb.egov.accounting.data;

import java.util.Collection;

import javax.ejb.FinderException;

import com.idega.block.school.data.School;
import com.idega.data.GenericEntity;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;

public class SchoolProductCodeBMPBean extends GenericEntity implements SchoolProductCode {

	private static final String ENTITY_NAME = "acc_school_product_code";

	private static final String COLUMN_SCHOOL = "school_id";
	
	private static final String COLUMN_PRODUCT_CODE = "product_code";
	
	private static final String COLUMN_SCHOOL_PRODUCT_CODE = "school_product_code";

	public String getEntityName() {
		return ENTITY_NAME;
	}

	public void initializeAttributes() {
		addAttribute(getIDColumnName());
		
		addManyToOneRelationship(COLUMN_SCHOOL, School.class);
		addAttribute(COLUMN_PRODUCT_CODE, "Generic product code", String.class);
		addAttribute(COLUMN_SCHOOL_PRODUCT_CODE, "School product code", String.class);		
	}
	
	// Getters
	public School getSchool() {
		return (School) getColumnValue(COLUMN_SCHOOL);
	}
	
	public int getSchoolId() {
		return getIntColumnValue(COLUMN_SCHOOL);
	}
	
	public String getProductCode() {
		return getStringColumnValue(COLUMN_PRODUCT_CODE);
	}
	
	public String getSchoolProductCode() {
		return getStringColumnValue(COLUMN_SCHOOL_PRODUCT_CODE);
	}
	
	// Setters
	public void setSchool(School school) {
		setColumn(COLUMN_SCHOOL, school);
	}
	
	public void setSchoolId(int id) {
		setColumn(COLUMN_SCHOOL, id);
	}
	
	public void setProductCode(String code) {
		setColumn(COLUMN_PRODUCT_CODE, code);
	}
	
	public void setSchoolProductCode(String code) {
		setColumn(COLUMN_SCHOOL_PRODUCT_CODE, code);
	}
	
	// ejb
	public Collection ejbFindAllBySchool(School school) throws FinderException {
		Table table = new Table(this);
		
		SelectQuery query = new SelectQuery(table);
		query.addColumn(table, getIDColumnName());
		query.addCriteria(new MatchCriteria(table, COLUMN_SCHOOL, MatchCriteria.EQUALS, school));
		
		return idoFindPKsByQuery(query);
	}

	public Object ejbFindBySchoolAndProductCode(School school, String code) throws FinderException {
		Table table = new Table(this);
		
		SelectQuery query = new SelectQuery(table);
		query.addColumn(table, getIDColumnName());
		query.addCriteria(new MatchCriteria(table, COLUMN_SCHOOL, MatchCriteria.EQUALS, school));
		query.addCriteria(new MatchCriteria(table, COLUMN_PRODUCT_CODE, MatchCriteria.EQUALS, code));
		
		return idoFindOnePKByQuery(query);
	}
}