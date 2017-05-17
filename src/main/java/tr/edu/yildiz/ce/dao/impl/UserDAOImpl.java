package tr.edu.yildiz.ce.dao.impl;
 
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tr.edu.yildiz.ce.dao.MailSend;
import tr.edu.yildiz.ce.dao.SupporterDAO;
import tr.edu.yildiz.ce.dao.UserRoleDAO;

import tr.edu.yildiz.ce.entity.Activation;
import tr.edu.yildiz.ce.dao.ActivationDAO;
import tr.edu.yildiz.ce.dao.ComplaintDAO;
import tr.edu.yildiz.ce.dao.LocationDAO;
import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.model.ComplaintInfo;
import tr.edu.yildiz.ce.model.LocationInfo;
import tr.edu.yildiz.ce.model.SupporterInfo;
import tr.edu.yildiz.ce.model.UserInfo;
import tr.edu.yildiz.ce.entity.User;
 
@Service
@Transactional
public class UserDAOImpl implements UserDAO {
	@Autowired
	private LocationDAO locationDAO;
	
	@Autowired
	private SupporterDAO supporterDAO;
	
	@Autowired
	private ComplaintDAO complaintDAO;
	
	@Autowired
	private UserRoleDAO userRoleDAO;
	
	@Autowired 
	private ActivationDAO activationDAO;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private MailSend mailSend;

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
			user.setEnabled(false);
			Activation act = new Activation();
			act.setUsername(userInfo.getUsername());
		    act.setCode(getSaltString());
		    act.setRecordDate(new Date());
		    activationDAO.saveActivation(act);
		    String text = "IYS hesabını aktif etmek için aşağıdaki linke tıklayın.\n\n";
		    text = text.concat("http://localhost:8080/sysprog/activate?code="+act.getCode().toString());
		    mailSend.sendSimpleMessage(userInfo.getEmail(), "IYS Aktivasyon", text);
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
	
	protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

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

	@Override
	public List<UserInfo> listUserInfosForAssignment(Integer complaintId) {
		ComplaintInfo c=complaintDAO.findComplaintInfo(complaintId);
		List<SupporterInfo> supporterInfos=supporterDAO.listSupporterInfosBySupportType(c.getSupportTypeId());
		List<LocationInfo> pathUp = locationDAO.findLocationInfoUpperTree(c.getLocationId());
		List<Integer> pathUpIds = new ArrayList<Integer>();
		for(LocationInfo l:pathUp){
			pathUpIds.add(l.getId());
		}		
		List<UserInfo> userInfos=new ArrayList<UserInfo>();
		List<Integer> userIds=new ArrayList<Integer>();
        for(SupporterInfo s: supporterInfos){
        	if(pathUpIds.contains(s.getLocationId())&&!userIds.contains(s.getUserId())){
        		userIds.add(s.getUserId());
        		userInfos.add(this.findUserInfo(s.getUserId()));
        	}
        }
		return userInfos;		
	}

	@Override
	public List<UserInfo> reportSupportUserInfos() {
		List<UserInfo> userInfos = supporterDAO.listSupporterUserInfos();
		for(UserInfo u:userInfos){
			long totalAwarenessTime=0;
			long numAwarenessTime=0;
			long totalResponseTime=0;
			long numResponseTime=0;
			Integer waitingAck=0;
			Integer active=0;
			Integer waitingChild=0;
			Integer total=0;
			Integer reported=0;
			List<ComplaintInfo> complaintInfos =complaintDAO.listComplaintInfos(null, null, null, u.getId());
			total=complaintInfos.size();
			for(ComplaintInfo c:complaintInfos){
				if(c.getAssignTime()!=null&&c.getAckTime()!=null){
					totalAwarenessTime+=c.getAckTime().getTime()-c.getAssignTime().getTime();
					if(c.getAckTime().getTime()-c.getAssignTime().getTime()!=0){
						numAwarenessTime++;
					}
				}
				if(c.getAckTime()!=null&&c.getResponseTime()!=null){
					totalResponseTime+=c.getResponseTime().getTime()-c.getAckTime().getTime();
					if(c.getResponseTime().getTime()-c.getAckTime().getTime()!=0){
						numResponseTime++;	
					}
				}
				if(c.isAck()==false&&c.getSupportUserId()!=null){
					waitingAck++;
				}
				if(c.isAck()==true&&c.isEnded()==false&&c.getChildId()==null&&c.isReported()==false){
					active++;
				}
				if(c.isAck()==true&&c.isEnded()==false&&c.getChildId()!=null&&c.isReported()==false){
					waitingChild++;
				}
				if(c.isReported()==true){
					reported++;
				}
			}
			u.setWaitingAck(waitingAck);
			u.setActive(active);
			u.setWaitingChild(waitingChild);
			u.setReported(reported);
			u.setTotal(total);
			if(numAwarenessTime!=0){
				u.setAvgAwarenessTime(totalAwarenessTime/numAwarenessTime);
			}
			if(numResponseTime!=0){
				u.setAvgResponseTime(totalResponseTime/numResponseTime);
			}
		}
		return userInfos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> listUserInfosPagination(Integer offset, Integer maxResults) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(User.class);
        crit.setFirstResult(offset!=null?offset:0);
        crit.setMaxResults(maxResults!=null?maxResults:10);
        List<User> users =(List<User>) crit.list();
        List<UserInfo> userInfos=new ArrayList<UserInfo>();
        for(User u : users){
        	userInfos.add((UserInfo)findUserInfo(u.getId()));
        }
        return userInfos;
	}
	
	@Override
	public Long count() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(User.class);
        crit.setProjection(Projections.rowCount());
        return (Long) crit.uniqueResult();
	}
}