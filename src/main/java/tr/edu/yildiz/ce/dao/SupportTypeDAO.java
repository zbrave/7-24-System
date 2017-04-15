package tr.edu.yildiz.ce.dao;

import tr.edu.yildiz.ce.entity.SupportType;
import tr.edu.yildiz.ce.model.SupportTypeInfo;

public interface SupportTypeDAO {
	
	public SupportType findSupportType(Integer id); 
	public void saveSupportType (SupportTypeInfo supportTypeInfo);
    public SupportTypeInfo findSupportTypeInfo(Integer id);  
    public void deleteSupportType (Integer id);
}
