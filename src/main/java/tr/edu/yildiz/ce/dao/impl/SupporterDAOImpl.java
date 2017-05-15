package tr.edu.yildiz.ce.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import tr.edu.yildiz.ce.dao.ComplaintDAO;
import tr.edu.yildiz.ce.dao.SupporterDAO;
import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.entity.Complaint;
import tr.edu.yildiz.ce.entity.Supporter;
import tr.edu.yildiz.ce.model.ComplaintInfo;
import tr.edu.yildiz.ce.model.SupporterInfo;
import tr.edu.yildiz.ce.model.UserInfo;






public class SupporterDAOImpl implements SupporterDAO {
	
	
    @Autowired
    private SessionFactory sessionFactory;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private ComplaintDAO complaintDAO;
	
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
        supporter.setUserId(supporterInfo.getUserId());
        supporter.setSupportTypeId(supporterInfo.getSupportTypeId());
        supporter.setLocationId(supporterInfo.getLocationId()); 
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
        return new SupporterInfo(supporter.getId(),supporter.getUserId(),supporter.getSupportTypeId(),supporter.getLocationId());
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
	@Override
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

	@SuppressWarnings("unchecked")
	@Override
	public List<SupporterInfo> listSupporterInfosBySupportType(Integer supportTypeId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Supporter.class);
        crit.add(Restrictions.eq("supportTypeId", supportTypeId));
        List<Supporter> supporters=(List<Supporter>) crit.list();
        List<SupporterInfo> supporterInfos= new ArrayList<SupporterInfo>();
        
        for(Supporter s: supporters){
        	supporterInfos.add(this.findSupporterInfo(s.getId()));
        }
        return supporterInfos;
	}

	@Override
	public List<UserInfo> listSupporterUserInfos() {
		List<SupporterInfo> supporterInfos = listSupporterInfos();
		List<UserInfo> userInfos=new ArrayList<UserInfo>();
		List<Integer> userIds =new ArrayList<Integer>();
		for(SupporterInfo s:supporterInfos){
			if(!userIds.contains(s.getUserId())){
				userIds.add(s.getUserId());
				userInfos.add(userDAO.findUserInfo(s.getUserId()));
				
			}
		}
		return userInfos;
	}

	@Override
	public List<SupporterInfo> reportSupporterInfos() {
		List<SupporterInfo> supporterInfos = listSupporterInfos();
		for(SupporterInfo s:supporterInfos){
			Session session = sessionFactory.getCurrentSession();
	        Criteria crit = session.createCriteria(Complaint.class);
	        crit.add(Restrictions.eq("supportUserId", s.getUserId()));
	        crit.add(Restrictions.eq("locationId", s.getLocationId()));
	        crit.add(Restrictions.eq("supportTypeId", s.getSupportTypeId()));
	        
	        long totalAwarenessTime=0;
			long numAwarenessTime=0;
			long totalResponseTime=0;
			long numResponseTime=0;
			Integer waitingAck=0;
			Integer active=0;
			Integer waitingChild=0;
			Integer total=0;
			Integer reported=0;
			List<Complaint> complaints = (List<Complaint>)crit.list();
			List<ComplaintInfo> complaintInfos =new ArrayList<ComplaintInfo>();
			
	        for(Complaint c:complaints){
	        	complaintInfos.add(complaintDAO.findComplaintInfo(c.getId()));
	        }
			total=complaintInfos.size();
			for(ComplaintInfo c:complaintInfos){
				if(c.getComplaintTime()!=null&&c.getAckTime()!=null){
					totalAwarenessTime+=c.getAckTime().getTime()-c.getComplaintTime().getTime();
					numAwarenessTime++;
				}
				if(c.getAckTime()!=null&&c.getResponseTime()!=null){
					totalResponseTime+=c.getResponseTime().getTime()-c.getAckTime().getTime();
					numResponseTime++;
				}
				if(c.isAck()==false&&c.getSupportUserId()!=null){
					waitingAck++;
				}
				if(c.isAck()==true&&c.isEnded()==false&&c.getChildId()==null&&c.isReported()==false){
					active++;
				}
				if(c.isAck()==true&&c.isEnded()==false&&c.getChildId()!=null&&c.isReported()==false){
					waitingChild++;
				}
				if(c.isReported()==true){
					reported++;
				}
			}
			s.setWaitingAck(waitingAck);
			s.setActive(active);
			s.setWaitingChild(waitingChild);
			s.setReported(reported);
			s.setTotal(total);
			s.setAvgAwarenessTime(totalAwarenessTime/numAwarenessTime);
			s.setAvgResponseTime(totalResponseTime/numResponseTime);
		}
		return supporterInfos;
	}

}
