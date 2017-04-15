package tr.edu.yildiz.ce.model;

public class SupporterInfo {
	private Integer id;
	private UserInfo userInfo;
	private SupportTypeInfo supportTypeInfo;
	private LocationInfo locationInfo;
	
	public SupporterInfo(){
		
	}
	public SupporterInfo(Integer id,UserInfo userInfo,SupportTypeInfo supportTypeInfo,LocationInfo locationInfo){
		this.id=id;
		this.userInfo=userInfo;
		this.supportTypeInfo=supportTypeInfo;
		this.locationInfo=locationInfo;
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
	
	public SupportTypeInfo getSupportTypeInfo() {
		return supportTypeInfo;
	}
	public void setSupportTypeInfo(SupportTypeInfo supportTypeInfo) {
		this.supportTypeInfo = supportTypeInfo;
	}
	
	public LocationInfo getLocationInfo() {
		return locationInfo;
	}
	public void setLocationInfo(LocationInfo locationInfo) {
		this.locationInfo = locationInfo;
	}
}
