package is.idega.idegaweb.egov.accounting.data;


import com.idega.block.school.data.School;
import com.idega.data.IDOEntity;

public interface SchoolProductCode extends IDOEntity {

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.SchoolProductCodeBMPBean#getSchool
	 */
	public School getSchool();

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.SchoolProductCodeBMPBean#getSchoolId
	 */
	public int getSchoolId();

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.SchoolProductCodeBMPBean#getProductCode
	 */
	public String getProductCode();

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.SchoolProductCodeBMPBean#getSchoolProductCode
	 */
	public String getSchoolProductCode();

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.SchoolProductCodeBMPBean#setSchool
	 */
	public void setSchool(School school);

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.SchoolProductCodeBMPBean#setSchoolId
	 */
	public void setSchoolId(int id);

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.SchoolProductCodeBMPBean#setProductCode
	 */
	public void setProductCode(String code);

	/**
	 * @see is.idega.idegaweb.egov.accounting.data.SchoolProductCodeBMPBean#setSchoolProductCode
	 */
	public void setSchoolProductCode(String code);
}