package tr.edu.yildiz.ce.dao;

import tr.edu.yildiz.ce.entity.Complaint;
import tr.edu.yildiz.ce.model.ComplaintInfo;

public interface ComplaintDAO {
	public Complaint findComplaint(int id);
	public void saveComplaint (ComplaintInfo complaintInfo);
	public ComplaintInfo findComplaintInfo(int id);
	public void deleteComplaint (int id);
	
}
