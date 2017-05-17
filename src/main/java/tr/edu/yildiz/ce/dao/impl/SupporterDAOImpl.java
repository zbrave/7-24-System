package tr.edu.yildiz.ce.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import tr.edu.yildiz.ce.dao.ComplaintDAO;
import tr.edu.yildiz.ce.dao.SupporterDAO;
import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.entity.Complaint;
import tr.edu.yildiz.ce.entity.Supporter;
import tr.edu.yildiz.ce.entity.UserRole;
import tr.edu.yildiz.ce.model.ComplaintInfo;
import tr.edu.yildiz.ce.model.SupportTypeInfo;
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
	        long totalAwarenessTime=0;
			long numAwarenessTime=0;
			long totalResponseTime=0;
			long numResponseTime=0;
			Integer waitingAck=0;
			Integer active=0;
			Integer waitingChild=0;
			Integer total=0;
			Integer reported=0;
			Integer ended=0;
			List<ComplaintInfo> complaintInfos =complaintDAO.listComplaintInfos(null, null, s.getId(), null);
			total=complaintInfos.size();
			for(ComplaintInfo c:complaintInfos){
				if(c.getAssignTime()!=null&&c.getAckTime()!=null){
					totalAwarenessTime+=c.getAckTime().getTime()-c.getAssignTime().getTime();
					if(c.getAckTime().getTime()-c.getAssignTime().getTime()!=0){
						numAwarenessTime++;
					}
				}
				if(c.getAckTime()!=null&&c.getResponseTime()!=null){
					totalResponseTime+=c.getResponseTime().getTime()-c.getAckTime().getTime();
					if(c.getResponseTime().getTime()-c.getAckTime().getTime()!=0){
						numResponseTime++;	
					}
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
				if(c.isEnded()==true){
					ended++;
				}
			}
			s.setWaitingAck(waitingAck);
			s.setActive(active);
			s.setWaitingChild(waitingChild);
			s.setReported(reported);
			s.setTotal(total);
			s.setEnded(ended);
			if(numAwarenessTime!=0){
				s.setAvgAwarenessTime(totalAwarenessTime/numAwarenessTime);
			}
			if(numResponseTime!=0){
				s.setAvgResponseTime(totalResponseTime/numResponseTime);
			}
			
		}
		return supporterInfos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SupporterInfo> listSupporterInfosPagination(Integer offset, Integer maxResults) {
		List<SupporterInfo> fullList=listSupporterInfos();
        List<SupporterInfo> offsetList=new ArrayList<SupporterInfo>();
        maxResults = maxResults!=null?maxResults:10;
        offset = (offset!=null?offset:0);
        for(int i=0; i<maxResults && offset+i<fullList.size(); i++){
        	offsetList.add(fullList.get(offset+i));
        }
        return offsetList;
	}
	
	@Override
	public Long count() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Supporter.class);
        crit.setProjection(Projections.rowCount());
        return (Long) crit.uniqueResult();
	}

}
