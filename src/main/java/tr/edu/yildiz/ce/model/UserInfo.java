package tr.edu.yildiz.ce.model;
 
public class UserInfo {
	
	private Integer id;
	private String email;
    private String username;
    private String password;
    private String passwordConf;
    private boolean enabled;
	
    public UserInfo() {
    	
    }
    
    public UserInfo(Integer id,String email,String username, String password, boolean enabled) {
    	this.id=id;
    	this.email=email;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPasswordConf() {
		return passwordConf;
	}

	public void setPasswordConf(String passwordConf) {
		this.passwordConf = passwordConf;
	}
     
    
 
}