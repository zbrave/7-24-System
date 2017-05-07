package tr.edu.yildiz.ce.model;

public class SupportTypeInfo {
	
	private Integer id;
	private String type;
	private Long avgTime;
	private Integer total;
	private Integer wait;
	private Integer active;
	
	public SupportTypeInfo(){
		
	}
	
	public SupportTypeInfo(Integer id,String type){
		this.id=id;
		this.type=type;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public Long getAvgTime() {
		return avgTime;
	}

	public void setAvgTime(Long avgTime) {
		this.avgTime = avgTime;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getWait() {
		return wait;
	}

	public void setWait(Integer wait) {
		this.wait = wait;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}
	
}
