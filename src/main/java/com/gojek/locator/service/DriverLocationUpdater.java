package com.gojek.locator.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.gojek.locator.cache.DriverLocationCache;
import com.gojek.locator.cache.LocationToDriverCache;
import com.gojek.locator.model.DriverLocation;
import com.gojek.locator.model.UpdateDriverLocationRequest;

public class DriverLocationUpdater implements Runnable{
	
	private DriverLocation request;
	private DriverLocationCache driverLocationCache;
	private LocationToDriverCache locationToDriverCache;
	
	public DriverLocationUpdater(DriverLocation request,DriverLocationCache driverLocationCache,LocationToDriverCache locationToDriverCache) {
		this.request = request;
		this.driverLocationCache = driverLocationCache;
		this.locationToDriverCache = locationToDriverCache;
	}
	
	@Override
	public void run() {
		
		
	}

}
