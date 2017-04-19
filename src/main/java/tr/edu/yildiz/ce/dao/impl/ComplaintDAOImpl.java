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
import tr.edu.yildiz.ce.dao.SupportTypeDAO;
import tr.edu.yildiz.ce.dao.SupporterDAO;
import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.entity.Complaint;
import tr.edu.yildiz.ce.model.ComplaintInfo;
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
        complaint.setLocationId(null);
        if(complaintInfo.getLocationInfo()!=null){
        	complaint.setLocationId(complaintInfo.getLocationInfo().getId());
        }else{
        	complaint.setLocationId(complaintInfo.getLocationId());
        }
        complaint.setSupportTypeId(null);
        if(complaintInfo.getSupportTypeInfo()!=null){
        	complaint.setSupportTypeId(complaintInfo.getSupportTypeInfo().getId());
        }else{
        	complaint.setSupportTypeId(complaintInfo.getSupportTypeId());
        }
        complaint.setParentId(null);
        if(complaintInfo.getParentInfo()!=null){
        	complaint.setParentId(complaintInfo.getParentInfo().getId());
        }else{
        	complaint.setParentId(complaintInfo.getParentId());
        }
        complaint.setComplaintTime(complaintInfo.getComplaintTime());
        complaint.setComplaintText(complaintInfo.getComplaintText());
        complaint.setSupportUserId(null);
        if(complaintInfo.getSupportUserInfo()!=null){
        	complaint.setSupportUserId(complaintInfo.getSupportUserInfo().getId());
        }else{
        	complaint.setSupportUserId(complaintInfo.getSupportUserId());
        }
        complaint.setResponseTime(complaintInfo.getResponseTime());
        complaint.setResponseText(complaintInfo.getResponseText());
        
        complaint.setComplainantUserId(null);
        if(complaintInfo.getComplainantUserInfo()!=null){
        	complaint.setComplainantUserId(complaintInfo.getComplainantUserInfo().getId());
        }else{
        	complaint.setComplainantUserId( complaintInfo.getComplainantUserId() );
        }

        complaint.setChildId(null);
        if(complaintInfo.getChildInfo()!=null){
        	complaint.setChildId(complaintInfo.getChildInfo().getId());
        }else{
        	complaint.setChildId(complaintInfo.getChildId());
        }
        complaint.setEnded(complaintInfo.isEnded());
        if (isNew) {
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
        return new ComplaintInfo(complaint.getId(), locationDAO.findLocationInfo(complaint.getLocationId()) ,
        		supportTypeDAO.findSupportTypeInfo(complaint.getSupportTypeId()) , findComplaintInfo(complaint.getParentId()),
        		userDAO.findUserInfo(complaint.getComplainantUserId()),complaint.getComplaintTime(), complaint.getComplaintText(),
        		userDAO.findUserInfo(complaint.getSupportUserId()),complaint.getResponseTime(),complaint.getResponseText(),
        		findComplaintInfo( complaint.getChildId() ),complaint.isEnded() );
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
			String complaintText) {
		//notifications
		ComplaintInfo complaintInfo = new ComplaintInfo();
		complaintInfo.setLocationInfo(locationDAO.findLocationInfo(locationId));
		complaintInfo.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(supportTypeId));
		complaintInfo.setComplainantUserInfo(userDAO.findUserInfo(complainantUserId));
		complaintInfo.setComplaintText(complaintText);
		complaintInfo.setComplaintTime(new Date());
		complaintInfo.setEnded(false);
		saveComplaint(complaintInfo);
	}

	@Override
	public void transferComplaint(Integer id, Integer supportUserId, String responseText, Integer newLocationId,
		Integer newSupportTypeId, String newComplaintText,boolean ended) {
		//notifications
		Complaint complaint = this.findComplaint(id);
		ComplaintInfo newComplaintInfo = new ComplaintInfo();
		complaint.setSupportUserId(supportUserId);
		complaint.setResponseText(responseText);
		complaint.setResponseTime(new Date());
		complaint.setEnded(ended);
		newComplaintInfo.setParentInfo(findComplaintInfo(id));
		newComplaintInfo.setLocationInfo(locationDAO.findLocationInfo(newLocationId));
		newComplaintInfo.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(newSupportTypeId));
		newComplaintInfo.setComplainantUserInfo(userDAO.findUserInfo(supportUserId));
		newComplaintInfo.setComplaintText(newComplaintText);
		newComplaintInfo.setComplaintTime(new Date());
		
		saveComplaint(newComplaintInfo);
		complaint.setChildId(findChild(id).getId());
	}

	@Override
	public void endComplaint(Integer id, Integer supportUserId, String responseText) {
		//notifications
		Complaint complaint = this.findComplaint(id);
		complaint.setSupportUserId(supportUserId);
		complaint.setResponseText(responseText);
		complaint.setResponseTime(new Date());
		complaint.setEnded(true);
		if(complaint.getParentId()!=null){
			ComplaintInfo notComplete = findComplaintInfo(complaint.getParentId());
			while( notComplete.getParentInfo()!=null && notComplete.isEnded()==true ){
				notComplete = notComplete.getParentInfo();
			}
			if(notComplete.isEnded()==false){
				transferComplaint(id, supportUserId, responseText,notComplete.getLocationInfo().getId(),
						notComplete.getSupportTypeInfo().getId(), notComplete.getComplaintText(),true);
			}
				
		}
				
	}

	@Override
	public void uniteComplaints(Integer id1, Integer id2) {
		// TODO Auto-generated method stub

	}
	public Complaint findChild(Integer parentId){
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.eq("parentId", parentId));
        return (Complaint) crit.uniqueResult();
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
	        Criteria crit = session.createCriteria(Complaint.class);
	        crit.add(Restrictions.eq("supportTypeId",s.getSupportTypeInfo().getId()));
	        crit.add(Restrictions.eq("locationId",s.getLocationInfo().getId()));
	        complaints.addAll((List<Complaint>)crit.list());
		}
        for(Complaint c:complaints){
        	complaintInfos.add((ComplaintInfo)findComplaintInfo(c.getId()));
        }
        return complaintInfos;
	}
	
}
