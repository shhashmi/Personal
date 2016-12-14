package com.gojek.locator.service;
import com.gojek.locator.model.GetDriverResponse;
import com.gojek.locator.model.Location;
import com.gojek.locator.model.UpdateDriverLocationRequest;
import com.gojek.locator.model.UpdateDriverLocationResponse;


public interface DriverService {

	/** hadles request to update driver location, if driver information did not exist in system it is added otherwise udpated
	 * @param driverId
	 * @param request
	 * @return
	 */
	UpdateDriverLocationResponse handleUpdateRequest(int driverId,UpdateDriverLocationRequest request);
	
	/**
	 * Get the drivers around a location
	 * @param location
	 * @param driverCountLimit
	 * @param radius
	 * @return
	 */
	GetDriverResponse getDrivers(Location location,int driverCountLimit,int radius);
}
