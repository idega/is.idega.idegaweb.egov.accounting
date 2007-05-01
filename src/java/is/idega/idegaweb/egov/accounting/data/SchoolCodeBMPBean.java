package is.idega.idegaweb.egov.accounting.data;

import java.util.Collection;

import javax.ejb.FinderException;

import com.idega.block.school.data.School;
import com.idega.block.school.data.SchoolType;
import com.idega.data.GenericEntity;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;

public class SchoolCodeBMPBean extends GenericEntity implements SchoolCode {

	private static final String ENTITY_NAME = "acc_school_code";

	private static final String COLUMN_SCHOOL = "school_id";

	private static final String COLUMN_SCHOOL_TYPE = "school_type_id";

	private static final String COLUMN_SCHOOL_CODE = "school_code";

	public String getEntityName() {
		return ENTITY_NAME;
	}

	public void initializeAttributes() {
		addAttribute(getIDColumnName());

		addManyToOneRelationship(COLUMN_SCHOOL, School.class);
		addManyToOneRelationship(COLUMN_SCHOOL_TYPE, SchoolType.class);
		addAttribute(COLUMN_SCHOOL_CODE, "School code", String.class);
	}

	// Getters
	public School getSchool() {
		return (School) getColumnValue(COLUMN_SCHOOL);
	}

	public int getSchoolId() {
		return getIntColumnValue(COLUMN_SCHOOL);
	}

	public SchoolType getSchoolType() {
		return (SchoolType) getColumnValue(COLUMN_SCHOOL_TYPE);
	}

	public int getSchoolTypeId() {
		return getIntColumnValue(COLUMN_SCHOOL_TYPE);
	}

	public String getSchoolCode() {
		return getStringColumnValue(COLUMN_SCHOOL_CODE);
	}

	// Setters
	public void setSchool(School school) {
		setColumn(COLUMN_SCHOOL, school);
	}

	public void setSchoolId(int id) {
		setColumn(COLUMN_SCHOOL, id);
	}

	public void setSchoolType(SchoolType type) {
		setColumn(COLUMN_SCHOOL_TYPE, type);
	}

	public void setSchoolTypeId(int id) {
		setColumn(COLUMN_SCHOOL_TYPE, id);
	}

	public void setSchoolCode(String code) {
		setColumn(COLUMN_SCHOOL_CODE, code);
	}

	// ejb
	public Collection ejbFindAllBySchool(School school) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table, getIDColumnName());
		query.addCriteria(new MatchCriteria(table, COLUMN_SCHOOL, MatchCriteria.EQUALS, school));

		return idoFindPKsByQuery(query);
	}

	public Object ejbFindBySchoolAndSchoolType(School school, SchoolType type) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table, getIDColumnName());
		query.addCriteria(new MatchCriteria(table, COLUMN_SCHOOL, MatchCriteria.EQUALS, school));
		query.addCriteria(new MatchCriteria(table, COLUMN_SCHOOL_TYPE, MatchCriteria.EQUALS, type));

		return idoFindOnePKByQuery(query);
	}
}