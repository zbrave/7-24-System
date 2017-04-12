package tr.edu.yildiz.ce.dao;

import tr.edu.yildiz.ce.entity.Location;
import tr.edu.yildiz.ce.model.LocationInfo;

public interface LocationDAO {
	public Location findLocation(int id);
	public void saveLocation (LocationInfo locationInfo);
	public LocationInfo findLocationInfo(int id);
	public void deleteLocation (int id);
}
