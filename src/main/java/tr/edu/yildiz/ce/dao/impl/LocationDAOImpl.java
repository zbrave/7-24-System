package tr.edu.yildiz.ce.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import tr.edu.yildiz.ce.dao.LocationDAO;
import tr.edu.yildiz.ce.entity.Location;
import tr.edu.yildiz.ce.model.LocationInfo;

public class LocationDAOImpl implements LocationDAO {

    @Autowired
    private SessionFactory sessionFactory;
    
	@Override
	public Location findLocation(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Location.class);
        crit.add(Restrictions.eq("id", id));
        return (Location) crit.uniqueResult();
	}

	@Override
	public void saveLocation(LocationInfo locationInfo) {
		 Integer id = locationInfo.getId();
	     Location location = null;
	     if (id != null) {
	      	location = this.findLocation(id);
	     }
	     boolean isNew = false;
	     if (location == null) {
	    	 isNew = true;
	         location = new Location();
	     }
	     location.setId(locationInfo.getId());
	     location.setDescription(locationInfo.getDescription());
	     location.setParentId(null);
	     if(locationInfo.getParent()!=null){
	    	 location.setParentId(locationInfo.getParent().getId());
	     }
	 
	     if (isNew) {
	         Session session = this.sessionFactory.getCurrentSession();
	         session.persist(location);
	     }

	}

	@Override
	public LocationInfo findLocationInfo(Integer id) {
		Location location = this.findLocation(id);
        if (location == null) {
            return null;
        }
        return new LocationInfo(location.getId(), location.getDescription(), findLocationInfo( location.getParentId() ) );
	}

	@Override
	public void deleteLocation(Integer id) {
		Location location = this.findLocation(id);
        if (location != null) {
            this.sessionFactory.getCurrentSession().delete(location);
        }

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LocationInfo> findParents() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(LocationInfo.class);
        crit.add(Restrictions.eq("parentId", null));
		List<Location> locations =(List<Location>) crit.list();
        List<LocationInfo> locationInfos= new ArrayList<LocationInfo>();
        for(Location l :locations ){
        	locationInfos.add(findLocationInfo(l.getId()));
        }
        return locationInfos;
	}
    
	@SuppressWarnings("unchecked")
	@Override
	public List<LocationInfo> findChilds(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Location.class);
        crit.add(Restrictions.eq("parentId", id));
		List<Location> locations =(List<Location>) crit.list();
        List<LocationInfo> locationInfos= new ArrayList<LocationInfo>();
        for(Location l :locations ){
        	locationInfos.add(findLocationInfo(l.getId()));
        }
        return locationInfos;
	}

}
