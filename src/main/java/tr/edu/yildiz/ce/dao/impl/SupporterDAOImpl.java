package tr.edu.yildiz.ce.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;


import tr.edu.yildiz.ce.dao.SupporterDAO;
import tr.edu.yildiz.ce.model.SupportTypeInfo;
import tr.edu.yildiz.ce.entity.SupportType;
import tr.edu.yildiz.ce.entity.Supporter;
import tr.edu.yildiz.ce.model.SupporterInfo;






public class SupporterDAOImpl implements SupporterDAO {
	
	@Autowired
	private LocationDAOImpl locationDAOImpl;
	
	@Autowired
	private SupportTypeDAOImpl supportTypeDAOImpl;
	
	@Autowired
	private UserDAOImpl userInfoDAOImpl;
	
    @Autowired
    private SessionFactory sessionFactory;
    
	@Override
	public Supporter findSupporter(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Supporter.class);
        crit.add(Restrictions.eq("id", id));
        return (Supporter) crit.uniqueResult();
	}

	@Override
	public void saveSupporter(SupporterInfo supporterInfo) {
       	Integer id = supporterInfo.getId();
       	Supporter supporter = null;
        if (id != null) {
        	supporter = this.findSupporter(id);
        }
        boolean isNew = false;
        if (supporter == null) {
            isNew = true;
            supporter = new Supporter();
        }
        supporter.setId(supporterInfo.getId());
        supporter.setUserId(supporterInfo.getUserInfo().getId());
        supporter.setSupportTypeId(supporterInfo.getSupportTypeInfo().getId());
        supporter.setLocationId(supporterInfo.getLocationInfo().getId());
 
        if (isNew) {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(supporter);
        }
	}

	@Override
	public SupporterInfo findSupporterInfo(Integer id) {
		Supporter supporter = this.findSupporter(id);
        if (supporter == null) {
            return null;
        }
        return new SupporterInfo(supporter.getId(),userInfoDAOImpl.findUserInfo(supporter.getUserId()),
        		supportTypeDAOImpl.findSupportTypeInfo(supporter.getSupportTypeId()),
        		locationDAOImpl.findLocationInfo(supporter.getLocationId()));

	}

	@Override
	public void deleteSupporter(Integer id) {
		Supporter supporter = this.findSupporter(id);
        if (supporter != null) {
            this.sessionFactory.getCurrentSession().delete(supporter);
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SupportTypeInfo> getSupportTypes(Integer userId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(SupportType.class);
        crit.add(Restrictions.eq("userId", userId));
        List<SupportType> supportTypes=(List<SupportType>) crit.list();
        List<SupportTypeInfo> supportTypeInfos= new ArrayList<SupportTypeInfo>();
        for(SupportType s:supportTypes){
        	supportTypeInfos.add(supportTypeDAOImpl.findSupportTypeInfo(s.getId()));
        }
        return supportTypeInfos;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SupporterInfo> listSupporterInfos() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Supporter.class);
        List<Supporter> supporters=(List<Supporter>) crit.list();
        List<SupporterInfo> supporterInfos= new ArrayList<SupporterInfo>();
        for(Supporter s: supporters){
        	supporterInfos.add((SupporterInfo)findSupporterInfo(s.getId()));
        }
        return supporterInfos;
	}
	

}
