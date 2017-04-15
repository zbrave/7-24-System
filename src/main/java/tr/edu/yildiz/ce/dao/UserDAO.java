package tr.edu.yildiz.ce.dao;

import tr.edu.yildiz.ce.entity.User;
import tr.edu.yildiz.ce.model.UserInfo;
 
public interface UserDAO {
	
	public User findUser (Integer id); 
	public void saveUser (UserInfo userInfo);
    public UserInfo findUserInfo (Integer id);  
    public void deleteUser (Integer id);
    
    public User findLoginUser(String username);
    public UserInfo findLoginUserInfo(String username);
    
}