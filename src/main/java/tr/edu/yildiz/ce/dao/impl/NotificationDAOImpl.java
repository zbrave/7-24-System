package tr.edu.yildiz.ce.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import tr.edu.yildiz.ce.dao.NotificationDAO;
import tr.edu.yildiz.ce.entity.Notification;
import tr.edu.yildiz.ce.model.ComplaintInfo;
import tr.edu.yildiz.ce.model.NotificationInfo;
import tr.edu.yildiz.ce.model.UserInfo;

public class NotificationDAOImpl implements NotificationDAO {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private ComplaintDAOImpl complaintDAOImpl;
	@Autowired
	private UserDAOImpl userDAOImpl;
	
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
		notification.setUserId(notificationInfo.getUserInfo().getId());
		notification.setComplaintId(notificationInfo.getComplaintInfo().getId());
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
		return new NotificationInfo(notification.getId() ,userDAOImpl.findUserInfo(notification.getUserId()),complaintDAOImpl.findComplaintInfo(notification.getComplaintId()));
	}

	@Override
	public void deleteNotification(Integer id) {
		Notification notification=this.findNotification(id);
		if(notification!=null){
			this.sessionFactory.getCurrentSession().delete(notification);
		}

	}

}
