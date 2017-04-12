package tr.edu.yildiz.ce.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "supporters")
public class Supporter {
	private int userInfoId;
	private int supportTypeInfoId;
	private int locationInfoId;

	@Column(name = "id", nullable = false)
	public int getUserInfoId() {
		return userInfoId;
	}
	public void setUserInfoId(int userInfoId) {
		this.userInfoId = userInfoId;
	}
	@Column(name = "support_type_id", nullable = false)
	public int getSupportTypeInfoId() {
		return supportTypeInfoId;
	}
	public void setSupportTypeInfoId(int supportTypeInfoId) {
		this.supportTypeInfoId = supportTypeInfoId;
	}
	@Column(name = "location_id", nullable = false)
	public int getLocationInfoId() {
		return locationInfoId;
	}
	public void setLocationInfoId(int locationInfoId) {
		this.locationInfoId = locationInfoId;
	}
}
