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
	private boolean ended;
	
	private Integer locationId;
	private Integer supportTypeId;
	private Integer parentId;
	private Integer complainantUserId;
	private Integer supportUserId;
	private Integer childId;
	
	public ComplaintInfo(){
		
	}
	public ComplaintInfo(Integer id,LocationInfo locationInfo,SupportTypeInfo supportTypeInfo,ComplaintInfo parentInfo,UserInfo complainantUserInfo,Date complaintTime,
			String complaintText,UserInfo supportUserInfo,Date responseTime,String responseText,ComplaintInfo childInfo,boolean ended){

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
		this.ended=ended;
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

	public boolean isEnded() {
		return ended;
	}
	public void setEnded(boolean ended) {
		this.ended = ended;
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
	public Integer getChildId() {
		return childId;
	}
	public void setChildId(Integer childId) {
		this.childId = childId;
	}
	public Integer getSupportUserId() {
		return supportUserId;
	}
	public void setSupportUserId(Integer supportUserId) {
		this.supportUserId = supportUserId;
	}

}
