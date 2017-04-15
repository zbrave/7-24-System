package tr.edu.yildiz.ce.model;

import java.util.Date;



public class ComplaintInfo{
	private Integer id;
	private LocationInfo locationInfo;
	private SupportTypeInfo supportTypeInfo;
	private ComplaintInfo parentInfo;
	private UserInfo complainantUserInfo;

	
	private Date complaintTime;
	private String complaintText;
	
	private UserInfo supportUserInfo;
	private Date responseTime;
	private String responseText;
	private ComplaintInfo childInfo;
	
	public ComplaintInfo(){
		
	}
	public ComplaintInfo(Integer id,LocationInfo locationInfo,SupportTypeInfo supportTypeInfo,ComplaintInfo parentInfo,UserInfo complainantUserInfo,Date complaintTime,
			String complaintText,UserInfo supportUserInfo,Date responseTime,String responseText,ComplaintInfo childInfo){
		this.id=id;
		this.locationInfo=locationInfo;
		this.supportTypeInfo=supportTypeInfo;
		this.parentInfo=parentInfo;
		this.complainantUserInfo=complainantUserInfo;
		this.complaintTime=complaintTime;
		this.complaintText=complaintText;
		this.supportUserInfo=supportUserInfo;
		this.responseTime=responseTime;
		this.responseText=responseText;
		this.childInfo=childInfo;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public LocationInfo getLocationInfo() {
		return locationInfo;
	}
	public void setLocationInfo(LocationInfo locationInfo) {
		this.locationInfo = locationInfo;
	}
	
	public SupportTypeInfo getSupportTypeInfo() {
		return supportTypeInfo;
	}
	public void setSupportTypeInfo(SupportTypeInfo supportTypeInfo) {
		this.supportTypeInfo = supportTypeInfo;
	}
	
	public ComplaintInfo getParentInfo() {
		return parentInfo;
	}
	public void setParentInfo(ComplaintInfo parentInfo) {
		this.parentInfo = parentInfo;
	}
	
	public UserInfo  getComplainantUserInfo() {
		return complainantUserInfo;
	}
	public void setComplainantUserInfo(UserInfo complainantUserInfo) {
		this.complainantUserInfo = complainantUserInfo;
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
	
	public UserInfo  getSupportUserInfo() {
		return supportUserInfo;
	}
	public void setSupportUserInfo(UserInfo supportUserInfo) {
		this.supportUserInfo = supportUserInfo;
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
	
	public ComplaintInfo getChildInfo() {
		return childInfo;
	}
	public void setChildInfo(ComplaintInfo childInfo) {
		this.childInfo = childInfo;
	}
}
