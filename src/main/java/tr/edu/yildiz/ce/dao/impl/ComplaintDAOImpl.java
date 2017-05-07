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
import tr.edu.yildiz.ce.dao.SupportTypeDAO;
import tr.edu.yildiz.ce.dao.SupporterDAO;
import tr.edu.yildiz.ce.dao.NotificationDAO;
import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.entity.Complaint;
import tr.edu.yildiz.ce.model.ComplaintInfo;
import tr.edu.yildiz.ce.model.LocationInfo;
import tr.edu.yildiz.ce.model.NotificationInfo;
import tr.edu.yildiz.ce.model.SupporterInfo;

public class ComplaintDAOImpl implements ComplaintDAO {
	@Autowired
	private SessionFactory sessionFactory;
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
        		complaint.getResponseTime(),complaint.getResponseText(),complaint.getChildId() ,complaint.isEnded() );
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
			String complaintText ) {
		//notifications
		ComplaintInfo complaintInfo = new ComplaintInfo();
		complaintInfo.setLocationId(locationId);
		complaintInfo.setSupportTypeId(supportTypeId);
		complaintInfo.setComplainantUserId(complainantUserId);
		complaintInfo.setComplaintText(complaintText);
		Date dateNow =new Date();
		complaintInfo.setComplaintTime(dateNow);
		complaintInfo.setEnded(false);
		saveComplaint(complaintInfo);
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.eq("locationId", locationId));
        crit.add(Restrictions.eq("supportTypeId", supportTypeId));
        crit.add(Restrictions.eq("complainantUserId", complainantUserId));
        crit.add(Restrictions.eq("complaintText", complaintText));
        crit.add(Restrictions.eq("complaintTime", dateNow));
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
		"Complaint Text : "+complaintText+ ",\n"+
		"Complaint Time : "+dateNow.toString()+ ",\n";
		mailSend.sendSimpleMessage(to, subject, text);
	}

	@Override
	public void transferComplaint(Integer id, Integer supportUserId, String responseText, Integer newLocationId,
		Integer newSupportTypeId, String newComplaintText,boolean ended) {
		//notifications
		Integer parentComplaintId=id;
		Integer childComplaintId;
		Complaint complaint = this.findComplaint(id);
		ComplaintInfo newComplaintInfo = new ComplaintInfo();
		complaint.setSupportUserId(supportUserId);
		complaint.setResponseText(responseText);
		complaint.setResponseTime(new Date());
		complaint.setEnded(ended);
		newComplaintInfo.setParentId(id);
		newComplaintInfo.setLocationId(newLocationId);
		newComplaintInfo.setSupportTypeId(newSupportTypeId);
		newComplaintInfo.setComplainantUserId(supportUserId);
		newComplaintInfo.setComplaintText(newComplaintText);
		newComplaintInfo.setComplaintTime(new Date());
		
		saveComplaint(newComplaintInfo);
		complaint.setChildId(findChildInfo(id).getId());
		childComplaintId=findChildInfo(id).getId();
		List<NotificationInfo> notificationInfos = notificationDAO.listNotificationInfosForComplaint(parentComplaintId);
		for(NotificationInfo n : notificationInfos){
			n.setComplaintId(childComplaintId);
			notificationDAO.saveNotification(n);
		}
		
	}

	@Override
	public void endComplaint(Integer id, Integer supportUserId, String responseText) {
		//notifications
		boolean sendEmail=false;
		Complaint complaint = this.findComplaint(id);
		complaint.setSupportUserId(supportUserId);
		complaint.setResponseText(responseText);
		complaint.setResponseTime(new Date());
		complaint.setEnded(true);
		if(complaint.getParentId()!=null){
			ComplaintInfo notComplete = findComplaintInfo(complaint.getParentId());
			while( notComplete.getParentId()!=null && notComplete.isEnded()==true ){
				notComplete =findComplaintInfo(notComplete.getParentId());
			}
			if(notComplete.isEnded()==false){
				notComplete.setEnded(true);
				saveComplaint(notComplete);
				transferComplaint(id, supportUserId, responseText,notComplete.getLocationId(),
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
	public List<ComplaintInfo> listComplaintInfos() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        List<Complaint> complaints = crit.list();
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
		List<SupporterInfo> supporterInfos = supporterDAO.listSupporterInfosById(userId);
		List<Complaint> complaints = new ArrayList<Complaint>(); 
		List<ComplaintInfo> complaintInfos =new ArrayList<ComplaintInfo>();
		for(SupporterInfo s:supporterInfos){
			List<LocationInfo> locationInfos =new ArrayList<LocationInfo>();
			locationInfos.addAll(locationDAO.findLocationInfoTree(s.getLocationId()));
			for(LocationInfo l:locationInfos){
		        Criteria crit = session.createCriteria(Complaint.class);
		        crit.add(Restrictions.eq("supportTypeId", s.getSupportTypeId()));
		        crit.add(Restrictions.eq("locationId", l.getId()));
		        crit.add(Restrictions.eq("ended",false));
		        crit.add(Restrictions.isNull("childId"));
		        complaints.addAll((List<Complaint>)crit.list());
			}
		}
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
	public List<ComplaintInfo> listActiveComplaintInfos() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.eq("ended",false));
        List<Complaint> complaints = crit.list();
        List<ComplaintInfo> complaintInfos =new ArrayList<ComplaintInfo>(); 
        for(Complaint c:complaints){
        	complaintInfos.add((ComplaintInfo)findComplaintInfo(c.getId()));
        }
        return complaintInfos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintInfo> listComplaintInfosForManager(Integer userId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.eq("ended",false));
        crit.add(Restrictions.isNull("childId"));
        List<Complaint> complaints = crit.list();
        List<ComplaintInfo> complaintInfos =new ArrayList<ComplaintInfo>(); 
        List<Integer> userIds= new ArrayList<Integer>();
        for(Complaint c:complaints){
        	complaintInfos.add((ComplaintInfo)findComplaintInfo(c.getId()));
        	userIds.addAll(supporterDAO.listSupporterUserIdBySupportType(c.getSupportTypeId()));
        }
		for(Integer i : userIds){
			complaintInfos.removeAll(listComplaintInfosForSupport(i));
		}
		complaintInfos.addAll(this.listComplaintInfosForSupport(userId));
		return complaintInfos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long avgTimeForProcess() {
		long time=0;
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.eq("ended",true));
        crit.add(Restrictions.isNull("childId"));
        List<Complaint> complaints = crit.list();
        
        List<ComplaintInfo> complaintInfos =new ArrayList<ComplaintInfo>(); 
        for(Complaint c:complaints){
        	complaintInfos= this.listComplaintProcess(c.getId());
        	time+=c.getResponseTime().getTime()-complaintInfos.get(0).getComplaintTime().getTime();
        }
        if(complaints.size()>0){
            time=time/complaints.size();      	
        }
		return time;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long avgTimeForComplaintBySupportType(Integer supportTypeId) {
		long time=0;
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.eq("ended",true));
        if(supportTypeId!=null){
            crit.add(Restrictions.eq("supportTypeId", supportTypeId));
        }
        List<Complaint> complaints = crit.list();
        for(Complaint c:complaints){
        	time+=c.getResponseTime().getTime()-c.getComplaintTime().getTime();
        }
        if(complaints.size()>0){
            time=time/complaints.size();      	
        }
        return time;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer numOfActiveComplaintBySupportType(Integer supportTypeId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.eq("ended",false));
        crit.add(Restrictions.isNull("childId"));
        if(supportTypeId!=null){
            crit.add(Restrictions.eq("supportTypeId", supportTypeId));
        }
        List<Complaint> complaints = crit.list();
        return complaints.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer numOfWaitingComplaintBySupportType(Integer supportTypeId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.eq("ended",false));
        crit.add(Restrictions.isNotNull("childId"));
        if(supportTypeId!=null){
            crit.add(Restrictions.eq("supportTypeId", supportTypeId));
        }
        List<Complaint> complaints = crit.list();
        return complaints.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer numOfComplaintBySupportType(Integer supportTypeId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        if(supportTypeId!=null){
            crit.add(Restrictions.eq("supportTypeId", supportTypeId));
        }
        List<Complaint> complaints = crit.list();
        return complaints.size();
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
	
}
