package tr.edu.yildiz.ce.model;

public class NotificationInfo {
	private Integer id;
	private UserInfo userInfo;
	private ComplaintInfo complaintInfo;
	public NotificationInfo(){
		
	}
	public NotificationInfo(Integer id,UserInfo userInfo,ComplaintInfo complaintInfo){
		this.id=id;
		this.userInfo=userInfo;
		this.complaintInfo=complaintInfo;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public ComplaintInfo getComplaintInfo() {
		return complaintInfo;
	}
	public void setComplaintInfo(ComplaintInfo complaintInfo) {
		this.complaintInfo = complaintInfo;
	}
}
