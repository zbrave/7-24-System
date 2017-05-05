package tr.edu.yildiz.ce.dao;

import java.util.List;

import tr.edu.yildiz.ce.entity.Supporter;
import tr.edu.yildiz.ce.model.SupporterInfo;
import tr.edu.yildiz.ce.model.UserInfo;

public interface SupporterDAO {
	public Supporter findSupporter (Integer id); 
	public void saveSupporter (SupporterInfo supporterInfo);
    public SupporterInfo findSupporterInfo (Integer id);  
    public void deleteSupporter (Integer id);
    
    public List<SupporterInfo> listSupporterInfosById(Integer userId);
    public List<SupporterInfo> listSupporterInfos();
    public List<Integer> listSupporterUserIdBySupportType(Integer supportTypeId);
}
