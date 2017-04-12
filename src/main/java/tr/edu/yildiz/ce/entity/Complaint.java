package tr.edu.yildiz.ce.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "complaints")
public class Complaint {
	private int id;
	private int locationId;
	private int supportTypeId;
	private int parentId;
	
	private Date complaintTime;
	private String complaintText;
	
	private int supporterId;
	private Date responseTime;
	private String responseText;
	private int childId;
	
	@Id
	@Column(name = "id", nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "location_id", nullable = false)
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	@Column(name = "support_type_id", nullable = false)
	public int getSupportTypeId() {
		return supportTypeId;
	}
	public void setSupportTypeId(int supportTypeId) {
		this.supportTypeId = supportTypeId;
	}	
	
	@Column(name = "parent_id")
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	@Column(name = "complaint_time", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getComplaintTime(){
		return complaintTime;
	}
	public void setComplaintTime(Date complaintTime){
		this.complaintTime=complaintTime;
	}
	
	@Column(name = "complaint_text", length = 50)
	public String getComplaintText(){
		return complaintText;
	}
	public void setComplaintText(String complaintText){
		this.complaintText=complaintText;
	}
	
	@Column(name = "supporter_id")
	public int getSupporterId() {
		return supporterId;
	}
	public void setSupporterId(int supporterId) {
		this.supporterId = supporterId;
	}
	
	@Column(name = "response_time", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getResponseTime(){
		return responseTime;
	}
	public void setResponseTime(Date responseTime){
		this.responseTime=responseTime;
	}		
	
	@Column(name = "response_text", length = 50)
	public String getResponseText(){
		return responseText;
	}
	public void setResponseText(String responseText){
		this.responseText=responseText;
	}
	
	@Column(name = "child_id")
	public int getChildId() {
		return childId;
	}
	public void setChildId(int childId) {
		this.childId = childId;
	}
}
