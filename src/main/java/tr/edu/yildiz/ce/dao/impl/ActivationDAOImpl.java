package tr.edu.yildiz.ce.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import tr.edu.yildiz.ce.dao.ActivationDAO;
import tr.edu.yildiz.ce.entity.Activation;
import tr.edu.yildiz.ce.entity.User;

public class ActivationDAOImpl implements ActivationDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Activation findActivation(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Activation.class);
        crit.add(Restrictions.eq("id", id));
        return (Activation) crit.uniqueResult();
	}
	
	@Override
	public Activation findActivationWithCode(String code) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Activation.class);
        crit.add(Restrictions.eq("code", code));
        return (Activation) crit.uniqueResult();
	}
	
	@Override
	public Activation findActivationWithUser(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Activation.class);
        crit.add(Restrictions.eq("userId", id));
        return (Activation) crit.uniqueResult();
	}
	
	@Override
	public void saveActivation(Activation act) {
		Integer id = act.getId();
		Activation actv = null;
		if( id!=null ){
			actv = this.findActivation(id);
		}
		boolean isNew = false;
		if( actv == null ){
			isNew = true;
			actv = new Activation();
		}
		actv.setId(act.getId());
	    actv.setUsername(act.getUsername());
	    actv.setCode(act.getCode());
	    if(isNew){
	    	Session session=this.sessionFactory.getCurrentSession();
	    	session.persist(actv);
	    }
		
	}

	@Override
	public void deleteActivation(Integer id) {
		Activation act = this.findActivation(id);
		if(act!=null){
			this.sessionFactory.getCurrentSession().delete(act);
		}
	}

}
