package tr.edu.yildiz.ce.model;

public class SupporterInfo {
	private Integer id;
	private Integer userId;
	private Integer supportTypeId;
	private Integer locationId;



	
	public SupporterInfo(){
		
	}
	public SupporterInfo(Integer id,Integer userId,Integer supportTypeId,Integer locationId){
		this.id=id;
		this.userId=userId;
		this.supportTypeId=supportTypeId;
		this.locationId=locationId;
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
	
	public Integer getSupportTypeId() {
		return supportTypeId;
	}
	public void setSupportTypeId(Integer supportTypeId) {
		this.supportTypeId = supportTypeId;
	}
	
	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}



}
