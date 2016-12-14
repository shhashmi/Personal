package com.gojek.locator.dao;

import com.gojek.locator.model.Location;

public interface DriverLocationDao {

	
	/**Given a driver id this method returns current location of driver. If data for driver
	 * does not exist then null is returned
	 * @param driverId
	 * @return Location
	 */
	Location getLocation(Integer driverId);

	/** Update the current location of driver. 
	 * @param driverId
	 * @param location
	 */
	void updateLocation(Integer driverId, Location location);

}