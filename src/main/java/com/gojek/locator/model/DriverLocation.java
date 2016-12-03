package com.gojek.locator.model;

public class DriverLocation {

	int driverId;
	Location location;
	public int getDriverId() {
		return driverId;
	}
	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public DriverLocation(int driverId, Location location) {
		super();
		this.driverId = driverId;
		this.location = location;
	}
	
	
}
