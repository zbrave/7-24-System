package tr.edu.yildiz.ce.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import tr.edu.yildiz.ce.dao.ComplaintDAO;
import tr.edu.yildiz.ce.dao.LocationDAO;
import tr.edu.yildiz.ce.entity.Location;
import tr.edu.yildiz.ce.model.LocationInfo;

public class LocationDAOImpl implements LocationDAO {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private ComplaintDAO complaintDAO;
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
	     if(locationInfo.getParentId()!=locationInfo.getId()){
	    	 location.setParentId(locationInfo.getParentId());
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
        return new LocationInfo(location.getId(), location.getDescription(),location.getParentId());
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
        Criteria crit = session.createCriteria(Location.class);
        crit.add(Restrictions.isNull("parentId"));
		List<Location> locations =(List<Location>) crit.list();
        List<LocationInfo> locationInfos= new ArrayList<LocationInfo>();
        for(Location l :locations ){
        	locationInfos.add((LocationInfo)findLocationInfo(l.getId()));
        }
        return locationInfos;
	}
    
	@SuppressWarnings("unchecked")
	@Override
	public List<LocationInfo> findChildInfos(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Location.class);
        crit.add(Restrictions.eq("parentId", id));
		List<Location> locations =(List<Location>) crit.list();
        List<LocationInfo> locationInfos= new ArrayList<LocationInfo>();
        for(Location l :locations ){
        	locationInfos.add((LocationInfo) findLocationInfo(l.getId()));
        }
        return locationInfos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LocationInfo> listLocationInfos() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Location.class);
		List<Location> locations =(List<Location>) crit.list();
        List<LocationInfo> locationInfos= new ArrayList<LocationInfo>();
        for(Location l :locations ){
        	locationInfos.add((LocationInfo) findLocationInfo(l.getId()));
        }
        return locationInfos;
	}


	@Override
	public List<LocationInfo> findLocationInfoTree(Integer id) {
		List<LocationInfo> locationInfos= new ArrayList<LocationInfo>();
		locationInfos.add(findLocationInfo(id));
		int lenght=locationInfos.size();
		for(int i=0;i<lenght;i++){
			List<LocationInfo> loc= findChildInfos( locationInfos.get(i).getId() ) ;
			if(loc!=null){
				locationInfos.addAll(loc);
			}
			lenght=locationInfos.size();
		}
		return locationInfos;
	}

	@Override
	public List<LocationInfo> listLocationInfoByProximity(Integer id) {
		LocationInfo origin =this.findLocationInfo(id);
		List<LocationInfo> finalTree=new ArrayList<LocationInfo>();
		List<LocationInfo> tree = findLocationInfoTree(id);
		List<LocationInfo> pathUp = new ArrayList<LocationInfo>();
		LocationInfo up=origin;
		while(up.getParentId()!=null){
			up=this.findLocationInfo(up.getParentId());
			pathUp.add(up);
		}
		List<LocationInfo> upTree = findLocationInfoTree(up.getId());
		
		List<Integer> treeIds = new ArrayList<Integer>();
		List<Integer> pathUpIds = new ArrayList<Integer>();
		List<Integer> upTreeIds = new ArrayList<Integer>();
		for(LocationInfo l:tree){
			treeIds.add(l.getId());
		}
		for(LocationInfo l:pathUp){
			pathUpIds.add(l.getId());
		}		
		for(LocationInfo l:upTree){
			upTreeIds.add(l.getId());
		}			
		upTreeIds.removeAll(treeIds);
		upTreeIds.removeAll(pathUpIds);
		for(Integer i:treeIds){
			finalTree.add(this.findLocationInfo(i));
		}
		for(Integer i:pathUpIds){
			finalTree.add(this.findLocationInfo(i));
		}		
		for(Integer i:upTreeIds){
			finalTree.add(this.findLocationInfo(i));
		}
		return finalTree;
	}

	@Override
	public List<LocationInfo> findLocationInfoUpperTree(Integer id) {
		LocationInfo origin =this.findLocationInfo(id);
		LocationInfo up=origin;
		List<LocationInfo> pathUp = new ArrayList<LocationInfo>();
		pathUp.add(origin);
		
		while(up.getParentId()!=null){
			up=this.findLocationInfo(up.getParentId());
			pathUp.add(up);
		}
		return pathUp;
	}

	@Override
	public List<LocationInfo> reportLocationInfos() {
		List<LocationInfo> locationInfos= this.listLocationInfos();
		for(LocationInfo l:locationInfos){
			l.setWaitingAssign(complaintDAO.listWaitingAssingnComplaintInfos(l.getId(),null,null,null).size());
			l.setWaitingAck(complaintDAO.listWaitingAckComplaintInfos(l.getId(),null,null,null).size());
			l.setActive(complaintDAO.listActiveComplaintInfos(l.getId(),null,null,null).size());
			l.setWaitingChild(complaintDAO.listWaitingChildComplaintInfos(l.getId(),null,null,null).size());
			l.setTotal(complaintDAO.listComplaintInfos(l.getId(),null,null,null).size());
			l.setReported(complaintDAO.listReportedComplaintInfos(l.getId(),null,null,null).size());
		}
		return locationInfos;
	}
}
