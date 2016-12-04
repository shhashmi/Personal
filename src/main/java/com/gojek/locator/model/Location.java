package com.gojek.locator.model;

public class Location {

	private Float latitude;
	private Float longitude;
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	public Location(Float latitude, Float longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public int diff(Location location) {
		
		return  new Double(Math.sqrt(Math.pow(location.getLatitude()-this.getLatitude(), 2)+Math.pow(location.getLongitude()-this.getLongitude(), 2))).intValue();
	}
}
