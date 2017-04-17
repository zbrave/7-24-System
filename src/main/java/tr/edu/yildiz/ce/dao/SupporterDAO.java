package tr.edu.yildiz.ce.dao;

import java.util.List;

import tr.edu.yildiz.ce.model.SupportTypeInfo;
import tr.edu.yildiz.ce.entity.Supporter;
import tr.edu.yildiz.ce.model.SupporterInfo;

public interface SupporterDAO {
	public Supporter findSupporter (Integer id); 
	public void saveSupporter (SupporterInfo supporterInfo);
    public SupporterInfo findSupporterInfo (Integer id);  
    public void deleteSupporter (Integer id);
    
    public List<SupportTypeInfo> getSupportTypes(Integer userId);
}
