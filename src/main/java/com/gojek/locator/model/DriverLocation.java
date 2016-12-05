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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + driverId;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DriverLocation other = (DriverLocation) obj;
		if (driverId != other.driverId)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "DriverLocation [driverId=" + driverId + ", location=" + location + "]";
	}
	
	
	
}
