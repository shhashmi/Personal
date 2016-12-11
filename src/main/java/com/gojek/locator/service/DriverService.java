package com.gojek.locator.service;
import java.util.Set;

import com.gojek.locator.model.GetDriverResponse;
import com.gojek.locator.model.Location;
import com.gojek.locator.model.UpdateDriverLocationRequest;
import com.gojek.locator.model.UpdateDriverLocationResponse;


public interface DriverService {

	UpdateDriverLocationResponse handleUpdateRequest(int driverId,UpdateDriverLocationRequest request);
	
	Set<GetDriverResponse> getDrivers(Location location,int driverCountLimit,int radius);
}
