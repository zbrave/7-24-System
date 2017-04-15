package tr.edu.yildiz.ce.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import tr.edu.yildiz.ce.dao.SupportTypeDAO;
import tr.edu.yildiz.ce.entity.SupportType;
import tr.edu.yildiz.ce.model.SupportTypeInfo;

public class SupportTypeDAOImpl implements SupportTypeDAO {

	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public SupportType findSupportType(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(SupportType.class);
        crit.add(Restrictions.eq("id", id));
        return (SupportType) crit.uniqueResult();
	}

	@Override
	public void saveSupportType(SupportTypeInfo supportTypeInfo) {
        Integer id = supportTypeInfo.getId();
        SupportType supportType = null;
        if (id != null) {
        	supportType = this.findSupportType(id);
        }
        boolean isNew = false;
        if (supportType == null) {
            isNew = true;
            supportType = new SupportType();
        }
        supportType.setId(supportTypeInfo.getId());
        supportType.setType(supportTypeInfo.getType());
 
        if (isNew) {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(supportType);
        }

	}

	@Override
	public SupportTypeInfo findSupportTypeInfo(Integer id) {
		SupportType supportType = this.findSupportType(id);
        if (supportType == null) {
            return null;
        }
        return new SupportTypeInfo(supportType.getId(), supportType.getType() );
	}

	@Override
	public void deleteSupportType(Integer id) {
		SupportType supportType = this.findSupportType(id);
        if (supportType != null) {
            this.sessionFactory.getCurrentSession().delete(supportType);
        }

	}

}
