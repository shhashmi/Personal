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
import com.gojek.locator.model.GetDriverResponse.NearByDriver;
import com.gojek.locator.model.Location;
import com.gojek.locator.model.UpdateDriverLocationRequest;
import com.gojek.locator.model.UpdateDriverLocationResponse;
import com.gojek.locator.service.DriverService;
import com.gojek.locator.utils.Constants;
import com.gojek.locator.utils.ModelTransformer;

@Component
public class DriverServiceImpl implements DriverService {

	@Autowired
	private DriverLocationDao driverLocationdao;

	@Autowired
	private LocationToDriverDao locationToDriverdao;

	@Override
	public void handleUpdateRequest(int driverId, UpdateDriverLocationRequest request) {

		DriverLocation location = ModelTransformer.getDriverLocation(driverId, request);
		updateLocation(location);

	}

	private void updateLocation(DriverLocation location) {
		DriverLocation driverPreviousLocation = new DriverLocation(location.getDriverId(),
				driverLocationdao.getLocation(location.getDriverId()));
		locationToDriverdao.removeDriver(driverPreviousLocation);
		driverLocationdao.updateLocation(location.getDriverId(), location.getLocation());
		locationToDriverdao.addDriverLocation(location.getDriverId(), location.getLocation());
	}

	@Override
	public GetDriverResponse getDrivers(Location location, int driverCountLimit, int radius) {
		Map<DriverLocation, Integer> driverLocationWithDistance = locationToDriverdao
				.getMaxDriverCountInRange(location, radius, driverCountLimit);
		Set<NearByDriver> driverLocationsWithDistance = new HashSet<NearByDriver>();

		driverLocationWithDistance.forEach(((dLoc, disntace) -> {
			NearByDriver driver = new NearByDriver();
			driver.setDriverId(dLoc.getDriverId());
			Location driverLocation = dLoc.getLocation();
			driver.setLatitude(driverLocation.getLatitude());
			driver.setLongitude(driverLocation.getLongitude());
			driver.setDistance(location.diff(driverLocation));
			driverLocationsWithDistance.add(driver);
		}));
		GetDriverResponse response = new GetDriverResponse();
		response.setDrivers(driverLocationsWithDistance);
		return response;
	}

	public DriverLocationDao getDriverLocationdao() {
		return driverLocationdao;
	}

	public void setDriverLocationdao(DriverLocationDao driverLocationdao) {
		this.driverLocationdao = driverLocationdao;
	}

	public LocationToDriverDao getLocationToDriverdao() {
		return locationToDriverdao;
	}

	public void setLocationToDriverdao(LocationToDriverDao locationToDriverdao) {
		this.locationToDriverdao = locationToDriverdao;
	}
	
	

}
