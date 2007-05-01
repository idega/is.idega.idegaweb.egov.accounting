package is.idega.idegaweb.egov.accounting.data;


import com.idega.block.school.data.School;
import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.block.school.data.SchoolType;
import javax.ejb.FinderException;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class SchoolCodeHomeImpl extends IDOFactory implements SchoolCodeHome {

	public Class getEntityInterfaceClass() {
		return SchoolCode.class;
	}

	public SchoolCode create() throws CreateException {
		return (SchoolCode) super.createIDO();
	}

	public SchoolCode findByPrimaryKey(Object pk) throws FinderException {
		return (SchoolCode) super.findByPrimaryKeyIDO(pk);
	}

	public Collection findAllBySchool(School school) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((SchoolCodeBMPBean) entity).ejbFindAllBySchool(school);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public SchoolCode findBySchoolAndSchoolType(School school, SchoolType type) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((SchoolCodeBMPBean) entity).ejbFindBySchoolAndSchoolType(school, type);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}
}