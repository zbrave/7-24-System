package tr.edu.yildiz.ce.dao;

import java.util.List;

import tr.edu.yildiz.ce.entity.Complaint;
import tr.edu.yildiz.ce.model.ComplaintInfo;

public interface ComplaintDAO {
	public Complaint findComplaint (Integer id); 
	public void saveComplaint (ComplaintInfo complaintInfo);
    public ComplaintInfo findComplaintInfo (Integer id);  
    public void deleteComplaint (Integer id);
    
    public void recordComplaint (Integer locationId,Integer supportTypeId,Integer complainantUserId,String complaintText,Integer parentId);
    public void assingComplaint(Integer id,Integer supportUserId);
    public void ackComplaint(Integer id);
    public void reportComplaint(Integer id);
    public void transferComplaint (Integer id,String responseText,Integer newLocationId,Integer newSupportTypeId,String newComplaintText,boolean ended);
    public void endComplaint(Integer id,String responseText);
    public void uniteComplaints(Integer uniteTo,Integer delete);
    
    public List<ComplaintInfo> listComplaintInfos (Integer locationId,Integer supportTypeId);
    public List<ComplaintInfo> listWaitingAssingnComplaintInfos (Integer locationId,Integer supportTypeId);
    public List<ComplaintInfo> listWaitingAckComplaintInfos (Integer locationId,Integer supportTypeId);
    public List<ComplaintInfo> listActiveComplaintInfos (Integer locationId,Integer supportTypeId);
    public List<ComplaintInfo> listWaitingChildComplaintInfos (Integer locationId,Integer supportTypeId);
    public List<ComplaintInfo> listReportedComplaintInfos (Integer locationId,Integer supportTypeId);
    public List<ComplaintInfo> listComplaintInfosByUserId (Integer supportUserId);
    public List<ComplaintInfo> listComplaintInfosForSupport(Integer userId);
    public List<ComplaintInfo> listComplaintInfosForSupportAck(Integer userId);
    public List<ComplaintInfo> listComplaintProcess(Integer id);
    public List<ComplaintInfo> listComplaintInfosForAssignment(Integer userId);
    public List<ComplaintInfo> listReportedComplaintInfosForManager(Integer userId);
    public List<ComplaintInfo> listActiveComplaintInfosForUnification(Integer id);
    
    public Integer numOfProcess();
}
