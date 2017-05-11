package tr.edu.yildiz.ce.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import tr.edu.yildiz.ce.dao.ComplaintDAO;
import tr.edu.yildiz.ce.dao.LocationDAO;
import tr.edu.yildiz.ce.dao.MailSend;
import tr.edu.yildiz.ce.dao.ManagerDAO;
import tr.edu.yildiz.ce.dao.SupportTypeDAO;
import tr.edu.yildiz.ce.dao.SupporterDAO;
import tr.edu.yildiz.ce.dao.NotificationDAO;
import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.entity.Complaint;
import tr.edu.yildiz.ce.model.ComplaintInfo;
import tr.edu.yildiz.ce.model.LocationInfo;
import tr.edu.yildiz.ce.model.ManagerInfo;
import tr.edu.yildiz.ce.model.NotificationInfo;
import tr.edu.yildiz.ce.model.SupporterInfo;

public class ComplaintDAOImpl implements ComplaintDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private ManagerDAO managerDAO;
	@Autowired
	private LocationDAO locationDAO;
	@Autowired
	private SupportTypeDAO supportTypeDAO;
	@Autowired
	private UserDAO userDAO;	
	@Autowired
	private SupporterDAO supporterDAO;
	@Autowired
	private NotificationDAO notificationDAO;
	@Autowired
	private MailSend mailSend;
	@Override
	public Complaint findComplaint(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.eq("id", id));
        return (Complaint) crit.uniqueResult();
	}

	@Override
	public void saveComplaint(ComplaintInfo complaintInfo) {
        Integer id = complaintInfo.getId();
        Complaint complaint = null;
        if (id != null) {
        	complaint = this.findComplaint(id);
        }
        boolean isNew = false;
        if (complaint == null) {
            isNew = true;
            complaint = new Complaint();
        }
        complaint.setId(complaintInfo.getId());
        complaint.setLocationId(complaintInfo.getLocationId());
        complaint.setSupportTypeId(complaintInfo.getSupportTypeId());
        complaint.setParentId(complaintInfo.getParentId());
        complaint.setComplainantUserId( complaintInfo.getComplainantUserId() );
        complaint.setComplaintTime(complaintInfo.getComplaintTime());
        complaint.setComplaintText(complaintInfo.getComplaintText());
        complaint.setSupportUserId(complaintInfo.getSupportUserId());
        complaint.setResponseTime(complaintInfo.getResponseTime());
        complaint.setResponseText(complaintInfo.getResponseText());
        complaint.setChildId(complaintInfo.getChildId());
        complaint.setEnded(complaintInfo.isEnded());
        complaint.setAck(complaintInfo.isAck());
        complaint.setAckTime(complaintInfo.getAckTime());
        complaint.setReported(complaintInfo.isReported());
        if (isNew){
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(complaint);
        }
	}

	@Override
	public ComplaintInfo findComplaintInfo(Integer id) {
		Complaint complaint = this.findComplaint(id);
        if (complaint == null) {
            return null;
        }
        return new ComplaintInfo(complaint.getId(), complaint.getLocationId() ,complaint.getSupportTypeId() ,complaint.getParentId(),
        		complaint.getComplainantUserId(),complaint.getComplaintTime(), complaint.getComplaintText(),complaint.getSupportUserId(),
        		complaint.getResponseTime(),complaint.getResponseText(),complaint.getChildId() ,complaint.isEnded(),complaint.getAckTime(),complaint.isAck(),complaint.isReported() );
	}

	@Override
	public void deleteComplaint(Integer id) {
		Complaint complaint = this.findComplaint(id);
        if (complaint != null) {
            this.sessionFactory.getCurrentSession().delete(complaint);
        }
	}

	@Override
	public void recordComplaint(Integer locationId, Integer supportTypeId, Integer complainantUserId,
			String complaintText,Integer parentId ) {
		//notifications
		ComplaintInfo complaintInfo = new ComplaintInfo();
		complaintInfo.setLocationId(locationId);
		complaintInfo.setSupportTypeId(supportTypeId);
		complaintInfo.setComplainantUserId(complainantUserId);
		complaintInfo.setComplaintText(complaintText);
		complaintInfo.setEnded(false);
		complaintInfo.setAck(false);
		complaintInfo.setParentId(parentId);
		complaintInfo.setReported(false);
		saveComplaint(complaintInfo);
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.eq("locationId", locationId));
        crit.add(Restrictions.eq("supportTypeId", supportTypeId));
        crit.add(Restrictions.eq("complainantUserId", complainantUserId));
        crit.add(Restrictions.eq("complaintText", complaintText));
        Complaint complaint=(Complaint) crit.uniqueResult();
		NotificationInfo notificationInfo =new NotificationInfo();
		notificationInfo.setUserId(complaint.getComplainantUserId());
		notificationInfo.setComplaintId(complaint.getId());
		notificationDAO.saveNotification(notificationInfo);
		complaintInfo=findComplaintInfo(complaint.getId());
		
		String to=userDAO.findUserInfo(complainantUserId).getEmail();
		String subject="Complaint Recorded";
		String text="  Merhaba "+userDAO.findUserInfo(complainantUserId).getUsername()+",\n"+
		"Location : "+locationDAO.findLocationInfo(locationId).getDescription()+",\n"+
		"Support Type : "+supportTypeDAO.findSupportTypeInfo( supportTypeId).getType() + ",\n"+
		"Complaint Text : "+complaintText+ ",\n";
		mailSend.sendSimpleMessage(to, subject, text);
	}

	@Override
	public void ackComplaint(Integer id) {
		ComplaintInfo c =this.findComplaintInfo(id);
		c.setAck(true);
		Date dateNow =new Date();
		c.setAckTime(dateNow);
		saveComplaint(c);
	}
	@Override
	public void reportComplaint(Integer id) {
		ComplaintInfo c =this.findComplaintInfo(id);
		c.setReported(true);
		saveComplaint(c);
		
	}
	@Override
	public void assingComplaint(Integer id, Integer supportUserId) {
		ComplaintInfo c =this.findComplaintInfo(id);
		c.setSupportUserId(supportUserId);
		Date dateNow =new Date();
		c.setComplaintTime(dateNow);
	}
	
	@Override
	public void transferComplaint(Integer id,  String responseText, Integer newLocationId,
		Integer newSupportTypeId, String newComplaintText,boolean ended) {
		//notifications
		Integer parentComplaintId=id;
		Integer childComplaintId;
		ComplaintInfo complaintInfo = this.findComplaintInfo(id);
		complaintInfo.setResponseText(responseText);
		complaintInfo.setResponseTime(new Date());
		complaintInfo.setEnded(ended);
		complaintInfo.setReported(false);
		
		this.recordComplaint(newLocationId,newSupportTypeId,complaintInfo.getComplainantUserId(),
				newComplaintText,complaintInfo.getId() );
		complaintInfo.setChildId(this.findChildInfo(id).getId());
		childComplaintId=findChildInfo(id).getId();
		this.saveComplaint(complaintInfo);
		childComplaintId=findChildInfo(id).getId();
		List<NotificationInfo> notificationInfos = notificationDAO.listNotificationInfosForComplaint(parentComplaintId);
		for(NotificationInfo n : notificationInfos){
			n.setComplaintId(childComplaintId);
			notificationDAO.saveNotification(n);
		}
		
	}

	@Override
	public void endComplaint(Integer id, String responseText) {
		boolean sendEmail=false;
		ComplaintInfo complaintInfo = this.findComplaintInfo(id);
		complaintInfo.setResponseText(responseText);
		complaintInfo.setResponseTime(new Date());
		complaintInfo.setEnded(true);
		complaintInfo.setReported(false);
		saveComplaint(complaintInfo);
		
		if(complaintInfo.getParentId()!=null){
			ComplaintInfo notComplete = findComplaintInfo(complaintInfo.getParentId());
			while( notComplete.getParentId()!=null && notComplete.isEnded()==true ){
				notComplete =findComplaintInfo(notComplete.getParentId());
			}
			if(notComplete.isEnded()==false){
				notComplete.setEnded(true);
				saveComplaint(notComplete);
				transferComplaint(id, responseText,notComplete.getLocationId(),
						notComplete.getSupportTypeId(), notComplete.getComplaintText(),true);
			}else{
				sendEmail=true;
			}
				
		}else {
			sendEmail=true;
		}
		if(sendEmail){
			List<NotificationInfo> notificationInfos = notificationDAO.listNotificationInfosForComplaint(id);
			List<ComplaintInfo> process = this.listComplaintProcess(id);
			String past="\n";
			for(ComplaintInfo c: process ){
				past=past+"Location : "+ locationDAO.findLocationInfo(c.getLocationId()).getDescription()+",\n"+
						"Support Type : "+supportTypeDAO.findSupportTypeInfo(c.getSupportTypeId() ).getType() + ",\n"+
						"Response Text : "+c.getResponseText()+ ",\n"+
						"Response Time : "+c.getResponseTime()+ ",\n\n";
			}
			for(NotificationInfo n : notificationInfos){
				String to=userDAO.findUserInfo(n.getUserId()).getEmail();
				String subject="Complaint Resulted";
				String text="	Merhaba "+userDAO.findUserInfo(n.getUserId()).getUsername()+",\n"+past;
				mailSend.sendSimpleMessage(to, subject, text);
				notificationDAO.deleteNotification(n.getId());
			}
		}
	}

	@Override
	public void uniteComplaints(Integer uniteTo, Integer delete) {
		List<ComplaintInfo> del = this.listComplaintProcess(delete);
		ComplaintInfo c = this.findComplaintInfo(uniteTo);
		while(c.getChildId()!=null){
			c=this.findComplaintInfo(c.getChildId());
			uniteTo = c.getId();
		}
		List<NotificationInfo> notificationInfos = notificationDAO.listNotificationInfosForComplaint(delete);
		for(NotificationInfo n : notificationInfos){
			n.setComplaintId(uniteTo);
			notificationDAO.saveNotification(n);
		}
		for(ComplaintInfo d:del){
			deleteComplaint(d.getId());
		}
	}
	
	public ComplaintInfo findChildInfo(Integer parentId){
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.eq("parentId", parentId));
        Complaint c=(Complaint)crit.uniqueResult();
        return findComplaintInfo(c.getId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintInfo> listComplaintInfos(Integer locationId,Integer supportTypeId) {
        Session session = sessionFactory.getCurrentSession();
        List<Complaint> complaints =new ArrayList<Complaint>();
        if(supportTypeId==null&&locationId==null){
        	Criteria crit = session.createCriteria(Complaint.class);
        	complaints.addAll(crit.list());
        }
        if(supportTypeId!=null&&locationId==null){
        	Criteria crit = session.createCriteria(Complaint.class);
        	crit.add(Restrictions.eq("supportTypeId", supportTypeId));
        	complaints.addAll(crit.list());
        }
        if(locationId!=null){
            List<LocationInfo> locationInfoTree = locationDAO.findLocationInfoTree(locationId);
    		for(LocationInfo l:locationInfoTree){
    			Criteria crit = session.createCriteria(Complaint.class);
    	        if(supportTypeId!=null){
    	        	crit.add(Restrictions.eq("supportTypeId", supportTypeId));
    	        }
    	        crit.add(Restrictions.eq("locationId", l.getId()));
    	        complaints.addAll(crit.list());
    		}
        }

        List<ComplaintInfo> complaintInfos =new ArrayList<ComplaintInfo>(); 
        for(Complaint c:complaints){
        	complaintInfos.add((ComplaintInfo)findComplaintInfo(c.getId()));
        }
        return complaintInfos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintInfo> listComplaintInfosForSupport(Integer userId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.eq("supportUserId", userId));
        crit.add(Restrictions.eq("ack", true));
        crit.add(Restrictions.eq("reported", false));
		List<Complaint> complaints = (List<Complaint>)crit.list();
		List<ComplaintInfo> complaintInfos =new ArrayList<ComplaintInfo>();
		
        for(Complaint c:complaints){
        	complaintInfos.add((ComplaintInfo)findComplaintInfo(c.getId()));
        }
        return complaintInfos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintInfo> listComplaintInfosForSupportAck(Integer userId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.eq("supportUserId", userId));
        crit.add(Restrictions.eq("ack", false));
        crit.add(Restrictions.eq("reported", false));
		List<Complaint> complaints = (List<Complaint>)crit.list();
		List<ComplaintInfo> complaintInfos =new ArrayList<ComplaintInfo>();
		
        for(Complaint c:complaints){
        	complaintInfos.add((ComplaintInfo)findComplaintInfo(c.getId()));
        }
        return complaintInfos;
	}
	@Override
	public List<ComplaintInfo> listComplaintProcess(Integer id) {
		ComplaintInfo c=this.findComplaintInfo(id);
		if(c==null){
			return null;
		}
		List<ComplaintInfo> complaintInfos =new ArrayList<ComplaintInfo>();
		while(c.getParentId()!=null){
			c=this.findComplaintInfo(c.getParentId());
		}
		complaintInfos.add(c);
		while(c.getChildId()!=null){
			c=this.findComplaintInfo(c.getChildId());
			complaintInfos.add(c);
		}
		return complaintInfos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintInfo> listComplaintInfosForAssignment(Integer userId) {
        List<ManagerInfo> managerInfos= managerDAO.listManagerInfosById(userId);
        List<LocationInfo> locationInfoTree = new ArrayList<LocationInfo>();
        for(ManagerInfo m:managerInfos){
        	List<LocationInfo> loc=locationDAO.findLocationInfoTree(m.getLocationId());
        	loc.removeAll(locationInfoTree);
        	locationInfoTree.addAll(loc);
        }
        Session session = sessionFactory.getCurrentSession();
        List<Complaint> complaints =new ArrayList<Complaint>(); 
		for(LocationInfo l:locationInfoTree){
			Criteria crit = session.createCriteria(Complaint.class);
			crit.add(Restrictions.isNull("supportUserId"));
	        crit.add(Restrictions.eq("locationId", l.getId()));
	        complaints.addAll(crit.list());
		}
        List<ComplaintInfo> complaintInfos =new ArrayList<ComplaintInfo>(); 
        for(Complaint c:complaints){
        	complaintInfos.add((ComplaintInfo)findComplaintInfo(c.getId()));
        }
        return complaintInfos;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintInfo> listReportedComplaintInfosForManager(Integer userId) {
        List<ManagerInfo> managerInfos= managerDAO.listManagerInfosById(userId);
        List<LocationInfo> locationInfoTree = new ArrayList<LocationInfo>();
        for(ManagerInfo m:managerInfos){
        	List<LocationInfo> loc=locationDAO.findLocationInfoTree(m.getLocationId());
        	loc.removeAll(locationInfoTree);
        	locationInfoTree.addAll(loc);
        }
        
        Session session = sessionFactory.getCurrentSession();
        List<Complaint> complaints =new ArrayList<Complaint>(); 
		for(LocationInfo l:locationInfoTree){
			Criteria crit = session.createCriteria(Complaint.class);
	        crit.add(Restrictions.eq("reported", true));
	        crit.add(Restrictions.eq("locationId", l.getId()));
	        complaints.addAll(crit.list());
		}
        List<ComplaintInfo> complaintInfos =new ArrayList<ComplaintInfo>(); 
        for(Complaint c:complaints){
        	complaintInfos.add((ComplaintInfo)findComplaintInfo(c.getId()));
        }
        return complaintInfos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintInfo> listActiveComplaintInfosForUnification(Integer id) {
		ComplaintInfo com=this.findComplaintInfo(id);
		List<LocationInfo> locTree=locationDAO.listLocationInfoByProximity(com.getLocationId());
        Session session = sessionFactory.getCurrentSession();
        List<Complaint> complaints =new ArrayList<Complaint>();
        for(LocationInfo l:locTree){
        	Criteria crit = session.createCriteria(Complaint.class);
            crit.add(Restrictions.eq("ended",false));
            crit.add(Restrictions.eq("reported",false));
            crit.add(Restrictions.eq("locationId",l.getId()));
            complaints.addAll(crit.list());
        }
        List<Complaint> allComplaints =new ArrayList<Complaint>();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.eq("ended",false));
        crit.add(Restrictions.eq("reported",false));
        allComplaints.addAll(crit.list());
        allComplaints.removeAll(complaints);
        complaints.addAll(allComplaints);
        List<ComplaintInfo> complaintInfos =new ArrayList<ComplaintInfo>();
        for(Complaint c:complaints){
        	complaintInfos.add((ComplaintInfo)findComplaintInfo(c.getId()));
        }
        return complaintInfos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Integer numOfProcess() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.isNull("childId"));
        List<Complaint> complaints = crit.list();
        return complaints.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintInfo> listComplaintInfosByUserId(Integer supportUserId) {
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.eq("supportUserId",supportUserId));
		List<Complaint> complaints = (List<Complaint>)crit.list();
		List<ComplaintInfo> complaintInfos =new ArrayList<ComplaintInfo>();
        for(Complaint c:complaints){
        	complaintInfos.add((ComplaintInfo)findComplaintInfo(c.getId()));
        }
        return complaintInfos;
	}

	@Override
	public List<ComplaintInfo> listWaitingAssingnComplaintInfos(Integer locationId, Integer supportTypeId) {
		List<ComplaintInfo> complaintInfos=this.listComplaintInfos(locationId, supportTypeId);
		List<ComplaintInfo> retComplaintInfos=new ArrayList<ComplaintInfo>();
		for(ComplaintInfo c:complaintInfos){
			if(c.getSupportUserId()==null){
				retComplaintInfos.add(c);
			}
		}
		return retComplaintInfos;
	}

	@Override
	public List<ComplaintInfo> listWaitingAckComplaintInfos(Integer locationId, Integer supportTypeId) {
		List<ComplaintInfo> complaintInfos=this.listComplaintInfos(locationId, supportTypeId);
		List<ComplaintInfo> retComplaintInfos=new ArrayList<ComplaintInfo>();
		for(ComplaintInfo c:complaintInfos){
			if(c.isAck()==false&&c.getSupportUserId()!=null){
				retComplaintInfos.add(c);
			}
		}
		return retComplaintInfos;
	}

	@Override
	public List<ComplaintInfo> listActiveComplaintInfos(Integer locationId, Integer supportTypeId) {
		List<ComplaintInfo> complaintInfos=this.listComplaintInfos(locationId, supportTypeId);
		List<ComplaintInfo> retComplaintInfos=new ArrayList<ComplaintInfo>();
		for(ComplaintInfo c:complaintInfos){
			if(c.isAck()==true&&c.isEnded()==false&&c.getChildId()==null&&c.isReported()==false){
				retComplaintInfos.add(c);
			}
		}
		return retComplaintInfos;
	}
	@Override
	public List<ComplaintInfo> listWaitingChildComplaintInfos(Integer locationId, Integer supportTypeId) {
		List<ComplaintInfo> complaintInfos=this.listComplaintInfos(locationId, supportTypeId);
		List<ComplaintInfo> retComplaintInfos=new ArrayList<ComplaintInfo>();
		for(ComplaintInfo c:complaintInfos){
			if(c.isAck()==true&&c.isEnded()==false&&c.getChildId()!=null&&c.isReported()==false){
				retComplaintInfos.add(c);
			}
		}
		return retComplaintInfos;
	}

	@Override
	public List<ComplaintInfo> listReportedComplaintInfos(Integer locationId, Integer supportTypeId) {
		List<ComplaintInfo> complaintInfos=this.listComplaintInfos(locationId, supportTypeId);
		List<ComplaintInfo> retComplaintInfos=new ArrayList<ComplaintInfo>();
		for(ComplaintInfo c:complaintInfos){
			if(c.isReported()==true){
				retComplaintInfos.add(c);
			}
		}
		return retComplaintInfos;
	}
}
