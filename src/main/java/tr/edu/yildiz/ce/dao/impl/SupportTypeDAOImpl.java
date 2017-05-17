package tr.edu.yildiz.ce.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import tr.edu.yildiz.ce.dao.ComplaintDAO;
import tr.edu.yildiz.ce.dao.SupportTypeDAO;
import tr.edu.yildiz.ce.entity.SupportType;
import tr.edu.yildiz.ce.entity.UserRole;
import tr.edu.yildiz.ce.model.LocationInfo;
import tr.edu.yildiz.ce.model.SupportTypeInfo;

public class SupportTypeDAOImpl implements SupportTypeDAO {
    @Autowired
    private ComplaintDAO complaintDAO;

	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public SupportType findSupportType(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(SupportType.class);
        crit.add(Restrictions.eq("id", id));
        return (SupportType) crit.uniqueResult();
	}

	@Override
	public void saveSupportType(SupportTypeInfo supportTypeInfo) {
        Integer id = supportTypeInfo.getId();
        SupportType supportType = null;
        if (id != null) {
        	supportType = this.findSupportType(id);
        }
        boolean isNew = false;
        if (supportType == null) {
            isNew = true;
            supportType = new SupportType();
        }
        supportType.setId(supportTypeInfo.getId());
        supportType.setType(supportTypeInfo.getType());
 
        if (isNew) {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(supportType);
        }

	}

	@Override
	public SupportTypeInfo findSupportTypeInfo(Integer id) {
		SupportType supportType = this.findSupportType(id);
        if (supportType == null) {
            return null;
        }
        return new SupportTypeInfo(supportType.getId(), supportType.getType() );
	}

	@Override
	public void deleteSupportType(Integer id) {
		SupportType supportType = this.findSupportType(id);
        if (supportType != null) {
            this.sessionFactory.getCurrentSession().delete(supportType);
        }

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SupportTypeInfo> listSupportTypeInfos() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(SupportType.class);
        List<SupportType> supportTypes=(List<SupportType>)crit.list();
        List<SupportTypeInfo> supportTypeInfos=new ArrayList<SupportTypeInfo>();
        for(SupportType s:supportTypes){
        	supportTypeInfos.add((SupportTypeInfo)findSupportTypeInfo(s.getId()));
        }
        return supportTypeInfos;
	}

	@Override
	public List<SupportTypeInfo> reportSupportTypeInfos() {
		List<SupportTypeInfo> supportTypeInfos= this.listSupportTypeInfos();
		for(SupportTypeInfo s:supportTypeInfos){
			s.setWaitingAssign(complaintDAO.listWaitingAssingnComplaintInfos(null,s.getId(),null,null).size());
			s.setWaitingAck(complaintDAO.listWaitingAckComplaintInfos(null,s.getId(),null,null).size());
			s.setActive(complaintDAO.listActiveComplaintInfos(null,s.getId(),null,null).size());
			s.setWaitingChild(complaintDAO.listWaitingChildComplaintInfos(null,s.getId(),null,null).size());
			s.setTotal(complaintDAO.listComplaintInfos(null,s.getId(),null,null).size());
			s.setReported(complaintDAO.listReportedComplaintInfos(null,s.getId(),null,null).size());
		}
		return supportTypeInfos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SupportTypeInfo> listSupportTypeInfosPagination(Integer offset, Integer maxResults) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(SupportType.class);
        crit.setFirstResult(offset!=null?offset:0);
        crit.setMaxResults(maxResults!=null?maxResults:10);
        List<SupportType> supportTypes=(List<SupportType>)crit.list();
        List<SupportTypeInfo> supportTypeInfos=new ArrayList<SupportTypeInfo>();
        for(SupportType s:supportTypes){
        	supportTypeInfos.add((SupportTypeInfo)findSupportTypeInfo(s.getId()));
        }
        return supportTypeInfos;
	}
	
	@Override
	public Long count() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(SupportType.class);
        crit.setProjection(Projections.rowCount());
        return (Long) crit.uniqueResult();
	}

}
