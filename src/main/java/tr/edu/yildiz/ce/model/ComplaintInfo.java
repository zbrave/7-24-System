package tr.edu.yildiz.ce.model;

import java.util.Date;



public class ComplaintInfo{
	private Integer id;
	
	private Integer locationId;
	private Integer supportTypeId;
	private Integer parentId;
	private Integer complainantUserId;
	
	private Date complaintTime;
	private String complaintText;
	
	private Integer supportUserId;
	
	private Date responseTime;
	private String responseText;
	
	private Integer childId;
	
	private boolean ended;
	
	public ComplaintInfo(){
		
	}
	public ComplaintInfo(Integer id,Integer locationId,Integer supportTypeId,Integer parentId,Integer complainantUserId,Date complaintTime,
			String complaintText,Integer supportUserId,Date responseTime,String responseText,Integer childId,boolean ended){
		this.id=id;
		this.locationId=locationId;
		this.supportTypeId=supportTypeId;
		this.parentId=parentId;
		this.complainantUserId=complainantUserId;
		this.complaintTime=complaintTime;
		this.complaintText=complaintText;
		this.supportUserId=supportUserId;
		this.responseTime=responseTime;
		this.responseText=responseText;
		this.childId=childId;
		this.ended=ended;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	
	public Integer getSupportTypeId() {
		return supportTypeId;
	}
	public void setSupportTypeId(Integer supportTypeId) {
		this.supportTypeId = supportTypeId;
	}
	
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public Integer getComplainantUserId() {
		return complainantUserId;
	}
	public void setComplainantUserId(Integer complainantUserId) {
		this.complainantUserId = complainantUserId;
	}
	
	public Date getComplaintTime() {
		return complaintTime;
	}
	public void setComplaintTime(Date complaintTime) {
		this.complaintTime = complaintTime;
	}
	
	public String getComplaintText() {
		return complaintText;
	}
	public void setComplaintText(String complaintText) {
		this.complaintText = complaintText;
	}
	

	public Integer getSupportUserId() {
		return supportUserId;
	}
	public void setSupportUserId(Integer supportUserId) {
		this.supportUserId = supportUserId;
	}
	
	public Date getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}
	
	public String getResponseText() {
		return responseText;
	}
	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}
	
	public Integer getChildId() {
		return childId;
	}
	public void setChildId(Integer childId) {
		this.childId = childId;
	}
	
	public boolean isEnded() {
		return ended;
	}
	public void setEnded(boolean ended) {
		this.ended = ended;
	}






}
