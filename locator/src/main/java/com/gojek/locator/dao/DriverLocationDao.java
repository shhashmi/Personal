package com.gojek.locator.dao;

import com.gojek.locator.model.Location;

public interface DriverLocationDao {

	Location getLocation(Integer driverId);

	void updateLocation(Integer driverId, Location location);

}