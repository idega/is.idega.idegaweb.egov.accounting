package is.idega.idegaweb.egov.accounting.data;


import com.idega.block.school.data.School;
import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import com.idega.block.school.data.SchoolType;
import javax.ejb.FinderException;

public interface SchoolCodeHome extends IDOHome {

	public SchoolCode create() throws CreateException;

	public SchoolCode findByPrimaryKey(Object pk) throws FinderException;

	public Collection findAllBySchool(School school) throws FinderException;

	public SchoolCode findBySchoolAndSchoolType(School school, SchoolType type) throws FinderException;
}