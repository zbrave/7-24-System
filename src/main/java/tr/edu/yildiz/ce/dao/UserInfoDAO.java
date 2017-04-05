package tr.edu.yildiz.ce.dao;
 
import java.util.List;

import tr.edu.yildiz.ce.model.UserInfo;
 
public interface UserInfoDAO {
     
    public UserInfo findUserInfo(String userName);
     
    // [USER,AMIN,..]
    public List<String> getUserRoles(String userName);
     
}