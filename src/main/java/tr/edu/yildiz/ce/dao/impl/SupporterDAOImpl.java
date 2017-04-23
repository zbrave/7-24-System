package tr.edu.yildiz.ce.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import tr.edu.yildiz.ce.dao.LocationDAO;
import tr.edu.yildiz.ce.dao.SupportTypeDAO;
import tr.edu.yildiz.ce.dao.SupporterDAO;
import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.entity.Supporter;
import tr.edu.yildiz.ce.model.SupporterInfo;






public class SupporterDAOImpl implements SupporterDAO {
	
	@Autowired
	private LocationDAO locationDAO;
	
	@Autowired
	private SupportTypeDAO supportTypeDAO;
	
	@Autowired
	private UserDAO userDAO;
	
    @Autowired
    private SessionFactory sessionFactory;
    
	
	public Supporter findSupporter(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Supporter.class);
        crit.add(Restrictions.eq("id", id));
        return (Supporter) crit.uniqueResult();
	}

	
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
        if(supporterInfo.getUserInfo()!=null){
        	supporter.setUserId(supporterInfo.getUserInfo().getId());
        }else{
        	supporter.setUserId(supporterInfo.getUserId());
        }
        if(supporterInfo.getSupportTypeInfo()!=null){
        	supporter.setSupportTypeId(supporterInfo.getSupportTypeInfo().getId());
        }else{
        	supporter.setSupportTypeId(supporterInfo.getSupportTypeId());
        }
        
        if(supporterInfo.getLocationInfo()!=null){
        	supporter.setLocationId(supporterInfo.getLocationInfo().getId());
        }else{
        	supporter.setLocationId(supporterInfo.getLocationId());
        }
 
        if (isNew) {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(supporter);
        }
	}

	
	public SupporterInfo findSupporterInfo(Integer id) {
		Supporter supporter = this.findSupporter(id);
        if (supporter == null) {
            return null;
        }
        return new SupporterInfo(supporter.getId(),userDAO.findUserInfo(supporter.getUserId()),
        		supportTypeDAO.findSupportTypeInfo(supporter.getSupportTypeId()),
        		locationDAO.findLocationInfo(supporter.getLocationId()));

	}

	
	public void deleteSupporter(Integer id) {
		Supporter supporter = this.findSupporter(id);
        if (supporter != null) {
            this.sessionFactory.getCurrentSession().delete(supporter);
        }
	}

	@SuppressWarnings("unchecked")
	
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

	@SuppressWarnings("unchecked")
	
	public List<SupporterInfo> listSupporterInfosById(Integer userId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Supporter.class);
        crit.add(Restrictions.eq("userId", userId));
        List<Supporter> supporters=(List<Supporter>) crit.list();
        List<SupporterInfo> supporterInfos= new ArrayList<SupporterInfo>();
        for(Supporter s: supporters){
        	supporterInfos.add((SupporterInfo)findSupporterInfo(s.getId()));
        }
        return supporterInfos;
	}
	

}
