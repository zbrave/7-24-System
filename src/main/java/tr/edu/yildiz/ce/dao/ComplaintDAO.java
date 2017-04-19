package tr.edu.yildiz.ce.dao;

import java.util.List;

import tr.edu.yildiz.ce.entity.Complaint;
import tr.edu.yildiz.ce.model.ComplaintInfo;

public interface ComplaintDAO {
	public Complaint findComplaint (Integer id); 
	public void saveComplaint (ComplaintInfo complaintInfo);
    public ComplaintInfo findComplaintInfo (Integer id);  
    public void deleteComplaint (Integer id);
    
    public void recordComplaint (Integer locationId,Integer supportTypeId,Integer complainantUserId,String complaintText);
    public void transferComplaint (Integer id,Integer supportUserId,String responseText,Integer newLocationId,Integer newSupportTypeId,String newComplaintText,boolean ended);
    public void endComplaint(Integer id,Integer supportUserId,String responseText);
    public void uniteComplaints(Integer id1,Integer id2);
    public List<ComplaintInfo> listComplaintInfos (); 
    public List<ComplaintInfo> listComplaintInfosForSupport(Integer userId);
}
