package com.gojek.locator.service.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.gojek.locator.cache.DriverLocationCache;
import com.gojek.locator.cache.LocationToDriverCache;
import com.gojek.locator.model.DriverLocation;
import com.gojek.locator.model.GetDriverResponse;
import com.gojek.locator.model.Location;
import com.gojek.locator.model.UpdateDriverLocationRequest;
import com.gojek.locator.service.DriverService;
import com.gojek.locator.utils.ModelTransformer;

@Component
public class DriverServiceImpl implements DriverService{

	@Autowired
	private DriverLocationCache driverLocationCache;
	
	@Autowired
	private LocationToDriverCache locationToDriverCache;
	
	@Autowired
	@Qualifier("com.gojek.driverupdate.requestqueue")
	private LinkedBlockingQueue<UpdateDriverLocationRequest> linkedQueue;
	
	@Autowired
	@Qualifier("com.gojek.driverupdate.threadpool")
	private ThreadPoolTaskExecutor executor;
	@Override
	public void handleUpdateRequest(int driverId,UpdateDriverLocationRequest request) {
		
		//driverLocationCache.updateLocation(driverId, new Location(request.getLatitude(), request.getLongitude()));
		DriverLocation location = ModelTransformer.getDriverLocation(driverId, request);
		updateCache(new DriverLocation(driverId,new Location(request.getLatitude(),request.getLongitude())));
//		DriverLocationUpdater updator = new DriverLocationUpdater(location, driverLocationCache,locationToDriverCache);
//		executor.execute(updator);
	}
	
	private void updateCache(DriverLocation location) {
		DriverLocation driverPreviousLocation = new DriverLocation(location.getDriverId(), driverLocationCache.getLocation(location.getDriverId()));
		locationToDriverCache.removeDriver(driverPreviousLocation);
		driverLocationCache.updateLocation(location.getDriverId(), location.getLocation());
		locationToDriverCache.addToCache(location.getDriverId(), location.getLocation());
	}

	@Override
	public Set<GetDriverResponse> getDrivers(Location location, int driverCountLimit, int radius) {
		Map<DriverLocation,Integer> driverLocationWithDistance = locationToDriverCache.getMaxDriverCountInRange(location, radius, driverCountLimit);
		Set<GetDriverResponse> driverLocationsWithDistance = new HashSet<GetDriverResponse>();
		driverLocationWithDistance.forEach(((dLoc,disntace)->{
			GetDriverResponse getDriverResponse = new GetDriverResponse();
			getDriverResponse.setDriverId(dLoc.getDriverId());
			Location driverLocation = dLoc.getLocation();
			getDriverResponse.setLatitude(driverLocation.getLatitude());
			getDriverResponse.setLongitude(driverLocation.getLongitude());
			getDriverResponse.setDistance(location.diff(driverLocation));
			driverLocationsWithDistance.add(getDriverResponse);
		}));
		
		return driverLocationsWithDistance;
	}
	
	

}
