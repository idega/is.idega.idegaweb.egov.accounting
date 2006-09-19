package is.idega.idegaweb.egov.accounting.data;


import com.idega.block.school.data.School;
import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import javax.ejb.FinderException;

public interface SchoolProductCodeHome extends IDOHome {

	public SchoolProductCode create() throws CreateException;

	public SchoolProductCode findByPrimaryKey(Object pk) throws FinderException;

	public Collection findAllBySchool(School school) throws FinderException;

	public SchoolProductCode findBySchoolAndProductCode(School school, String code) throws FinderException;
}