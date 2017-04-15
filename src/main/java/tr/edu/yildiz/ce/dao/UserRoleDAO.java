package tr.edu.yildiz.ce.dao;

import java.util.List;

import tr.edu.yildiz.ce.entity.UserRole;
import tr.edu.yildiz.ce.model.UserRoleInfo;

public interface UserRoleDAO {
	
	public UserRole findUserRole (Integer id);
	public void saveUserRole (UserRoleInfo userRoleInfo );
	public UserRoleInfo findUserRoleInfo (Integer id);
	public void deleteUserRole(Integer id);
	
	public List<String> getUserRoles(Integer userId);
}
