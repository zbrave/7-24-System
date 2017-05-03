package tr.edu.yildiz.ce.model;

public class LocationInfo {
	
	private Integer id;
	private String description;
	private Integer parentId;
	
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
	
}
