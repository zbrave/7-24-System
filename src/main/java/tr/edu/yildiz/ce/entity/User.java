package tr.edu.yildiz.ce.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "user")
public class User implements Serializable{
	
	@Id
	@Column(name = "username", length = 45, nullable = false)
	private String username;
	
	@Column(name = "password", length = 60, nullable = false)
	private String password;
	
	@Column(name = "enabled", nullable = false)
	private Boolean enabled;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
}
