package tr.edu.yildiz.ce.model;

public class LocationInfo {
	
	private Integer id;
	private String description;
	private Integer parentId;
	private LocationInfo parent;
	private Integer total;
	private Integer wait;
	private Integer active;
	public LocationInfo(){
		
	}
	public LocationInfo(Integer id,String description,Integer parentId){
		this.id=id;
		this.description=description;
		this.parentId=parentId;
	}	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public LocationInfo getParent() {
		return parent;
	}
	public void setParent(LocationInfo parent) {
		this.parent = parent;
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
