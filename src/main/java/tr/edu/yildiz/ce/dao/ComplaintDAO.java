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
    public void assingComplaint(Integer id,Integer supportUserId);//manager assign edicek+
    public void ackComplaint(Integer id);// support+
    public void reportComplaint(Integer id);// support un her yerine ekle -ack olanlar ona ait değilse report edebilsin+
    public void transferComplaint (Integer id,String responseText,Integer newLocationId,Integer newSupportTypeId,String newComplaintText,boolean ended);
    public void endComplaint(Integer id,String responseText);
    public void uniteComplaints(Integer uniteTo,Integer delete);// manager report edilenler ve assigndan önce+
    public List<ComplaintInfo> listComplaintInfos (Integer locationId,Integer supportTypeId);// report için hepsini gösteriyor+
    public List<ComplaintInfo> listWaitingAssingnComplaintInfos (Integer locationId,Integer supportTypeId);// report için
    public List<ComplaintInfo> listWaitingAckComplaintInfos (Integer locationId,Integer supportTypeId);
    public List<ComplaintInfo> listActiveComplaintInfos (Integer locationId,Integer supportTypeId);
    public List<ComplaintInfo> listWaitingChildComplaintInfos (Integer locationId,Integer supportTypeId);
    public List<ComplaintInfo> listReportedComplaintInfos (Integer locationId,Integer supportTypeId);
    public List<ComplaintInfo> listEndedComplaintInfos (Integer locationId,Integer supportTypeId);
    public List<ComplaintInfo> listComplaintInfosByUserId (Integer supportUserId);// buraya kadar report
    public List<ComplaintInfo> listComplaintInfosForSupport(Integer userId);//ack yapılanlar+
    public List<ComplaintInfo> listComplaintInfosForSupportAck(Integer userId);// ack yapılmayanlar+
    public List<ComplaintInfo> listComplaintProcess(Integer id);// tüm tree çekiyor listeleme için // report için-compl listleenn yerlerede konulabilir
    public List<ComplaintInfo> listComplaintInfosForAssignment(Integer userId);//manager assign edecekleri+
    public List<ComplaintInfo> listReportedComplaintInfosForManager(Integer userId);//manager report edilenler+
    public List<ComplaintInfo> listActiveComplaintInfosForUnification(Integer id);// unify edilecekler+
    
    public Integer numOfProcess();//kaç tane aktif işlem var
}
