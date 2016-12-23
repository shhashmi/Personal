package com.gojek.locator.dao;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.gojek.locator.model.DriverLocation;
import com.gojek.locator.model.Location;

import mockit.Expectations;
import mockit.Mocked;

public class LocationToDriverDaoImplTest {

	@Mocked
	private DriverLocationDao driverLoctionDao;

	@Test
	public void getMaxDriverTest() {

		new Expectations() {
			{
				driverLoctionDao.getLocation(1);
				result = new Location(10.00, 11.00);
			}

		};
		new Expectations() {
			{
				driverLoctionDao.getLocation(2);
				result = new Location(10.01, 11.01);
			}
		};
		LocationToDriverDaoImpl impl = new LocationToDriverDaoImpl();
		impl.setDriverLocation(driverLoctionDao);
		impl.addDriverLocation(1, new Location(10.00,11.00));
		impl.addDriverLocation(2, new Location(10.01,11.01));
		
		Map<DriverLocation,Integer> drivers = impl.getMaxDriverCountInRange(new Location(10.00,11.00), 100, 10);
		
		Assert.assertTrue(drivers.containsKey(new DriverLocation(1, new Location(10.00,11.00))));
		Assert.assertTrue(drivers.containsKey(new DriverLocation(2, new Location(10.01,11.01))));
		
		
	}
	

	@Test
	public void getMaxDriverTestDriverLimit() {

		new Expectations() {
			{
				driverLoctionDao.getLocation(1);
				result = new Location(10.00, 11.00);
				minTimes = 0;
			}

		};
		
		new Expectations() {
			{
				driverLoctionDao.getLocation(2);
				result = new Location(10.01, 11.01);
				minTimes = 0;
			}
		};
		LocationToDriverDaoImpl impl = new LocationToDriverDaoImpl();
		impl.setDriverLocation(driverLoctionDao);
		impl.addDriverLocation(1, new Location(10.00,11.00));
		impl.addDriverLocation(2, new Location(10.01,11.01));
		
		Map<DriverLocation,Integer> drivers = impl.getMaxDriverCountInRange(new Location(10.00,11.00), 100, 1);
		
		if(drivers.containsKey(new DriverLocation(1, new Location(10.00,11.00)))) {
			Assert.assertTrue(!drivers.containsKey(new DriverLocation(2, new Location(10.01,11.01))));	
		}
		else if (drivers.containsKey(new DriverLocation(2, new Location(10.01,11.01)))) {
			Assert.assertTrue(!drivers.containsKey(new DriverLocation(1, new Location(10.00,11.00))));	
		}
		
	}
	
	@Test
	public void getMaxDriverTestMaxDistance() {

		new Expectations() {
			{
				driverLoctionDao.getLocation(1);
				result = new Location(-80.00, -70.00);
				minTimes = 0;
			}

		};
		
		new Expectations() {
			{
				driverLoctionDao.getLocation(2);
				result = new Location(70.01, 71.01);
				minTimes = 0;
			}
		};
		LocationToDriverDaoImpl impl = new LocationToDriverDaoImpl();
		impl.setDriverLocation(driverLoctionDao);
		impl.addDriverLocation(1, new Location(10.00,11.00));
		impl.addDriverLocation(2, new Location(70.01,71.01));
		
		Map<DriverLocation,Integer> drivers = impl.getMaxDriverCountInRange(new Location(70.00,71.00), 100, 2);
		
		Assert.assertTrue(drivers.containsKey(new DriverLocation(2, new Location(70.01, 71.01))));
		
	}
	
	@Test
	public void getMaxDriverTestNoDriverFound() {

		new Expectations() {
			{
				driverLoctionDao.getLocation(1);
				result = new Location(-80.00, -70.00);
				minTimes = 0;
			}

		};
		
		new Expectations() {
			{
				driverLoctionDao.getLocation(2);
				result = new Location(-80.00, -70.00);
				minTimes = 0;
			}
		};
		LocationToDriverDaoImpl impl = new LocationToDriverDaoImpl();
		impl.setDriverLocation(driverLoctionDao);
		impl.addDriverLocation(1, new Location(-80.00, -70.00));
		impl.addDriverLocation(2, new Location(-80.00, -70.00));
		
		Map<DriverLocation,Integer> drivers = impl.getMaxDriverCountInRange(new Location(70.00,71.00), 100, 2);
		
		Assert.assertTrue(drivers.size()==0);
		
	}
	
	
	
	
}
