package tr.edu.yildiz.ce.model;

public class NotificationInfo {
	private Integer id;
	private Integer userId;
	private Integer complaintId;
	
	public NotificationInfo(){
		
	}
	public NotificationInfo(Integer id,Integer userId,Integer complaintId){
		this.id=id;
		this.userId=userId;
		this.complaintId=complaintId;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId;
	}
}
