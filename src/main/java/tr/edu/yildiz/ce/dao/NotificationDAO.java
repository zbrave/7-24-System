package tr.edu.yildiz.ce.dao;

import tr.edu.yildiz.ce.entity.Notification;
import tr.edu.yildiz.ce.model.NotificationInfo;

public interface NotificationDAO {
	public Notification findNotification (Integer id); 
	public void saveNotification (NotificationInfo notificationInfo);
    public NotificationInfo findNotificationInfo (Integer id);  
    public void deleteNotification (Integer id);
}
