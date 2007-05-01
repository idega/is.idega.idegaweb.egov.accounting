package is.idega.idegaweb.egov.accounting.data;


import com.idega.block.school.data.School;
import com.idega.block.school.data.SchoolType;
import com.idega.data.IDOEntity;

public interface SchoolCode extends IDOEntity {

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.SchoolCodeBMPBean#getSchool
	 */
	public School getSchool();

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.SchoolCodeBMPBean#getSchoolId
	 */
	public int getSchoolId();

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.SchoolCodeBMPBean#getSchoolType
	 */
	public SchoolType getSchoolType();

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.SchoolCodeBMPBean#getSchoolTypeId
	 */
	public int getSchoolTypeId();

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.SchoolCodeBMPBean#getSchoolCode
	 */
	public String getSchoolCode();

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.SchoolCodeBMPBean#setSchool
	 */
	public void setSchool(School school);

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.SchoolCodeBMPBean#setSchoolId
	 */
	public void setSchoolId(int id);

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.SchoolCodeBMPBean#setSchoolType
	 */
	public void setSchoolType(SchoolType type);

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.SchoolCodeBMPBean#setSchoolTypeId
	 */
	public void setSchoolTypeId(int id);

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.SchoolCodeBMPBean#setSchoolCode
	 */
	public void setSchoolCode(String code);
}