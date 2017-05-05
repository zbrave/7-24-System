package tr.edu.yildiz.ce.dao.impl;
 
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.model.UserInfo;
import tr.edu.yildiz.ce.entity.User;
 
@Service
@Transactional
public class UserDAOImpl implements UserDAO {
 
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User findUser(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(User.class);
        crit.add(Restrictions.eq("id", id));
        return (User) crit.uniqueResult();
	}

	@Override
	public void saveUser(UserInfo userInfo) {
		Integer id = userInfo.getId();
		User user = null;
		if( id!=null ){
			user = this.findUser(id);
		}
		boolean isNew = false;
		if( user == null ){
			isNew = true;
			user = new User();
		}
		user.setId(userInfo.getId());
	    user.setEmail(userInfo.getEmail());
	    user.setUsername(userInfo.getUsername());
	    user.setPassword(userInfo.getPassword());
	    user.setEnabled(userInfo.isEnabled());
	    if(isNew){
	    	Session session=this.sessionFactory.getCurrentSession();
	    	session.persist(user);
	    }
	}

	@Override
	public UserInfo findUserInfo(Integer id) {
		User user = this.findUser(id);
		if(user==null){
			return null;
		}
		return new UserInfo(user.getId(),user.getEmail(),user.getUsername(),user.getPassword(),user.isEnabled());
	}

	@Override
	public void deleteUser(Integer id) {
		User user = this.findUser(id);
		if(user!=null){
			this.sessionFactory.getCurrentSession().delete(user);
		}
		
	}

	@Override
	public User findLoginUser(String username) {
    	Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(User.class);
        crit.add(Restrictions.eq("username", username));
        return (User) crit.uniqueResult();
	}

	@Override
	public UserInfo findLoginUserInfo(String username) {
		User user = this.findLoginUser(username);
        if (user == null) {
            return null;
        }
        return new UserInfo(user.getId(),user.getEmail(),user.getUsername(),user.getPassword(),user.isEnabled());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfos() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(User.class);
        List<User> users =(List<User>) crit.list();
        List<UserInfo> userInfos=new ArrayList<UserInfo>();
        for(User u : users){
        	userInfos.add((UserInfo)findUserInfo(u.getId()));
        }
        return userInfos;
	}

}