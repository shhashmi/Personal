package com.gojek.locator.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.gojek.locator.dao.DriverLocationDao;
import com.gojek.locator.dao.LocationToDriverDao;
import com.gojek.locator.error.ErrorMessage;
import com.gojek.locator.model.DriverLocation;
import com.gojek.locator.model.GetDriverResponse;
import com.gojek.locator.model.Location;
import com.gojek.locator.model.UpdateDriverLocationRequest;
import com.gojek.locator.model.UpdateDriverLocationResponse;
import com.gojek.locator.service.DriverService;
import com.gojek.locator.utils.Constants;
import com.gojek.locator.utils.ModelTransformer;

@Component
public class DriverServiceImpl implements DriverService{

	@Autowired
	private DriverLocationDao driverLocationCache;
	
	@Autowired
	private LocationToDriverDao locationToDriverCache;
	
	@Autowired
	@Qualifier("com.gojek.driverupdate.requestqueue")
	private LinkedBlockingQueue<UpdateDriverLocationRequest> linkedQueue;
	
	@Autowired
	@Qualifier("com.gojek.driverupdate.threadpool")
	private ThreadPoolTaskExecutor executor;
	
	
	@Override
	public UpdateDriverLocationResponse handleUpdateRequest(int driverId,UpdateDriverLocationRequest request) {
		
		UpdateDriverLocationResponse errorResponse = validateErrors(request);
		if(errorResponse!=null) {
			DriverLocation location = ModelTransformer.getDriverLocation(driverId, request);
			updateCache(location);
		}
		return errorResponse;
	}
	
	private UpdateDriverLocationResponse validateErrors(UpdateDriverLocationRequest request) {
		UpdateDriverLocationResponse response = null;
		List<String> errorMessages = new ArrayList<String>();
		if(request.getLatitude()<Constants.MIN_LATITUDE || request.getLatitude()>Constants.MAX_LATITUDE) {
			 errorMessages.add(ErrorMessage.InvalidLatitude.getMessage());
		}
		if(request.getLongitude()<-Constants.MIN_LONGITUDE || request.getLongitude()>Constants.MAX_LONGITUDE) {
			errorMessages.add(ErrorMessage.InvalidLongitude.getMessage());
		}

		if(!errorMessages.isEmpty()) {
			response = new UpdateDriverLocationResponse();
			response.setErrors(errorMessages.toArray(new String[errorMessages.size()]));
		}
		
		return response;
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
