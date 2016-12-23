package com.gojek.locator.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.gojek.locator.dao.DriverLocationDao;
import com.gojek.locator.dao.LocationToDriverDao;
import com.gojek.locator.model.DriverLocation;
import com.gojek.locator.model.GetDriverResponse;
import com.gojek.locator.model.GetDriverResponse.NearByDriver;
import com.gojek.locator.model.Location;
import com.gojek.locator.model.UpdateDriverLocationRequest;

import mockit.Expectations;
import mockit.Mocked;

public class DriverServiceImplTest {

	@Mocked
	private DriverLocationDao driverLocationdao;

	@Mocked
	private LocationToDriverDao locationToDriverdao;
	
	
	private void setExpectations() {

		Location oldLocation = new Location(10.01010,11.000);
		Location newLocation = new Location(12.000,33.0000);
		
		new Expectations() {
			{
				driverLocationdao.getLocation(1);
				result = oldLocation;
			}
		};
		
		new Expectations() {
			{
				locationToDriverdao.removeDriver(new DriverLocation(1, oldLocation));
				
			}
		};
		
		new Expectations() {
			{
				driverLocationdao.updateLocation(1, newLocation);
			}
		};
		
		new Expectations() {
			{
				locationToDriverdao.addDriverLocation(1, newLocation);
			}
		};
	}
	
	@Test
	public void handleUpdateRequestTest() {
		setExpectations();
		DriverServiceImpl driverService = new DriverServiceImpl();
		driverService.setDriverLocationdao(driverLocationdao);
		driverService.setLocationToDriverdao(locationToDriverdao);
		UpdateDriverLocationRequest request = new UpdateDriverLocationRequest();
		request.setLatitude(12.000);
		request.setLongitude(33.0000);
		driverService.handleUpdateRequest(1, request);
	}
	
	
	@Test
	public void getDriversDriversFoundTest() {
		Location location = new Location(10.101010,12.101010);
		DriverLocation driverLocation1 = new DriverLocation(1, new Location(20.151010,12.131010));
		DriverLocation driverLocation2 = new DriverLocation(45,new Location(20.191010,22.181010));
		Map<DriverLocation ,Integer> driverDistanceMap = new HashMap<DriverLocation,Integer>();
		driverDistanceMap.put(driverLocation1, 12);
		driverDistanceMap.put(driverLocation2, 10);
		NearByDriver driver1 = new NearByDriver();
		driver1.setDriverId(45);
		driver1.setDistance(14);
		driver1.setLatitude(20.191010);
		driver1.setLongitude(22.181010);
		
		NearByDriver driver2 = new NearByDriver();
		driver2.setDriverId(1);
		driver2.setDistance(10);
		driver2.setLatitude(20.151010);
		driver2.setLongitude(12.131010);
		
		Set<NearByDriver> drivers = new HashSet<NearByDriver>();
		drivers.add(driver1);
		drivers.add(driver2);
		
		
		
		new Expectations() {
			{
				locationToDriverdao.getMaxDriverCountInRange(location, 12, 10);
				result = driverDistanceMap;
			}
		};
		
		DriverServiceImpl driverService = new DriverServiceImpl();
		driverService.setLocationToDriverdao(locationToDriverdao);
		GetDriverResponse response = driverService.getDrivers(location, 10, 12);

		Assert.assertEquals(response.getDrivers(), drivers);

	}
	

	@Test
	public void getDriversDriversNotFoundTest() {
		Location location = new Location(10.101010,12.101010);
		
		Map<DriverLocation ,Integer> driverDistanceMap = new HashMap<DriverLocation,Integer>();
		
		
		new Expectations() {
			{
				locationToDriverdao.getMaxDriverCountInRange(location, 12, 10);
				result = driverDistanceMap;
			}
		};
		
		DriverServiceImpl driverService = new DriverServiceImpl();
		driverService.setLocationToDriverdao(locationToDriverdao);
		GetDriverResponse response = driverService.getDrivers(location, 10, 12);

		Assert.assertEquals(response.getDrivers(), new HashSet<NearByDriver>());
		Assert.assertEquals(response.getErrors(), null);

	}
	
	
}
