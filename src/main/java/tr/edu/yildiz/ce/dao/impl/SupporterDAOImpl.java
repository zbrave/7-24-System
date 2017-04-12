package tr.edu.yildiz.ce.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import tr.edu.yildiz.ce.dao.SupportTypeDAO;
import tr.edu.yildiz.ce.dao.SupporterDAO;

import tr.edu.yildiz.ce.entity.Supporter;
import tr.edu.yildiz.ce.model.SupporterInfo;

public class SupporterDAOImpl implements SupporterDAO {
	@Autowired
	private LocationDAOImpl locationDAOImpl;
	@Autowired
	private SupportTypeDAO supportTypeDAO;
	@Autowired
	private UserInfoDAOImpl userInfoDAOImpl;
    @Autowired
    private SessionFactory sessionFactory;
	@Override
	public Supporter findSupporter(int id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Supporter.class);
        crit.add(Restrictions.eq("id", id));
        return (Supporter) crit.uniqueResult();
	}

	@Override
	public void saveSupporter(SupporterInfo supporterInfo) {
	       	Integer id = supporterInfo.getUserInfo().getStdId();
	       	Supporter supporter = null;
	        if (id != null) {
	        	supporter = this.findSupporter(id);
	        }
	        boolean isNew = false;
	        if (supporter == null) {
	            isNew = true;
	            supporter = new Supporter();
	        }
	        supporter.setUserInfoId(supporterInfo.getUserInfo().getStdId());
	        supporter.setSupportTypeInfoId(supporterInfo.getSupportTypeInfo().getId());
	        supporter.setLocationInfoId(supporterInfo.getLocationInfo().getId());
	 
	        if (isNew) {
	            Session session = this.sessionFactory.getCurrentSession();
	            session.persist(supporter);
	        }

	}

	@Override
	public SupporterInfo findSupporterInfo(int id) {
		Supporter supporter = this.findSupporter(id);
        if (supporter == null) {
            return null;
        }
        return new SupporterInfo(userInfoDAOImpl.findUserInfoById(supporter.getUserInfoId()),
        		supportTypeDAO.findSupportTypeInfo(supporter.getSupportTypeInfoId()),
        		locationDAOImpl.findLocationInfo(supporter.getLocationInfoId()));

	}

	@Override
	public void deleteSupporter(int id) {
		Supporter supporter = this.findSupporter(id);
        if (supporter != null) {
            this.sessionFactory.getCurrentSession().delete(supporter);
        }

	}

}
