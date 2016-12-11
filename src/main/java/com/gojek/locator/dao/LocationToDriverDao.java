package com.gojek.locator.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import com.gojek.locator.model.DriverLocation;
import com.gojek.locator.model.Location;

public interface LocationToDriverDao {

	void removeDriver(DriverLocation driverLocation);

	Map<DriverLocation,Integer> getMaxDriverCountInRange(Location searchLocation, int distance, int limit);

	Set<Integer> getDrivers(Location location);

	void addToCache(int driverId, Location location);

}