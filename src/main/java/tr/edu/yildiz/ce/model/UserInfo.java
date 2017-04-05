package tr.edu.yildiz.ce.model;
 
public class UserInfo {
	
    private String username;
    private String password;
    private Integer stdId;
    private boolean enabled;
	
    public UserInfo() {
    	
    }
    
    public UserInfo(String username, String password, Integer stdId, boolean enabled) {
		super();
		this.username = username;
		this.password = password;
		this.stdId = stdId;
		this.enabled = enabled;
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

	public Integer getStdId() {
		return stdId;
	}

	public void setStdId(Integer stdId) {
		this.stdId = stdId;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
     
    
 
}