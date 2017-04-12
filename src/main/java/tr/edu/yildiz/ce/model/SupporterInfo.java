package tr.edu.yildiz.ce.model;

public class SupporterInfo {
	private UserInfo userInfo;
	private SupportTypeInfo supportTypeInfo;
	private LocationInfo locationInfo;
	
	public SupporterInfo(){
		
	}
	public SupporterInfo(UserInfo userInfo,SupportTypeInfo supportTypeInfo,LocationInfo locationInfo){
		this.userInfo=userInfo;
		this.supportTypeInfo=supportTypeInfo;
		this.locationInfo=locationInfo;
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
