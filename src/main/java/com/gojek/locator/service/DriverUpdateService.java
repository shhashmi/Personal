package com.gojek.locator.service;
import com.gojek.locator.model.UpdateDriverLocationRequest;


public interface DriverUpdateService {

	void handleUpdateRequest(int driverId,UpdateDriverLocationRequest request);
}
