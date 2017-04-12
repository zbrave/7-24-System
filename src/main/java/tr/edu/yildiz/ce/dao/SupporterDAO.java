package tr.edu.yildiz.ce.dao;

import tr.edu.yildiz.ce.entity.Supporter;
import tr.edu.yildiz.ce.model.SupporterInfo;

public interface SupporterDAO {
	public Supporter findSupporter(int id);
	public void saveSupporter (SupporterInfo supporterInfo);
	public SupporterInfo findSupporterInfo(int id);
	public void deleteSupporter (int id);
}
