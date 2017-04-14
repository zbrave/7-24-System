package tr.edu.yildiz.ce.dao.impl;
 
import java.util.List;
 
import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tr.edu.yildiz.ce.dao.UserInfoDAO;
import tr.edu.yildiz.ce.model.UserInfo;
import tr.edu.yildiz.ce.entity.User;
 
@Service
@Transactional
public class UserInfoDAOImpl extends JdbcDaoSupport implements UserInfoDAO {
 
	@Autowired
	private SessionFactory sessionFactory;
	
    @Autowired
    public UserInfoDAOImpl(DataSource dataSource) {
        this.setDataSource(dataSource);
    }
    
    @Override
    public User findUser(String userName) {
    	Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(User.class);
        crit.add(Restrictions.eq("username", userName));
        return (User) crit.uniqueResult();
    }
    
    @Override
	public UserInfo findUserInfo(String username) {
		User user = this.findUser(username);
        if (user == null) {
            return null;
        }
        return new UserInfo(user.getUsername(), user.getPassword(), user.getEnabled());
	}
    
	public UserInfo findUserInfoById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(UserInfo.class);
        crit.add(Restrictions.eq("std_id", id));
        return (UserInfo) crit.uniqueResult();
	}
 
 
    @Override
    public List<String> getUserRoles(String userName) {
        String sql = "Select r.user_role "//
                + " from user_roles r where r.user_username = ? ";
         
        Object[] params = new Object[] { userName };
         
        List<String> roles = this.getJdbcTemplate().queryForList(sql,params, String.class);
         
        return roles;
    }

}