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
	private SupporterDAOImpl supporterDAOImpl;
	
	@Autowired
    private SessionFactory sessionFactory;
	
	@Autowired
	private LocationDAOImpl locationDAOImpl;
	
	@Autowired
	private SupportTypeDAOImpl supportTypeDAOImpl;
	
	@Override
	public Complaint findComplaint(int id) {
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
        complaint.setLocationId(complaintInfo.getLocationInfo().getId());
        complaint.setSupportTypeId(complaintInfo.getSupportTypeInfo().getId());
        complaint.setParentId(complaintInfo.getParentInfo().getId());
        complaint.setComplaintTime(complaintInfo.getComplaintTime());
        complaint.setComplaintText(complaintInfo.getComplaintText());
        complaint.setSupporterId(complaintInfo.getSupporterInfo().getUserInfo().getStdId());
        complaint.setResponseTime(complaintInfo.getResponseTime());
        complaint.setResponseText(complaintInfo.getResponseText());
        complaint.setChildId(complaintInfo.getChildInfo().getId());
        if (isNew) {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(complaint);
        }
	}

	@Override
	public ComplaintInfo findComplaintInfo(int id) {
		Complaint complaint = this.findComplaint(id);
        if (complaint == null) {
            return null;
        }
        return new ComplaintInfo(complaint.getId(), locationDAOImpl.findLocationInfo(complaint.getLocationId()),
        		supportTypeDAOImpl.findSupportTypeInfo(complaint.getSupportTypeId()) , findComplaintInfo(complaint.getParentId()),
        		complaint.getComplaintTime(), complaint.getComplaintText(),supporterDAOImpl.findSupporterInfo(complaint.getSupporterId()),
        		complaint.getResponseTime(),complaint.getResponseText(), findComplaintInfo( complaint.getChildId() ) );
	}

	@Override
	public void deleteComplaint(int id) {
		Complaint complaint = this.findComplaint(id);
        if (complaint != null) {
            this.sessionFactory.getCurrentSession().delete(complaint);
        }
	}

}
