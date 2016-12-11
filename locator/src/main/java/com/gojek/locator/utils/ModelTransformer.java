package com.gojek.locator.utils;

import com.gojek.locator.model.DriverLocation;
import com.gojek.locator.model.Location;
import com.gojek.locator.model.UpdateDriverLocationRequest;

public class ModelTransformer {

	public static DriverLocation getDriverLocation(int driverId,UpdateDriverLocationRequest updateRequest) {
		return new DriverLocation(driverId, new Location(updateRequest.getLatitude(), updateRequest.getLongitude()));
	}
}
