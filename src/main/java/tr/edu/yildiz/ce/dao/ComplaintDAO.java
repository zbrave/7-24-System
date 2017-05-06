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
    public void uniteComplaints(Integer uniteTo,Integer delete);
    public List<ComplaintInfo> listComplaintInfos (); 
    public List<ComplaintInfo> listComplaintInfosForSupport(Integer userId);
    public List<ComplaintInfo> listComplaintProcess(Integer id);
    public List<ComplaintInfo> listActiveComplaintInfos();
    public List<ComplaintInfo> listComplaintInfosForManager(Integer userId);
    public long avgTimeForProcess();
    public long avgTimeForComplaintBySupportType(Integer supportTypeId);
    public Integer numOfActiveComplaintBySupportType(Integer supportTypeId);
    public Integer numOfWaitingComplaintBySupportType(Integer supportTypeId);
    public Integer numOfComplaintBySupportType(Integer supportTypeId);
    public Integer numOfProcess();
}
