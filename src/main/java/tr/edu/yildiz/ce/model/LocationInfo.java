package tr.edu.yildiz.ce.model;

public class LocationInfo {
	private int id;
	private String description;
	private LocationInfo parent;
	
	public LocationInfo(){
		
	}
	public LocationInfo(int id,String description,LocationInfo parent){
		this.id=id;
		this.description=description;
		this.parent=parent;
	}	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public LocationInfo getParent() {
		return parent;
	}
	public void setParrent(LocationInfo parent) {
		this.parent = parent;
	}
	
}
