package tr.edu.yildiz.ce.model;

import java.util.Date;



public class ComplaintInfo{
	private int id;
	private LocationInfo locationInfo;
	private SupportTypeInfo supportTypeInfo;
	private ComplaintInfo parentInfo;
	
	private Date complaintTime;
	private String complaintText;
	
	private SupporterInfo supporterInfo;
	private Date responseTime;
	private String responseText;
	private ComplaintInfo childInfo;
	
	public ComplaintInfo(){
		
	}
	public ComplaintInfo(int id,LocationInfo locationInfo,SupportTypeInfo supportTypeInfo,ComplaintInfo parentInfo,Date complaintTime,
			String complaintText,SupporterInfo supporterInfo,Date responseTime,String responseText,ComplaintInfo childInfo){
		this.id=id;
		this.locationInfo=locationInfo;
		this.supportTypeInfo=supportTypeInfo;
		this.parentInfo=parentInfo;
		this.complaintTime=complaintTime;
		this.complaintText=complaintText;
		this.supporterInfo=supporterInfo;
		this.responseTime=responseTime;
		this.responseText=responseText;
		this.childInfo=childInfo;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	
	public SupporterInfo getSupporterInfo() {
		return supporterInfo;
	}
	public void setSupporterInfo(SupporterInfo supporterInfo) {
		this.supporterInfo = supporterInfo;
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
