package com.gojek.locator.dao;

import java.util.Map;
import java.util.Set;

import com.gojek.locator.model.DriverLocation;
import com.gojek.locator.model.Location;

public interface LocationToDriverDao {

	
	/** removes a driver from the given position 
	 * @param driverLocation
	 */
	void removeDriver(DriverLocation driverLocation);

	/**
	 * Given a locaiton, this API retrieves the map of DriverLocaiton and its distance from the given location.
	 * @param searchLocation : location around which drivers are to be searched
	 * @param distance : radious within which drivers are to be searched.
	 * @param limit : maximum number of drives to look for.
	 * @return
	 */
	Map<DriverLocation,Integer> getMaxDriverCountInRange(Location searchLocation, int distance, int limit);

	/**Get driver Ids near to a given location cell. 
	 * 
	 * @param location
	 * @return
	 */
	Set<Integer> getDrivers(Location location);

	/** add a driver to the system
	 * @param driverId
	 * @param location
	 */
	void addDriverLocation(int driverId, Location location);

}