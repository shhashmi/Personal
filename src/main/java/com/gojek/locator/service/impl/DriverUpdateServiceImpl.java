package com.gojek.locator.service.impl;

import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.gojek.locator.cache.DriverLocationCache;
import com.gojek.locator.model.DriverLocation;
import com.gojek.locator.model.Location;
import com.gojek.locator.model.UpdateDriverLocationRequest;
import com.gojek.locator.service.DriverLocationUpdater;
import com.gojek.locator.service.DriverUpdateService;
import com.gojek.locator.utils.ModelTransformer;

public class DriverUpdateServiceImpl implements DriverUpdateService{

	@Autowired
	private DriverLocationCache driverLocationCache;
	
	@Autowired
	@Qualifier("com.gojek.driverupdate.requestqueue")
	private LinkedBlockingQueue<UpdateDriverLocationRequest> linkedQueue;
	
	@Autowired
	@Qualifier("com.gojek.driverupdate.threadpool")
	private ThreadPoolTaskExecutor executor;
	@Override
	public void handleUpdateRequest(int driverId,UpdateDriverLocationRequest request) {
		
		driverLocationCache.updateLocation(driverId, new Location(request.getLatitude(), request.getLongitude()));
		DriverLocation location = ModelTransformer.getDriverLocation(driverId, request);
		DriverLocationUpdater updator = new DriverLocationUpdater(location);
		executor.execute(updator);
	}

}
