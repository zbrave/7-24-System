package tr.edu.yildiz.ce.dao.impl;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import tr.edu.yildiz.ce.dao.ComplaintDAO;
import tr.edu.yildiz.ce.entity.Complaint;
import tr.edu.yildiz.ce.model.ComplaintInfo;

public class ComplaintDAOImpl implements ComplaintDAO {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private LocationDAOImpl locationDAOImpl;
	@Autowired
	private SupportTypeDAOImpl supportTypeDAOImpl;
	@Autowired
	private SupporterDAOImpl supporterDAOImpl;
	@Autowired
	private UserDAOImpl userDAOImpl;	
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
        }
        complaint.setSupportTypeId(null);
        if(complaintInfo.getSupportTypeInfo()!=null){
        	complaint.setSupportTypeId(complaintInfo.getSupportTypeInfo().getId());
        }
        complaint.setParentId(null);
        if(complaintInfo.getParentInfo()!=null){
        	complaint.setParentId(complaintInfo.getParentInfo().getId());
        }
        complaint.setComplaintTime(complaintInfo.getComplaintTime());
        complaint.setComplaintText(complaintInfo.getComplaintText());
        complaint.setSupportUserId(null);
        if(complaintInfo.getSupportUserInfo()!=null){
        	complaint.setSupportUserId(complaintInfo.getSupportUserInfo().getId());
        }
        complaint.setResponseTime(complaintInfo.getResponseTime());
        complaint.setResponseText(complaintInfo.getResponseText());
        complaint.setChildId(null);
        if(complaintInfo.getChildInfo()!=null){
        	complaint.setChildId(complaintInfo.getChildInfo().getId());
        }
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
        return new ComplaintInfo(complaint.getId(), locationDAOImpl.findLocationInfo(complaint.getLocationId()) ,
        		supportTypeDAOImpl.findSupportTypeInfo(complaint.getSupportTypeId()) , findComplaintInfo(complaint.getParentId()),
        		userDAOImpl.findUserInfo(complaint.getComplainantUserId()),complaint.getComplaintTime(), complaint.getComplaintText(),
        		userDAOImpl.findUserInfo(complaint.getSupportUserId()),complaint.getResponseTime(),complaint.getResponseText(),
        		findComplaintInfo( complaint.getChildId() ) );
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
		ComplaintInfo complaintInfo = new ComplaintInfo();
		complaintInfo.setLocationInfo(locationDAOImpl.findLocationInfo(locationId));
		complaintInfo.setSupportTypeInfo(supportTypeDAOImpl.findSupportTypeInfo(supportTypeId));
		complaintInfo.setComplainantUserInfo(userDAOImpl.findUserInfo(complainantUserId));
		complaintInfo.setComplaintText(complaintText);
		complaintInfo.setComplaintTime(new Date());
		saveComplaint(complaintInfo);
	}

	@Override
	public void transferComplaint(Integer id, Integer supportUserId, String responseText, Integer newLocationId,
		Integer newSupportTypeId, String newComplaintText) {
		Complaint complaint = this.findComplaint(id);
		ComplaintInfo newComplaintInfo = new ComplaintInfo();
		complaint.setSupportUserId(supportUserId);
		complaint.setResponseText(responseText);
		complaint.setResponseTime(new Date());
		newComplaintInfo.setParentInfo(findComplaintInfo(id));
		newComplaintInfo.setLocationInfo(locationDAOImpl.findLocationInfo(newLocationId));
		newComplaintInfo.setSupportTypeInfo(supportTypeDAOImpl.findSupportTypeInfo(newSupportTypeId));
		newComplaintInfo.setComplainantUserInfo(userDAOImpl.findUserInfo(supportUserId));
		newComplaintInfo.setComplaintText(newComplaintText);
		newComplaintInfo.setComplaintTime(new Date());
		
		saveComplaint(newComplaintInfo);
		complaint.setChildId(findChild(id).getId());
	}

	@Override
	public void endComplaint(Integer id, Integer supportUserId, String responseText) {
		Complaint complaint = this.findComplaint(id);
		complaint.setSupportUserId(supportUserId);
		complaint.setResponseText(responseText);
		complaint.setResponseTime(new Date());
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
}
