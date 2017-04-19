package tr.edu.yildiz.ce.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import tr.edu.yildiz.ce.dao.ComplaintDAO;
import tr.edu.yildiz.ce.dao.NotificationDAO;
import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.entity.Notification;
import tr.edu.yildiz.ce.model.NotificationInfo;

public class NotificationDAOImpl implements NotificationDAO {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private ComplaintDAO complaintDAO;
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public Notification findNotification(Integer id) {
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Notification.class);
        crit.add(Restrictions.eq("id", id));
        return (Notification) crit.uniqueResult();
	}

	@Override
	public void saveNotification(NotificationInfo notificationInfo) {
		Integer id=notificationInfo.getId();
		Notification notification = null;
		if (id != null){
			notification = this.findNotification(id);
		}
		boolean isNew = false;
		if(notification==null){
			isNew = true;
			notification = new Notification();
		}
		notification.setId(notificationInfo.getId());
		if(notificationInfo.getUserInfo()!=null){
			notification.setUserId(notificationInfo.getUserInfo().getId());
		}else{
			notification.setUserId(notificationInfo.getUserId());
		}
		if(notificationInfo.getComplaintInfo()!=null){
			notification.setComplaintId(notificationInfo.getComplaintInfo().getId());
		}else{
			notification.setComplaintId(notificationInfo.getComplaintId());
		}
        if (isNew) {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(notification);
        }
	}

	@Override
	public NotificationInfo findNotificationInfo(Integer id) {
		Notification notification=this.findNotification(id);
		if(notification==null){
			 return null;
		}
		return new NotificationInfo(notification.getId() ,userDAO.findUserInfo(notification.getUserId()),complaintDAO.findComplaintInfo(notification.getComplaintId()));
	}

	@Override
	public void deleteNotification(Integer id) {
		Notification notification=this.findNotification(id);
		if(notification!=null){
			this.sessionFactory.getCurrentSession().delete(notification);
		}
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NotificationInfo> listNotificationInfos() {
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Notification.class);
        List<Notification> notifications =(List<Notification>)crit.list();
        List<NotificationInfo> notificationInfos =new ArrayList<NotificationInfo>();
        for(Notification n:notifications){
        	notificationInfos.add((NotificationInfo)findNotificationInfo(n.getId()));
        }
        return notificationInfos;
	}

}
