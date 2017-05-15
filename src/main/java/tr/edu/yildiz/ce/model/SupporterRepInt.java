package tr.edu.yildiz.ce.model;

public class SupporterRepInt {
	private UserInfo userInfo;
	private LocationInfo locationInfo;
	private SupportTypeInfo supportTypeInfo;
	private Integer comps;
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
	public Integer getComps() {
		return comps;
	}
	public void setComps(Integer comps) {
		this.comps = comps;
	}
	public SupporterRepInt(LocationInfo locationInfo, SupportTypeInfo supportTypeInfo, Integer comps) {
		super();
		this.locationInfo = locationInfo;
		this.supportTypeInfo = supportTypeInfo;
		this.comps = comps;
	}
	public SupporterRepInt() {
		super();
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
}
