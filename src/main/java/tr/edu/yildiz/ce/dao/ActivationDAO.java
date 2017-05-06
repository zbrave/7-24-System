package tr.edu.yildiz.ce.dao;

import tr.edu.yildiz.ce.entity.Activation;

public interface ActivationDAO {
	
	public Activation findActivation(Integer id);
	public Activation findActivationWithUser(Integer id);
	public Activation findActivationWithCode(String code);
	public void saveActivation(Activation act);
	public void deleteActivation(Integer id);
}
