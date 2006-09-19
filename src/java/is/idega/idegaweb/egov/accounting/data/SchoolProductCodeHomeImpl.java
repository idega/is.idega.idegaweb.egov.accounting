package is.idega.idegaweb.egov.accounting.data;


import com.idega.block.school.data.School;
import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class SchoolProductCodeHomeImpl extends IDOFactory implements SchoolProductCodeHome {

	public Class getEntityInterfaceClass() {
		return SchoolProductCode.class;
	}

	public SchoolProductCode create() throws CreateException {
		return (SchoolProductCode) super.createIDO();
	}

	public SchoolProductCode findByPrimaryKey(Object pk) throws FinderException {
		return (SchoolProductCode) super.findByPrimaryKeyIDO(pk);
	}

	public Collection findAllBySchool(School school) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((SchoolProductCodeBMPBean) entity).ejbFindAllBySchool(school);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public SchoolProductCode findBySchoolAndProductCode(School school, String code) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((SchoolProductCodeBMPBean) entity).ejbFindBySchoolAndProductCode(school, code);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}
}