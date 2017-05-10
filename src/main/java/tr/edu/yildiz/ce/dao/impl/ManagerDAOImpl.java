package tr.edu.yildiz.ce.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import tr.edu.yildiz.ce.dao.ManagerDAO;
import tr.edu.yildiz.ce.entity.Manager;
import tr.edu.yildiz.ce.entity.Supporter;
import tr.edu.yildiz.ce.model.ManagerInfo;
import tr.edu.yildiz.ce.model.SupporterInfo;

public class ManagerDAOImpl implements ManagerDAO {
	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public Manager findManager(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Manager.class);
        crit.add(Restrictions.eq("id", id));
        return (Manager) crit.uniqueResult();
	}

	@Override
	public void saveManager(ManagerInfo managerInfo) {
       	Integer id = managerInfo.getId();
       	Manager manager = null;
        if (id != null) {
        	manager = this.findManager(id);
        }
        boolean isNew = false;
        if (manager == null) {
            isNew = true;
            manager = new Manager();
        }
        manager.setId(managerInfo.getId());
        manager.setUserId(managerInfo.getUserId());
        manager.setLocationId(managerInfo.getLocationId()); 
        if (isNew) {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(manager);
        }
	}

	@Override
	public ManagerInfo findManagerInfo(Integer id) {
		Manager manager = this.findManager(id);
        if (manager == null) {
            return null;
        }
        return new ManagerInfo(manager.getId(),manager.getUserId(),manager.getLocationId());
	}

	@Override
	public void deleteManager(Integer id) {
		Manager manager = this.findManager(id);
        if (manager != null) {
            this.sessionFactory.getCurrentSession().delete(manager);
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ManagerInfo> listManagerInfosById(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Manager.class);
        crit.add(Restrictions.eq("userId", userId));
        List<Manager> managers=(List<Manager>) crit.list();
        List<ManagerInfo> managerInfos= new ArrayList<ManagerInfo>();
        for(Manager m: managers){
        	managerInfos.add((ManagerInfo)findManagerInfo(m.getId()));
        }
        return managerInfos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ManagerInfo> listManagerInfos() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Manager.class);
        List<Manager> managers=(List<Manager>) crit.list();
        List<ManagerInfo> managerInfos= new ArrayList<ManagerInfo>();
        for(Manager m: managers){
        	managerInfos.add((ManagerInfo)findManagerInfo(m.getId()));
        }
        return managerInfos;
	}

}
